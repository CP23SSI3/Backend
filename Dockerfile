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

RUN mvn clean package -DskipTests

# Create a lightweight runtime image
FROM openjdk:17-jdk-slim

COPY --from=build /app/target/*.jar ./app.jar

# Expose the port that the application will run on
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
