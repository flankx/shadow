## 项目配置

+ SpringBoot 2.7.6
+ Spring shiro 1.10.0
+ MySQL 8.0.32
+ Docker compose

## Docker 启动配置

+ 编译并打包 `maven build package`
+ 生成镜像：在 `Dockerfile` 目录下执行 `docker build -t snowflyaway/shadow:{tagname} .`
+ 部署镜像：在 `Dockerfile` 目录下执行 `docker compose up -d`
+ 查看编排的镜像：`docker compose ls`
+ 检查服务运行状态：http://localhost:8080