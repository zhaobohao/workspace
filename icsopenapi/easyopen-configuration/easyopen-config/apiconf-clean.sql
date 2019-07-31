/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.5.19 : Database - apiconf
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`apiconf` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `apiconf`;

/*Table structure for table `admin_user` */

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `global_config` */

DROP TABLE IF EXISTS `global_config`;

CREATE TABLE `global_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `key_name` varchar(64) NOT NULL,
  `field_name` varchar(64) NOT NULL,
  `field_value` varchar(100) NOT NULL,
  `remark` varchar(100) DEFAULT NULL COMMENT '描述',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='全局配置表';

/*Table structure for table `limit_app_config` */

DROP TABLE IF EXISTS `limit_app_config`;

CREATE TABLE `limit_app_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `api_id` bigint(20) NOT NULL COMMENT 'perm_api_info.id',
  `app` varchar(100) NOT NULL COMMENT 'app',
  `name` varchar(64) NOT NULL COMMENT '接口名',
  `version` varchar(64) NOT NULL COMMENT '版本号',
  `limit_type` varchar(20) NOT NULL COMMENT '限流策略',
  `limit_count` int(11) DEFAULT NULL COMMENT '每秒可处理请求数',
  `limit_code` varchar(20) DEFAULT NULL COMMENT '返回的错误码',
  `limit_msg` varchar(100) DEFAULT NULL COMMENT '返回的错误信息',
  `token_bucket_count` int(11) DEFAULT NULL COMMENT '令牌桶容量',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:开启，0关闭',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gmt_update` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_apiid` (`api_id`),
  KEY `idx_app` (`app`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限流配置';

/*Table structure for table `perm_api_info` */

DROP TABLE IF EXISTS `perm_api_info`;

CREATE TABLE `perm_api_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '0' COMMENT '接口名',
  `version` varchar(50) NOT NULL COMMENT '版本号',
  `description` varchar(200) DEFAULT NULL COMMENT '接口描述',
  `module_name` varchar(50) DEFAULT NULL COMMENT '模块名',
  `order_index` int(11) DEFAULT NULL COMMENT '排序字段',
  `app` varchar(50) NOT NULL COMMENT '所属app',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：使用中，1：未使用',
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_app` (`app`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口信息表';

/*Table structure for table `perm_client` */

DROP TABLE IF EXISTS `perm_client`;

CREATE TABLE `perm_client` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `app_key` varchar(100) NOT NULL COMMENT 'appKey',
  `secret` varchar(200) NOT NULL COMMENT 'secret',
  `pub_key` text COMMENT '公钥',
  `pri_key` text COMMENT '私钥',
  `app` varchar(50) NOT NULL COMMENT 'app名称',
  `status` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '0启用，1禁用',
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_key_app` (`app_key`,`app`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app信息表';

/*Table structure for table `perm_client_role` */

DROP TABLE IF EXISTS `perm_client_role`;

CREATE TABLE `perm_client_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) NOT NULL COMMENT '客户端id',
  `role_code` varchar(50) NOT NULL COMMENT '角色code',
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`client_id`,`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端角色';

/*Table structure for table `perm_role` */

DROP TABLE IF EXISTS `perm_role`;

CREATE TABLE `perm_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) NOT NULL COMMENT '角色代码',
  `description` varchar(50) NOT NULL COMMENT '角色描述',
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Table structure for table `perm_role_permission` */

DROP TABLE IF EXISTS `perm_role_permission`;

CREATE TABLE `perm_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) NOT NULL COMMENT '角色表code',
  `api_id` bigint(20) NOT NULL COMMENT 'api_id',
  `app` varchar(50) NOT NULL COMMENT 'app名称',
  `gmt_create` datetime NOT NULL,
  `gmt_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_perm` (`app`,`role_code`,`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

insert  into `admin_user`(`id`,`username`,`password`,`gmt_create`,`gmt_update`) values (2,'admin','14e1b600b1fd579f47433b88e8d85291','2018-07-13 17:26:53','2018-07-13 17:26:55');
insert  into `global_config`(`id`,`key_name`,`field_name`,`field_value`,`remark`,`gmt_create`,`gmt_update`) values (1,'limit','default_limit_type','LIMIT','默认限流策略（不可删除）','2018-07-12 19:50:58','2018-07-13 15:33:48'),(2,'limit','default_limit_count','50','默认每秒可处理请求数（不可删除）','2018-07-12 19:53:34','2018-07-13 15:33:51'),(3,'limit','default_token_bucket_count','50','默认令牌桶个数（不可删除）','2018-07-12 19:54:11','2018-07-13 15:33:53');


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


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
