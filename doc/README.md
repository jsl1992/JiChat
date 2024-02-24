### 环境部署说明

#### doc 目录结构

```shell
.
├── doc
│   ├── mysql
│   │   ├── conf
│   │   └── source
│   └── script
│       └── docker-compose.yml
```

- `mysql`：mysql 相关的配置以及脚本文件
  - `conf`：mysql 的配置文件
  - `source`：mysql 的初始化脚本
- `script`：部署需要执行的脚本
  - `docker-compose.yml`：docker 部署的脚本文件

### 快速开始

> 需要 docker 环境支持，请在如下命令执行打印正常后继续
>
> ```shell
> > docker-compose -v
> Docker Compose version v2.18.1
> ```

1. 创建 `/data` 目录

2. 上传项目中的 `/mysql` 目录到 `/data` 目录下
3. 上传项目的 `docker-compose.yml` 配置文件到 `/data` 目录下

第 2 步和第 3 步操作后的效果如下

```shell
.
├── docker-compose.yml
└── mysql
    ├── conf
    │   └── my.cnf
    └── source
        ├── jichat_user.sql
        └── nacos-db.sql
```

4. 执行 `docker-compose up -d`，等待控制台打印完毕

```shell
[+] Running 5/5
 ✔ Network   data_ji-chat      Created                             0.4s 
 ✔ Container ji-chat-mysql     Started                             0.9s 
 ✔ Container ji-chat-rabbitmq  Started                             1.2s 
 ✔ Container ji-chat-redis     Started                             1.1s 
 ✔ Container ji-chat-nacos     Started                             1.7s
```

#### 注释事项

1. 宿主机注意开放相应的端口以供容器外的应用访问