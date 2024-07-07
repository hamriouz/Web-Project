#FROM postgres:latest
#
#ENV POSTGRES_DB=hamraz
#ENV POSTGRES_USER=postgres
#ENV POSTGRES_PASSWORD=postgres
#
#EXPOSE 5432
#
#HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
#    CMD pg_isready -U $POSTGRES_USER || exit 1
#
#CMD ["postgres"]


# First stage: Build the application with Maven
FROM maven:3.6.0-jdk-8-alpine AS build

# Set the working directory
WORKDIR /app

# Add the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the entire project
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Second stage: Create a lightweight image for running the application
FROM openjdk:8-jdk-alpine

# Set the working directory
WORKDIR /root/

# Copy the jar file from the build stage
COPY --from=build /app/target/*-jar-with-dependencies.jar app.jar

# Set the entrypoint
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
