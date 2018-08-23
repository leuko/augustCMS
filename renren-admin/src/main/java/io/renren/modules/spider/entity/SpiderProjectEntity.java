package io.renren.modules.spider.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据采集
 * 
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-21 17:58:10
 */
@TableName("spider_projects")
public class SpiderProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 列表URL, url中可以有{page}
	 */
	private String listUrl;
	/**
	 * Method： get，post
	 */
	private String method;
	/**
	 * 返回内容类型：json，html
	 */
	private String returnContentType;
	/**
	 * 抓取的网站编码：UTF-8，GBK，GB2312
	 */
	private String charsetName;
	/**
	 * 抓取起始页，会替换列表URL中的{page}
	 */
	private Integer pageStart;
	/**
	 * 抓取结束页，会替换列表URL中的{page}
	 */
	private Integer pageEnd;
	/**
	 * 伪造User-Agent
	 */
	private String spiderOptionUserAgent;
	/**
	 * 伪造来源
	 */
	private String spiderOptionReferer;
	/**
	 * 伪造Cookie
	 */
	private String spiderOptionCookie;
	/**
	 * 伪造header，key:value 多个换行
	 */
	private String spiderOptionHeader;
	/**
	 * POST数据：如果method=post时有效，key:value 多个换行
	 */
	private String spiderOptionPostData;
	/**
	 * connection timeout seconds
	 */
	private String spiderOptionTimeout;
	/**
	 * 抓取详情页url的选择器语法，兼容CSS or Jquery；若是JSON数据使用.法 
	 */
	private String detailUrlSelector;
	/**
	 * 抓取详情页url，人工合成，大括号中的变量为，json中的值，如：http://www.baidu.com/{id}-{name}
	 */
	private String detailUrlArtifact;
	/**
	 * 抓取详情页缩略图的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名 
	 */
	private String picUrlSelector;
	/**
	 * 抓取详情页title的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名
	 */
	private String titleSelector;
	/**
	 * 抓取的数据插入的表
	 */
	private String toTable;
	/**
	 * 
	 */
	private Date createTime;

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
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：列表URL, url中可以有{page}
	 */
	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}
	/**
	 * 获取：列表URL, url中可以有{page}
	 */
	public String getListUrl() {
		return listUrl;
	}
	/**
	 * 设置：Method： get，post
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：Method： get，post
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：返回内容类型：json，html
	 */
	public void setReturnContentType(String returnContentType) {
		this.returnContentType = returnContentType;
	}
	/**
	 * 获取：返回内容类型：json，html
	 */
	public String getReturnContentType() {
		return returnContentType;
	}
	/**
	 * 设置：抓取的网站编码：UTF-8，GBK，GB2312
	 */
	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}
	/**
	 * 获取：抓取的网站编码：UTF-8，GBK，GB2312
	 */
	public String getCharsetName() {
		return charsetName;
	}
	/**
	 * 设置：抓取起始页，会替换列表URL中的{page}
	 */
	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}
	/**
	 * 获取：抓取起始页，会替换列表URL中的{page}
	 */
	public Integer getPageStart() {
		return pageStart;
	}
	/**
	 * 设置：抓取结束页，会替换列表URL中的{page}
	 */
	public void setPageEnd(Integer pageEnd) {
		this.pageEnd = pageEnd;
	}
	/**
	 * 获取：抓取结束页，会替换列表URL中的{page}
	 */
	public Integer getPageEnd() {
		return pageEnd;
	}
	/**
	 * 设置：伪造User-Agent
	 */
	public void setSpiderOptionUserAgent(String spiderOptionUserAgent) {
		this.spiderOptionUserAgent = spiderOptionUserAgent;
	}
	/**
	 * 获取：伪造User-Agent
	 */
	public String getSpiderOptionUserAgent() {
		return spiderOptionUserAgent;
	}
	/**
	 * 设置：伪造来源
	 */
	public void setSpiderOptionReferer(String spiderOptionReferer) {
		this.spiderOptionReferer = spiderOptionReferer;
	}
	/**
	 * 获取：伪造来源
	 */
	public String getSpiderOptionReferer() {
		return spiderOptionReferer;
	}
	/**
	 * 设置：伪造Cookie
	 */
	public void setSpiderOptionCookie(String spiderOptionCookie) {
		this.spiderOptionCookie = spiderOptionCookie;
	}
	/**
	 * 获取：伪造Cookie
	 */
	public String getSpiderOptionCookie() {
		return spiderOptionCookie;
	}
	/**
	 * 设置：伪造header，key:value 多个换行
	 */
	public void setSpiderOptionHeader(String spiderOptionHeader) {
		this.spiderOptionHeader = spiderOptionHeader;
	}
	/**
	 * 获取：伪造header，key:value 多个换行
	 */
	public String getSpiderOptionHeader() {
		return spiderOptionHeader;
	}
	/**
	 * 设置：POST数据：如果method=post时有效，key:value 多个换行
	 */
	public void setSpiderOptionPostData(String spiderOptionPostData) {
		this.spiderOptionPostData = spiderOptionPostData;
	}
	/**
	 * 获取：POST数据：如果method=post时有效，key:value 多个换行
	 */
	public String getSpiderOptionPostData() {
		return spiderOptionPostData;
	}
	/**
	 * 设置：connection timeout seconds
	 */
	public void setSpiderOptionTimeout(String spiderOptionTimeout) {
		this.spiderOptionTimeout = spiderOptionTimeout;
	}
	/**
	 * 获取：connection timeout seconds
	 */
	public String getSpiderOptionTimeout() {
		return spiderOptionTimeout;
	}
	/**
	 * 设置：抓取详情页url的选择器语法，兼容CSS or Jquery；若是JSON数据使用.法 
	 */
	public void setDetailUrlSelector(String detailUrlSelector) {
		this.detailUrlSelector = detailUrlSelector;
	}
	/**
	 * 获取：抓取详情页url的选择器语法，兼容CSS or Jquery；若是JSON数据使用.法 
	 */
	public String getDetailUrlSelector() {
		return detailUrlSelector;
	}
	/**
	 * 设置：抓取详情页url，人工合成，大括号中的变量为，json中的值，如：http://www.baidu.com/{id}-{name}
	 */
	public void setDetailUrlArtifact(String detailUrlArtifact) {
		this.detailUrlArtifact = detailUrlArtifact;
	}
	/**
	 * 获取：抓取详情页url，人工合成，大括号中的变量为，json中的值，如：http://www.baidu.com/{id}-{name}
	 */
	public String getDetailUrlArtifact() {
		return detailUrlArtifact;
	}
	/**
	 * 设置：抓取详情页缩略图的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名 
	 */
	public void setPicUrlSelector(String picUrlSelector) {
		this.picUrlSelector = picUrlSelector;
	}
	/**
	 * 获取：抓取详情页缩略图的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名 
	 */
	public String getPicUrlSelector() {
		return picUrlSelector;
	}
	/**
	 * 设置：抓取详情页title的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名
	 */
	public void setTitleSelector(String titleSelector) {
		this.titleSelector = titleSelector;
	}
	/**
	 * 获取：抓取详情页title的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名
	 */
	public String getTitleSelector() {
		return titleSelector;
	}
	/**
	 * 设置：抓取的数据插入的表
	 */
	public void setToTable(String toTable) {
		this.toTable = toTable;
	}
	/**
	 * 获取：抓取的数据插入的表
	 */
	public String getToTable() {
		return toTable;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
