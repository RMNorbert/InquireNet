FROM openjdk:17-jdk
WORKDIR /app

COPY target/InquireNet-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "InquireNet-0.0.1-SNAPSHOT.jar"]
