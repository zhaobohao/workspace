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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `admin_user` */

insert  into `admin_user`(`id`,`username`,`password`,`gmt_create`,`gmt_update`) values (1,'admin','14e1b600b1fd579f47433b88e8d85291','2018-07-13 15:20:05','2018-07-13 15:20:07');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='全局配置表';

/*Data for the table `global_config` */

insert  into `global_config`(`id`,`key_name`,`field_name`,`field_value`,`remark`,`gmt_create`,`gmt_update`) values (1,'limit','default_limit_type','LIMIT','默认漏桶策略（不可删除）','2018-07-12 19:50:58','2018-07-13 15:33:48'),(2,'limit','default_limit_count','50','默认每秒可处理请求数（不可删除）','2018-07-12 19:53:34','2018-07-13 15:33:51'),(3,'limit','default_token_bucket_count','50','默认令牌桶个数（不可删除）','2018-07-12 19:54:11','2018-07-13 15:33:53');

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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='限流配置';

/*Data for the table `limit_app_config` */

insert  into `limit_app_config`(`id`,`api_id`,`app`,`name`,`version`,`limit_type`,`limit_count`,`limit_code`,`limit_msg`,`token_bucket_count`,`status`,`gmt_create`,`gmt_update`) values (3,548,'app1','manager.session.get','','TOKEN_BUCKET',50,NULL,NULL,50,1,'2018-07-13 09:51:36','2018-07-13 11:29:53'),(4,549,'app1','goods.add','','LIMIT',50,'sdfgsdfg','asfasdfsdf',50,1,'2018-07-13 09:52:09','2018-07-16 09:48:04'),(17,550,'app1','doc.param.6','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:17:04','2018-07-16 09:48:09'),(18,551,'app1','param.type.3','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:17:04','2018-07-13 11:31:33'),(19,552,'app1','doc.param.5','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:17:04','2018-07-13 11:34:16'),(20,553,'app1','param.type.4','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:03','2018-07-16 09:48:36'),(21,554,'app1','doc.param.4','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:34:33'),(22,555,'app1','doc.param.3','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(23,556,'app1','doc.param.2','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(24,557,'app1','doc.param.1','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(25,558,'app1','wrapResult.false','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(26,559,'app1','session.set','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(27,560,'app1','doc.result.7','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(28,561,'app1','doc.result.4','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(29,562,'app1','doc.result.3','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(30,563,'app1','goods.pageinfo','2.0','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(31,564,'app1','doc.result.6','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(32,565,'app1','doc.result.5','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(33,566,'app1','doc.result.2','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(34,567,'app1','doc.result.1','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),(35,568,'app1','goods.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(36,569,'app1','hello','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(37,570,'app1','user.goods.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(38,571,'app1','userjwt.goods.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(39,572,'app1','file.upload2','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(40,573,'app1','goods.list','2.0','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(41,574,'app1','goods.pageinfo','1.0','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(42,575,'app1','file.upload3','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(43,576,'app1','file.upload','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(44,577,'app1','session.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(45,578,'app1','param.type.1','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(46,579,'app1','param.type.2','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),(47,580,'app1','userlock.test','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53');

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
) ENGINE=InnoDB AUTO_INCREMENT=627 DEFAULT CHARSET=utf8 COMMENT='接口信息表';

/*Data for the table `perm_api_info` */

insert  into `perm_api_info`(`id`,`name`,`version`,`description`,`module_name`,`order_index`,`app`,`status`,`gmt_create`,`gmt_update`) values (548,'manager.session.get','','',NULL,0,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:15'),(549,'goods.add','','添加商品','商品模块',1,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(550,'doc.param.6','','参数方式6，数组参数','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(551,'param.type.3','','参数类型，Map接收','参数类型demo',2147483647,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(552,'doc.param.5','','参数方式5，外部类指定参数，可复用，第二个','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(553,'param.type.4','','参数类型，String接收','参数类型demo',2147483647,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(554,'doc.param.4','','参数方式4,自定义属性，第一个','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(555,'doc.param.3','','参数方式3,聚合','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(556,'doc.param.2','','参数方式2,继承','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(557,'doc.param.1','','参数方式1,默认','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(558,'wrapResult.false','','',NULL,0,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(559,'session.set','','',NULL,0,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(560,'doc.result.7','','返回结果7,模板复用','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(561,'doc.result.4','','返回结果4,返回List','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(562,'doc.result.3','','返回结果3,自定义字段','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(563,'goods.pageinfo','2.0','获取商品列表','商品模块',1,'app1',1,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(564,'doc.result.6','','返回结果6,自定义类','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(565,'doc.result.5','','返回结果5,外部指定','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(566,'doc.result.2','','返回结果2,指定返回结果类','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(567,'doc.result.1','','返回结果1,默认','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(568,'goods.get','','获取商品','商品模块',1,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(569,'hello','','',NULL,0,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(570,'user.goods.get','','获取用户商品(accessToken)','商品模块',1,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(571,'userjwt.goods.get','','获取用户商品(jwt)','商品模块',1,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(572,'file.upload2','','',NULL,0,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(573,'goods.list','2.0','获取商品列表','商品模块',1,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(574,'goods.pageinfo','1.0','获取商品列表','商品模块',1,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(575,'file.upload3','','文件上传,不确定数量','文件上传',2147483647,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(576,'file.upload','','文件上传','文件上传',2147483647,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(577,'session.get','','',NULL,0,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(578,'param.type.1','','参数类型，自定义类','参数类型demo',2147483647,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(579,'param.type.2','','参数类型，JSONObject','参数类型demo',2147483647,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(580,'userlock.test','','',NULL,0,'app1',0,'2018-07-09 09:43:59','2018-09-20 11:21:16'),(581,'goods.get','',NULL,NULL,NULL,'app2',0,'2018-07-13 18:43:46','2018-07-13 18:43:46'),(582,'goods.list','2.0',NULL,NULL,NULL,'app2',0,'2018-07-13 18:43:46','2018-07-13 18:43:46'),(583,'goods.get','',NULL,NULL,NULL,'app-normal',0,'2018-08-02 12:50:16','2018-08-02 12:50:16'),(584,'goods.list','2.0',NULL,NULL,NULL,'app-normal',0,'2018-08-02 12:50:16','2018-08-02 12:50:16'),(585,'goods.get','',NULL,NULL,NULL,'app-WebFlux',0,'2018-08-02 12:50:46','2018-08-02 12:50:46'),(586,'goods.list','2.0',NULL,NULL,NULL,'app-WebFlux',0,'2018-08-02 12:50:46','2018-08-02 12:50:46'),(587,'manager.session.get','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(588,'goods.add','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(589,'doc.param.6','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(590,'param.type.3','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(591,'doc.param.5','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(592,'param.type.4','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(593,'doc.param.4','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(594,'doc.param.3','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(595,'doc.param.2','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(596,'doc.param.1','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(597,'wrapResult.false','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(598,'session.set','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(599,'doc.result.7','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(600,'doc.result.4','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(601,'doc.result.3','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(602,'goods.pageinfo','2.0',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(603,'doc.result.6','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(604,'doc.result.5','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(605,'doc.result.2','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(606,'doc.result.1','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(607,'goods.get','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(608,'hello','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(609,'user.goods.get','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(610,'userjwt.goods.get','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(611,'file.upload2','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(612,'goods.list','2.0',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(613,'goods.pageinfo','1.0',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(614,'file.upload3','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(615,'file.upload','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(616,'session.get','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(617,'param.type.1','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(618,'param.type.2','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(619,'userlock.test','',NULL,NULL,NULL,'app-manual',0,'2018-08-06 11:09:57','2018-08-06 11:09:57'),(620,'doc.result.8','','返回结果8,最外部包装类','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-08-16 18:28:54','2018-09-20 11:21:16'),(621,'doc.param.7','','参数方式7，数组对象参数','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-08-22 14:33:33','2018-09-20 11:21:16'),(622,'doc.result.9','','返回结果9','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-08-30 17:57:29','2018-09-20 11:21:16'),(623,'param.type.5','','参数类型，int接收','参数类型demo',2147483647,'app1',0,'2018-09-04 21:16:06','2018-09-20 11:21:16'),(624,'download.test','','',NULL,0,'app1',0,'2018-09-13 17:37:52','2018-09-20 11:21:16'),(625,'doc.result.0','','返回结果0,没有返回结果','文档demo，参考DocDemoApi.java',2,'app1',0,'2018-09-13 17:37:52','2018-09-20 11:21:16'),(626,'download.test2','','',NULL,0,'app1',0,'2018-09-13 17:37:52','2018-09-20 11:21:16');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='app信息表';

/*Data for the table `perm_client` */

insert  into `perm_client`(`id`,`app_key`,`secret`,`pub_key`,`pri_key`,`app`,`status`,`gmt_create`,`gmt_update`) values (1,'admin','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-13 15:30:17'),(2,'test1','a739329eaaa84f6aab3608148cff8823','','','app1',1,'2018-01-22 21:03:17','2018-07-12 15:41:30'),(3,'aaaa','32d1fe6d5cf14502bc10487b7b73dd19','','','app1',0,'2018-01-22 21:03:17','2018-07-12 15:30:06'),(4,'bbbb','43d8e21b277d42bcb537bb8ae7b51fb9',NULL,NULL,'app1',0,'2018-01-22 21:03:17','2018-07-12 15:35:21'),(5,'cccc','123456',NULL,NULL,'app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),(7,'ddd','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),(8,'eeee','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),(9,'ffff','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),(10,'ggggg','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),(11,'hhhhhh','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),(12,'iiiii','123456','','','app1',0,'2018-01-22 21:03:17','2018-07-09 16:32:57'),(13,'adm','123','','','app1',0,'2018-07-09 14:00:40','2018-07-09 16:32:40'),(14,'123','123','','','app1',0,'2018-07-09 15:53:54','2018-07-09 16:32:30'),(15,'466216391167442944','b6f6dc51428140e99cadd46b7eb08f27','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCD6D5+jFULCht95HTv2SquU+4S32J5vc3bKX5qfHwhEY7MXKSt3xmrdUXTpm2RRPnm38XFE/CXTu0ZV3SYKVEp/Glq+XQPwH/vZiHr6vYYykmIGbktrxb/YWnaTQfGJPilYq2i0W8d2hjOARgweyWgHseZ3yBIeD8WGn1nleiEnQIDAQAB','MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIPoPn6MVQsKG33kdO/ZKq5T7hLfYnm9zdspfmp8fCERjsxcpK3fGat1RdOmbZFE+ebfxcUT8JdO7RlXdJgpUSn8aWr5dA/Af+9mIevq9hjKSYgZuS2vFv9hadpNB8Yk+KViraLRbx3aGM4BGDB7JaAex5nfIEh4PxYafWeV6ISdAgMBAAECgYAJrdwQGyoOYlJ8HWx7QRtBqYT5u4yVVFkrsNLG65WMM6af1lh7LbjLfRJ/omG93CreRyNS/9CSucbqKiGzHK0I5W8Qhxff8FRHz1wrKNAMdD5BnGPuATVxewo09ImfOhysij2PnhvhRp8KdzGAi0IYMsgI3ArQVO5KjWl0ACp5oQJBANQAKVZsdsr1Fp403FMCCaagnuSlRhUP8voksXFdEiz8zc2EWxzs9W7J7U9DJ5kU8qb8R/dTqICcoeED8PAYijUCQQCfSKBUs6WC6z7Lp0we/ueSPcJ43lSrdtr6GtbWvRTC6ksoPXaRdEvz1dHvvpWJ7f+PhiKu6Pp6V8t9dBCejB3JAkEAnklQk/p/56F+tbj6F/AZjJO4jzbnUKqL3ECxS9cVzwkiECSWcs5pocKasOePFTkSw6LxiLSpxv0r0HnE1z87UQJAZlVptKFqT69Fu9+y9yL7sif9nlvw9+w3iYHezz3RbdzLBoJef9rAxVp1M1s1hdUjYJlkBQ3nhtyK/wGnf8UziQJBAKkHCGZvVIhqy/yLN4KrwmFFBh6jBcqBD8vvK7kUhu5VQCYrX7AHf8ELMBgG4jHgAANAxYR53Xwhy9MrTj+hnlU=','app1',0,'2018-07-10 12:09:22','2018-07-10 12:09:22'),(16,'466217419711447040','6d45d78745164aad91eb11498f1e0ce5','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAW1v5dLsw7oH+WCUOinPBu5c3T71yLy4ZbOE+PyR00mKSd5ZCEsxsM8X7bqKEArbUBX3UpwTu1JAKMQR3EhwhUToKAN7ZUr+6rLwsNU4pBVXdU1WSQrt+wxL4ISOPG3zuYPmKWhfgMxUmKy3aHcl2qjylU8z2pAG5tyX5yo3PSwIDAQAB','MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIBbW/l0uzDugf5YJQ6Kc8G7lzdPvXIvLhls4T4/JHTSYpJ3lkISzGwzxftuooQCttQFfdSnBO7UkAoxBHcSHCFROgoA3tlSv7qsvCw1TikFVd1TVZJCu37DEvghI48bfO5g+YpaF+AzFSYrLdodyXaqPKVTzPakAbm3JfnKjc9LAgMBAAECgYBk12HK+gqWxssaJ+X1JpELA3Yp3lvX+XkeQIw1eyYJIcCPkaRbBKnV2FcS0jibvgYlTKsvBoi5mgYP+7KmWXP/F+v4Q632livWpFIKhDhozQpGhEmSJx1elpJjrQ3uNmFBc/vXtiBlvta7cpyuX3G5obvJ01c4KBt9+q4EuKVcAQJBANcnjSpI7R00X3MFuUbW/lIc9f6aJQkh93BrSb577EKi5pSkU/3DZViCP1fGLAmGIB6/cStsgcekEozu2xt8LLsCQQCYuXerRzG3riL7JChcEwZlpK37E3WTGUL3uLvpfQT+UtTwJRq/A6zRC4gcoehUszksPN4qcKDG8rRCqGfWy4axAkBYzBIO6Zoy+3KJXkthWUoNSbadJbEzW7lrxFvjKfIQbnsLzeZ8TTB+nwLVjwyNwbVv+0MR0O6fHrApSktTxKQRAkBos+5I7Fw3cxjkWFubFCf0kzv7v9GBcXYp4uphHqbHvP3jTWzSu7BE+MTt7Eodl2mA3JHrQp/XBRl0AYR/C7VhAkB0X5afIS/e+qg72xztvDJf1pqd+wum8fx3u1CIpNAJ7sLDYg0pxvmQE9YUb0jWQMJA2rmUemOuQ11xhWv5dPo7','app1',0,'2018-07-10 12:13:19','2018-07-10 12:13:19'),(17,'466218931707707392','c30ddc5a02944f89bf3601c8fd4b9a6f','','','app1',0,'2018-07-10 12:21:34','2018-07-10 12:21:41'),(18,'test','123456',NULL,NULL,'app1',0,'2018-07-13 16:54:05','2018-07-13 16:58:38');

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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='客户端角色';

/*Data for the table `perm_client_role` */

insert  into `perm_client_role`(`id`,`client_id`,`role_code`,`gmt_create`,`gmt_update`) values (10,14,'normal','2018-07-09 16:32:30','2018-07-09 16:32:30'),(11,13,'normal','2018-07-09 16:32:40','2018-07-09 16:32:40'),(12,12,'normal','2018-07-09 16:32:57','2018-07-09 16:32:57'),(24,15,'normal','2018-07-10 12:09:22','2018-07-10 12:09:22'),(25,16,'normal','2018-07-10 12:13:19','2018-07-10 12:13:19'),(29,17,'normal','2018-07-10 12:21:41','2018-07-10 12:21:41'),(35,3,'normal','2018-07-12 15:30:06','2018-07-12 15:30:06'),(37,4,'normal','2018-07-12 15:35:21','2018-07-12 15:35:21'),(38,2,'normal','2018-07-12 15:41:30','2018-07-12 15:41:30'),(39,1,'pay','2018-07-13 15:30:17','2018-07-13 15:30:17'),(43,18,'normal','2018-07-13 16:58:38','2018-07-13 16:58:38');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `perm_role` */

insert  into `perm_role`(`id`,`role_code`,`description`,`gmt_create`,`gmt_update`) values (1,'normal','普通ISV','2018-01-23 09:21:59','2018-07-13 15:30:23'),(2,'pay','付费ISV','2018-07-09 15:04:26','2018-07-09 15:04:27');

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
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

/*Data for the table `perm_role_permission` */

insert  into `perm_role_permission`(`id`,`role_code`,`api_id`,`app`,`gmt_create`,`gmt_update`) values (132,'normal',581,'app2','2018-07-24 15:30:50','2018-07-24 15:30:50'),(134,'normal',554,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(135,'normal',552,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(136,'normal',550,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(137,'normal',567,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(138,'normal',566,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(139,'normal',562,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(140,'normal',561,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(141,'normal',565,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(142,'normal',564,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(143,'normal',560,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(144,'normal',576,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(145,'normal',572,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(146,'normal',575,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(147,'normal',549,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(148,'normal',568,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(149,'normal',573,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(150,'normal',574,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(151,'normal',563,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(152,'normal',569,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(153,'normal',548,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(154,'normal',578,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(155,'normal',579,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(156,'normal',577,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(157,'normal',559,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(158,'normal',570,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(159,'normal',571,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(160,'normal',580,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),(161,'normal',558,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50');


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
