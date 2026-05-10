# ===============================================
<<<<<<< HEAD
# Imagem leve apenas para rodar o projeto
# IMPORTANTE: Antes de buildar, compile o projeto
# localmente com: mvn clean package -DskipTests
# ===============================================

# Usa uma imagem com JRE 21 (apenas para executar, sem Maven)
# O alpine e uma versao minimalista do Linux (~100MB)
FROM eclipse-temurin:21-jre-alpine

# Define /app como diretorio de trabalho dentro do container
# (todos os comandos abaixo serao executados aqui)
WORKDIR /app

# Copia o .jar gerado localmente (pasta target/) para dentro do container
# Certifique-se de ter rodado: mvn clean package -DskipTests antes!
COPY target/*.jar app.jar

# Informa que o container vai utilizar a porta 8080
# (para expor a porta ao rodar, use: docker run -p 8080:8080)
EXPOSE 8080

# Comando executado quando o container iniciar
# Equivale a rodar: java -jar app.jar no terminal
=======
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
>>>>>>> 91d1f63 (Finalizacao da parte final do codigo base de BackEnd do projeto)
ENTRYPOINT ["java", "-jar", "app.jar"]
