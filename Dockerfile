#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:17-jre-ubi9-minimal
COPY --from=build /home/app/target/*.jar /usr/local/lib/legacy-server.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/legacy-server.jar"]