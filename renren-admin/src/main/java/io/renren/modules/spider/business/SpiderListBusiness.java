package io.renren.modules.spider.business;

import io.renren.common.utils.DateUtils;
import io.renren.modules.spider.domain.ArticleUrl;
import io.renren.modules.spider.entity.SpiderProjectEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

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

    Map grabDetail(ArticleUrl articleUrl) throws IOException;

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
     * @param projectEntity
     * @param page
     * @return
     * @throws IOException
     */
    default Document connect(SpiderProjectEntity projectEntity, Integer page) throws IOException {


        String url = projectEntity.getListUrl().replace("{page}", page.toString());
        Connection connection = Jsoup.connect(url);
        connection.ignoreContentType(true);
        logger.debug("Connection url:{}", url);

        //post Data
        String postData = projectEntity.getSpiderOptionPostData();
        if (postData != null && !"".equals(postData)) {
            Map mapPostData = new HashMap();
            Arrays.stream(postData.split("\n")).forEach(line -> {
                String[] splitLine = line.split(":", 2);
                if (splitLine.length < 2) return;
                String originalValue = splitLine[1].trim()
                        .replace("{page}", page.toString())
                        .replace("{time}", Long.toString(new Date().getTime()));
                String treatedValue = originalValue;
                if (treatedValue.matches("base64|urlencode")) {
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
            connection.data(mapPostData);
        }

        //cookie
        String cookie = projectEntity.getSpiderOptionCookie();
        if (cookie != null && !"".equals(cookie)) {
            Map mapPostData = new HashMap();
            Arrays.stream(cookie.split("\n")).forEach(line -> {
                String[] splitLine = line.split(":", 2);
                if (splitLine.length < 2) return;
                mapPostData.put(splitLine[0].trim(), splitLine[1].trim().replace("{page}", page.toString()));
            });
            connection.cookies(mapPostData);
        }

        //header
        String header = projectEntity.getSpiderOptionHeader();
        if (header != null && !"".equals(header)) {
            Map mapPostData = new HashMap();
            Arrays.stream(header.split("\n")).forEach(line -> {
                String[] splitLine = line.split(":", 2);
                if (splitLine.length < 2) return;
                mapPostData.put(splitLine[0].trim(), splitLine[1].trim().replace("{page}", page.toString()));
            });
            connection.headers(mapPostData);
        }

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

}
