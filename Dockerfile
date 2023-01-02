#
#Build stage
#
#FROM maven:3.5.0-jdk-8-slim AS build
#COPY src /home/helpdesk-api/
#COPY pom.xml /home/helpdesk-api
#RUN mvn -f /home/helpdesk-api/pom.xml clean package
#
# Package stage
#
FROM openjdk:8-jre
COPY target/helpdesk-api.jar /usr/local/lib/helpdesk-api.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/usr/local/lib/helpdesk-api.jar","-Dspring.profiles.active=autolib-test"]
