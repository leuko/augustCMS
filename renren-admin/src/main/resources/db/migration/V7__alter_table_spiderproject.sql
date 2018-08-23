

ALTER TABLE spider_projects ADD detail_method VARCHAR(32)  NULL COMMENT "详情页的method" AFTER detail_url_artifact,
 ADD detail_post_data VARCHAR(512)  NULL COMMENT "详情页的post data" AFTER detail_url_artifact,
 ADD detail_return_content_type VARCHAR(32)  NULL COMMENT "详情页的返回内容类型：json，html" AFTER detail_url_artifact,
 ADD detail_referer VARCHAR(256)  NULL COMMENT "详情页的伪造来源" AFTER detail_url_artifact