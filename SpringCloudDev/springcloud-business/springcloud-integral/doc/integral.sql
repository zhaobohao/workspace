/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : integral_all

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 15/01/2020 00:55:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_account_integral_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_account_integral_detail`;
CREATE TABLE `tbl_account_integral_detail`  (
  `account_integral_detail_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ACCOUNT_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `CUST_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `IDENTY_TYPE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `IDENTY_CARD` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `CHANGE_INTEGRAL` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易积分',
  `CHANGE_DATE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易日期',
  `INTEGRAL_VALID_BLANCE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分有效期余额',
  `INTEGRAL_VALID_DATE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分有效期',
  `ACCOUNT_BLANCE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户余额',
  `BUSINESS` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `INTEGRAL_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分类型',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`account_integral_detail_id`) USING BTREE,
  UNIQUE INDEX `tbl_account_integral_detail_id_idx`(`account_integral_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '账户积分交易明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_account_integral_detail
-- ----------------------------
INSERT INTO `tbl_account_integral_detail` VALUES (10, '111', '111111', NULL, NULL, '+10', '2019-11-06', '30', '2019-10-20', '170', '内管调整', '内管调整', '标准积分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_account_integral_detail` VALUES (11, '111', '111111', NULL, NULL, '+10', '2019-11-06', '40', '2019-10-20', '180', '内管调整', '内管调整', '标准积分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_act_prm
-- ----------------------------
DROP TABLE IF EXISTS `tbl_act_prm`;
CREATE TABLE `tbl_act_prm`  (
  `act_prm_ID` bigint(32) NOT NULL COMMENT '表id编号',
  `CODE` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '活动类别编号',
  `NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '活动类别名称',
  `AP_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `stat` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态取自参数表',
  `sort` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `HIDE_DEPT_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DATA_MODE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_CODER_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SELECTOR_MODE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CONDITION_TYPE_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SYS_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属系统ID',
  `create_user` bigint(32) NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `update_user` bigint(32) UNSIGNED NOT NULL DEFAULT 1 COMMENT '最近操作人',
  `reserve_cokumn1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段1',
  `reserve_cokumn2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段2',
  `reserve_cokumn3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段3',
  `DATE_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '日期类型（当DATA_MODE为DATE时的扩展）',
  `trial_show` int(11) NULL DEFAULT 0 COMMENT '试算是否显示字段，0显示1不显示',
  `parent_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '隐藏预留字段',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转换表达式',
  `status` int(1) NULL DEFAULT NULL COMMENT '数据状态字段',
  PRIMARY KEY (`act_prm_ID`) USING BTREE,
  UNIQUE INDEX `tbl_act_prm_id_idx`(`act_prm_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统活动参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_act_prm
-- ----------------------------
INSERT INTO `tbl_act_prm` VALUES (1, 'address', '地址', '地址', 'start_sign_start', 1, '', 'data_mode_str', 'parent_coder_scope', 'selector_mode_input', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 08:52:28', '2019-10-31 16:52:27', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (2, 'birday', '出生日期', '', 'start_sign_start', 1, 'hide_dept_MOST', 'data_mode_str', 'parent_coder_time', 'selector_mode_date', 'condition_type_between', 'sys_list_integral', NULL, '2019-10-22 08:48:23', '2019-10-28 12:54:46', 1, '', '', '', 'daterange', 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (3, 'birthday_month', '生日月份', '生日月份', 'start_sign_start', 8, '', 'data_mode_str', 'parent_coder_time', 'selector_mode_checkbox', 'condition_type_month', 'sys_list_integral', NULL, '2019-10-30 05:44:55', '2019-10-30 13:44:55', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (4, 'birthday_today', '生日当天', '生日当天', 'start_sign_start', 6, '', 'data_mode_str', 'parent_coder_time', 'selector_mode_checkbox', 'condition_type_day', 'sys_list_integral', NULL, '2019-10-30 02:48:28', '2019-10-30 10:48:28', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (5, 'birthday_week', '生日当周', '生日当周', 'start_sign_start', 7, NULL, 'data_mode_str', 'parent_coder_time', 'selector_mode_checkbox', 'condition_type_week', 'sys_list_integral', NULL, '2019-10-30 05:43:39', '2019-10-30 13:43:39', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (6, 'card_bank', '所属机构', '所属机构', 'start_sign_start', 2, '', 'data_mode_str', 'parent_coder_scope', 'selector_mode_checkbox', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:01:32', '2019-10-31 17:01:31', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (7, 'cert_no', '客户证件号', '客户证件号', 'start_sign_start', 1, '', 'data_mode_str', 'parent_coder_client', 'selector_mode_input', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:16:50', '2019-10-31 17:16:50', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (8, 'cert_type', '证件类型', '证件类型', 'start_sign_start', 2, '', 'data_mode_str', 'parent_coder_client', 'selector_mode_input', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:17:31', '2019-10-31 17:17:30', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (9, 'client_list', '客户名单', '客户名单', 'start_sign_start', 4, '', 'data_mode_str', 'parent_coder_scope', 'selector_mode_input', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:05:21', '2019-10-31 17:05:21', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (10, 'gender', '性别', '性别', 'start_sign_start', 3, '', 'data_mode_str', 'parent_coder_client', 'selector_mode_checkbox-group', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:19:09', '2019-10-31 17:19:09', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (11, 'marry_status', '婚姻状况', '婚姻状况', 'start_sign_start', 2, '', 'data_mode_int', 'parent_coder_client', 'selector_mode_checkbox-group', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:21:38', '2019-10-31 17:21:37', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (12, 'mcc_code', 'MCC码', 'MCC码', 'start_sign_start', 3, 'hide_dept_MOST', 'data_mode_str', 'parent_coder_scope', 'selector_mode_input', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:03:42', '2019-10-31 17:03:41', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (13, 'open_date', '开户日期', '开户日期', 'start_sign_start', 2, 'hide_dept_MOST', 'data_mode_str', 'parent_coder_time', 'selector_mode_date', 'condition_type_between', 'sys_list_integral', NULL, '2019-10-30 01:47:29', '2019-10-30 09:47:29', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (14, 'sendcard_date', '发卡日期', '发卡日期', 'start_sign_start', 3, 'hide_dept_MOST', 'data_mode_str', 'parent_coder_time', 'selector_mode_date', 'condition_type_between', 'sys_list_integral', NULL, '2019-10-30 01:51:43', '2019-10-30 09:51:42', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (15, 'txn_bank', '交易所属机构', '交易所属机构', 'start_sign_start', 6, '', 'data_mode_str', 'parent_coder_scope', 'selector_mode_checkbox', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-31 09:06:37', '2019-10-31 17:06:36', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (16, 'txn_date', '交易日期', '交易日期', 'start_sign_start', 4, 'hide_dept_MOST', 'data_mode_str', 'parent_coder_time', 'selector_mode_date', 'condition_type_between', 'sys_list_integral', NULL, '2019-10-30 01:54:24', '2019-10-30 09:54:24', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (17, 'txn_month', '交易月份', '交易月份', 'start_sign_start', 9, '', 'data_mode_int', 'parent_coder_time', 'selector_mode_checkbox-group', 'condition_type_equal', 'sys_list_integral', NULL, '2019-10-30 05:47:01', '2019-10-30 13:47:01', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_act_prm` VALUES (18, 'txn_time', '交易时间', '交易时间', 'start_sign_start', 3, '', 'data_mode_str', 'parent_coder_time', 'selector_mode_time', 'condition_type_between', 'sys_list_integral', NULL, '2019-10-30 01:57:22', '2019-10-30 09:57:21', 1, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_act_prm_cate
-- ----------------------------
DROP TABLE IF EXISTS `tbl_act_prm_cate`;
CREATE TABLE `tbl_act_prm_cate`  (
  `act_prm_cate_id` bigint(32) NOT NULL COMMENT '表id编号',
  `AP_CODE` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属活动类别编号',
  `CODE` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类别项类别编号',
  `NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类别项类别名称',
  `status` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `APC_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NOT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `update_user` bigint(32) NULL DEFAULT 1 COMMENT '最近操作人',
  `reserve_cokumn1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段1',
  `reserve_cokumn2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段2',
  `reserve_cokumn3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段3',
  `AP_order` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  PRIMARY KEY (`act_prm_cate_id`) USING BTREE,
  UNIQUE INDEX `tbl_act_prm_cate_id_index`(`act_prm_cate_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统活动参数类别项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_act_prm_cate
-- ----------------------------
INSERT INTO `tbl_act_prm_cate` VALUES (1, 'gender', '1', '男', 'start_sign_start', NULL, NULL, NULL, '2019-10-31 09:19:29', '2019-10-31 17:19:29', 1, NULL, NULL, NULL, 1);
INSERT INTO `tbl_act_prm_cate` VALUES (2, 'gender', '2', '女', 'start_sign_start', NULL, NULL, NULL, '2019-10-31 09:19:39', '2019-10-31 17:19:39', 1, NULL, NULL, NULL, 2);
INSERT INTO `tbl_act_prm_cate` VALUES (3, 'marry_status', '1', '已婚', 'start_sign_start', NULL, NULL, NULL, '2019-10-31 09:21:58', '2019-10-31 17:21:57', 1, NULL, NULL, NULL, 1);
INSERT INTO `tbl_act_prm_cate` VALUES (4, 'marry_status', '2', '未婚', 'start_sign_start', NULL, NULL, NULL, '2019-10-31 09:22:08', '2019-10-31 17:22:08', 1, NULL, NULL, NULL, 2);
INSERT INTO `tbl_act_prm_cate` VALUES (5, 'txn_month', '1', '1月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:57:47', '2019-10-30 14:57:46', 1, NULL, NULL, NULL, 1);
INSERT INTO `tbl_act_prm_cate` VALUES (6, 'txn_month', '10', '10月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:59:08', '2019-10-30 14:59:07', 1, NULL, NULL, NULL, 10);
INSERT INTO `tbl_act_prm_cate` VALUES (7, 'txn_month', '11', '11月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:59:16', '2019-10-30 14:59:15', 1, NULL, NULL, NULL, 11);
INSERT INTO `tbl_act_prm_cate` VALUES (8, 'txn_month', '12', '12月', 'start_sign_start', NULL, NULL, NULL, '2019-10-31 08:43:02', '2019-10-31 16:43:02', 1, NULL, NULL, NULL, 12);
INSERT INTO `tbl_act_prm_cate` VALUES (9, 'txn_month', '2', '2月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:57:58', '2019-10-30 14:57:57', 1, NULL, NULL, NULL, 2);
INSERT INTO `tbl_act_prm_cate` VALUES (10, 'txn_month', '3', '3月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:58:03', '2019-10-30 14:58:03', 1, NULL, NULL, NULL, 3);
INSERT INTO `tbl_act_prm_cate` VALUES (11, 'txn_month', '4', '4月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:58:09', '2019-10-30 14:58:09', 1, NULL, NULL, NULL, 4);
INSERT INTO `tbl_act_prm_cate` VALUES (12, 'txn_month', '5', '5月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:58:22', '2019-10-30 14:58:21', 1, NULL, NULL, NULL, 5);
INSERT INTO `tbl_act_prm_cate` VALUES (13, 'txn_month', '6', '6月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:58:30', '2019-10-30 14:58:30', 1, NULL, NULL, NULL, 6);
INSERT INTO `tbl_act_prm_cate` VALUES (14, 'txn_month', '7', '7月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:58:38', '2019-10-30 14:58:37', 1, NULL, NULL, NULL, 7);
INSERT INTO `tbl_act_prm_cate` VALUES (15, 'txn_month', '8', '8月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:58:46', '2019-10-30 14:58:46', 1, NULL, NULL, NULL, 8);
INSERT INTO `tbl_act_prm_cate` VALUES (16, 'txn_month', '9', '9月', 'start_sign_start', NULL, NULL, NULL, '2019-10-30 06:58:54', '2019-10-30 14:58:54', 1, NULL, NULL, NULL, 9);

-- ----------------------------
-- Table structure for tbl_act_rule_info
-- ----------------------------
DROP TABLE IF EXISTS `tbl_act_rule_info`;
CREATE TABLE `tbl_act_rule_info`  (
  `act_rule_info_ID` bigint(32) NOT NULL COMMENT '表id编号',
  `ACT_CODE` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RULE_ID` bigint(32) NULL DEFAULT NULL,
  `type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '规则计算类型 ',
  `GRADE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '优先级',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段三',
  `RULE_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`act_rule_info_ID`) USING BTREE,
  UNIQUE INDEX `tbl_act_rule_info_id_idx`(`act_rule_info_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_batch_record
-- ----------------------------
DROP TABLE IF EXISTS `tbl_batch_record`;
CREATE TABLE `tbl_batch_record`  (
  `BATCH_ID` bigint(32) NOT NULL COMMENT '批量编号',
  `BATCH_TYPE` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批量所属类型',
  `BATCH_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '批量描述',
  `BATCH_NM` int(11) NULL DEFAULT NULL COMMENT '批量数量',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`BATCH_ID`) USING BTREE,
  UNIQUE INDEX `tbl_batch_record_id_idx`(`BATCH_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_client_act_rel
-- ----------------------------
DROP TABLE IF EXISTS `tbl_client_act_rel`;
CREATE TABLE `tbl_client_act_rel`  (
  `client_act_rel_id` bigint(32) NOT NULL COMMENT '表id编号',
  `client_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户ID',
  `act_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '积分活动ID',
  `account_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '账户ID',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`client_act_rel_id`) USING BTREE,
  UNIQUE INDEX `tbl_client_act_rel_id_idx`(`client_act_rel_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户活动映射关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_client_black_list
-- ----------------------------
DROP TABLE IF EXISTS `tbl_client_black_list`;
CREATE TABLE `tbl_client_black_list`  (
  `BLACK_ID` bigint(32) NOT NULL COMMENT '黑名单编号',
  `CLIENT_ID` bigint(32) NULL DEFAULT NULL COMMENT '规则名称',
  `CLIENT_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '客户姓名',
  `BANK_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开户行名称',
  `SEX` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '性别',
  `MOBILE` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系电话',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`BLACK_ID`) USING BTREE,
  UNIQUE INDEX `tbl_client_black_list_id_idx`(`BLACK_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_client_group
-- ----------------------------
DROP TABLE IF EXISTS `tbl_client_group`;
CREATE TABLE `tbl_client_group`  (
  `GROUP_ID` bigint(32) NOT NULL COMMENT '组群编号',
  `GROUP_NM` bigint(32) NULL DEFAULT NULL COMMENT '组群号码',
  `GROUP_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组群名称',
  `GROUP_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组群描述',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`GROUP_ID`) USING BTREE,
  UNIQUE INDEX `tbl_client_group_id_idx`(`GROUP_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_cust_integral_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cust_integral_detail`;
CREATE TABLE `tbl_cust_integral_detail`  (
  `cust_integral_detail_ID` bigint(32) NOT NULL COMMENT '表id主键',
  `ACCOUNT_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '账户号',
  `CUST_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '客户编号',
  `CUST_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '客户姓名',
  `IDENTY_TYPE` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件类型',
  `IDENTY_CARD` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '证件号码',
  `PHONE_NUM` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '客户电话',
  `CUST_ADDRESS` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '客户地址',
  `INTEGRAL_TYPE` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分类型',
  `INTEGRAL_TYPE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分类型ID',
  `INTEGRAL_VALID_DATE` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分有效期',
  `INTEGRAL_VALID_BLANCE` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分有效期余额',
  `ACCOUNT_BLANCE` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '账户余额',
  `USED_TOATL` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '已使用总额',
  `ACCOUNT_STATUS` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '账户状态',
  `ACCOUNT_STATUS_ID` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '账户状态ID',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`cust_integral_detail_ID`) USING BTREE,
  UNIQUE INDEX `tbl_cust_integral_detail_id_idx`(`cust_integral_detail_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户积分明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_cust_integral_detail
-- ----------------------------
INSERT INTO `tbl_cust_integral_detail` VALUES (1, '111', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-10-20', '20', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (2, '112', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-11-20', '50', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (3, '113', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-12-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (4, '114', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-01-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (5, '115', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-02-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (6, '116', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-03-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (7, '117', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-04-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (8, '118', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-05-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (9, '119', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-06-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (10, '120', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-07-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_cust_integral_detail` VALUES (11, '121', '111111', 'zhangsan', 'cert_type_ssno', '123456', '18862814567', '上海', '标准积分', 'integral_type_normal', '2019-08-20', '10', '0', '40', '', '1', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_data_dic_prm
-- ----------------------------
DROP TABLE IF EXISTS `tbl_data_dic_prm`;
CREATE TABLE `tbl_data_dic_prm`  (
  `data_dic_prm_ID` bigint(32) NOT NULL COMMENT '编号',
  `CODE` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '参数编号',
  `parent_id` bigint(32) NULL DEFAULT 0 COMMENT '父级菜单',
  `NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '参数名称',
  `DD_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '参数描述',
  `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '参数类别',
  `status` int(1) NULL DEFAULT 1 COMMENT '参数状态0启用，1未启用',
  `reserve_cokumn1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段1',
  `reserve_cokumn2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段2',
  `reserve_cokumn3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段3',
  `is_leaf` int(1) NOT NULL DEFAULT 0 COMMENT '是否是叶子节点，0是，1不是',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`data_dic_prm_ID`) USING BTREE,
  UNIQUE INDEX `tbl_data_dic_prm_id_idx`(`data_dic_prm_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统字典参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_data_dic_prm
-- ----------------------------
INSERT INTO `tbl_data_dic_prm` VALUES (1, '0', 67, '冻结', '账户状态冻结', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (2, '1', 67, '正常', '账户状态正常', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (3, '2', 67, '冻结待审核', '账户状态冻结待审核', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (4, '3', 67, '解冻待审核', '账户状态解冻待审核', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (5, 'audit_status', 0, '审核状态', '审核状态', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (6, 'audit_status_nopass', 5, '审核拒绝', '审核状态_未通过', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (7, 'audit_status_pass', 5, '审核通过', '审核状态_审核成功', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (8, 'audit_status_tosub', 5, '待提交', '审核状态_待提交', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (9, 'audit_status_wait', 5, '待审核', '审核状态_待审核', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (10, 'cert_type', 0, '证件类型', '证件类型', 'C03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (11, 'cert_type_kmtc', 10, '通行证', '证件类型_通行证', 'C03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (12, 'cert_type_pspt', 10, '护照', '证件类型_护照', 'C03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (13, 'cert_type_ssno', 10, '身份证', '证件类型_身份证', 'C03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (14, 'condition_type', 0, '条件类型', '条件类型', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (15, 'condition_type_between', 14, 'between', '条件类型_between', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (16, 'condition_type_day', 14, '生日当天', '审核状态_生日当天', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (17, 'condition_type_equal', 14, '等于', '条件类型_等于', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (18, 'condition_type_greater_than', 14, '大于', '条件类型_大于', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (19, 'condition_type_less_than', 14, '小于', '条件类型_小于', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (20, 'condition_type_month', 14, '生日月份', '审核状态_生日月份', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (21, 'condition_type_trans_month', 14, '交易月份', '条件类型_交易月', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (22, 'condition_type_week', 14, '生日当周', '审核状态_生日当周', 'A05', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (23, 'data_mode', 0, '数据方式', '数据方式', 'A02', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (24, 'data_mode_int', 23, '数字', '数据方式_数字', 'A02', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (25, 'data_mode_str', 23, '字符串', '数据方式_字符串', 'A02', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (26, 'date_type', 0, '日期类型', '日期类型', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (27, 'date_type_date', 26, 'date', '日期类型', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (28, 'date_type_daterange', 26, 'daterange', '日期类型', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (29, 'date_type_datetime', 26, 'dateTime', '日期类型', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (30, 'date_type_datetimerange', 26, 'datetimerange', '日期类型', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (31, 'dept', 0, '部门', '部门', 'B01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (32, 'dept_retail', 31, '零售部', '部门_零售部', 'B01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (33, 'dept_tech', 31, '科技部', '部门_科技部', 'B01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (34, 'grp_sort', 0, '组群类别', '组群类别', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (35, 'grp_sort_client', 34, '客户类别', '组群类别_客户', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (36, 'grp_sort_mcc', 34, 'MCC类别', '组群类别_MCC', 'C04', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (37, 'grp_type', 0, '组群类型', '组群类型', 'A12', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (38, 'grp_type_black', 37, '黑名单', '组群类型_黑名单', 'A12', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (39, 'grp_type_noral', 37, '普通类型', '组群类型_普通类型', 'A12', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (40, 'grp_type_white', 37, '白名单', '组群类型_白名单', 'A12', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (41, 'hide_dept', 0, '隐藏部门', '隐藏部门列表', 'A01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (42, 'hide_dept_MOST', 41, '科技部', '隐藏部门_科技部', 'A01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (43, 'hide_dept_RD', 41, '零售部', '隐藏部门_零售部', 'A01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (44, 'integral_type', 0, '积分类型', '积分类型', 'C01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (45, 'integral_type_normal', 44, '标准积分', '积分类型_标准积分', 'C01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (46, 'parent_coder', 0, '父编码', '父编码', 'A03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (47, 'parent_coder_client', 46, '活动客户', '父编码_活动客户', 'A03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (48, 'parent_coder_event', 46, '活动事件', '父编码_活动事件', 'A03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (49, 'parent_coder_scope', 46, '活动范围', '父编码_活动范围', 'A03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (50, 'parent_coder_time', 46, '时间类型', '父编码_时间类型', 'A03', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_data_dic_prm` VALUES (67, 'account_status', 0, '账户状态', '账户状态', 'S01', 1, '', '', '', 1, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_grp_cls
-- ----------------------------
DROP TABLE IF EXISTS `tbl_grp_cls`;
CREATE TABLE `tbl_grp_cls`  (
  `grp_cls_ID` bigint(32) NOT NULL COMMENT '编号',
  `list_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CODE` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '组群码',
  `NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组群名称',
  `GC_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组群类别',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`grp_cls_ID`) USING BTREE,
  UNIQUE INDEX `tbl_grp_cls_id_idx`(`grp_cls_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统组群表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_grp_list
-- ----------------------------
DROP TABLE IF EXISTS `tbl_grp_list`;
CREATE TABLE `tbl_grp_list`  (
  `grp_list_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `CODE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集合码',
  `NAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '集合名称',
  `GL_DESC` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `SORT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集合类别',
  `TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集合类型',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`grp_list_id`) USING BTREE,
  UNIQUE INDEX `tbl_grp_list_id_idx`(`grp_list_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统组群集合表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_integral_act
-- ----------------------------
DROP TABLE IF EXISTS `tbl_integral_act`;
CREATE TABLE `tbl_integral_act`  (
  `integral_act_id` bigint(32) NOT NULL COMMENT '编号',
  `ACT_CODE` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '积分活动编号',
  `ACT_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分活动名称',
  `MARKET_ACT_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '营销活动编号',
  `end_time` varchar(24) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '营销活动结束时间',
  `DEPARTMENT` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属部门',
  `INTEGRAL_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分类型',
  `INTEGRAL_LIMIT_TIME_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分有效期类型',
  `INTEGRAL_LIMIT_YEAR_NUM` bigint(19) NULL DEFAULT NULL COMMENT '积分有效期年限',
  `INTEGRAL_END_MONTH` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '积分到期月份',
  `PREPARE_INTEGRAL_NUM` bigint(19) NULL DEFAULT NULL COMMENT '计划积分',
  `PREPARE_COUNT` bigint(19) NULL DEFAULT NULL COMMENT '计划人数',
  `ACT_BREIF` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '活动描述',
  `STATE` varchar(19) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '状态: audit_status_tosub：新增，audit_status_wait提交待审核，audit_status_pass审核通过，audit_status_nopass退回',
  `RULE_TEAM` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属规则组',
  `OPINION` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核意见',
  `CRT_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建人',
  `CRT_TIME` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `LST_UPD_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后更新人',
  `LST_UPD_TIME` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`integral_act_id`) USING BTREE,
  UNIQUE INDEX `tbl_integral_act_id_idx`(`integral_act_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '积分活动管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_integral_act
-- ----------------------------
INSERT INTO `tbl_integral_act` VALUES (1, '5555', '5555', '423423', NULL, 'sdfad', '54545', '01', 2, NULL, 1000, 1000, '测试', 'audit_status_tosub', NULL, NULL, NULL, '2019-10-24 17:05:27', NULL, '2019-10-24 06:53:19', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_act` VALUES (2, 'ACT101', 'ACT100', 'ASFAS', NULL, 'BB', '01', '1', 2, '2012-03', 20, 300, '', 'audit_status_tosub', 'SDFSD', '', 'AA', '2019-10-25 06:39:06', 'AA', '2019-10-25 06:39:06', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_act` VALUES (3, 'ACT5555', 'ACT5555', '423423', NULL, 'sdfad', '54545', '01', 2, NULL, 1000, 1000, '测试', 'audit_status_tosub', NULL, NULL, NULL, '2019-10-24 17:05:35', NULL, '2019-10-24 06:54:07', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_act` VALUES (4, 'ACT55551', 'ACT5555', '423423', NULL, 'dept_tech', 'integral_type_normal', '01', 2, NULL, 1000, 1000, '测试', 'audit_status_nopass', NULL, NULL, NULL, '2019-10-24 17:54:33', 'aa', '2019-10-24 09:54:33', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_act` VALUES (5, 'ACT55555ewr', 'ACT5555kkldfg', '423423', NULL, 'dept_tech', 'integral_type_normal', '01', 2, NULL, 100011, 1000111, '测试', 'audit_status_tosub', NULL, NULL, NULL, '2019-10-24 17:09:14', NULL, '2019-10-24 09:09:15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_act` VALUES (6, 'Act09999', '测试', 'ACT1111', NULL, 'dept_retail', 'integral_type_normal', 'false', 2, NULL, 1232, 1232, '测试', 'audit_status_tosub', NULL, NULL, NULL, '2019-10-24 17:05:31', NULL, '2019-10-24 07:20:39', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_act` VALUES (7, 'Act099991', '测试', 'ACT1111', NULL, 'dept_retail', 'integral_type_normal', 'false', 2, NULL, 1232, 1232, '测试', 'audit_status_tosub', NULL, NULL, NULL, '2019-10-24 17:05:33', NULL, '2019-10-24 07:21:37', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_integral_adjust
-- ----------------------------
DROP TABLE IF EXISTS `tbl_integral_adjust`;
CREATE TABLE `tbl_integral_adjust`  (
  `integral_adjust_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ACCOUNT_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `CUST_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `ADJUST_TYPE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整类型',
  `ADJUST_NUM` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整数量',
  `ADJUST_REASON` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整原因',
  `CRT_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整人',
  `CRT_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整日期',
  `LST_UPD_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
  `LST_UPD_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`integral_adjust_id`) USING BTREE,
  UNIQUE INDEX `tbl_integral_adjust_id_idx`(`integral_adjust_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分调整表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_integral_adjust_action
-- ----------------------------
DROP TABLE IF EXISTS `tbl_integral_adjust_action`;
CREATE TABLE `tbl_integral_adjust_action`  (
  `integral_adjust_action_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ACCOUNT_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `CUST_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `ADJUST_NUM` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整数量',
  `STATUS_VALUE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整状态id',
  `STATUS_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整状态name',
  `CRT_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整人',
  `INTEGRAL_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分类型',
  `INTEGRAL_TYPE_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分类型id',
  `LST_UPD_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新人',
  `LST_UPD_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新时间',
  `ADJUST_DATE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整日期',
  `ADJUST_REASON` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整原因',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`integral_adjust_action_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分调整动作表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_integral_adjust_action
-- ----------------------------
INSERT INTO `tbl_integral_adjust_action` VALUES (2, '111', '111111', '+10', 'audit_status_pass', NULL, 'A1001', NULL, NULL, 'A1001', '2019-11-06 15:30:57', '2019-11-06 15:30:51', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_adjust_action` VALUES (3, '111', '111111', '+10', 'audit_status_pass', NULL, 'A1001', NULL, NULL, 'A1001', '2019-11-06 15:36:10', '2019-11-06 15:35:57', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_integral_status
-- ----------------------------
DROP TABLE IF EXISTS `tbl_integral_status`;
CREATE TABLE `tbl_integral_status`  (
  `integral_status_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ACCOUNT_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `CUST_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `STATUS_Value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调整状态',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`integral_status_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分冻结解冻状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_integral_status_action
-- ----------------------------
DROP TABLE IF EXISTS `tbl_integral_status_action`;
CREATE TABLE `tbl_integral_status_action`  (
  `integral_status_action_id` bigint(20) NOT NULL COMMENT '自增主键',
  `ACCOUNT_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账户号',
  `CUST_ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户编号',
  `CUST_NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `INTEGRAL_TYPE` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分类型',
  `BLANCE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前余额',
  `OPERATION_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作',
  `REJECT_REASON` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退回原因',
  `ACCOUNT_STATUS_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态id',
  `ACCOUNT_STATUS` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`integral_status_action_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分冻结解冻状态动作表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_integral_status_action
-- ----------------------------
INSERT INTO `tbl_integral_status_action` VALUES (1, '111', '111111', 'zhangsan', 'integral_type_normal', '40', '冻结', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_integral_status_action` VALUES (2, '111', '111111', 'zhangsan', 'integral_type_normal', '40', '解冻', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_latest_act_code
-- ----------------------------
DROP TABLE IF EXISTS `tbl_latest_act_code`;
CREATE TABLE `tbl_latest_act_code`  (
  `ACT_CODE_ID` bigint(32) NOT NULL COMMENT '活动codeid',
  `PREFIX_ACT_CODE` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '活动编号前缀',
  `SUFFIX_ACT_CODE` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '活动编号后缀',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`ACT_CODE_ID`) USING BTREE,
  UNIQUE INDEX `tbl_latest_act_code_id_idx`(`ACT_CODE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '最新活动积分编号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_latest_act_code
-- ----------------------------
INSERT INTO `tbl_latest_act_code` VALUES (1, 'ACT', '101', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_marketing_act
-- ----------------------------
DROP TABLE IF EXISTS `tbl_marketing_act`;
CREATE TABLE `tbl_marketing_act`  (
  `marketing_act_id` bigint(32) NOT NULL COMMENT '表id',
  `ACT_CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动CODE',
  `ACT_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `BEGIN_TIME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动开始时间',
  `END_TIME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动结束时间',
  `CARD_TIEKET_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡券编号',
  `INTEGRAL_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分类型',
  `INTEGRAL_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '积分类型ID',
  `ACT_LINK` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动链接',
  `ACT_STATUS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动状态',
  `ACT_STATUS_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动状态ID',
  `ACT_EXPLAIN` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动说明',
  `ACT_TARGET_BREIF` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动目标描述',
  `ACT_CARD_TICKET_NUM` bigint(19) NULL DEFAULT NULL COMMENT '活动卡券数量',
  `ACT_TARGET_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动目标类型',
  `ACT_TARGET_NUM_NUM` bigint(19) NULL DEFAULT NULL COMMENT '活动目标数量',
  `ACT_INTEGRAL_NUM_NUM` bigint(19) NULL DEFAULT NULL COMMENT '活动积分数量',
  `REVIEW_COMMENTS` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核评语',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  `create_user` bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`marketing_act_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_marketing_act
-- ----------------------------
INSERT INTO `tbl_marketing_act` VALUES (1, 'ACT009', '测试', '2019-11-05', '2019-12-02', NULL, '', 'integral_type_normal', NULL, NULL, 'audit_status_wait', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tbl_marketing_act` VALUES (2, 'ACT3330', '测试222', '2019-11-05', '2019-11-07', NULL, NULL, 'integral_type_normal', NULL, NULL, 'audit_status_tosub', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tbl_rule_exp
-- ----------------------------
DROP TABLE IF EXISTS `tbl_rule_exp`;
CREATE TABLE `tbl_rule_exp`  (
  `RULE_ID` int(11) NOT NULL DEFAULT 0,
  `EXP_ID` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表达式',
  `MCC_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MCC码',
  `NAME_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名单码',
  `CRT_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPD_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `UPD_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `version` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`RULE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '规则表达式表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_rule_exp
-- ----------------------------
INSERT INTO `tbl_rule_exp` VALUES (38, '(trade_amount!=nil&&(intBetween(trade_amount,\'1000,5000\')))?(trade_amount!=nil&&trade_amount>\'1000\'?multiplication(subtraction(trade_amount,\'1000\'),\'0.01\'):0):0', NULL, NULL, '2019-11-07 06:13:44', '2019-11-07 06:13:44', NULL, '1573107224495', NULL);
INSERT INTO `tbl_rule_exp` VALUES (39, '(trade_amount!=nil&&(intBetween(trade_amount,\'1000,10000\')))?(trade_amount!=nil&&trade_amount>\'1000\'?multiplication(subtraction(trade_amount,\'1000\'),\'0.01\'):0):0', NULL, NULL, '2019-11-07 06:38:52', '2019-11-07 06:38:52', NULL, '1573108731552', NULL);
INSERT INTO `tbl_rule_exp` VALUES (40, '(trade_amount!=nil&&(intBetween(trade_amount,\',\')))&&(string.split(txn_date,\' \')[0]!=nil&&(timeBetween(string.split(txn_date,\' \')[0],\'2019-11-07,2019-11-09\')))&&(txn_date!=nil&&include(string.split(\'3月,4月,5月\',\',\'),txn_date))?(tradeCount!=nil&&double(tradeCount)>=0?multiplication(tradeCount,\'5\'):0):0', NULL, NULL, '2019-11-07 06:46:24', '2019-11-07 06:46:24', NULL, '1573109184393', NULL);
INSERT INTO `tbl_rule_exp` VALUES (41, '(trade_amount!=nil&&(intBetween(trade_amount,\',\')))&&(string.split(txn_date,\' \')[0]!=nil&&(timeBetween(string.split(txn_date,\' \')[0],\'2019-11-13,2019-11-15\')))?(tradeCount!=nil&&double(tradeCount)>=0?multiplication(tradeCount,\'5\'):0):0', NULL, NULL, '2019-11-07 07:03:05', '2019-11-07 07:03:05', NULL, '1573110184657', NULL);
INSERT INTO `tbl_rule_exp` VALUES (42, '(trade_amount!=nil&&(intBetween(trade_amount,\',\')))&&(txn_date!=nil&&include(string.split(\'4,5,6\',\',\'),txn_date))?(tradeCount!=nil&&double(tradeCount)>=0?multiplication(tradeCount,\'5\'):0):0', NULL, NULL, '2019-11-07 07:04:06', '2019-11-07 07:04:06', NULL, '1573110245963', NULL);
INSERT INTO `tbl_rule_exp` VALUES (43, '(trade_amount!=nil&&(intBetween(trade_amount,\',\')))?(trade_amount!=nil&&trade_amount>\'1000\'?multiplication(subtraction(trade_amount,\'1000\'),\'0.01\'):0):0', NULL, NULL, '2019-11-07 08:17:28', '2019-11-07 08:17:28', NULL, '1573114648170', NULL);

-- ----------------------------
-- Table structure for tbl_rule_info
-- ----------------------------
DROP TABLE IF EXISTS `tbl_rule_info`;
CREATE TABLE `tbl_rule_info`  (
  `RULE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '规则编号',
  `RULE_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则名称',
  `RULE_STATUS` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则状态',
  `RULE_EXP` varchar(10240) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REMARK` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `CRT_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CRT_DATE` date NULL DEFAULT NULL COMMENT '创建日期',
  `LST_UPD_USER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '送审人',
  `LST_UPD_TIME` date NULL DEFAULT NULL COMMENT '送审日期',
  `RESERVE_COLUMN1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段一',
  `RESERVE_COLUMN2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段二',
  `RESERVE_COLUMN3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段三',
  PRIMARY KEY (`RULE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_rule_info
-- ----------------------------
INSERT INTO `tbl_rule_info` VALUES (38, '测试001', 'audit_status_pass', '[{\"ref\":null,\"label\":\"活动客户\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"证件类型\",\"type\":\"input\",\"value\":\"cert_type\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户证件号\",\"type\":\"input\",\"value\":\"cert_no\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"已婚\"},{\"value\":\"2\",\"key\":\"未婚\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"婚姻状况\",\"type\":\"checkbox-group\",\"value\":\"marry_status\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"男\"},{\"value\":\"2\",\"key\":\"女\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"性别\",\"type\":\"checkbox-group\",\"value\":\"gender\"}],\"data\":{\"cert_type\":\"\",\"cert_no\":\"\",\"marry_status\":[],\"gender\":[]}},{\"ref\":null,\"label\":\"活动事件\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易金额\",\"type\":\"input-range\",\"value\":\"trade_amount\"},{\"dateType\":\"date\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"eq\",\"label\":\"测试003\",\"type\":\"date\",\"value\":\"测试003\"},{\"dateType\":\"datetimerange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试005\",\"type\":\"date\",\"value\":\"test_005\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试002\",\"type\":\"time\",\"value\":\"test_002\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"测试001\",\"type\":\"time\",\"value\":\"test_001\"}],\"data\":{\"trade_amount\":[\"1000\",\"5000\"],\"测试003\":\"\",\"test_005\":\"\",\"test_002\":\"\",\"test_001\":\"\"}},{\"ref\":null,\"label\":\"活动范围\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"MCC码\",\"type\":\"input\",\"value\":\"mcc_code\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户名单\",\"type\":\"input\",\"value\":\"client_list\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"地址\",\"type\":\"input\",\"value\":\"address\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易所属机构\",\"type\":\"checkbox\",\"value\":\"txn_bank\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"所属机构\",\"type\":\"checkbox\",\"value\":\"card_bank\"}],\"data\":{\"mcc_code\":\"\",\"client_list\":\"\",\"address\":\"\",\"txn_bank\":\"\",\"card_bank\":\"\"}},{\"ref\":null,\"label\":\"时间类型\",\"fieldList\":[{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"交易日期\",\"type\":\"date\",\"value\":\"txn_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"发卡日期\",\"type\":\"date\",\"value\":\"sendcard_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"开户日期\",\"type\":\"date\",\"value\":\"open_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"出生日期\",\"type\":\"date\",\"value\":\"birday\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易时间\",\"type\":\"time\",\"value\":\"txn_time\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"1月\"},{\"value\":\"2\",\"key\":\"2月\"},{\"value\":\"3\",\"key\":\"3月\"},{\"value\":\"4\",\"key\":\"4月\"},{\"value\":\"5\",\"key\":\"5月\"},{\"value\":\"6\",\"key\":\"6月\"},{\"value\":\"7\",\"key\":\"7月\"},{\"value\":\"8\",\"key\":\"8月\"},{\"value\":\"9\",\"key\":\"9月\"},{\"value\":\"10\",\"key\":\"10月\"},{\"value\":\"11\",\"key\":\"11月\"},{\"value\":\"12\",\"key\":\"12月\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易月份\",\"type\":\"checkbox-group\",\"value\":\"txn_month\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当周\",\"type\":\"checkbox\",\"value\":\"birthday_week\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当天\",\"type\":\"checkbox\",\"value\":\"birthday_today\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日月份\",\"type\":\"checkbox\",\"value\":\"birthday_month\"}],\"data\":{\"txn_date\":\"\",\"sendcard_date\":\"\",\"open_date\":\"\",\"birday\":\"\",\"txn_time\":\"\",\"txn_month\":[],\"birthday_week\":\"\",\"birthday_today\":\"\",\"birthday_month\":\"\"}},{\"tradeType\":0,\"overAmount\":\"1000\",\"topScore\":\"0\",\"integralRate\":[\"100\",\"1\"],\"perCountFixScore\":\"0\",\"tradeCount\":\"0\"}]', NULL, 'A1001', '2019-11-07', 'A1001', '2019-11-07', NULL, NULL, NULL);
INSERT INTO `tbl_rule_info` VALUES (39, '测试002', 'audit_status_pass', '[{\"ref\":null,\"label\":\"活动客户\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"证件类型\",\"type\":\"input\",\"value\":\"cert_type\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户证件号\",\"type\":\"input\",\"value\":\"cert_no\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"已婚\"},{\"value\":\"2\",\"key\":\"未婚\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"婚姻状况\",\"type\":\"checkbox-group\",\"value\":\"marry_status\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"男\"},{\"value\":\"2\",\"key\":\"女\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"性别\",\"type\":\"checkbox-group\",\"value\":\"gender\"}],\"data\":{\"cert_type\":\"\",\"cert_no\":\"\",\"marry_status\":[],\"gender\":[]}},{\"ref\":null,\"label\":\"活动事件\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易金额\",\"type\":\"input-range\",\"value\":\"trade_amount\"},{\"dateType\":\"date\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"eq\",\"label\":\"测试003\",\"type\":\"date\",\"value\":\"测试003\"},{\"dateType\":\"datetimerange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试005\",\"type\":\"date\",\"value\":\"test_005\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试002\",\"type\":\"time\",\"value\":\"test_002\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"测试001\",\"type\":\"time\",\"value\":\"test_001\"}],\"data\":{\"trade_amount\":[\"1000\",\"10000\"],\"测试003\":\"\",\"test_005\":\"\",\"test_002\":\"\",\"test_001\":\"\"}},{\"ref\":null,\"label\":\"活动范围\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"MCC码\",\"type\":\"input\",\"value\":\"mcc_code\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户名单\",\"type\":\"input\",\"value\":\"client_list\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"地址\",\"type\":\"input\",\"value\":\"address\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易所属机构\",\"type\":\"checkbox\",\"value\":\"txn_bank\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"所属机构\",\"type\":\"checkbox\",\"value\":\"card_bank\"}],\"data\":{\"mcc_code\":\"\",\"client_list\":\"\",\"address\":\"\",\"txn_bank\":\"\",\"card_bank\":\"\"}},{\"ref\":null,\"label\":\"时间类型\",\"fieldList\":[{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"交易日期\",\"type\":\"date\",\"value\":\"txn_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"发卡日期\",\"type\":\"date\",\"value\":\"sendcard_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"开户日期\",\"type\":\"date\",\"value\":\"open_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"出生日期\",\"type\":\"date\",\"value\":\"birday\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易时间\",\"type\":\"time\",\"value\":\"txn_time\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"1月\"},{\"value\":\"2\",\"key\":\"2月\"},{\"value\":\"3\",\"key\":\"3月\"},{\"value\":\"4\",\"key\":\"4月\"},{\"value\":\"5\",\"key\":\"5月\"},{\"value\":\"6\",\"key\":\"6月\"},{\"value\":\"7\",\"key\":\"7月\"},{\"value\":\"8\",\"key\":\"8月\"},{\"value\":\"9\",\"key\":\"9月\"},{\"value\":\"10\",\"key\":\"10月\"},{\"value\":\"11\",\"key\":\"11月\"},{\"value\":\"12\",\"key\":\"12月\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易月份\",\"type\":\"checkbox-group\",\"value\":\"txn_month\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当周\",\"type\":\"checkbox\",\"value\":\"birthday_week\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当天\",\"type\":\"checkbox\",\"value\":\"birthday_today\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日月份\",\"type\":\"checkbox\",\"value\":\"birthday_month\"}],\"data\":{\"txn_date\":\"\",\"sendcard_date\":\"\",\"open_date\":\"\",\"birday\":\"\",\"txn_time\":\"\",\"txn_month\":[],\"birthday_week\":\"\",\"birthday_today\":\"\",\"birthday_month\":\"\"}},{\"tradeType\":0,\"overAmount\":\"1000\",\"topScore\":\"0\",\"integralRate\":[\"100\",\"1\"],\"perCountFixScore\":\"0\",\"tradeCount\":\"0\"}]', NULL, NULL, '2019-11-07', NULL, '2019-11-07', NULL, NULL, NULL);
INSERT INTO `tbl_rule_info` VALUES (40, '测试003', 'audit_status_pass', '[{\"ref\":null,\"label\":\"活动客户\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"证件类型\",\"type\":\"input\",\"value\":\"cert_type\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户证件号\",\"type\":\"input\",\"value\":\"cert_no\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"已婚\"},{\"value\":\"2\",\"key\":\"未婚\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"婚姻状况\",\"type\":\"checkbox-group\",\"value\":\"marry_status\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"男\"},{\"value\":\"2\",\"key\":\"女\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"性别\",\"type\":\"checkbox-group\",\"value\":\"gender\"}],\"data\":{\"cert_type\":\"\",\"cert_no\":\"\",\"marry_status\":[],\"gender\":[]}},{\"ref\":null,\"label\":\"活动事件\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易金额\",\"type\":\"input-range\",\"value\":\"trade_amount\"},{\"dateType\":\"date\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"eq\",\"label\":\"测试003\",\"type\":\"date\",\"value\":\"测试003\"},{\"dateType\":\"datetimerange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试005\",\"type\":\"date\",\"value\":\"test_005\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试002\",\"type\":\"time\",\"value\":\"test_002\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"测试001\",\"type\":\"time\",\"value\":\"test_001\"}],\"data\":{\"trade_amount\":[\"\",\"\"],\"测试003\":\"\",\"test_005\":\"\",\"test_002\":\"\",\"test_001\":\"\"}},{\"ref\":null,\"label\":\"活动范围\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"MCC码\",\"type\":\"input\",\"value\":\"mcc_code\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户名单\",\"type\":\"input\",\"value\":\"client_list\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"地址\",\"type\":\"input\",\"value\":\"address\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易所属机构\",\"type\":\"checkbox\",\"value\":\"txn_bank\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"所属机构\",\"type\":\"checkbox\",\"value\":\"card_bank\"}],\"data\":{\"mcc_code\":\"\",\"client_list\":\"\",\"address\":\"\",\"txn_bank\":\"\",\"card_bank\":\"\"}},{\"ref\":null,\"label\":\"时间类型\",\"fieldList\":[{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"交易日期\",\"type\":\"date\",\"value\":\"txn_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"发卡日期\",\"type\":\"date\",\"value\":\"sendcard_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"开户日期\",\"type\":\"date\",\"value\":\"open_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"出生日期\",\"type\":\"date\",\"value\":\"birday\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易时间\",\"type\":\"time\",\"value\":\"txn_time\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"1月\"},{\"value\":\"2\",\"key\":\"2月\"},{\"value\":\"3\",\"key\":\"3月\"},{\"value\":\"4\",\"key\":\"4月\"},{\"value\":\"5\",\"key\":\"5月\"},{\"value\":\"6\",\"key\":\"6月\"},{\"value\":\"7\",\"key\":\"7月\"},{\"value\":\"8\",\"key\":\"8月\"},{\"value\":\"9\",\"key\":\"9月\"},{\"value\":\"10\",\"key\":\"10月\"},{\"value\":\"11\",\"key\":\"11月\"},{\"value\":\"12\",\"key\":\"12月\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易月份\",\"type\":\"checkbox-group\",\"value\":\"txn_month\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当周\",\"type\":\"checkbox\",\"value\":\"birthday_week\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当天\",\"type\":\"checkbox\",\"value\":\"birthday_today\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日月份\",\"type\":\"checkbox\",\"value\":\"birthday_month\"}],\"data\":{\"txn_date\":[\"2019-11-07\",\"2019-11-09\"],\"sendcard_date\":\"\",\"open_date\":\"\",\"birday\":\"\",\"txn_time\":\"\",\"txn_month\":[\"3月\",\"4月\",\"5月\"],\"birthday_week\":\"\",\"birthday_today\":\"\",\"birthday_month\":\"\"}},{\"tradeType\":1,\"overAmount\":\"0\",\"topScore\":\"0\",\"integralRate\":[\"0\",\"0\"],\"perCountFixScore\":\"5\",\"tradeCount\":\"0\"}]', NULL, NULL, '2019-11-07', NULL, '2019-11-07', NULL, NULL, NULL);
INSERT INTO `tbl_rule_info` VALUES (41, '测试004', 'audit_status_pass', '[{\"ref\":null,\"label\":\"活动客户\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"证件类型\",\"type\":\"input\",\"value\":\"cert_type\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户证件号\",\"type\":\"input\",\"value\":\"cert_no\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"已婚\"},{\"value\":\"2\",\"key\":\"未婚\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"婚姻状况\",\"type\":\"checkbox-group\",\"value\":\"marry_status\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"男\"},{\"value\":\"2\",\"key\":\"女\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"性别\",\"type\":\"checkbox-group\",\"value\":\"gender\"}],\"data\":{\"cert_type\":\"\",\"cert_no\":\"\",\"marry_status\":[],\"gender\":[]}},{\"ref\":null,\"label\":\"活动事件\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易笔数\",\"type\":\"input\",\"value\":\"tradeCount\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易金额\",\"type\":\"input-range\",\"value\":\"trade_amount\"},{\"dateType\":\"date\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"eq\",\"label\":\"测试003\",\"type\":\"date\",\"value\":\"测试003\"},{\"dateType\":\"datetimerange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试005\",\"type\":\"date\",\"value\":\"test_005\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试002\",\"type\":\"time\",\"value\":\"test_002\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"测试001\",\"type\":\"time\",\"value\":\"test_001\"}],\"data\":{\"tradeCount\":\"\",\"trade_amount\":[\"\",\"\"],\"测试003\":\"\",\"test_005\":\"\",\"test_002\":\"\",\"test_001\":\"\"}},{\"ref\":null,\"label\":\"活动范围\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"MCC码\",\"type\":\"input\",\"value\":\"mcc_code\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户名单\",\"type\":\"input\",\"value\":\"client_list\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"地址\",\"type\":\"input\",\"value\":\"address\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易所属机构\",\"type\":\"checkbox\",\"value\":\"txn_bank\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"所属机构\",\"type\":\"checkbox\",\"value\":\"card_bank\"}],\"data\":{\"mcc_code\":\"\",\"client_list\":\"\",\"address\":\"\",\"txn_bank\":\"\",\"card_bank\":\"\"}},{\"ref\":null,\"label\":\"时间类型\",\"fieldList\":[{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"交易日期\",\"type\":\"date\",\"value\":\"txn_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"发卡日期\",\"type\":\"date\",\"value\":\"sendcard_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"开户日期\",\"type\":\"date\",\"value\":\"open_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"出生日期\",\"type\":\"date\",\"value\":\"birday\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易时间\",\"type\":\"time\",\"value\":\"txn_time\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"1月\"},{\"value\":\"2\",\"key\":\"2月\"},{\"value\":\"3\",\"key\":\"3月\"},{\"value\":\"4\",\"key\":\"4月\"},{\"value\":\"5\",\"key\":\"5月\"},{\"value\":\"6\",\"key\":\"6月\"},{\"value\":\"7\",\"key\":\"7月\"},{\"value\":\"8\",\"key\":\"8月\"},{\"value\":\"9\",\"key\":\"9月\"},{\"value\":\"10\",\"key\":\"10月\"},{\"value\":\"11\",\"key\":\"11月\"},{\"value\":\"12\",\"key\":\"12月\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易月份\",\"type\":\"checkbox-group\",\"value\":\"txn_month\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当周\",\"type\":\"checkbox\",\"value\":\"birthday_week\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当天\",\"type\":\"checkbox\",\"value\":\"birthday_today\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日月份\",\"type\":\"checkbox\",\"value\":\"birthday_month\"}],\"data\":{\"txn_date\":[\"2019-11-13\",\"2019-11-15\"],\"sendcard_date\":\"\",\"open_date\":\"\",\"birday\":\"\",\"txn_time\":\"\",\"txn_month\":[],\"birthday_week\":\"\",\"birthday_today\":\"\",\"birthday_month\":\"\"}},{\"tradeType\":1,\"overAmount\":\"0\",\"topScore\":\"0\",\"integralRate\":[\"0\",\"0\"],\"perCountFixScore\":\"5\",\"tradeCount\":\"0\"}]', NULL, 'A1001', '2019-11-07', 'A1001', '2019-11-07', NULL, NULL, NULL);
INSERT INTO `tbl_rule_info` VALUES (42, '测试005', 'audit_status_pass', '[{\"ref\":null,\"label\":\"活动客户\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"证件类型\",\"type\":\"input\",\"value\":\"cert_type\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户证件号\",\"type\":\"input\",\"value\":\"cert_no\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"已婚\"},{\"value\":\"2\",\"key\":\"未婚\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"婚姻状况\",\"type\":\"checkbox-group\",\"value\":\"marry_status\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"男\"},{\"value\":\"2\",\"key\":\"女\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"性别\",\"type\":\"checkbox-group\",\"value\":\"gender\"}],\"data\":{\"cert_type\":\"\",\"cert_no\":\"\",\"marry_status\":[],\"gender\":[]}},{\"ref\":null,\"label\":\"活动事件\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易笔数\",\"type\":\"input\",\"value\":\"tradeCount\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易金额\",\"type\":\"input-range\",\"value\":\"trade_amount\"},{\"dateType\":\"date\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"eq\",\"label\":\"测试003\",\"type\":\"date\",\"value\":\"测试003\"},{\"dateType\":\"datetimerange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试005\",\"type\":\"date\",\"value\":\"test_005\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试002\",\"type\":\"time\",\"value\":\"test_002\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"测试001\",\"type\":\"time\",\"value\":\"test_001\"}],\"data\":{\"tradeCount\":\"\",\"trade_amount\":[\"\",\"\"],\"测试003\":\"\",\"test_005\":\"\",\"test_002\":\"\",\"test_001\":\"\"}},{\"ref\":null,\"label\":\"活动范围\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"MCC码\",\"type\":\"input\",\"value\":\"mcc_code\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户名单\",\"type\":\"input\",\"value\":\"client_list\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"地址\",\"type\":\"input\",\"value\":\"address\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易所属机构\",\"type\":\"checkbox\",\"value\":\"txn_bank\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"所属机构\",\"type\":\"checkbox\",\"value\":\"card_bank\"}],\"data\":{\"mcc_code\":\"\",\"client_list\":\"\",\"address\":\"\",\"txn_bank\":\"\",\"card_bank\":\"\"}},{\"ref\":null,\"label\":\"时间类型\",\"fieldList\":[{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"交易日期\",\"type\":\"date\",\"value\":\"txn_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"发卡日期\",\"type\":\"date\",\"value\":\"sendcard_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"开户日期\",\"type\":\"date\",\"value\":\"open_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"出生日期\",\"type\":\"date\",\"value\":\"birday\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易时间\",\"type\":\"time\",\"value\":\"txn_time\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"1月\"},{\"value\":\"2\",\"key\":\"2月\"},{\"value\":\"3\",\"key\":\"3月\"},{\"value\":\"4\",\"key\":\"4月\"},{\"value\":\"5\",\"key\":\"5月\"},{\"value\":\"6\",\"key\":\"6月\"},{\"value\":\"7\",\"key\":\"7月\"},{\"value\":\"8\",\"key\":\"8月\"},{\"value\":\"9\",\"key\":\"9月\"},{\"value\":\"10\",\"key\":\"10月\"},{\"value\":\"11\",\"key\":\"11月\"},{\"value\":\"12\",\"key\":\"12月\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易月份\",\"type\":\"checkbox-group\",\"value\":\"txn_month\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当周\",\"type\":\"checkbox\",\"value\":\"birthday_week\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当天\",\"type\":\"checkbox\",\"value\":\"birthday_today\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日月份\",\"type\":\"checkbox\",\"value\":\"birthday_month\"}],\"data\":{\"txn_date\":\"\",\"sendcard_date\":\"\",\"open_date\":\"\",\"birday\":\"\",\"txn_time\":\"\",\"txn_month\":[\"4\",\"5\",\"6\"],\"birthday_week\":\"\",\"birthday_today\":\"\",\"birthday_month\":\"\"}},{\"tradeType\":1,\"overAmount\":\"0\",\"topScore\":\"0\",\"integralRate\":[\"0\",\"0\"],\"perCountFixScore\":\"5\",\"tradeCount\":\"0\"}]', NULL, 'A1001', '2019-11-07', 'A1001', '2019-11-07', NULL, NULL, NULL);
INSERT INTO `tbl_rule_info` VALUES (43, '测试006', 'audit_status_pass', '[{\"ref\":null,\"label\":\"活动客户\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"证件类型\",\"type\":\"input\",\"value\":\"cert_type\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户证件号\",\"type\":\"input\",\"value\":\"cert_no\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"已婚\"},{\"value\":\"2\",\"key\":\"未婚\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"婚姻状况\",\"type\":\"checkbox-group\",\"value\":\"marry_status\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"男\"},{\"value\":\"2\",\"key\":\"女\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"性别\",\"type\":\"checkbox-group\",\"value\":\"gender\"}],\"data\":{\"cert_type\":\"\",\"cert_no\":\"\",\"marry_status\":[],\"gender\":[]}},{\"ref\":null,\"label\":\"活动事件\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易笔数\",\"type\":\"input\",\"value\":\"tradeCount\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易金额\",\"type\":\"input-range\",\"value\":\"trade_amount\"},{\"dateType\":\"date\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"eq\",\"label\":\"测试003\",\"type\":\"date\",\"value\":\"测试003\"},{\"dateType\":\"datetimerange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试005\",\"type\":\"date\",\"value\":\"test_005\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试002\",\"type\":\"time\",\"value\":\"test_002\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"测试001\",\"type\":\"time\",\"value\":\"test_001\"}],\"data\":{\"tradeCount\":\"\",\"trade_amount\":[\"\",\"\"],\"测试003\":\"\",\"test_005\":\"\",\"test_002\":\"\",\"test_001\":\"\"}},{\"ref\":null,\"label\":\"活动范围\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"MCC码\",\"type\":\"input\",\"value\":\"mcc_code\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户名单\",\"type\":\"input\",\"value\":\"client_list\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"地址\",\"type\":\"input\",\"value\":\"address\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易所属机构\",\"type\":\"checkbox\",\"value\":\"txn_bank\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"所属机构\",\"type\":\"checkbox\",\"value\":\"card_bank\"}],\"data\":{\"mcc_code\":\"\",\"client_list\":\"\",\"address\":\"\",\"txn_bank\":\"\",\"card_bank\":\"\"}},{\"ref\":null,\"label\":\"时间类型\",\"fieldList\":[{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"交易日期\",\"type\":\"date\",\"value\":\"txn_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"发卡日期\",\"type\":\"date\",\"value\":\"sendcard_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"开户日期\",\"type\":\"date\",\"value\":\"open_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"出生日期\",\"type\":\"date\",\"value\":\"birday\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易时间\",\"type\":\"time\",\"value\":\"txn_time\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"1月\"},{\"value\":\"2\",\"key\":\"2月\"},{\"value\":\"3\",\"key\":\"3月\"},{\"value\":\"4\",\"key\":\"4月\"},{\"value\":\"5\",\"key\":\"5月\"},{\"value\":\"6\",\"key\":\"6月\"},{\"value\":\"7\",\"key\":\"7月\"},{\"value\":\"8\",\"key\":\"8月\"},{\"value\":\"9\",\"key\":\"9月\"},{\"value\":\"10\",\"key\":\"10月\"},{\"value\":\"11\",\"key\":\"11月\"},{\"value\":\"12\",\"key\":\"12月\"}],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易月份\",\"type\":\"checkbox-group\",\"value\":\"txn_month\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当周\",\"type\":\"checkbox\",\"value\":\"birthday_week\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当天\",\"type\":\"checkbox\",\"value\":\"birthday_today\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日月份\",\"type\":\"checkbox\",\"value\":\"birthday_month\"}],\"data\":{\"txn_date\":\"\",\"sendcard_date\":\"\",\"open_date\":\"\",\"birday\":\"\",\"txn_time\":\"\",\"txn_month\":[],\"birthday_week\":\"\",\"birthday_today\":\"\",\"birthday_month\":\"\"}},{\"tradeType\":0,\"overAmount\":\"1000\",\"topScore\":\"0\",\"integralRate\":[\"100\",\"1\"],\"perCountFixScore\":\"0\",\"tradeCount\":\"0\"}]', NULL, NULL, '2019-11-07', 'A1001', '2019-11-07', NULL, NULL, NULL);
INSERT INTO `tbl_rule_info` VALUES (44, '测试007', 'audit_status_nopass', '[{\"ref\":null,\"label\":\"活动客户\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"证件类型\",\"type\":\"input\",\"value\":\"cert_type\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户证件号\",\"type\":\"input\",\"value\":\"cert_no\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"已婚\"},{\"value\":\"2\",\"key\":\"未婚\"}],\"itemValue\":[],\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"婚姻状况\",\"type\":\"checkbox-group\",\"value\":\"marry_status\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"男\"},{\"value\":\"2\",\"key\":\"女\"}],\"itemValue\":[],\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"性别\",\"type\":\"checkbox-group\",\"value\":\"gender\"}],\"data\":{\"cert_type\":\"\",\"cert_no\":\"\",\"marry_status\":[],\"gender\":[]}},{\"ref\":null,\"label\":\"活动事件\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易笔数\",\"type\":\"input\",\"value\":\"tradeCount\"},{\"subItems\":[],\"itemValue\":[\"\",\"\"],\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易金额\",\"type\":\"input-range\",\"value\":\"trade_amount\"},{\"dateType\":\"date\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"eq\",\"label\":\"测试003\",\"type\":\"date\",\"value\":\"测试003\"},{\"dateType\":\"datetimerange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试005\",\"type\":\"date\",\"value\":\"test_005\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"测试002\",\"type\":\"time\",\"value\":\"test_002\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"测试001\",\"type\":\"time\",\"value\":\"test_001\"}],\"data\":{\"tradeCount\":\"\",\"trade_amount\":[\"\",\"\"],\"测试003\":\"\",\"test_005\":\"\",\"test_002\":\"\",\"test_001\":\"\"}},{\"ref\":null,\"label\":\"活动范围\",\"fieldList\":[{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"MCC码\",\"type\":\"input\",\"value\":\"mcc_code\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"客户名单\",\"type\":\"input\",\"value\":\"client_list\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"地址\",\"type\":\"input\",\"value\":\"address\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易所属机构\",\"type\":\"checkbox\",\"value\":\"txn_bank\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"所属机构\",\"type\":\"checkbox\",\"value\":\"card_bank\"}],\"data\":{\"mcc_code\":\"\",\"client_list\":\"\",\"address\":\"\",\"txn_bank\":\"\",\"card_bank\":\"\"}},{\"ref\":null,\"label\":\"时间类型\",\"fieldList\":[{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":[\"2019-11-07\",\"2019-11-08\"],\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"交易日期\",\"type\":\"date\",\"value\":\"txn_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"发卡日期\",\"type\":\"date\",\"value\":\"sendcard_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"开户日期\",\"type\":\"date\",\"value\":\"open_date\"},{\"dateType\":\"daterange\",\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd\",\"condType\":\"between\",\"label\":\"出生日期\",\"type\":\"date\",\"value\":\"birday\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"between\",\"label\":\"交易时间\",\"type\":\"time\",\"value\":\"txn_time\"},{\"subItems\":[{\"value\":\"1\",\"key\":\"1月\"},{\"value\":\"2\",\"key\":\"2月\"},{\"value\":\"3\",\"key\":\"3月\"},{\"value\":\"4\",\"key\":\"4月\"},{\"value\":\"5\",\"key\":\"5月\"},{\"value\":\"6\",\"key\":\"6月\"},{\"value\":\"7\",\"key\":\"7月\"},{\"value\":\"8\",\"key\":\"8月\"},{\"value\":\"9\",\"key\":\"9月\"},{\"value\":\"10\",\"key\":\"10月\"},{\"value\":\"11\",\"key\":\"11月\"},{\"value\":\"12\",\"key\":\"12月\"}],\"itemValue\":[\"5\",\"6\"],\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"交易月份\",\"type\":\"checkbox-group\",\"value\":\"txn_month\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当周\",\"type\":\"checkbox\",\"value\":\"birthday_week\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日当天\",\"type\":\"checkbox\",\"value\":\"birthday_today\"},{\"subItems\":[],\"itemValue\":\"\",\"valueFormat\":\"yyyy-MM-dd HH:mm:ss\",\"condType\":\"eq\",\"label\":\"生日月份\",\"type\":\"checkbox\",\"value\":\"birthday_month\"}],\"data\":{\"txn_date\":[\"2019-11-07\",\"2019-11-08\"],\"sendcard_date\":\"\",\"open_date\":\"\",\"birday\":\"\",\"txn_time\":\"\",\"txn_month\":[\"5\",\"6\"],\"birthday_week\":\"\",\"birthday_today\":\"\",\"birthday_month\":\"\"}},{\"overAmount\":\"1000\",\"tradeCount\":\"0\",\"topScore\":\"0\",\"integralRate\":[\"100\",\"1\"],\"perCountFixScore\":\"0\",\"tradeType\":0}]', NULL, 'A1001', '2019-11-07', 'A1001', '2019-11-07', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
