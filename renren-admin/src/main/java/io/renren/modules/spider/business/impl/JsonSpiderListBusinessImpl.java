package io.renren.modules.spider.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.modules.spider.business.SpiderListBusiness;
import io.renren.modules.spider.domain.ArticleUrl;
import io.renren.modules.spider.entity.SpiderProjectEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class JsonSpiderListBusinessImpl implements SpiderListBusiness {

    /**
     * @param projectEntity
     * @param page
     * @return
     * @throws IOException
     */
    @Override
    public List<ArticleUrl> grabDetailUrl(SpiderProjectEntity projectEntity, Integer page) throws IOException {
        if (!"json".equals(projectEntity.getReturnContentType())) {
            return null;
        }
        // 1、创建 Document
        Document document = connect(projectEntity, page);


        // 2、解析json
        String json = document.text();
        logger.debug("Response: {}", json);
        JSONObject parseObject = (JSONObject) JSON.parse(json);

        // 3、解析字段
        JSONArray result = valueByPath(projectEntity.getDetailUrlSelector(), parseObject);

        List detailUrl = new ArrayList();
        for (Object item : result.toArray()) {
            JSONObject tmpJsonObject = (JSONObject) item;
            ArticleUrl articleUrl = new ArticleUrl();
            articleUrl.setProjectId(projectEntity.getId());
            if (projectEntity.getPicUrlSelector() != null && !"".equals(projectEntity.getPicUrlSelector())) {
                articleUrl.setPicUrl(normalUrl(projectEntity.getListUrl(), valueByPath(projectEntity.getPicUrlSelector(), tmpJsonObject)));
            }
            if (projectEntity.getTitleSelector() != null && !"".equals(projectEntity.getTitleSelector())) {
                articleUrl.setTitle(valueByPath(projectEntity.getTitleSelector(), tmpJsonObject));
            }
            String url = projectEntity.getDetailUrlArtifact();
            for (String key : tmpJsonObject.keySet()) {
                if (tmpJsonObject.get(key) == null) {
                    continue;
                }
                url = url.replace("{" + key + "}", valueByPath(key, tmpJsonObject).toString());
            }
            articleUrl.setUrl(normalUrl(projectEntity.getListUrl(), url));
            detailUrl.add(articleUrl);
        }

        return detailUrl;
    }

    @Override
    public Map grabDetail(ArticleUrl articleUrl) throws IOException {

        return null;
    }

    /**
     * 点路径法 获取JSONObject中的元素（不支持数组列表）
     *
     * @param path
     * @param jsonObject
     * @param <T>
     * @return
     */
    public static <T> T valueByPath(String path, JSONObject jsonObject) {

        String[] split = path.split("\\.");
        Object result = null;

        if (split.length == 1) {
            result = jsonObject.getOrDefault(split[0], null);
        } else {
            JSONObject tmpObject = jsonObject.getJSONObject(split[0]);
            for (int i = 1; i < split.length; i++) {
                if (tmpObject == null) {
                    break;
                }
                if (i == split.length - 1) {
                    result = tmpObject.getOrDefault(split[i], null);
                } else {
                    tmpObject = tmpObject.getJSONObject(split[i]);
                }
            }
        }

        return result == null ? null : (T) result;
    }
}
