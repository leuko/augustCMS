

-- 创建表
CREATE TABLE `spider_logs` (
            id INT unsigned NOT NULL AUTO_INCREMENT ,
            spider_project_id INT NOT NULL DEFAULT 0 ,
            detail_url varchar(256) NULL COMMENT "详情页URL" ,
            content TEXT NULL COMMENT "抓取内容" ,
            status tinyint(1) DEFAULT 1 COMMENT "抓取成功与否" ,
            try_count INT NOT NULL DEFAULT 0 COMMENT "失败重试次数" ,
            error_msg TEXT NULL COMMENT "抓取失败原因" ,
            last_try_time TIMESTAMP NULL COMMENT "最后一次重试时间" ,
            create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL ,
            PRIMARY KEY (id),
            KEY (spider_project_id, detail_url)
    )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT "";