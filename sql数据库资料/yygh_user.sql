/*
 Navicat Premium Data Transfer

 Source Server         : bobochang
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : yygh_user

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 12/07/2022 11:56:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `certificates_type` varchar(3) DEFAULT NULL COMMENT '证件类型',
  `certificates_no` varchar(30) DEFAULT NULL COMMENT '证件编号',
  `sex` tinyint DEFAULT NULL COMMENT '性别',
  `birthdate` date DEFAULT NULL COMMENT '出生年月',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `is_marry` tinyint DEFAULT NULL COMMENT '是否结婚',
  `province_code` varchar(20) DEFAULT NULL COMMENT '省code',
  `city_code` varchar(20) DEFAULT NULL COMMENT '市code',
  `district_code` varchar(20) DEFAULT NULL COMMENT '区code',
  `address` varchar(100) DEFAULT NULL COMMENT '详情地址',
  `contacts_name` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `contacts_certificates_type` varchar(3) DEFAULT NULL COMMENT '联系人证件类型',
  `contacts_certificates_no` varchar(30) DEFAULT NULL COMMENT '联系人证件号',
  `contacts_phone` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `card_no` varchar(50) DEFAULT NULL COMMENT '就诊卡号',
  `is_insure` tinyint DEFAULT '0' COMMENT '是否有医保',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态（0：默认 1：已认证）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COMMENT='就诊人表';

-- ----------------------------
-- Records of patient
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `openid` varchar(100) DEFAULT NULL COMMENT '微信openid',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `certificates_type` varchar(3) DEFAULT NULL COMMENT '证件类型',
  `certificates_no` varchar(30) DEFAULT NULL COMMENT '证件编号',
  `certificates_url` varchar(200) DEFAULT NULL COMMENT '证件路径',
  `auth_status` tinyint NOT NULL DEFAULT '0' COMMENT '认证状态（0：未认证 1：认证中 2：认证成功 -1：认证失败）',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0：锁定 1：正常）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`),
  KEY `uk_mobile` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` (`id`, `openid`, `nick_name`, `phone`, `name`, `certificates_type`, `certificates_no`, `certificates_url`, `auth_status`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES (1, NULL, NULL, '13726750698', '', NULL, NULL, NULL, 0, 1, '2022-07-06 09:52:31', '2022-07-06 09:52:57', 0);
COMMIT;

-- ----------------------------
-- Table structure for user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `user_login_record`;
CREATE TABLE `user_login_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3 COMMENT='用户登录记录表';

-- ----------------------------
-- Records of user_login_record
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
