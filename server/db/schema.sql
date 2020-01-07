/*
 Navicat Premium Data Transfer

 Source Server         : ray
 Source Server Type    : MySQL
 Source Server Version : 100311
 Source Host           : 192.168.33.101:3307
 Source Schema         : ray

 Target Server Type    : MySQL
 Target Server Version : 100311
 File Encoding         : 65001

 Date: 07/01/2020 21:48:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_his
-- ----------------------------
DROP TABLE IF EXISTS `t_his`;
CREATE TABLE `t_his`  (
  `id` int(11) NOT NULL,
  `type` int(255) NOT NULL COMMENT '0专栏1一心理2极客视频',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父id',
  `cid` int(11) NULL DEFAULT NULL COMMENT '当前id',
  `uid` int(11) NULL DEFAULT NULL,
  `add_time` datetime(0) NULL DEFAULT curtime,
  `up_time` datetime(0) NULL DEFAULT curtime,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_special
-- ----------------------------
DROP TABLE IF EXISTS `t_special`;
CREATE TABLE `t_special`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fid` bigint(20) NULL DEFAULT NULL,
  `finish` bit(1) NULL DEFAULT NULL,
  `add_time` datetime(0) NULL DEFAULT current_timestamp(0),
  `up_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `title_index`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 861 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_special_item
-- ----------------------------
DROP TABLE IF EXISTS `t_special_item`;
CREATE TABLE `t_special_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fsid_audio` bigint(20) NULL DEFAULT NULL,
  `fsid_html` bigint(20) NULL DEFAULT NULL,
  `audio_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `html_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `special_id` int(11) NULL DEFAULT NULL,
  `add_time` datetime(0) NULL DEFAULT current_timestamp(0),
  `up_time` datetime(0) NULL DEFAULT current_timestamp(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `title_index`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6961 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT current_timestamp(0),
  `up_time` datetime(0) NOT NULL DEFAULT current_timestamp(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
