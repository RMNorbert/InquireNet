FROM maven:3.8.3-openjdk-17-slim AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /project/target/InquireNet*.jar  inquirenet.jar
EXPOSE 8080
CMD ["java", "-jar", "inquirenet.jar"]
