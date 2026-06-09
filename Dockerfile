# Build em dois estagios:
# 1. maven:3.9-eclipse-temurin-21 compila o projeto
# 2. eclipse-temurin:21-jre-alpine empacota o JAR
#
# O NICAN e uma aplicacao desktop Java Swing. Para uso normal,
# suba apenas o PostgreSQL pelo docker-compose e execute o JAR localmente
# com Java 17. Rodar Swing dentro de container exige display grafico externo.

FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
