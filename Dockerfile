#FROM openjdk:8
#FROM openjdk:8-slim
FROM openjdk:8-jdk-alpine as base

RUN echo "Asia/Shanghai" > /etc/timezone

MAINTAINER flankx

RUN mkdir "/opt/app"

WORKDIR /opt/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005'"]

FROM base as build
RUN ./mvnw package

FROM openjdk:8-jre-alpine as production
COPY --from=build /opt/app/target/shadow-*.jar /opt/app/app.jar
EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app/app.jar
