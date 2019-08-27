/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : apolloconfigdb

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 28/08/2019 02:13:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AppId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '应用名',
  `OrgId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '部门Id',
  `OrgName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '部门名字',
  `OwnerName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'ownerName',
  `OwnerEmail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'ownerEmail',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `AppId`(`AppId`(191)) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_Name`(`Name`(191)) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO `app` VALUES (1, '10001', 'spring-boot-2', 'TEST1', '样例部门1', 'apollo', 'apollo@acme.com', b'0', 'apollo', '2019-08-27 22:05:48', 'apollo', '2019-08-27 22:05:48');

-- ----------------------------
-- Table structure for appnamespace
-- ----------------------------
DROP TABLE IF EXISTS `appnamespace`;
CREATE TABLE `appnamespace`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'namespace名字，注意，需要全局唯一',
  `AppId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'app id',
  `Format` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'properties' COMMENT 'namespace的format类型',
  `IsPublic` bit(1) NOT NULL DEFAULT b'0' COMMENT 'namespace是否为公共',
  `Comment` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_AppId`(`AppId`) USING BTREE,
  INDEX `Name_AppId`(`Name`, `AppId`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '应用namespace定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appnamespace
-- ----------------------------
INSERT INTO `appnamespace` VALUES (1, 'application', '10001', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2019-08-27 22:05:48', 'apollo', '2019-08-27 22:05:48');
INSERT INTO `appnamespace` VALUES (2, 'common.log4j2', '10001', 'properties', b'1', '共用的log4j2的配置', b'0', 'apollo', '2019-08-27 23:32:13', 'apollo', '2019-08-27 23:32:13');

-- ----------------------------
-- Table structure for audit
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `EntityName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '表名',
  `EntityId` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '记录ID',
  `OpName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '操作类型',
  `Comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日志审计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit
