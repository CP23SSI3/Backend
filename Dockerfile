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
#  --------------
# Use a base image with Maven and Java 17 pre-installed
# FROM maven:3.8.4-openjdk-17 AS build
# WORKDIR /app

# # Copy the source code and pom.xml to the container
# COPY pom.xml .
# COPY src ./src

# # Build the application with Maven
# RUN mvn spring-boot:run

# -----------



# ------ image maven ok but error  --from=build /app/target/*.jar ./app.jar-----
# Pull base image.
FROM alpine as build

ARG MAVEN_VERSION=3.9.6
ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

# Install Java.
RUN apk --update --no-cache add openjdk17 curl

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

# Define working directory.
WORKDIR /app

COPY pom.xml .
COPY src ./src

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/default-jvm/

# Define default command.
# RUN mvn clean install -U
CMD ["mvn", "spring-boot:run"]

# Create a lightweight runtime image
# FROM adoptopenjdk/openjdk17:alpine-jre
# FROM openjdk:17-jdk-slim
# WORKDIR /app

# COPY --from=build /app/target/*.jar ./app.jar

# EXPOSE 8080

# # Set the entrypoint to run the Spring Boot application
# ENTRYPOINT ["java", "-jar", "app.jar"]

# --------------- AI ------------------
# # Use a base image with Maven and Java 17 pre-installed
# FROM maven:3.9.6-openjdk-17 AS build

# # Set the working directory in the container
# WORKDIR /app

# # Copy the source code and pom.xml to the container
# COPY pom.xml .
# COPY src ./src

# # Build the application with Maven
# RUN mvn clean package -DskipTests

# Create a lightweight runtime image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
# WORKDIR /app

# Define the argument for the JAR file
# ARG JAR_FILE=target/*.jar
# ARG JAR_FILE=/app/target/*.jar
COPY ./target/*.jar ./app.jar

# Copy the JAR file from the build stage to the runtime image
# COPY --from=build /app/target/*.jar ./app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
