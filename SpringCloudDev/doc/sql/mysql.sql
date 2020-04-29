/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : mk

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 20/04/2020 18:44:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for db_db_instance
-- ----------------------------
DROP TABLE IF EXISTS `db_db_instance`;
CREATE TABLE `db_db_instance`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''数据库名称'',
  `data_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''data用户账号'',
  `etl_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''etl用户账号'',
  `opr_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''opr用户账号'',
  `rpt_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''rpt用户账号'',
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `is_deleted` int(2) NOT NULL COMMENT ''是否已删除'',
  `status` int(2) NULL DEFAULT NULL COMMENT ''状态'',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idex_id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_db_instance
-- ----------------------------
INSERT INTO `db_db_instance` VALUES (1, ''网商贷oracle业务数据库'', ''DistrlData'', ''DistrlEtl'', ''DistrlOpr'', ''DistrlRpt'', 1, ''2019-12-17 15:48:12'', 1, ''2019-12-17 15:56:08'', 0, 1);
INSERT INTO `db_db_instance` VALUES (2, ''马上金融oracle业务数据库'', ''DistrlMaData'', ''DistrlMaEtl'', ''DistrlMaOpr'', ''DistrlMaRpt'', 1, ''2019-12-17 15:52:52'', 1, ''2019-12-17 15:52:52'', 0, 1);
INSERT INTO `db_db_instance` VALUES (3, ''232'', ''2323'', ''323'', ''23'', ''2323'', 1, ''2019-12-17 15:57:53'', 1, ''2019-12-17 15:57:53'', 1, 1);
INSERT INTO `db_db_instance` VALUES (4, ''234'', ''4234234'', ''234'', ''23'', ''234'', 1, ''2019-12-17 15:58:02'', 1, ''2019-12-17 15:58:02'', 1, 1);
INSERT INTO `db_db_instance` VALUES (5, ''3333333333333333'', ''234234'', ''423'', ''234'', ''4234'', 1, ''2019-12-17 15:58:14'', 1, ''2019-12-17 15:58:19'', 1, 1);

