# NICAN-BACKEND

#### Projeto Integrador / Mensal da Faculdade 

O backend do site Nican Mopohua é um sistema de gestão para grupos escoteiros, desenvolvido para digitalizar 
e otimizar processos administrativos internos. Ele centraliza a gestão de presenças e materiais em uma plataforma 
segura, com controle de acesso por perfis, permitindo que jovens e chefes utilizem funcionalidades específicas
conforme seu nível hierárquico. Esta implementação serve como base para a futura evolução do projeto em uma 
aplicação web completa.


## Estrutura do Projeto
Os arquivos principais do Projeto estao localizados em: 
```bash
# Nosso Arquivo Principal
./src/main/java/com/faculdade/projeto/App.java

# Cada Funcionabilidade
./src/main/java/com/faculdade/projeto/almoxarife/[...]
./src/main/java/com/faculdade/projeto/loginSingin/[...]
./src/main/java/com/faculdade/projeto/presenca/[...]
```

## Ferramentas utilizadas e Instalation
O projeto em um todo foi feito na linguagem de Programacao Java, e foi feito em Maven para poder ser rodado.
Utilizamos Git/Github para upload dos arquivos em nuvem, alem de Docker para poder rodar o projeto de forma 
mais dinamica em outros dispositivos.

Para rodar o projeto para testes, utilize o seguintes comandos (docker):
```bash
# Compila o Maven antes localmente... 
mvn clean package -DskipTests

# Dps faz um build do Docker
docker build -t projeto-mensal .

# Por fim, manda o bixao rodar
docker run -p 8080:8080 projeto-mensal
```

## Alunos 
- Herinque F. Pantaleão 
- Christian Ferreira 
- Daniel Nunez

