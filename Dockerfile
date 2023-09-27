FROM openjdk:11

EXPOSE 8080

COPY target/springboot-backend-0.0.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

