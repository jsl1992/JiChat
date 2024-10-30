/*
Navicat MySQL Data Transfer

Source Server         : 192.168.137.179_3306(本地)
Source Server Version : 80027
Source Host           : 192.168.137.179:3306
Source Database       : jichat_user

Target Server Type    : MYSQL
Target Server Version : 80027
File Encoding         : 65001

Date: 2024-01-29 13:43:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_chat_message
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_message`;
CREATE TABLE `t_chat_message` (
  `id` bigint NOT NULL COMMENT 'id主键',
  `message_id` bigint NOT NULL COMMENT '信息id',
  `channel_key` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '频道key',
  `message_from` bigint NOT NULL COMMENT '信息来源',
  `message_to` bigint NOT NULL COMMENT '信息到达',
  `message_type` int NOT NULL DEFAULT '1' COMMENT '消息类型 1：文字 2：图片 3：语音 4：视频 5：文件',
  `message_content` varchar(4096) COLLATE utf8mb4_general_ci NOT NULL COMMENT '信息内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='聊天信息表';

-- ----------------------------
-- Table structure for t_chat_server_info
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_server_info`;
CREATE TABLE `t_chat_server_info` (
  `id` bigint NOT NULL COMMENT 'id主键',
  `outside_ip` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '外网IP',
  `inner_ip` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'httpIp',
  `http_port` int NOT NULL COMMENT 'http端口',
  `tcp_port` int NOT NULL COMMENT 'tcp端口',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（0停用 1正常）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_innerip_httpport` (`inner_ip`,`http_port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='聊天服务器信息表';

-- ----------------------------
-- Table structure for t_device
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `id` bigint NOT NULL COMMENT 'id主键',
  `device_identifier` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备标识',
  `device_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备名称',
  `device_type` int NOT NULL DEFAULT '1' COMMENT '设备类型（1手机 2电脑 3平板）',
  `os_type` int NOT NULL DEFAULT '1' COMMENT '操作系统类型',
  `online_status` int NOT NULL DEFAULT '1' COMMENT '在线状态（0离线 1在线）',
  `login_ip` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='设备表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL COMMENT 'id主键',
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(500) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `mobile` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '帐号状态（0停用 1正常）',
  `online_status` int NOT NULL DEFAULT '1' COMMENT '在线状态（0离线 1在线）',
  `login_ip` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `del_flag` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0存在 1已删除)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- ----------------------------
-- Table structure for t_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_user_relation`;
CREATE TABLE `t_user_relation` (
  `id` bigint NOT NULL COMMENT 'id主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `relation_id` bigint NOT NULL COMMENT '关联id(用户id/群id)',
  `channel_key` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '频道key',
  `relation_type` int NOT NULL DEFAULT '1' COMMENT '关联类型(1:用户 2:群)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='好友关系表';
