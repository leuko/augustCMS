package io.renren.modules.spider.business;

import io.renren.common.utils.DateUtils;
import io.renren.modules.spider.domain.ArticleUrl;
import io.renren.modules.spider.entity.SpiderProjectColumnEntity;
import io.renren.modules.spider.entity.SpiderProjectEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;

public interface SpiderListBusiness {

    Logger logger = LoggerFactory.getLogger(SpiderListBusiness.class);

    /**
     * 抓取列表中的url
     *
     * @param projectEntity
     * @param page
     * @return
     * @throws IOException
     */
    List<ArticleUrl> grabDetailUrl(SpiderProjectEntity projectEntity, Integer page) throws IOException;


    /**
     * 抓取详情页的内容
     *
     * @param projectEntity
     * @param articleUrl
     * @return
     * @throws IOException
     */
    Map grabDetail(SpiderProjectEntity projectEntity, ArticleUrl articleUrl) throws IOException;


    /**
     * 解析 parentUrl 网页中抓取的 subUrl
     *
     * @param parentUrl
     * @param subUrl
     * @return
     */
    default String normalUrl(String parentUrl, String subUrl) {
        if (parentUrl == null || subUrl == null) {
            return "";
        }
        subUrl = subUrl.toLowerCase();
        if (subUrl.startsWith("http://") || subUrl.startsWith("https://")) {
            return subUrl;
        }
        try {
            URI uri = new URI(parentUrl).resolve(subUrl);
            return uri.normalize().toString();
        } catch (URISyntaxException e) {
            return subUrl;
        }
    }


    /**
     * 列表页connection
     *
     * @param projectEntity
     * @param page
     * @return
     * @throws IOException
     */
    default Document connectForList(SpiderProjectEntity projectEntity, Integer page) throws IOException {

        String url = projectEntity.getListUrl().replace("{page}", page.toString());
        Connection connection = Jsoup.connect(url);
        connection.ignoreContentType(true);
        logger.info("Connection url:{}", url);

        //post Data
        connection.data(replaceDataToMap(projectEntity.getSpiderOptionPostData(), page));

        //cookie
        connection.cookies(replaceDataToMap(projectEntity.getSpiderOptionCookie(), page));

        //header
        connection.headers(replaceDataToMap(projectEntity.getSpiderOptionHeader(), page));

        //user agent
        String userAgent = projectEntity.getSpiderOptionUserAgent();
        if (userAgent != null && !"".equals(userAgent)) {
            connection.userAgent(userAgent);
        }

        //referer
        String referer = projectEntity.getSpiderOptionReferer();
        if (referer != null && !"".equals(referer)) {
            connection.referrer(referer);
        }

        //timeout
        String timeout = projectEntity.getSpiderOptionReferer();
        if (timeout != null && !"".equals(timeout)) {
            connection.timeout(Integer.valueOf(timeout));
        }

        if ("post".equals(projectEntity.getMethod())) {
            return connection.post();
        } else {
            return connection.get();
        }
    }

    /**
     * 详情页connection
     *
     * @param projectEntity
     * @param articleUrl
     * @return
     * @throws IOException
     */
    default Document connectForDetail(SpiderProjectEntity projectEntity, ArticleUrl articleUrl) throws IOException {

        String url = articleUrl.getUrl();
        Connection connection = Jsoup.connect(url);
        connection.ignoreContentType(true);
        logger.info("Connection url:{}", url);

        //post Data
        connection.data(replaceDataToMap(projectEntity.getDetailPostData(), null));

        //cookie
        connection.cookies(replaceDataToMap(projectEntity.getSpiderOptionCookie(), null));

        //header
        connection.headers(replaceDataToMap(projectEntity.getSpiderOptionHeader(), null));

        //user agent
        String userAgent = projectEntity.getSpiderOptionUserAgent();
        if (userAgent != null && !"".equals(userAgent)) {
            connection.userAgent(userAgent);
        }

        //referer
        String referer = projectEntity.getDetailReferer();
        if (referer != null && !"".equals(referer)) {
            connection.referrer(referer);
        }

        //timeout
        String timeout = projectEntity.getSpiderOptionReferer();
        if (timeout != null && !"".equals(timeout)) {
            connection.timeout(Integer.valueOf(timeout));
        }

        if ("post".equals(projectEntity.getDetailMethod())) {
            return connection.post();
        } else {
            return connection.get();
        }
    }

