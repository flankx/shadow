#FROM openjdk:8
#FROM openjdk:8-slim
FROM openjdk:8-jdk-alpine

MAINTAINER flankx

RUN mkdir "/opt/app"

WORKDIR /opt/app

ADD ./target/shadow-0.1.0-SNAPSHOT.jar /opt/app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar"]

CMD ["app.jar"]