-- ----------------------------
INSERT INTO `audit` VALUES (1, 'App', 1, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 22:05:48', NULL, '2019-08-27 22:05:48');
INSERT INTO `audit` VALUES (2, 'AppNamespace', 1, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 22:05:48', NULL, '2019-08-27 22:05:48');
INSERT INTO `audit` VALUES (3, 'Cluster', 1, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 22:05:48', NULL, '2019-08-27 22:05:48');
INSERT INTO `audit` VALUES (4, 'Namespace', 1, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 22:05:48', NULL, '2019-08-27 22:05:48');
INSERT INTO `audit` VALUES (5, 'Item', 1, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 22:06:56', NULL, '2019-08-27 22:06:56');
INSERT INTO `audit` VALUES (6, 'Release', 1, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 22:07:12', NULL, '2019-08-27 22:07:12');
INSERT INTO `audit` VALUES (7, 'ReleaseHistory', 1, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 22:07:12', NULL, '2019-08-27 22:07:12');
INSERT INTO `audit` VALUES (8, 'Namespace', 2, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:32:13', NULL, '2019-08-27 23:32:13');
INSERT INTO `audit` VALUES (9, 'AppNamespace', 2, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:32:13', NULL, '2019-08-27 23:32:13');
INSERT INTO `audit` VALUES (10, 'Item', 2, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (11, 'Item', 3, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (12, 'Item', 4, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (13, 'Item', 5, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (14, 'Item', 6, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (15, 'Item', 7, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (16, 'Item', 8, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (17, 'Item', 9, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (18, 'Item', 10, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (19, 'Item', 11, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (20, 'Item', 12, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (21, 'Item', 13, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (22, 'Item', 14, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (23, 'Item', 15, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (24, 'Item', 16, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (25, 'Item', 17, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (26, 'Item', 18, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (27, 'Item', 19, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (28, 'Item', 20, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (29, 'Item', 21, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (30, 'Item', 22, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (31, 'Item', 23, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (32, 'Item', 24, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (33, 'Item', 25, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (34, 'Item', 26, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (35, 'Item', 27, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (36, 'Item', 28, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (37, 'Item', 29, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (38, 'Item', 30, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (39, 'Item', 31, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (40, 'Item', 32, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (41, 'Item', 33, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (42, 'Item', 34, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (43, 'Item', 35, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (44, 'Item', 36, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (45, 'Item', 37, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (46, 'Item', 38, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (47, 'Item', 39, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (48, 'Item', 40, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (49, 'Item', 41, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (50, 'Item', 42, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (51, 'Item', 43, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (52, 'Item', 44, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (53, 'Item', 45, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (54, 'Item', 46, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (55, 'Item', 47, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (56, 'Item', 48, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (57, 'Item', 49, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (58, 'Item', 50, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (59, 'Item', 51, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (60, 'Item', 52, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (61, 'Item', 53, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (62, 'Item', 54, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (63, 'Item', 55, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (64, 'Item', 56, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (65, 'Item', 57, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (66, 'Item', 58, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (67, 'Item', 59, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (68, 'Item', 60, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (69, 'Item', 61, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (70, 'Item', 62, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (71, 'Item', 63, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (72, 'Item', 64, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (73, 'Item', 65, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (74, 'Item', 66, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (75, 'Item', 67, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (76, 'Item', 68, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (77, 'Item', 69, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (78, 'Item', 70, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (79, 'Item', 71, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (80, 'Item', 72, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (81, 'ItemSet', NULL, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:34:53', NULL, '2019-08-27 23:34:53');
INSERT INTO `audit` VALUES (82, 'Release', 2, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:35:18', NULL, '2019-08-27 23:35:18');
INSERT INTO `audit` VALUES (83, 'ReleaseHistory', 2, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:35:18', NULL, '2019-08-27 23:35:18');
INSERT INTO `audit` VALUES (84, 'Item', 62, 'UPDATE', NULL, b'0', 'apollo', '2019-08-27 23:37:53', NULL, '2019-08-27 23:37:53');
INSERT INTO `audit` VALUES (85, 'Release', 3, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:37:59', NULL, '2019-08-27 23:37:59');
INSERT INTO `audit` VALUES (86, 'ReleaseHistory', 3, 'INSERT', NULL, b'0', 'apollo', '2019-08-27 23:37:59', NULL, '2019-08-27 23:37:59');
INSERT INTO `audit` VALUES (87, 'Item', 63, 'UPDATE', NULL, b'0', 'apollo', '2019-08-28 00:42:00', NULL, '2019-08-28 00:42:00');
INSERT INTO `audit` VALUES (88, 'Release', 4, 'INSERT', NULL, b'0', 'apollo', '2019-08-28 00:42:02', NULL, '2019-08-28 00:42:02');
INSERT INTO `audit` VALUES (89, 'ReleaseHistory', 4, 'INSERT', NULL, b'0', 'apollo', '2019-08-28 00:42:02', NULL, '2019-08-28 00:42:02');

-- ----------------------------
-- Table structure for cluster
-- ----------------------------
DROP TABLE IF EXISTS `cluster`;
CREATE TABLE `cluster`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '集群名字',
  `AppId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'App id',
  `ParentClusterId` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父cluster',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_AppId_Name`(`AppId`, `Name`) USING BTREE,
  INDEX `IX_ParentClusterId`(`ParentClusterId`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '集群' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cluster
-- ----------------------------
INSERT INTO `cluster` VALUES (1, 'default', '10001', 0, b'0', 'apollo', '2019-08-27 22:05:48', 'apollo', '2019-08-27 22:05:48');

-- ----------------------------
-- Table structure for commit
-- ----------------------------
DROP TABLE IF EXISTS `commit`;
CREATE TABLE `commit`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ChangeSets` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改变更集',
  `AppId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `NamespaceName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'namespaceName',
  `Comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `AppId`(`AppId`(191)) USING BTREE,
  INDEX `ClusterName`(`ClusterName`(191)) USING BTREE,
  INDEX `NamespaceName`(`NamespaceName`(191)) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'commit 历史表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commit
-- ----------------------------
INSERT INTO `commit` VALUES (1, '{\"createItems\":[{\"namespaceId\":1,\"key\":\"spring.boot.timeout\",\"value\":\"1000\",\"comment\":\"读取数据的过期时间\",\"lineNum\":1,\"id\":1,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 22:06:56\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 22:06:56\"}],\"updateItems\":[],\"deleteItems\":[]}', '10001', 'default', 'application', NULL, b'0', 'apollo', '2019-08-27 22:06:56', 'apollo', '2019-08-27 22:06:56');
INSERT INTO `commit` VALUES (2, '{\"createItems\":[{\"namespaceId\":2,\"key\":\"status\",\"value\":\"warn\",\"comment\":\"\",\"lineNum\":1,\"id\":2,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"strict\",\"value\":\"true\",\"comment\":\"\",\"lineNum\":2,\"id\":3,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"property.baseDir\",\"value\":\"logs\",\"comment\":\"\",\"lineNum\":3,\"id\":4,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"property.appName\",\"value\":\"log4j\",\"comment\":\"\",\"lineNum\":4,\"id\":5,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"property.pattern\",\"value\":\"[${appName}] %d [%t] %p %C{10}.%M:%L | %m%n\",\"comment\":\"\",\"lineNum\":5,\"id\":6,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"property.logSize\",\"value\":\"50MB\",\"comment\":\"\",\"lineNum\":6,\"id\":7,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"property.errorLogSize\",\"value\":\"50MB\",\"comment\":\"\",\"lineNum\":7,\"id\":8,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appenders\",\"value\":\"console,rolling,error\",\"comment\":\"\",\"lineNum\":8,\"id\":9,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.console.type\",\"value\":\"Console\",\"comment\":\"\",\"lineNum\":9,\"id\":10,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.console.name\",\"value\":\"STDOUT\",\"comment\":\"\",\"lineNum\":10,\"id\":11,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.console.layout.type\",\"value\":\"PatternLayout\",\"comment\":\"\",\"lineNum\":11,\"id\":12,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.console.layout.pattern\",\"value\":\"${pattern}\",\"comment\":\"\",\"lineNum\":12,\"id\":13,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.type\",\"value\":\"RollingFile\",\"comment\":\"\",\"lineNum\":13,\"id\":14,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.name\",\"value\":\"RollingFile\",\"comment\":\"\",\"lineNum\":14,\"id\":15,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.fileName\",\"value\":\"${baseDir}/${appName}.log\",\"comment\":\"\",\"lineNum\":15,\"id\":16,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.filePattern\",\"value\":\"${baseDir}/$${date:yyyy-MM}/${appName}-%d{yyyy-MM-dd}-%i.log\",\"comment\":\"\",\"lineNum\":16,\"id\":17,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.layout.type\",\"value\":\"PatternLayout\",\"comment\":\"\",\"lineNum\":17,\"id\":18,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.layout.pattern\",\"value\":\"${pattern}\",\"comment\":\"\",\"lineNum\":18,\"id\":19,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.policies.type\",\"value\":\"Policies\",\"comment\":\"\",\"lineNum\":19,\"id\":20,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.policies.time.type\",\"value\":\"TimeBasedTriggeringPolicy\",\"comment\":\"\",\"lineNum\":20,\"id\":21,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.policies.size.type\",\"value\":\"SizeBasedTriggeringPolicy\",\"comment\":\"\",\"lineNum\":21,\"id\":22,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.policies.size.size\",\"value\":\"${logSize}\",\"comment\":\"\",\"lineNum\":22,\"id\":23,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.type\",\"value\":\"DefaultRolloverStrategy\",\"comment\":\"\",\"lineNum\":23,\"id\":24,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.max\",\"value\":\"10\",\"comment\":\"\",\"lineNum\":24,\"id\":25,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.type\",\"value\":\"Delete\",\"comment\":\"\",\"lineNum\":25,\"id\":26,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.basePath\",\"value\":\"${baseDir}\",\"comment\":\"\",\"lineNum\":26,\"id\":27,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.maxDepth\",\"value\":\"2\",\"comment\":\"\",\"lineNum\":27,\"id\":28,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.type\",\"value\":\"IfFileName\",\"comment\":\"\",\"lineNum\":28,\"id\":29,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.glob\",\"value\":\"*/${appName}-*.log\",\"comment\":\"\",\"lineNum\":29,\"id\":30,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.iflastmodified.type\",\"value\":\"IfLastModified\",\"comment\":\"\",\"lineNum\":30,\"id\":31,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.iflastmodified.age\",\"value\":\"10d\",\"comment\":\"\",\"lineNum\":31,\"id\":32,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.type\",\"value\":\"IfAny\",\"comment\":\"\",\"lineNum\":32,\"id\":33,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\",\"value\":\"IfAccumulatedFileSize\",\"comment\":\"\",\"lineNum\":33,\"id\":34,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\",\"value\":\"256 MB\",\"comment\":\"\",\"lineNum\":34,\"id\":35,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\",\"value\":\"IfAccumulatedFileCount\",\"comment\":\"\",\"lineNum\":35,\"id\":36,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\",\"value\":\"10\",\"comment\":\"\",\"lineNum\":36,\"id\":37,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.type\",\"value\":\"RollingFile\",\"comment\":\"\",\"lineNum\":37,\"id\":38,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.name\",\"value\":\"ErrorRollingFile\",\"comment\":\"\",\"lineNum\":38,\"id\":39,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.fileName\",\"value\":\"${baseDir}/${appName}Error.log\",\"comment\":\"\",\"lineNum\":39,\"id\":40,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.filePattern\",\"value\":\"${baseDir}/$${date:yyyy-MM}/${appName}Error-%d{yyyy-MM-dd}-%i.log\",\"comment\":\"\",\"lineNum\":40,\"id\":41,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.layout.type\",\"value\":\"PatternLayout\",\"comment\":\"\",\"lineNum\":41,\"id\":42,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.layout.pattern\",\"value\":\"${pattern}\",\"comment\":\"\",\"lineNum\":42,\"id\":43,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.policies.type\",\"value\":\"Policies\",\"comment\":\"\",\"lineNum\":43,\"id\":44,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.policies.time.type\",\"value\":\"TimeBasedTriggeringPolicy\",\"comment\":\"\",\"lineNum\":44,\"id\":45,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.policies.size.type\",\"value\":\"SizeBasedTriggeringPolicy\",\"comment\":\"\",\"lineNum\":45,\"id\":46,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.policies.size.size\",\"value\":\"${errorLogSize}\",\"comment\":\"\",\"lineNum\":46,\"id\":47,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.type\",\"value\":\"DefaultRolloverStrategy\",\"comment\":\"\",\"lineNum\":47,\"id\":48,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.max\",\"value\":\"10\",\"comment\":\"\",\"lineNum\":48,\"id\":49,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.type\",\"value\":\"Delete\",\"comment\":\"\",\"lineNum\":49,\"id\":50,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.basePath\",\"value\":\"${baseDir}\",\"comment\":\"\",\"lineNum\":50,\"id\":51,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.maxDepth\",\"value\":\"2\",\"comment\":\"\",\"lineNum\":51,\"id\":52,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.type\",\"value\":\"IfFileName\",\"comment\":\"\",\"lineNum\":52,\"id\":53,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.glob\",\"value\":\"*/${appName}Error-*.log\",\"comment\":\"\",\"lineNum\":53,\"id\":54,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.iflastmodified.type\",\"value\":\"IfLastModified\",\"comment\":\"\",\"lineNum\":54,\"id\":55,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.iflastmodified.age\",\"value\":\"10d\",\"comment\":\"\",\"lineNum\":55,\"id\":56,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.type\",\"value\":\"IfAny\",\"comment\":\"\",\"lineNum\":56,\"id\":57,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\",\"value\":\"IfAccumulatedFileSize\",\"comment\":\"\",\"lineNum\":57,\"id\":58,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\",\"value\":\"256 MB\",\"comment\":\"\",\"lineNum\":58,\"id\":59,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\",\"value\":\"IfAccumulatedFileCount\",\"comment\":\"\",\"lineNum\":59,\"id\":60,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\",\"value\":\"10\",\"comment\":\"\",\"lineNum\":60,\"id\":61,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"logger.dm.name\",\"value\":\"com.dm\",\"comment\":\"\",\"lineNum\":61,\"id\":62,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"logger.dm.level\",\"value\":\"debug\",\"comment\":\"\",\"lineNum\":62,\"id\":63,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"logger.spring.name\",\"value\":\"org.springframework\",\"comment\":\"\",\"lineNum\":63,\"id\":64,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"logger.spring.level\",\"value\":\"info\",\"comment\":\"\",\"lineNum\":64,\"id\":65,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"logger.axis2.name\",\"value\":\"org.apache.axis2\",\"comment\":\"\",\"lineNum\":65,\"id\":66,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"logger.axis2.level\",\"value\":\"info\",\"comment\":\"\",\"lineNum\":66,\"id\":67,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"rootLogger.level\",\"value\":\"warn\",\"comment\":\"\",\"lineNum\":67,\"id\":68,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"rootLogger.appenderRef.stdout.ref\",\"value\":\"STDOUT\",\"comment\":\"\",\"lineNum\":68,\"id\":69,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"rootLogger.appenderRef.rolling.ref\",\"value\":\"RollingFile\",\"comment\":\"\",\"lineNum\":69,\"id\":70,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"rootLogger.appenderRef.error.ref\",\"value\":\"ErrorRollingFile\",\"comment\":\"\",\"lineNum\":70,\"id\":71,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},{\"namespaceId\":2,\"key\":\"rootLogger.appenderRef.error.level\",\"value\":\"error\",\"comment\":\"\",\"lineNum\":71,\"id\":72,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"}],\"updateItems\":[],\"deleteItems\":[]}', '10001', 'default', 'common.log4j2', NULL, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `commit` VALUES (3, '{\"createItems\":[],\"updateItems\":[{\"oldItem\":{\"namespaceId\":2,\"key\":\"logger.dm.name\",\"value\":\"com.dm\",\"comment\":\"\",\"lineNum\":61,\"id\":62,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},\"newItem\":{\"namespaceId\":2,\"key\":\"logger.dm.name\",\"value\":\"org.spring.test.apollo\",\"comment\":\"\",\"lineNum\":61,\"id\":62,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:37:52\"}}],\"deleteItems\":[]}', '10001', 'default', 'common.log4j2', NULL, b'0', 'apollo', '2019-08-27 23:37:53', 'apollo', '2019-08-27 23:37:53');
INSERT INTO `commit` VALUES (4, '{\"createItems\":[],\"updateItems\":[{\"oldItem\":{\"namespaceId\":2,\"key\":\"logger.dm.level\",\"value\":\"debug\",\"comment\":\"\",\"lineNum\":62,\"id\":63,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-27 23:34:53\"},\"newItem\":{\"namespaceId\":2,\"key\":\"logger.dm.level\",\"value\":\"info\",\"comment\":\"\",\"lineNum\":62,\"id\":63,\"isDeleted\":false,\"dataChangeCreatedBy\":\"apollo\",\"dataChangeCreatedTime\":\"2019-08-27 23:34:53\",\"dataChangeLastModifiedBy\":\"apollo\",\"dataChangeLastModifiedTime\":\"2019-08-28 00:41:59\"}}],\"deleteItems\":[]}', '10001', 'default', 'common.log4j2', NULL, b'0', 'apollo', '2019-08-28 00:42:00', 'apollo', '2019-08-28 00:42:00');

-- ----------------------------
-- Table structure for grayreleaserule
-- ----------------------------
DROP TABLE IF EXISTS `grayreleaserule`;
CREATE TABLE `grayreleaserule`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AppId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Cluster Name',
  `NamespaceName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Namespace Name',
  `BranchName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'branch name',
  `Rules` varchar(16000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '[]' COMMENT '灰度规则',
  `ReleaseId` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '灰度对应的release',
  `BranchStatus` tinyint(2) NULL DEFAULT 1 COMMENT '灰度分支状态: 0:删除分支,1:正在使用的规则 2：全量发布',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_Namespace`(`AppId`, `ClusterName`, `NamespaceName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '灰度规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for instance
-- ----------------------------
DROP TABLE IF EXISTS `instance`;
CREATE TABLE `instance`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `AppId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `DataCenter` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Data Center Name',
  `Ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'instance ip',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  UNIQUE INDEX `IX_UNIQUE_KEY`(`AppId`, `ClusterName`, `Ip`, `DataCenter`) USING BTREE,
  INDEX `IX_IP`(`Ip`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '使用配置的应用实例' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of instance
-- ----------------------------
INSERT INTO `instance` VALUES (1, '10001', 'default', '', '192.168.1.3', '2019-08-27 22:35:31', '2019-08-27 22:35:31');

-- ----------------------------
-- Table structure for instanceconfig
-- ----------------------------
DROP TABLE IF EXISTS `instanceconfig`;
CREATE TABLE `instanceconfig`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `InstanceId` int(11) UNSIGNED NULL DEFAULT NULL COMMENT 'Instance Id',
  `ConfigAppId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Config App Id',
  `ConfigClusterName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Config Cluster Name',
  `ConfigNamespaceName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Config Namespace Name',
  `ReleaseKey` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '发布的Key',
  `ReleaseDeliveryTime` timestamp(0) NULL DEFAULT NULL COMMENT '配置获取时间',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  UNIQUE INDEX `IX_UNIQUE_KEY`(`InstanceId`, `ConfigAppId`, `ConfigNamespaceName`) USING BTREE,
  INDEX `IX_ReleaseKey`(`ReleaseKey`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_Valid_Namespace`(`ConfigAppId`, `ConfigClusterName`, `ConfigNamespaceName`, `DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '应用实例的配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of instanceconfig
-- ----------------------------
INSERT INTO `instanceconfig` VALUES (1, 1, '10001', 'default', 'application', '20190827220712-52c094da42ab4eba', '2019-08-27 22:35:30', '2019-08-27 22:35:30', '2019-08-27 22:35:30');
INSERT INTO `instanceconfig` VALUES (2, 1, '10001', 'default', 'common.log4j2', '20190828004202-114b94da42ab4ebd', '2019-08-28 00:42:10', '2019-08-28 00:33:35', '2019-08-28 00:42:11');

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `NamespaceId` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '集群NamespaceId',
  `Key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '配置项Key',
  `Value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置项值',
  `Comment` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '注释',
  `LineNum` int(10) UNSIGNED NULL DEFAULT 0 COMMENT '行号',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_GroupId`(`NamespaceId`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '配置项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, 1, 'spring.boot.timeout', '1000', '读取数据的过期时间', 1, b'0', 'apollo', '2019-08-27 22:06:56', 'apollo', '2019-08-27 22:06:56');
INSERT INTO `item` VALUES (2, 2, 'status', 'warn', '', 1, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (3, 2, 'strict', 'true', '', 2, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (4, 2, 'property.baseDir', 'logs', '', 3, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (5, 2, 'property.appName', 'log4j', '', 4, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (6, 2, 'property.pattern', '[${appName}] %d [%t] %p %C{10}.%M:%L | %m%n', '', 5, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (7, 2, 'property.logSize', '50MB', '', 6, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (8, 2, 'property.errorLogSize', '50MB', '', 7, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (9, 2, 'appenders', 'console,rolling,error', '', 8, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (10, 2, 'appender.console.type', 'Console', '', 9, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (11, 2, 'appender.console.name', 'STDOUT', '', 10, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (12, 2, 'appender.console.layout.type', 'PatternLayout', '', 11, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (13, 2, 'appender.console.layout.pattern', '${pattern}', '', 12, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (14, 2, 'appender.rolling.type', 'RollingFile', '', 13, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (15, 2, 'appender.rolling.name', 'RollingFile', '', 14, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (16, 2, 'appender.rolling.fileName', '${baseDir}/${appName}.log', '', 15, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (17, 2, 'appender.rolling.filePattern', '${baseDir}/$${date:yyyy-MM}/${appName}-%d{yyyy-MM-dd}-%i.log', '', 16, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (18, 2, 'appender.rolling.layout.type', 'PatternLayout', '', 17, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (19, 2, 'appender.rolling.layout.pattern', '${pattern}', '', 18, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (20, 2, 'appender.rolling.policies.type', 'Policies', '', 19, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (21, 2, 'appender.rolling.policies.time.type', 'TimeBasedTriggeringPolicy', '', 20, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (22, 2, 'appender.rolling.policies.size.type', 'SizeBasedTriggeringPolicy', '', 21, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (23, 2, 'appender.rolling.policies.size.size', '${logSize}', '', 22, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (24, 2, 'appender.rolling.strategy.type', 'DefaultRolloverStrategy', '', 23, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (25, 2, 'appender.rolling.strategy.max', '10', '', 24, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (26, 2, 'appender.rolling.strategy.delete.type', 'Delete', '', 25, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (27, 2, 'appender.rolling.strategy.delete.basePath', '${baseDir}', '', 26, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (28, 2, 'appender.rolling.strategy.delete.maxDepth', '2', '', 27, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (29, 2, 'appender.rolling.strategy.delete.iffilename.type', 'IfFileName', '', 28, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (30, 2, 'appender.rolling.strategy.delete.iffilename.glob', '*/${appName}-*.log', '', 29, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (31, 2, 'appender.rolling.strategy.delete.iffilename.iflastmodified.type', 'IfLastModified', '', 30, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (32, 2, 'appender.rolling.strategy.delete.iffilename.iflastmodified.age', '10d', '', 31, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (33, 2, 'appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.type', 'IfAny', '', 32, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (34, 2, 'appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type', 'IfAccumulatedFileSize', '', 33, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (35, 2, 'appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds', '256 MB', '', 34, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (36, 2, 'appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type', 'IfAccumulatedFileCount', '', 35, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (37, 2, 'appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds', '10', '', 36, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (38, 2, 'appender.error.type', 'RollingFile', '', 37, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (39, 2, 'appender.error.name', 'ErrorRollingFile', '', 38, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (40, 2, 'appender.error.fileName', '${baseDir}/${appName}Error.log', '', 39, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (41, 2, 'appender.error.filePattern', '${baseDir}/$${date:yyyy-MM}/${appName}Error-%d{yyyy-MM-dd}-%i.log', '', 40, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (42, 2, 'appender.error.layout.type', 'PatternLayout', '', 41, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (43, 2, 'appender.error.layout.pattern', '${pattern}', '', 42, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (44, 2, 'appender.error.policies.type', 'Policies', '', 43, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (45, 2, 'appender.error.policies.time.type', 'TimeBasedTriggeringPolicy', '', 44, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (46, 2, 'appender.error.policies.size.type', 'SizeBasedTriggeringPolicy', '', 45, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (47, 2, 'appender.error.policies.size.size', '${errorLogSize}', '', 46, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (48, 2, 'appender.error.strategy.type', 'DefaultRolloverStrategy', '', 47, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (49, 2, 'appender.error.strategy.max', '10', '', 48, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (50, 2, 'appender.error.strategy.delete.type', 'Delete', '', 49, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (51, 2, 'appender.error.strategy.delete.basePath', '${baseDir}', '', 50, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (52, 2, 'appender.error.strategy.delete.maxDepth', '2', '', 51, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (53, 2, 'appender.error.strategy.delete.iffilename.type', 'IfFileName', '', 52, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (54, 2, 'appender.error.strategy.delete.iffilename.glob', '*/${appName}Error-*.log', '', 53, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (55, 2, 'appender.error.strategy.delete.iffilename.iflastmodified.type', 'IfLastModified', '', 54, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (56, 2, 'appender.error.strategy.delete.iffilename.iflastmodified.age', '10d', '', 55, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (57, 2, 'appender.error.strategy.delete.iffilename.iflastmodified.ifany.type', 'IfAny', '', 56, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (58, 2, 'appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type', 'IfAccumulatedFileSize', '', 57, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (59, 2, 'appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds', '256 MB', '', 58, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (60, 2, 'appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type', 'IfAccumulatedFileCount', '', 59, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (61, 2, 'appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds', '10', '', 60, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (62, 2, 'logger.dm.name', 'org.spring.test.apollo', '', 61, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:37:53');
INSERT INTO `item` VALUES (63, 2, 'logger.dm.level', 'info', '', 62, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-28 00:42:00');
INSERT INTO `item` VALUES (64, 2, 'logger.spring.name', 'org.springframework', '', 63, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (65, 2, 'logger.spring.level', 'info', '', 64, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (66, 2, 'logger.axis2.name', 'org.apache.axis2', '', 65, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (67, 2, 'logger.axis2.level', 'info', '', 66, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (68, 2, 'rootLogger.level', 'warn', '', 67, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (69, 2, 'rootLogger.appenderRef.stdout.ref', 'STDOUT', '', 68, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (70, 2, 'rootLogger.appenderRef.rolling.ref', 'RollingFile', '', 69, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (71, 2, 'rootLogger.appenderRef.error.ref', 'ErrorRollingFile', '', 70, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');
INSERT INTO `item` VALUES (72, 2, 'rootLogger.appenderRef.error.level', 'error', '', 71, b'0', 'apollo', '2019-08-27 23:34:53', 'apollo', '2019-08-27 23:34:53');

-- ----------------------------
-- Table structure for namespace
-- ----------------------------
DROP TABLE IF EXISTS `namespace`;
CREATE TABLE `namespace`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `AppId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Cluster Name',
  `NamespaceName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'Namespace Name',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `AppId_ClusterName_NamespaceName`(`AppId`(191), `ClusterName`(191), `NamespaceName`(191)) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_NamespaceName`(`NamespaceName`(191)) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '命名空间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of namespace
-- ----------------------------
INSERT INTO `namespace` VALUES (1, '10001', 'default', 'application', b'0', 'apollo', '2019-08-27 22:05:48', 'apollo', '2019-08-27 22:05:48');
INSERT INTO `namespace` VALUES (2, '10001', 'default', 'common.log4j2', b'0', 'apollo', '2019-08-27 23:32:13', 'apollo', '2019-08-27 23:32:13');

-- ----------------------------
-- Table structure for namespacelock
-- ----------------------------
DROP TABLE IF EXISTS `namespacelock`;
CREATE TABLE `namespacelock`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `NamespaceId` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '集群NamespaceId',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  `IsDeleted` bit(1) NULL DEFAULT b'0' COMMENT '软删除',
  PRIMARY KEY (`Id`) USING BTREE,
  UNIQUE INDEX `IX_NamespaceId`(`NamespaceId`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'namespace的编辑锁' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for release
-- ----------------------------
DROP TABLE IF EXISTS `release`;
CREATE TABLE `release`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ReleaseKey` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '发布的Key',
  `Name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '发布名字',
  `Comment` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布说明',
  `AppId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `NamespaceName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'namespaceName',
  `Configurations` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发布配置',
  `IsAbandoned` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否废弃',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `AppId_ClusterName_GroupName`(`AppId`(191), `ClusterName`(191), `NamespaceName`(191)) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_ReleaseKey`(`ReleaseKey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发布' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of release
-- ----------------------------
INSERT INTO `release` VALUES (1, '20190827220712-52c094da42ab4eba', '20190827220659-release', '发布过期时间设置', '10001', 'default', 'application', '{\"spring.boot.timeout\":\"1000\"}', b'0', b'0', 'apollo', '2019-08-27 22:07:12', 'apollo', '2019-08-27 22:07:12');
INSERT INTO `release` VALUES (2, '20190827233517-114b94da42ab4ebb', '20190827233456-release', 'log4j2的配置，可支持热修改', '10001', 'default', 'common.log4j2', '{\"appender.console.type\":\"Console\",\"appender.error.layout.type\":\"PatternLayout\",\"logger.dm.name\":\"com.dm\",\"property.appName\":\"log4j\",\"appender.console.layout.pattern\":\"${pattern}\",\"appender.error.policies.type\":\"Policies\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\":\"10\",\"appender.error.type\":\"RollingFile\",\"rootLogger.appenderRef.stdout.ref\":\"STDOUT\",\"appender.error.strategy.delete.iffilename.iflastmodified.type\":\"IfLastModified\",\"appender.error.policies.time.type\":\"TimeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\":\"IfAccumulatedFileSize\",\"appender.rolling.type\":\"RollingFile\",\"property.pattern\":\"[${appName}] %d [%t] %p %C{10}.%M:%L | %m%n\",\"strict\":\"true\",\"appender.rolling.policies.size.type\":\"SizeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.maxDepth\":\"2\",\"appender.error.strategy.delete.maxDepth\":\"2\",\"appender.rolling.layout.pattern\":\"${pattern}\",\"appender.rolling.policies.size.size\":\"${logSize}\",\"appender.error.filePattern\":\"${baseDir}/$${date:yyyy-MM}/${appName}Error-%d{yyyy-MM-dd}-%i.log\",\"appender.error.policies.size.type\":\"SizeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\":\"256 MB\",\"property.logSize\":\"50MB\",\"property.errorLogSize\":\"50MB\",\"appender.error.strategy.delete.basePath\":\"${baseDir}\",\"logger.spring.level\":\"info\",\"appender.rolling.filePattern\":\"${baseDir}/$${date:yyyy-MM}/${appName}-%d{yyyy-MM-dd}-%i.log\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.type\":\"IfAny\",\"appender.error.strategy.delete.type\":\"Delete\",\"rootLogger.level\":\"warn\",\"appender.rolling.strategy.type\":\"DefaultRolloverStrategy\",\"appender.rolling.strategy.delete.basePath\":\"${baseDir}\",\"rootLogger.appenderRef.error.level\":\"error\",\"appender.error.strategy.delete.iffilename.type\":\"IfFileName\",\"logger.dm.level\":\"debug\",\"rootLogger.appenderRef.rolling.ref\":\"RollingFile\",\"appender.error.fileName\":\"${baseDir}/${appName}Error.log\",\"appender.console.name\":\"STDOUT\",\"appender.console.layout.type\":\"PatternLayout\",\"appender.error.strategy.max\":\"10\",\"status\":\"warn\",\"appender.rolling.name\":\"RollingFile\",\"appender.rolling.strategy.delete.type\":\"Delete\",\"appender.error.strategy.delete.iffilename.iflastmodified.age\":\"10d\",\"rootLogger.appenderRef.error.ref\":\"ErrorRollingFile\",\"logger.axis2.name\":\"org.apache.axis2\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.type\":\"IfLastModified\",\"appenders\":\"console,rolling,error\",\"appender.rolling.strategy.delete.iffilename.type\":\"IfFileName\",\"property.baseDir\":\"logs\",\"appender.rolling.fileName\":\"${baseDir}/${appName}.log\",\"appender.rolling.policies.time.type\":\"TimeBasedTriggeringPolicy\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.type\":\"IfAny\",\"appender.error.name\":\"ErrorRollingFile\",\"appender.rolling.strategy.max\":\"10\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\":\"IfAccumulatedFileCount\",\"appender.rolling.policies.type\":\"Policies\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\":\"IfAccumulatedFileSize\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\":\"IfAccumulatedFileCount\",\"logger.spring.name\":\"org.springframework\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.age\":\"10d\",\"appender.error.strategy.type\":\"DefaultRolloverStrategy\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\":\"256 MB\",\"appender.rolling.layout.type\":\"PatternLayout\",\"appender.error.layout.pattern\":\"${pattern}\",\"appender.error.policies.size.size\":\"${errorLogSize}\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\":\"10\",\"appender.rolling.strategy.delete.iffilename.glob\":\"*/${appName}-*.log\",\"logger.axis2.level\":\"info\",\"appender.error.strategy.delete.iffilename.glob\":\"*/${appName}Error-*.log\"}', b'0', b'0', 'apollo', '2019-08-27 23:35:18', 'apollo', '2019-08-27 23:35:18');
INSERT INTO `release` VALUES (3, '20190827233758-114b94da42ab4ebc', '20190827233757-release', 'log4j2的配置，可支持热修改', '10001', 'default', 'common.log4j2', '{\"appender.console.type\":\"Console\",\"appender.error.layout.type\":\"PatternLayout\",\"logger.dm.name\":\"org.spring.test.apollo\",\"property.appName\":\"log4j\",\"appender.console.layout.pattern\":\"${pattern}\",\"appender.error.policies.type\":\"Policies\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\":\"10\",\"appender.error.type\":\"RollingFile\",\"rootLogger.appenderRef.stdout.ref\":\"STDOUT\",\"appender.error.strategy.delete.iffilename.iflastmodified.type\":\"IfLastModified\",\"appender.error.policies.time.type\":\"TimeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\":\"IfAccumulatedFileSize\",\"appender.rolling.type\":\"RollingFile\",\"property.pattern\":\"[${appName}] %d [%t] %p %C{10}.%M:%L | %m%n\",\"strict\":\"true\",\"appender.rolling.policies.size.type\":\"SizeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.maxDepth\":\"2\",\"appender.error.strategy.delete.maxDepth\":\"2\",\"appender.rolling.layout.pattern\":\"${pattern}\",\"appender.rolling.policies.size.size\":\"${logSize}\",\"appender.error.filePattern\":\"${baseDir}/$${date:yyyy-MM}/${appName}Error-%d{yyyy-MM-dd}-%i.log\",\"appender.error.policies.size.type\":\"SizeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\":\"256 MB\",\"property.logSize\":\"50MB\",\"property.errorLogSize\":\"50MB\",\"appender.error.strategy.delete.basePath\":\"${baseDir}\",\"logger.spring.level\":\"info\",\"appender.rolling.filePattern\":\"${baseDir}/$${date:yyyy-MM}/${appName}-%d{yyyy-MM-dd}-%i.log\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.type\":\"IfAny\",\"appender.error.strategy.delete.type\":\"Delete\",\"rootLogger.level\":\"warn\",\"appender.rolling.strategy.type\":\"DefaultRolloverStrategy\",\"appender.rolling.strategy.delete.basePath\":\"${baseDir}\",\"rootLogger.appenderRef.error.level\":\"error\",\"appender.error.strategy.delete.iffilename.type\":\"IfFileName\",\"logger.dm.level\":\"debug\",\"rootLogger.appenderRef.rolling.ref\":\"RollingFile\",\"appender.error.fileName\":\"${baseDir}/${appName}Error.log\",\"appender.console.name\":\"STDOUT\",\"appender.console.layout.type\":\"PatternLayout\",\"appender.error.strategy.max\":\"10\",\"status\":\"warn\",\"appender.rolling.name\":\"RollingFile\",\"appender.rolling.strategy.delete.type\":\"Delete\",\"appender.error.strategy.delete.iffilename.iflastmodified.age\":\"10d\",\"rootLogger.appenderRef.error.ref\":\"ErrorRollingFile\",\"logger.axis2.name\":\"org.apache.axis2\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.type\":\"IfLastModified\",\"appenders\":\"console,rolling,error\",\"appender.rolling.strategy.delete.iffilename.type\":\"IfFileName\",\"property.baseDir\":\"logs\",\"appender.rolling.fileName\":\"${baseDir}/${appName}.log\",\"appender.rolling.policies.time.type\":\"TimeBasedTriggeringPolicy\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.type\":\"IfAny\",\"appender.error.name\":\"ErrorRollingFile\",\"appender.rolling.strategy.max\":\"10\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\":\"IfAccumulatedFileCount\",\"appender.rolling.policies.type\":\"Policies\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\":\"IfAccumulatedFileSize\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\":\"IfAccumulatedFileCount\",\"logger.spring.name\":\"org.springframework\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.age\":\"10d\",\"appender.error.strategy.type\":\"DefaultRolloverStrategy\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\":\"256 MB\",\"appender.rolling.layout.type\":\"PatternLayout\",\"appender.error.layout.pattern\":\"${pattern}\",\"appender.error.policies.size.size\":\"${errorLogSize}\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\":\"10\",\"appender.rolling.strategy.delete.iffilename.glob\":\"*/${appName}-*.log\",\"logger.axis2.level\":\"info\",\"appender.error.strategy.delete.iffilename.glob\":\"*/${appName}Error-*.log\"}', b'0', b'0', 'apollo', '2019-08-27 23:37:59', 'apollo', '2019-08-27 23:37:59');
INSERT INTO `release` VALUES (4, '20190828004202-114b94da42ab4ebd', '20190828004201-release', 'log4j2的配置，可支持热修改', '10001', 'default', 'common.log4j2', '{\"appender.console.type\":\"Console\",\"appender.error.layout.type\":\"PatternLayout\",\"logger.dm.name\":\"org.spring.test.apollo\",\"property.appName\":\"log4j\",\"appender.console.layout.pattern\":\"${pattern}\",\"appender.error.policies.type\":\"Policies\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\":\"10\",\"appender.error.type\":\"RollingFile\",\"rootLogger.appenderRef.stdout.ref\":\"STDOUT\",\"appender.error.strategy.delete.iffilename.iflastmodified.type\":\"IfLastModified\",\"appender.error.policies.time.type\":\"TimeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\":\"IfAccumulatedFileSize\",\"appender.rolling.type\":\"RollingFile\",\"property.pattern\":\"[${appName}] %d [%t] %p %C{10}.%M:%L | %m%n\",\"strict\":\"true\",\"appender.rolling.policies.size.type\":\"SizeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.maxDepth\":\"2\",\"appender.error.strategy.delete.maxDepth\":\"2\",\"appender.rolling.layout.pattern\":\"${pattern}\",\"appender.rolling.policies.size.size\":\"${logSize}\",\"appender.error.filePattern\":\"${baseDir}/$${date:yyyy-MM}/${appName}Error-%d{yyyy-MM-dd}-%i.log\",\"appender.error.policies.size.type\":\"SizeBasedTriggeringPolicy\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\":\"256 MB\",\"property.logSize\":\"50MB\",\"property.errorLogSize\":\"50MB\",\"appender.error.strategy.delete.basePath\":\"${baseDir}\",\"logger.spring.level\":\"info\",\"appender.rolling.filePattern\":\"${baseDir}/$${date:yyyy-MM}/${appName}-%d{yyyy-MM-dd}-%i.log\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.type\":\"IfAny\",\"appender.error.strategy.delete.type\":\"Delete\",\"rootLogger.level\":\"warn\",\"appender.rolling.strategy.type\":\"DefaultRolloverStrategy\",\"appender.rolling.strategy.delete.basePath\":\"${baseDir}\",\"rootLogger.appenderRef.error.level\":\"error\",\"appender.error.strategy.delete.iffilename.type\":\"IfFileName\",\"logger.dm.level\":\"info\",\"rootLogger.appenderRef.rolling.ref\":\"RollingFile\",\"appender.error.fileName\":\"${baseDir}/${appName}Error.log\",\"appender.console.name\":\"STDOUT\",\"appender.console.layout.type\":\"PatternLayout\",\"appender.error.strategy.max\":\"10\",\"status\":\"warn\",\"appender.rolling.name\":\"RollingFile\",\"appender.rolling.strategy.delete.type\":\"Delete\",\"appender.error.strategy.delete.iffilename.iflastmodified.age\":\"10d\",\"rootLogger.appenderRef.error.ref\":\"ErrorRollingFile\",\"logger.axis2.name\":\"org.apache.axis2\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.type\":\"IfLastModified\",\"appenders\":\"console,rolling,error\",\"appender.rolling.strategy.delete.iffilename.type\":\"IfFileName\",\"property.baseDir\":\"logs\",\"appender.rolling.fileName\":\"${baseDir}/${appName}.log\",\"appender.rolling.policies.time.type\":\"TimeBasedTriggeringPolicy\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.type\":\"IfAny\",\"appender.error.name\":\"ErrorRollingFile\",\"appender.rolling.strategy.max\":\"10\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\":\"IfAccumulatedFileCount\",\"appender.rolling.policies.type\":\"Policies\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.type\":\"IfAccumulatedFileSize\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.type\":\"IfAccumulatedFileCount\",\"logger.spring.name\":\"org.springframework\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.age\":\"10d\",\"appender.error.strategy.type\":\"DefaultRolloverStrategy\",\"appender.error.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilesize.exceeds\":\"256 MB\",\"appender.rolling.layout.type\":\"PatternLayout\",\"appender.error.layout.pattern\":\"${pattern}\",\"appender.error.policies.size.size\":\"${errorLogSize}\",\"appender.rolling.strategy.delete.iffilename.iflastmodified.ifany.ifaccumulatedfilecount.exceeds\":\"10\",\"appender.rolling.strategy.delete.iffilename.glob\":\"*/${appName}-*.log\",\"logger.axis2.level\":\"info\",\"appender.error.strategy.delete.iffilename.glob\":\"*/${appName}Error-*.log\"}', b'0', b'0', 'apollo', '2019-08-28 00:42:02', 'apollo', '2019-08-28 00:42:02');

-- ----------------------------
-- Table structure for releasehistory
-- ----------------------------
DROP TABLE IF EXISTS `releasehistory`;
CREATE TABLE `releasehistory`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `AppId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `ClusterName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'ClusterName',
  `NamespaceName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT 'namespaceName',
  `BranchName` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '发布分支名',
  `ReleaseId` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '关联的Release Id',
  `PreviousReleaseId` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '前一次发布的ReleaseId',
  `Operation` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '发布类型，0: 普通发布，1: 回滚，2: 灰度发布，3: 灰度规则更新，4: 灰度合并回主分支发布，5: 主分支发布灰度自动发布，6: 主分支回滚灰度自动发布，7: 放弃灰度',
  `OperationContext` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发布上下文信息',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_Namespace`(`AppId`, `ClusterName`, `NamespaceName`, `BranchName`) USING BTREE,
  INDEX `IX_ReleaseId`(`ReleaseId`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发布历史' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of releasehistory
-- ----------------------------
INSERT INTO `releasehistory` VALUES (1, '10001', 'default', 'application', 'default', 1, 0, 0, '{\"isEmergencyPublish\":false}', b'0', 'apollo', '2019-08-27 22:07:12', 'apollo', '2019-08-27 22:07:12');
INSERT INTO `releasehistory` VALUES (2, '10001', 'default', 'common.log4j2', 'default', 2, 0, 0, '{\"isEmergencyPublish\":false}', b'0', 'apollo', '2019-08-27 23:35:18', 'apollo', '2019-08-27 23:35:18');
INSERT INTO `releasehistory` VALUES (3, '10001', 'default', 'common.log4j2', 'default', 3, 2, 0, '{\"isEmergencyPublish\":false}', b'0', 'apollo', '2019-08-27 23:37:59', 'apollo', '2019-08-27 23:37:59');
INSERT INTO `releasehistory` VALUES (4, '10001', 'default', 'common.log4j2', 'default', 4, 3, 0, '{\"isEmergencyPublish\":false}', b'0', 'apollo', '2019-08-28 00:42:02', 'apollo', '2019-08-28 00:42:02');

-- ----------------------------
-- Table structure for releasemessage
-- ----------------------------
DROP TABLE IF EXISTS `releasemessage`;
CREATE TABLE `releasemessage`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Message` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '发布的消息内容',
  `DataChange_LastTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_Message`(`Message`(191)) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发布消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of releasemessage
-- ----------------------------
INSERT INTO `releasemessage` VALUES (1, '10001+default+application', '2019-08-27 22:07:12');
INSERT INTO `releasemessage` VALUES (3, '10001+default+common.log4j2', '2019-08-27 23:37:59');
INSERT INTO `releasemessage` VALUES (4, '10001+default+common.log4j2', '2019-08-28 00:42:02');

-- ----------------------------
-- Table structure for serverconfig
-- ----------------------------
DROP TABLE IF EXISTS `serverconfig`;
CREATE TABLE `serverconfig`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '配置项Key',
  `Cluster` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '配置对应的集群，default为不针对特定的集群',
  `Value` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '配置项值',
  `Comment` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_Key`(`Key`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '配置服务自身配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of serverconfig
-- ----------------------------
INSERT INTO `serverconfig` VALUES (1, 'eureka.service.url', 'default', 'http://localhost:18080/eureka/', 'Eureka服务Url，多个service以英文逗号分隔', b'0', 'default', '2019-08-27 21:08:05', '', '2019-08-27 21:30:05');
INSERT INTO `serverconfig` VALUES (2, 'namespace.lock.switch', 'default', 'false', '一次发布只能有一个人修改开关', b'0', 'default', '2019-08-27 21:08:05', '', '2019-08-27 21:08:05');
INSERT INTO `serverconfig` VALUES (3, 'item.key.length.limit', 'default', '128', 'item key 最大长度限制', b'0', 'default', '2019-08-27 21:08:05', '', '2019-08-27 21:08:05');
INSERT INTO `serverconfig` VALUES (4, 'item.value.length.limit', 'default', '20000', 'item value最大长度限制', b'0', 'default', '2019-08-27 21:08:05', '', '2019-08-27 21:08:05');
INSERT INTO `serverconfig` VALUES (5, 'config-service.cache.enabled', 'default', 'false', 'ConfigService是否开启缓存，开启后能提高性能，但是会增大内存消耗！', b'0', 'default', '2019-08-27 21:08:05', '', '2019-08-27 21:08:05');

SET FOREIGN_KEY_CHECKS = 1;
