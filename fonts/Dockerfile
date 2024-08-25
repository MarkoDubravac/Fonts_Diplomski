FROM openjdk:latest
LABEL authors="marko"

COPY target/fonts-0.0.1-SNAPSHOT.jar fonts.jar

ENTRYPOINT ["java", "-jar", "/fonts.jar"]