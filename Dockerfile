# ===============================================
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
ENTRYPOINT ["java", "-jar", "app.jar"]
