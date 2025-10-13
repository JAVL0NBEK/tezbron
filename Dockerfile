FROM openjdk:19-jdk-alpine

# === Working directory ===
WORKDIR /bron

# === Copy JAR file ===
COPY build/libs/bron-0.0.1-SNAPSHOT.jar app.jar

# === Expose port ===
EXPOSE 8080

# === Run the app with prod profile ===
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]