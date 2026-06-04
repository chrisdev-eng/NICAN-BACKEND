package com.faculdade.nican;

import com.faculdade.nican.view.TelaHome;
import org.flywaydb.core.Flyway;

/**
 *  ~ Sistema Nican — Ponto de entrada principal ~
 *
 *  Regras de Negócio:
 *    1. Conta desativada não realiza login.
 *    2. Somente contas ADMIN criam contas, itens, validam requerimentos etc.
 *    3. Login duplicado não é permitido.
 *    4. Somente admin cadastra admin.
 *    5. Itens com requerimentos não podem ser removidos.
 *    6. Menu dinâmico dependendo do tipo de usuário.
 *    7. Admin: controle de devolução e responsável pelo empréstimo.
 *    8. Usuário: histórico de requisições e confirmação de devolução.
 */
public class App {

  public static void main(String[] args) {

    // Inicializa o banco via Flyway (garante tabelas em ordem antes de qualquer operação)
    try {
      String jdbcUrl = "jdbc:postgresql://localhost:5432/nicandb";
      Flyway flyway = Flyway.configure()
              .dataSource(jdbcUrl, "postgres", "postgres")
              .locations("classpath:db/migration")
              .baselineOnMigrate(true)
              .load();

      flyway.migrate();
      System.out.println("[OK] Banco inicializado pelo Flyway.\n");
    } catch (Exception e) {
      System.out.println("[ERRO] Falha ao executar Flyway: " + e.getMessage());
      System.out.println("Verifique se o PostgreSQL esta rodando e o banco 'nicandb' existe.");
      return;
    }

    // Abre a interface gráfica em vez do menu de terminal
    new TelaHome();
  }
}