FROM maven:3.8.3-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/service-provider-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 80

CMD ["java", "-jar", "app.jar"]