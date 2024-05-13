FROM openjdk:17
LABEL authors="User"
EXPOSE 8084
ADD target/course-service-docker.jar course-service-docker.jar

ENTRYPOINT ["java", "-jar", "/course-service-docker.jar"]