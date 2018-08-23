

-- 创建表
CREATE TABLE `spider_project_columns` (
            id INT unsigned NOT NULL AUTO_INCREMENT ,
            spider_project_id INT NOT NULL ,
            table_column varchar(32) NOT NULL COMMENT "对应表字段" ,
            field_selector varchar(256) NULL COMMENT "详情页的字段选择器，兼容CSS or Jquery" ,
            default_value varchar(256) NULL COMMENT "当field_selector没设置，或空时，使用此默认值" ,
            is_pic tinyint(1) NOT NULL DEFAULT 0 COMMENT "是否图片" ,
            is_date tinyint(1) NOT NULL DEFAULT 0 COMMENT "是否日期格式" ,
            date_format varchar(64) NULL COMMENT "如果是日期字段，必须填写格式，如：yyyy-MM-dd HH:mm:ss" ,
            content_whitelist_type varchar(32) NOT NULL DEFAULT "none" COMMENT "过滤内容白名单类型：none,simpleText,basic,basicWithImages,relaxed" ,
            PRIMARY KEY (id),
            KEY (spider_project_id)
    )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT "";