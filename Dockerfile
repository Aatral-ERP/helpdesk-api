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
COPY helpdesk-api-0.0.1-SNAPSHOT.jar /usr/local/lib/helpdesk-api.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","/usr/local/lib/helpdesk-api.jar"]
