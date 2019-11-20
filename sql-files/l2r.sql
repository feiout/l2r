/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : l2r

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-11-20 16:02:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `userType` varchar(255) DEFAULT NULL COMMENT 'Super = 系统管理员(可创建Admin+Subscription)\r\nAdmin= 企业管理员(可创建企业的Staff)\r\nStaff=企业普通使用户',
  `subscriptionId` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Messi', 'IT', 'Super', '1');

-- ----------------------------
-- Table structure for userdepartment
-- ----------------------------
DROP TABLE IF EXISTS `userdepartment`;
CREATE TABLE `userdepartment` (
  `Id` int(100) NOT NULL AUTO_INCREMENT,
  `Department` varchar(255) DEFAULT NULL,
  `SubscriptionId` int(100) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userdepartment
-- ----------------------------
INSERT INTO `userdepartment` VALUES ('1', 'IT', '1');

-- ----------------------------
-- Table structure for userlogin
-- ----------------------------
DROP TABLE IF EXISTS `userlogin`;
CREATE TABLE `userlogin` (
  `Id` int(255) NOT NULL AUTO_INCREMENT,
  `UserId` int(100) NOT NULL,
  `LoginName` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userlogin
-- ----------------------------
INSERT INTO `userlogin` VALUES ('1', '1', '52486846@qq.com', '123jklJKL');

-- ----------------------------
-- Table structure for userlogs
-- ----------------------------
DROP TABLE IF EXISTS `userlogs`;
CREATE TABLE `userlogs` (
  `Id` int(100) NOT NULL AUTO_INCREMENT,
  `UserId` int(100) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `LastLognDate` datetime DEFAULT NULL,
  `LastChange` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userlogs
-- ----------------------------
INSERT INTO `userlogs` VALUES ('1', '1', '2019-11-19 14:00:43', null, null);

-- ----------------------------
-- Table structure for usersubscription
-- ----------------------------
DROP TABLE IF EXISTS `usersubscription`;
CREATE TABLE `usersubscription` (
  `Id` int(100) NOT NULL AUTO_INCREMENT,
  `CompanyName` varchar(255) NOT NULL,
  `Amount` int(255) NOT NULL,
  `ExpireDate` date NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usersubscription
-- ----------------------------
INSERT INTO `usersubscription` VALUES ('1', 'SMS', '10', '2099-11-28');
