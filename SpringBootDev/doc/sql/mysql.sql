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

 Date: 19/12/2019 16:45:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for db_db_instance
-- ----------------------------
DROP TABLE IF EXISTS `db_db_instance`;
CREATE TABLE `db_db_instance`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '数据库名称',
  `data_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data用户账号',
  `etl_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'etl用户账号',
  `opr_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'opr用户账号',
  `rpt_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'rpt用户账号',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idex_id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_db_instance
-- ----------------------------
INSERT INTO `db_db_instance` VALUES (1, '网商贷oracle业务数据库', 'DistrlData', 'DistrlEtl', 'DistrlOpr', 'DistrlRpt', 1, '2019-12-17 15:48:12', 1, '2019-12-17 15:56:08', 0, 1);
INSERT INTO `db_db_instance` VALUES (2, '马上金融oracle业务数据库', 'DistrlMaData', 'DistrlMaEtl', 'DistrlMaOpr', 'DistrlMaRpt', 1, '2019-12-17 15:52:52', 1, '2019-12-17 15:52:52', 0, 1);
INSERT INTO `db_db_instance` VALUES (3, '232', '2323', '323', '23', '2323', 1, '2019-12-17 15:57:53', 1, '2019-12-17 15:57:53', 1, 1);
INSERT INTO `db_db_instance` VALUES (4, '234', '4234234', '234', '23', '234', 1, '2019-12-17 15:58:02', 1, '2019-12-17 15:58:02', 1, 1);
INSERT INTO `db_db_instance` VALUES (5, '3333333333333333', '234234', '423', '234', '4234', 1, '2019-12-17 15:58:14', 1, '2019-12-17 15:58:19', 1, 1);

-- ----------------------------
-- Table structure for db_table_info
-- ----------------------------
DROP TABLE IF EXISTS `db_table_info`;
CREATE TABLE `db_table_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `type_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '字段类型',
  `type_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '字段类型长度',
  `is_empty` int(2) NULL DEFAULT NULL COMMENT '是否为空 1为空2不为空',
  `default_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '默认值 ',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '注释说明',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父级菜单',
  `category` int(2) NULL DEFAULT NULL COMMENT '1表，2字段',
  `db_instance_id` int(11) NULL DEFAULT NULL COMMENT '数据库id',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  `is_leaf` int(2) UNSIGNED NULL DEFAULT 0 COMMENT '是否是叶子节点，0是，1不是',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_db_table_info_id`(`id`) USING BTREE,
  INDEX `idx_db_table_info_dbid`(`db_instance_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_table_info
-- ----------------------------
INSERT INTO `db_table_info` VALUES (12, 'batch_bank_info', 'VARCHAR2', '2', 1, '', '银行信息表', 0, 1, 1, 0, 0, 1, '2019-12-18 14:52:25', 1, '2019-12-18 15:00:49', 1);
INSERT INTO `db_table_info` VALUES (13, 'seria_no', 'NUMBER', '19', 1, NULL, '表主键', 12, 2, 1, 0, 0, 1, '2019-12-18 14:53:33', 1, '2019-12-18 14:53:33', 1);
INSERT INTO `db_table_info` VALUES (14, 'third_apply_info', 'VARCHAR2', '64', 2, 'abc', '第三方申请信息', 12, 2, 1, 0, 0, 1, '2019-12-18 14:54:21', 1, '2019-12-18 14:54:21', 1);
INSERT INTO `db_table_info` VALUES (15, 'bank_no', 'NUMBER', '10', 1, '3', '银行编号', 12, 2, 1, 0, 0, 1, '2019-12-18 14:54:43', 1, '2019-12-18 15:18:08', 1);
INSERT INTO `db_table_info` VALUES (18, 'bank_no_create_date', 'DATE', '10', 1, 'sysDate', '银行编号', 12, 2, 1, 0, 0, 1, '2019-12-18 15:18:11', 1, '2019-12-18 15:21:38', 1);

