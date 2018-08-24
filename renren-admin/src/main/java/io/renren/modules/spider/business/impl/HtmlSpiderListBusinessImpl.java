package io.renren.modules.spider.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.modules.oss.service.SysOssService;
import io.renren.modules.spider.business.SpiderListBusiness;
import io.renren.modules.spider.domain.ArticleUrl;
import io.renren.modules.spider.entity.SpiderProjectColumnEntity;
import io.renren.modules.spider.entity.SpiderProjectEntity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HtmlSpiderListBusinessImpl implements SpiderListBusiness {

    @Autowired
    SysOssService sysOssService;

    /**
     * @param projectEntity
     * @param page
     * @return
     * @throws IOException
     */
    @Override
    public List<ArticleUrl> grabDetailUrl(SpiderProjectEntity projectEntity, Integer page) throws IOException {
        List<ArticleUrl> detailUrl = new ArrayList();
        if (!"html".equals(projectEntity.getReturnContentType())) {
            return detailUrl;
        }
        // 1、创建 Document
        Document document = connectForList(projectEntity, page);

        // 2、解析字段
        Elements links = document.select(projectEntity.getDetailUrlSelector());
//        Elements images = document.select(projectEntity.getPicUrlSelector());
        Elements titles = document.select(projectEntity.getTitleSelector());
        if (titles == null || links == null || links.size() == 0 || links.size() != titles.size()) {
            logger.error("抓取列表页错误：url {}, detail url selector[{}], title selector [{}]",
                    projectEntity.getListUrl(),
                    projectEntity.getDetailUrlSelector(), projectEntity.getTitleSelector()
            );
            throw new RuntimeException("抓取的链接数" + links.size() + "与标题数量不相等" + titles.size());
        }
        int index = 0;
        for (Element title : titles) {
            ArticleUrl articleUrl = new ArticleUrl();
            articleUrl.setProjectId(projectEntity.getId());
            articleUrl.setTitle(title.text());
            Element link = links.get(index++);
            link.setBaseUri(projectEntity.getListUrl());
            articleUrl.setUrl(link.absUrl("href"));
            detailUrl.add(articleUrl);
        }

        return detailUrl;
    }

    @Override
    public Map grabDetail(SpiderProjectEntity projectEntity, ArticleUrl articleUrl) throws IOException {

        Map resultMap = new HashMap();

        if (!"html".equals(projectEntity.getDetailReturnContentType())) {
            return resultMap;
        }
        // 1、创建 Document
        Document document = connectForDetail(projectEntity, articleUrl);

        // 2、解析字段
        List<SpiderProjectColumnEntity> columnEntities = projectEntity.getColumnEntity();
        for (SpiderProjectColumnEntity columnEntity : columnEntities) {
            Object columnContent = dealColumnContent(
                    articleUrl.getUrl(),
                    columnEntity,
                    fieldSelector -> document.select(fieldSelector).html(),
                    imgUrl -> {
                        try {
                            return sysOssService.upload("spider", imgUrl, new URL(imgUrl).openStream()).getUrl();
                        } catch (Exception e) {
                            logger.error("抓取图片出错：{}, {}", imgUrl, e.getMessage());
                            return imgUrl;
                        }
                    }
            );
            resultMap.put(columnEntity.getTableColumn(), columnContent);
        }

        return resultMap;
    }


}
