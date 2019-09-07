/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : data_quality

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 06/09/2019 17:11:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_table
-- ----------------------------
DROP TABLE IF EXISTS `data_table`;
CREATE TABLE `data_table`  (
  `table_id` int(8) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `driver` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_table
-- ----------------------------
INSERT INTO `data_table` VALUES (1, 'jdbc:mysql://localhost:3306/data_quality?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8', 'com.mysql.cj.jdbc.Driver', 'root', 'root');
INSERT INTO `data_table` VALUES (2, 'jdbc:mysql://localhost:3306/c2c?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8', 'com.mysql.cj.jdbc.Driver', 'root', 'root');

-- ----------------------------
-- Table structure for msg_template
-- ----------------------------
DROP TABLE IF EXISTS `msg_template`;
CREATE TABLE `msg_template`  (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `code` int(8) NOT NULL,
  `type` tinyint(1) NOT NULL COMMENT '类型：0：短信；1：邮件',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `locale` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_code_type`(`code`, `type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of msg_template
-- ----------------------------
INSERT INTO `msg_template` VALUES (1, 10020001, 1, '规则报警', '<div style=\"border:1px solid #CCC;background:#F4F4F4;width:100%;text-align:left\"><div style=\"border:none;background:#FCFCFC;padding:20px;color:#333;font-size:14px;\"><p>您好！</p><p>您设立的规则：<span style=\"color:#c83935;font-size:16px\">%s</span></p><p>未满足要求，请确认具体情况。<p>感謝您的支持！</p><p>仟金顶</p></div></div><br><br></div>', 'zh_CN');

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(8) NOT NULL,
  `table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` int(8) NOT NULL,
  `columns` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `column_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `project_leader` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `leader_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_inactive` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用',
  `corn` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp(0) NOT NULL,
  `update_time` timestamp(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES (1, 2, 'account_detail', '流水每日总数统计', 10010001, NULL, '>', '20', '啦啦啦', 'liyangzhen@qjdchina.com', 1, '*/1000 * * * * ?', '2019-09-04 20:07:54', '2019-09-04 20:07:57');
INSERT INTO `rule` VALUES (2, 1, 'data_table', '数据源每日总数统计', 10010001, NULL, '>', '20', '啦啦啦', 'liyangzhen@qjdchina.com', 1, '*/1000 * * * * ?', '2019-09-04 20:07:54', '2019-09-04 20:07:57');
INSERT INTO `rule` VALUES (11, 1, 'rule', '规则每日总数统计', 10010001, NULL, '>', '20', '啦啦啦', 'liyangzhen@qjdchina.com', 1, '*/20 * * * * ?', '2019-09-05 16:53:25', '2019-09-06 14:22:22');

SET FOREIGN_KEY_CHECKS = 1;
