-- 菜单SQL
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
VALUES ('1', '文章类别', 'modules/cms/articlecategory.html', NULL, '1', 'fa fa-file-code-o', '6');

-- 按钮父菜单ID
set @parentId = @@identity;

-- 菜单对应按钮SQL
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '查看', null, 'cms:articlecategory:list,cms:articlecategory:info', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '新增', null, 'cms:articlecategory:save', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '修改', null, 'cms:articlecategory:update', '2', null, '6';
INSERT INTO `sys_menu`(`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  SELECT @parentId, '删除', null, 'cms:articlecategory:delete', '2', null, '6';

-- 创建表
CREATE TABLE `article_categories` (
            id int(10) unsigned NOT NULL AUTO_INCREMENT ,
            category_name varchar(32) NULL ,
            category_remark varchar(128) NULL ,
            updated_at datetime NULL ,
            created_at datetime NULL ,
            PRIMARY KEY (id)
    )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;