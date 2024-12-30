# 【JiChat】基于Netty打造百万级用户IM平台：探索可扩展和高性能通信的威力

<!--
Travis CI 徽章：
访问 Travis CI，使用 GitHub 账户登录，并启用你的项目的构建。
在仓库的 Travis CI 页面找到 "Build Status" 徽章，选择需要的格式，然后将其添加到你的 README 文件中。
Codecov 徽章：

访问 Codecov，使用 GitHub 账户登录，并启用你的项目的代码覆盖率报告。
在仓库的 Codecov 页面找到 "Badge" 选项，选择需要的格式，然后将其添加到你的 README 文件中。
Maven中央仓库版本徽章：

访问 Shields.io，选择 "Maven Central"，输入你的 Maven 仓库坐标（group ID 和 artifact ID），然后生成徽章代码并添加到你的 README 文件中。
许可证信息徽章：

访问 Shields.io，选择 "License"，输入你的项目的许可证，然后生成徽章代码并添加到你的 README 文件中。
Is It Maintained 徽章：

访问 Is It Maintained，搜索你的项目，获取徽章代码并添加到你的 README 文件中。
-->
[![CircleCI](https://dl.circleci.com/status-badge/img/circleci/CgWndursnfTN85ScJmBdyi/R7S69YhP9B1F39MDmdLyCB/tree/master.svg?style=svg&circle-token=f619bb84e6e68f060795b1c21a25d39a1c1b0cb4)](https://dl.circleci.com/status-badge/redirect/circleci/CgWndursnfTN85ScJmBdyi/R7S69YhP9B1F39MDmdLyCB/tree/master)
[![codecov](https://codecov.io/gh/jsl1992/JiChat/graph/badge.svg?token=NVFGT76HQF)](https://codecov.io/gh/jsl1992/JiChat)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/dc30543fa9844f98bc5fa169c97913d9)](https://app.codacy.com/gh/jsl1992/JiChat/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
![maven](https://img.shields.io/maven-central/v/com.ji.jichat/jichat.svg)
![GitHub License](https://img.shields.io/github/license/jsl1992/JiChat)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/jsl1992/JiChat.svg)](http://isitmaintained.com/project/jsl1992/JiChat "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/jsl1992/JiChat.svg)](http://isitmaintained.com/project/jsl1992/JiChat "Percentage of issues still open")
[![Leaderboard](https://img.shields.io/badge/JiChat-%E6%9F%A5%E7%9C%8B%E8%B4%A1%E7%8C%AE%E6%8E%92%E8%A1%8C%E6%A6%9C-orange)](https://github.com/jsl1992/JiChat)



# 前言
 'JiChat' 的IM系统:不仅支持多客户端登录，还实现了历史消息同步、消息顺序一致性和零消息丢失的特性,同时支持端到端加密会话保护用户隐私。为确保系统的可扩展性，整个架构按照百万级用户流量的标准进行设计，并支持无缝的横向扩展。



# 系统设计
系统采用了分布式架构，基于 Spring Cloud 实现微服务化，服务注册和发现方面使用 Nacos，消息中间件选择 RabbitMQ，持久性数据存储采用 MySQL 数据库，缓存层使用 Redis。在数据访问层面，使用 MyBatis-Plus 简化数据库操作。服务之间通过 OpenFeign 实现远程调用，以提高服务之间的通信效率。为了满足即时通讯需求，引入了 Netty 框架，以实现高性能、实时的消息传递。




# 业务流程
![image](https://github.com/jsl1992/JiChat/assets/34052259/7ccf4c17-59ef-4eff-991f-41c624812791)



## 消息流程
1. 用户A向聊天服务器1发送聊天消息。
2. 聊天服务器1从ID生成器获取消息ID。
3. 聊天服务器1将消息发送到消息同步队列。
4. 消息存储在键值存储中。
5. a.如果用户 B 在线，则消息将转发到用户 B 所在的聊天服务器 2连接的。
5. b. 如果用户 B 离线，则从推送通知 (PN) 服务器发送推送通知。
6. 聊天服务器2将消息转发给用户B。有一个持久的TCP用户 B 和聊天服务器 2 之间的连接。

消息大略流程： 客户端A→服务端a→服务端b→客户端B


## 项目部署
### (1)安装docker和docker-compose，安装组件
请执行doc目录中的docker-compose.yml文件，以安装RabbitMQ、Nacos、MySQL、Redis等组件。如果Nacos安装失败，请确保在MySQL中初始化Nacos所需的nacos-db.sql文件数据。(为解决docker镜像国内无法下载问题，这边镜像地址使用阿里云的个人仓库地址，个人仓库镜像公开都可以下载)
### (2)启动user-service-app和chat-service-app服务
进行JiChat数据库的初始化，执行jichat_user.sql脚本。然后，修改user-service-app和chat-service-app服务的配置文件，确保连接到正确的RabbitMQ、Nacos、MySQL和Redis地址以及账号信息。最后，启动user-service-app和chat-service-app服务。
### (3)启动chat-client客户端
调用user-service-app的注册账号接口后，修改chat-client的application-dev.yaml文件中的用户信息和连接地址，即可启动客户端。为了方便实现通信，建议在dev和test配置文件中使用不同端口和用户id
### (4)访问地址
    chat-server swagger: http://localhost:18080/chat-api/doc.html#/home
    user-server swagger: http://localhost:18081/user-api/doc.html#/home
    chat-client swagger: http://localhost:9192/doc.html#/home

# 项目业务分析博客
JiChat 博客地址 [https://blog.csdn.net/weixin_42887222/article/details/135910752 ](https://blog.csdn.net/weixin_42887222/article/details/135910752)

# 反馈交流
邮箱: jishenglong92@gmail.com

