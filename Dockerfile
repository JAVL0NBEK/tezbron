# Use an appropriate base image that contains the JDK and other necessary tools
FROM openjdk:19-jdk-alpine

# Set the working directory in the container
WORKDIR /bron

# Copy the jar file (only the fat jar, NOT plain.jar)
COPY build/libs/bron-0.0.1-SNAPSHOT.jar bron-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "bron-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]