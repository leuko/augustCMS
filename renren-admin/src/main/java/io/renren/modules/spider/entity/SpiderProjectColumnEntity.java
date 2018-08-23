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
 * @date 2018-08-22 11:59:04
 */
@TableName("spider_project_columns")
public class SpiderProjectColumnEntity implements Serializable {
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
	 * 对应表字段
	 */
	private String tableColumn;
	/**
	 * 详情页的字段选择器，兼容CSS or Jquery
	 */
	private String fieldSelector;
	/**
	 * 当field_selector没设置，或空时，使用此默认值
	 */
	private String defaultValue;
	/**
	 * 是否图片
	 */
	private Integer isPic;
	/**
	 * 是否日期格式
	 */
	private Integer isDate;
	/**
	 * 如果是日期字段，必须填写格式，如：yyyy-MM-dd HH:mm:ss
	 */
	private String dateFormat;
	/**
	 * 过滤内容白名单类型：none,simpleText,basic,basicWithImages,relaxed
	 */
	private String contentWhitelistType;

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
	 * 设置：对应表字段
	 */
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	/**
	 * 获取：对应表字段
	 */
	public String getTableColumn() {
		return tableColumn;
	}
	/**
	 * 设置：详情页的字段选择器，兼容CSS or Jquery
	 */
	public void setFieldSelector(String fieldSelector) {
		this.fieldSelector = fieldSelector;
	}
	/**
	 * 获取：详情页的字段选择器，兼容CSS or Jquery
	 */
	public String getFieldSelector() {
		return fieldSelector;
	}
	/**
	 * 设置：当field_selector没设置，或空时，使用此默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * 获取：当field_selector没设置，或空时，使用此默认值
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * 设置：是否图片
	 */
	public void setIsPic(Integer isPic) {
		this.isPic = isPic;
	}
	/**
	 * 获取：是否图片
	 */
	public Integer getIsPic() {
		return isPic;
	}
	/**
	 * 设置：是否日期格式
	 */
	public void setIsDate(Integer isDate) {
		this.isDate = isDate;
	}
	/**
	 * 获取：是否日期格式
	 */
	public Integer getIsDate() {
		return isDate;
	}
	/**
	 * 设置：如果是日期字段，必须填写格式，如：yyyy-MM-dd HH:mm:ss
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	/**
	 * 获取：如果是日期字段，必须填写格式，如：yyyy-MM-dd HH:mm:ss
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * 设置：过滤内容白名单类型：none,simpleText,basic,basicWithImages,relaxed
	 */
	public void setContentWhitelistType(String contentWhitelistType) {
		this.contentWhitelistType = contentWhitelistType;
	}
	/**
	 * 获取：过滤内容白名单类型：none,simpleText,basic,basicWithImages,relaxed
	 */
	public String getContentWhitelistType() {
		return contentWhitelistType;
	}

	@Override
	public String toString() {
		return "SpiderProjectColumnEntity{" +
				"id=" + id +
				", spiderProjectId=" + spiderProjectId +
				", tableColumn='" + tableColumn + '\'' +
				", fieldSelector='" + fieldSelector + '\'' +
				", defaultValue='" + defaultValue + '\'' +
				", isPic=" + isPic +
				", isDate=" + isDate +
				", dateFormat='" + dateFormat + '\'' +
				", contentWhitelistType='" + contentWhitelistType + '\'' +
				'}';
	}
}
