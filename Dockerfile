FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM tomcat:10.1-jdk17
COPY --from=build /app/target/api.war /usr/local/tomcat/webapps/api.war