-- ----------------------------
-- Table structure for db_table_info
-- ----------------------------
DROP TABLE IF EXISTS `db_table_info`;
CREATE TABLE `db_table_info`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''名称'',
  `type_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''字段类型'',
  `type_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''字段类型长度'',
  `is_empty` int(2) NULL DEFAULT NULL COMMENT ''是否为空 1为空2不为空'',
  `default_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''默认值 '',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''注释说明'',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT ''父级菜单'',
  `category` int(2) NULL DEFAULT NULL COMMENT ''1表，2字段'',
  `db_instance_id` int(11) NULL DEFAULT NULL COMMENT ''数据库id'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  `is_leaf` int(2) UNSIGNED NULL DEFAULT 0 COMMENT ''是否是叶子节点，0是，1不是'',
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_db_table_info_id`(`id`) USING BTREE,
  INDEX `idx_db_table_info_dbid`(`db_instance_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_table_info
-- ----------------------------
INSERT INTO `db_table_info` VALUES (12, ''batch_bank_info'', ''VARCHAR2'', ''2'', 1, '''', ''银行信息表'', 0, 1, 1, 0, 0, 1, ''2019-12-18 14:52:25'', 1, ''2019-12-18 15:00:49'', 1);
INSERT INTO `db_table_info` VALUES (13, ''seria_no'', ''NUMBER'', ''19'', 1, NULL, ''表主键'', 12, 2, 1, 0, 0, 1, ''2019-12-18 14:53:33'', 1, ''2019-12-18 14:53:33'', 1);
INSERT INTO `db_table_info` VALUES (14, ''third_apply_info'', ''VARCHAR2'', ''64'', 2, ''abc'', ''第三方申请信息'', 12, 2, 1, 0, 0, 1, ''2019-12-18 14:54:21'', 1, ''2019-12-18 14:54:21'', 1);
INSERT INTO `db_table_info` VALUES (15, ''bank_no'', ''NUMBER'', ''10'', 1, ''3'', ''银行编号'', 12, 2, 1, 0, 0, 1, ''2019-12-18 14:54:43'', 1, ''2019-12-18 15:18:08'', 1);
INSERT INTO `db_table_info` VALUES (18, ''bank_no_create_date'', ''DATE'', ''10'', 1, ''sysDate'', ''银行编号'', 12, 2, 1, 0, 0, 1, ''2019-12-18 15:18:11'', 1, ''2019-12-18 15:21:38'', 1);

-- ----------------------------
-- Table structure for interface_info
-- ----------------------------
DROP TABLE IF EXISTS `interface_info`;
CREATE TABLE `interface_info`  (
  `api_id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''ID'',
  `api_method` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''HttpMethod：GET、PUT、POST'',
  `api_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''拦截路径'',
  `api_status` int(2) NOT NULL COMMENT ''状态：0草稿，1发布，2有变更，3禁用'',
  `api_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT ''注释'',
  `api_type` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''脚本类型：SQL、DataQL'',
  `api_script` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''查询脚本：xxxxxxx'',
  `api_schema` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ''接口的请求/响应数据结构'',
  `api_sample` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ''请求/响应/请求头样本数据'',
  `api_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT ''创建时间'',
  `api_gmt_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT ''修改时间'',
  PRIMARY KEY (`api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = ''Dataway 中的API'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interface_info
-- ----------------------------
INSERT INTO `interface_info` VALUES (2, ''GET'', ''/api/getclient'', 1, '''', ''SQL'', ''select * from mk_client'', ''{}'', ''{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}'', ''2020-04-15 10:46:29'', ''2020-04-15 10:47:28'');

-- ----------------------------
-- Table structure for interface_release
-- ----------------------------
DROP TABLE IF EXISTS `interface_release`;
CREATE TABLE `interface_release`  (
  `pub_id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''Publish ID'',
  `pub_api_id` int(11) NOT NULL COMMENT ''所属API ID'',
  `pub_method` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''HttpMethod：GET、PUT、POST'',
  `pub_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''拦截路径'',
  `pub_status` int(2) NOT NULL COMMENT ''状态：0有效，1无效（可能被下线）'',
  `pub_type` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''脚本类型：SQL、DataQL'',
  `pub_script` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''查询脚本：xxxxxxx'',
  `pub_script_ori` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT ''原始查询脚本，仅当类型为SQL时不同'',
  `pub_schema` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ''接口的请求/响应数据结构'',
  `pub_sample` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ''请求/响应/请求头样本数据'',
  `pub_release_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT ''发布时间（下线不更新）'',
  PRIMARY KEY (`pub_id`) USING BTREE,
  INDEX `idx_interface_release`(`pub_api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = ''Dataway API 发布历史。'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interface_release
-- ----------------------------
INSERT INTO `interface_release` VALUES (1, 1, ''POST'', ''/api/getClient'', 1, ''SQL'', ''var tempCall = @@sql(`message`)<%\n\n\n\nselect * from mk_client%>;\nreturn tempCall(${message});'', ''\n\n\n\nselect * from mk_client'', ''{}'', ''{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}'', ''2020-04-15 10:43:54'');
INSERT INTO `interface_release` VALUES (2, 1, ''POST'', ''/api/getClient'', 1, ''SQL'', ''var tempCall = @@sql(`message`)<%\n\n\n\nselect * from mk_client%>;\nreturn tempCall(${message});'', ''\n\n\n\nselect * from mk_client'', ''{}'', ''{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}'', ''2020-04-15 10:44:49'');
INSERT INTO `interface_release` VALUES (3, 2, ''GET'', ''/api/getclient'', 0, ''DataQL'', ''\n\n\nselect * from mk_client'', ''\n\n\nselect * from mk_client'', ''{}'', ''{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}'', ''2020-04-15 10:46:31'');
INSERT INTO `interface_release` VALUES (4, 2, ''GET'', ''/api/getclient'', 0, ''SQL'', ''var tempCall = @@sql(`message`)<%select * from mk_client%>;\nreturn tempCall(${message});'', ''select * from mk_client'', ''{}'', ''{\"requestBody\":\"{\\\"message\\\":\\\"Hello DataQL.\\\"}\",\"headerData\":[]}'', ''2020-04-15 10:47:28'');

-- ----------------------------
-- Table structure for mk_area
-- ----------------------------
DROP TABLE IF EXISTS `mk_area`;
CREATE TABLE `mk_area`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `area_code` int(100) NOT NULL COMMENT ''区域编码'',
  `parent_id` bigint(32) NOT NULL COMMENT ''父级编号'',
  `sort` decimal(10, 0) NOT NULL COMMENT ''本级排序号（升序）'',
  `is_leaf` int(1) NOT NULL DEFAULT 0 COMMENT ''是否叶子节点'',
  `area_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''区域名称'',
  `area_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''区域类型'',
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NULL DEFAULT NULL COMMENT ''状态'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  `remarks` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''备注信息'',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_area_pc`(`parent_id`) USING BTREE,
  INDEX `idx_sys_area_ts`(`sort`) USING BTREE,
  INDEX `idx_sys_area_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = ''行政区划'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_area
-- ----------------------------
INSERT INTO `mk_area` VALUES (1, 110000, 0, 110000, 1, ''北京市'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2, 110100, 110000, 110100, 1, ''北京城区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3, 110101, 110100, 110101, 0, ''东城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (4, 110102, 110100, 110102, 0, ''西城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (5, 110105, 110100, 110105, 0, ''朝阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (6, 110106, 110100, 110106, 0, ''丰台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (7, 110107, 110100, 110107, 0, ''石景山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (8, 110108, 110100, 110108, 0, ''海淀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (9, 110109, 110100, 110109, 0, ''门头沟区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (10, 110111, 110100, 110111, 0, ''房山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (11, 110112, 110100, 110112, 0, ''通州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (12, 110113, 110100, 110113, 0, ''顺义区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (13, 110114, 110100, 110114, 0, ''昌平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (14, 110115, 110100, 110115, 0, ''大兴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (15, 110116, 110100, 110116, 0, ''怀柔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (16, 110117, 110100, 110117, 0, ''平谷区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (17, 110118, 110100, 110118, 0, ''密云区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (18, 110119, 110100, 110119, 0, ''延庆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (19, 120000, 0, 120000, 1, ''天津市'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (20, 120100, 120000, 120100, 1, ''天津城区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (21, 120101, 120100, 120101, 0, ''和平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (22, 120102, 120100, 120102, 0, ''河东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (23, 120103, 120100, 120103, 0, ''河西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (24, 120104, 120100, 120104, 0, ''南开区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (25, 120105, 120100, 120105, 0, ''河北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (26, 120106, 120100, 120106, 0, ''红桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (27, 120110, 120100, 120110, 0, ''东丽区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (28, 120111, 120100, 120111, 0, ''西青区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (29, 120112, 120100, 120112, 0, ''津南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (30, 120113, 120100, 120113, 0, ''北辰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (31, 120114, 120100, 120114, 0, ''武清区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (32, 120115, 120100, 120115, 0, ''宝坻区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (33, 120116, 120100, 120116, 0, ''滨海新区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (34, 120117, 120100, 120117, 0, ''宁河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (35, 120118, 120100, 120118, 0, ''静海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (36, 120119, 120100, 120119, 0, ''蓟州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (37, 130000, 0, 130000, 1, ''河北省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (38, 130100, 130000, 130100, 1, ''石家庄市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (39, 130102, 130100, 130102, 0, ''长安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (40, 130104, 130100, 130104, 0, ''桥西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (41, 130105, 130100, 130105, 0, ''新华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (42, 130107, 130100, 130107, 0, ''井陉矿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (43, 130108, 130100, 130108, 0, ''裕华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (44, 130109, 130100, 130109, 0, ''藁城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (45, 130110, 130100, 130110, 0, ''鹿泉区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (46, 130111, 130100, 130111, 0, ''栾城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (47, 130121, 130100, 130121, 0, ''井陉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (48, 130123, 130100, 130123, 0, ''正定县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (49, 130125, 130100, 130125, 0, ''行唐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (50, 130126, 130100, 130126, 0, ''灵寿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (51, 130127, 130100, 130127, 0, ''高邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (52, 130128, 130100, 130128, 0, ''深泽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (53, 130129, 130100, 130129, 0, ''赞皇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (54, 130130, 130100, 130130, 0, ''无极县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (55, 130131, 130100, 130131, 0, ''平山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (56, 130132, 130100, 130132, 0, ''元氏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (57, 130133, 130100, 130133, 0, ''赵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (58, 130181, 130100, 130181, 0, ''辛集市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (59, 130183, 130100, 130183, 0, ''晋州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (60, 130184, 130100, 130184, 0, ''新乐市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (61, 130200, 130000, 130200, 1, ''唐山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (62, 130202, 130200, 130202, 0, ''路南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (63, 130203, 130200, 130203, 0, ''路北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (64, 130204, 130200, 130204, 0, ''古冶区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (65, 130205, 130200, 130205, 0, ''开平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (66, 130207, 130200, 130207, 0, ''丰南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (67, 130208, 130200, 130208, 0, ''丰润区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (68, 130209, 130200, 130209, 0, ''曹妃甸区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (69, 130223, 130200, 130223, 0, ''滦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (70, 130224, 130200, 130224, 0, ''滦南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (71, 130225, 130200, 130225, 0, ''乐亭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (72, 130227, 130200, 130227, 0, ''迁西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (73, 130229, 130200, 130229, 0, ''玉田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (74, 130281, 130200, 130281, 0, ''遵化市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (75, 130283, 130200, 130283, 0, ''迁安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (76, 130300, 130000, 130300, 1, ''秦皇岛市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (77, 130302, 130300, 130302, 0, ''海港区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (78, 130303, 130300, 130303, 0, ''山海关区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (79, 130304, 130300, 130304, 0, ''北戴河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (80, 130306, 130300, 130306, 0, ''抚宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (81, 130321, 130300, 130321, 0, ''青龙满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (82, 130322, 130300, 130322, 0, ''昌黎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (83, 130324, 130300, 130324, 0, ''卢龙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (84, 130400, 130000, 130400, 1, ''邯郸市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (85, 130402, 130400, 130402, 0, ''邯山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (86, 130403, 130400, 130403, 0, ''丛台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (87, 130404, 130400, 130404, 0, ''复兴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (88, 130406, 130400, 130406, 0, ''峰峰矿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (89, 130407, 130400, 130407, 0, ''肥乡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (90, 130408, 130400, 130408, 0, ''永年区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (91, 130423, 130400, 130423, 0, ''临漳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (92, 130424, 130400, 130424, 0, ''成安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (93, 130425, 130400, 130425, 0, ''大名县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (94, 130426, 130400, 130426, 0, ''涉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (95, 130427, 130400, 130427, 0, ''磁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (96, 130430, 130400, 130430, 0, ''邱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (97, 130431, 130400, 130431, 0, ''鸡泽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (98, 130432, 130400, 130432, 0, ''广平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (99, 130433, 130400, 130433, 0, ''馆陶县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (100, 130434, 130400, 130434, 0, ''魏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (101, 130435, 130400, 130435, 0, ''曲周县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (102, 130481, 130400, 130481, 0, ''武安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (103, 130500, 130000, 130500, 1, ''邢台市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (104, 130502, 130500, 130502, 0, ''桥东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (105, 130503, 130500, 130503, 0, ''桥西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (106, 130521, 130500, 130521, 0, ''邢台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (107, 130522, 130500, 130522, 0, ''临城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (108, 130523, 130500, 130523, 0, ''内丘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (109, 130524, 130500, 130524, 0, ''柏乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (110, 130525, 130500, 130525, 0, ''隆尧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (111, 130526, 130500, 130526, 0, ''任县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (112, 130527, 130500, 130527, 0, ''南和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (113, 130528, 130500, 130528, 0, ''宁晋县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (114, 130529, 130500, 130529, 0, ''巨鹿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (115, 130530, 130500, 130530, 0, ''新河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (116, 130531, 130500, 130531, 0, ''广宗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (117, 130532, 130500, 130532, 0, ''平乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (118, 130533, 130500, 130533, 0, ''威县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (119, 130534, 130500, 130534, 0, ''清河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (120, 130535, 130500, 130535, 0, ''临西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (121, 130581, 130500, 130581, 0, ''南宫市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (122, 130582, 130500, 130582, 0, ''沙河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (123, 130600, 130000, 130600, 1, ''保定市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (124, 130602, 130600, 130602, 0, ''竞秀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (125, 130606, 130600, 130606, 0, ''莲池区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (126, 130607, 130600, 130607, 0, ''满城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (127, 130608, 130600, 130608, 0, ''清苑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (128, 130609, 130600, 130609, 0, ''徐水区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (129, 130623, 130600, 130623, 0, ''涞水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (130, 130624, 130600, 130624, 0, ''阜平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (131, 130626, 130600, 130626, 0, ''定兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (132, 130627, 130600, 130627, 0, ''唐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (133, 130628, 130600, 130628, 0, ''高阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (134, 130629, 130600, 130629, 0, ''容城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (135, 130630, 130600, 130630, 0, ''涞源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (136, 130631, 130600, 130631, 0, ''望都县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (137, 130632, 130600, 130632, 0, ''安新县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (138, 130633, 130600, 130633, 0, ''易县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (139, 130634, 130600, 130634, 0, ''曲阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (140, 130635, 130600, 130635, 0, ''蠡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (141, 130636, 130600, 130636, 0, ''顺平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (142, 130637, 130600, 130637, 0, ''博野县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (143, 130638, 130600, 130638, 0, ''雄县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (144, 130681, 130600, 130681, 0, ''涿州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (145, 130682, 130600, 130682, 0, ''定州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (146, 130683, 130600, 130683, 0, ''安国市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (147, 130684, 130600, 130684, 0, ''高碑店市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (148, 130700, 130000, 130700, 1, ''张家口市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (149, 130702, 130700, 130702, 0, ''桥东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (150, 130703, 130700, 130703, 0, ''桥西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (151, 130705, 130700, 130705, 0, ''宣化区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (152, 130706, 130700, 130706, 0, ''下花园区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (153, 130708, 130700, 130708, 0, ''万全区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (154, 130709, 130700, 130709, 0, ''崇礼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (155, 130722, 130700, 130722, 0, ''张北县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (156, 130723, 130700, 130723, 0, ''康保县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (157, 130724, 130700, 130724, 0, ''沽源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (158, 130725, 130700, 130725, 0, ''尚义县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (159, 130726, 130700, 130726, 0, ''蔚县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (160, 130727, 130700, 130727, 0, ''阳原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (161, 130728, 130700, 130728, 0, ''怀安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (162, 130730, 130700, 130730, 0, ''怀来县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (163, 130731, 130700, 130731, 0, ''涿鹿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (164, 130732, 130700, 130732, 0, ''赤城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (165, 130800, 130000, 130800, 1, ''承德市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (166, 130802, 130800, 130802, 0, ''双桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (167, 130803, 130800, 130803, 0, ''双滦区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (168, 130804, 130800, 130804, 0, ''鹰手营子矿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (169, 130821, 130800, 130821, 0, ''承德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (170, 130822, 130800, 130822, 0, ''兴隆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (171, 130824, 130800, 130824, 0, ''滦平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (172, 130825, 130800, 130825, 0, ''隆化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (173, 130826, 130800, 130826, 0, ''丰宁满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (174, 130827, 130800, 130827, 0, ''宽城满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (175, 130828, 130800, 130828, 0, ''围场满族蒙古族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (176, 130881, 130800, 130881, 0, ''平泉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (177, 130900, 130000, 130900, 1, ''沧州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (178, 130902, 130900, 130902, 0, ''新华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (179, 130903, 130900, 130903, 0, ''运河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (180, 130921, 130900, 130921, 0, ''沧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (181, 130922, 130900, 130922, 0, ''青县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (182, 130923, 130900, 130923, 0, ''东光县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (183, 130924, 130900, 130924, 0, ''海兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (184, 130925, 130900, 130925, 0, ''盐山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (185, 130926, 130900, 130926, 0, ''肃宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (186, 130927, 130900, 130927, 0, ''南皮县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (187, 130928, 130900, 130928, 0, ''吴桥县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (188, 130929, 130900, 130929, 0, ''献县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (189, 130930, 130900, 130930, 0, ''孟村回族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (190, 130981, 130900, 130981, 0, ''泊头市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (191, 130982, 130900, 130982, 0, ''任丘市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (192, 130983, 130900, 130983, 0, ''黄骅市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (193, 130984, 130900, 130984, 0, ''河间市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (194, 131000, 130000, 131000, 1, ''廊坊市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (195, 131002, 131000, 131002, 0, ''安次区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (196, 131003, 131000, 131003, 0, ''广阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (197, 131022, 131000, 131022, 0, ''固安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (198, 131023, 131000, 131023, 0, ''永清县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (199, 131024, 131000, 131024, 0, ''香河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (200, 131025, 131000, 131025, 0, ''大城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (201, 131026, 131000, 131026, 0, ''文安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (202, 131028, 131000, 131028, 0, ''大厂回族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (203, 131081, 131000, 131081, 0, ''霸州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (204, 131082, 131000, 131082, 0, ''三河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (205, 131100, 130000, 131100, 1, ''衡水市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (206, 131102, 131100, 131102, 0, ''桃城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (207, 131103, 131100, 131103, 0, ''冀州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (208, 131121, 131100, 131121, 0, ''枣强县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (209, 131122, 131100, 131122, 0, ''武邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (210, 131123, 131100, 131123, 0, ''武强县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (211, 131124, 131100, 131124, 0, ''饶阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (212, 131125, 131100, 131125, 0, ''安平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (213, 131126, 131100, 131126, 0, ''故城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (214, 131127, 131100, 131127, 0, ''景县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (215, 131128, 131100, 131128, 0, ''阜城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (216, 131182, 131100, 131182, 0, ''深州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (217, 140000, 0, 140000, 1, ''山西省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (218, 140100, 140000, 140100, 1, ''太原市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (219, 140105, 140100, 140105, 0, ''小店区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (220, 140106, 140100, 140106, 0, ''迎泽区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (221, 140107, 140100, 140107, 0, ''杏花岭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (222, 140108, 140100, 140108, 0, ''尖草坪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (223, 140109, 140100, 140109, 0, ''万柏林区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (224, 140110, 140100, 140110, 0, ''晋源区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (225, 140121, 140100, 140121, 0, ''清徐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (226, 140122, 140100, 140122, 0, ''阳曲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (227, 140123, 140100, 140123, 0, ''娄烦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (228, 140181, 140100, 140181, 0, ''古交市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (229, 140200, 140000, 140200, 1, ''大同市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (230, 140202, 140200, 140202, 0, ''城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (231, 140203, 140200, 140203, 0, ''矿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (232, 140211, 140200, 140211, 0, ''南郊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (233, 140212, 140200, 140212, 0, ''新荣区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (234, 140221, 140200, 140221, 0, ''阳高县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (235, 140222, 140200, 140222, 0, ''天镇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (236, 140223, 140200, 140223, 0, ''广灵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (237, 140224, 140200, 140224, 0, ''灵丘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (238, 140225, 140200, 140225, 0, ''浑源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (239, 140226, 140200, 140226, 0, ''左云县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (240, 140227, 140200, 140227, 0, ''大同县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (241, 140300, 140000, 140300, 1, ''阳泉市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (242, 140302, 140300, 140302, 0, ''城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (243, 140303, 140300, 140303, 0, ''矿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (244, 140311, 140300, 140311, 0, ''郊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (245, 140321, 140300, 140321, 0, ''平定县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (246, 140322, 140300, 140322, 0, ''盂县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (247, 140400, 140000, 140400, 1, ''长治市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (248, 140402, 140400, 140402, 0, ''城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (249, 140411, 140400, 140411, 0, ''郊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (250, 140421, 140400, 140421, 0, ''长治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (251, 140423, 140400, 140423, 0, ''襄垣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (252, 140424, 140400, 140424, 0, ''屯留县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (253, 140425, 140400, 140425, 0, ''平顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (254, 140426, 140400, 140426, 0, ''黎城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (255, 140427, 140400, 140427, 0, ''壶关县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (256, 140428, 140400, 140428, 0, ''长子县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (257, 140429, 140400, 140429, 0, ''武乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (258, 140430, 140400, 140430, 0, ''沁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (259, 140431, 140400, 140431, 0, ''沁源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (260, 140481, 140400, 140481, 0, ''潞城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (261, 140500, 140000, 140500, 1, ''晋城市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (262, 140502, 140500, 140502, 0, ''城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (263, 140521, 140500, 140521, 0, ''沁水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (264, 140522, 140500, 140522, 0, ''阳城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (265, 140524, 140500, 140524, 0, ''陵川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (266, 140525, 140500, 140525, 0, ''泽州县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (267, 140581, 140500, 140581, 0, ''高平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (268, 140600, 140000, 140600, 1, ''朔州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (269, 140602, 140600, 140602, 0, ''朔城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (270, 140603, 140600, 140603, 0, ''平鲁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (271, 140621, 140600, 140621, 0, ''山阴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (272, 140622, 140600, 140622, 0, ''应县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (273, 140623, 140600, 140623, 0, ''右玉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (274, 140624, 140600, 140624, 0, ''怀仁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (275, 140700, 140000, 140700, 1, ''晋中市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (276, 140702, 140700, 140702, 0, ''榆次区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (277, 140721, 140700, 140721, 0, ''榆社县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (278, 140722, 140700, 140722, 0, ''左权县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (279, 140723, 140700, 140723, 0, ''和顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (280, 140724, 140700, 140724, 0, ''昔阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (281, 140725, 140700, 140725, 0, ''寿阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (282, 140726, 140700, 140726, 0, ''太谷县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (283, 140727, 140700, 140727, 0, ''祁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (284, 140728, 140700, 140728, 0, ''平遥县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (285, 140729, 140700, 140729, 0, ''灵石县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (286, 140781, 140700, 140781, 0, ''介休市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (287, 140800, 140000, 140800, 1, ''运城市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (288, 140802, 140800, 140802, 0, ''盐湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (289, 140821, 140800, 140821, 0, ''临猗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (290, 140822, 140800, 140822, 0, ''万荣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (291, 140823, 140800, 140823, 0, ''闻喜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (292, 140824, 140800, 140824, 0, ''稷山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (293, 140825, 140800, 140825, 0, ''新绛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (294, 140826, 140800, 140826, 0, ''绛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (295, 140827, 140800, 140827, 0, ''垣曲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (296, 140828, 140800, 140828, 0, ''夏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (297, 140829, 140800, 140829, 0, ''平陆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (298, 140830, 140800, 140830, 0, ''芮城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (299, 140881, 140800, 140881, 0, ''永济市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (300, 140882, 140800, 140882, 0, ''河津市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (301, 140900, 140000, 140900, 1, ''忻州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (302, 140902, 140900, 140902, 0, ''忻府区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (303, 140921, 140900, 140921, 0, ''定襄县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (304, 140922, 140900, 140922, 0, ''五台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (305, 140923, 140900, 140923, 0, ''代县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (306, 140924, 140900, 140924, 0, ''繁峙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (307, 140925, 140900, 140925, 0, ''宁武县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (308, 140926, 140900, 140926, 0, ''静乐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (309, 140927, 140900, 140927, 0, ''神池县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (310, 140928, 140900, 140928, 0, ''五寨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (311, 140929, 140900, 140929, 0, ''岢岚县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (312, 140930, 140900, 140930, 0, ''河曲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (313, 140931, 140900, 140931, 0, ''保德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (314, 140932, 140900, 140932, 0, ''偏关县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (315, 140981, 140900, 140981, 0, ''原平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (316, 141000, 140000, 141000, 1, ''临汾市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (317, 141002, 141000, 141002, 0, ''尧都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (318, 141021, 141000, 141021, 0, ''曲沃县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (319, 141022, 141000, 141022, 0, ''翼城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (320, 141023, 141000, 141023, 0, ''襄汾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (321, 141024, 141000, 141024, 0, ''洪洞县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (322, 141025, 141000, 141025, 0, ''古县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (323, 141026, 141000, 141026, 0, ''安泽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (324, 141027, 141000, 141027, 0, ''浮山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (325, 141028, 141000, 141028, 0, ''吉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (326, 141029, 141000, 141029, 0, ''乡宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (327, 141030, 141000, 141030, 0, ''大宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (328, 141031, 141000, 141031, 0, ''隰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (329, 141032, 141000, 141032, 0, ''永和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (330, 141033, 141000, 141033, 0, ''蒲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (331, 141034, 141000, 141034, 0, ''汾西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (332, 141081, 141000, 141081, 0, ''侯马市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (333, 141082, 141000, 141082, 0, ''霍州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (334, 141100, 140000, 141100, 1, ''吕梁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (335, 141102, 141100, 141102, 0, ''离石区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (336, 141121, 141100, 141121, 0, ''文水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (337, 141122, 141100, 141122, 0, ''交城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (338, 141123, 141100, 141123, 0, ''兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (339, 141124, 141100, 141124, 0, ''临县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (340, 141125, 141100, 141125, 0, ''柳林县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (341, 141126, 141100, 141126, 0, ''石楼县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (342, 141127, 141100, 141127, 0, ''岚县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (343, 141128, 141100, 141128, 0, ''方山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (344, 141129, 141100, 141129, 0, ''中阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (345, 141130, 141100, 141130, 0, ''交口县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (346, 141181, 141100, 141181, 0, ''孝义市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (347, 141182, 141100, 141182, 0, ''汾阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (348, 150000, 0, 150000, 1, ''内蒙古自治区'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (349, 150100, 150000, 150100, 1, ''呼和浩特市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (350, 150102, 150100, 150102, 0, ''新城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (351, 150103, 150100, 150103, 0, ''回民区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (352, 150104, 150100, 150104, 0, ''玉泉区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (353, 150105, 150100, 150105, 0, ''赛罕区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (354, 150121, 150100, 150121, 0, ''土默特左旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (355, 150122, 150100, 150122, 0, ''托克托县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (356, 150123, 150100, 150123, 0, ''和林格尔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (357, 150124, 150100, 150124, 0, ''清水河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (358, 150125, 150100, 150125, 0, ''武川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (359, 150200, 150000, 150200, 1, ''包头市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (360, 150202, 150200, 150202, 0, ''东河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (361, 150203, 150200, 150203, 0, ''昆都仑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (362, 150204, 150200, 150204, 0, ''青山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (363, 150205, 150200, 150205, 0, ''石拐区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (364, 150206, 150200, 150206, 0, ''白云鄂博矿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (365, 150207, 150200, 150207, 0, ''九原区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (366, 150221, 150200, 150221, 0, ''土默特右旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (367, 150222, 150200, 150222, 0, ''固阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (368, 150223, 150200, 150223, 0, ''达尔罕茂明安联合旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (369, 150300, 150000, 150300, 1, ''乌海市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (370, 150302, 150300, 150302, 0, ''海勃湾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (371, 150303, 150300, 150303, 0, ''海南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (372, 150304, 150300, 150304, 0, ''乌达区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (373, 150400, 150000, 150400, 1, ''赤峰市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (374, 150402, 150400, 150402, 0, ''红山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (375, 150403, 150400, 150403, 0, ''元宝山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (376, 150404, 150400, 150404, 0, ''松山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (377, 150421, 150400, 150421, 0, ''阿鲁科尔沁旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (378, 150422, 150400, 150422, 0, ''巴林左旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (379, 150423, 150400, 150423, 0, ''巴林右旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (380, 150424, 150400, 150424, 0, ''林西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (381, 150425, 150400, 150425, 0, ''克什克腾旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (382, 150426, 150400, 150426, 0, ''翁牛特旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (383, 150428, 150400, 150428, 0, ''喀喇沁旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (384, 150429, 150400, 150429, 0, ''宁城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (385, 150430, 150400, 150430, 0, ''敖汉旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (386, 150500, 150000, 150500, 1, ''通辽市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (387, 150502, 150500, 150502, 0, ''科尔沁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (388, 150521, 150500, 150521, 0, ''科尔沁左翼中旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (389, 150522, 150500, 150522, 0, ''科尔沁左翼后旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (390, 150523, 150500, 150523, 0, ''开鲁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (391, 150524, 150500, 150524, 0, ''库伦旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (392, 150525, 150500, 150525, 0, ''奈曼旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (393, 150526, 150500, 150526, 0, ''扎鲁特旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (394, 150581, 150500, 150581, 0, ''霍林郭勒市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (395, 150600, 150000, 150600, 1, ''鄂尔多斯市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (396, 150602, 150600, 150602, 0, ''东胜区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (397, 150603, 150600, 150603, 0, ''康巴什区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (398, 150621, 150600, 150621, 0, ''达拉特旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (399, 150622, 150600, 150622, 0, ''准格尔旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (400, 150623, 150600, 150623, 0, ''鄂托克前旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (401, 150624, 150600, 150624, 0, ''鄂托克旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (402, 150625, 150600, 150625, 0, ''杭锦旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (403, 150626, 150600, 150626, 0, ''乌审旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (404, 150627, 150600, 150627, 0, ''伊金霍洛旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (405, 150700, 150000, 150700, 1, ''呼伦贝尔市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (406, 150702, 150700, 150702, 0, ''海拉尔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (407, 150703, 150700, 150703, 0, ''扎赉诺尔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (408, 150721, 150700, 150721, 0, ''阿荣旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (409, 150722, 150700, 150722, 0, ''莫力达瓦达斡尔族自治旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (410, 150723, 150700, 150723, 0, ''鄂伦春自治旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (411, 150724, 150700, 150724, 0, ''鄂温克族自治旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (412, 150725, 150700, 150725, 0, ''陈巴尔虎旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (413, 150726, 150700, 150726, 0, ''新巴尔虎左旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (414, 150727, 150700, 150727, 0, ''新巴尔虎右旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (415, 150781, 150700, 150781, 0, ''满洲里市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (416, 150782, 150700, 150782, 0, ''牙克石市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (417, 150783, 150700, 150783, 0, ''扎兰屯市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (418, 150784, 150700, 150784, 0, ''额尔古纳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (419, 150785, 150700, 150785, 0, ''根河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (420, 150800, 150000, 150800, 1, ''巴彦淖尔市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (421, 150802, 150800, 150802, 0, ''临河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (422, 150821, 150800, 150821, 0, ''五原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (423, 150822, 150800, 150822, 0, ''磴口县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (424, 150823, 150800, 150823, 0, ''乌拉特前旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (425, 150824, 150800, 150824, 0, ''乌拉特中旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (426, 150825, 150800, 150825, 0, ''乌拉特后旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (427, 150826, 150800, 150826, 0, ''杭锦后旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (428, 150900, 150000, 150900, 1, ''乌兰察布市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (429, 150902, 150900, 150902, 0, ''集宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (430, 150921, 150900, 150921, 0, ''卓资县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (431, 150922, 150900, 150922, 0, ''化德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (432, 150923, 150900, 150923, 0, ''商都县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (433, 150924, 150900, 150924, 0, ''兴和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (434, 150925, 150900, 150925, 0, ''凉城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (435, 150926, 150900, 150926, 0, ''察哈尔右翼前旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (436, 150927, 150900, 150927, 0, ''察哈尔右翼中旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (437, 150928, 150900, 150928, 0, ''察哈尔右翼后旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (438, 150929, 150900, 150929, 0, ''四子王旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (439, 150981, 150900, 150981, 0, ''丰镇市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (440, 152200, 150000, 152200, 1, ''兴安盟'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (441, 152201, 152200, 152201, 0, ''乌兰浩特市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (442, 152202, 152200, 152202, 0, ''阿尔山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (443, 152221, 152200, 152221, 0, ''科尔沁右翼前旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (444, 152222, 152200, 152222, 0, ''科尔沁右翼中旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (445, 152223, 152200, 152223, 0, ''扎赉特旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (446, 152224, 152200, 152224, 0, ''突泉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (447, 152500, 150000, 152500, 1, ''锡林郭勒盟'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (448, 152501, 152500, 152501, 0, ''二连浩特市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (449, 152502, 152500, 152502, 0, ''锡林浩特市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (450, 152522, 152500, 152522, 0, ''阿巴嘎旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (451, 152523, 152500, 152523, 0, ''苏尼特左旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (452, 152524, 152500, 152524, 0, ''苏尼特右旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (453, 152525, 152500, 152525, 0, ''东乌珠穆沁旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (454, 152526, 152500, 152526, 0, ''西乌珠穆沁旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (455, 152527, 152500, 152527, 0, ''太仆寺旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (456, 152528, 152500, 152528, 0, ''镶黄旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (457, 152529, 152500, 152529, 0, ''正镶白旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (458, 152530, 152500, 152530, 0, ''正蓝旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (459, 152531, 152500, 152531, 0, ''多伦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (460, 152900, 150000, 152900, 1, ''阿拉善盟'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (461, 152921, 152900, 152921, 0, ''阿拉善左旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (462, 152922, 152900, 152922, 0, ''阿拉善右旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (463, 152923, 152900, 152923, 0, ''额济纳旗'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (464, 210000, 0, 210000, 1, ''辽宁省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (465, 210100, 210000, 210100, 1, ''沈阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (466, 210102, 210100, 210102, 0, ''和平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (467, 210103, 210100, 210103, 0, ''沈河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (468, 210104, 210100, 210104, 0, ''大东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (469, 210105, 210100, 210105, 0, ''皇姑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (470, 210106, 210100, 210106, 0, ''铁西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (471, 210111, 210100, 210111, 0, ''苏家屯区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (472, 210112, 210100, 210112, 0, ''浑南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (473, 210113, 210100, 210113, 0, ''沈北新区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (474, 210114, 210100, 210114, 0, ''于洪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (475, 210115, 210100, 210115, 0, ''辽中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (476, 210123, 210100, 210123, 0, ''康平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (477, 210124, 210100, 210124, 0, ''法库县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (478, 210181, 210100, 210181, 0, ''新民市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (479, 210200, 210000, 210200, 1, ''大连市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (480, 210202, 210200, 210202, 0, ''中山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (481, 210203, 210200, 210203, 0, ''西岗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (482, 210204, 210200, 210204, 0, ''沙河口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (483, 210211, 210200, 210211, 0, ''甘井子区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (484, 210212, 210200, 210212, 0, ''旅顺口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (485, 210213, 210200, 210213, 0, ''金州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (486, 210214, 210200, 210214, 0, ''普兰店区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (487, 210224, 210200, 210224, 0, ''长海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (488, 210281, 210200, 210281, 0, ''瓦房店市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (489, 210283, 210200, 210283, 0, ''庄河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (490, 210300, 210000, 210300, 1, ''鞍山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (491, 210302, 210300, 210302, 0, ''铁东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (492, 210303, 210300, 210303, 0, ''铁西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (493, 210304, 210300, 210304, 0, ''立山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (494, 210311, 210300, 210311, 0, ''千山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (495, 210321, 210300, 210321, 0, ''台安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (496, 210323, 210300, 210323, 0, ''岫岩满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (497, 210381, 210300, 210381, 0, ''海城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (498, 210400, 210000, 210400, 1, ''抚顺市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (499, 210402, 210400, 210402, 0, ''新抚区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (500, 210403, 210400, 210403, 0, ''东洲区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (501, 210404, 210400, 210404, 0, ''望花区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (502, 210411, 210400, 210411, 0, ''顺城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (503, 210421, 210400, 210421, 0, ''抚顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (504, 210422, 210400, 210422, 0, ''新宾满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (505, 210423, 210400, 210423, 0, ''清原满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (506, 210500, 210000, 210500, 1, ''本溪市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (507, 210502, 210500, 210502, 0, ''平山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (508, 210503, 210500, 210503, 0, ''溪湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (509, 210504, 210500, 210504, 0, ''明山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (510, 210505, 210500, 210505, 0, ''南芬区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (511, 210521, 210500, 210521, 0, ''本溪满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (512, 210522, 210500, 210522, 0, ''桓仁满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (513, 210600, 210000, 210600, 1, ''丹东市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (514, 210602, 210600, 210602, 0, ''元宝区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (515, 210603, 210600, 210603, 0, ''振兴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (516, 210604, 210600, 210604, 0, ''振安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (517, 210624, 210600, 210624, 0, ''宽甸满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (518, 210681, 210600, 210681, 0, ''东港市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (519, 210682, 210600, 210682, 0, ''凤城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (520, 210700, 210000, 210700, 1, ''锦州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (521, 210702, 210700, 210702, 0, ''古塔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (522, 210703, 210700, 210703, 0, ''凌河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (523, 210711, 210700, 210711, 0, ''太和区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (524, 210726, 210700, 210726, 0, ''黑山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (525, 210727, 210700, 210727, 0, ''义县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (526, 210781, 210700, 210781, 0, ''凌海市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (527, 210782, 210700, 210782, 0, ''北镇市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (528, 210800, 210000, 210800, 1, ''营口市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (529, 210802, 210800, 210802, 0, ''站前区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (530, 210803, 210800, 210803, 0, ''西市区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (531, 210804, 210800, 210804, 0, ''鲅鱼圈区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (532, 210811, 210800, 210811, 0, ''老边区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (533, 210881, 210800, 210881, 0, ''盖州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (534, 210882, 210800, 210882, 0, ''大石桥市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (535, 210900, 210000, 210900, 1, ''阜新市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (536, 210902, 210900, 210902, 0, ''海州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (537, 210903, 210900, 210903, 0, ''新邱区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (538, 210904, 210900, 210904, 0, ''太平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (539, 210905, 210900, 210905, 0, ''清河门区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (540, 210911, 210900, 210911, 0, ''细河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (541, 210921, 210900, 210921, 0, ''阜新蒙古族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (542, 210922, 210900, 210922, 0, ''彰武县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (543, 211000, 210000, 211000, 1, ''辽阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (544, 211002, 211000, 211002, 0, ''白塔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (545, 211003, 211000, 211003, 0, ''文圣区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (546, 211004, 211000, 211004, 0, ''宏伟区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (547, 211005, 211000, 211005, 0, ''弓长岭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (548, 211011, 211000, 211011, 0, ''太子河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (549, 211021, 211000, 211021, 0, ''辽阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (550, 211081, 211000, 211081, 0, ''灯塔市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (551, 211100, 210000, 211100, 1, ''盘锦市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (552, 211102, 211100, 211102, 0, ''双台子区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (553, 211103, 211100, 211103, 0, ''兴隆台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (554, 211104, 211100, 211104, 0, ''大洼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (555, 211122, 211100, 211122, 0, ''盘山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (556, 211200, 210000, 211200, 1, ''铁岭市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (557, 211202, 211200, 211202, 0, ''银州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (558, 211204, 211200, 211204, 0, ''清河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (559, 211221, 211200, 211221, 0, ''铁岭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (560, 211223, 211200, 211223, 0, ''西丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (561, 211224, 211200, 211224, 0, ''昌图县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (562, 211281, 211200, 211281, 0, ''调兵山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (563, 211282, 211200, 211282, 0, ''开原市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (564, 211300, 210000, 211300, 1, ''朝阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (565, 211302, 211300, 211302, 0, ''双塔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (566, 211303, 211300, 211303, 0, ''龙城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (567, 211321, 211300, 211321, 0, ''朝阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (568, 211322, 211300, 211322, 0, ''建平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (569, 211324, 211300, 211324, 0, ''喀喇沁左翼蒙古族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (570, 211381, 211300, 211381, 0, ''北票市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (571, 211382, 211300, 211382, 0, ''凌源市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (572, 211400, 210000, 211400, 1, ''葫芦岛市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (573, 211402, 211400, 211402, 0, ''连山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (574, 211403, 211400, 211403, 0, ''龙港区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (575, 211404, 211400, 211404, 0, ''南票区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (576, 211421, 211400, 211421, 0, ''绥中县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (577, 211422, 211400, 211422, 0, ''建昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (578, 211481, 211400, 211481, 0, ''兴城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (579, 220000, 0, 220000, 1, ''吉林省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (580, 220100, 220000, 220100, 1, ''长春市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (581, 220102, 220100, 220102, 0, ''南关区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (582, 220103, 220100, 220103, 0, ''宽城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (583, 220104, 220100, 220104, 0, ''朝阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (584, 220105, 220100, 220105, 0, ''二道区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (585, 220106, 220100, 220106, 0, ''绿园区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (586, 220112, 220100, 220112, 0, ''双阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (587, 220113, 220100, 220113, 0, ''九台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (588, 220122, 220100, 220122, 0, ''农安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (589, 220182, 220100, 220182, 0, ''榆树市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (590, 220183, 220100, 220183, 0, ''德惠市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (591, 220200, 220000, 220200, 1, ''吉林市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (592, 220202, 220200, 220202, 0, ''昌邑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (593, 220203, 220200, 220203, 0, ''龙潭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (594, 220204, 220200, 220204, 0, ''船营区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (595, 220211, 220200, 220211, 0, ''丰满区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (596, 220221, 220200, 220221, 0, ''永吉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (597, 220281, 220200, 220281, 0, ''蛟河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (598, 220282, 220200, 220282, 0, ''桦甸市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (599, 220283, 220200, 220283, 0, ''舒兰市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (600, 220284, 220200, 220284, 0, ''磐石市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (601, 220300, 220000, 220300, 1, ''四平市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (602, 220302, 220300, 220302, 0, ''铁西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (603, 220303, 220300, 220303, 0, ''铁东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (604, 220322, 220300, 220322, 0, ''梨树县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (605, 220323, 220300, 220323, 0, ''伊通满族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (606, 220381, 220300, 220381, 0, ''公主岭市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (607, 220382, 220300, 220382, 0, ''双辽市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (608, 220400, 220000, 220400, 1, ''辽源市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (609, 220402, 220400, 220402, 0, ''龙山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (610, 220403, 220400, 220403, 0, ''西安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (611, 220421, 220400, 220421, 0, ''东丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (612, 220422, 220400, 220422, 0, ''东辽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (613, 220500, 220000, 220500, 1, ''通化市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (614, 220502, 220500, 220502, 0, ''东昌区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (615, 220503, 220500, 220503, 0, ''二道江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (616, 220521, 220500, 220521, 0, ''通化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (617, 220523, 220500, 220523, 0, ''辉南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (618, 220524, 220500, 220524, 0, ''柳河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (619, 220581, 220500, 220581, 0, ''梅河口市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (620, 220582, 220500, 220582, 0, ''集安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (621, 220600, 220000, 220600, 1, ''白山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (622, 220602, 220600, 220602, 0, ''浑江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (623, 220605, 220600, 220605, 0, ''江源区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (624, 220621, 220600, 220621, 0, ''抚松县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (625, 220622, 220600, 220622, 0, ''靖宇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (626, 220623, 220600, 220623, 0, ''长白朝鲜族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (627, 220681, 220600, 220681, 0, ''临江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (628, 220700, 220000, 220700, 1, ''松原市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (629, 220702, 220700, 220702, 0, ''宁江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (630, 220721, 220700, 220721, 0, ''前郭尔罗斯蒙古族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (631, 220722, 220700, 220722, 0, ''长岭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (632, 220723, 220700, 220723, 0, ''乾安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (633, 220781, 220700, 220781, 0, ''扶余市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (634, 220800, 220000, 220800, 1, ''白城市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (635, 220802, 220800, 220802, 0, ''洮北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (636, 220821, 220800, 220821, 0, ''镇赉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (637, 220822, 220800, 220822, 0, ''通榆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (638, 220881, 220800, 220881, 0, ''洮南市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (639, 220882, 220800, 220882, 0, ''大安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (640, 222400, 220000, 222400, 1, ''延边朝鲜族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (641, 222401, 222400, 222401, 0, ''延吉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (642, 222402, 222400, 222402, 0, ''图们市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (643, 222403, 222400, 222403, 0, ''敦化市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (644, 222404, 222400, 222404, 0, ''珲春市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (645, 222405, 222400, 222405, 0, ''龙井市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (646, 222406, 222400, 222406, 0, ''和龙市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (647, 222424, 222400, 222424, 0, ''汪清县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (648, 222426, 222400, 222426, 0, ''安图县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (649, 230000, 0, 230000, 1, ''黑龙江省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (650, 230100, 230000, 230100, 1, ''哈尔滨市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (651, 230102, 230100, 230102, 0, ''道里区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (652, 230103, 230100, 230103, 0, ''南岗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (653, 230104, 230100, 230104, 0, ''道外区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (654, 230108, 230100, 230108, 0, ''平房区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (655, 230109, 230100, 230109, 0, ''松北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (656, 230110, 230100, 230110, 0, ''香坊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (657, 230111, 230100, 230111, 0, ''呼兰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (658, 230112, 230100, 230112, 0, ''阿城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (659, 230113, 230100, 230113, 0, ''双城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (660, 230123, 230100, 230123, 0, ''依兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (661, 230124, 230100, 230124, 0, ''方正县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (662, 230125, 230100, 230125, 0, ''宾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (663, 230126, 230100, 230126, 0, ''巴彦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (664, 230127, 230100, 230127, 0, ''木兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (665, 230128, 230100, 230128, 0, ''通河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (666, 230129, 230100, 230129, 0, ''延寿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (667, 230183, 230100, 230183, 0, ''尚志市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (668, 230184, 230100, 230184, 0, ''五常市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (669, 230200, 230000, 230200, 1, ''齐齐哈尔市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (670, 230202, 230200, 230202, 0, ''龙沙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (671, 230203, 230200, 230203, 0, ''建华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (672, 230204, 230200, 230204, 0, ''铁锋区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (673, 230205, 230200, 230205, 0, ''昂昂溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (674, 230206, 230200, 230206, 0, ''富拉尔基区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (675, 230207, 230200, 230207, 0, ''碾子山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (676, 230208, 230200, 230208, 0, ''梅里斯达斡尔族区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (677, 230221, 230200, 230221, 0, ''龙江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (678, 230223, 230200, 230223, 0, ''依安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (679, 230224, 230200, 230224, 0, ''泰来县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (680, 230225, 230200, 230225, 0, ''甘南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (681, 230227, 230200, 230227, 0, ''富裕县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (682, 230229, 230200, 230229, 0, ''克山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (683, 230230, 230200, 230230, 0, ''克东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (684, 230231, 230200, 230231, 0, ''拜泉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (685, 230281, 230200, 230281, 0, ''讷河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (686, 230300, 230000, 230300, 1, ''鸡西市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (687, 230302, 230300, 230302, 0, ''鸡冠区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (688, 230303, 230300, 230303, 0, ''恒山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (689, 230304, 230300, 230304, 0, ''滴道区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (690, 230305, 230300, 230305, 0, ''梨树区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (691, 230306, 230300, 230306, 0, ''城子河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (692, 230307, 230300, 230307, 0, ''麻山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (693, 230321, 230300, 230321, 0, ''鸡东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (694, 230381, 230300, 230381, 0, ''虎林市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (695, 230382, 230300, 230382, 0, ''密山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (696, 230400, 230000, 230400, 1, ''鹤岗市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (697, 230402, 230400, 230402, 0, ''向阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (698, 230403, 230400, 230403, 0, ''工农区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (699, 230404, 230400, 230404, 0, ''南山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (700, 230405, 230400, 230405, 0, ''兴安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (701, 230406, 230400, 230406, 0, ''东山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (702, 230407, 230400, 230407, 0, ''兴山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (703, 230421, 230400, 230421, 0, ''萝北县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (704, 230422, 230400, 230422, 0, ''绥滨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (705, 230500, 230000, 230500, 1, ''双鸭山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (706, 230502, 230500, 230502, 0, ''尖山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (707, 230503, 230500, 230503, 0, ''岭东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (708, 230505, 230500, 230505, 0, ''四方台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (709, 230506, 230500, 230506, 0, ''宝山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (710, 230521, 230500, 230521, 0, ''集贤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (711, 230522, 230500, 230522, 0, ''友谊县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (712, 230523, 230500, 230523, 0, ''宝清县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (713, 230524, 230500, 230524, 0, ''饶河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (714, 230600, 230000, 230600, 1, ''大庆市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (715, 230602, 230600, 230602, 0, ''萨尔图区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (716, 230603, 230600, 230603, 0, ''龙凤区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (717, 230604, 230600, 230604, 0, ''让胡路区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (718, 230605, 230600, 230605, 0, ''红岗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (719, 230606, 230600, 230606, 0, ''大同区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (720, 230621, 230600, 230621, 0, ''肇州县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (721, 230622, 230600, 230622, 0, ''肇源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (722, 230623, 230600, 230623, 0, ''林甸县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (723, 230624, 230600, 230624, 0, ''杜尔伯特蒙古族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (724, 230700, 230000, 230700, 1, ''伊春市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (725, 230702, 230700, 230702, 0, ''伊春区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (726, 230703, 230700, 230703, 0, ''南岔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (727, 230704, 230700, 230704, 0, ''友好区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (728, 230705, 230700, 230705, 0, ''西林区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (729, 230706, 230700, 230706, 0, ''翠峦区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (730, 230707, 230700, 230707, 0, ''新青区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (731, 230708, 230700, 230708, 0, ''美溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (732, 230709, 230700, 230709, 0, ''金山屯区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (733, 230710, 230700, 230710, 0, ''五营区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (734, 230711, 230700, 230711, 0, ''乌马河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (735, 230712, 230700, 230712, 0, ''汤旺河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (736, 230713, 230700, 230713, 0, ''带岭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (737, 230714, 230700, 230714, 0, ''乌伊岭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (738, 230715, 230700, 230715, 0, ''红星区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (739, 230716, 230700, 230716, 0, ''上甘岭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (740, 230722, 230700, 230722, 0, ''嘉荫县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (741, 230781, 230700, 230781, 0, ''铁力市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (742, 230800, 230000, 230800, 1, ''佳木斯市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (743, 230803, 230800, 230803, 0, ''向阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (744, 230804, 230800, 230804, 0, ''前进区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (745, 230805, 230800, 230805, 0, ''东风区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (746, 230811, 230800, 230811, 0, ''郊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (747, 230822, 230800, 230822, 0, ''桦南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (748, 230826, 230800, 230826, 0, ''桦川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (749, 230828, 230800, 230828, 0, ''汤原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (750, 230881, 230800, 230881, 0, ''同江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (751, 230882, 230800, 230882, 0, ''富锦市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (752, 230883, 230800, 230883, 0, ''抚远市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (753, 230900, 230000, 230900, 1, ''七台河市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (754, 230902, 230900, 230902, 0, ''新兴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (755, 230903, 230900, 230903, 0, ''桃山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (756, 230904, 230900, 230904, 0, ''茄子河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (757, 230921, 230900, 230921, 0, ''勃利县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (758, 231000, 230000, 231000, 1, ''牡丹江市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (759, 231002, 231000, 231002, 0, ''东安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (760, 231003, 231000, 231003, 0, ''阳明区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (761, 231004, 231000, 231004, 0, ''爱民区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (762, 231005, 231000, 231005, 0, ''西安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (763, 231025, 231000, 231025, 0, ''林口县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (764, 231081, 231000, 231081, 0, ''绥芬河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (765, 231083, 231000, 231083, 0, ''海林市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (766, 231084, 231000, 231084, 0, ''宁安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (767, 231085, 231000, 231085, 0, ''穆棱市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (768, 231086, 231000, 231086, 0, ''东宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (769, 231100, 230000, 231100, 1, ''黑河市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (770, 231102, 231100, 231102, 0, ''爱辉区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (771, 231121, 231100, 231121, 0, ''嫩江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (772, 231123, 231100, 231123, 0, ''逊克县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (773, 231124, 231100, 231124, 0, ''孙吴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (774, 231181, 231100, 231181, 0, ''北安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (775, 231182, 231100, 231182, 0, ''五大连池市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (776, 231200, 230000, 231200, 1, ''绥化市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (777, 231202, 231200, 231202, 0, ''北林区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (778, 231221, 231200, 231221, 0, ''望奎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (779, 231222, 231200, 231222, 0, ''兰西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (780, 231223, 231200, 231223, 0, ''青冈县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (781, 231224, 231200, 231224, 0, ''庆安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (782, 231225, 231200, 231225, 0, ''明水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (783, 231226, 231200, 231226, 0, ''绥棱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (784, 231281, 231200, 231281, 0, ''安达市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (785, 231282, 231200, 231282, 0, ''肇东市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (786, 231283, 231200, 231283, 0, ''海伦市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (787, 232700, 230000, 232700, 1, ''大兴安岭地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (788, 232701, 232700, 232701, 0, ''加格达奇区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (789, 232721, 232700, 232721, 0, ''呼玛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (790, 232722, 232700, 232722, 0, ''塔河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (791, 232723, 232700, 232723, 0, ''漠河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (792, 310000, 0, 310000, 1, ''上海市'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (793, 310100, 310000, 310100, 1, ''上海城区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (794, 310101, 310100, 310101, 0, ''黄浦区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (795, 310104, 310100, 310104, 0, ''徐汇区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (796, 310105, 310100, 310105, 0, ''长宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (797, 310106, 310100, 310106, 0, ''静安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (798, 310107, 310100, 310107, 0, ''普陀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (799, 310109, 310100, 310109, 0, ''虹口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (800, 310110, 310100, 310110, 0, ''杨浦区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (801, 310112, 310100, 310112, 0, ''闵行区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (802, 310113, 310100, 310113, 0, ''宝山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (803, 310114, 310100, 310114, 0, ''嘉定区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (804, 310115, 310100, 310115, 0, ''浦东新区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (805, 310116, 310100, 310116, 0, ''金山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (806, 310117, 310100, 310117, 0, ''松江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (807, 310118, 310100, 310118, 0, ''青浦区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (808, 310120, 310100, 310120, 0, ''奉贤区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (809, 310151, 310100, 310151, 0, ''崇明区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (810, 320000, 0, 320000, 1, ''江苏省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (811, 320100, 320000, 320100, 1, ''南京市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (812, 320102, 320100, 320102, 0, ''玄武区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (813, 320104, 320100, 320104, 0, ''秦淮区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (814, 320105, 320100, 320105, 0, ''建邺区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (815, 320106, 320100, 320106, 0, ''鼓楼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (816, 320111, 320100, 320111, 0, ''浦口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (817, 320113, 320100, 320113, 0, ''栖霞区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (818, 320114, 320100, 320114, 0, ''雨花台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (819, 320115, 320100, 320115, 0, ''江宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (820, 320116, 320100, 320116, 0, ''六合区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (821, 320117, 320100, 320117, 0, ''溧水区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (822, 320118, 320100, 320118, 0, ''高淳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (823, 320200, 320000, 320200, 1, ''无锡市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (824, 320205, 320200, 320205, 0, ''锡山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (825, 320206, 320200, 320206, 0, ''惠山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (826, 320211, 320200, 320211, 0, ''滨湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (827, 320213, 320200, 320213, 0, ''梁溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (828, 320214, 320200, 320214, 0, ''新吴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (829, 320281, 320200, 320281, 0, ''江阴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (830, 320282, 320200, 320282, 0, ''宜兴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (831, 320300, 320000, 320300, 1, ''徐州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (832, 320302, 320300, 320302, 0, ''鼓楼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (833, 320303, 320300, 320303, 0, ''云龙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (834, 320305, 320300, 320305, 0, ''贾汪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (835, 320311, 320300, 320311, 0, ''泉山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (836, 320312, 320300, 320312, 0, ''铜山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (837, 320321, 320300, 320321, 0, ''丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (838, 320322, 320300, 320322, 0, ''沛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (839, 320324, 320300, 320324, 0, ''睢宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (840, 320381, 320300, 320381, 0, ''新沂市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (841, 320382, 320300, 320382, 0, ''邳州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (842, 320400, 320000, 320400, 1, ''常州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (843, 320402, 320400, 320402, 0, ''天宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (844, 320404, 320400, 320404, 0, ''钟楼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (845, 320411, 320400, 320411, 0, ''新北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (846, 320412, 320400, 320412, 0, ''武进区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (847, 320413, 320400, 320413, 0, ''金坛区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (848, 320481, 320400, 320481, 0, ''溧阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (849, 320500, 320000, 320500, 1, ''苏州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (850, 320505, 320500, 320505, 0, ''虎丘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (851, 320506, 320500, 320506, 0, ''吴中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (852, 320507, 320500, 320507, 0, ''相城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (853, 320508, 320500, 320508, 0, ''姑苏区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (854, 320509, 320500, 320509, 0, ''吴江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (855, 320581, 320500, 320581, 0, ''常熟市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (856, 320582, 320500, 320582, 0, ''张家港市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (857, 320583, 320500, 320583, 0, ''昆山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (858, 320585, 320500, 320585, 0, ''太仓市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (859, 320600, 320000, 320600, 1, ''南通市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (860, 320602, 320600, 320602, 0, ''崇川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (861, 320611, 320600, 320611, 0, ''港闸区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (862, 320612, 320600, 320612, 0, ''通州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (863, 320621, 320600, 320621, 0, ''海安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (864, 320623, 320600, 320623, 0, ''如东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (865, 320681, 320600, 320681, 0, ''启东市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (866, 320682, 320600, 320682, 0, ''如皋市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (867, 320684, 320600, 320684, 0, ''海门市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (868, 320700, 320000, 320700, 1, ''连云港市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (869, 320703, 320700, 320703, 0, ''连云区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (870, 320706, 320700, 320706, 0, ''海州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (871, 320707, 320700, 320707, 0, ''赣榆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (872, 320722, 320700, 320722, 0, ''东海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (873, 320723, 320700, 320723, 0, ''灌云县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (874, 320724, 320700, 320724, 0, ''灌南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (875, 320800, 320000, 320800, 1, ''淮安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (876, 320803, 320800, 320803, 0, ''淮安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (877, 320804, 320800, 320804, 0, ''淮阴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (878, 320812, 320800, 320812, 0, ''清江浦区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (879, 320813, 320800, 320813, 0, ''洪泽区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (880, 320826, 320800, 320826, 0, ''涟水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (881, 320830, 320800, 320830, 0, ''盱眙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (882, 320831, 320800, 320831, 0, ''金湖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (883, 320900, 320000, 320900, 1, ''盐城市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (884, 320902, 320900, 320902, 0, ''亭湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (885, 320903, 320900, 320903, 0, ''盐都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (886, 320904, 320900, 320904, 0, ''大丰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (887, 320921, 320900, 320921, 0, ''响水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (888, 320922, 320900, 320922, 0, ''滨海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (889, 320923, 320900, 320923, 0, ''阜宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (890, 320924, 320900, 320924, 0, ''射阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (891, 320925, 320900, 320925, 0, ''建湖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (892, 320981, 320900, 320981, 0, ''东台市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (893, 321000, 320000, 321000, 1, ''扬州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (894, 321002, 321000, 321002, 0, ''广陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (895, 321003, 321000, 321003, 0, ''邗江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (896, 321012, 321000, 321012, 0, ''江都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (897, 321023, 321000, 321023, 0, ''宝应县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (898, 321081, 321000, 321081, 0, ''仪征市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (899, 321084, 321000, 321084, 0, ''高邮市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (900, 321100, 320000, 321100, 1, ''镇江市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (901, 321102, 321100, 321102, 0, ''京口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (902, 321111, 321100, 321111, 0, ''润州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (903, 321112, 321100, 321112, 0, ''丹徒区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (904, 321181, 321100, 321181, 0, ''丹阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (905, 321182, 321100, 321182, 0, ''扬中市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (906, 321183, 321100, 321183, 0, ''句容市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (907, 321200, 320000, 321200, 1, ''泰州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (908, 321202, 321200, 321202, 0, ''海陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (909, 321203, 321200, 321203, 0, ''高港区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (910, 321204, 321200, 321204, 0, ''姜堰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (911, 321281, 321200, 321281, 0, ''兴化市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (912, 321282, 321200, 321282, 0, ''靖江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (913, 321283, 321200, 321283, 0, ''泰兴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (914, 321300, 320000, 321300, 1, ''宿迁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (915, 321302, 321300, 321302, 0, ''宿城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (916, 321311, 321300, 321311, 0, ''宿豫区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (917, 321322, 321300, 321322, 0, ''沭阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (918, 321323, 321300, 321323, 0, ''泗阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (919, 321324, 321300, 321324, 0, ''泗洪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (920, 330000, 0, 330000, 1, ''浙江省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (921, 330100, 330000, 330100, 1, ''杭州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (922, 330102, 330100, 330102, 0, ''上城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (923, 330103, 330100, 330103, 0, ''下城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (924, 330104, 330100, 330104, 0, ''江干区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (925, 330105, 330100, 330105, 0, ''拱墅区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (926, 330106, 330100, 330106, 0, ''西湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (927, 330108, 330100, 330108, 0, ''滨江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (928, 330109, 330100, 330109, 0, ''萧山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (929, 330110, 330100, 330110, 0, ''余杭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (930, 330111, 330100, 330111, 0, ''富阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (931, 330122, 330100, 330122, 0, ''桐庐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (932, 330127, 330100, 330127, 0, ''淳安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (933, 330182, 330100, 330182, 0, ''建德市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (934, 330185, 330100, 330185, 0, ''临安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (935, 330200, 330000, 330200, 1, ''宁波市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (936, 330203, 330200, 330203, 0, ''海曙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (937, 330205, 330200, 330205, 0, ''江北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (938, 330206, 330200, 330206, 0, ''北仑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (939, 330211, 330200, 330211, 0, ''镇海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (940, 330212, 330200, 330212, 0, ''鄞州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (941, 330213, 330200, 330213, 0, ''奉化区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (942, 330225, 330200, 330225, 0, ''象山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (943, 330226, 330200, 330226, 0, ''宁海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (944, 330281, 330200, 330281, 0, ''余姚市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (945, 330282, 330200, 330282, 0, ''慈溪市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (946, 330300, 330000, 330300, 1, ''温州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (947, 330302, 330300, 330302, 0, ''鹿城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (948, 330303, 330300, 330303, 0, ''龙湾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (949, 330304, 330300, 330304, 0, ''瓯海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (950, 330305, 330300, 330305, 0, ''洞头区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (951, 330324, 330300, 330324, 0, ''永嘉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (952, 330326, 330300, 330326, 0, ''平阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (953, 330327, 330300, 330327, 0, ''苍南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (954, 330328, 330300, 330328, 0, ''文成县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (955, 330329, 330300, 330329, 0, ''泰顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (956, 330381, 330300, 330381, 0, ''瑞安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (957, 330382, 330300, 330382, 0, ''乐清市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (958, 330400, 330000, 330400, 1, ''嘉兴市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (959, 330402, 330400, 330402, 0, ''南湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (960, 330411, 330400, 330411, 0, ''秀洲区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (961, 330421, 330400, 330421, 0, ''嘉善县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (962, 330424, 330400, 330424, 0, ''海盐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (963, 330481, 330400, 330481, 0, ''海宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (964, 330482, 330400, 330482, 0, ''平湖市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (965, 330483, 330400, 330483, 0, ''桐乡市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (966, 330500, 330000, 330500, 1, ''湖州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (967, 330502, 330500, 330502, 0, ''吴兴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (968, 330503, 330500, 330503, 0, ''南浔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (969, 330521, 330500, 330521, 0, ''德清县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (970, 330522, 330500, 330522, 0, ''长兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (971, 330523, 330500, 330523, 0, ''安吉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (972, 330600, 330000, 330600, 1, ''绍兴市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (973, 330602, 330600, 330602, 0, ''越城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (974, 330603, 330600, 330603, 0, ''柯桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (975, 330604, 330600, 330604, 0, ''上虞区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (976, 330624, 330600, 330624, 0, ''新昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (977, 330681, 330600, 330681, 0, ''诸暨市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (978, 330683, 330600, 330683, 0, ''嵊州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (979, 330700, 330000, 330700, 1, ''金华市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (980, 330702, 330700, 330702, 0, ''婺城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (981, 330703, 330700, 330703, 0, ''金东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (982, 330723, 330700, 330723, 0, ''武义县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (983, 330726, 330700, 330726, 0, ''浦江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (984, 330727, 330700, 330727, 0, ''磐安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (985, 330781, 330700, 330781, 0, ''兰溪市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (986, 330782, 330700, 330782, 0, ''义乌市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (987, 330783, 330700, 330783, 0, ''东阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (988, 330784, 330700, 330784, 0, ''永康市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (989, 330800, 330000, 330800, 1, ''衢州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (990, 330802, 330800, 330802, 0, ''柯城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (991, 330803, 330800, 330803, 0, ''衢江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (992, 330822, 330800, 330822, 0, ''常山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (993, 330824, 330800, 330824, 0, ''开化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (994, 330825, 330800, 330825, 0, ''龙游县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (995, 330881, 330800, 330881, 0, ''江山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (996, 330900, 330000, 330900, 1, ''舟山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (997, 330902, 330900, 330902, 0, ''定海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (998, 330903, 330900, 330903, 0, ''普陀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (999, 330921, 330900, 330921, 0, ''岱山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1000, 330922, 330900, 330922, 0, ''嵊泗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1001, 331000, 330000, 331000, 1, ''台州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1002, 331002, 331000, 331002, 0, ''椒江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1003, 331003, 331000, 331003, 0, ''黄岩区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1004, 331004, 331000, 331004, 0, ''路桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1005, 331022, 331000, 331022, 0, ''三门县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1006, 331023, 331000, 331023, 0, ''天台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1007, 331024, 331000, 331024, 0, ''仙居县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1008, 331081, 331000, 331081, 0, ''温岭市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1009, 331082, 331000, 331082, 0, ''临海市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1010, 331083, 331000, 331083, 0, ''玉环市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1011, 331100, 330000, 331100, 1, ''丽水市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1012, 331102, 331100, 331102, 0, ''莲都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1013, 331121, 331100, 331121, 0, ''青田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1014, 331122, 331100, 331122, 0, ''缙云县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1015, 331123, 331100, 331123, 0, ''遂昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1016, 331124, 331100, 331124, 0, ''松阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1017, 331125, 331100, 331125, 0, ''云和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1018, 331126, 331100, 331126, 0, ''庆元县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1019, 331127, 331100, 331127, 0, ''景宁畲族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1020, 331181, 331100, 331181, 0, ''龙泉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1021, 340000, 0, 340000, 1, ''安徽省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1022, 340100, 340000, 340100, 1, ''合肥市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1023, 340102, 340100, 340102, 0, ''瑶海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1024, 340103, 340100, 340103, 0, ''庐阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1025, 340104, 340100, 340104, 0, ''蜀山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1026, 340111, 340100, 340111, 0, ''包河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1027, 340121, 340100, 340121, 0, ''长丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1028, 340122, 340100, 340122, 0, ''肥东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1029, 340123, 340100, 340123, 0, ''肥西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1030, 340124, 340100, 340124, 0, ''庐江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1031, 340181, 340100, 340181, 0, ''巢湖市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1032, 340200, 340000, 340200, 1, ''芜湖市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1033, 340202, 340200, 340202, 0, ''镜湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1034, 340203, 340200, 340203, 0, ''弋江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1035, 340207, 340200, 340207, 0, ''鸠江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1036, 340208, 340200, 340208, 0, ''三山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1037, 340221, 340200, 340221, 0, ''芜湖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1038, 340222, 340200, 340222, 0, ''繁昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1039, 340223, 340200, 340223, 0, ''南陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1040, 340225, 340200, 340225, 0, ''无为县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1041, 340300, 340000, 340300, 1, ''蚌埠市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1042, 340302, 340300, 340302, 0, ''龙子湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1043, 340303, 340300, 340303, 0, ''蚌山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1044, 340304, 340300, 340304, 0, ''禹会区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1045, 340311, 340300, 340311, 0, ''淮上区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1046, 340321, 340300, 340321, 0, ''怀远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1047, 340322, 340300, 340322, 0, ''五河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1048, 340323, 340300, 340323, 0, ''固镇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1049, 340400, 340000, 340400, 1, ''淮南市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1050, 340402, 340400, 340402, 0, ''大通区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1051, 340403, 340400, 340403, 0, ''田家庵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1052, 340404, 340400, 340404, 0, ''谢家集区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1053, 340405, 340400, 340405, 0, ''八公山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1054, 340406, 340400, 340406, 0, ''潘集区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1055, 340421, 340400, 340421, 0, ''凤台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1056, 340422, 340400, 340422, 0, ''寿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1057, 340500, 340000, 340500, 1, ''马鞍山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1058, 340503, 340500, 340503, 0, ''花山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1059, 340504, 340500, 340504, 0, ''雨山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1060, 340506, 340500, 340506, 0, ''博望区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1061, 340521, 340500, 340521, 0, ''当涂县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1062, 340522, 340500, 340522, 0, ''含山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1063, 340523, 340500, 340523, 0, ''和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1064, 340600, 340000, 340600, 1, ''淮北市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1065, 340602, 340600, 340602, 0, ''杜集区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1066, 340603, 340600, 340603, 0, ''相山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1067, 340604, 340600, 340604, 0, ''烈山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1068, 340621, 340600, 340621, 0, ''濉溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1069, 340700, 340000, 340700, 1, ''铜陵市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1070, 340705, 340700, 340705, 0, ''铜官区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1071, 340706, 340700, 340706, 0, ''义安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1072, 340711, 340700, 340711, 0, ''郊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1073, 340722, 340700, 340722, 0, ''枞阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1074, 340800, 340000, 340800, 1, ''安庆市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1075, 340802, 340800, 340802, 0, ''迎江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1076, 340803, 340800, 340803, 0, ''大观区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1077, 340811, 340800, 340811, 0, ''宜秀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1078, 340822, 340800, 340822, 0, ''怀宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1079, 340824, 340800, 340824, 0, ''潜山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1080, 340825, 340800, 340825, 0, ''太湖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1081, 340826, 340800, 340826, 0, ''宿松县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1082, 340827, 340800, 340827, 0, ''望江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1083, 340828, 340800, 340828, 0, ''岳西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1084, 340881, 340800, 340881, 0, ''桐城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1085, 341000, 340000, 341000, 1, ''黄山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1086, 341002, 341000, 341002, 0, ''屯溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1087, 341003, 341000, 341003, 0, ''黄山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1088, 341004, 341000, 341004, 0, ''徽州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1089, 341021, 341000, 341021, 0, ''歙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1090, 341022, 341000, 341022, 0, ''休宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1091, 341023, 341000, 341023, 0, ''黟县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1092, 341024, 341000, 341024, 0, ''祁门县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1093, 341100, 340000, 341100, 1, ''滁州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1094, 341102, 341100, 341102, 0, ''琅琊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1095, 341103, 341100, 341103, 0, ''南谯区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1096, 341122, 341100, 341122, 0, ''来安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1097, 341124, 341100, 341124, 0, ''全椒县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1098, 341125, 341100, 341125, 0, ''定远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1099, 341126, 341100, 341126, 0, ''凤阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1100, 341181, 341100, 341181, 0, ''天长市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1101, 341182, 341100, 341182, 0, ''明光市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1102, 341200, 340000, 341200, 1, ''阜阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1103, 341202, 341200, 341202, 0, ''颍州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1104, 341203, 341200, 341203, 0, ''颍东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1105, 341204, 341200, 341204, 0, ''颍泉区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1106, 341221, 341200, 341221, 0, ''临泉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1107, 341222, 341200, 341222, 0, ''太和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1108, 341225, 341200, 341225, 0, ''阜南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1109, 341226, 341200, 341226, 0, ''颍上县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1110, 341282, 341200, 341282, 0, ''界首市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1111, 341300, 340000, 341300, 1, ''宿州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1112, 341302, 341300, 341302, 0, ''埇桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1113, 341321, 341300, 341321, 0, ''砀山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1114, 341322, 341300, 341322, 0, ''萧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1115, 341323, 341300, 341323, 0, ''灵璧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1116, 341324, 341300, 341324, 0, ''泗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1117, 341500, 340000, 341500, 1, ''六安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1118, 341502, 341500, 341502, 0, ''金安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1119, 341503, 341500, 341503, 0, ''裕安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1120, 341504, 341500, 341504, 0, ''叶集区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1121, 341522, 341500, 341522, 0, ''霍邱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1122, 341523, 341500, 341523, 0, ''舒城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1123, 341524, 341500, 341524, 0, ''金寨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1124, 341525, 341500, 341525, 0, ''霍山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1125, 341600, 340000, 341600, 1, ''亳州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1126, 341602, 341600, 341602, 0, ''谯城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1127, 341621, 341600, 341621, 0, ''涡阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1128, 341622, 341600, 341622, 0, ''蒙城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1129, 341623, 341600, 341623, 0, ''利辛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1130, 341700, 340000, 341700, 1, ''池州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1131, 341702, 341700, 341702, 0, ''贵池区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1132, 341721, 341700, 341721, 0, ''东至县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1133, 341722, 341700, 341722, 0, ''石台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1134, 341723, 341700, 341723, 0, ''青阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1135, 341800, 340000, 341800, 1, ''宣城市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1136, 341802, 341800, 341802, 0, ''宣州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1137, 341821, 341800, 341821, 0, ''郎溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1138, 341822, 341800, 341822, 0, ''广德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1139, 341823, 341800, 341823, 0, ''泾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1140, 341824, 341800, 341824, 0, ''绩溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1141, 341825, 341800, 341825, 0, ''旌德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1142, 341881, 341800, 341881, 0, ''宁国市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1143, 350000, 0, 350000, 1, ''福建省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1144, 350100, 350000, 350100, 1, ''福州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1145, 350102, 350100, 350102, 0, ''鼓楼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1146, 350103, 350100, 350103, 0, ''台江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1147, 350104, 350100, 350104, 0, ''仓山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1148, 350105, 350100, 350105, 0, ''马尾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1149, 350111, 350100, 350111, 0, ''晋安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1150, 350121, 350100, 350121, 0, ''闽侯县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1151, 350122, 350100, 350122, 0, ''连江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1152, 350123, 350100, 350123, 0, ''罗源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1153, 350124, 350100, 350124, 0, ''闽清县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1154, 350125, 350100, 350125, 0, ''永泰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1155, 350128, 350100, 350128, 0, ''平潭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1156, 350181, 350100, 350181, 0, ''福清市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1157, 350182, 350100, 350182, 0, ''长乐区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1158, 350200, 350000, 350200, 1, ''厦门市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1159, 350203, 350200, 350203, 0, ''思明区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1160, 350205, 350200, 350205, 0, ''海沧区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1161, 350206, 350200, 350206, 0, ''湖里区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1162, 350211, 350200, 350211, 0, ''集美区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1163, 350212, 350200, 350212, 0, ''同安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1164, 350213, 350200, 350213, 0, ''翔安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1165, 350300, 350000, 350300, 1, ''莆田市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1166, 350302, 350300, 350302, 0, ''城厢区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1167, 350303, 350300, 350303, 0, ''涵江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1168, 350304, 350300, 350304, 0, ''荔城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1169, 350305, 350300, 350305, 0, ''秀屿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1170, 350322, 350300, 350322, 0, ''仙游县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1171, 350400, 350000, 350400, 1, ''三明市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1172, 350402, 350400, 350402, 0, ''梅列区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1173, 350403, 350400, 350403, 0, ''三元区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1174, 350421, 350400, 350421, 0, ''明溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1175, 350423, 350400, 350423, 0, ''清流县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1176, 350424, 350400, 350424, 0, ''宁化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1177, 350425, 350400, 350425, 0, ''大田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1178, 350426, 350400, 350426, 0, ''尤溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1179, 350427, 350400, 350427, 0, ''沙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1180, 350428, 350400, 350428, 0, ''将乐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1181, 350429, 350400, 350429, 0, ''泰宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1182, 350430, 350400, 350430, 0, ''建宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1183, 350481, 350400, 350481, 0, ''永安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1184, 350500, 350000, 350500, 1, ''泉州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1185, 350502, 350500, 350502, 0, ''鲤城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1186, 350503, 350500, 350503, 0, ''丰泽区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1187, 350504, 350500, 350504, 0, ''洛江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1188, 350505, 350500, 350505, 0, ''泉港区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1189, 350521, 350500, 350521, 0, ''惠安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1190, 350524, 350500, 350524, 0, ''安溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1191, 350525, 350500, 350525, 0, ''永春县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1192, 350526, 350500, 350526, 0, ''德化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1193, 350527, 350500, 350527, 0, ''金门县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1194, 350581, 350500, 350581, 0, ''石狮市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1195, 350582, 350500, 350582, 0, ''晋江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1196, 350583, 350500, 350583, 0, ''南安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1197, 350600, 350000, 350600, 1, ''漳州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1198, 350602, 350600, 350602, 0, ''芗城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1199, 350603, 350600, 350603, 0, ''龙文区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1200, 350622, 350600, 350622, 0, ''云霄县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1201, 350623, 350600, 350623, 0, ''漳浦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1202, 350624, 350600, 350624, 0, ''诏安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1203, 350625, 350600, 350625, 0, ''长泰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1204, 350626, 350600, 350626, 0, ''东山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1205, 350627, 350600, 350627, 0, ''南靖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1206, 350628, 350600, 350628, 0, ''平和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1207, 350629, 350600, 350629, 0, ''华安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1208, 350681, 350600, 350681, 0, ''龙海市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1209, 350700, 350000, 350700, 1, ''南平市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1210, 350702, 350700, 350702, 0, ''延平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1211, 350703, 350700, 350703, 0, ''建阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1212, 350721, 350700, 350721, 0, ''顺昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1213, 350722, 350700, 350722, 0, ''浦城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1214, 350723, 350700, 350723, 0, ''光泽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1215, 350724, 350700, 350724, 0, ''松溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1216, 350725, 350700, 350725, 0, ''政和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1217, 350781, 350700, 350781, 0, ''邵武市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1218, 350782, 350700, 350782, 0, ''武夷山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1219, 350783, 350700, 350783, 0, ''建瓯市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1220, 350800, 350000, 350800, 1, ''龙岩市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1221, 350802, 350800, 350802, 0, ''新罗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1222, 350803, 350800, 350803, 0, ''永定区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1223, 350821, 350800, 350821, 0, ''长汀县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1224, 350823, 350800, 350823, 0, ''上杭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1225, 350824, 350800, 350824, 0, ''武平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1226, 350825, 350800, 350825, 0, ''连城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1227, 350881, 350800, 350881, 0, ''漳平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1228, 350900, 350000, 350900, 1, ''宁德市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1229, 350902, 350900, 350902, 0, ''蕉城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1230, 350921, 350900, 350921, 0, ''霞浦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1231, 350922, 350900, 350922, 0, ''古田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1232, 350923, 350900, 350923, 0, ''屏南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1233, 350924, 350900, 350924, 0, ''寿宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1234, 350925, 350900, 350925, 0, ''周宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1235, 350926, 350900, 350926, 0, ''柘荣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1236, 350981, 350900, 350981, 0, ''福安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1237, 350982, 350900, 350982, 0, ''福鼎市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1238, 360000, 0, 360000, 1, ''江西省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1239, 360100, 360000, 360100, 1, ''南昌市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1240, 360102, 360100, 360102, 0, ''东湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1241, 360103, 360100, 360103, 0, ''西湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1242, 360104, 360100, 360104, 0, ''青云谱区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1243, 360105, 360100, 360105, 0, ''湾里区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1244, 360111, 360100, 360111, 0, ''青山湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1245, 360112, 360100, 360112, 0, ''新建区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1246, 360121, 360100, 360121, 0, ''南昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1247, 360123, 360100, 360123, 0, ''安义县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1248, 360124, 360100, 360124, 0, ''进贤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1249, 360200, 360000, 360200, 1, ''景德镇市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1250, 360202, 360200, 360202, 0, ''昌江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1251, 360203, 360200, 360203, 0, ''珠山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1252, 360222, 360200, 360222, 0, ''浮梁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1253, 360281, 360200, 360281, 0, ''乐平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1254, 360300, 360000, 360300, 1, ''萍乡市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1255, 360302, 360300, 360302, 0, ''安源区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1256, 360313, 360300, 360313, 0, ''湘东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1257, 360321, 360300, 360321, 0, ''莲花县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1258, 360322, 360300, 360322, 0, ''上栗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1259, 360323, 360300, 360323, 0, ''芦溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1260, 360400, 360000, 360400, 1, ''九江市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1261, 360402, 360400, 360402, 0, ''濂溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1262, 360403, 360400, 360403, 0, ''浔阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1263, 360421, 360400, 360421, 0, ''柴桑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1264, 360423, 360400, 360423, 0, ''武宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1265, 360424, 360400, 360424, 0, ''修水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1266, 360425, 360400, 360425, 0, ''永修县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1267, 360426, 360400, 360426, 0, ''德安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1268, 360428, 360400, 360428, 0, ''都昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1269, 360429, 360400, 360429, 0, ''湖口县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1270, 360430, 360400, 360430, 0, ''彭泽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1271, 360481, 360400, 360481, 0, ''瑞昌市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1272, 360482, 360400, 360482, 0, ''共青城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1273, 360483, 360400, 360483, 0, ''庐山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1274, 360500, 360000, 360500, 1, ''新余市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1275, 360502, 360500, 360502, 0, ''渝水区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1276, 360521, 360500, 360521, 0, ''分宜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1277, 360600, 360000, 360600, 1, ''鹰潭市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1278, 360602, 360600, 360602, 0, ''月湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1279, 360622, 360600, 360622, 0, ''余江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1280, 360681, 360600, 360681, 0, ''贵溪市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1281, 360700, 360000, 360700, 1, ''赣州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1282, 360702, 360700, 360702, 0, ''章贡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1283, 360703, 360700, 360703, 0, ''南康区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1284, 360704, 360700, 360704, 0, ''赣县区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1285, 360722, 360700, 360722, 0, ''信丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1286, 360723, 360700, 360723, 0, ''大余县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1287, 360724, 360700, 360724, 0, ''上犹县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1288, 360725, 360700, 360725, 0, ''崇义县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1289, 360726, 360700, 360726, 0, ''安远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1290, 360727, 360700, 360727, 0, ''龙南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1291, 360728, 360700, 360728, 0, ''定南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1292, 360729, 360700, 360729, 0, ''全南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1293, 360730, 360700, 360730, 0, ''宁都县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1294, 360731, 360700, 360731, 0, ''于都县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1295, 360732, 360700, 360732, 0, ''兴国县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1296, 360733, 360700, 360733, 0, ''会昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1297, 360734, 360700, 360734, 0, ''寻乌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1298, 360735, 360700, 360735, 0, ''石城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1299, 360781, 360700, 360781, 0, ''瑞金市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1300, 360800, 360000, 360800, 1, ''吉安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1301, 360802, 360800, 360802, 0, ''吉州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1302, 360803, 360800, 360803, 0, ''青原区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1303, 360821, 360800, 360821, 0, ''吉安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1304, 360822, 360800, 360822, 0, ''吉水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1305, 360823, 360800, 360823, 0, ''峡江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1306, 360824, 360800, 360824, 0, ''新干县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1307, 360825, 360800, 360825, 0, ''永丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1308, 360826, 360800, 360826, 0, ''泰和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1309, 360827, 360800, 360827, 0, ''遂川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1310, 360828, 360800, 360828, 0, ''万安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1311, 360829, 360800, 360829, 0, ''安福县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1312, 360830, 360800, 360830, 0, ''永新县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1313, 360881, 360800, 360881, 0, ''井冈山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1314, 360900, 360000, 360900, 1, ''宜春市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1315, 360902, 360900, 360902, 0, ''袁州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1316, 360921, 360900, 360921, 0, ''奉新县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1317, 360922, 360900, 360922, 0, ''万载县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1318, 360923, 360900, 360923, 0, ''上高县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1319, 360924, 360900, 360924, 0, ''宜丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1320, 360925, 360900, 360925, 0, ''靖安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1321, 360926, 360900, 360926, 0, ''铜鼓县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1322, 360981, 360900, 360981, 0, ''丰城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1323, 360982, 360900, 360982, 0, ''樟树市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1324, 360983, 360900, 360983, 0, ''高安市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1325, 361000, 360000, 361000, 1, ''抚州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1326, 361002, 361000, 361002, 0, ''临川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1327, 361003, 361000, 361003, 0, ''东乡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1328, 361021, 361000, 361021, 0, ''南城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1329, 361022, 361000, 361022, 0, ''黎川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1330, 361023, 361000, 361023, 0, ''南丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1331, 361024, 361000, 361024, 0, ''崇仁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1332, 361025, 361000, 361025, 0, ''乐安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1333, 361026, 361000, 361026, 0, ''宜黄县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1334, 361027, 361000, 361027, 0, ''金溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1335, 361028, 361000, 361028, 0, ''资溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1336, 361030, 361000, 361030, 0, ''广昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1337, 361100, 360000, 361100, 1, ''上饶市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1338, 361102, 361100, 361102, 0, ''信州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1339, 361103, 361100, 361103, 0, ''广丰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1340, 361121, 361100, 361121, 0, ''广信区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1341, 361123, 361100, 361123, 0, ''玉山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1342, 361124, 361100, 361124, 0, ''铅山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1343, 361125, 361100, 361125, 0, ''横峰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1344, 361126, 361100, 361126, 0, ''弋阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1345, 361127, 361100, 361127, 0, ''余干县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1346, 361128, 361100, 361128, 0, ''鄱阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1347, 361129, 361100, 361129, 0, ''万年县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1348, 361130, 361100, 361130, 0, ''婺源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1349, 361181, 361100, 361181, 0, ''德兴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1350, 370000, 0, 370000, 1, ''山东省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1351, 370100, 370000, 370100, 1, ''济南市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1352, 370102, 370100, 370102, 0, ''历下区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1353, 370103, 370100, 370103, 0, ''市中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1354, 370104, 370100, 370104, 0, ''槐荫区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1355, 370105, 370100, 370105, 0, ''天桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1356, 370112, 370100, 370112, 0, ''历城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1357, 370113, 370100, 370113, 0, ''长清区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1358, 370114, 370100, 370114, 0, ''章丘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1359, 370124, 370100, 370124, 0, ''平阴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1360, 370125, 370100, 370125, 0, ''济阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1361, 370126, 370100, 370126, 0, ''商河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1362, 370200, 370000, 370200, 1, ''青岛市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1363, 370202, 370200, 370202, 0, ''市南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1364, 370203, 370200, 370203, 0, ''市北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1365, 370211, 370200, 370211, 0, ''黄岛区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1366, 370212, 370200, 370212, 0, ''崂山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1367, 370213, 370200, 370213, 0, ''李沧区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1368, 370214, 370200, 370214, 0, ''城阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1369, 370281, 370200, 370281, 0, ''胶州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1370, 370282, 370200, 370282, 0, ''即墨区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1371, 370283, 370200, 370283, 0, ''平度市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1372, 370285, 370200, 370285, 0, ''莱西市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1373, 370300, 370000, 370300, 1, ''淄博市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1374, 370302, 370300, 370302, 0, ''淄川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1375, 370303, 370300, 370303, 0, ''张店区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1376, 370304, 370300, 370304, 0, ''博山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1377, 370305, 370300, 370305, 0, ''临淄区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1378, 370306, 370300, 370306, 0, ''周村区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1379, 370321, 370300, 370321, 0, ''桓台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1380, 370322, 370300, 370322, 0, ''高青县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1381, 370323, 370300, 370323, 0, ''沂源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1382, 370400, 370000, 370400, 1, ''枣庄市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1383, 370402, 370400, 370402, 0, ''市中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1384, 370403, 370400, 370403, 0, ''薛城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1385, 370404, 370400, 370404, 0, ''峄城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1386, 370405, 370400, 370405, 0, ''台儿庄区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1387, 370406, 370400, 370406, 0, ''山亭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1388, 370481, 370400, 370481, 0, ''滕州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1389, 370500, 370000, 370500, 1, ''东营市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1390, 370502, 370500, 370502, 0, ''东营区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1391, 370503, 370500, 370503, 0, ''河口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1392, 370505, 370500, 370505, 0, ''垦利区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1393, 370522, 370500, 370522, 0, ''利津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1394, 370523, 370500, 370523, 0, ''广饶县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1395, 370600, 370000, 370600, 1, ''烟台市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1396, 370602, 370600, 370602, 0, ''芝罘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1397, 370611, 370600, 370611, 0, ''福山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1398, 370612, 370600, 370612, 0, ''牟平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1399, 370613, 370600, 370613, 0, ''莱山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1400, 370634, 370600, 370634, 0, ''长岛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1401, 370681, 370600, 370681, 0, ''龙口市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1402, 370682, 370600, 370682, 0, ''莱阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1403, 370683, 370600, 370683, 0, ''莱州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1404, 370684, 370600, 370684, 0, ''蓬莱市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1405, 370685, 370600, 370685, 0, ''招远市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1406, 370686, 370600, 370686, 0, ''栖霞市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1407, 370687, 370600, 370687, 0, ''海阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1408, 370700, 370000, 370700, 1, ''潍坊市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1409, 370702, 370700, 370702, 0, ''潍城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1410, 370703, 370700, 370703, 0, ''寒亭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1411, 370704, 370700, 370704, 0, ''坊子区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1412, 370705, 370700, 370705, 0, ''奎文区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1413, 370724, 370700, 370724, 0, ''临朐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1414, 370725, 370700, 370725, 0, ''昌乐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1415, 370781, 370700, 370781, 0, ''青州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1416, 370782, 370700, 370782, 0, ''诸城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1417, 370783, 370700, 370783, 0, ''寿光市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1418, 370784, 370700, 370784, 0, ''安丘市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1419, 370785, 370700, 370785, 0, ''高密市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1420, 370786, 370700, 370786, 0, ''昌邑市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1421, 370800, 370000, 370800, 1, ''济宁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1422, 370811, 370800, 370811, 0, ''任城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1423, 370812, 370800, 370812, 0, ''兖州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1424, 370826, 370800, 370826, 0, ''微山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1425, 370827, 370800, 370827, 0, ''鱼台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1426, 370828, 370800, 370828, 0, ''金乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1427, 370829, 370800, 370829, 0, ''嘉祥县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1428, 370830, 370800, 370830, 0, ''汶上县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1429, 370831, 370800, 370831, 0, ''泗水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1430, 370832, 370800, 370832, 0, ''梁山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1431, 370881, 370800, 370881, 0, ''曲阜市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1432, 370883, 370800, 370883, 0, ''邹城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1433, 370900, 370000, 370900, 1, ''泰安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1434, 370902, 370900, 370902, 0, ''泰山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1435, 370911, 370900, 370911, 0, ''岱岳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1436, 370921, 370900, 370921, 0, ''宁阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1437, 370923, 370900, 370923, 0, ''东平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1438, 370982, 370900, 370982, 0, ''新泰市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1439, 370983, 370900, 370983, 0, ''肥城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1440, 371000, 370000, 371000, 1, ''威海市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1441, 371002, 371000, 371002, 0, ''环翠区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1442, 371003, 371000, 371003, 0, ''文登区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1443, 371082, 371000, 371082, 0, ''荣成市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1444, 371083, 371000, 371083, 0, ''乳山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1445, 371100, 370000, 371100, 1, ''日照市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1446, 371102, 371100, 371102, 0, ''东港区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1447, 371103, 371100, 371103, 0, ''岚山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1448, 371121, 371100, 371121, 0, ''五莲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1449, 371122, 371100, 371122, 0, ''莒县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1450, 371200, 370000, 371200, 1, ''莱芜市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1451, 371202, 371200, 371202, 0, ''莱城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1452, 371203, 371200, 371203, 0, ''钢城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1453, 371300, 370000, 371300, 1, ''临沂市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1454, 371302, 371300, 371302, 0, ''兰山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1455, 371311, 371300, 371311, 0, ''罗庄区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1456, 371312, 371300, 371312, 0, ''河东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1457, 371321, 371300, 371321, 0, ''沂南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1458, 371322, 371300, 371322, 0, ''郯城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1459, 371323, 371300, 371323, 0, ''沂水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1460, 371324, 371300, 371324, 0, ''兰陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1461, 371325, 371300, 371325, 0, ''费县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1462, 371326, 371300, 371326, 0, ''平邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1463, 371327, 371300, 371327, 0, ''莒南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1464, 371328, 371300, 371328, 0, ''蒙阴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1465, 371329, 371300, 371329, 0, ''临沭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1466, 371400, 370000, 371400, 1, ''德州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1467, 371402, 371400, 371402, 0, ''德城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1468, 371403, 371400, 371403, 0, ''陵城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1469, 371422, 371400, 371422, 0, ''宁津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1470, 371423, 371400, 371423, 0, ''庆云县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1471, 371424, 371400, 371424, 0, ''临邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1472, 371425, 371400, 371425, 0, ''齐河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1473, 371426, 371400, 371426, 0, ''平原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1474, 371427, 371400, 371427, 0, ''夏津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1475, 371428, 371400, 371428, 0, ''武城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1476, 371481, 371400, 371481, 0, ''乐陵市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1477, 371482, 371400, 371482, 0, ''禹城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1478, 371500, 370000, 371500, 1, ''聊城市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1479, 371502, 371500, 371502, 0, ''东昌府区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1480, 371521, 371500, 371521, 0, ''阳谷县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1481, 371522, 371500, 371522, 0, ''莘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1482, 371523, 371500, 371523, 0, ''茌平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1483, 371524, 371500, 371524, 0, ''东阿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1484, 371525, 371500, 371525, 0, ''冠县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1485, 371526, 371500, 371526, 0, ''高唐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1486, 371581, 371500, 371581, 0, ''临清市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1487, 371600, 370000, 371600, 1, ''滨州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1488, 371602, 371600, 371602, 0, ''滨城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1489, 371603, 371600, 371603, 0, ''沾化区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1490, 371621, 371600, 371621, 0, ''惠民县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1491, 371622, 371600, 371622, 0, ''阳信县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1492, 371623, 371600, 371623, 0, ''无棣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1493, 371625, 371600, 371625, 0, ''博兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1494, 371626, 371600, 371626, 0, ''邹平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1495, 371700, 370000, 371700, 1, ''菏泽市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1496, 371702, 371700, 371702, 0, ''牡丹区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1497, 371703, 371700, 371703, 0, ''定陶区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1498, 371721, 371700, 371721, 0, ''曹县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1499, 371722, 371700, 371722, 0, ''单县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1500, 371723, 371700, 371723, 0, ''成武县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1501, 371724, 371700, 371724, 0, ''巨野县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1502, 371725, 371700, 371725, 0, ''郓城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1503, 371726, 371700, 371726, 0, ''鄄城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1504, 371728, 371700, 371728, 0, ''东明县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1505, 410000, 0, 410000, 1, ''河南省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1506, 410100, 410000, 410100, 1, ''郑州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1507, 410102, 410100, 410102, 0, ''中原区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1508, 410103, 410100, 410103, 0, ''二七区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1509, 410104, 410100, 410104, 0, ''管城回族区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1510, 410105, 410100, 410105, 0, ''金水区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1511, 410106, 410100, 410106, 0, ''上街区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1512, 410108, 410100, 410108, 0, ''惠济区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1513, 410122, 410100, 410122, 0, ''中牟县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1514, 410181, 410100, 410181, 0, ''巩义市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1515, 410182, 410100, 410182, 0, ''荥阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1516, 410183, 410100, 410183, 0, ''新密市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1517, 410184, 410100, 410184, 0, ''新郑市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1518, 410185, 410100, 410185, 0, ''登封市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1519, 410200, 410000, 410200, 1, ''开封市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1520, 410202, 410200, 410202, 0, ''龙亭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1521, 410203, 410200, 410203, 0, ''顺河回族区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1522, 410204, 410200, 410204, 0, ''鼓楼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1523, 410205, 410200, 410205, 0, ''禹王台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1524, 410212, 410200, 410212, 0, ''祥符区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1525, 410221, 410200, 410221, 0, ''杞县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1526, 410222, 410200, 410222, 0, ''通许县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1527, 410223, 410200, 410223, 0, ''尉氏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1528, 410225, 410200, 410225, 0, ''兰考县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1529, 410300, 410000, 410300, 1, ''洛阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1530, 410302, 410300, 410302, 0, ''老城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1531, 410303, 410300, 410303, 0, ''西工区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1532, 410304, 410300, 410304, 0, ''瀍河回族区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1533, 410305, 410300, 410305, 0, ''涧西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1534, 410306, 410300, 410306, 0, ''吉利区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1535, 410311, 410300, 410311, 0, ''洛龙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1536, 410322, 410300, 410322, 0, ''孟津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1537, 410323, 410300, 410323, 0, ''新安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1538, 410324, 410300, 410324, 0, ''栾川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1539, 410325, 410300, 410325, 0, ''嵩县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1540, 410326, 410300, 410326, 0, ''汝阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1541, 410327, 410300, 410327, 0, ''宜阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1542, 410328, 410300, 410328, 0, ''洛宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1543, 410329, 410300, 410329, 0, ''伊川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1544, 410381, 410300, 410381, 0, ''偃师市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1545, 410400, 410000, 410400, 1, ''平顶山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1546, 410402, 410400, 410402, 0, ''新华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1547, 410403, 410400, 410403, 0, ''卫东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1548, 410404, 410400, 410404, 0, ''石龙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1549, 410411, 410400, 410411, 0, ''湛河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1550, 410421, 410400, 410421, 0, ''宝丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1551, 410422, 410400, 410422, 0, ''叶县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1552, 410423, 410400, 410423, 0, ''鲁山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1553, 410425, 410400, 410425, 0, ''郏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1554, 410481, 410400, 410481, 0, ''舞钢市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1555, 410482, 410400, 410482, 0, ''汝州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1556, 410500, 410000, 410500, 1, ''安阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1557, 410502, 410500, 410502, 0, ''文峰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1558, 410503, 410500, 410503, 0, ''北关区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1559, 410505, 410500, 410505, 0, ''殷都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1560, 410506, 410500, 410506, 0, ''龙安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1561, 410522, 410500, 410522, 0, ''安阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1562, 410523, 410500, 410523, 0, ''汤阴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1563, 410526, 410500, 410526, 0, ''滑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1564, 410527, 410500, 410527, 0, ''内黄县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1565, 410581, 410500, 410581, 0, ''林州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1566, 410600, 410000, 410600, 1, ''鹤壁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1567, 410602, 410600, 410602, 0, ''鹤山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1568, 410603, 410600, 410603, 0, ''山城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1569, 410611, 410600, 410611, 0, ''淇滨区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1570, 410621, 410600, 410621, 0, ''浚县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1571, 410622, 410600, 410622, 0, ''淇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1572, 410700, 410000, 410700, 1, ''新乡市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1573, 410702, 410700, 410702, 0, ''红旗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1574, 410703, 410700, 410703, 0, ''卫滨区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1575, 410704, 410700, 410704, 0, ''凤泉区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1576, 410711, 410700, 410711, 0, ''牧野区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1577, 410721, 410700, 410721, 0, ''新乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1578, 410724, 410700, 410724, 0, ''获嘉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1579, 410725, 410700, 410725, 0, ''原阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1580, 410726, 410700, 410726, 0, ''延津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1581, 410727, 410700, 410727, 0, ''封丘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1582, 410728, 410700, 410728, 0, ''长垣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1583, 410781, 410700, 410781, 0, ''卫辉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1584, 410782, 410700, 410782, 0, ''辉县市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1585, 410800, 410000, 410800, 1, ''焦作市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1586, 410802, 410800, 410802, 0, ''解放区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1587, 410803, 410800, 410803, 0, ''中站区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1588, 410804, 410800, 410804, 0, ''马村区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1589, 410811, 410800, 410811, 0, ''山阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1590, 410821, 410800, 410821, 0, ''修武县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1591, 410822, 410800, 410822, 0, ''博爱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1592, 410823, 410800, 410823, 0, ''武陟县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1593, 410825, 410800, 410825, 0, ''温县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1594, 410882, 410800, 410882, 0, ''沁阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1595, 410883, 410800, 410883, 0, ''孟州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1596, 410900, 410000, 410900, 1, ''濮阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1597, 410902, 410900, 410902, 0, ''华龙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1598, 410922, 410900, 410922, 0, ''清丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1599, 410923, 410900, 410923, 0, ''南乐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1600, 410926, 410900, 410926, 0, ''范县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1601, 410927, 410900, 410927, 0, ''台前县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1602, 410928, 410900, 410928, 0, ''濮阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1603, 411000, 410000, 411000, 1, ''许昌市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1604, 411002, 411000, 411002, 0, ''魏都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1605, 411003, 411000, 411003, 0, ''建安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1606, 411024, 411000, 411024, 0, ''鄢陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1607, 411025, 411000, 411025, 0, ''襄城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1608, 411081, 411000, 411081, 0, ''禹州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1609, 411082, 411000, 411082, 0, ''长葛市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1610, 411100, 410000, 411100, 1, ''漯河市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1611, 411102, 411100, 411102, 0, ''源汇区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1612, 411103, 411100, 411103, 0, ''郾城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1613, 411104, 411100, 411104, 0, ''召陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1614, 411121, 411100, 411121, 0, ''舞阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1615, 411122, 411100, 411122, 0, ''临颍县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1616, 411200, 410000, 411200, 1, ''三门峡市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1617, 411202, 411200, 411202, 0, ''湖滨区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1618, 411203, 411200, 411203, 0, ''陕州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1619, 411221, 411200, 411221, 0, ''渑池县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1620, 411224, 411200, 411224, 0, ''卢氏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1621, 411281, 411200, 411281, 0, ''义马市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1622, 411282, 411200, 411282, 0, ''灵宝市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1623, 411300, 410000, 411300, 1, ''南阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1624, 411302, 411300, 411302, 0, ''宛城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1625, 411303, 411300, 411303, 0, ''卧龙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1626, 411321, 411300, 411321, 0, ''南召县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1627, 411322, 411300, 411322, 0, ''方城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1628, 411323, 411300, 411323, 0, ''西峡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1629, 411324, 411300, 411324, 0, ''镇平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1630, 411325, 411300, 411325, 0, ''内乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1631, 411326, 411300, 411326, 0, ''淅川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1632, 411327, 411300, 411327, 0, ''社旗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1633, 411328, 411300, 411328, 0, ''唐河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1634, 411329, 411300, 411329, 0, ''新野县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1635, 411330, 411300, 411330, 0, ''桐柏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1636, 411381, 411300, 411381, 0, ''邓州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1637, 411400, 410000, 411400, 1, ''商丘市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1638, 411402, 411400, 411402, 0, ''梁园区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1639, 411403, 411400, 411403, 0, ''睢阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1640, 411421, 411400, 411421, 0, ''民权县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1641, 411422, 411400, 411422, 0, ''睢县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1642, 411423, 411400, 411423, 0, ''宁陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1643, 411424, 411400, 411424, 0, ''柘城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1644, 411425, 411400, 411425, 0, ''虞城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1645, 411426, 411400, 411426, 0, ''夏邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1646, 411481, 411400, 411481, 0, ''永城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1647, 411500, 410000, 411500, 1, ''信阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1648, 411502, 411500, 411502, 0, ''浉河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1649, 411503, 411500, 411503, 0, ''平桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1650, 411521, 411500, 411521, 0, ''罗山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1651, 411522, 411500, 411522, 0, ''光山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1652, 411523, 411500, 411523, 0, ''新县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1653, 411524, 411500, 411524, 0, ''商城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1654, 411525, 411500, 411525, 0, ''固始县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1655, 411526, 411500, 411526, 0, ''潢川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1656, 411527, 411500, 411527, 0, ''淮滨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1657, 411528, 411500, 411528, 0, ''息县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1658, 411600, 410000, 411600, 1, ''周口市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1659, 411602, 411600, 411602, 0, ''川汇区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1660, 411621, 411600, 411621, 0, ''扶沟县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1661, 411622, 411600, 411622, 0, ''西华县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1662, 411623, 411600, 411623, 0, ''商水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1663, 411624, 411600, 411624, 0, ''沈丘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1664, 411625, 411600, 411625, 0, ''郸城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1665, 411626, 411600, 411626, 0, ''淮阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1666, 411627, 411600, 411627, 0, ''太康县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1667, 411628, 411600, 411628, 0, ''鹿邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1668, 411681, 411600, 411681, 0, ''项城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1669, 411700, 410000, 411700, 1, ''驻马店市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1670, 411702, 411700, 411702, 0, ''驿城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1671, 411721, 411700, 411721, 0, ''西平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1672, 411722, 411700, 411722, 0, ''上蔡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1673, 411723, 411700, 411723, 0, ''平舆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1674, 411724, 411700, 411724, 0, ''正阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1675, 411725, 411700, 411725, 0, ''确山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1676, 411726, 411700, 411726, 0, ''泌阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1677, 411727, 411700, 411727, 0, ''汝南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1678, 411728, 411700, 411728, 0, ''遂平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1679, 411729, 411700, 411729, 0, ''新蔡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1680, 419001, 410000, 419001, 0, ''济源市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1681, 420000, 0, 420000, 1, ''湖北省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1682, 420100, 420000, 420100, 1, ''武汉市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1683, 420102, 420100, 420102, 0, ''江岸区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1684, 420103, 420100, 420103, 0, ''江汉区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1685, 420104, 420100, 420104, 0, ''硚口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1686, 420105, 420100, 420105, 0, ''汉阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1687, 420106, 420100, 420106, 0, ''武昌区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1688, 420107, 420100, 420107, 0, ''青山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1689, 420111, 420100, 420111, 0, ''洪山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1690, 420112, 420100, 420112, 0, ''东西湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1691, 420113, 420100, 420113, 0, ''汉南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1692, 420114, 420100, 420114, 0, ''蔡甸区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1693, 420115, 420100, 420115, 0, ''江夏区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1694, 420116, 420100, 420116, 0, ''黄陂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1695, 420117, 420100, 420117, 0, ''新洲区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1696, 420200, 420000, 420200, 1, ''黄石市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1697, 420202, 420200, 420202, 0, ''黄石港区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1698, 420203, 420200, 420203, 0, ''西塞山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1699, 420204, 420200, 420204, 0, ''下陆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1700, 420205, 420200, 420205, 0, ''铁山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1701, 420222, 420200, 420222, 0, ''阳新县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1702, 420281, 420200, 420281, 0, ''大冶市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1703, 420300, 420000, 420300, 1, ''十堰市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1704, 420302, 420300, 420302, 0, ''茅箭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1705, 420303, 420300, 420303, 0, ''张湾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1706, 420304, 420300, 420304, 0, ''郧阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1707, 420322, 420300, 420322, 0, ''郧西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1708, 420323, 420300, 420323, 0, ''竹山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1709, 420324, 420300, 420324, 0, ''竹溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1710, 420325, 420300, 420325, 0, ''房县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1711, 420381, 420300, 420381, 0, ''丹江口市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1712, 420500, 420000, 420500, 1, ''宜昌市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1713, 420502, 420500, 420502, 0, ''西陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1714, 420503, 420500, 420503, 0, ''伍家岗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1715, 420504, 420500, 420504, 0, ''点军区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1716, 420505, 420500, 420505, 0, ''猇亭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1717, 420506, 420500, 420506, 0, ''夷陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1718, 420525, 420500, 420525, 0, ''远安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1719, 420526, 420500, 420526, 0, ''兴山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1720, 420527, 420500, 420527, 0, ''秭归县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1721, 420528, 420500, 420528, 0, ''长阳土家族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1722, 420529, 420500, 420529, 0, ''五峰土家族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1723, 420581, 420500, 420581, 0, ''宜都市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1724, 420582, 420500, 420582, 0, ''当阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1725, 420583, 420500, 420583, 0, ''枝江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1726, 420600, 420000, 420600, 1, ''襄阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1727, 420602, 420600, 420602, 0, ''襄城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1728, 420606, 420600, 420606, 0, ''樊城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1729, 420607, 420600, 420607, 0, ''襄州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1730, 420624, 420600, 420624, 0, ''南漳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1731, 420625, 420600, 420625, 0, ''谷城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1732, 420626, 420600, 420626, 0, ''保康县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1733, 420682, 420600, 420682, 0, ''老河口市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1734, 420683, 420600, 420683, 0, ''枣阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1735, 420684, 420600, 420684, 0, ''宜城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1736, 420700, 420000, 420700, 1, ''鄂州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1737, 420702, 420700, 420702, 0, ''梁子湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1738, 420703, 420700, 420703, 0, ''华容区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1739, 420704, 420700, 420704, 0, ''鄂城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1740, 420800, 420000, 420800, 1, ''荆门市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1741, 420802, 420800, 420802, 0, ''东宝区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1742, 420804, 420800, 420804, 0, ''掇刀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1743, 420821, 420800, 420821, 0, ''京山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1744, 420822, 420800, 420822, 0, ''沙洋县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1745, 420881, 420800, 420881, 0, ''钟祥市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1746, 420900, 420000, 420900, 1, ''孝感市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1747, 420902, 420900, 420902, 0, ''孝南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1748, 420921, 420900, 420921, 0, ''孝昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1749, 420922, 420900, 420922, 0, ''大悟县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1750, 420923, 420900, 420923, 0, ''云梦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1751, 420981, 420900, 420981, 0, ''应城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1752, 420982, 420900, 420982, 0, ''安陆市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1753, 420984, 420900, 420984, 0, ''汉川市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1754, 421000, 420000, 421000, 1, ''荆州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1755, 421002, 421000, 421002, 0, ''沙市区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1756, 421003, 421000, 421003, 0, ''荆州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1757, 421022, 421000, 421022, 0, ''公安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1758, 421023, 421000, 421023, 0, ''监利县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1759, 421024, 421000, 421024, 0, ''江陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1760, 421081, 421000, 421081, 0, ''石首市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1761, 421083, 421000, 421083, 0, ''洪湖市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1762, 421087, 421000, 421087, 0, ''松滋市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1763, 421100, 420000, 421100, 1, ''黄冈市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1764, 421102, 421100, 421102, 0, ''黄州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1765, 421121, 421100, 421121, 0, ''团风县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1766, 421122, 421100, 421122, 0, ''红安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1767, 421123, 421100, 421123, 0, ''罗田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1768, 421124, 421100, 421124, 0, ''英山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1769, 421125, 421100, 421125, 0, ''浠水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1770, 421126, 421100, 421126, 0, ''蕲春县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1771, 421127, 421100, 421127, 0, ''黄梅县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1772, 421181, 421100, 421181, 0, ''麻城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1773, 421182, 421100, 421182, 0, ''武穴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1774, 421200, 420000, 421200, 1, ''咸宁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1775, 421202, 421200, 421202, 0, ''咸安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1776, 421221, 421200, 421221, 0, ''嘉鱼县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1777, 421222, 421200, 421222, 0, ''通城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1778, 421223, 421200, 421223, 0, ''崇阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1779, 421224, 421200, 421224, 0, ''通山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1780, 421281, 421200, 421281, 0, ''赤壁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1781, 421300, 420000, 421300, 1, ''随州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1782, 421303, 421300, 421303, 0, ''曾都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1783, 421321, 421300, 421321, 0, ''随县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1784, 421381, 421300, 421381, 0, ''广水市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1785, 422800, 420000, 422800, 1, ''恩施土家族苗族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1786, 422801, 422800, 422801, 0, ''恩施市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1787, 422802, 422800, 422802, 0, ''利川市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1788, 422822, 422800, 422822, 0, ''建始县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1789, 422823, 422800, 422823, 0, ''巴东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1790, 422825, 422800, 422825, 0, ''宣恩县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1791, 422826, 422800, 422826, 0, ''咸丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1792, 422827, 422800, 422827, 0, ''来凤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1793, 422828, 422800, 422828, 0, ''鹤峰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1794, 429004, 420000, 429004, 0, ''仙桃市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1795, 429005, 420000, 429005, 0, ''潜江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1796, 429006, 420000, 429006, 0, ''天门市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1797, 429021, 420000, 429021, 0, ''神农架林区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1798, 430000, 0, 430000, 1, ''湖南省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1799, 430100, 430000, 430100, 1, ''长沙市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1800, 430102, 430100, 430102, 0, ''芙蓉区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1801, 430103, 430100, 430103, 0, ''天心区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1802, 430104, 430100, 430104, 0, ''岳麓区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1803, 430105, 430100, 430105, 0, ''开福区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1804, 430111, 430100, 430111, 0, ''雨花区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1805, 430112, 430100, 430112, 0, ''望城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1806, 430121, 430100, 430121, 0, ''长沙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1807, 430124, 430100, 430124, 0, ''宁乡市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1808, 430181, 430100, 430181, 0, ''浏阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1809, 430200, 430000, 430200, 1, ''株洲市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1810, 430202, 430200, 430202, 0, ''荷塘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1811, 430203, 430200, 430203, 0, ''芦淞区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1812, 430204, 430200, 430204, 0, ''石峰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1813, 430211, 430200, 430211, 0, ''天元区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1814, 430221, 430200, 430221, 0, ''株洲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1815, 430223, 430200, 430223, 0, ''攸县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1816, 430224, 430200, 430224, 0, ''茶陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1817, 430225, 430200, 430225, 0, ''炎陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1818, 430281, 430200, 430281, 0, ''醴陵市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1819, 430300, 430000, 430300, 1, ''湘潭市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1820, 430302, 430300, 430302, 0, ''雨湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1821, 430304, 430300, 430304, 0, ''岳塘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1822, 430321, 430300, 430321, 0, ''湘潭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1823, 430381, 430300, 430381, 0, ''湘乡市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1824, 430382, 430300, 430382, 0, ''韶山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1825, 430400, 430000, 430400, 1, ''衡阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1826, 430405, 430400, 430405, 0, ''珠晖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1827, 430406, 430400, 430406, 0, ''雁峰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1828, 430407, 430400, 430407, 0, ''石鼓区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1829, 430408, 430400, 430408, 0, ''蒸湘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1830, 430412, 430400, 430412, 0, ''南岳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1831, 430421, 430400, 430421, 0, ''衡阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1832, 430422, 430400, 430422, 0, ''衡南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1833, 430423, 430400, 430423, 0, ''衡山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1834, 430424, 430400, 430424, 0, ''衡东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1835, 430426, 430400, 430426, 0, ''祁东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1836, 430481, 430400, 430481, 0, ''耒阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1837, 430482, 430400, 430482, 0, ''常宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1838, 430500, 430000, 430500, 1, ''邵阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1839, 430502, 430500, 430502, 0, ''双清区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1840, 430503, 430500, 430503, 0, ''大祥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1841, 430511, 430500, 430511, 0, ''北塔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1842, 430521, 430500, 430521, 0, ''邵东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1843, 430522, 430500, 430522, 0, ''新邵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1844, 430523, 430500, 430523, 0, ''邵阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1845, 430524, 430500, 430524, 0, ''隆回县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1846, 430525, 430500, 430525, 0, ''洞口县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1847, 430527, 430500, 430527, 0, ''绥宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1848, 430528, 430500, 430528, 0, ''新宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1849, 430529, 430500, 430529, 0, ''城步苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1850, 430581, 430500, 430581, 0, ''武冈市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1851, 430600, 430000, 430600, 1, ''岳阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1852, 430602, 430600, 430602, 0, ''岳阳楼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1853, 430603, 430600, 430603, 0, ''云溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1854, 430611, 430600, 430611, 0, ''君山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1855, 430621, 430600, 430621, 0, ''岳阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1856, 430623, 430600, 430623, 0, ''华容县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1857, 430624, 430600, 430624, 0, ''湘阴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1858, 430626, 430600, 430626, 0, ''平江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1859, 430681, 430600, 430681, 0, ''汨罗市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1860, 430682, 430600, 430682, 0, ''临湘市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1861, 430700, 430000, 430700, 1, ''常德市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1862, 430702, 430700, 430702, 0, ''武陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1863, 430703, 430700, 430703, 0, ''鼎城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1864, 430721, 430700, 430721, 0, ''安乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1865, 430722, 430700, 430722, 0, ''汉寿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1866, 430723, 430700, 430723, 0, ''澧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1867, 430724, 430700, 430724, 0, ''临澧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1868, 430725, 430700, 430725, 0, ''桃源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1869, 430726, 430700, 430726, 0, ''石门县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1870, 430781, 430700, 430781, 0, ''津市市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1871, 430800, 430000, 430800, 1, ''张家界市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1872, 430802, 430800, 430802, 0, ''永定区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1873, 430811, 430800, 430811, 0, ''武陵源区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1874, 430821, 430800, 430821, 0, ''慈利县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1875, 430822, 430800, 430822, 0, ''桑植县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1876, 430900, 430000, 430900, 1, ''益阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1877, 430902, 430900, 430902, 0, ''资阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1878, 430903, 430900, 430903, 0, ''赫山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1879, 430921, 430900, 430921, 0, ''南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1880, 430922, 430900, 430922, 0, ''桃江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1881, 430923, 430900, 430923, 0, ''安化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1882, 430981, 430900, 430981, 0, ''沅江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1883, 431000, 430000, 431000, 1, ''郴州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1884, 431002, 431000, 431002, 0, ''北湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1885, 431003, 431000, 431003, 0, ''苏仙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1886, 431021, 431000, 431021, 0, ''桂阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1887, 431022, 431000, 431022, 0, ''宜章县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1888, 431023, 431000, 431023, 0, ''永兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1889, 431024, 431000, 431024, 0, ''嘉禾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1890, 431025, 431000, 431025, 0, ''临武县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1891, 431026, 431000, 431026, 0, ''汝城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1892, 431027, 431000, 431027, 0, ''桂东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1893, 431028, 431000, 431028, 0, ''安仁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1894, 431081, 431000, 431081, 0, ''资兴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1895, 431100, 430000, 431100, 1, ''永州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1896, 431102, 431100, 431102, 0, ''零陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1897, 431103, 431100, 431103, 0, ''冷水滩区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1898, 431121, 431100, 431121, 0, ''祁阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1899, 431122, 431100, 431122, 0, ''东安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1900, 431123, 431100, 431123, 0, ''双牌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1901, 431124, 431100, 431124, 0, ''道县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1902, 431125, 431100, 431125, 0, ''江永县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1903, 431126, 431100, 431126, 0, ''宁远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1904, 431127, 431100, 431127, 0, ''蓝山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1905, 431128, 431100, 431128, 0, ''新田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1906, 431129, 431100, 431129, 0, ''江华瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1907, 431200, 430000, 431200, 1, ''怀化市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1908, 431202, 431200, 431202, 0, ''鹤城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1909, 431221, 431200, 431221, 0, ''中方县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1910, 431222, 431200, 431222, 0, ''沅陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1911, 431223, 431200, 431223, 0, ''辰溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1912, 431224, 431200, 431224, 0, ''溆浦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1913, 431225, 431200, 431225, 0, ''会同县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1914, 431226, 431200, 431226, 0, ''麻阳苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1915, 431227, 431200, 431227, 0, ''新晃侗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1916, 431228, 431200, 431228, 0, ''芷江侗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1917, 431229, 431200, 431229, 0, ''靖州苗族侗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1918, 431230, 431200, 431230, 0, ''通道侗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1919, 431281, 431200, 431281, 0, ''洪江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1920, 431300, 430000, 431300, 1, ''娄底市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1921, 431302, 431300, 431302, 0, ''娄星区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1922, 431321, 431300, 431321, 0, ''双峰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1923, 431322, 431300, 431322, 0, ''新化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1924, 431381, 431300, 431381, 0, ''冷水江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1925, 431382, 431300, 431382, 0, ''涟源市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1926, 433100, 430000, 433100, 1, ''湘西土家族苗族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1927, 433101, 433100, 433101, 0, ''吉首市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1928, 433122, 433100, 433122, 0, ''泸溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1929, 433123, 433100, 433123, 0, ''凤凰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1930, 433124, 433100, 433124, 0, ''花垣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1931, 433125, 433100, 433125, 0, ''保靖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1932, 433126, 433100, 433126, 0, ''古丈县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1933, 433127, 433100, 433127, 0, ''永顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1934, 433130, 433100, 433130, 0, ''龙山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1935, 440000, 0, 440000, 1, ''广东省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1936, 440100, 440000, 440100, 1, ''广州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1937, 440103, 440100, 440103, 0, ''荔湾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1938, 440104, 440100, 440104, 0, ''越秀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1939, 440105, 440100, 440105, 0, ''海珠区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1940, 440106, 440100, 440106, 0, ''天河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1941, 440111, 440100, 440111, 0, ''白云区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1942, 440112, 440100, 440112, 0, ''黄埔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1943, 440113, 440100, 440113, 0, ''番禺区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1944, 440114, 440100, 440114, 0, ''花都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1945, 440115, 440100, 440115, 0, ''南沙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1946, 440117, 440100, 440117, 0, ''从化区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1947, 440118, 440100, 440118, 0, ''增城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1948, 440200, 440000, 440200, 1, ''韶关市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1949, 440203, 440200, 440203, 0, ''武江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1950, 440204, 440200, 440204, 0, ''浈江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1951, 440205, 440200, 440205, 0, ''曲江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1952, 440222, 440200, 440222, 0, ''始兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1953, 440224, 440200, 440224, 0, ''仁化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1954, 440229, 440200, 440229, 0, ''翁源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1955, 440232, 440200, 440232, 0, ''乳源瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1956, 440233, 440200, 440233, 0, ''新丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1957, 440281, 440200, 440281, 0, ''乐昌市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1958, 440282, 440200, 440282, 0, ''南雄市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1959, 440300, 440000, 440300, 1, ''深圳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1960, 440303, 440300, 440303, 0, ''罗湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1961, 440304, 440300, 440304, 0, ''福田区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1962, 440305, 440300, 440305, 0, ''南山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1963, 440306, 440300, 440306, 0, ''宝安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1964, 440307, 440300, 440307, 0, ''龙岗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1965, 440308, 440300, 440308, 0, ''盐田区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1966, 440309, 440300, 440309, 0, ''龙华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1967, 440310, 440300, 440310, 0, ''坪山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1968, 440400, 440000, 440400, 1, ''珠海市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1969, 440402, 440400, 440402, 0, ''香洲区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1970, 440403, 440400, 440403, 0, ''斗门区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1971, 440404, 440400, 440404, 0, ''金湾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1972, 440500, 440000, 440500, 1, ''汕头市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1973, 440507, 440500, 440507, 0, ''龙湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1974, 440511, 440500, 440511, 0, ''金平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1975, 440512, 440500, 440512, 0, ''濠江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1976, 440513, 440500, 440513, 0, ''潮阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1977, 440514, 440500, 440514, 0, ''潮南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1978, 440515, 440500, 440515, 0, ''澄海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1979, 440523, 440500, 440523, 0, ''南澳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1980, 440600, 440000, 440600, 1, ''佛山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1981, 440604, 440600, 440604, 0, ''禅城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1982, 440605, 440600, 440605, 0, ''南海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1983, 440606, 440600, 440606, 0, ''顺德区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1984, 440607, 440600, 440607, 0, ''三水区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1985, 440608, 440600, 440608, 0, ''高明区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1986, 440700, 440000, 440700, 1, ''江门市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1987, 440703, 440700, 440703, 0, ''蓬江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1988, 440704, 440700, 440704, 0, ''江海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1989, 440705, 440700, 440705, 0, ''新会区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1990, 440781, 440700, 440781, 0, ''台山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1991, 440783, 440700, 440783, 0, ''开平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1992, 440784, 440700, 440784, 0, ''鹤山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1993, 440785, 440700, 440785, 0, ''恩平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1994, 440800, 440000, 440800, 1, ''湛江市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1995, 440802, 440800, 440802, 0, ''赤坎区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1996, 440803, 440800, 440803, 0, ''霞山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1997, 440804, 440800, 440804, 0, ''坡头区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1998, 440811, 440800, 440811, 0, ''麻章区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (1999, 440823, 440800, 440823, 0, ''遂溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2000, 440825, 440800, 440825, 0, ''徐闻县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2001, 440881, 440800, 440881, 0, ''廉江市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2002, 440882, 440800, 440882, 0, ''雷州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2003, 440883, 440800, 440883, 0, ''吴川市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2004, 440900, 440000, 440900, 1, ''茂名市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2005, 440902, 440900, 440902, 0, ''茂南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2006, 440904, 440900, 440904, 0, ''电白区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2007, 440981, 440900, 440981, 0, ''高州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2008, 440982, 440900, 440982, 0, ''化州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2009, 440983, 440900, 440983, 0, ''信宜市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2010, 441200, 440000, 441200, 1, ''肇庆市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2011, 441202, 441200, 441202, 0, ''端州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2012, 441203, 441200, 441203, 0, ''鼎湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2013, 441204, 441200, 441204, 0, ''高要区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2014, 441223, 441200, 441223, 0, ''广宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2015, 441224, 441200, 441224, 0, ''怀集县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2016, 441225, 441200, 441225, 0, ''封开县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2017, 441226, 441200, 441226, 0, ''德庆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2018, 441284, 441200, 441284, 0, ''四会市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2019, 441300, 440000, 441300, 1, ''惠州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2020, 441302, 441300, 441302, 0, ''惠城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2021, 441303, 441300, 441303, 0, ''惠阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2022, 441322, 441300, 441322, 0, ''博罗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2023, 441323, 441300, 441323, 0, ''惠东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2024, 441324, 441300, 441324, 0, ''龙门县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2025, 441400, 440000, 441400, 1, ''梅州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2026, 441402, 441400, 441402, 0, ''梅江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2027, 441403, 441400, 441403, 0, ''梅县区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2028, 441422, 441400, 441422, 0, ''大埔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2029, 441423, 441400, 441423, 0, ''丰顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2030, 441424, 441400, 441424, 0, ''五华县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2031, 441426, 441400, 441426, 0, ''平远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2032, 441427, 441400, 441427, 0, ''蕉岭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2033, 441481, 441400, 441481, 0, ''兴宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2034, 441500, 440000, 441500, 1, ''汕尾市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2035, 441502, 441500, 441502, 0, ''城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2036, 441521, 441500, 441521, 0, ''海丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2037, 441523, 441500, 441523, 0, ''陆河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2038, 441581, 441500, 441581, 0, ''陆丰市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2039, 441600, 440000, 441600, 1, ''河源市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2040, 441602, 441600, 441602, 0, ''源城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2041, 441621, 441600, 441621, 0, ''紫金县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2042, 441622, 441600, 441622, 0, ''龙川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2043, 441623, 441600, 441623, 0, ''连平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2044, 441624, 441600, 441624, 0, ''和平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2045, 441625, 441600, 441625, 0, ''东源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2046, 441700, 440000, 441700, 1, ''阳江市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2047, 441702, 441700, 441702, 0, ''江城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2048, 441704, 441700, 441704, 0, ''阳东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2049, 441721, 441700, 441721, 0, ''阳西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2050, 441781, 441700, 441781, 0, ''阳春市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2051, 441800, 440000, 441800, 1, ''清远市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2052, 441802, 441800, 441802, 0, ''清城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2053, 441803, 441800, 441803, 0, ''清新区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2054, 441821, 441800, 441821, 0, ''佛冈县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2055, 441823, 441800, 441823, 0, ''阳山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2056, 441825, 441800, 441825, 0, ''连山壮族瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2057, 441826, 441800, 441826, 0, ''连南瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2058, 441881, 441800, 441881, 0, ''英德市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2059, 441882, 441800, 441882, 0, ''连州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2060, 441900, 440000, 441900, 0, ''东莞市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2061, 442000, 440000, 442000, 0, ''中山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2062, 442100, 440000, 442100, 0, ''东沙群岛'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2063, 445100, 440000, 445100, 1, ''潮州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2064, 445102, 445100, 445102, 0, ''湘桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2065, 445103, 445100, 445103, 0, ''潮安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2066, 445122, 445100, 445122, 0, ''饶平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2067, 445200, 440000, 445200, 1, ''揭阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2068, 445202, 445200, 445202, 0, ''榕城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2069, 445203, 445200, 445203, 0, ''揭东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2070, 445222, 445200, 445222, 0, ''揭西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2071, 445224, 445200, 445224, 0, ''惠来县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2072, 445281, 445200, 445281, 0, ''普宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2073, 445300, 440000, 445300, 1, ''云浮市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2074, 445302, 445300, 445302, 0, ''云城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2075, 445303, 445300, 445303, 0, ''云安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2076, 445321, 445300, 445321, 0, ''新兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2077, 445322, 445300, 445322, 0, ''郁南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2078, 445381, 445300, 445381, 0, ''罗定市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2079, 450000, 0, 450000, 1, ''广西壮族自治区'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2080, 450100, 450000, 450100, 1, ''南宁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2081, 450102, 450100, 450102, 0, ''兴宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2082, 450103, 450100, 450103, 0, ''青秀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2083, 450105, 450100, 450105, 0, ''江南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2084, 450107, 450100, 450107, 0, ''西乡塘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2085, 450108, 450100, 450108, 0, ''良庆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2086, 450109, 450100, 450109, 0, ''邕宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2087, 450110, 450100, 450110, 0, ''武鸣区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2088, 450123, 450100, 450123, 0, ''隆安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2089, 450124, 450100, 450124, 0, ''马山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2090, 450125, 450100, 450125, 0, ''上林县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2091, 450126, 450100, 450126, 0, ''宾阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2092, 450127, 450100, 450127, 0, ''横县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2093, 450200, 450000, 450200, 1, ''柳州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2094, 450202, 450200, 450202, 0, ''城中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2095, 450203, 450200, 450203, 0, ''鱼峰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2096, 450204, 450200, 450204, 0, ''柳南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2097, 450205, 450200, 450205, 0, ''柳北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2098, 450206, 450200, 450206, 0, ''柳江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2099, 450222, 450200, 450222, 0, ''柳城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2100, 450223, 450200, 450223, 0, ''鹿寨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2101, 450224, 450200, 450224, 0, ''融安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2102, 450225, 450200, 450225, 0, ''融水苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2103, 450226, 450200, 450226, 0, ''三江侗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2104, 450300, 450000, 450300, 1, ''桂林市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2105, 450302, 450300, 450302, 0, ''秀峰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2106, 450303, 450300, 450303, 0, ''叠彩区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2107, 450304, 450300, 450304, 0, ''象山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2108, 450305, 450300, 450305, 0, ''七星区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2109, 450311, 450300, 450311, 0, ''雁山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2110, 450312, 450300, 450312, 0, ''临桂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2111, 450321, 450300, 450321, 0, ''阳朔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2112, 450323, 450300, 450323, 0, ''灵川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2113, 450324, 450300, 450324, 0, ''全州县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2114, 450325, 450300, 450325, 0, ''兴安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2115, 450326, 450300, 450326, 0, ''永福县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2116, 450327, 450300, 450327, 0, ''灌阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2117, 450328, 450300, 450328, 0, ''龙胜各族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2118, 450329, 450300, 450329, 0, ''资源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2119, 450330, 450300, 450330, 0, ''平乐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2120, 450331, 450300, 450331, 0, ''荔浦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2121, 450332, 450300, 450332, 0, ''恭城瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2122, 450400, 450000, 450400, 1, ''梧州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2123, 450403, 450400, 450403, 0, ''万秀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2124, 450405, 450400, 450405, 0, ''长洲区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2125, 450406, 450400, 450406, 0, ''龙圩区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2126, 450421, 450400, 450421, 0, ''苍梧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2127, 450422, 450400, 450422, 0, ''藤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2128, 450423, 450400, 450423, 0, ''蒙山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2129, 450481, 450400, 450481, 0, ''岑溪市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2130, 450500, 450000, 450500, 1, ''北海市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2131, 450502, 450500, 450502, 0, ''海城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2132, 450503, 450500, 450503, 0, ''银海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2133, 450512, 450500, 450512, 0, ''铁山港区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2134, 450521, 450500, 450521, 0, ''合浦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2135, 450600, 450000, 450600, 1, ''防城港市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2136, 450602, 450600, 450602, 0, ''港口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2137, 450603, 450600, 450603, 0, ''防城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2138, 450621, 450600, 450621, 0, ''上思县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2139, 450681, 450600, 450681, 0, ''东兴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2140, 450700, 450000, 450700, 1, ''钦州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2141, 450702, 450700, 450702, 0, ''钦南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2142, 450703, 450700, 450703, 0, ''钦北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2143, 450721, 450700, 450721, 0, ''灵山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2144, 450722, 450700, 450722, 0, ''浦北县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2145, 450800, 450000, 450800, 1, ''贵港市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2146, 450802, 450800, 450802, 0, ''港北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2147, 450803, 450800, 450803, 0, ''港南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2148, 450804, 450800, 450804, 0, ''覃塘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2149, 450821, 450800, 450821, 0, ''平南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2150, 450881, 450800, 450881, 0, ''桂平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2151, 450900, 450000, 450900, 1, ''玉林市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2152, 450902, 450900, 450902, 0, ''玉州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2153, 450903, 450900, 450903, 0, ''福绵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2154, 450921, 450900, 450921, 0, ''容县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2155, 450922, 450900, 450922, 0, ''陆川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2156, 450923, 450900, 450923, 0, ''博白县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2157, 450924, 450900, 450924, 0, ''兴业县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2158, 450981, 450900, 450981, 0, ''北流市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2159, 451000, 450000, 451000, 1, ''百色市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2160, 451002, 451000, 451002, 0, ''右江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2161, 451021, 451000, 451021, 0, ''田阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2162, 451022, 451000, 451022, 0, ''田东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2163, 451023, 451000, 451023, 0, ''平果县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2164, 451024, 451000, 451024, 0, ''德保县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2165, 451026, 451000, 451026, 0, ''那坡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2166, 451027, 451000, 451027, 0, ''凌云县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2167, 451028, 451000, 451028, 0, ''乐业县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2168, 451029, 451000, 451029, 0, ''田林县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2169, 451030, 451000, 451030, 0, ''西林县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2170, 451031, 451000, 451031, 0, ''隆林各族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2171, 451081, 451000, 451081, 0, ''靖西市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2172, 451100, 450000, 451100, 1, ''贺州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2173, 451102, 451100, 451102, 0, ''八步区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2174, 451103, 451100, 451103, 0, ''平桂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2175, 451121, 451100, 451121, 0, ''昭平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2176, 451122, 451100, 451122, 0, ''钟山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2177, 451123, 451100, 451123, 0, ''富川瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2178, 451200, 450000, 451200, 1, ''河池市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2179, 451202, 451200, 451202, 0, ''金城江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2180, 451203, 451200, 451203, 0, ''宜州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2181, 451221, 451200, 451221, 0, ''南丹县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2182, 451222, 451200, 451222, 0, ''天峨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2183, 451223, 451200, 451223, 0, ''凤山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2184, 451224, 451200, 451224, 0, ''东兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2185, 451225, 451200, 451225, 0, ''罗城仫佬族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2186, 451226, 451200, 451226, 0, ''环江毛南族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2187, 451227, 451200, 451227, 0, ''巴马瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2188, 451228, 451200, 451228, 0, ''都安瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2189, 451229, 451200, 451229, 0, ''大化瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2190, 451300, 450000, 451300, 1, ''来宾市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2191, 451302, 451300, 451302, 0, ''兴宾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2192, 451321, 451300, 451321, 0, ''忻城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2193, 451322, 451300, 451322, 0, ''象州县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2194, 451323, 451300, 451323, 0, ''武宣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2195, 451324, 451300, 451324, 0, ''金秀瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2196, 451381, 451300, 451381, 0, ''合山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2197, 451400, 450000, 451400, 1, ''崇左市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2198, 451402, 451400, 451402, 0, ''江州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2199, 451421, 451400, 451421, 0, ''扶绥县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2200, 451422, 451400, 451422, 0, ''宁明县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2201, 451423, 451400, 451423, 0, ''龙州县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2202, 451424, 451400, 451424, 0, ''大新县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2203, 451425, 451400, 451425, 0, ''天等县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2204, 451481, 451400, 451481, 0, ''凭祥市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2205, 460000, 0, 460000, 1, ''海南省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2206, 460100, 460000, 460100, 1, ''海口市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2207, 460105, 460100, 460105, 0, ''秀英区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2208, 460106, 460100, 460106, 0, ''龙华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2209, 460107, 460100, 460107, 0, ''琼山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2210, 460108, 460100, 460108, 0, ''美兰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2211, 460200, 460000, 460200, 1, ''三亚市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2212, 460202, 460200, 460202, 0, ''海棠区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2213, 460203, 460200, 460203, 0, ''吉阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2214, 460204, 460200, 460204, 0, ''天涯区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2215, 460205, 460200, 460205, 0, ''崖州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2216, 460300, 460000, 460300, 1, ''三沙市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2217, 460321, 460300, 460321, 0, ''西沙群岛'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2218, 460322, 460300, 460322, 0, ''南沙群岛'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2219, 460323, 460300, 460323, 0, ''中沙群岛的岛礁及其海域'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2220, 460400, 460000, 460400, 0, ''儋州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2221, 469001, 460000, 469001, 0, ''五指山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2222, 469002, 460000, 469002, 0, ''琼海市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2223, 469005, 460000, 469005, 0, ''文昌市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2224, 469006, 460000, 469006, 0, ''万宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2225, 469007, 460000, 469007, 0, ''东方市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2226, 469021, 460000, 469021, 0, ''定安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2227, 469022, 460000, 469022, 0, ''屯昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2228, 469023, 460000, 469023, 0, ''澄迈县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2229, 469024, 460000, 469024, 0, ''临高县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2230, 469025, 460000, 469025, 0, ''白沙黎族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2231, 469026, 460000, 469026, 0, ''昌江黎族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2232, 469027, 460000, 469027, 0, ''乐东黎族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2233, 469028, 460000, 469028, 0, ''陵水黎族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2234, 469029, 460000, 469029, 0, ''保亭黎族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2235, 469030, 460000, 469030, 0, ''琼中黎族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2236, 500000, 0, 500000, 1, ''重庆市'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2237, 500100, 500000, 500100, 1, ''重庆城区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2238, 500101, 500100, 500101, 0, ''万州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2239, 500102, 500100, 500102, 0, ''涪陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2240, 500103, 500100, 500103, 0, ''渝中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2241, 500104, 500100, 500104, 0, ''大渡口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2242, 500105, 500100, 500105, 0, ''江北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2243, 500106, 500100, 500106, 0, ''沙坪坝区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2244, 500107, 500100, 500107, 0, ''九龙坡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2245, 500108, 500100, 500108, 0, ''南岸区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2246, 500109, 500100, 500109, 0, ''北碚区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2247, 500110, 500100, 500110, 0, ''綦江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2248, 500111, 500100, 500111, 0, ''大足区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2249, 500112, 500100, 500112, 0, ''渝北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2250, 500113, 500100, 500113, 0, ''巴南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2251, 500114, 500100, 500114, 0, ''黔江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2252, 500115, 500100, 500115, 0, ''长寿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2253, 500116, 500100, 500116, 0, ''江津区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2254, 500117, 500100, 500117, 0, ''合川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2255, 500118, 500100, 500118, 0, ''永川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2256, 500119, 500100, 500119, 0, ''南川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2257, 500120, 500100, 500120, 0, ''璧山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2258, 500151, 500100, 500151, 0, ''铜梁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2259, 500152, 500100, 500152, 0, ''潼南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2260, 500153, 500100, 500153, 0, ''荣昌区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2261, 500154, 500100, 500154, 0, ''开州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2262, 500200, 500000, 500200, 1, ''重庆郊县'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2263, 500155, 500200, 500155, 0, ''梁平区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2264, 500156, 500200, 500156, 0, ''武隆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2265, 500229, 500200, 500229, 0, ''城口县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2266, 500230, 500200, 500230, 0, ''丰都县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2267, 500231, 500200, 500231, 0, ''垫江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2268, 500233, 500200, 500233, 0, ''忠县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2269, 500235, 500200, 500235, 0, ''云阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2270, 500236, 500200, 500236, 0, ''奉节县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2271, 500237, 500200, 500237, 0, ''巫山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2272, 500238, 500200, 500238, 0, ''巫溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2273, 500240, 500200, 500240, 0, ''石柱土家族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2274, 500241, 500200, 500241, 0, ''秀山土家族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2275, 500242, 500200, 500242, 0, ''酉阳土家族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2276, 500243, 500200, 500243, 0, ''彭水苗族土家族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2277, 510000, 0, 510000, 1, ''四川省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2278, 510100, 510000, 510100, 1, ''成都市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2279, 510104, 510100, 510104, 0, ''锦江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2280, 510105, 510100, 510105, 0, ''青羊区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2281, 510106, 510100, 510106, 0, ''金牛区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2282, 510107, 510100, 510107, 0, ''武侯区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2283, 510108, 510100, 510108, 0, ''成华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2284, 510112, 510100, 510112, 0, ''龙泉驿区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2285, 510113, 510100, 510113, 0, ''青白江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2286, 510114, 510100, 510114, 0, ''新都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2287, 510115, 510100, 510115, 0, ''温江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2288, 510116, 510100, 510116, 0, ''双流区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2289, 510117, 510100, 510117, 0, ''郫都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2290, 510121, 510100, 510121, 0, ''金堂县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2291, 510129, 510100, 510129, 0, ''大邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2292, 510131, 510100, 510131, 0, ''蒲江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2293, 510132, 510100, 510132, 0, ''新津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2294, 510181, 510100, 510181, 0, ''都江堰市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2295, 510182, 510100, 510182, 0, ''彭州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2296, 510183, 510100, 510183, 0, ''邛崃市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2297, 510184, 510100, 510184, 0, ''崇州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2298, 510185, 510100, 510185, 0, ''简阳市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2299, 510300, 510000, 510300, 1, ''自贡市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2300, 510302, 510300, 510302, 0, ''自流井区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2301, 510303, 510300, 510303, 0, ''贡井区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2302, 510304, 510300, 510304, 0, ''大安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2303, 510311, 510300, 510311, 0, ''沿滩区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2304, 510321, 510300, 510321, 0, ''荣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2305, 510322, 510300, 510322, 0, ''富顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2306, 510400, 510000, 510400, 1, ''攀枝花市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2307, 510402, 510400, 510402, 0, ''东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2308, 510403, 510400, 510403, 0, ''西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2309, 510411, 510400, 510411, 0, ''仁和区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2310, 510421, 510400, 510421, 0, ''米易县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2311, 510422, 510400, 510422, 0, ''盐边县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2312, 510500, 510000, 510500, 1, ''泸州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2313, 510502, 510500, 510502, 0, ''江阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2314, 510503, 510500, 510503, 0, ''纳溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2315, 510504, 510500, 510504, 0, ''龙马潭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2316, 510521, 510500, 510521, 0, ''泸县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2317, 510522, 510500, 510522, 0, ''合江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2318, 510524, 510500, 510524, 0, ''叙永县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2319, 510525, 510500, 510525, 0, ''古蔺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2320, 510600, 510000, 510600, 1, ''德阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2321, 510603, 510600, 510603, 0, ''旌阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2322, 510623, 510600, 510623, 0, ''中江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2323, 510626, 510600, 510626, 0, ''罗江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2324, 510681, 510600, 510681, 0, ''广汉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2325, 510682, 510600, 510682, 0, ''什邡市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2326, 510683, 510600, 510683, 0, ''绵竹市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2327, 510700, 510000, 510700, 1, ''绵阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2328, 510703, 510700, 510703, 0, ''涪城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2329, 510704, 510700, 510704, 0, ''游仙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2330, 510705, 510700, 510705, 0, ''安州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2331, 510722, 510700, 510722, 0, ''三台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2332, 510723, 510700, 510723, 0, ''盐亭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2333, 510725, 510700, 510725, 0, ''梓潼县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2334, 510726, 510700, 510726, 0, ''北川羌族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2335, 510727, 510700, 510727, 0, ''平武县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2336, 510781, 510700, 510781, 0, ''江油市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2337, 510800, 510000, 510800, 1, ''广元市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2338, 510802, 510800, 510802, 0, ''利州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2339, 510811, 510800, 510811, 0, ''昭化区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2340, 510812, 510800, 510812, 0, ''朝天区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2341, 510821, 510800, 510821, 0, ''旺苍县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2342, 510822, 510800, 510822, 0, ''青川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2343, 510823, 510800, 510823, 0, ''剑阁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2344, 510824, 510800, 510824, 0, ''苍溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2345, 510900, 510000, 510900, 1, ''遂宁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2346, 510903, 510900, 510903, 0, ''船山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2347, 510904, 510900, 510904, 0, ''安居区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2348, 510921, 510900, 510921, 0, ''蓬溪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2349, 510922, 510900, 510922, 0, ''射洪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2350, 510923, 510900, 510923, 0, ''大英县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2351, 511000, 510000, 511000, 1, ''内江市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2352, 511002, 511000, 511002, 0, ''市中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2353, 511011, 511000, 511011, 0, ''东兴区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2354, 511024, 511000, 511024, 0, ''威远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2355, 511025, 511000, 511025, 0, ''资中县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2356, 511083, 511000, 511083, 0, ''隆昌市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2357, 511100, 510000, 511100, 1, ''乐山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2358, 511102, 511100, 511102, 0, ''市中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2359, 511111, 511100, 511111, 0, ''沙湾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2360, 511112, 511100, 511112, 0, ''五通桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2361, 511113, 511100, 511113, 0, ''金口河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2362, 511123, 511100, 511123, 0, ''犍为县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2363, 511124, 511100, 511124, 0, ''井研县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2364, 511126, 511100, 511126, 0, ''夹江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2365, 511129, 511100, 511129, 0, ''沐川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2366, 511132, 511100, 511132, 0, ''峨边彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2367, 511133, 511100, 511133, 0, ''马边彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2368, 511181, 511100, 511181, 0, ''峨眉山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2369, 511300, 510000, 511300, 1, ''南充市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2370, 511302, 511300, 511302, 0, ''顺庆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2371, 511303, 511300, 511303, 0, ''高坪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2372, 511304, 511300, 511304, 0, ''嘉陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2373, 511321, 511300, 511321, 0, ''南部县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2374, 511322, 511300, 511322, 0, ''营山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2375, 511323, 511300, 511323, 0, ''蓬安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2376, 511324, 511300, 511324, 0, ''仪陇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2377, 511325, 511300, 511325, 0, ''西充县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2378, 511381, 511300, 511381, 0, ''阆中市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2379, 511400, 510000, 511400, 1, ''眉山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2380, 511402, 511400, 511402, 0, ''东坡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2381, 511403, 511400, 511403, 0, ''彭山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2382, 511421, 511400, 511421, 0, ''仁寿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2383, 511423, 511400, 511423, 0, ''洪雅县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2384, 511424, 511400, 511424, 0, ''丹棱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2385, 511425, 511400, 511425, 0, ''青神县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2386, 511500, 510000, 511500, 1, ''宜宾市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2387, 511502, 511500, 511502, 0, ''翠屏区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2388, 511503, 511500, 511503, 0, ''南溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2389, 511521, 511500, 511521, 0, ''宜宾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2390, 511523, 511500, 511523, 0, ''江安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2391, 511524, 511500, 511524, 0, ''长宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2392, 511525, 511500, 511525, 0, ''高县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2393, 511526, 511500, 511526, 0, ''珙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2394, 511527, 511500, 511527, 0, ''筠连县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2395, 511528, 511500, 511528, 0, ''兴文县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2396, 511529, 511500, 511529, 0, ''屏山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2397, 511600, 510000, 511600, 1, ''广安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2398, 511602, 511600, 511602, 0, ''广安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2399, 511603, 511600, 511603, 0, ''前锋区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2400, 511621, 511600, 511621, 0, ''岳池县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2401, 511622, 511600, 511622, 0, ''武胜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2402, 511623, 511600, 511623, 0, ''邻水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2403, 511681, 511600, 511681, 0, ''华蓥市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2404, 511700, 510000, 511700, 1, ''达州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2405, 511702, 511700, 511702, 0, ''通川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2406, 511703, 511700, 511703, 0, ''达川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2407, 511722, 511700, 511722, 0, ''宣汉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2408, 511723, 511700, 511723, 0, ''开江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2409, 511724, 511700, 511724, 0, ''大竹县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2410, 511725, 511700, 511725, 0, ''渠县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2411, 511781, 511700, 511781, 0, ''万源市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2412, 511800, 510000, 511800, 1, ''雅安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2413, 511802, 511800, 511802, 0, ''雨城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2414, 511803, 511800, 511803, 0, ''名山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2415, 511822, 511800, 511822, 0, ''荥经县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2416, 511823, 511800, 511823, 0, ''汉源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2417, 511824, 511800, 511824, 0, ''石棉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2418, 511825, 511800, 511825, 0, ''天全县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2419, 511826, 511800, 511826, 0, ''芦山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2420, 511827, 511800, 511827, 0, ''宝兴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2421, 511900, 510000, 511900, 1, ''巴中市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2422, 511902, 511900, 511902, 0, ''巴州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2423, 511903, 511900, 511903, 0, ''恩阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2424, 511921, 511900, 511921, 0, ''通江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2425, 511922, 511900, 511922, 0, ''南江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2426, 511923, 511900, 511923, 0, ''平昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2427, 512000, 510000, 512000, 1, ''资阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2428, 512002, 512000, 512002, 0, ''雁江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2429, 512021, 512000, 512021, 0, ''安岳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2430, 512022, 512000, 512022, 0, ''乐至县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2431, 513200, 510000, 513200, 1, ''阿坝藏族羌族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2432, 513201, 513200, 513201, 0, ''马尔康市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2433, 513221, 513200, 513221, 0, ''汶川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2434, 513222, 513200, 513222, 0, ''理县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2435, 513223, 513200, 513223, 0, ''茂县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2436, 513224, 513200, 513224, 0, ''松潘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2437, 513225, 513200, 513225, 0, ''九寨沟县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2438, 513226, 513200, 513226, 0, ''金川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2439, 513227, 513200, 513227, 0, ''小金县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2440, 513228, 513200, 513228, 0, ''黑水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2441, 513230, 513200, 513230, 0, ''壤塘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2442, 513231, 513200, 513231, 0, ''阿坝县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2443, 513232, 513200, 513232, 0, ''若尔盖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2444, 513233, 513200, 513233, 0, ''红原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2445, 513300, 510000, 513300, 1, ''甘孜藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2446, 513301, 513300, 513301, 0, ''康定市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2447, 513322, 513300, 513322, 0, ''泸定县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2448, 513323, 513300, 513323, 0, ''丹巴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2449, 513324, 513300, 513324, 0, ''九龙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2450, 513325, 513300, 513325, 0, ''雅江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2451, 513326, 513300, 513326, 0, ''道孚县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2452, 513327, 513300, 513327, 0, ''炉霍县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2453, 513328, 513300, 513328, 0, ''甘孜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2454, 513329, 513300, 513329, 0, ''新龙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2455, 513330, 513300, 513330, 0, ''德格县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2456, 513331, 513300, 513331, 0, ''白玉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2457, 513332, 513300, 513332, 0, ''石渠县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2458, 513333, 513300, 513333, 0, ''色达县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2459, 513334, 513300, 513334, 0, ''理塘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2460, 513335, 513300, 513335, 0, ''巴塘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2461, 513336, 513300, 513336, 0, ''乡城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2462, 513337, 513300, 513337, 0, ''稻城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2463, 513338, 513300, 513338, 0, ''得荣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2464, 513400, 510000, 513400, 1, ''凉山彝族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2465, 513401, 513400, 513401, 0, ''西昌市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2466, 513422, 513400, 513422, 0, ''木里藏族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2467, 513423, 513400, 513423, 0, ''盐源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2468, 513424, 513400, 513424, 0, ''德昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2469, 513425, 513400, 513425, 0, ''会理县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2470, 513426, 513400, 513426, 0, ''会东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2471, 513427, 513400, 513427, 0, ''宁南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2472, 513428, 513400, 513428, 0, ''普格县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2473, 513429, 513400, 513429, 0, ''布拖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2474, 513430, 513400, 513430, 0, ''金阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2475, 513431, 513400, 513431, 0, ''昭觉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2476, 513432, 513400, 513432, 0, ''喜德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2477, 513433, 513400, 513433, 0, ''冕宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2478, 513434, 513400, 513434, 0, ''越西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2479, 513435, 513400, 513435, 0, ''甘洛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2480, 513436, 513400, 513436, 0, ''美姑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2481, 513437, 513400, 513437, 0, ''雷波县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2482, 520000, 0, 520000, 1, ''贵州省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2483, 520100, 520000, 520100, 1, ''贵阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2484, 520102, 520100, 520102, 0, ''南明区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2485, 520103, 520100, 520103, 0, ''云岩区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2486, 520111, 520100, 520111, 0, ''花溪区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2487, 520112, 520100, 520112, 0, ''乌当区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2488, 520113, 520100, 520113, 0, ''白云区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2489, 520115, 520100, 520115, 0, ''观山湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2490, 520121, 520100, 520121, 0, ''开阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2491, 520122, 520100, 520122, 0, ''息烽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2492, 520123, 520100, 520123, 0, ''修文县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2493, 520181, 520100, 520181, 0, ''清镇市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2494, 520200, 520000, 520200, 1, ''六盘水市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2495, 520201, 520200, 520201, 0, ''钟山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2496, 520203, 520200, 520203, 0, ''六枝特区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2497, 520221, 520200, 520221, 0, ''水城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2498, 520281, 520200, 520281, 0, ''盘州市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2499, 520300, 520000, 520300, 1, ''遵义市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2500, 520302, 520300, 520302, 0, ''红花岗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2501, 520303, 520300, 520303, 0, ''汇川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2502, 520304, 520300, 520304, 0, ''播州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2503, 520322, 520300, 520322, 0, ''桐梓县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2504, 520323, 520300, 520323, 0, ''绥阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2505, 520324, 520300, 520324, 0, ''正安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2506, 520325, 520300, 520325, 0, ''道真仡佬族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2507, 520326, 520300, 520326, 0, ''务川仡佬族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2508, 520327, 520300, 520327, 0, ''凤冈县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2509, 520328, 520300, 520328, 0, ''湄潭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2510, 520329, 520300, 520329, 0, ''余庆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2511, 520330, 520300, 520330, 0, ''习水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2512, 520381, 520300, 520381, 0, ''赤水市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2513, 520382, 520300, 520382, 0, ''仁怀市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2514, 520400, 520000, 520400, 1, ''安顺市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2515, 520402, 520400, 520402, 0, ''西秀区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2516, 520403, 520400, 520403, 0, ''平坝区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2517, 520422, 520400, 520422, 0, ''普定县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2518, 520423, 520400, 520423, 0, ''镇宁布依族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2519, 520424, 520400, 520424, 0, ''关岭布依族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2520, 520425, 520400, 520425, 0, ''紫云苗族布依族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2521, 520500, 520000, 520500, 1, ''毕节市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2522, 520502, 520500, 520502, 0, ''七星关区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2523, 520521, 520500, 520521, 0, ''大方县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2524, 520522, 520500, 520522, 0, ''黔西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2525, 520523, 520500, 520523, 0, ''金沙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2526, 520524, 520500, 520524, 0, ''织金县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2527, 520525, 520500, 520525, 0, ''纳雍县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2528, 520526, 520500, 520526, 0, ''威宁彝族回族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2529, 520527, 520500, 520527, 0, ''赫章县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2530, 520600, 520000, 520600, 1, ''铜仁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2531, 520602, 520600, 520602, 0, ''碧江区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2532, 520603, 520600, 520603, 0, ''万山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2533, 520621, 520600, 520621, 0, ''江口县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2534, 520622, 520600, 520622, 0, ''玉屏侗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2535, 520623, 520600, 520623, 0, ''石阡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2536, 520624, 520600, 520624, 0, ''思南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2537, 520625, 520600, 520625, 0, ''印江土家族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2538, 520626, 520600, 520626, 0, ''德江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2539, 520627, 520600, 520627, 0, ''沿河土家族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2540, 520628, 520600, 520628, 0, ''松桃苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2541, 522300, 520000, 522300, 1, ''黔西南布依族苗族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2542, 522301, 522300, 522301, 0, ''兴义市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2543, 522322, 522300, 522322, 0, ''兴仁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2544, 522323, 522300, 522323, 0, ''普安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2545, 522324, 522300, 522324, 0, ''晴隆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2546, 522325, 522300, 522325, 0, ''贞丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2547, 522326, 522300, 522326, 0, ''望谟县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2548, 522327, 522300, 522327, 0, ''册亨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2549, 522328, 522300, 522328, 0, ''安龙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2550, 522600, 520000, 522600, 1, ''黔东南苗族侗族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2551, 522601, 522600, 522601, 0, ''凯里市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2552, 522622, 522600, 522622, 0, ''黄平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2553, 522623, 522600, 522623, 0, ''施秉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2554, 522624, 522600, 522624, 0, ''三穗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2555, 522625, 522600, 522625, 0, ''镇远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2556, 522626, 522600, 522626, 0, ''岑巩县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2557, 522627, 522600, 522627, 0, ''天柱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2558, 522628, 522600, 522628, 0, ''锦屏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2559, 522629, 522600, 522629, 0, ''剑河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2560, 522630, 522600, 522630, 0, ''台江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2561, 522631, 522600, 522631, 0, ''黎平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2562, 522632, 522600, 522632, 0, ''榕江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2563, 522633, 522600, 522633, 0, ''从江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2564, 522634, 522600, 522634, 0, ''雷山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2565, 522635, 522600, 522635, 0, ''麻江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2566, 522636, 522600, 522636, 0, ''丹寨县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2567, 522700, 520000, 522700, 1, ''黔南布依族苗族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2568, 522701, 522700, 522701, 0, ''都匀市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2569, 522702, 522700, 522702, 0, ''福泉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2570, 522722, 522700, 522722, 0, ''荔波县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2571, 522723, 522700, 522723, 0, ''贵定县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2572, 522725, 522700, 522725, 0, ''瓮安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2573, 522726, 522700, 522726, 0, ''独山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2574, 522727, 522700, 522727, 0, ''平塘县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2575, 522728, 522700, 522728, 0, ''罗甸县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2576, 522729, 522700, 522729, 0, ''长顺县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2577, 522730, 522700, 522730, 0, ''龙里县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2578, 522731, 522700, 522731, 0, ''惠水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2579, 522732, 522700, 522732, 0, ''三都水族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2580, 530000, 0, 530000, 1, ''云南省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2581, 530100, 530000, 530100, 1, ''昆明市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2582, 530102, 530100, 530102, 0, ''五华区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2583, 530103, 530100, 530103, 0, ''盘龙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2584, 530111, 530100, 530111, 0, ''官渡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2585, 530112, 530100, 530112, 0, ''西山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2586, 530113, 530100, 530113, 0, ''东川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2587, 530114, 530100, 530114, 0, ''呈贡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2588, 530115, 530100, 530115, 0, ''晋宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2589, 530124, 530100, 530124, 0, ''富民县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2590, 530125, 530100, 530125, 0, ''宜良县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2591, 530126, 530100, 530126, 0, ''石林彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2592, 530127, 530100, 530127, 0, ''嵩明县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2593, 530128, 530100, 530128, 0, ''禄劝彝族苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2594, 530129, 530100, 530129, 0, ''寻甸回族彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2595, 530181, 530100, 530181, 0, ''安宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2596, 530300, 530000, 530300, 1, ''曲靖市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2597, 530302, 530300, 530302, 0, ''麒麟区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2598, 530303, 530300, 530303, 0, ''沾益区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2599, 530321, 530300, 530321, 0, ''马龙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2600, 530322, 530300, 530322, 0, ''陆良县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2601, 530323, 530300, 530323, 0, ''师宗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2602, 530324, 530300, 530324, 0, ''罗平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2603, 530325, 530300, 530325, 0, ''富源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2604, 530326, 530300, 530326, 0, ''会泽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2605, 530381, 530300, 530381, 0, ''宣威市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2606, 530400, 530000, 530400, 1, ''玉溪市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2607, 530402, 530400, 530402, 0, ''红塔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2608, 530403, 530400, 530403, 0, ''江川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2609, 530422, 530400, 530422, 0, ''澄江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2610, 530423, 530400, 530423, 0, ''通海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2611, 530424, 530400, 530424, 0, ''华宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2612, 530425, 530400, 530425, 0, ''易门县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2613, 530426, 530400, 530426, 0, ''峨山彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2614, 530427, 530400, 530427, 0, ''新平彝族傣族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2615, 530428, 530400, 530428, 0, ''元江哈尼族彝族傣族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2616, 530500, 530000, 530500, 1, ''保山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2617, 530502, 530500, 530502, 0, ''隆阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2618, 530521, 530500, 530521, 0, ''施甸县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2619, 530523, 530500, 530523, 0, ''龙陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2620, 530524, 530500, 530524, 0, ''昌宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2621, 530581, 530500, 530581, 0, ''腾冲市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2622, 530600, 530000, 530600, 1, ''昭通市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2623, 530602, 530600, 530602, 0, ''昭阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2624, 530621, 530600, 530621, 0, ''鲁甸县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2625, 530622, 530600, 530622, 0, ''巧家县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2626, 530623, 530600, 530623, 0, ''盐津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2627, 530624, 530600, 530624, 0, ''大关县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2628, 530625, 530600, 530625, 0, ''永善县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2629, 530626, 530600, 530626, 0, ''绥江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2630, 530627, 530600, 530627, 0, ''镇雄县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2631, 530628, 530600, 530628, 0, ''彝良县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2632, 530629, 530600, 530629, 0, ''威信县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2633, 530630, 530600, 530630, 0, ''水富县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2634, 530700, 530000, 530700, 1, ''丽江市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2635, 530702, 530700, 530702, 0, ''古城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2636, 530721, 530700, 530721, 0, ''玉龙纳西族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2637, 530722, 530700, 530722, 0, ''永胜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2638, 530723, 530700, 530723, 0, ''华坪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2639, 530724, 530700, 530724, 0, ''宁蒗彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2640, 530800, 530000, 530800, 1, ''普洱市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2641, 530802, 530800, 530802, 0, ''思茅区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2642, 530821, 530800, 530821, 0, ''宁洱哈尼族彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2643, 530822, 530800, 530822, 0, ''墨江哈尼族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2644, 530823, 530800, 530823, 0, ''景东彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2645, 530824, 530800, 530824, 0, ''景谷傣族彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2646, 530825, 530800, 530825, 0, ''镇沅彝族哈尼族拉祜族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2647, 530826, 530800, 530826, 0, ''江城哈尼族彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2648, 530827, 530800, 530827, 0, ''孟连傣族拉祜族佤族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2649, 530828, 530800, 530828, 0, ''澜沧拉祜族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2650, 530829, 530800, 530829, 0, ''西盟佤族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2651, 530900, 530000, 530900, 1, ''临沧市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2652, 530902, 530900, 530902, 0, ''临翔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2653, 530921, 530900, 530921, 0, ''凤庆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2654, 530922, 530900, 530922, 0, ''云县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2655, 530923, 530900, 530923, 0, ''永德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2656, 530924, 530900, 530924, 0, ''镇康县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2657, 530925, 530900, 530925, 0, ''双江拉祜族佤族布朗族傣族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2658, 530926, 530900, 530926, 0, ''耿马傣族佤族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2659, 530927, 530900, 530927, 0, ''沧源佤族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2660, 532300, 530000, 532300, 1, ''楚雄彝族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2661, 532301, 532300, 532301, 0, ''楚雄市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2662, 532322, 532300, 532322, 0, ''双柏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2663, 532323, 532300, 532323, 0, ''牟定县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2664, 532324, 532300, 532324, 0, ''南华县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2665, 532325, 532300, 532325, 0, ''姚安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2666, 532326, 532300, 532326, 0, ''大姚县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2667, 532327, 532300, 532327, 0, ''永仁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2668, 532328, 532300, 532328, 0, ''元谋县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2669, 532329, 532300, 532329, 0, ''武定县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2670, 532331, 532300, 532331, 0, ''禄丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2671, 532500, 530000, 532500, 1, ''红河哈尼族彝族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2672, 532501, 532500, 532501, 0, ''个旧市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2673, 532502, 532500, 532502, 0, ''开远市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2674, 532503, 532500, 532503, 0, ''蒙自市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2675, 532504, 532500, 532504, 0, ''弥勒市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2676, 532523, 532500, 532523, 0, ''屏边苗族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2677, 532524, 532500, 532524, 0, ''建水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2678, 532525, 532500, 532525, 0, ''石屏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2679, 532527, 532500, 532527, 0, ''泸西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2680, 532528, 532500, 532528, 0, ''元阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2681, 532529, 532500, 532529, 0, ''红河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2682, 532530, 532500, 532530, 0, ''金平苗族瑶族傣族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2683, 532531, 532500, 532531, 0, ''绿春县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2684, 532532, 532500, 532532, 0, ''河口瑶族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2685, 532600, 530000, 532600, 1, ''文山壮族苗族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2686, 532601, 532600, 532601, 0, ''文山市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2687, 532622, 532600, 532622, 0, ''砚山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2688, 532623, 532600, 532623, 0, ''西畴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2689, 532624, 532600, 532624, 0, ''麻栗坡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2690, 532625, 532600, 532625, 0, ''马关县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2691, 532626, 532600, 532626, 0, ''丘北县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2692, 532627, 532600, 532627, 0, ''广南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2693, 532628, 532600, 532628, 0, ''富宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2694, 532800, 530000, 532800, 1, ''西双版纳傣族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2695, 532801, 532800, 532801, 0, ''景洪市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2696, 532822, 532800, 532822, 0, ''勐海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2697, 532823, 532800, 532823, 0, ''勐腊县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2698, 532900, 530000, 532900, 1, ''大理白族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2699, 532901, 532900, 532901, 0, ''大理市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2700, 532922, 532900, 532922, 0, ''漾濞彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2701, 532923, 532900, 532923, 0, ''祥云县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2702, 532924, 532900, 532924, 0, ''宾川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2703, 532925, 532900, 532925, 0, ''弥渡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2704, 532926, 532900, 532926, 0, ''南涧彝族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2705, 532927, 532900, 532927, 0, ''巍山彝族回族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2706, 532928, 532900, 532928, 0, ''永平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2707, 532929, 532900, 532929, 0, ''云龙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2708, 532930, 532900, 532930, 0, ''洱源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2709, 532931, 532900, 532931, 0, ''剑川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2710, 532932, 532900, 532932, 0, ''鹤庆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2711, 533100, 530000, 533100, 1, ''德宏傣族景颇族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2712, 533102, 533100, 533102, 0, ''瑞丽市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2713, 533103, 533100, 533103, 0, ''芒市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2714, 533122, 533100, 533122, 0, ''梁河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2715, 533123, 533100, 533123, 0, ''盈江县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2716, 533124, 533100, 533124, 0, ''陇川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2717, 533300, 530000, 533300, 1, ''怒江傈僳族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2718, 533301, 533300, 533301, 0, ''泸水市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2719, 533323, 533300, 533323, 0, ''福贡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2720, 533324, 533300, 533324, 0, ''贡山独龙族怒族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2721, 533325, 533300, 533325, 0, ''兰坪白族普米族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2722, 533400, 530000, 533400, 1, ''迪庆藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2723, 533401, 533400, 533401, 0, ''香格里拉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2724, 533422, 533400, 533422, 0, ''德钦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2725, 533423, 533400, 533423, 0, ''维西傈僳族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2726, 540000, 0, 540000, 1, ''西藏自治区'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2727, 540100, 540000, 540100, 1, ''拉萨市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2728, 540102, 540100, 540102, 0, ''城关区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2729, 540103, 540100, 540103, 0, ''堆龙德庆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2730, 540121, 540100, 540121, 0, ''林周县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2731, 540122, 540100, 540122, 0, ''当雄县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2732, 540123, 540100, 540123, 0, ''尼木县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2733, 540124, 540100, 540124, 0, ''曲水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2734, 540126, 540100, 540126, 0, ''达孜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2735, 540127, 540100, 540127, 0, ''墨竹工卡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2736, 540200, 540000, 540200, 1, ''日喀则市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2737, 540202, 540200, 540202, 0, ''桑珠孜区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2738, 540221, 540200, 540221, 0, ''南木林县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2739, 540222, 540200, 540222, 0, ''江孜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2740, 540223, 540200, 540223, 0, ''定日县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2741, 540224, 540200, 540224, 0, ''萨迦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2742, 540225, 540200, 540225, 0, ''拉孜县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2743, 540226, 540200, 540226, 0, ''昂仁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2744, 540227, 540200, 540227, 0, ''谢通门县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2745, 540228, 540200, 540228, 0, ''白朗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2746, 540229, 540200, 540229, 0, ''仁布县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2747, 540230, 540200, 540230, 0, ''康马县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2748, 540231, 540200, 540231, 0, ''定结县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2749, 540232, 540200, 540232, 0, ''仲巴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2750, 540233, 540200, 540233, 0, ''亚东县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2751, 540234, 540200, 540234, 0, ''吉隆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2752, 540235, 540200, 540235, 0, ''聂拉木县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2753, 540236, 540200, 540236, 0, ''萨嘎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2754, 540237, 540200, 540237, 0, ''岗巴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2755, 540300, 540000, 540300, 1, ''昌都市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2756, 540302, 540300, 540302, 0, ''卡若区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2757, 540321, 540300, 540321, 0, ''江达县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2758, 540322, 540300, 540322, 0, ''贡觉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2759, 540323, 540300, 540323, 0, ''类乌齐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2760, 540324, 540300, 540324, 0, ''丁青县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2761, 540325, 540300, 540325, 0, ''察雅县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2762, 540326, 540300, 540326, 0, ''八宿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2763, 540327, 540300, 540327, 0, ''左贡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2764, 540328, 540300, 540328, 0, ''芒康县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2765, 540329, 540300, 540329, 0, ''洛隆县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2766, 540330, 540300, 540330, 0, ''边坝县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2767, 540400, 540000, 540400, 1, ''林芝市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2768, 540402, 540400, 540402, 0, ''巴宜区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2769, 540421, 540400, 540421, 0, ''工布江达县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2770, 540422, 540400, 540422, 0, ''米林县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2771, 540423, 540400, 540423, 0, ''墨脱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2772, 540424, 540400, 540424, 0, ''波密县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2773, 540425, 540400, 540425, 0, ''察隅县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2774, 540426, 540400, 540426, 0, ''朗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2775, 540500, 540000, 540500, 1, ''山南市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2776, 540502, 540500, 540502, 0, ''乃东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2777, 540521, 540500, 540521, 0, ''扎囊县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2778, 540522, 540500, 540522, 0, ''贡嘎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2779, 540523, 540500, 540523, 0, ''桑日县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2780, 540524, 540500, 540524, 0, ''琼结县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2781, 540525, 540500, 540525, 0, ''曲松县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2782, 540526, 540500, 540526, 0, ''措美县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2783, 540527, 540500, 540527, 0, ''洛扎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2784, 540528, 540500, 540528, 0, ''加查县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2785, 540529, 540500, 540529, 0, ''隆子县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2786, 540530, 540500, 540530, 0, ''错那县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2787, 540531, 540500, 540531, 0, ''浪卡子县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2788, 542400, 540000, 542400, 1, ''那曲地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2789, 542421, 542400, 542421, 0, ''那曲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2790, 542422, 542400, 542422, 0, ''嘉黎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2791, 542423, 542400, 542423, 0, ''比如县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2792, 542424, 542400, 542424, 0, ''聂荣县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2793, 542425, 542400, 542425, 0, ''安多县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2794, 542426, 542400, 542426, 0, ''申扎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2795, 542427, 542400, 542427, 0, ''索县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2796, 542428, 542400, 542428, 0, ''班戈县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2797, 542429, 542400, 542429, 0, ''巴青县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2798, 542430, 542400, 542430, 0, ''尼玛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2799, 542431, 542400, 542431, 0, ''双湖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2800, 542500, 540000, 542500, 1, ''阿里地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2801, 542521, 542500, 542521, 0, ''普兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2802, 542522, 542500, 542522, 0, ''札达县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2803, 542523, 542500, 542523, 0, ''噶尔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2804, 542524, 542500, 542524, 0, ''日土县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2805, 542525, 542500, 542525, 0, ''革吉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2806, 542526, 542500, 542526, 0, ''改则县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2807, 542527, 542500, 542527, 0, ''措勤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2808, 610000, 0, 610000, 1, ''陕西省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2809, 610100, 610000, 610100, 1, ''西安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2810, 610102, 610100, 610102, 0, ''新城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2811, 610103, 610100, 610103, 0, ''碑林区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2812, 610104, 610100, 610104, 0, ''莲湖区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2813, 610111, 610100, 610111, 0, ''灞桥区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2814, 610112, 610100, 610112, 0, ''未央区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2815, 610113, 610100, 610113, 0, ''雁塔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2816, 610114, 610100, 610114, 0, ''阎良区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2817, 610115, 610100, 610115, 0, ''临潼区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2818, 610116, 610100, 610116, 0, ''长安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2819, 610117, 610100, 610117, 0, ''高陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2820, 610118, 610100, 610118, 0, ''鄠邑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2821, 610122, 610100, 610122, 0, ''蓝田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2822, 610124, 610100, 610124, 0, ''周至县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2823, 610200, 610000, 610200, 1, ''铜川市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2824, 610202, 610200, 610202, 0, ''王益区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2825, 610203, 610200, 610203, 0, ''印台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2826, 610204, 610200, 610204, 0, ''耀州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2827, 610222, 610200, 610222, 0, ''宜君县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2828, 610300, 610000, 610300, 1, ''宝鸡市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2829, 610302, 610300, 610302, 0, ''渭滨区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2830, 610303, 610300, 610303, 0, ''金台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2831, 610304, 610300, 610304, 0, ''陈仓区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2832, 610322, 610300, 610322, 0, ''凤翔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2833, 610323, 610300, 610323, 0, ''岐山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2834, 610324, 610300, 610324, 0, ''扶风县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2835, 610326, 610300, 610326, 0, ''眉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2836, 610327, 610300, 610327, 0, ''陇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2837, 610328, 610300, 610328, 0, ''千阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2838, 610329, 610300, 610329, 0, ''麟游县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2839, 610330, 610300, 610330, 0, ''凤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2840, 610331, 610300, 610331, 0, ''太白县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2841, 610400, 610000, 610400, 1, ''咸阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2842, 610402, 610400, 610402, 0, ''秦都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2843, 610403, 610400, 610403, 0, ''杨陵区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2844, 610404, 610400, 610404, 0, ''渭城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2845, 610422, 610400, 610422, 0, ''三原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2846, 610423, 610400, 610423, 0, ''泾阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2847, 610424, 610400, 610424, 0, ''乾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2848, 610425, 610400, 610425, 0, ''礼泉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2849, 610426, 610400, 610426, 0, ''永寿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2850, 610427, 610400, 610427, 0, ''彬县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2851, 610428, 610400, 610428, 0, ''长武县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2852, 610429, 610400, 610429, 0, ''旬邑县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2853, 610430, 610400, 610430, 0, ''淳化县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2854, 610431, 610400, 610431, 0, ''武功县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2855, 610481, 610400, 610481, 0, ''兴平市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2856, 610500, 610000, 610500, 1, ''渭南市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2857, 610502, 610500, 610502, 0, ''临渭区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2858, 610503, 610500, 610503, 0, ''华州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2859, 610522, 610500, 610522, 0, ''潼关县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2860, 610523, 610500, 610523, 0, ''大荔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2861, 610524, 610500, 610524, 0, ''合阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2862, 610525, 610500, 610525, 0, ''澄城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2863, 610526, 610500, 610526, 0, ''蒲城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2864, 610527, 610500, 610527, 0, ''白水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2865, 610528, 610500, 610528, 0, ''富平县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2866, 610581, 610500, 610581, 0, ''韩城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2867, 610582, 610500, 610582, 0, ''华阴市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2868, 610600, 610000, 610600, 1, ''延安市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2869, 610602, 610600, 610602, 0, ''宝塔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2870, 610603, 610600, 610603, 0, ''安塞区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2871, 610621, 610600, 610621, 0, ''延长县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2872, 610622, 610600, 610622, 0, ''延川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2873, 610623, 610600, 610623, 0, ''子长县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2874, 610625, 610600, 610625, 0, ''志丹县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2875, 610626, 610600, 610626, 0, ''吴起县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2876, 610627, 610600, 610627, 0, ''甘泉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2877, 610628, 610600, 610628, 0, ''富县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2878, 610629, 610600, 610629, 0, ''洛川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2879, 610630, 610600, 610630, 0, ''宜川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2880, 610631, 610600, 610631, 0, ''黄龙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2881, 610632, 610600, 610632, 0, ''黄陵县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2882, 610700, 610000, 610700, 1, ''汉中市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2883, 610702, 610700, 610702, 0, ''汉台区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2884, 610721, 610700, 610721, 0, ''南郑区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2885, 610722, 610700, 610722, 0, ''城固县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2886, 610723, 610700, 610723, 0, ''洋县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2887, 610724, 610700, 610724, 0, ''西乡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2888, 610725, 610700, 610725, 0, ''勉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2889, 610726, 610700, 610726, 0, ''宁强县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2890, 610727, 610700, 610727, 0, ''略阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2891, 610728, 610700, 610728, 0, ''镇巴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2892, 610729, 610700, 610729, 0, ''留坝县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2893, 610730, 610700, 610730, 0, ''佛坪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2894, 610800, 610000, 610800, 1, ''榆林市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2895, 610802, 610800, 610802, 0, ''榆阳区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2896, 610803, 610800, 610803, 0, ''横山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2897, 610822, 610800, 610822, 0, ''府谷县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2898, 610824, 610800, 610824, 0, ''靖边县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2899, 610825, 610800, 610825, 0, ''定边县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2900, 610826, 610800, 610826, 0, ''绥德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2901, 610827, 610800, 610827, 0, ''米脂县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2902, 610828, 610800, 610828, 0, ''佳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2903, 610829, 610800, 610829, 0, ''吴堡县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2904, 610830, 610800, 610830, 0, ''清涧县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2905, 610831, 610800, 610831, 0, ''子洲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2906, 610881, 610800, 610881, 0, ''神木市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2907, 610900, 610000, 610900, 1, ''安康市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2908, 610902, 610900, 610902, 0, ''汉滨区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2909, 610921, 610900, 610921, 0, ''汉阴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2910, 610922, 610900, 610922, 0, ''石泉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2911, 610923, 610900, 610923, 0, ''宁陕县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2912, 610924, 610900, 610924, 0, ''紫阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2913, 610925, 610900, 610925, 0, ''岚皋县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2914, 610926, 610900, 610926, 0, ''平利县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2915, 610927, 610900, 610927, 0, ''镇坪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2916, 610928, 610900, 610928, 0, ''旬阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2917, 610929, 610900, 610929, 0, ''白河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2918, 611000, 610000, 611000, 1, ''商洛市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2919, 611002, 611000, 611002, 0, ''商州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2920, 611021, 611000, 611021, 0, ''洛南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2921, 611022, 611000, 611022, 0, ''丹凤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2922, 611023, 611000, 611023, 0, ''商南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2923, 611024, 611000, 611024, 0, ''山阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2924, 611025, 611000, 611025, 0, ''镇安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2925, 611026, 611000, 611026, 0, ''柞水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2926, 620000, 0, 620000, 1, ''甘肃省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2927, 620100, 620000, 620100, 1, ''兰州市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2928, 620102, 620100, 620102, 0, ''城关区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2929, 620103, 620100, 620103, 0, ''七里河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2930, 620104, 620100, 620104, 0, ''西固区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2931, 620105, 620100, 620105, 0, ''安宁区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2932, 620111, 620100, 620111, 0, ''红古区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2933, 620121, 620100, 620121, 0, ''永登县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2934, 620122, 620100, 620122, 0, ''皋兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2935, 620123, 620100, 620123, 0, ''榆中县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2936, 620200, 620000, 620200, 0, ''嘉峪关市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2937, 620300, 620000, 620300, 1, ''金昌市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2938, 620302, 620300, 620302, 0, ''金川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2939, 620321, 620300, 620321, 0, ''永昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2940, 620400, 620000, 620400, 1, ''白银市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2941, 620402, 620400, 620402, 0, ''白银区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2942, 620403, 620400, 620403, 0, ''平川区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2943, 620421, 620400, 620421, 0, ''靖远县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2944, 620422, 620400, 620422, 0, ''会宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2945, 620423, 620400, 620423, 0, ''景泰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2946, 620500, 620000, 620500, 1, ''天水市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2947, 620502, 620500, 620502, 0, ''秦州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2948, 620503, 620500, 620503, 0, ''麦积区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2949, 620521, 620500, 620521, 0, ''清水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2950, 620522, 620500, 620522, 0, ''秦安县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2951, 620523, 620500, 620523, 0, ''甘谷县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2952, 620524, 620500, 620524, 0, ''武山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2953, 620525, 620500, 620525, 0, ''张家川回族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2954, 620600, 620000, 620600, 1, ''武威市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2955, 620602, 620600, 620602, 0, ''凉州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2956, 620621, 620600, 620621, 0, ''民勤县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2957, 620622, 620600, 620622, 0, ''古浪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2958, 620623, 620600, 620623, 0, ''天祝藏族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2959, 620700, 620000, 620700, 1, ''张掖市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2960, 620702, 620700, 620702, 0, ''甘州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2961, 620721, 620700, 620721, 0, ''肃南裕固族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2962, 620722, 620700, 620722, 0, ''民乐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2963, 620723, 620700, 620723, 0, ''临泽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2964, 620724, 620700, 620724, 0, ''高台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2965, 620725, 620700, 620725, 0, ''山丹县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2966, 620800, 620000, 620800, 1, ''平凉市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2967, 620802, 620800, 620802, 0, ''崆峒区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2968, 620821, 620800, 620821, 0, ''泾川县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2969, 620822, 620800, 620822, 0, ''灵台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2970, 620823, 620800, 620823, 0, ''崇信县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2971, 620824, 620800, 620824, 0, ''华亭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2972, 620825, 620800, 620825, 0, ''庄浪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2973, 620826, 620800, 620826, 0, ''静宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2974, 620900, 620000, 620900, 1, ''酒泉市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2975, 620902, 620900, 620902, 0, ''肃州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2976, 620921, 620900, 620921, 0, ''金塔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2977, 620922, 620900, 620922, 0, ''瓜州县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2978, 620923, 620900, 620923, 0, ''肃北蒙古族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2979, 620924, 620900, 620924, 0, ''阿克塞哈萨克族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2980, 620981, 620900, 620981, 0, ''玉门市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2981, 620982, 620900, 620982, 0, ''敦煌市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2982, 621000, 620000, 621000, 1, ''庆阳市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2983, 621002, 621000, 621002, 0, ''西峰区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2984, 621021, 621000, 621021, 0, ''庆城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2985, 621022, 621000, 621022, 0, ''环县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2986, 621023, 621000, 621023, 0, ''华池县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2987, 621024, 621000, 621024, 0, ''合水县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2988, 621025, 621000, 621025, 0, ''正宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2989, 621026, 621000, 621026, 0, ''宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2990, 621027, 621000, 621027, 0, ''镇原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2991, 621100, 620000, 621100, 1, ''定西市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2992, 621102, 621100, 621102, 0, ''安定区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2993, 621121, 621100, 621121, 0, ''通渭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2994, 621122, 621100, 621122, 0, ''陇西县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2995, 621123, 621100, 621123, 0, ''渭源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2996, 621124, 621100, 621124, 0, ''临洮县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2997, 621125, 621100, 621125, 0, ''漳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2998, 621126, 621100, 621126, 0, ''岷县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (2999, 621200, 620000, 621200, 1, ''陇南市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3000, 621202, 621200, 621202, 0, ''武都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3001, 621221, 621200, 621221, 0, ''成县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3002, 621222, 621200, 621222, 0, ''文县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3003, 621223, 621200, 621223, 0, ''宕昌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3004, 621224, 621200, 621224, 0, ''康县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3005, 621225, 621200, 621225, 0, ''西和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3006, 621226, 621200, 621226, 0, ''礼县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3007, 621227, 621200, 621227, 0, ''徽县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3008, 621228, 621200, 621228, 0, ''两当县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3009, 622900, 620000, 622900, 1, ''临夏回族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3010, 622901, 622900, 622901, 0, ''临夏市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3011, 622921, 622900, 622921, 0, ''临夏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3012, 622922, 622900, 622922, 0, ''康乐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3013, 622923, 622900, 622923, 0, ''永靖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3014, 622924, 622900, 622924, 0, ''广河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3015, 622925, 622900, 622925, 0, ''和政县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3016, 622926, 622900, 622926, 0, ''东乡族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3017, 622927, 622900, 622927, 0, ''积石山保安族东乡族撒拉族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3018, 623000, 620000, 623000, 1, ''甘南藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3019, 623001, 623000, 623001, 0, ''合作市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3020, 623021, 623000, 623021, 0, ''临潭县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3021, 623022, 623000, 623022, 0, ''卓尼县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3022, 623023, 623000, 623023, 0, ''舟曲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3023, 623024, 623000, 623024, 0, ''迭部县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3024, 623025, 623000, 623025, 0, ''玛曲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3025, 623026, 623000, 623026, 0, ''碌曲县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3026, 623027, 623000, 623027, 0, ''夏河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3027, 630000, 0, 630000, 1, ''青海省'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3028, 630100, 630000, 630100, 1, ''西宁市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3029, 630102, 630100, 630102, 0, ''城东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3030, 630103, 630100, 630103, 0, ''城中区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3031, 630104, 630100, 630104, 0, ''城西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3032, 630105, 630100, 630105, 0, ''城北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3033, 630121, 630100, 630121, 0, ''大通回族土族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3034, 630122, 630100, 630122, 0, ''湟中县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3035, 630123, 630100, 630123, 0, ''湟源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3036, 630200, 630000, 630200, 1, ''海东市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3037, 630202, 630200, 630202, 0, ''乐都区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3038, 630203, 630200, 630203, 0, ''平安区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3039, 630222, 630200, 630222, 0, ''民和回族土族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3040, 630223, 630200, 630223, 0, ''互助土族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3041, 630224, 630200, 630224, 0, ''化隆回族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3042, 630225, 630200, 630225, 0, ''循化撒拉族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3043, 632200, 630000, 632200, 1, ''海北藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3044, 632221, 632200, 632221, 0, ''门源回族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3045, 632222, 632200, 632222, 0, ''祁连县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3046, 632223, 632200, 632223, 0, ''海晏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3047, 632224, 632200, 632224, 0, ''刚察县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3048, 632300, 630000, 632300, 1, ''黄南藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3049, 632321, 632300, 632321, 0, ''同仁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3050, 632322, 632300, 632322, 0, ''尖扎县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3051, 632323, 632300, 632323, 0, ''泽库县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3052, 632324, 632300, 632324, 0, ''河南蒙古族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3053, 632500, 630000, 632500, 1, ''海南藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3054, 632521, 632500, 632521, 0, ''共和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3055, 632522, 632500, 632522, 0, ''同德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3056, 632523, 632500, 632523, 0, ''贵德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3057, 632524, 632500, 632524, 0, ''兴海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3058, 632525, 632500, 632525, 0, ''贵南县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3059, 632600, 630000, 632600, 1, ''果洛藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3060, 632621, 632600, 632621, 0, ''玛沁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3061, 632622, 632600, 632622, 0, ''班玛县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3062, 632623, 632600, 632623, 0, ''甘德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3063, 632624, 632600, 632624, 0, ''达日县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3064, 632625, 632600, 632625, 0, ''久治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3065, 632626, 632600, 632626, 0, ''玛多县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3066, 632700, 630000, 632700, 1, ''玉树藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3067, 632701, 632700, 632701, 0, ''玉树市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3068, 632722, 632700, 632722, 0, ''杂多县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3069, 632723, 632700, 632723, 0, ''称多县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3070, 632724, 632700, 632724, 0, ''治多县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3071, 632725, 632700, 632725, 0, ''囊谦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3072, 632726, 632700, 632726, 0, ''曲麻莱县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3073, 632800, 630000, 632800, 1, ''海西蒙古族藏族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3074, 632801, 632800, 632801, 0, ''格尔木市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3075, 632802, 632800, 632802, 0, ''德令哈市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3076, 632821, 632800, 632821, 0, ''乌兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3077, 632822, 632800, 632822, 0, ''都兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3078, 632823, 632800, 632823, 0, ''天峻县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3079, 632825, 632800, 632825, 0, ''海西蒙古族藏族自治州直辖'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3080, 640000, 0, 640000, 1, ''宁夏回族自治区'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3081, 640100, 640000, 640100, 1, ''银川市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3082, 640104, 640100, 640104, 0, ''兴庆区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3083, 640105, 640100, 640105, 0, ''西夏区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3084, 640106, 640100, 640106, 0, ''金凤区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3085, 640121, 640100, 640121, 0, ''永宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3086, 640122, 640100, 640122, 0, ''贺兰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3087, 640181, 640100, 640181, 0, ''灵武市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3088, 640200, 640000, 640200, 1, ''石嘴山市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3089, 640202, 640200, 640202, 0, ''大武口区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3090, 640205, 640200, 640205, 0, ''惠农区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3091, 640221, 640200, 640221, 0, ''平罗县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3092, 640300, 640000, 640300, 1, ''吴忠市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3093, 640302, 640300, 640302, 0, ''利通区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3094, 640303, 640300, 640303, 0, ''红寺堡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3095, 640323, 640300, 640323, 0, ''盐池县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3096, 640324, 640300, 640324, 0, ''同心县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3097, 640381, 640300, 640381, 0, ''青铜峡市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3098, 640400, 640000, 640400, 1, ''固原市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3099, 640402, 640400, 640402, 0, ''原州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3100, 640422, 640400, 640422, 0, ''西吉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3101, 640423, 640400, 640423, 0, ''隆德县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3102, 640424, 640400, 640424, 0, ''泾源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3103, 640425, 640400, 640425, 0, ''彭阳县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3104, 640500, 640000, 640500, 1, ''中卫市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3105, 640502, 640500, 640502, 0, ''沙坡头区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3106, 640521, 640500, 640521, 0, ''中宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3107, 640522, 640500, 640522, 0, ''海原县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3108, 650000, 0, 650000, 1, ''新疆维吾尔自治区'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3109, 650100, 650000, 650100, 1, ''乌鲁木齐市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3110, 650102, 650100, 650102, 0, ''天山区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3111, 650103, 650100, 650103, 0, ''沙依巴克区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3112, 650104, 650100, 650104, 0, ''新市区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3113, 650105, 650100, 650105, 0, ''水磨沟区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3114, 650106, 650100, 650106, 0, ''头屯河区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3115, 650107, 650100, 650107, 0, ''达坂城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3116, 650109, 650100, 650109, 0, ''米东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3117, 650121, 650100, 650121, 0, ''乌鲁木齐县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3118, 650200, 650000, 650200, 1, ''克拉玛依市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3119, 650202, 650200, 650202, 0, ''独山子区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3120, 650203, 650200, 650203, 0, ''克拉玛依区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3121, 650204, 650200, 650204, 0, ''白碱滩区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3122, 650205, 650200, 650205, 0, ''乌尔禾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3123, 650400, 650000, 650400, 1, ''吐鲁番市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3124, 650402, 650400, 650402, 0, ''高昌区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3125, 650421, 650400, 650421, 0, ''鄯善县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3126, 650422, 650400, 650422, 0, ''托克逊县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3127, 650500, 650000, 650500, 1, ''哈密市'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3128, 650502, 650500, 650502, 0, ''伊州区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3129, 650521, 650500, 650521, 0, ''巴里坤哈萨克自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3130, 650522, 650500, 650522, 0, ''伊吾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3131, 652300, 650000, 652300, 1, ''昌吉回族自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3132, 652301, 652300, 652301, 0, ''昌吉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3133, 652302, 652300, 652302, 0, ''阜康市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3134, 652323, 652300, 652323, 0, ''呼图壁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3135, 652324, 652300, 652324, 0, ''玛纳斯县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3136, 652325, 652300, 652325, 0, ''奇台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3137, 652327, 652300, 652327, 0, ''吉木萨尔县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3138, 652328, 652300, 652328, 0, ''木垒哈萨克自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3139, 652700, 650000, 652700, 1, ''博尔塔拉蒙古自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3140, 652701, 652700, 652701, 0, ''博乐市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3141, 652702, 652700, 652702, 0, ''阿拉山口市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3142, 652722, 652700, 652722, 0, ''精河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3143, 652723, 652700, 652723, 0, ''温泉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3144, 652800, 650000, 652800, 1, ''巴音郭楞蒙古自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3145, 652801, 652800, 652801, 0, ''库尔勒市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3146, 652822, 652800, 652822, 0, ''轮台县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3147, 652823, 652800, 652823, 0, ''尉犁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3148, 652824, 652800, 652824, 0, ''若羌县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3149, 652825, 652800, 652825, 0, ''且末县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3150, 652826, 652800, 652826, 0, ''焉耆回族自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3151, 652827, 652800, 652827, 0, ''和静县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3152, 652828, 652800, 652828, 0, ''和硕县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3153, 652829, 652800, 652829, 0, ''博湖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3154, 652900, 650000, 652900, 1, ''阿克苏地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3155, 652901, 652900, 652901, 0, ''阿克苏市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3156, 652922, 652900, 652922, 0, ''温宿县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3157, 652923, 652900, 652923, 0, ''库车县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3158, 652924, 652900, 652924, 0, ''沙雅县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3159, 652925, 652900, 652925, 0, ''新和县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3160, 652926, 652900, 652926, 0, ''拜城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3161, 652927, 652900, 652927, 0, ''乌什县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3162, 652928, 652900, 652928, 0, ''阿瓦提县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3163, 652929, 652900, 652929, 0, ''柯坪县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3164, 653000, 650000, 653000, 1, ''克孜勒苏柯尔克孜自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3165, 653001, 653000, 653001, 0, ''阿图什市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3166, 653022, 653000, 653022, 0, ''阿克陶县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3167, 653023, 653000, 653023, 0, ''阿合奇县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3168, 653024, 653000, 653024, 0, ''乌恰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3169, 653100, 650000, 653100, 1, ''喀什地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3170, 653101, 653100, 653101, 0, ''喀什市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3171, 653121, 653100, 653121, 0, ''疏附县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3172, 653122, 653100, 653122, 0, ''疏勒县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3173, 653123, 653100, 653123, 0, ''英吉沙县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3174, 653124, 653100, 653124, 0, ''泽普县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3175, 653125, 653100, 653125, 0, ''莎车县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3176, 653126, 653100, 653126, 0, ''叶城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3177, 653127, 653100, 653127, 0, ''麦盖提县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3178, 653128, 653100, 653128, 0, ''岳普湖县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3179, 653129, 653100, 653129, 0, ''伽师县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3180, 653130, 653100, 653130, 0, ''巴楚县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3181, 653131, 653100, 653131, 0, ''塔什库尔干塔吉克自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3182, 653200, 650000, 653200, 1, ''和田地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3183, 653201, 653200, 653201, 0, ''和田市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3184, 653221, 653200, 653221, 0, ''和田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3185, 653222, 653200, 653222, 0, ''墨玉县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3186, 653223, 653200, 653223, 0, ''皮山县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3187, 653224, 653200, 653224, 0, ''洛浦县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3188, 653225, 653200, 653225, 0, ''策勒县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3189, 653226, 653200, 653226, 0, ''于田县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3190, 653227, 653200, 653227, 0, ''民丰县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3191, 654000, 650000, 654000, 1, ''伊犁哈萨克自治州'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3192, 654002, 654000, 654002, 0, ''伊宁市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3193, 654003, 654000, 654003, 0, ''奎屯市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3194, 654004, 654000, 654004, 0, ''霍尔果斯市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3195, 654021, 654000, 654021, 0, ''伊宁县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3196, 654022, 654000, 654022, 0, ''察布查尔锡伯自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3197, 654023, 654000, 654023, 0, ''霍城县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3198, 654024, 654000, 654024, 0, ''巩留县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3199, 654025, 654000, 654025, 0, ''新源县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3200, 654026, 654000, 654026, 0, ''昭苏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3201, 654027, 654000, 654027, 0, ''特克斯县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3202, 654028, 654000, 654028, 0, ''尼勒克县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3203, 654200, 650000, 654200, 1, ''塔城地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3204, 654201, 654200, 654201, 0, ''塔城市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3205, 654202, 654200, 654202, 0, ''乌苏市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3206, 654221, 654200, 654221, 0, ''额敏县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3207, 654223, 654200, 654223, 0, ''沙湾县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3208, 654224, 654200, 654224, 0, ''托里县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3209, 654225, 654200, 654225, 0, ''裕民县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3210, 654226, 654200, 654226, 0, ''和布克赛尔蒙古自治县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3211, 654300, 650000, 654300, 1, ''阿勒泰地区'', ''3'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3212, 654301, 654300, 654301, 0, ''阿勒泰市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3213, 654321, 654300, 654321, 0, ''布尔津县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3214, 654322, 654300, 654322, 0, ''富蕴县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3215, 654323, 654300, 654323, 0, ''福海县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3216, 654324, 654300, 654324, 0, ''哈巴河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3217, 654325, 654300, 654325, 0, ''青河县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3218, 654326, 654300, 654326, 0, ''吉木乃县'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3219, 659001, 650000, 659001, 0, ''石河子市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3220, 659002, 650000, 659002, 0, ''阿拉尔市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3221, 659003, 650000, 659003, 0, ''图木舒克市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3222, 659004, 650000, 659004, 0, ''五家渠市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3223, 659005, 650000, 659005, 0, ''北屯市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3224, 659006, 650000, 659006, 0, ''铁门关市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3225, 659007, 650000, 659007, 0, ''双河市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3226, 659008, 650000, 659008, 0, ''可克达拉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3227, 659009, 650000, 659009, 0, ''昆玉市'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3228, 710000, 0, 710000, 0, ''台湾省'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3229, 810000, 0, 810000, 1, ''香港特别行政区'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3230, 810001, 810000, 810001, 0, ''中西区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3231, 810002, 810000, 810002, 0, ''湾仔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3232, 810003, 810000, 810003, 0, ''东区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3233, 810004, 810000, 810004, 0, ''南区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3234, 810005, 810000, 810005, 0, ''油尖旺区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3235, 810006, 810000, 810006, 0, ''深水埗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3236, 810007, 810000, 810007, 0, ''九龙城区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3237, 810008, 810000, 810008, 0, ''黄大仙区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3238, 810009, 810000, 810009, 0, ''观塘区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3239, 810010, 810000, 810010, 0, ''荃湾区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3240, 810011, 810000, 810011, 0, ''屯门区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3241, 810012, 810000, 810012, 0, ''元朗区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3242, 810013, 810000, 810013, 0, ''北区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3243, 810014, 810000, 810014, 0, ''大埔区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3244, 810015, 810000, 810015, 0, ''西贡区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3245, 810016, 810000, 810016, 0, ''沙田区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3246, 810017, 810000, 810017, 0, ''葵青区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3247, 810018, 810000, 810018, 0, ''离岛区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3248, 820000, 0, 820000, 1, ''澳门特别行政区'', ''2'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3249, 820001, 820000, 820001, 0, ''花地玛堂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3250, 820002, 820000, 820002, 0, ''花王堂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3251, 820003, 820000, 820003, 0, ''望德堂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3252, 820004, 820000, 820004, 0, ''大堂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3253, 820005, 820000, 820005, 0, ''风顺堂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3254, 820006, 820000, 820006, 0, ''嘉模堂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3255, 820007, 820000, 820007, 0, ''路凼填海区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);
INSERT INTO `mk_area` VALUES (3256, 820008, 820000, 820008, 0, ''圣方济各堂区'', ''4'', NULL, NULL, NULL, NULL, 0, 0, NULL);

-- ----------------------------
-- Table structure for mk_client
-- ----------------------------
DROP TABLE IF EXISTS `mk_client`;
CREATE TABLE `mk_client`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `client_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''客户端id'',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''客户端密钥'',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''资源集合'',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''授权范围'',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''授权类型'',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''回调地址'',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''权限'',
  `access_token_validity` int(11) NOT NULL COMMENT ''令牌过期秒数'',
  `refresh_token_validity` int(11) NOT NULL COMMENT ''刷新令牌过期秒数'',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''附加说明'',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''自动授权'',
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NOT NULL COMMENT ''状态'',
  `is_deleted` int(2) NOT NULL COMMENT ''是否已删除'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''客户端表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_client
-- ----------------------------
INSERT INTO `mk_client` VALUES (2, ''systemId'', ''system_secret'', '''', ''all'', ''refresh_token,password,authorization_code'', ''http://localhost:9527'', '''', 3600, 604800, ''前后端面分离时，前端接入的authorcode'', '''', 1, ''2019-03-24 10:42:29'', 1, ''2019-12-12 20:44:29'', 1, 0);

-- ----------------------------
-- Table structure for mk_code
-- ----------------------------
DROP TABLE IF EXISTS `mk_code`;
CREATE TABLE `mk_code`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `datasource_id` bigint(32) NULL DEFAULT NULL COMMENT ''数据源主键'',
  `service_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务名称'',
  `code_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''模块名称'',
  `table_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''表名'',
  `table_prefix` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''表前缀'',
  `pk_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''主键名'',
  `package_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''后端包名'',
  `base_mode` int(2) NULL DEFAULT NULL COMMENT ''基础业务模式'',
  `wrap_mode` int(2) NULL DEFAULT NULL COMMENT ''包装器模式'',
  `api_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''后端路径'',
  `web_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''前端路径'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''代码生成表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_code
-- ----------------------------
INSERT INTO `mk_code` VALUES (1, 1, ''springcloud-demo'', ''通知公告'', ''mk_notice'', ''mk_'', ''id'', ''org.springclouddev.desktop'', 2, 2, ''springcloud-ops\\springcloud-develop'', ''vue-element-admin'', 1);
INSERT INTO `mk_code` VALUES (9, 1, ''springcloud-develop'', ''数据库管理'', ''db_db_instance'', ''db_'', ''id'', ''org.springclouddev.develop'', 2, 2, ''D:\\Develop\\WorkSpace\\Git\\\\springcloud-ops\\springcloud-develop'', ''D:\\Develop\\WorkSpace\\Git\\Sword'', 1);
INSERT INTO `mk_code` VALUES (10, 1, ''springcloud-develop'', ''表元数据管理'', ''db_table_info'', ''db_'', ''id'', ''org.springclouddev.develop'', 2, 2, ''D:\\Develop\\WorkSpace\\Git\\\\springcloud-ops\\springcloud-develop'', ''D:\\Develop\\WorkSpace\\Git\\Sword'', 1);
INSERT INTO `mk_code` VALUES (11, 1, ''springcloud-system'', ''行政地区模块'', ''mk_area'', ''mk_'', ''id'', ''org.springclouddev.system'', 2, 2, ''springcloud-ops\\springcloud-develop'', ''vue-element-admin'', 1);
INSERT INTO `mk_code` VALUES (1217074120318054401, 1217070670268141570, ''springcloud-integral'', ''Integral'', ''tbl_act_prm,tbl_act_prm_cate,tbl_act_rule_info,tbl_batch_record,tbl_client_act_rel,tbl_client_black_list,tbl_client_group,tbl_cust_integral_detail,tbl_data_dic_prm,tbl_grp_cls,tbl_integral_act,tbl_latest_act_code,tbl_marketing_act,tbl_rule_exp,tbl_rule_info'', ''tbl_'', ''id'', ''org.springclouddev.integral'', 2, 2, ''/src'', ''/ui'', 1);
INSERT INTO `mk_code` VALUES (1217085073520652289, 1217070670268141570, ''spirngcloud-integral'', ''integral'', ''tbl_account_integral_detail,tbl_act_prm,tbl_act_prm_cate,tbl_act_rule_info,tbl_batch_record,tbl_client_act_rel,tbl_client_black_list,tbl_client_group,tbl_cust_integral_detail,tbl_data_dic_prm,tbl_grp_cls,tbl_grp_list,tbl_integral_act,tbl_integral_adjust,tbl_integral_adjust_action,tbl_integral_status,tbl_integral_status_action,tbl_latest_act_code,tbl_marketing_act,tbl_rule_exp,tbl_rule_info'', ''tbl_'', ''id'', ''org.springclouddev.integral'', 2, 2, ''src'', ''ui'', 0);
INSERT INTO `mk_code` VALUES (1247356225719382018, 1, ''springcloud-mock-server'', ''mockserver'', ''mk_mock_http,mk_mock_web_site'', ''mk_'', ''id'', ''org.springclouddev.mockserver'', 2, 2, ''code'', ''view'', 0);

-- ----------------------------
-- Table structure for mk_datasource
-- ----------------------------
DROP TABLE IF EXISTS `mk_datasource`;
CREATE TABLE `mk_datasource`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''名称'',
  `driver_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''驱动类'',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''连接地址'',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户名'',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''密码'',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''备注'',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT ''创建部门'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NULL DEFAULT NULL COMMENT ''状态'',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT ''是否已删除'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''数据源配置表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_datasource
-- ----------------------------
INSERT INTO `mk_datasource` VALUES (1, ''mysql'', ''com.mysql.cj.jdbc.Driver'', ''jdbc:mysql://localhost:3306/mk?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroTIMESTAMP Behavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true'', ''root'', ''1qaz2wsx'', ''mysql'', 1, 1, ''2019-08-14 11:43:06'', 1, ''2019-12-16 22:28:10'', 1, 0);
INSERT INTO `mk_datasource` VALUES (2, ''postgresql'', ''org.postgresql.Driver'', ''jdbc:postgresql://127.0.0.1:5432/blade'', ''postgres'', ''123456'', ''postgresql'', 1, 1, ''2019-08-14 11:43:41'', 1, ''2019-08-14 11:43:41'', 1, 0);
INSERT INTO `mk_datasource` VALUES (3, ''oracle'', ''oracle.jdbc.OracleDriver'', ''jdbc:oracle:thin:@127.0.0.1:49161:orcl'', ''root'', ''root'', ''oracle'', 1, 1, ''2019-08-14 11:44:03'', 1, ''2019-08-14 11:44:03'', 1, 0);
INSERT INTO `mk_datasource` VALUES (1217070670268141570, ''mysql_integral_all'', ''com.mysql.cj.jdbc.Driver'', ''jdbc:mysql://localhost:3306/integral_all?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroTIMESTAMP Behavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true'', ''root'', ''1qaz2wsx'', ''mysql,integral_all.'', 1, NULL, ''2020-01-14 21:07:15'', NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for mk_dept
-- ----------------------------
DROP TABLE IF EXISTS `mk_dept`;
CREATE TABLE `mk_dept`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  `parent_id` bigint(32) NULL DEFAULT 0 COMMENT ''父主键'',
  `dept_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''部门名'',
  `full_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''部门全称'',
  `sort` int(11) NULL DEFAULT NULL COMMENT ''排序'',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''备注'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  `is_leaf` int(2) NOT NULL DEFAULT 0 COMMENT ''是否是叶子节点，0是，1不是'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''部门表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_dept
-- ----------------------------
INSERT INTO `mk_dept` VALUES (1, ''000000'', 0, ''软竹科技'', ''上海软件竹科技有限公司'', 1, ''2222'', 0, 1);
INSERT INTO `mk_dept` VALUES (2, ''000000'', 1, ''北京软竹'', ''北京软竹科技有限公司'', 1, '''', 0, 1);
INSERT INTO `mk_dept` VALUES (3, ''000000'', 1, ''深圳软竹'', ''深圳软竹科技有限公司'', 1, ''32342'', 0, 1);

-- ----------------------------
-- Table structure for mk_dict
-- ----------------------------
DROP TABLE IF EXISTS `mk_dict`;
CREATE TABLE `mk_dict`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `parent_id` bigint(32) NULL DEFAULT 0 COMMENT ''父主键'',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典码'',
  `dict_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典值'',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典名称'',
  `sort` int(11) NULL DEFAULT NULL COMMENT ''排序'',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典备注'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  `is_leaf` int(2) NOT NULL DEFAULT 0 COMMENT ''是否是叶子节点，0是，1不是'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''字典表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_dict
-- ----------------------------
INSERT INTO `mk_dict` VALUES (1, 0, ''sex'', ''-1'', ''性别'', 1, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (2, 1, ''sex'', ''1'', ''男'', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (3, 1, ''sex'', ''2'', ''女'', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (4, 0, ''notice'', ''-1'', ''通知类型'', 2, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (5, 4, ''notice'', ''1'', ''发布通知'', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (6, 4, ''notice'', ''2'', ''批转通知'', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (7, 4, ''notice'', ''3'', ''转发通知'', 3, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (8, 4, ''notice'', ''4'', ''指示通知'', 4, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (9, 4, ''notice'', ''5'', ''任免通知'', 5, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (10, 4, ''notice'', ''6'', ''事务通知'', 6, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (11, 0, ''menu_category'', ''-1'', ''菜单类型'', 3, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (12, 11, ''menu_category'', ''1'', ''菜单'', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (13, 11, ''menu_category'', ''2'', ''按钮'', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (14, 0, ''button_func'', ''-1'', ''按钮功能'', 4, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (15, 14, ''button_func'', ''1'', ''工具栏'', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (16, 14, ''button_func'', ''2'', ''操作栏'', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (17, 14, ''button_func'', ''3'', ''工具操作栏'', 3, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (18, 0, ''yes_no'', ''-1'', ''是否'', 5, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (19, 18, ''yes_no'', ''1'', ''否'', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (20, 18, ''yes_no'', ''2'', ''是'', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (22, 0, ''validDate'', ''-1'', ''有效期'', 7, ''产品的有效期选项'', 0, 1);
INSERT INTO `mk_dict` VALUES (23, 22, ''validDate'', ''1'', ''一年'', 1, ''一年有效期'', 0, 0);
INSERT INTO `mk_dict` VALUES (24, 22, ''validDate'', ''2'', ''半年'', 2, ''半年有效期'', 0, 0);
INSERT INTO `mk_dict` VALUES (26, 0, ''tableinfo'', ''-1'', ''表元数据类型'', 7, ''表元数据类型'', 0, 1);
INSERT INTO `mk_dict` VALUES (27, 26, ''tableinfo'', ''1'', ''数据表'', 1, ''数据表'', 0, 0);
INSERT INTO `mk_dict` VALUES (28, 26, ''tableinfo'', ''2'', ''表字段'', 2, ''表字段'', 0, 0);
INSERT INTO `mk_dict` VALUES (32, 0, ''area_type'', ''-1'', '' 区域类型'', 1, '' 区域类型'', 0, 1);
INSERT INTO `mk_dict` VALUES (33, 32, ''area_type'', ''1'', ''国家'', 1, ''国家'', 0, 0);
INSERT INTO `mk_dict` VALUES (34, 32, ''area_type'', ''2'', ''省份直辖市'', 2, ''省份直辖市'', 0, 0);
INSERT INTO `mk_dict` VALUES (35, 32, ''area_type'', ''3'', ''地市'', 3, ''地市'', 0, 0);
INSERT INTO `mk_dict` VALUES (36, 32, ''area_type'', ''4'', ''区县'', 4, ''区县'', 0, 0);

-- ----------------------------
-- Table structure for mk_log_api
-- ----------------------------
DROP TABLE IF EXISTS `mk_log_api`;
CREATE TABLE `mk_log_api`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务ID'',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务器名'',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务器IP地址'',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务器环境'',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''1'' COMMENT ''日志类型'',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '''' COMMENT ''日志标题'',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''操作方式'',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''请求URI'',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户代理'',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''操作IP地址'',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''方法类'',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''方法名'',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''操作提交的数据'',
  `time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''执行时间'',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建者'',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT ''创建时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''接口日志表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mk_log_error
-- ----------------------------
DROP TABLE IF EXISTS `mk_log_error`;
CREATE TABLE `mk_log_error`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务ID'',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务器名'',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务器IP地址'',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''系统环境'',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''操作方式'',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''请求URI'',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户代理'',
  `stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''堆栈'',
  `exception_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''异常名'',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''异常信息'',
  `line_number` int(11) NULL DEFAULT NULL COMMENT ''错误行数'',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''操作IP地址'',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''方法类'',
  `file_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件名'',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''方法名'',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''操作提交的数据'',
  `time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''执行时间'',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建者'',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT ''创建时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''错误日志表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_log_error
-- ----------------------------

-- ----------------------------
-- Table structure for mk_log_usual
-- ----------------------------
DROP TABLE IF EXISTS `mk_log_usual`;
CREATE TABLE `mk_log_usual`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务ID'',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务器名'',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''服务器IP地址'',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''系统环境'',
  `log_level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''日志级别'',
  `log_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''日志业务id'',
  `log_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''日志数据'',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''操作方式'',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''请求URI'',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''操作IP地址'',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''方法类'',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''方法名'',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户代理'',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''操作提交的数据'',
  `time` timestamp(0) NULL DEFAULT NULL COMMENT ''执行时间'',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建者'',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT ''创建时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''通用日志表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- -- Records of mk_log_usual
-- -- ----------------------------

-- ----------------------------
-- Table structure for mk_menu
-- ----------------------------
DROP TABLE IF EXISTS `mk_menu`;
CREATE TABLE `mk_menu`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `parent_id` bigint(32) NULL DEFAULT 0 COMMENT ''父级菜单'',
  `tenant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''租户ID'',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单编号'',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单名称'',
  `alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单别名'',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''请求地址'',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单资源'',
  `sort` int(2) NULL DEFAULT NULL COMMENT ''排序'',
  `category` int(2) NULL DEFAULT NULL COMMENT ''菜单类型'',
  `action` int(2) NULL DEFAULT 0 COMMENT ''操作按钮类型'',
  `is_open` int(2) NULL DEFAULT 1 COMMENT ''是否打开新页面'',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''备注'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  `is_leaf` int(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT ''是否是叶子节点，0是，1不是'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''菜单表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_menu
-- ----------------------------
INSERT INTO `mk_menu` VALUES (1, 0, ''000000'', ''desk'', ''工作台'', ''menu.desk'', ''/desk'', ''table'', 1, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (2, 1, ''000000'', ''notice'', ''通知公告'', ''menu.desk.notice'', ''/desk/notice'', ''email'', 1, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (3, 0, ''000000'', ''system'', ''系统管理'', ''menu.system'', ''/system'', ''list'', 2, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (4, 3, ''000000'', ''user'', ''用户管理'', ''menu.system.user'', ''/system/user'', ''user'', 1, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (5, 3, ''000000'', ''dept'', ''部门管理'', ''menu.system.dept'', ''/system/dept'', ''peoples'', 2, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (6, 3, ''000000'', ''dict'', ''字典管理'', ''menu.system.dict'', ''/system/dict'', ''excel'', 3, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (7, 3, ''000000'', ''menu'', ''菜单管理'', ''menu.system.menu'', ''/system/menu'', ''tree-table'', 4, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (8, 3, ''000000'', ''role'', ''角色管理'', ''menu'', ''/permission/role'', ''people'', 5, 1, 0, 1, '''', 1, 1);
INSERT INTO `mk_menu` VALUES (9, 3, ''000000'', ''params'', ''参数管理'', ''menu.system.param'', ''/system/param'', ''306'', 6, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (10, 0, ''000000'', ''monitor'', ''系统监控'', ''menu.monitor'', ''/monitor'', ''international'', 6, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (11, 10, ''000000'', ''doc'', ''接口文档'', ''menu.api.doc'', ''http://localhost/doc.html'', ''excel'', 1, 1, 0, 2, '''', 0, 0);
INSERT INTO `mk_menu` VALUES (12, 10, ''000000'', ''servicemesh'', ''服务治理'', ''menu.service.mesh'', ''http://localhost:7002'', ''component'', 2, 1, 0, 2, '''', 0, 0);
INSERT INTO `mk_menu` VALUES (13, 10, ''000000'', ''log'', ''日志管理'', ''menu.monitor.log'', ''/monitor/log'', ''form'', 3, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (14, 13, ''000000'', ''log_usual'', ''通用日志'', ''menu.monitor.log.usual'', ''/monitor/log/usual'', ''attention'', 1, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (15, 13, ''000000'', ''log_api'', ''接口日志'', ''menu.monitor.log.api'', ''/monitor/log/api'', ''nested'', 2, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (16, 13, ''000000'', ''log_error'', ''错误日志'', ''menu.monitor.log.error'', ''/monitor/log/error'', ''404'', 3, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (17, 0, ''000000'', ''tool'', ''研发工具'', ''menu.tool'', ''/tool'', ''eye-open'', 7, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (18, 17, ''000000'', ''codes'', ''代码生成'', ''menu.tool.code'', ''/tool/code'', ''rainm'', 10, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (19, 2, ''000000'', ''notice_add'', ''新增'', ''desk.notice.add'', ''/desk/notice/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (20, 2, ''000000'', ''notice_edit'', ''修改'', ''desk.notice.edit'', ''/desk/notice/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (21, 2, ''000000'', ''notice_delete'', ''删除'', ''api.springcloud-desk.notice.remove'', ''/api/springcloud-desk/notice/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (22, 2, ''000000'', ''notice_view'', ''查看'', ''desk.notice.view'', ''/desk/notice/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (23, 4, ''000000'', ''user_add'', ''新增'', ''system.user.add'', ''/system/user/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (24, 4, ''000000'', ''user_edit'', ''修改'', ''system.user.edit'', ''/system/user/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (25, 4, ''000000'', ''user_delete'', ''删除'', ''api.springcloud-user.remove'', ''/api/springcloud-user/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (26, 4, ''000000'', ''user_role'', ''角色配置'', NULL, NULL, ''user-add'', 4, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (27, 4, ''000000'', ''user_reset'', ''密码重置'', ''api.springcloud-user.reset-password'', ''/api/springcloud-user/reset-password'', ''retweet'', 5, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (28, 4, ''000000'', ''user_view'', ''查看'', ''system.user.view'', ''/system/user/view'', ''file-text'', 6, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (29, 5, ''000000'', ''dept_add'', ''新增'', ''system.dept.add'', ''/system/dept/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (30, 5, ''000000'', ''dept_edit'', ''修改'', ''system.dept.edit'', ''/system/dept/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (31, 5, ''000000'', ''dept_delete'', ''删除'', ''api.springcloud-system.dept.remove'', ''/api/springcloud-system/dept/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (32, 5, ''000000'', ''dept_view'', ''查看'', ''system.dept.view'', ''/system/dept/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (33, 6, ''000000'', ''dict_add'', ''新增'', ''system.dict.add'', ''/system/dict/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (34, 6, ''000000'', ''dict_edit'', ''修改'', ''system.dict.edit'', ''/system/dict/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (35, 6, ''000000'', ''dict_delete'', ''删除'', ''api.springcloud-system.dict.remove'', ''/api/springcloud-system/dict/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (36, 6, ''000000'', ''dict_view'', ''查看'', ''system.dict.view'', ''/system/dict/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (37, 7, ''000000'', ''menu_add'', ''新增'', ''system.menu.add'', ''/system/menu/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (38, 7, ''000000'', ''menu_edit'', ''修改'', ''system.menu.edit'', ''/system/menu/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (39, 7, ''000000'', ''menu_delete'', ''删除'', ''api.springcloud-system.menu.remove'', ''/api/springcloud-system/menu/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (40, 7, ''000000'', ''menu_view'', ''查看'', ''system.menu.view'', ''/system/menu/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (41, 8, ''000000'', ''role_add'', ''新增'', ''system.role.add'', ''/system/role/add'', ''plus'', 1, 2, 1, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (42, 8, ''000000'', ''role_edit'', ''修改'', ''system.role.edit'', ''/system/role/edit'', ''form'', 2, 2, 2, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (43, 8, ''000000'', ''role_delete'', ''删除'', ''api.springcloud-system.role.remove'', ''/api/springcloud-system/role/remove'', ''delete'', 3, 2, 3, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (44, 8, ''000000'', ''role_view'', ''查看'', ''system.role.view'', ''/system/role/view'', ''file-text'', 4, 2, 2, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (45, 9, ''000000'', ''param_add'', ''新增'', ''system.param.add'', ''/system/param/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (46, 9, ''000000'', ''param_edit'', ''修改'', ''system.param.edit'', ''/system/param/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (47, 9, ''000000'', ''param_delete'', ''删除'', ''api.springcloud-system.param.remove'', ''/api/springcloud-system/param/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (48, 9, ''000000'', ''param_view'', ''查看'', ''system.param.view'', ''/system/param/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (49, 14, ''000000'', ''log_usual_view'', ''查看'', ''monitor.log.usual.view'', ''/monitor/log/usual/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (50, 15, ''000000'', ''log_api_view'', ''查看'', ''monitor.log.api.view'', ''/monitor/log/api/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (51, 16, ''000000'', ''log_error_view'', ''查看'', ''monitor.log.error.view'', ''/monitor/log/error/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (52, 18, ''000000'', ''code_add'', ''新增'', ''tool.code.add'', ''/tool/code/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (53, 18, ''000000'', ''code_edit'', ''修改'', ''tool.code.edit'', ''/tool/code/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (54, 18, ''000000'', ''code_delete'', ''删除'', ''api.springcloud-system.code.remove'', ''/api/springcloud-system/code/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (55, 18, ''000000'', ''code_view'', ''查看'', ''tool.code.view'', ''/tool/code/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (56, 3, ''000000'', ''tenant'', ''租户管理'', ''menu.system.tenant'', ''/system/tenant'', ''tree'', 7, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (57, 56, ''000000'', ''tenant_add'', ''新增'', ''system.tenant.add'', ''/system/tenant/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (58, 56, ''000000'', ''tenant_edit'', ''修改'', ''system.tenant.edit'', ''/system/tenant/edit'', ''form'', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (59, 56, ''000000'', ''tenant_delete'', ''删除'', ''api.springcloud-system.tenant.remove'', ''/api/springcloud-system/tenant/remove'', ''delete'', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (60, 56, ''000000'', ''tenant_view'', ''查看'', ''system.tenant.view'', ''/system/tenant/view'', ''file-text'', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (61, 3, ''000000'', ''client'', ''应用管理'', ''menu.system.client'', ''/system/client'', ''exit-fullscreen'', 8, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (62, 61, ''000000'', ''client_add'', ''新增'', ''system.client.add'', ''/system/client/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (63, 61, ''000000'', ''client_edit'', ''修改'', ''system.client.edit'', ''/system/client/edit'', ''form'', 2, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (64, 61, ''000000'', ''client_delete'', ''删除'', ''api.springcloud-system.client.remove'', ''/api/springcloud-system/client/remove'', ''delete'', 3, 2, 3, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (65, 61, ''000000'', ''client_view'', ''查看'', ''system.client.view'', ''/system/client/view'', ''file-text'', 4, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (66, 17, ''000000'', ''datasource'', ''数据源管理'', ''menu.tool.datasource'', ''/tool/datasource'', ''carderror'', 9, 1, 0, 1, '''', 0, 1);
INSERT INTO `mk_menu` VALUES (67, 66, ''000000'', ''datasource_add'', ''新增'', ''tool.datasource.add'', ''/tool/datasource/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (68, 66, ''000000'', ''datasource_edit'', ''修改'', ''tool.datasource.edit'', ''/tool/datasource/edit'', ''form'', 2, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (69, 66, ''000000'', ''datasource_delete'', ''删除'', ''api.springcloud-develop.datasource.remove'', ''/api/springcloud-develop/datasource/remove'', ''delete'', 3, 2, 3, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (70, 66, ''000000'', ''datasource_view'', ''查看'', ''tool.datasource.view'', ''/tool/datasource/view'', ''file-text'', 4, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (71, 17, ''000000'', ''dbinstance'', ''数据库管理'', ''menu.tool.database'', ''/tool/database'', ''charge'', 1, 1, 0, 1, ''管理数据库的各种用户'', 0, 0);
INSERT INTO `mk_menu` VALUES (72, 17, ''000000'', ''tableinfo'', ''创建数据表'', ''menu.tool.tables'', ''/tool/tables'', ''chart'', 2, 1, 0, 1, ''对数据库中的表结构进行管理，创建并生成dml语句和ddl语句'', 0, 0);
INSERT INTO `mk_menu` VALUES (73, 0, ''000000'', ''permissions'', ''权限管理'', ''menu.permissions'', ''/permission'', ''documentation'', 3, 1, 0, 1, ''系统权限管理功能'', 0, 0);
INSERT INTO `mk_menu` VALUES (74, 73, ''000000'', ''role'', ''角色管理'', ''menu.permission.role'', ''/permission/role'', ''theme'', 1, 1, 0, 1, ''系统角色管理'', 0, 0);
INSERT INTO `mk_menu` VALUES (75, 73, ''000000'', ''datarole'', ''数据权限'', ''menu.permission.datarole'', ''/permission/datarole'', ''skin'', 2, 1, 0, 1, ''可访问数据的权限'', 0, 0);
INSERT INTO `mk_menu` VALUES (76, 73, ''000000'', ''interfacerole'', ''接口权限'', ''menu.permission.interfacerole'', ''/permission/interfacerole'', ''eye'', 3, 1, 0, 1, ''系统的接口权限管理'', 0, 0);
INSERT INTO `mk_menu` VALUES (79, 0, ''000000'', ''dbinstance'', '''', ''menu'', ''/develop/dbinstance'', NULL, 1, 1, 0, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (80, 71, ''000000'', ''dbinstance_add'', ''新增'', ''develop.dbinstance.add'', ''/develop/dbinstance/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (81, 71, ''000000'', ''dbinstance_edit'', ''修改'', ''develop.dbinstance.edit'', ''/develop/dbinstance/edit'', ''form'', 2, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (82, 71, ''000000'', ''dbinstance_delete'', ''删除'', ''api.springcloud-develop.dbinstance.remove'', ''/api/springcloud-develop/dbinstance/remove'', ''delete'', 3, 2, 1, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (83, 71, ''000000'', ''dbinstance_view'', ''查看'', ''develop.dbinstance.view'', ''/develop/dbinstance/view'', ''file-text'', 4, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (84, 0, ''000000'', ''tableinfo'', '''', ''menu'', ''/develop/tableinfo'', NULL, 1, 1, 0, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (85, 72, ''000000'', ''tableinfo_add'', ''新增'', ''develop.tableinfo.add'', ''/develop/tableinfo/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (86, 72, ''000000'', ''tableinfo_edit'', ''修改'', ''develop.tableinfo.edit'', ''/develop/tableinfo/edit'', ''form'', 2, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (87, 72, ''000000'', ''tableinfo_delete'', ''删除'', ''api.springcloud-develop.tableinfo.remove'', ''/api/springcloud-develop/tableinfo/remove'', ''delete'', 3, 2, 1, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (88, 72, ''000000'', ''tableinfo_view'', ''导出DDL'', ''develop.tableinfo.view'', ''/develop/tableinfo/view'', ''file-text'', 4, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (89, 3, ''000000'', ''areas'', ''区域管理'', ''menu.system.area'', ''/system/area'', ''fullscreen'', 5, 1, 0, 1, ''区域管理管理省市 区数据 '', 0, 0);
INSERT INTO `mk_menu` VALUES (90, 89, ''000000'', ''areas_add'', ''新增'', ''system.area.add'', ''/system/area/add'', ''plus'', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (91, 89, ''000000'', ''areas_edit'', ''修改'', ''system.area.edit'', ''/system/area/edit'', ''form'', 2, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (92, 89, ''000000'', ''areas_delete'', ''删除'', ''system.area.remove'', ''/system/area/remove'', ''delete'', 3, 2, 1, 3, '''', 0, 0);
INSERT INTO `mk_menu` VALUES (93, 89, ''000000'', ''areas_view'', ''查看'', ''system.area.view'', ''/system/area/view'', ''file-text'', 4, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (1218005471066120193, 0, ''000000'', ''activity'', ''营销管理'', ''menu.activity'', ''/activity'', ''money'', 8, 1, 0, 1, ''积分项目的营销管理菜单'', 0, 1);
INSERT INTO `mk_menu` VALUES (1218006949558280194, 1218005471066120193, ''000000'', ''activity_manager'', ''营销活动管理'', ''menu.activityManager'', ''/activityManager/activityManager'', ''scan'', 1, 1, 0, 1, ''营销管理中的活动管理菜单'', 0, 0);
INSERT INTO `mk_menu` VALUES (1218048884088037378, 1218005471066120193, ''000000'', ''activity_audit'', ''营销活动审核'', ''menu.activityAudit'', ''/activityManager/activityAudit'', ''lock'', 2, 1, 0, 1, ''营销活动审核菜单'', 0, 0);
INSERT INTO `mk_menu` VALUES (1218050368225730562, 0, ''000000'', ''integral'', ''积分管理'', ''menu.integral'', ''/integral'', ''qq'', 9, 1, 0, 1, ''积分管理菜单'', 0, 1);
INSERT INTO `mk_menu` VALUES (1218071066507796481, 1218050368225730562, ''000000'', ''integral_balance'', ''积分余额查询'', ''menu.integral.integralBalance'', ''/integral/integralBalance'', ''search'', 1, 1, 0, 1, ''积分余额查询菜单'', 0, 0);
INSERT INTO `mk_menu` VALUES (1218074531384582145, 1218050368225730562, ''000000'', ''trade_detail'', ''积分明细查询 '', ''menu.integral.tradeDetail'', ''/integral/tradeDetail'', ''list'', 2, 1, 0, 1, ''积分明细查询菜单'', 0, 0);
INSERT INTO `mk_menu` VALUES (1218074984545574913, 1218050368225730562, ''000000'', ''integral_adjust'', ''积分调整'', ''menu.integral.integralAdjust'', ''/integral/integralAdjust'', ''shopping'', 3, 1, 0, 1, ''积分调整菜单'', 0, 0);
INSERT INTO `mk_menu` VALUES (1218076181276975105, 1218050368225730562, ''000000'', ''integral_adjust_aduit'', ''积分调整审核'', ''menu.integral.integralAdjustAduit'', ''/integral/integralAdjustAduit'', ''size'', 4, 1, 0, 1, ''积分调整审核菜单'', 0, 0);
INSERT INTO `mk_menu` VALUES (1218077454713806850, 1218050368225730562, ''000000'', ''integral_terminal'', ''积分冻结/解冻'', ''menu.integral.integralTerminal'', ''/integral/integralTerminal'', ''tab'', 5, 1, 0, 1, ''积分冻结/解冻菜单'', 0, 0);
INSERT INTO `mk_menu` VALUES (1218078387258580994, 1218050368225730562, ''000000'', ''integral_terminal_aduit'', ''积分冻结/解冻审核'', ''menu.integral.integralTerminalAduit'', ''/integral/integralTerminalAduit'', ''table'', 6, 1, 0, 1, ''积分冻结/解冻审核'', 0, 0);
INSERT INTO `mk_menu` VALUES (1247344466862342146, 0, ''000000'', ''mockserver'', ''mock服务'', ''menu.mockserver'', ''/mockserver'', ''chart'', 10, 1, 0, 1, ''mock服务器的配置，具体使用可参见参数配置表'', 0, 1);
INSERT INTO `mk_menu` VALUES (1247346011947466754, 1247344466862342146, ''000000'', ''mock_website'', ''站点设置'', ''menu.mockserver.website'', ''/mockserver/website'', ''tree-table'', 1, 1, 0, 1, ''先要设置站点的参数。然后在去设置站点mock接口的参数。'', 0, 0);
INSERT INTO `mk_menu` VALUES (1247346561095106561, 1247344466862342146, ''000000'', ''mock_interface'', ''mock接口'', ''mockserver.interface'', ''/mockserver/interface'', ''component'', 2, 1, 0, 1, ''设置mocker服务相关接口的参数，后端服务，会根据相关设置的参数，暴漏出来mock接口，供调用。'', 0, 0);

-- ----------------------------
-- Table structure for mk_mock_http
-- ----------------------------
DROP TABLE IF EXISTS `mk_mock_http`;
CREATE TABLE `mk_mock_http`  (
  `id` bigint(32) NOT NULL COMMENT ''表id'',
  `web_site_id` bigint(32) NOT NULL COMMENT ''web_site表id'',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ''接口名称'',
  `request_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''配置的url路径，支持正则表达式'',
  `request_method` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''http的method,例如get,put,delete,post,支持正则表达式'',
  `request_params` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''http传过来的参数，录入为json结构。key,value支持正则表达式'',
  `request_headers` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''http传过来的头部参数，录入为json结构，key,value支持正则表达式'',
  `request_cookies` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''htpp传过来的cookies参数，录入为json结构，key,value支持正则表达式'',
  `request_json_body` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''http传过来的request报文里的body段所包含的值，不支持正则表达式'',
  `request_charsets` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''request报文的编码，默认为utf-8'',
  `request_string_body` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''request传过来的报文，当成字符串，支持正则表达式'',
  `request_form_body` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''http传过来的request报文里的body段所包含的值，不支持正则表达式'',
  `response_headers` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''response报文里的headers,录入为json.程序会正确拆分，附值 。'',
  `response_body` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT ''response报文里的body'',
  `response_charsets` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''response报文里的body的编码'',
  `response_status_code` int(6) NULL DEFAULT NULL COMMENT ''response报文里的 status code.例如：400，302，501'',
  `response_reason_phrase` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''配合statuscode使用的，状态码解释文本。'',
  `response_delay` int(8) NULL DEFAULT NULL COMMENT ''延迟响应时候，默认时间单位为秒。'',
  `forward_host` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''跳转域名'',
  `forward_port` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''跳转域名接口'',
  `forward_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''跳转路径'',
  `forward_headers` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''跳转时头部参数，录入为json.程序自动拆分配置。'',
  `forward_socket_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''跳转时，重写request时的，请求地址。录入时为json数据格式。程序会自动拆分。withSocketAddress(\"target.host.com\", 1234, SocketAddress.Scheme.HTTPS)'',
  `forward_delay` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''跳转时的延迟时间，默认时间单位为秒'',
  `forward_body` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''跳转时，重写转给第三方的body.'',
  `error_drop_connection` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''是否丢失链接，true,false'',
  `error_response_bytes` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''出错时，返回的报文'',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `is_deleted` int(1) NOT NULL COMMENT ''是否已删除'',
  `status` int(1) NULL DEFAULT NULL COMMENT ''状态'',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_mock_http
-- ----------------------------
INSERT INTO `mk_mock_http` VALUES (1252057126321303554, 1248162814462291970, ''/findname'', ''get'', ''{\"name\":\"naci\"}'', NULL, NULL, NULL, ''utf-8'', NULL, NULL, ''u name is :${name}'', NULL, 200, ''接口访问成功'', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, ''2020-04-20 10:10:56'', NULL, NULL, 0, 1, ''000000'');
INSERT INTO `mk_mock_http` VALUES (1252091455818768386, 1248162814462291970, ''/forward'', ''get'', '''', '''', '''', '''', ''utf-8'', '''', '''', '''', '''', 200, ''接口访问成功'', 0, ''127.0.0.1'', ''1080'', ''/hello'', '''', '''', ''0'', '''', '''', '''', 1, ''2020-04-20 12:27:20'', -1, ''2020-04-20 12:37:32'', 0, 1, ''000000'');

-- ----------------------------
-- Table structure for mk_mock_web_site
-- ----------------------------
DROP TABLE IF EXISTS `mk_mock_web_site`;
CREATE TABLE `mk_mock_web_site`  (
  `id` bigint(32) UNSIGNED NOT NULL COMMENT ''表id'',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT ''站点名称'',
  `address_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''远端的访问地址，带端口号。例如：http://xxxx.yyy.com:9090/uri'',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `is_deleted` int(1) NOT NULL COMMENT ''是否已删除'',
  `status` int(1) NULL DEFAULT NULL COMMENT ''状态'',
  `tenant_id` varchar(12) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_mock_web_site
-- ----------------------------
INSERT INTO `mk_mock_web_site` VALUES (1247697598985281538, ''小贷金融'', ''http://www.xdfineal.me'', 1, ''2020-04-08 09:27:43'', -1, ''2020-04-08 09:29:02'', 0, 1, ''000000'');
INSERT INTO `mk_mock_web_site` VALUES (1248162007356567554, ''33'', ''33'', 1, NULL, NULL, NULL, 1, 1, ''000000'');
INSERT INTO `mk_mock_web_site` VALUES (1248162060687142914, ''5533'', ''5544'', 1, NULL, -1, NULL, 1, 1, ''000000'');
INSERT INTO `mk_mock_web_site` VALUES (1248162814462291970, ''联合贷款'', ''http://union.fineal.com'', 1, NULL, NULL, NULL, 0, 1, ''000000'');

-- ----------------------------
-- Table structure for mk_notice
-- ----------------------------
DROP TABLE IF EXISTS `mk_notice`;
CREATE TABLE `mk_notice`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''标题'',
  `category` int(11) NULL DEFAULT NULL COMMENT ''类型'',
  `release_time` timestamp(0) NULL DEFAULT NULL COMMENT ''发布时间'',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''内容'',
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NULL DEFAULT NULL COMMENT ''状态'',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT ''是否已删除'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''通知公告表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_notice
-- ----------------------------
INSERT INTO `mk_notice` VALUES (23, ''000000'', ''测试公告'', 3, ''2018-12-31 20:03:31'', ''222'', 1, ''2018-12-05 20:03:31'', 1, ''2018-12-28 11:10:51'', 1, 0);
INSERT INTO `mk_notice` VALUES (24, ''000000'', ''测试公告2'', 1, ''2018-12-05 20:03:31'', ''333'', 1, ''2018-12-28 10:32:26'', 1, ''2018-12-28 11:10:34'', 1, 0);
INSERT INTO `mk_notice` VALUES (25, ''000000'', ''测试公告3'', 6, ''2018-12-29 00:00:00'', ''11111'', 1, ''2018-12-28 11:03:44'', 1, ''2018-12-28 11:10:28'', 1, 0);

-- ----------------------------
-- Table structure for mk_param
-- ----------------------------
DROP TABLE IF EXISTS `mk_param`;
CREATE TABLE `mk_param`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''租户ID'',
  `param_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数名'',
  `param_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数键'',
  `param_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数值'',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''备注'',
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NULL DEFAULT NULL COMMENT ''状态'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''参数表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_param
-- ----------------------------
INSERT INTO `mk_param` VALUES (1, '''', ''是否开启注册功能'', ''account.registerUser'', ''true'', ''开启注册'', 1, ''2018-12-28 12:19:01'', 1, ''2018-12-28 12:19:01'', 1, 0);
INSERT INTO `mk_param` VALUES (2, '''', ''账号初始密码'', ''account.initPassword'', ''123456'', ''初始密码'', 1, ''2018-12-28 12:19:01'', 1, ''2018-12-28 12:19:01'', 1, 0);

-- ----------------------------
-- Table structure for mk_role
-- ----------------------------
DROP TABLE IF EXISTS `mk_role`;
CREATE TABLE `mk_role`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  `parent_id` bigint(32) NULL DEFAULT 0 COMMENT ''父主键'',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色名'',
  `sort` int(11) NULL DEFAULT NULL COMMENT ''排序'',
  `role_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色别名'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  `is_leaf` int(2) NOT NULL DEFAULT 0 COMMENT ''是否是叶子节点，0是，1不是'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''角色表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_role
-- ----------------------------
INSERT INTO `mk_role` VALUES (1, ''000000'', 0, ''超级管理员'', 1, ''administrator'', 0, 1);
INSERT INTO `mk_role` VALUES (2, ''000000'', 0, ''用户'', 2, ''user'', 0, 1);
INSERT INTO `mk_role` VALUES (17, ''058431'', 0, ''管理员'', 2, ''admin'', 0, 0);

-- ----------------------------
-- Table structure for mk_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `mk_role_menu`;
CREATE TABLE `mk_role_menu`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `menu_id` bigint(32) NULL DEFAULT NULL COMMENT ''菜单id'',
  `role_id` bigint(32) NULL DEFAULT NULL COMMENT ''角色id'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''角色菜单表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_role_menu
-- ----------------------------
INSERT INTO `mk_role_menu` VALUES (235, 19, 2);
INSERT INTO `mk_role_menu` VALUES (236, 20, 2);
INSERT INTO `mk_role_menu` VALUES (237, 21, 2);
INSERT INTO `mk_role_menu` VALUES (238, 22, 2);
INSERT INTO `mk_role_menu` VALUES (239, 52, 2);
INSERT INTO `mk_role_menu` VALUES (240, 53, 2);
INSERT INTO `mk_role_menu` VALUES (241, 54, 2);
INSERT INTO `mk_role_menu` VALUES (242, 55, 2);
INSERT INTO `mk_role_menu` VALUES (243, 67, 2);
INSERT INTO `mk_role_menu` VALUES (244, 68, 2);
INSERT INTO `mk_role_menu` VALUES (245, 69, 2);
INSERT INTO `mk_role_menu` VALUES (246, 70, 2);
INSERT INTO `mk_role_menu` VALUES (247, 73, 2);
INSERT INTO `mk_role_menu` VALUES (248, 74, 2);
INSERT INTO `mk_role_menu` VALUES (1240555061854171137, 11, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061870948353, 12, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061879336961, 49, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061887725569, 50, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061887725570, 51, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061904502785, 52, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061904502786, 53, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061912891393, 54, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061929668610, 55, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061929668611, 67, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061946445826, 68, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061954834434, 69, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061963223042, 70, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061971611650, 80, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061984194561, 81, 17);
INSERT INTO `mk_role_menu` VALUES (1240555061992583169, 82, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062000971777, 83, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062009360385, 85, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062017748993, 86, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062026137601, 87, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062034526210, 88, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062042914817, 74, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062051303425, 75, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062059692034, 76, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062068080642, 1218006949558280194, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062076469250, 1218048884088037378, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062084857857, 1218071066507796481, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062093246466, 1218074531384582145, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062101635073, 1218074984545574913, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062101635074, 1218076181276975105, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062110023681, 1218077454713806850, 17);
INSERT INTO `mk_role_menu` VALUES (1240555062118412290, 1218078387258580994, 17);
INSERT INTO `mk_role_menu` VALUES (1251041414899351554, 19, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414907740161, 20, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414916128770, 21, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414924517378, 22, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414932905985, 23, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414941294593, 24, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414949683202, 25, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414958071809, 26, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414966460418, 27, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414974849026, 28, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414974849027, 29, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414983237634, 30, 1);
INSERT INTO `mk_role_menu` VALUES (1251041414991626242, 31, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415000014849, 32, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415008403457, 33, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415016792065, 34, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415025180673, 35, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415033569281, 36, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415033569282, 37, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415041957890, 38, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415050346497, 39, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415058735105, 40, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415067123713, 45, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415075512322, 46, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415075512323, 47, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415083900930, 48, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415092289538, 57, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415100678145, 58, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415109066754, 59, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415117455361, 60, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415125843970, 62, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415134232578, 63, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415134232579, 64, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415142621186, 65, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415151009793, 90, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415159398402, 91, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415167787010, 92, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415167787011, 93, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415176175618, 11, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415184564225, 12, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415192952834, 49, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415201341442, 50, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415201341443, 51, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415209730050, 52, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415218118658, 53, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415226507266, 54, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415234895874, 55, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415234895875, 67, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415243284482, 68, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415251673090, 69, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415260061698, 70, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415268450305, 80, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415276838913, 81, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415276838914, 82, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415285227522, 83, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415293616129, 85, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415302004738, 86, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415310393346, 87, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415318781953, 88, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415327170561, 74, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415335559170, 75, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415343947777, 76, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415352336386, 1218006949558280194, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415360724993, 1218048884088037378, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415369113601, 1218071066507796481, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415377502210, 1218074531384582145, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415385890818, 1218074984545574913, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415394279425, 1218076181276975105, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415402668034, 1218077454713806850, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415411056641, 1218078387258580994, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415419445249, 1247346011947466754, 1);
INSERT INTO `mk_role_menu` VALUES (1251041415427833857, 1247346561095106561, 1);

-- ----------------------------
-- Table structure for mk_tenant
-- ----------------------------
DROP TABLE IF EXISTS `mk_tenant`;
CREATE TABLE `mk_tenant`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''租户ID'',
  `tenant_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''租户名称'',
  `linkman` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''联系人'',
  `contact_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''联系电话'',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''联系地址'',
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NULL DEFAULT NULL COMMENT ''状态'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''租户表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_tenant
-- ----------------------------
INSERT INTO `mk_tenant` VALUES (1, ''000000'', ''管理组'', ''admin'', ''666666'', ''管理组的地址'', 1, ''2019-01-01 00:00:39'', 1, ''2019-01-01 00:00:39'', 1, 0);

-- ----------------------------
-- Table structure for mk_user
-- ----------------------------
DROP TABLE IF EXISTS `mk_user`;
CREATE TABLE `mk_user`  (
  `id` bigint(32) NOT NULL COMMENT ''主键'',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ''000000'' COMMENT ''租户ID'',
  `account` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''账号'',
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''密码'',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''昵称'',
  `real_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''真名'',
  `avatar` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''头像'',
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''邮箱'',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''手机'',
  `birthday` timestamp(0) NULL DEFAULT NULL COMMENT ''生日'',
  `sex` smallint(6) NULL DEFAULT NULL COMMENT ''性别'',
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色id'',
  `dept_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''部门id'',
  `code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL  COMMENT '用户编号' AFTER `tenant_id`,
  `post_id` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL  COMMENT '岗位id' AFTER `dept_id`;
  `create_user` int(11) NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user` int(11) NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''修改时间'',
  `status` int(2) NULL DEFAULT NULL COMMENT ''状态'',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT ''是否已删除'',
  `introduction` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''个人介绍'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_user
-- ----------------------------
INSERT INTO `mk_user` VALUES (1, ''000000'', ''admin'', ''90b9aa7e25f80cf4f64e990b78a9fc5ebd6cecad'', ''管理员'', ''管理员'', ''https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'', ''admin@xxxxxx.vip'', ''22233322'', ''2018-08-08 00:00:00'', 1, ''1,2,17'', ''1'', 1, ''2018-08-08 00:00:00'', 1, ''2019-12-26 14:37:47'', 1, 0, ''我是超级管理员'');
-- ----------------------------
-- 增加岗位管理表
-- ----------------------------
CREATE TABLE `mk_post`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `category` int(11) NULL DEFAULT NULL COMMENT '岗位类型',
  `post_code` varchar(12) NULL COMMENT '岗位编号',
  `post_name` varchar(64) NULL COMMENT '岗位名称',
  `sort` int(2) NULL COMMENT '岗位排序',
  `remark` varchar(255) NULL COMMENT '岗位描述',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) COMMENT = '岗位表';

-- ----------------------------
-- 增加岗位管理表数据
-- ----------------------------
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675201, '000000', 1, 'ceo', '首席执行官', 1, '总经理', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675202, '000000', 1, 'coo', '首席运营官', 2, '常务总经理', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675203, '000000', 1, 'cfo', '首席财务官', 3, '财务总经理', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675204, '000000', 1, 'cto', '首席技术官', 4, '技术总监', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675205, '000000', 1, 'cio', '首席信息官', 5, '信息总监', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675206, '000000', 2, 'pm', '技术经理', 6, '研发和产品是永远的朋友', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675207, '000000', 2, 'hrm', '人力经理', 7, '人力资源部门工作管理者', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);
INSERT INTO `mk_post`(`id`, `tenant_id`, `category`, `post_code`, `post_name`, `sort`, `remark`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`)
VALUES (1123598817738675208, '000000', 3, 'staff', '普通员工', 8, '普通员工', 1123598821738675201, 1123598813738675201, '2020-04-01 00:00:00', 1123598821738675201, '2020-04-01 00:00:00', 1, 0);

-- ----------------------------
-- 增加岗位管理菜单数据
-- ----------------------------
INSERT INTO `mk_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733389668962251', '1123598815738675203', 'post', '岗位管理', 'menu', '/system/post', 'iconfont iconicon_message', 2, 1, 0, 1, NULL, 0);
INSERT INTO `mk_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733389668962252', '1164733389668962251', 'post_add', '新增', 'add', '/system/post/add', 'plus', 1, 2, 1, 1, NULL, 0);
INSERT INTO `mk_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733389668962253', '1164733389668962251', 'post_edit', '修改', 'edit', '/system/post/edit', 'form', 2, 2, 2, 1, NULL, 0);
INSERT INTO `mk_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733389668962254', '1164733389668962251', 'post_delete', '删除', 'delete', '/api/blade-system/post/remove', 'delete', 3, 2, 3, 1, NULL, 0);
INSERT INTO `mk_menu`(`id`, `parent_id`, `code`, `name`, `alias`, `path`, `source`, `sort`, `category`, `action`, `is_open`, `remark`, `is_deleted`)
VALUES ('1164733389668962255', '1164733389668962251', 'post_view', '查看', 'view', '/system/post/view', 'file-text', 4, 2, 2, 1, NULL, 0);

-- ----------------------------
-- 增加岗位管理菜单权限数据
-- ----------------------------
INSERT INTO `mk_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875225001', '1164733389668962251', '1123598816738675201');
INSERT INTO `mk_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875225002', '1164733389668962252', '1123598816738675201');
INSERT INTO `mk_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875225003', '1164733389668962253', '1123598816738675201');
INSERT INTO `mk_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875225004', '1164733389668962254', '1123598816738675201');
INSERT INTO `mk_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875225005', '1164733389668962255', '1123598816738675201');
INSERT INTO `mk_role_menu`(`id`,`menu_id`,`role_id`)
VALUES ('1161272893875225006', '1164733389668962256', '1123598816738675201');

-- ----------------------------
-- 增加岗位类型字典数据
-- ----------------------------
INSERT INTO `mk_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`)
VALUES (1123598814738777220, 0, 'post_category', '-1', '岗位类型', 12, NULL, 0);
INSERT INTO `mk_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`)
VALUES (1123598814738777221, 1123598814738777220, 'post_category', '1', '高层', 1, NULL, 0);
INSERT INTO `mk_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`)
VALUES (1123598814738777222, 1123598814738777220, 'post_category', '2', '中层', 2, NULL, 0);
INSERT INTO `mk_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`)
VALUES (1123598814738777223, 1123598814738777220, 'post_category', '3', '基层', 3, NULL, 0);
INSERT INTO `mk_dict`(`id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_deleted`)
VALUES (1123598814738777224, 1123598814738777220, 'post_category', '4', '其他', 4, NULL, 0);
SET FOREIGN_KEY_CHECKS = 1;
