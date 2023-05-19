FROM maven:3-jdk-8-alpine as build
MAINTAINER flankx
RUN mkdir /opt/app
WORKDIR /opt/app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -U -DskipTests

FROM openjdk:8-jre-alpine
RUN echo "Asia/Shanghai" > /etc/timezone
COPY --from=build /opt/app/target/shadow-*.jar /app.jar
EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
