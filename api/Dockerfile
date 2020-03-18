# syntax=docker/dockerfile:experimental
FROM maven:3.6.3-jdk-8

COPY target/vocabulary-service-0.0.1-SNAPSHOT.jar vocabulary-service.jar

ENTRYPOINT [ "java", "-jar", "vocabulary-service.jar"]
