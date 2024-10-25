# Use a base image with JDK 17 for building the application
FROM maven:3.8.5-openjdk-17-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the src directory for Maven to build
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a smaller image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/ProjetoIntegradorVI-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
