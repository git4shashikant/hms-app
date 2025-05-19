FROM eclipse-temurin:17-jdk-alpine

ADD target/hms-app-*.jar hms-app.jar
EXPOSE 9003

ENTRYPOINT ["java", "-jar", "hms-app.jar"]