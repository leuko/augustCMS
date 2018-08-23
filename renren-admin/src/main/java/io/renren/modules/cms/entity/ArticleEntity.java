package io.renren.modules.cms.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章管理
 * 
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-16 18:08:18
 */
@TableName("articles")
public class ArticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer articleCategoryId;
	/**
	 * 
	 */
	private String articleTitle;
	/**
	 * 
	 */
	private String articleDesc;


	private String thumbnail;

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	private Integer isTop;

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	/**
	 * 
	 */
	private String articalBody;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String metaTitle;
	/**
	 * 
	 */
	private String metaDesc;
	/**
	 * 
	 */
	private Date updatedAt;
	/**
	 * 
	 */
	private Date createdAt;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setArticleCategoryId(Integer articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}
	/**
	 * 获取：
	 */
	public Integer getArticleCategoryId() {
		return articleCategoryId;
	}
	/**
	 * 设置：
	 */
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	/**
	 * 获取：
	 */
	public String getArticleTitle() {
		return articleTitle;
	}
	/**
	 * 设置：
	 */
	public void setArticleDesc(String articleDesc) {
		this.articleDesc = articleDesc;
	}
	/**
	 * 获取：
	 */
	public String getArticleDesc() {
		return articleDesc;
	}
	/**
	 * 设置：
	 */
	public void setArticalBody(String articalBody) {
		this.articalBody = articalBody;
	}
	/**
	 * 获取：
	 */
	public String getArticalBody() {
		return articalBody;
	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}
	/**
	 * 获取：
	 */
	public String getMetaTitle() {
		return metaTitle;
	}
	/**
	 * 设置：
	 */
	public void setMetaDesc(String metaDesc) {
		this.metaDesc = metaDesc;
	}
	/**
	 * 获取：
	 */
	public String getMetaDesc() {
		return metaDesc;
	}
	/**
	 * 设置：
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	/**
	 * 获取：
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * 设置：
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * 获取：
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
}
