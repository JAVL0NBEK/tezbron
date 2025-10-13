# Java 19 JDK bazaviy imij
FROM openjdk:19-jdk-alpine

# Konteyner ichida ishchi papka
WORKDIR /bron

# Maven build natijasini kopyalash (target papkadan)
COPY target/bron-0.0.1-SNAPSHOT.jar bron-0.0.1-SNAPSHOT.jar

# Ilova porti
EXPOSE 8080

# Prod profil bilan ishga tushirish
CMD ["java", "-jar", "bron-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]