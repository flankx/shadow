FROM openjdk:8-jre-alpine
MAINTAINER flankx
WORKDIR /opt/app
RUN echo "Asia/Shanghai" > /etc/timezone
ADD ./target/shadow-*.jar /opt/app/app.jar
# 更新字体库
RUN apk add --update font-adobe-100dpi ttf-dejavu fontconfig
# 暴露端口
EXPOSE 8080
# 运行参数
ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar
