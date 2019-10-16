use sop;

DROP TABLE IF EXISTS `config_service_route`;

CREATE TABLE `config_service_route` (
  `id` varchar(128) NOT NULL DEFAULT '' COMMENT '路由id',
  `service_id` varchar(128) NOT NULL DEFAULT '',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '接口名',
  `version` varchar(64) NOT NULL DEFAULT '' COMMENT '版本号',
  `predicates` varchar(256) DEFAULT NULL COMMENT '路由断言（SpringCloudGateway专用）',
  `filters` varchar(256) DEFAULT NULL COMMENT '路由过滤器（SpringCloudGateway专用）',
  `uri` varchar(128) NOT NULL DEFAULT '' COMMENT '路由规则转发的目标uri',
  `path` varchar(128) NOT NULL DEFAULT '' COMMENT 'uri后面跟的path',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '路由执行的顺序',
  `ignore_validate` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否忽略验证，业务参数验证除外',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态，0：待审核，1：启用，2：禁用',
  `merge_result` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否合并结果',
  `permission` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否需要授权才能访问',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_serviceid` (`service_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='路由配置';
