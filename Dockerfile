FROM maven:3-eclipse-temurin-17 AS build

ENV MAVEN_OPTS="-Xmx2048m -Xms512m"

ENV MAVEN_CONFIG=/home/app/.m2
RUN mkdir -p /home/app/.m2 && \
    chown -R 1000:1000 /home/app/.m2

VOLUME /home/app/.m2

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests


# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY --from=build /app/target/whisper.jar whisper.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx1024m", "-Xms512m", "-jar", "whisper.jar"]
