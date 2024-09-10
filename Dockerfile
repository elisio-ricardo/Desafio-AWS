FROM eclipse-temurin:17-jdk-alpine
WORKDIR /APP
COPY target/desafioAWS-1.0.0.jar desafioAWS-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "desafioAWS-1.0.0.jar" ]
