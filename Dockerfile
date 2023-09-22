FROM openjdk:8-jre-alpine
MAINTAINER flankx
WORKDIR /opt/app
RUN echo "Asia/Shanghai" > /etc/timezone
ADD ./target/shadow-*.jar /opt/app/app.jar
EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar
