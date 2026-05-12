# NICAN — Sistema de Gestão Escoteira

Sistema desktop em Java Swing para gestão interna de grupos escoteiros.  
Desenvolvido como Projeto Integrador/Mensal da Faculdade.

---

## Sobre o Sistema

O NICAN centraliza a gestão de materiais e usuários do grupo, com controle de acesso por perfil (Administrador). Permite cadastro de usuários, gerenciamento do almoxarifado de itens, redefinição de senha e controle de contas — tudo através de uma interface gráfica desenvolvida com Java Swing.

---

## Tecnologias Utilizadas

- **Java 17**
- **Maven** — gerenciamento de dependências e build
- **Java Swing** — interface gráfica desktop
- **Hibernate / JPA** — persistência de dados
- **PostgreSQL 17** — banco de dados
- **Flyway** — migrations automáticas do banco
- **Docker / Docker Compose** — ambiente do banco de dados

---

## Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

| Ferramenta | Versão mínima | Download |
|---|---|---|
| Java JDK | 17 | https://adoptium.net |
| Maven | 3.8+ | https://maven.apache.org |
| Docker Desktop | qualquer | https://www.docker.com/products/docker-desktop |

---

## Como Rodar o Projeto

### Passo 1 — Subir o banco de dados com Docker

Abra o terminal na pasta raiz do projeto (onde está o `docker-compose.yml`) e execute:

```bash
docker compose up -d postgres
```

Isso vai iniciar o PostgreSQL na porta **5432** com as configurações:

| Parâmetro | Valor |
|---|---|
| Host | localhost |
| Porta | 5432 |
| Banco | nicandb |
| Usuário | postgres |
| Senha | postgres |

Para verificar se o banco subiu corretamente:

```bash
docker compose ps
```

O status do container `nicandb` deve aparecer como `healthy`.

---

### Passo 2 — Compilar o projeto

Na pasta raiz do projeto, execute:

```bash
mvn clean package -DskipTests
```

Isso vai compilar o código e gerar o arquivo `.jar` na pasta `target/`.

---

### Passo 3 — Executar a aplicação

```bash
java -jar target/nican-1.0-SNAPSHOT.jar
```

A interface gráfica do NICAN será aberta automaticamente.  
O Flyway já cria todas as tabelas no banco na primeira execução.

---

## Estrutura do Projeto

```
NICAN/
├── src/
│   └── main/
│       ├── java/com/faculdade/nican/
│       │   ├── App.java                  ← Ponto de entrada
│       │   ├── model/                    ← Entidades (Usuario, Item, etc.)
│       │   ├── repository/               ← Acesso ao banco de dados
│       │   ├── service/                  ← Regras de negócio
│       │   ├── ui/menu/view/             ← Telas Swing (uma classe por tela)
│       │   ├── config/                   ← Configuração JPA
│       │   └── util/                     ← Utilitários (validações)
│       └── resources/
│           └── db/migration/             ← Scripts SQL do Flyway
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## Funcionalidades

- **Tela Inicial** — Login, Criar Conta, Redefinir Senha
- **Login** — Autenticação com e-mail e senha (somente admins acessam o sistema)
- **Painel Admin** — Gerenciar usuários e acessar o almoxarifado
- **Almoxarifado** — Listar, adicionar e remover itens com categoria e qualidade
- **Painel Administrador** — Listar usuários, cadastrar novos admins, desativar contas

---

## Encerrando o Banco

Quando terminar, para parar o container do banco:

```bash
docker compose down
```

Para parar **e apagar os dados**:

```bash
docker compose down -v
```

---

## Alunos

- Henrique F. Pantaleão
- Christian Ferreira
- Daniel Nunez