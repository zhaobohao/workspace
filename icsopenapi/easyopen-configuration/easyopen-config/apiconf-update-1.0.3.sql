ALTER TABLE `apiconf`.`perm_api_info` ADD COLUMN `module_name` VARCHAR(50) NULL COMMENT '模块名' AFTER `description`;
ALTER TABLE `apiconf`.`perm_api_info` ADD COLUMN `order_index` INT NULL COMMENT '排序字段' AFTER `module_name`;
