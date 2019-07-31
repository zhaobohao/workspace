/**
  1.0.1版本升级文件
 */
CREATE TABLE `app_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `app` varchar(64) NOT NULL COMMENT 'app名称',
  `doc_url` varchar(100) NOT NULL COMMENT '文档页面url',
  `doc_password` varchar(100) DEFAULT NULL COMMENT '文档页面密码',
  `doc_status` tinyint(4) NOT NULL COMMENT '文档状态，1:开启，0：关闭',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app` (`app`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用信息表';


CREATE TABLE `app_connected_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `app` varchar(64) NOT NULL COMMENT 'app名称',
  `remote_address` varchar(100) NOT NULL COMMENT '远程连接',
  `connected_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '连接时间',
  `disconnected_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失去连接时间',
  `gmt_create` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `gmt_update` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_remoteaddr` (`remote_address`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='已连接到配置中心的app';