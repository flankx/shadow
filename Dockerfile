#FROM openjdk:8
#FROM openjdk:8-slim
FROM openjdk:8-jdk-alpine

RUN echo "Asia/Shanghai" > /etc/timezone

MAINTAINER flankx

RUN mkdir "/opt/app"

WORKDIR /opt/app

ADD ./target/shadow-0.1.0-SNAPSHOT.jar /opt/app/app.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar
