FROM openjdk:21-jdk-slim-buster

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY /build/libs/qp-assessment-*-RELEASE.jar app.jar

# Expose the port that the app will run on
EXPOSE 9080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
