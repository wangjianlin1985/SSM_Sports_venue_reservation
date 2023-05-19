/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : space_order_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-08-11 02:33:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_bookorder`
-- ----------------------------
DROP TABLE IF EXISTS `t_bookorder`;
CREATE TABLE `t_bookorder` (
  `orderId` int(11) NOT NULL auto_increment COMMENT '订单id',
  `spaceObj` varchar(20) NOT NULL COMMENT '预订场地',
  `spaceTypeObj` int(11) NOT NULL COMMENT '场地类型',
  `userObj` varchar(30) NOT NULL COMMENT '预订人',
  `startTime` varchar(20) default NULL COMMENT '开始时间',
  `hours` int(11) NOT NULL COMMENT '预订小时数',
  `totalMoney` float NOT NULL COMMENT '总价',
  `orderMemo` varchar(500) default NULL COMMENT '订单备注',
  `orderState` varchar(20) NOT NULL COMMENT '订单状态',
  `orderTime` varchar(20) default NULL COMMENT '预订时间',
  PRIMARY KEY  (`orderId`),
  KEY `spaceObj` (`spaceObj`),
  KEY `spaceTypeObj` (`spaceTypeObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_bookorder_ibfk_1` FOREIGN KEY (`spaceObj`) REFERENCES `t_space` (`spaceNo`),
  CONSTRAINT `t_bookorder_ibfk_2` FOREIGN KEY (`spaceTypeObj`) REFERENCES `t_spacetype` (`spaceTypeId`),
  CONSTRAINT `t_bookorder_ibfk_3` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bookorder
-- ----------------------------
INSERT INTO `t_bookorder` VALUES ('1', '101', '1', 'user1', '2019-06-31', '2', '400', '11', '退出占用', '2019-06-28 17:56:10');
INSERT INTO `t_bookorder` VALUES ('2', '101', '1', 'user1', '2019-07-05', '2', '400', '22', '待付款', '2019-06-30 23:41:59');
INSERT INTO `t_bookorder` VALUES ('3', '102', '2', 'user2', '2019-06-03', '2', '700', '33', '待处理', '2019-06-31 00:02:12');
INSERT INTO `t_bookorder` VALUES ('4', '102', '2', 'user1', '2019-08-03', '3', '1050', '44', '已付款', '2019-07-21 16:58:47');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '我预订场地方便了', '有了这个网站，我以后可以网上预订场地了', 'user1', '2019-06-28 17:56:44', '是的，我们就是为了大家的方便', '2019-06-28 17:56:56');
INSERT INTO `t_leaveword` VALUES ('2', '111', '22', 'user1', '2019-06-30 22:50:10', '33', '2019-06-30 22:50:32');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '公告内容',
  `hitNum` int(11) NOT NULL COMMENT '点击率',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '网上订房网站开通了', '<p>好消息到，同志们，需要预订酒店场地的朋友，可以到我们网站注册预订了哈！</p>', '4', '2019-06-28 17:57:41');

-- ----------------------------
-- Table structure for `t_space`
-- ----------------------------
DROP TABLE IF EXISTS `t_space`;
CREATE TABLE `t_space` (
  `spaceNo` varchar(20) NOT NULL COMMENT 'spaceNo',
  `spaceTypeObj` int(11) NOT NULL COMMENT '场地类型',
  `spacePhoto` varchar(60) NOT NULL COMMENT '场地图片',
  `spacePrice` float NOT NULL COMMENT '价格(每小时)',
  `floorNum` varchar(20) NOT NULL COMMENT '楼层',
  `spaceState` varchar(20) NOT NULL COMMENT '占用状态',
  `spaceDesc` varchar(5000) NOT NULL COMMENT '场地描述',
  PRIMARY KEY  (`spaceNo`),
  KEY `spaceTypeObj` (`spaceTypeObj`),
  CONSTRAINT `t_space_ibfk_1` FOREIGN KEY (`spaceTypeObj`) REFERENCES `t_spacetype` (`spaceTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_space
-- ----------------------------
INSERT INTO `t_space` VALUES ('101', '1', 'upload/05d9c6cd-54b0-486b-abf3-205366c1c670.jpg', '20', '1', '空闲中', '<p>羽毛球场地，该场地有10个一共，20快钱以小时</p>');
INSERT INTO `t_space` VALUES ('102', '2', 'upload/281b4439-a5a7-4740-a842-35ed44541081.jpg', '50', '1', '空闲中', '<p>篮球场地类型一共有10个场地，每个产地预约50块钱一小时，请预约空闲中的</p>');

-- ----------------------------
-- Table structure for `t_spacetype`
-- ----------------------------
DROP TABLE IF EXISTS `t_spacetype`;
CREATE TABLE `t_spacetype` (
  `spaceTypeId` int(11) NOT NULL auto_increment COMMENT '类型id',
  `spaceTypeName` varchar(20) NOT NULL COMMENT '场地类型',
  `price` float NOT NULL COMMENT '价格(每小时)',
  PRIMARY KEY  (`spaceTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_spacetype
-- ----------------------------
INSERT INTO `t_spacetype` VALUES ('1', '羽毛球场地', '20');
INSERT INTO `t_spacetype` VALUES ('2', '篮球场地', '50');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `cardNumber` varchar(30) NOT NULL COMMENT '身份证号',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '双鱼林', '男', 'upload/26e0dd04-29be-4259-bb02-bfd108a2e4f1.jpg', '2019-06-02', '513042199612113439', '13958342342', 'dashen@163.com', '四川成都红星路13号', '2019-06-28 17:53:47');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '李明霞', '女', 'upload/8a0b5e8d-78d3-4758-80a1-9d540414ddb5.jpg', '2019-06-04', '513055199812024323', '13984083084', 'mingxia@163.com', '四川攀枝花', '2019-06-30 23:50:30');
