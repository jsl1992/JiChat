# 【JiChat】基于Netty打造百万级用户IM平台：探索可扩展和高性能通信的威力


# 前言
不久前，笔者学习了《System Design Interview》中关于即时通讯（IM）系统设计的内容。为了更深入地理解和应用这些概念，我决定亲手实现一个名为 'JiChat' 的IM系统。该项目不仅支持多客户端登录，还实现了历史消息同步、消息顺序一致性和零消息丢失的特性。为确保系统的可扩展性，整个架构按照百万级用户流量的标准进行设计，并支持无缝的横向扩展。在这篇博客中，我将分享这个IM项目的设计和实现过程，希望能够为大家提供有价值的经验和启发。

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

## 项目目录
### doc
项目启动脚本和数据库脚本
### jichat-dependencies
定义 JiChat项目的所有依赖的版本
### jichat-framework
所有封装的技术框架都放在这里，基本都是使用spring-boot自动注入功能封装框架代码。
(1)jichat-common,公共包
(2)jichat-mybatis-gen:使用mybatis代码生成，封装了DTO，VO，Convert自定义类生成。网上很多都代码自动生成一个压缩包，还要手动复制比较麻烦。这边将代码生成在对应的目录里。因为要使用到mybatis里基类，引用了jichat-spring-boot-starter-mybatis。
(3)jichat-spring-boot-starter-mybatis:使用了mybatis-puls自动写入新增时间/更新时间/新增用户/更新用字段。之前项目分页用的都是PageHelper，而且这个比较简洁。这边也使用了，但是返回的PageInfo字段又太多了，这边封装了一个方法只返回total和list。

```java
public class JiPageHelper {

    /**
     * 封装PageHelper的分页（原来返回的PageInfo字段太多了，这边自定义返回PageVO）
     *
     * @param pageDTO 分页内容
     * @param select  接口中调用的查询方法
     * @return com.ji.jichat.common.pojo.PageVO<E>
     * @author jisl on 2024/1/28 18:11
     **/
    public static <E> PageVO<E> doSelectPageInfo(PageDTO pageDTO, ISelect select) {
        PageInfo<E> pageInfo = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize()).doSelectPageInfo(select);
        return new PageVO<>(pageInfo.getList(), pageInfo.getTotal());
    }
}
```
	
(4)jichat-spring-boot-starter-web:web服务功能封装，请求日志打印AccessLogAspect，TraceId和SpanId拦截器（这边没有使用第三方Zipkin，个人感觉太重了。这边服务还不是很多），统一异常处理，CommonResult返回给前端TraceId这样方便定位异常日志。

```java
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<CommonResult> {

    @Override
    @SuppressWarnings("NullableProblems") // 避免 IDEA 警告
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }
        // 只拦截返回结果为 CommonResult 类型
        return returnType.getMethod().getReturnType() == CommonResult.class;
    }


    @Override
    public CommonResult beforeBodyWrite(CommonResult commonResult, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //每个返回值，都加入traceId，这样方便定位日志
        commonResult.setTraceId(CommonWebUtil.getTraceId(Objects.requireNonNull(HttpContextUtil.getHttpServletRequest())));
        return commonResult;
    }
}
```
(5)jichat-spring-boot-starter-security:用户鉴权，将来考虑使用jichat-gateway来实现。

### jichat-gateway
网关，还未实现

### jichat-modules
所有的业务相关项目放在这个目录里，因为是分布式项目这边统一每个业务项目都有一个api和app。api项目放RPC接口，VO和DTO，枚举等；app是业务具体实现。
目前就user-service和chat-service两个服务，这边没有再封装一个web应用比较麻烦。将来使用Hbase会增加一个data-service，来处理消息数据。chat-client是客户端，实际环境中也不会用Java写客户端这边是为了实现调试。

### log
日志一开始想将配置文件logback-spring.xml放在jichat-spring-boot-starter-web项目里，但是启动项目无法识别项目名称。看网上是要使用bootstrap.yaml，比较麻烦没有放在那里。debug模式下，框架日志输出太多了，屏幕一些内容。同时不同环境使用的日志模式不一样，一般现在生产都是用ELK。有时间可以将日志转入ELK中。

```java
    <!-- io开头的（io.netty，lettuce） logger -->
    <logger name="io" level="info"/>
    <!--org开头的（ Spring Boot，redisson，apache） logger -->
    <logger name="org" level="info"/>
    <!-- netflix-ribbon logger -->
    <logger name="com.netflix" level="INFO"/>

    <!--开发环境:打印控制台-->
    <springProfile name="dev">
        <root level="DEBUG">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
        </root>
    </springProfile>


    <!--生产环境:输出到文件-->
    <springProfile name="!dev">
        <root level="INFO">
            <appender-ref ref="INFO_FILE" />
            <appender-ref ref="ERROR_FILE" />
        </root>
    </springProfile>
```
## 项目部署
### (1)安装docker和docker-compose，安装组件
执行doc目录里docker-compose.yml文件。即可安装rabbitmq，nacos，mysql，reids等组件。如果nacos安装失败，是因为需要在MySQL初始化nacos-db.sql文件数据。
### (2)启动user-service-app和chat-service-app服务
修改user-service-app和chat-service-app服务配置文件连接rabbitmq，nacos，mysql，reids地址和账号，即可启动user-service-app和chat-service-app服务
### (3)启动chat-client客户端
使用swagger注册账号，修改chat-client配置即可启动客户端。为了方便实现通信，这边dev和test配置文件，使用不同端口和用户id

# 项目业务分析博客
JiChat GitHub [https://github.com/jsl1992/JiChat ](https://github.com/jsl1992/JiChat)
