# Use lightweight OpenJDK base image
FROM openjdk:19-jdk-alpine

# Set working directory
WORKDIR /bron

# Copy the JAR file
COPY build/libs/bron-0.0.1-SNAPSHOT.jar bron-0.0.1-SNAPSHOT.jar

# Expose app port
EXPOSE 8080

# Run Spring Boot app with prod profile
CMD ["java", "-jar", "bron-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]
