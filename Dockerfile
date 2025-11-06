FROM eclipse-temurin:21-jdk

WORKDIR /bron

COPY build.gradle .
COPY src /bron/src
COPY build/libs/bron-0.0.1-SNAPSHOT.jar bron-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "bron-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]
