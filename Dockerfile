FROM amazoncorretto:11.0.17
ADD target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]