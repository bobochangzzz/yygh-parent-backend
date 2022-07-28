/*
 Navicat Premium Data Transfer

 Source Server         : bobochang
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : yygh_hosp

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 12/07/2022 11:56:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hospital_set
-- ----------------------------
DROP TABLE IF EXISTS `hospital_set`;
CREATE TABLE `hospital_set` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hosname` varchar(100) DEFAULT NULL COMMENT '医院名称',
  `hoscode` varchar(30) DEFAULT NULL COMMENT '医院编号',
  `api_url` varchar(100) DEFAULT NULL COMMENT 'api基础路径',
  `sign_key` varchar(50) DEFAULT NULL COMMENT '签名秘钥',
  `contacts_name` varchar(20) DEFAULT NULL COMMENT '联系人',
  `contacts_phone` varchar(11) DEFAULT NULL COMMENT '联系人手机',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_hoscode` (`hoscode`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COMMENT='医院设置表';

-- ----------------------------
-- Records of hospital_set
-- ----------------------------
BEGIN;
INSERT INTO `hospital_set` (`id`, `hosname`, `hoscode`, `api_url`, `sign_key`, `contacts_name`, `contacts_phone`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES (1, '北京协和医院', '1000_0', 'http://localhost:9998/', '674c4139707728439a6510eae12deea9', 'bobochang', '13700088899', 1, '2022-06-29 15:00:29', '2022-07-01 13:18:58', 0);
INSERT INTO `hospital_set` (`id`, `hosname`, `hoscode`, `api_url`, `sign_key`, `contacts_name`, `contacts_phone`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES (2, '佛山人民医院', '1000_1', 'http://localhost:9999/', '39b36fc1a16bd418561b299ebd872d1d', '啵啵肠', '13726750698', 1, '2022-06-29 19:36:42', '2022-06-29 19:53:54', 0);
INSERT INTO `hospital_set` (`id`, `hosname`, `hoscode`, `api_url`, `sign_key`, `contacts_name`, `contacts_phone`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES (3, '广州第一人民医院', '1000_2', 'http://localhost:8888/', NULL, '啵啵肠zz', '17302027074', 1, '2022-07-01 11:11:07', '2022-07-01 14:31:55', 0);
INSERT INTO `hospital_set` (`id`, `hosname`, `hoscode`, `api_url`, `sign_key`, `contacts_name`, `contacts_phone`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES (4, '广州慈善医院', '1000_3', 'http://localhost:8181/', NULL, '慈善医院', '13788899888', 1, '2022-07-01 11:12:30', '2022-07-01 14:31:56', 0);
INSERT INTO `hospital_set` (`id`, `hosname`, `hoscode`, `api_url`, `sign_key`, `contacts_name`, `contacts_phone`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES (5, '中山附属第一医院', '1000_4', 'http://localhost:9140/', '8b1f684f3f148f4421aa1d519b36a95e', '啵啵肠z', '13799999999', 1, '2022-07-01 13:50:47', '2022-07-01 14:31:58', 0);
INSERT INTO `hospital_set` (`id`, `hosname`, `hoscode`, `api_url`, `sign_key`, `contacts_name`, `contacts_phone`, `status`, `create_time`, `update_time`, `is_deleted`) VALUES (6, '禅城人民医院', '1000_5', 'http://localhost:3170/', '09a1a5da75355dc481818fe7a28218d8', 'bobochangzz', '13788899000', 1, '2022-07-01 14:30:55', '2022-07-01 14:31:59', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
