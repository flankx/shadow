## 项目配置

+ SpringBoot 2.7.X
+ Spring shiro 1.12.0
+ Kaptcha 2.3.2
+ layui 2.8.16
+ MySQL 8
+ druid-spring-boot-starter 1.2.19
+ Docker

## Docker 启动配置

+ 编译并打包 `maven clean package`
+ 生成镜像：在 `Dockerfile` 目录下执行 `docker build -t snowflyaway/shadow:{tagname} .`
+ 部署镜像：在 `Dockerfile` 目录下执行 `docker compose up -d`
+ 查看编排的镜像：`docker compose ls`
+ 检查服务运行状态：http://localhost:8080

## Kaptcha详细配置表

| 序号  | 属性名                           | 描述          | 示例                    |
|-----|-------------------------------|-------------|-----------------------|
| 1   | kaptcha.width	                | 验证码宽度	      | 200                   |
| 2   | kaptcha.height	               | 验证码高度	      | 50                    |
| 3   | kaptcha.border.enabled	       | 是否显示边框	     | false                 |
| 4   | kaptcha.border.color	         | 边框颜色	       | black                 |
| 5   | kaptcha.border.thickness	     | 边框厚度	       | 2                     |
| 6   | kaptcha.content.length	       | 验证码文本长度	    | 5                     |
| 7   | kaptcha.content.source	       | 文本源	        | abcde2345678gfynmnpwx |
| 8   | kaptcha.content.space	        | 文本间隔        | 2                     |
| 9   | kaptcha.font.name	            | 字体名称        | Arial                 |
| 10  | kaptcha.font.size	            | 字体大小        | 40                    |
| 11  | kaptcha.font.color	           | 字体颜色        | black                 |
| 12  | kaptcha.background-color.from | 背景颜色（开始渐变色） | lightGray             |
| 13  | kaptcha.background-color.to	  | 背景颜色（结束渐变色） | white                 |
