
-- 菜单SQL
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
VALUES ('1', '数据采集', 'modules/spider/spiderproject.html', NULL, '1', 'fa fa-file-code-o', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '查看', null, 'spider:spiderproject:list,spider:spiderproject:info', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '新增', null, 'spider:spiderproject:save', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '修改', null, 'spider:spiderproject:update', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '删除', null, 'spider:spiderproject:delete', '2', null, '6';

-- 创建表
CREATE TABLE `spider_projects` (
            id int(10) unsigned NOT NULL AUTO_INCREMENT ,
            name varchar(64) NULL ,
            list_url varchar(256) NULL COMMENT "列表URL, url中可以有{page}" ,
            method varchar(32) NOT NULL DEFAULT "get" COMMENT "Method： get，post" ,
            return_content_type varchar(32) NOT NULL DEFAULT "html" COMMENT "返回内容类型：json，html" ,
            charset_name varchar(32) NOT NULL DEFAULT "UTF-8" COMMENT "抓取的网站编码：UTF-8，GBK，GB2312" ,
            page_start int DEFAULT 1 COMMENT "抓取起始页，会替换列表URL中的{page}" ,
            page_end int DEFAULT 1 COMMENT "抓取结束页，会替换列表URL中的{page}" ,
            spider_option_user_agent varchar(256) NULL COMMENT "伪造User-Agent" ,
            spider_option_referer varchar(256) NULL COMMENT "伪造来源" ,
            spider_option_cookie varchar(256) NULL COMMENT "伪造Cookie" ,
            spider_option_header varchar(512) NULL COMMENT "伪造header，key:value 多个换行" ,
            spider_option_post_data varchar(512) NULL COMMENT "POST数据：如果method=post时有效，key:value 多个换行" ,
            spider_option_timeout varchar(256) NULL COMMENT "connection timeout seconds" ,
            detail_url_selector varchar(256) NULL COMMENT "抓取详情页url的选择器语法，兼容CSS or Jquery；若是JSON数据使用.法 " ,
            detail_url_artifact varchar(256) NULL COMMENT "抓取详情页url，人工合成，大括号中的变量为，json中的值，如：http://www.baidu.com/{id}-{name}" ,
            pic_url_selector varchar(256) NULL COMMENT "抓取详情页缩略图的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名 " ,
            title_selector varchar(256) NULL COMMENT "抓取详情页title的选择器语法，兼容CSS or Jquery；若是JSON数据，直接使用列表中的属性名" ,
            to_table varchar(32) NULL COMMENT "抓取的数据插入的表" ,
            create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL ,
            PRIMARY KEY (id)
    )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT "数据采集";