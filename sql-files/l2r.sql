/*
Navicat MySQL Data Transfer

Source Server         : Local
Source Server Version : 50544
Source Host           : localhost:3306
Source Database       : l2r

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2019-11-25 20:37:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for caserelation
-- ----------------------------
DROP TABLE IF EXISTS `caserelation`;
CREATE TABLE `caserelation` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `customerid` int(100) DEFAULT NULL,
  `companyid` int(100) DEFAULT NULL,
  `caseid` int(100) DEFAULT NULL,
  `createby` int(100) DEFAULT NULL,
  `responseid` int(100) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL COMMENT 'read:只读；modify:可编写',
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of caserelation
-- ----------------------------

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `inSideId` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL COMMENT 'Status=0,导入客户未产生leads；1,产生过case',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------

-- ----------------------------
-- Table structure for companycustomer
-- ----------------------------
DROP TABLE IF EXISTS `companycustomer`;
CREATE TABLE `companycustomer` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `companyid` int(100) DEFAULT NULL,
  `customerid` int(100) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of companycustomer
-- ----------------------------

-- ----------------------------
-- Table structure for companylogs
-- ----------------------------
DROP TABLE IF EXISTS `companylogs`;
CREATE TABLE `companylogs` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `companyId` int(100) DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `changebyid` int(100) DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of companylogs
-- ----------------------------

-- ----------------------------
-- Table structure for companyrelation
-- ----------------------------
DROP TABLE IF EXISTS `companyrelation`;
CREATE TABLE `companyrelation` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `parentcompanyid` int(100) NOT NULL,
  `subcompanyid` int(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of companyrelation
-- ----------------------------

-- ----------------------------
-- Table structure for companytags
-- ----------------------------
DROP TABLE IF EXISTS `companytags`;
CREATE TABLE `companytags` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `companyid` int(100) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of companytags
-- ----------------------------

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(100) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `cellphone` varchar(255) DEFAULT NULL,
  `workphone` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `sourcecode` varchar(255) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `nextvisit` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for customerlogs
-- ----------------------------
DROP TABLE IF EXISTS `customerlogs`;
CREATE TABLE `customerlogs` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `customerid` int(100) NOT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `changebyid` int(100) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customerlogs
-- ----------------------------

-- ----------------------------
-- Table structure for customertags
-- ----------------------------
DROP TABLE IF EXISTS `customertags`;
CREATE TABLE `customertags` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `customerid` int(100) NOT NULL,
  `tag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customertags
-- ----------------------------

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
-- Table structure for userrelation
-- ----------------------------
DROP TABLE IF EXISTS `userrelation`;
CREATE TABLE `userrelation` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `insideid` int(100) DEFAULT NULL,
  `responseid` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userrelation
-- ----------------------------

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
