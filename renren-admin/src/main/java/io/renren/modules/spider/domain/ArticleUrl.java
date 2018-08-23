package io.renren.modules.spider.domain;

/**
 * 抓取列表中，每篇文章的信息
 */
public class ArticleUrl {

    Integer projectId;
    String picUrl;
    String title;
    String url;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ArticleUrl{" +
                "projectId=" + projectId +
                ", picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
