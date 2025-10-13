FROM openjdk:19-jdk-alpine
WORKDIR /bron
COPY . .
RUN apk add --no-cache bash
RUN chmod +x gradlew
RUN ./gradlew clean build
EXPOSE 8080
CMD ["java", "-jar", "build/libs/bron-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]