FROM gradle:8.8-jdk17 AS builder

WORKDIR /app
COPY . .
RUN gradle clean jar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/kafka-event-generator-1.0.0.jar app.jar

CMD ["java", "-jar", "app.jar"]