-- ----------------------------
-- Table structure for mk_client
-- ----------------------------
DROP TABLE IF EXISTS `mk_client`;
CREATE TABLE `mk_client`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端id',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密钥',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源集合',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权范围',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权类型',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调地址',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) NOT NULL COMMENT '令牌过期秒数',
  `refresh_token_validity` int(11) NOT NULL COMMENT '刷新令牌过期秒数',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附加说明',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自动授权',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NOT NULL COMMENT '状态',
  `is_deleted` int(2) NOT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_client
-- ----------------------------
INSERT INTO `mk_client` VALUES (2, 'systemId', 'system_secret', '', 'all', 'refresh_token,password,authorization_code', 'http://localhost:9527', '', 3600, 604800, '前后端面分离时，前端接入的authorcode', '', 1, '2019-03-24 10:42:29', 1, '2019-12-12 20:44:29', 1, 0);

-- ----------------------------
-- Table structure for mk_code
-- ----------------------------
DROP TABLE IF EXISTS `mk_code`;
CREATE TABLE `mk_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `datasource_id` bigint(64) NULL DEFAULT NULL COMMENT '数据源主键',
  `service_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  `code_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `table_prefix` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表前缀',
  `pk_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主键名',
  `package_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端包名',
  `base_mode` int(2) NULL DEFAULT NULL COMMENT '基础业务模式',
  `wrap_mode` int(2) NULL DEFAULT NULL COMMENT '包装器模式',
  `api_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端路径',
  `web_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路径',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_code
-- ----------------------------
INSERT INTO `mk_code` VALUES (1, 1, 'springcloud-demo', '通知公告', 'mk_notice', 'mk_', 'id', 'org.springclouddev.desktop', 2, 2, 'D:\\Develop\\WorkSpace\\Git\\\\springcloud-ops\\springcloud-develop', 'D:\\Develop\\WorkSpace\\Git\\Sword', 0);
INSERT INTO `mk_code` VALUES (9, 1, 'springcloud-develop', '数据库管理', 'db_db_instance', 'db_', 'id', 'org.springclouddev.develop', 2, 2, 'D:\\Develop\\WorkSpace\\Git\\\\springcloud-ops\\springcloud-develop', 'D:\\Develop\\WorkSpace\\Git\\Sword', 1);
INSERT INTO `mk_code` VALUES (10, 1, 'springcloud-develop', '表元数据管理', 'db_table_info', 'db_', 'id', 'org.springclouddev.develop', 2, 2, 'D:\\Develop\\WorkSpace\\Git\\\\springcloud-ops\\springcloud-develop', 'D:\\Develop\\WorkSpace\\Git\\Sword', 0);

-- ----------------------------
-- Table structure for mk_datasource
-- ----------------------------
DROP TABLE IF EXISTS `mk_datasource`;
CREATE TABLE `mk_datasource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `driver_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '驱动类',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '连接地址',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint(64) NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint(64) NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据源配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_datasource
-- ----------------------------
INSERT INTO `mk_datasource` VALUES (1, 'mysql', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/mk?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroTIMESTAMP Behavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true', 'root', '1qaz2wsx', 'mysql', 1, 1, '2019-08-14 11:43:06', 1, '2019-12-16 22:28:10', 1, 0);
INSERT INTO `mk_datasource` VALUES (2, 'postgresql', 'org.postgresql.Driver', 'jdbc:postgresql://127.0.0.1:5432/blade', 'postgres', '123456', 'postgresql', 1, 1, '2019-08-14 11:43:41', 1, '2019-08-14 11:43:41', 1, 0);
INSERT INTO `mk_datasource` VALUES (3, 'oracle', 'oracle.jdbc.OracleDriver', 'jdbc:oracle:thin:@127.0.0.1:49161:orcl', 'root', 'root', 'oracle', 1, 1, '2019-08-14 11:44:03', 1, '2019-08-14 11:44:03', 1, 0);

-- ----------------------------
-- Table structure for mk_dept
-- ----------------------------
DROP TABLE IF EXISTS `mk_dept`;
CREATE TABLE `mk_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父主键',
  `dept_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名',
  `full_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门全称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  `is_leaf` int(2) NOT NULL DEFAULT 0 COMMENT '是否是叶子节点，0是，1不是',
  `parent_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所有父结点id的集合，逗号分隔',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_dept
-- ----------------------------
INSERT INTO `mk_dept` VALUES (1, '000000', 0, '软竹科技', '上海软件竹科技有限公司', 1, '2222', 0, 1, NULL);
INSERT INTO `mk_dept` VALUES (2, '000000', 1, '北京软竹', '北京软竹科技有限公司', 1, '', 0, 1, NULL);
INSERT INTO `mk_dept` VALUES (3, '000000', 1, '深圳软竹', '深圳软竹科技有限公司', 1, '32342', 0, 0, NULL);

-- ----------------------------
-- Table structure for mk_dict
-- ----------------------------
DROP TABLE IF EXISTS `mk_dict`;
CREATE TABLE `mk_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父主键',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典码',
  `dict_key` int(2) NULL DEFAULT NULL COMMENT '字典值',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典备注',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  `is_leaf` int(2) NOT NULL DEFAULT 0 COMMENT '是否是叶子节点，0是，1不是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_dict
