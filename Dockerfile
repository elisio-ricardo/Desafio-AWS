FROM eclipse-temurim:17-jdk-alpine
WORKDIR /APP
COPY target/desafioItau-1.0.0.jar desafioItau-1.0.0.jar
EXPOSE 8080
CMD [ "java", "jar", "desafioItau-1.0.0.jar" ]
