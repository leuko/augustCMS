package io.renren.modules.spider.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Leuko
 * @email leukoli@163.com
 * @date 2018-08-21 17:59:36
 */
@TableName("spider_logs")
public class SpiderLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer spiderProjectId;
	/**
	 * 详情页URL
	 */
	private String detailUrl;
	/**
	 * 抓取内容
	 */
	private String content;
	/**
	 * 抓取成功与否
	 */
	private Integer status;
	/**
	 * 失败重试次数
	 */
	private Integer tryCount;
	/**
	 * 抓取失败原因
	 */
	private String errorMsg;
	/**
	 * 最后一次重试时间
	 */
	private Date lastTryTime;
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
	public void setSpiderProjectId(Integer spiderProjectId) {
		this.spiderProjectId = spiderProjectId;
	}
	/**
	 * 获取：
	 */
	public Integer getSpiderProjectId() {
		return spiderProjectId;
	}
	/**
	 * 设置：详情页URL
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	/**
	 * 获取：详情页URL
	 */
	public String getDetailUrl() {
		return detailUrl;
	}
	/**
	 * 设置：抓取内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：抓取内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：抓取成功与否
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：抓取成功与否
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：失败重试次数
	 */
	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}
	/**
	 * 获取：失败重试次数
	 */
	public Integer getTryCount() {
		return tryCount;
	}
	/**
	 * 设置：抓取失败原因
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * 获取：抓取失败原因
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * 设置：最后一次重试时间
	 */
	public void setLastTryTime(Date lastTryTime) {
		this.lastTryTime = lastTryTime;
	}
	/**
	 * 获取：最后一次重试时间
	 */
	public Date getLastTryTime() {
		return lastTryTime;
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
