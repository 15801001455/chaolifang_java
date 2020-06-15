/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : chaolifang

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-06-15 22:43:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bookmanager`
-- ----------------------------
DROP TABLE IF EXISTS `bookmanager`;
CREATE TABLE `bookmanager` (
  `id` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `borrowTime` datetime DEFAULT NULL,
  `returnTime` datetime DEFAULT NULL,
  `borrowPerson` varchar(100) DEFAULT NULL,
  `borrowStatus` int(2) DEFAULT NULL,
  `mark` text,
  `insertTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bookmanager
-- ----------------------------
INSERT INTO `bookmanager` VALUES ('1', '2', '2020-06-14 16:53:58', '2020-06-26 16:54:03', '112', '1', '1212', '2020-06-14 22:47:34', '2020-06-14 22:47:37');
