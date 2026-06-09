# NICAN - Sistema de Gestao Escoteira

Aplicacao desktop em Java Swing para gestao de usuarios, materiais, requerimentos, emprestimos e devolucoes.

## Tecnologias

- Java 17
- Maven
- Java Swing
- Hibernate / JPA
- PostgreSQL
- Flyway
- Docker Compose para subir o banco local

## Como Rodar

1. Suba o PostgreSQL:

```bash
docker compose up -d postgres
```

2. Compile o projeto:

```bash
mvn clean package -DskipTests
```

3. Execute a aplicacao desktop:

```bash
java -jar target/nican-1.0-SNAPSHOT.jar
```

O banco padrao usado pela aplicacao e:

| Campo | Valor |
|---|---|
| Host | localhost |
| Porta | 5432 |
| Database | nicandb |
| Usuario | postgres |
| Senha | postgres |

Tambem e possivel sobrescrever a conexao com variaveis de ambiente:

- `DB_HOST`
- `DB_PORT`
- `DB_NAME`
- `DB_USER`
- `DB_PASS`

Se `DB_PASS` estiver vazio ou ausente, a aplicacao usa `postgres`.

## Estrutura MVC

```text
src/main/java/com/faculdade/nican/
  App.java
  config/
    DbConfig.java
    JPAUtils.java
  controller/
    AdminController.java
    LoginController.java
    UsuarioController.java
    ItemController.java
    RequerimentoController.java
    EmprestimoController.java
    DevolucaoController.java
  model/
    Admin.java
    Usuario.java
    Item.java
    Requerimento.java
    Emprestimo.java
    Sessao.java
    Categoria.java
    Qualidade.java
    Perfil.java
  repository/
    AdminRepository.java
    UsuarioRepository.java
    ItemRepository.java
    RequerimentoRepository.java
    EmprestimoRepository.java
  service/
    AdminService.java
    LoginService.java
    UsuarioService.java
    ItemService.java
    RequerimentoService.java
    EmprestimoService.java
  util/
    Validador.java
  view/
    Telas Swing do sistema
```

Fluxo de dependencia esperado:

```text
view -> controller -> service -> repository -> database
model = entidades e enums
```

## Observacoes

- Os menus antigos de console foram removidos para evitar chamadas diretas de View para Repository.
- A aplicacao principal e Swing, aberta por `App.java`.
- O Docker Compose sobe apenas o PostgreSQL. Rodar a interface Swing dentro de container nao e o fluxo recomendado.
- A aprovacao de requerimento valida estoque no Java, mas a baixa efetiva fica com os triggers do banco para evitar desconto duplicado.
