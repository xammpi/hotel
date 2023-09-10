FROM openjdk:17.0.2-jdk-oracle
WORKDIR /app
COPY /target/core-service-0.0.1-SNAPSHOT.jar /app/core-service.jar
ENTRYPOINT ["java", "-jar", "core-service.jar"]
