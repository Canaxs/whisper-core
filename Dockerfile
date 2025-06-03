# Build
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime
FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY --from=build /app/target/whisper.jar whisper.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "whisper.jar"]
