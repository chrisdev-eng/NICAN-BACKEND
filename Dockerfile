# ===============================================
# Build em dois estágios:
#   1. maven:3.9-eclipse-temurin-21 compila o projeto
#   2. eclipse-temurin:21-jre-alpine só executa o .jar
# Assim a imagem final fica leve (~180MB) e
# não precisa de nenhuma instalação local prévia.
# ===============================================

# ── Estágio 1: compilar ──────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar o pom.xml primeiro para aproveitar cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copiar o código-fonte e compilar
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Estágio 2: executar ──────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar somente o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# O app é um CLI interativo, sem porta HTTP exposta
# EXPOSE 8080  ← removido: este sistema é console, não servidor web

# Iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
