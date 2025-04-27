# BUILD STAGE
# Using an official Maven image to build the Spring Boot App
FROM maven:3.8.4-openjdk-17 AS build

# Setting the working directory
WORKDIR /build

# Copying the pom.xml and installing dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copying source code and build the jar app
COPY src ./src
RUN mvn clean package -DskipTests -B

# RUNTIME STAGE
# Using a lightweight JRE base image
FROM amazoncorretto:17

ARG PROFILE=prod
ARG APP_VERSION=1.0.0

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /build/target/pilot-api.jar pilot-api.jar

EXPOSE 8081

ENV DB_URL=jdbc:postgresql://postgres-pilot:5432/pilot_db
ENV MAILDEV_URL=localhost
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

#CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} student-management-${JAR_VERSION}.jar