/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : rumen

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-11-19 06:30:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for queue_new
-- ----------------------------
DROP TABLE IF EXISTS `queue_new`;
CREATE TABLE `queue_new` (
  `item_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary Key: Unique item ID.',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT 'The queue name.',
  `base_path` varchar(255) NOT NULL DEFAULT '' COMMENT 'The queue name.',
  `data` longblob DEFAULT NULL COMMENT 'The arbitrary data for the item.',
  `expire` int(11) NOT NULL DEFAULT 0 COMMENT 'Timestamp when the claim lease expires on the item.',
  `created` int(11) NOT NULL DEFAULT 0 COMMENT 'Timestamp when the item was created.',
  `todo` tinyint(1) DEFAULT NULL,
  `force` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `name_created` (`name`,`created`),
  KEY `expire` (`expire`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='Stores items in queues.';
