ALTER TABLE `sop`.`config_service_route` ADD COLUMN `need_token` TINYINT NOT NULL DEFAULT 0 COMMENT '是否需要token' AFTER `permission`;
