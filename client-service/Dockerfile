FROM openjdk:17.0.2-jdk-oracle
WORKDIR /app
COPY /target/client-service-0.0.1-SNAPSHOT.jar /app/client-service.jar
ENTRYPOINT ["java", "-jar", "client-service.jar"]
