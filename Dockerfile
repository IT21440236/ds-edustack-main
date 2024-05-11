FROM openjdk:17
LABEL authors="HP"
EXPOSE 9090
ADD target/payment-service-docker.jar payment-service-docker.jar

ENTRYPOINT ["java", "-jar", "/payment-service-docker.jar"]