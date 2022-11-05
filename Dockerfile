FROM amazoncorretto:11-alpine3.13-jdk
ADD target/app.jar app.jar
CMD ["java", "-jar", "app.jar"]