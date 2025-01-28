# Build stage
FROM maven:3.8-openjdk-8 AS builder
WORKDIR /build
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
COPY data/pay.db data/pay.db
CMD ["java", "-jar", "app.jar"] 
