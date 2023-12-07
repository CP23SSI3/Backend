# FROM maven:3.8.4-openjdk-17-slim as build
# WORKDIR /app
# COPY ./pom.xml .
# COPY ./src/ ./src/

# RUN mvn clean package -DskipTests

# FROM openjdk:17-jdk-slim
# ARG JAR_FILE=/app/target/*.jar
# COPY --from=build target/*.jar demo.jar
# EXPOSE 8080
# ENTRYPOINT ["exec", "java", "-jar", "demo.jar"]

# FROM maven:3.8.5-openjdk-17-slim as build
# FROM maven:3.8.4-openjdk-17-slim as build
# WORKDIR /app
# COPY pom.xml .
# COPY src/ ./src/

# RUN mvn clean package -DskipTests

# FROM openjdk:17-jdk-slim
# # FROM openjdk:17-jdk-alpine
# WORKDIR /app
# ARG JAR_FILE=/app/target/*.jar

# COPY --from=build ${JAR_FILE} app.jar

# EXPOSE 8080

# ENTRYPOINT exec java -jar app.jar

# Use a base image with Maven and Java 17 pre-installed
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy the source code and pom.xml to the container
COPY pom.xml .
COPY src ./src

# Build the application with Maven
RUN mvn spring-boot:run

# Create a lightweight runtime image
FROM adoptopenjdk/openjdk17:alpine-jre
WORKDIR /app

COPY --from=build /app/target/demo2-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