    /**
     *
     * @param baseUrl
     * @param columnEntity
     * @param function 箭头函数：参数为field selector，返回值为抓取到的内容
     * @return
     */
    default Object dealColumnContent(String baseUrl, SpiderProjectColumnEntity columnEntity, Function<String, String> function) {

        // 1、如果选择器没填，返回默认值
        if (columnEntity.getFieldSelector() == null || "".equals(columnEntity.getFieldSelector())) {
            return columnEntity.getDefaultValue();
        }

        // 2、从选择器中获取内容
        String grabContent = function.apply(columnEntity.getFieldSelector());
        if (grabContent == null || "".equals(grabContent)) {
            return columnEntity.getDefaultValue();
        }

        if (columnEntity.getIsPic() == 1) { // 3、是否图片 (注意加工：图片路径是否http开头)

            String imgUrl = normalUrl(baseUrl, grabContent);

        } else if (columnEntity.getIsDate() == 1) { // 4、是否日期格式

            if(columnEntity.getDateFormat()==null || "".equals(columnEntity.getDateFormat())){
                return null;
            }
            try {
                return DateUtils.stringToDate(grabContent, columnEntity.getDateFormat());
            }catch (Exception e){
                logger.error("日期转换错误：日期 {}, 格式 {}", grabContent, columnEntity.getDateFormat());
            }

        } else { // 5、文本内容处理
            // 过滤白名单外的html tag；过滤内容白名单类型：none,simpleText,basic,basicWithImages,relaxed
            switch (columnEntity.getContentWhitelistType().toLowerCase()){
                case "simpletext":
                    grabContent = Jsoup.clean(grabContent, Whitelist.simpleText());
                    break;
                case "basic":
                    grabContent = Jsoup.clean(grabContent, Whitelist.basic());
                    break;
                case "basicwithimages":
                    grabContent = Jsoup.clean(grabContent, Whitelist.basicWithImages());
                    break;
                case "relaxed":
                    grabContent = Jsoup.clean(grabContent, Whitelist.relaxed());
                    break;
            }
            // 内容中的图片下载到oss (注意加工：图片路径是否http开头)
            Document parseDocument = Jsoup.parse(grabContent);
            Elements images = parseDocument.select("img");
            for (Element image : images) {
                image.setBaseUri(baseUrl);
                String imgUrl = image.absUrl("src");
            }

        }

        return null;
    }

    /**
     * Connection中的 Post Data, cookie, header 中{time}{page}变量的替换，modifier base64，urlencode的执行
     *
     * @param postData
     * @param page
     * @return
     */
    default Map replaceDataToMap(String postData, Integer page) {
        Map mapPostData = new HashMap();
        if (postData == null || "".equals(postData)) {
            return mapPostData;
        }
        String curTime = Long.toString(new Date().getTime());
        Arrays.stream(postData.split("\n")).forEach(line -> {
            String[] splitLine = line.split(":", 2);
            if (splitLine.length < 2) return;
            String originalValue = splitLine[1].trim()
                    .replace("{time}", Long.toString(new Date().getTime()));
            if (page != null) {
                originalValue = originalValue.replace("{page}", page.toString());
            }
            String treatedValue = originalValue;
            if (treatedValue.matches("|base64|urlencode")) {
                treatedValue = treatedValue.substring(0, treatedValue.lastIndexOf("|"));
                if (originalValue.contains("urlencode")) {
                    try {
                        treatedValue = URLEncoder.encode(treatedValue, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                if (originalValue.contains("base64")) {
                    treatedValue = Base64.getEncoder().encodeToString(treatedValue.getBytes());
                }
            }
            mapPostData.put(splitLine[0].trim(), treatedValue);
        });
        return mapPostData;
    }

}
