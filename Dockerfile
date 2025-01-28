# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/*.jar app.jar
COPY data/ data/

# Compile the application
RUN ./mvnw package

# Run the application
CMD ["java", "-jar", "app.jar"] 