package io.renren.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.renren.BaseTest;
import io.renren.modules.spider.business.impl.JsonSpiderListBusinessImpl;
import io.renren.modules.spider.entity.SpiderProjectEntity;
import io.renren.modules.spider.service.SpiderProjectService;
import io.renren.modules.spider.service.impl.SpiderProjectServiceImpl;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class SpiderProjectServiceTest extends BaseTest {

    @Autowired
    SpiderProjectServiceImpl spiderProjectService;

    @Autowired
    JsonSpiderListBusinessImpl jsonSpiderListBusiness;

    @Test
    public void addSpiderProjectService() {

        SpiderProjectEntity spiderProjectEntity = new SpiderProjectEntity();
        spiderProjectEntity.setCharsetName("UTF-8");
        spiderProjectEntity.setDetailUrlArtifact("https://www.qhee.com/node/listed-company/{id}&{user_name}");
        spiderProjectEntity.setListUrl("https://www.qhee.com/node/proxy-action/qhee-webapp/action/web/entapply/EntApplyAction/queryByCategory?pagesize=16");
        spiderProjectEntity.setMethod("post");
        spiderProjectEntity.setName("qhee");
        spiderProjectEntity.setReturnContentType("json");
        spiderProjectEntity.setPageStart(1);
        spiderProjectEntity.setPageEnd(366);
        spiderProjectEntity.setToTable("articles");
        spiderProjectEntity.setSpiderOptionPostData("_action:/qhee-webapp/action/web/entapply/EntApplyAction/queryByCategory?pagesize=16\npageno:{page}\ntype:listed_file");
        spiderProjectEntity.setDetailUrlSelector("data.record");
        spiderProjectEntity.setPicUrlSelector("url");
        spiderProjectEntity.setTitleSelector("full_name");
        spiderProjectService.insert(spiderProjectEntity);

//        SpiderProjectEntity spiderProjectEntity = new SpiderProjectEntity();
//        spiderProjectEntity.setCharsetName("UTF-8");
//        spiderProjectEntity.setDetailUrlArtifact("https://www.bjotc.com.cn/kssp/showcase-details-standard.html?customerno={customerno}");
//        spiderProjectEntity.setListUrl("https://www.bjotc.com.cn/api");
//        spiderProjectEntity.setMethod("post");
//        spiderProjectEntity.setName("北京四板交易系统");
//        spiderProjectEntity.setReturnContentType("json");
//        spiderProjectEntity.setPageStart(1);
//        spiderProjectEntity.setPageEnd(366);
//        spiderProjectEntity.setToTable("articles");
//        spiderProjectEntity.setSpiderOptionPostData("KiNGDOM:{\"pageNumber\":\"{page}\",\"pageSize\":\"8\",\"islisting\":\"1\",\"sorttype\":\"4\",\"chinesename\":\"\",\"companytype\":\"4\"}|urlencode,base64\nKINGdOM:kingdom.kifp.api_get_kssp_company_info_bj|urlencode,base64\nKInGDOM:{time}|base64\nKINGDoM:v1.0|base64");
//        spiderProjectEntity.setSpiderOptionHeader("Content-Type: application/x-www-form-urlencoded; charset=UTF-8");
//        spiderProjectEntity.setDetailUrlSelector("kdjson.items");
//        spiderProjectEntity.setPicUrlSelector("image");
//        spiderProjectEntity.setTitleSelector("chinesename");
//        spiderProjectService.insert(spiderProjectEntity);
    }

    @Test
    public void grabDetailUrl() throws IOException {

        SpiderProjectEntity spiderProjectEntity = spiderProjectService.selectById(4);
        List detailUrl = jsonSpiderListBusiness.grabDetailUrl(spiderProjectEntity, 1);

        System.out.println(JSON.toJSON(detailUrl));



    }

    @Test
    public void urldecode() throws UnsupportedEncodingException {
        System.out.println(new Date().getTime());
        System.out.println(new String(Base64.getDecoder().decode("MTUzNDk5MDA1MTQ5OQ==")));
        System.out.println(new String(Base64.getDecoder().decode("a2luZ2RvbS5raWZwLmFwaV9nZXRfa3NzcF9jb21wYW55X2luZm9fYmo=")));
        System.out.println(new String(Base64.getDecoder().decode("djEuMA==")));
        System.out.println(URLDecoder.decode(new String(Base64.getDecoder().decode("JTdCJTIycGFnZU51bWJlciUyMiUzQSUyMjYlMjIlMkMlMjJwYWdlU2l6ZSUyMiUzQSUyMjglMjIlMkMlMjJpc2xpc3RpbmclMjIlM0ElMjIxJTIyJTJDJTIyc29ydHR5cGUlMjIlM0ElMjI0JTIyJTJDJTIyY2hpbmVzZW5hbWUlMjIlM0ElMjIlMjIlMkMlMjJjb21wYW55dHlwZSUyMiUzQSUyMjQlMjIlN0Q=")), "UTF-8"));
        System.out.println(new String(Base64.getDecoder().decode("JTdCJTIycGFnZU51bWJlciUyMiUzQSUyMjYlMjIlMkMlMjJwYWdlU2l6ZSUyMiUzQSUyMjglMjIlMkMlMjJpc2xpc3RpbmclMjIlM0ElMjIxJTIyJTJDJTIyc29ydHR5cGUlMjIlM0ElMjI0JTIyJTJDJTIyY2hpbmVzZW5hbWUlMjIlM0ElMjIlMjIlMkMlMjJjb21wYW55dHlwZSUyMiUzQSUyMjQlMjIlN0Q=")));
        System.out.println(new String(Base64.getDecoder().decode("d3d3LmJqb3RjLmNvbS5jbg==")));
        System.out.println(new String(Base64.getDecoder().decode("d3d3LmJqb3RjLmNvbS5jbg==")));
    }

    @Test
    public void test (){
        System.out.println(spiderProjectService.selectByIdWithColumn(1).getColumnEntity());
    }
}
