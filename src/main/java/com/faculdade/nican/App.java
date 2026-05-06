package com.faculdade.nican;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.nican.model.Sessao;
import com.faculdade.nican.ui.menu.MenuAlmoxarife;
import com.faculdade.nican.ui.menu.MenuDevolucao;
import com.faculdade.nican.ui.menu.MenuLogin;
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

    Scanner leitor = new Scanner(System.in);
    Sessao sessao = Sessao.get();

    int escolha;
    boolean rodando = true;

    do {
      System.out.println("\n\n\n\n====== Bem vindo ao Sistema Nican ======\n");
      sessao.imprimirStatusSessao();
      System.out.println("\nO que voce deseja fazer?\n");

      System.out.println("[1] ~ Fazer Login no Sistema.");
      System.out.println("[2] ~ Criar uma conta.");
      System.out.println("[3] ~ Redefinir senha.");
      System.out.println("[4] ~ Sair da Conta (Logout).\n");

      if (sessao.estaLogado()) {
        System.out.println("[5] ~ Ver Almoxarife (Lista de Materiais).");

        if (sessao.usuarioEhAdmin()) {
          // Admin vê: painel admin + controle de devoluções
          System.out.println("[6] ~ Painel do Administrador.");
          System.out.println("[7] ~ Controle de Devolucao e Responsavel.");
        } else {
          // Usuário vê: histórico de requisições + devolução
          System.out.println("[6] ~ Minhas Requisicoes e Devolucoes.");
        }
      }

      System.out.println("\n[0] ~ Sair do Sistema.\n\n");

      try {
        escolha = leitor.nextInt();

        switch (escolha) {
          case 0:
            rodando = false;
            System.out.println("\nSaindo do Sistema Nican. Ate logo!\n\n");
            break;

          case 1:
            MenuLogin.fazerLogin(leitor);
            break;

          case 2:
            MenuLogin.cadastrarUsuario(leitor);
            break;

          case 3:
            MenuLogin.redefinirSenha(leitor);
            break;

          case 4:
            MenuLogin.fazerLogout();
            break;

          case 5:
            if (sessao.estaLogado()) {
              MenuAlmoxarife.abrir(leitor);
            } else {
              System.out.println("\n  [AVISO] Faca login primeiro para acessar o Almoxarife.\n");
            }
            break;

          case 6:
            if (!sessao.estaLogado()) {
              System.out.println("\n  [AVISO] Faca login primeiro.\n");
            } else if (sessao.usuarioEhAdmin()) {
              MenuLogin.painelAdmin(leitor);
            } else {
              MenuDevolucao.abrir(leitor);
            }
            break;

          case 7:
            if (sessao.estaLogado() && sessao.usuarioEhAdmin()) {
              MenuDevolucao.abrir(leitor);
            } else {
              System.out.println("\n  [AVISO] Opcao invalida.\n");
            }
            break;

          default:
            System.out.println("\nOpcao invalida! Tentando novamente...\n\n\n\n");
            break;
        }

      } catch (InputMismatchException e) {
        System.out.println("Entrada/Valor Invalido! Tentando novamente... \n\n\n\n");
        leitor.nextLine();
      }

    } while (rodando);

    leitor.close();
  }
}