-- ----------------------------
INSERT INTO `mk_dict` VALUES (1, 0, 'sex', -1, '性别', 1, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (2, 1, 'sex', 1, '男', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (3, 1, 'sex', 2, '女', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (4, 0, 'notice', -1, '通知类型', 2, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (5, 4, 'notice', 1, '发布通知', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (6, 4, 'notice', 2, '批转通知', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (7, 4, 'notice', 3, '转发通知', 3, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (8, 4, 'notice', 4, '指示通知', 4, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (9, 4, 'notice', 5, '任免通知', 5, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (10, 4, 'notice', 6, '事务通知', 6, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (11, 0, 'menu_category', -1, '菜单类型', 3, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (12, 11, 'menu_category', 1, '菜单', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (13, 11, 'menu_category', 2, '按钮', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (14, 0, 'button_func', -1, '按钮功能', 4, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (15, 14, 'button_func', 1, '工具栏', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (16, 14, 'button_func', 2, '操作栏', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (17, 14, 'button_func', 3, '工具操作栏', 3, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (18, 0, 'yes_no', -1, '是否', 5, NULL, 0, 1);
INSERT INTO `mk_dict` VALUES (19, 18, 'yes_no', 1, '否', 1, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (20, 18, 'yes_no', 2, '是', 2, NULL, 0, 0);
INSERT INTO `mk_dict` VALUES (22, 0, 'validDate', -1, '有效期', 7, '产品的有效期选项', 0, 1);
INSERT INTO `mk_dict` VALUES (23, 22, 'validDate', 1, '一年', 1, '一年有效期', 0, 0);
INSERT INTO `mk_dict` VALUES (24, 22, 'validDate', 2, '半年', 2, '半年有效期', 0, 0);
INSERT INTO `mk_dict` VALUES (26, 0, 'tableinfo', -1, '表元数据类型', 7, '表元数据类型', 0, 1);
INSERT INTO `mk_dict` VALUES (27, 26, 'tableinfo', 1, '数据表', 1, '数据表', 0, 0);
INSERT INTO `mk_dict` VALUES (28, 26, 'tableinfo', 2, '表字段', 2, '表字段', 0, 0);
INSERT INTO `mk_dict` VALUES (29, 0, '22', -1, '22', 2, '2', 1, 0);
INSERT INTO `mk_dict` VALUES (30, 0, '323', -1, '234', 33, '3', 1, 0);
INSERT INTO `mk_dict` VALUES (31, 0, '2341wfads', -1, '234', 2323, '4234', 1, 0);

-- ----------------------------
-- Table structure for mk_log_api
-- ----------------------------
DROP TABLE IF EXISTS `mk_log_api`;
CREATE TABLE `mk_log_api`  (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器环境',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '日志标题',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作提交的数据',
  `time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mk_log_error
-- ----------------------------
DROP TABLE IF EXISTS `mk_log_error`;
CREATE TABLE `mk_log_error`  (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统环境',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '堆栈',
  `exception_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常名',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常信息',
  `line_number` int(11) NULL DEFAULT NULL COMMENT '错误行数',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `file_name` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作提交的数据',
  `time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '错误日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mk_log_usual
-- ----------------------------
DROP TABLE IF EXISTS `mk_log_usual`;
CREATE TABLE `mk_log_usual`  (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务ID',
  `server_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名',
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器IP地址',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统环境',
  `log_level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志级别',
  `log_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志业务id',
  `log_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志数据',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `remote_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户代理',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作提交的数据',
  `time` timestamp(0) NULL DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通用日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mk_menu
-- ----------------------------
DROP TABLE IF EXISTS `mk_menu`;
CREATE TABLE `mk_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父级菜单',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单别名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单资源',
  `sort` int(2) NULL DEFAULT NULL COMMENT '排序',
  `category` int(2) NULL DEFAULT NULL COMMENT '菜单类型',
  `action` int(2) NULL DEFAULT 0 COMMENT '操作按钮类型',
  `is_open` int(2) NULL DEFAULT 1 COMMENT '是否打开新页面',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  `is_leaf` int(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否是叶子节点，0是，1不是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_menu
-- ----------------------------
INSERT INTO `mk_menu` VALUES (1, 0, 'desk', '工作台', 'menu', '/desk', 'table', 1, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (2, 1, 'notice', '通知公告', 'menu', '/desk/notice', 'email', 1, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (3, 0, 'system', '系统管理', 'menu', '/system', 'list', 2, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (4, 3, 'user', '用户管理', 'menu', '/system/user', 'user', 1, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (5, 3, 'dept', '部门管理', 'menu', '/system/dept', 'peoples', 2, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (6, 3, 'dict', '字典管理', 'menu', '/system/dict', 'excel', 3, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (7, 3, 'menu', '菜单管理', 'menu', '/system/menu', 'tree-table', 4, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (8, 3, 'role', '角色管理', 'menu', '/permission/role', 'people', 5, 1, 0, 1, '', 1, 1);
INSERT INTO `mk_menu` VALUES (9, 3, 'params', '参数管理', 'menu', '/system/param', '306', 6, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (10, 0, 'monitor', '系统监控', 'menu', '/monitor', 'international', 6, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (11, 10, 'doc', '接口文档', 'menu', 'http://localhost/doc.html', 'excel', 1, 1, 0, 2, '', 0, 0);
INSERT INTO `mk_menu` VALUES (12, 10, 'admin', '服务治理', 'menu', 'http://localhost:7002', 'component', 2, 1, 0, 2, '', 0, 0);
INSERT INTO `mk_menu` VALUES (13, 10, 'log', '日志管理', 'menu', '/monitor/log', 'form', 3, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (14, 13, 'log_usual', '通用日志', 'menu', '/monitor/log/usual', 'attention', 1, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (15, 13, 'log_api', '接口日志', 'menu', '/monitor/log/api', 'nested', 2, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (16, 13, 'log_error', '错误日志', 'menu', '/monitor/log/error', '404', 3, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (17, 0, 'tool', '研发工具', 'menu', '/tool', 'eye-open', 7, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (18, 17, 'codes', '代码生成', 'menu', '/tool/code', 'rainm', 10, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (19, 2, 'notice_add', '新增', 'add', '/desk/notice/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (20, 2, 'notice_edit', '修改', 'edit', '/desk/notice/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (21, 2, 'notice_delete', '删除', 'delete', '/api/springcloud-desk/notice/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (22, 2, 'notice_view', '查看', 'view', '/desk/notice/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (23, 4, 'user_add', '新增', 'add', '/system/user/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (24, 4, 'user_edit', '修改', 'edit', '/system/user/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (25, 4, 'user_delete', '删除', 'delete', '/api/springcloud-user/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (26, 4, 'user_role', '角色配置', 'role', NULL, 'user-add', 4, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (27, 4, 'user_reset', '密码重置', 'reset-password', '/api/springcloud-user/reset-password', 'retweet', 5, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (28, 4, 'user_view', '查看', 'view', '/system/user/view', 'file-text', 6, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (29, 5, 'dept_add', '新增', 'add', '/system/dept/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (30, 5, 'dept_edit', '修改', 'edit', '/system/dept/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (31, 5, 'dept_delete', '删除', 'delete', '/api/springcloud-system/dept/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (32, 5, 'dept_view', '查看', 'view', '/system/dept/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (33, 6, 'dict_add', '新增', 'add', '/system/dict/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (34, 6, 'dict_edit', '修改', 'edit', '/system/dict/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (35, 6, 'dict_delete', '删除', 'delete', '/api/springcloud-system/dict/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (36, 6, 'dict_view', '查看', 'view', '/system/dict/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (37, 7, 'menu_add', '新增', 'add', '/system/menu/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (38, 7, 'menu_edit', '修改', 'edit', '/system/menu/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (39, 7, 'menu_delete', '删除', 'delete', '/api/springcloud-system/menu/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (40, 7, 'menu_view', '查看', 'view', '/system/menu/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (41, 8, 'role_add', '新增', 'add', '/system/role/add', 'plus', 1, 2, 1, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (42, 8, 'role_edit', '修改', 'edit', '/system/role/edit', 'form', 2, 2, 2, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (43, 8, 'role_delete', '删除', 'delete', '/api/springcloud-system/role/remove', 'delete', 3, 2, 3, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (44, 8, 'role_view', '查看', 'view', '/system/role/view', 'file-text', 4, 2, 2, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (45, 9, 'param_add', '新增', 'add', '/system/param/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (46, 9, 'param_edit', '修改', 'edit', '/system/param/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (47, 9, 'param_delete', '删除', 'delete', '/api/springcloud-system/param/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (48, 9, 'param_view', '查看', 'view', '/system/param/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (49, 14, 'log_usual_view', '查看', 'view', '/monitor/log/usual/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (50, 15, 'log_api_view', '查看', 'view', '/monitor/log/api/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (51, 16, 'log_error_view', '查看', 'view', '/monitor/log/error/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (52, 18, 'code_add', '新增', 'add', '/tool/code/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (53, 18, 'code_edit', '修改', 'edit', '/tool/code/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (54, 18, 'code_delete', '删除', 'delete', '/api/springcloud-system/code/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (55, 18, 'code_view', '查看', 'view', '/tool/code/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (56, 3, 'tenant', '租户管理', 'menu', '/system/tenant', 'tree', 7, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (57, 56, 'tenant_add', '新增', 'add', '/system/tenant/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (58, 56, 'tenant_edit', '修改', 'edit', '/system/tenant/edit', 'form', 2, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (59, 56, 'tenant_delete', '删除', 'delete', '/api/springcloud-system/tenant/remove', 'delete', 3, 2, 3, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (60, 56, 'tenant_view', '查看', 'view', '/system/tenant/view', 'file-text', 4, 2, 2, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (61, 3, 'client', '应用管理', 'menu', '/system/client', 'exit-fullscreen', 8, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (62, 61, 'client_add', '新增', 'add', '/system/client/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (63, 61, 'client_edit', '修改', 'edit', '/system/client/edit', 'form', 2, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (64, 61, 'client_delete', '删除', 'delete', '/api/springcloud-system/client/remove', 'delete', 3, 2, 3, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (65, 61, 'client_view', '查看', 'view', '/system/client/view', 'file-text', 4, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (66, 17, 'datasource', '数据源管理', 'menu', '/tool/datasource', 'carderror', 9, 1, 0, 1, '', 0, 1);
INSERT INTO `mk_menu` VALUES (67, 66, 'datasource_add', '新增', 'add', '/tool/datasource/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (68, 66, 'datasource_edit', '修改', 'edit', '/tool/datasource/edit', 'form', 2, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (69, 66, 'datasource_delete', '删除', 'delete', '/api/springcloud-develop/datasource/remove', 'delete', 3, 2, 3, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (70, 66, 'datasource_view', '查看', 'view', '/tool/datasource/view', 'file-text', 4, 2, 2, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (71, 17, 'dbinstance', '数据库管理', 'menu', '/tool/database', 'charge', 1, 1, 0, 1, '管理数据库的各种用户', 0, 0);
INSERT INTO `mk_menu` VALUES (72, 17, 'tableinfo', '创建数据表', 'menu', '/tool/tables', 'chart', 2, 1, 0, 1, '对数据库中的表结构进行管理，创建并生成dml语句和ddl语句', 0, 0);
INSERT INTO `mk_menu` VALUES (73, 0, 'permissions', '权限管理', 'menu', '/permission', 'documentation', 3, 1, 0, 1, '系统权限管理功能', 0, 0);
INSERT INTO `mk_menu` VALUES (74, 73, 'role', '角色管理', 'menu', '/permission/role', 'theme', 1, 1, 0, 1, '系统角色管理', 0, 0);
INSERT INTO `mk_menu` VALUES (75, 73, 'datarole', '数据权限', 'menu', '/permission/datarole', 'skin', 2, 1, 0, 1, '可访问数据的权限', 0, 0);
INSERT INTO `mk_menu` VALUES (76, 73, 'interfacerole', '接口权限', 'menu', '/permission/interfacerole', 'eye', 3, 1, 0, 1, '系统的接口权限管理', 0, 0);
INSERT INTO `mk_menu` VALUES (79, 0, 'dbinstance', '', 'menu', '/develop/dbinstance', NULL, 1, 1, 0, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (80, 71, 'dbinstance_add', '新增', 'add', '/develop/dbinstance/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (81, 71, 'dbinstance_edit', '修改', 'edit', '/develop/dbinstance/edit', 'form', 2, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (82, 71, 'dbinstance_delete', '删除', 'delete', '/api/springcloud-develop/dbinstance/remove', 'delete', 3, 2, 1, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (83, 71, 'dbinstance_view', '查看', 'view', '/develop/dbinstance/view', 'file-text', 4, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (84, 0, 'tableinfo', '', 'menu', '/develop/tableinfo', NULL, 1, 1, 0, 1, NULL, 1, 0);
INSERT INTO `mk_menu` VALUES (85, 72, 'tableinfo_add', '新增', 'add', '/develop/tableinfo/add', 'plus', 1, 2, 1, 1, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (86, 72, 'tableinfo_edit', '修改', 'edit', '/develop/tableinfo/edit', 'form', 2, 2, 1, 2, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (87, 72, 'tableinfo_delete', '删除', 'delete', '/api/springcloud-develop/tableinfo/remove', 'delete', 3, 2, 1, 3, NULL, 0, 0);
INSERT INTO `mk_menu` VALUES (88, 72, 'tableinfo_view', '导出DDL', 'view', '/develop/tableinfo/view', 'file-text', 4, 2, 1, 2, NULL, 0, 0);

-- ----------------------------
-- Table structure for mk_notice
-- ----------------------------
DROP TABLE IF EXISTS `mk_notice`;
CREATE TABLE `mk_notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `category` int(11) NULL DEFAULT NULL COMMENT '类型',
  `release_time` timestamp(0) NULL DEFAULT NULL COMMENT '发布时间',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_notice
-- ----------------------------
INSERT INTO `mk_notice` VALUES (23, '000000', '测试公告', 3, '2018-12-31 20:03:31', '222', 1, '2018-12-05 20:03:31', 1, '2018-12-28 11:10:51', 1, 0);
INSERT INTO `mk_notice` VALUES (24, '000000', '测试公告2', 1, '2018-12-05 20:03:31', '333', 1, '2018-12-28 10:32:26', 1, '2018-12-28 11:10:34', 1, 0);
INSERT INTO `mk_notice` VALUES (25, '000000', '测试公告3', 6, '2018-12-29 00:00:00', '11111', 1, '2018-12-28 11:03:44', 1, '2018-12-28 11:10:28', 1, 0);

-- ----------------------------
-- Table structure for mk_param
-- ----------------------------
DROP TABLE IF EXISTS `mk_param`;
CREATE TABLE `mk_param`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `param_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名',
  `param_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_param
-- ----------------------------
INSERT INTO `mk_param` VALUES (1, '是否开启注册功能', 'account.registerUser', 'true', '开启注册', 1, '2018-12-28 12:19:01', 1, '2018-12-28 12:19:01', 1, 0);
INSERT INTO `mk_param` VALUES (2, '账号初始密码', 'account.initPassword', '123456', '初始密码', 1, '2018-12-28 12:19:01', 1, '2018-12-28 12:19:01', 1, 0);

-- ----------------------------
-- Table structure for mk_role
-- ----------------------------
DROP TABLE IF EXISTS `mk_role`;
CREATE TABLE `mk_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父主键',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `role_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色别名',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  `is_leaf` int(2) NOT NULL DEFAULT 0 COMMENT '是否是叶子节点，0是，1不是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_role
-- ----------------------------
INSERT INTO `mk_role` VALUES (1, '000000', 0, '超级管理员', 1, 'administrator', 0, 1);
INSERT INTO `mk_role` VALUES (2, '000000', 0, '用户', 2, 'user', 0, 1);

-- ----------------------------
-- Table structure for mk_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `mk_role_menu`;
CREATE TABLE `mk_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 645 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

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
INSERT INTO `mk_role_menu` VALUES (588, 20, 1);
INSERT INTO `mk_role_menu` VALUES (589, 21, 1);
INSERT INTO `mk_role_menu` VALUES (590, 22, 1);
INSERT INTO `mk_role_menu` VALUES (591, 23, 1);
INSERT INTO `mk_role_menu` VALUES (592, 24, 1);
INSERT INTO `mk_role_menu` VALUES (593, 25, 1);
INSERT INTO `mk_role_menu` VALUES (594, 26, 1);
INSERT INTO `mk_role_menu` VALUES (595, 27, 1);
INSERT INTO `mk_role_menu` VALUES (596, 28, 1);
INSERT INTO `mk_role_menu` VALUES (597, 29, 1);
INSERT INTO `mk_role_menu` VALUES (598, 30, 1);
INSERT INTO `mk_role_menu` VALUES (599, 31, 1);
INSERT INTO `mk_role_menu` VALUES (600, 32, 1);
INSERT INTO `mk_role_menu` VALUES (601, 33, 1);
INSERT INTO `mk_role_menu` VALUES (602, 34, 1);
INSERT INTO `mk_role_menu` VALUES (603, 35, 1);
INSERT INTO `mk_role_menu` VALUES (604, 36, 1);
INSERT INTO `mk_role_menu` VALUES (605, 37, 1);
INSERT INTO `mk_role_menu` VALUES (606, 38, 1);
INSERT INTO `mk_role_menu` VALUES (607, 39, 1);
INSERT INTO `mk_role_menu` VALUES (608, 40, 1);
INSERT INTO `mk_role_menu` VALUES (609, 45, 1);
INSERT INTO `mk_role_menu` VALUES (610, 46, 1);
INSERT INTO `mk_role_menu` VALUES (611, 47, 1);
INSERT INTO `mk_role_menu` VALUES (612, 48, 1);
INSERT INTO `mk_role_menu` VALUES (613, 57, 1);
INSERT INTO `mk_role_menu` VALUES (614, 58, 1);
INSERT INTO `mk_role_menu` VALUES (615, 59, 1);
INSERT INTO `mk_role_menu` VALUES (616, 60, 1);
INSERT INTO `mk_role_menu` VALUES (617, 62, 1);
INSERT INTO `mk_role_menu` VALUES (618, 63, 1);
INSERT INTO `mk_role_menu` VALUES (619, 64, 1);
INSERT INTO `mk_role_menu` VALUES (620, 65, 1);
INSERT INTO `mk_role_menu` VALUES (621, 11, 1);
INSERT INTO `mk_role_menu` VALUES (622, 12, 1);
INSERT INTO `mk_role_menu` VALUES (623, 49, 1);
INSERT INTO `mk_role_menu` VALUES (624, 50, 1);
INSERT INTO `mk_role_menu` VALUES (625, 51, 1);
INSERT INTO `mk_role_menu` VALUES (626, 52, 1);
INSERT INTO `mk_role_menu` VALUES (627, 53, 1);
INSERT INTO `mk_role_menu` VALUES (628, 54, 1);
INSERT INTO `mk_role_menu` VALUES (629, 55, 1);
INSERT INTO `mk_role_menu` VALUES (630, 67, 1);
INSERT INTO `mk_role_menu` VALUES (631, 68, 1);
INSERT INTO `mk_role_menu` VALUES (632, 69, 1);
INSERT INTO `mk_role_menu` VALUES (633, 70, 1);
INSERT INTO `mk_role_menu` VALUES (634, 80, 1);
INSERT INTO `mk_role_menu` VALUES (635, 81, 1);
INSERT INTO `mk_role_menu` VALUES (636, 82, 1);
INSERT INTO `mk_role_menu` VALUES (637, 83, 1);
INSERT INTO `mk_role_menu` VALUES (638, 85, 1);
INSERT INTO `mk_role_menu` VALUES (639, 86, 1);
INSERT INTO `mk_role_menu` VALUES (640, 87, 1);
INSERT INTO `mk_role_menu` VALUES (641, 88, 1);
INSERT INTO `mk_role_menu` VALUES (642, 74, 1);
INSERT INTO `mk_role_menu` VALUES (643, 75, 1);
INSERT INTO `mk_role_menu` VALUES (644, 76, 1);

-- ----------------------------
-- Table structure for mk_tenant
-- ----------------------------
DROP TABLE IF EXISTS `mk_tenant`;
CREATE TABLE `mk_tenant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户ID',
  `tenant_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `linkman` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_tenant
-- ----------------------------
INSERT INTO `mk_tenant` VALUES (1, '000000', '管理组', 'admin', '666666', '管理组的地址', 1, '2019-01-01 00:00:39', 1, '2019-01-01 00:00:39', 1, 0);

-- ----------------------------
-- Table structure for mk_user
-- ----------------------------
DROP TABLE IF EXISTS `mk_user`;
CREATE TABLE `mk_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `account` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真名',
  `avatar` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `birthday` timestamp(0) NULL DEFAULT NULL COMMENT '生日',
  `sex` smallint(6) NULL DEFAULT NULL COMMENT '性别',
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `dept_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门id',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int(2) NULL DEFAULT 0 COMMENT '是否已删除',
  `introduction` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mk_user
-- ----------------------------
INSERT INTO `mk_user` VALUES (1, '000000', 'admin', '90b9aa7e25f80cf4f64e990b78a9fc5ebd6cecad', '管理员', '管理员', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'admin@xxxxxx.vip', '22233322', '2018-08-08 00:00:00', 1, '1', '1', 1, '2018-08-08 00:00:00', 1, '2018-08-08 00:00:00', 1, 0, '我是超级管理员');

SET FOREIGN_KEY_CHECKS = 1;
