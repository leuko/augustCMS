-- 菜单SQL
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
VALUES ('1', '文章管理', 'modules/cms/article.html', NULL, '1', 'fa fa-file-code-o', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '查看', null, 'cms:article:list,cms:article:info', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '新增', null, 'cms:article:save', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '修改', null, 'cms:article:update', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '删除', null, 'cms:article:delete', '2', null, '6';

-- 创建表
CREATE TABLE `articles` (
            id int(10) unsigned NOT NULL AUTO_INCREMENT ,
            article_category_id int NULL DEFAULT 0,
            article_title varchar(128) NULL ,
            article_desc varchar(256) NULL ,
            artical_body TEXT NULL ,
            status tinyint(2) NOT NULL DEFAULT 0 ,
            meta_title varchar(128) NULL ,
            meta_desc varchar(256) NULL ,
            updated_at datetime NULL ,
            created_at datetime NULL ,
            PRIMARY KEY (id)
    )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;