FROM openjdk:21-jdk-slim AS build

WORKDIR /app

# Copier les fichiers de projet Gradle
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./
COPY src ./src

# Donner les permissions d'exécution au script gradlew
RUN chmod +x ./gradlew

# Construire l'application
RUN ./gradlew build --no-daemon

FROM openjdk:21-jdk-slim

WORKDIR /app

# Copier le JAR depuis l'étape de construction
COPY --from=build /app/build/libs/library-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]