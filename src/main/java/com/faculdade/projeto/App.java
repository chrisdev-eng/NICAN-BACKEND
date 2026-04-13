package com.faculdade.projeto;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.Almoxarife;
import com.faculdade.projeto.almoxarife.classes.ListaItems;
import com.faculdade.projeto.login.Login;
import com.faculdade.projeto.login.classes.Sessao;
import org.flywaydb.core.Flyway;


/**
 *  ~ Sistema Nican — Ponto de entrada principal ~
 */
public class App {
  public static void main(String[] args) {

    // CORRECAO: senha atualizada para bater com docker-compose.yml (era string vazia)
    try {
      Flyway flyway = Flyway.configure()
              .dataSource("jdbc:postgresql://localhost:5432/nicandb", "postgres", "postgres")
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
    ListaItems almoxarife = new ListaItems();
    Sessao sessao = Sessao.get();

    int escolhaSistema;
    boolean sistemaCanRun = true;

    do {
      System.out.println("\n\n\n\n====== Bem vindo ao Sistema Nican ======\n");
      sessao.imprimirStatusSessao();
      System.out.println("\nO que voce deseja fazer?\n");

      System.out.println("[1] ~ Fazer Login no Sistema.");
      System.out.println("[2] ~ Criar uma conta pro Sistema.");
      System.out.println("[3] ~ Redefinir senha.");
      System.out.println("[4] ~ Sair da Conta (Logout).\n");

      if (sessao.estaLogado()) {
        System.out.println("[5] ~ Ver Almoxarife (Lista de Materiais).");

        if (sessao.usuarioEhAdmin()) {
          System.out.println("[6] ~ Painel do Administrador.");
        }
      }

      System.out.println("\n[0] ~ Sair do Sistema.\n\n");

      try {
        escolhaSistema = leitor.nextInt();

        switch (escolhaSistema) {
          case 0:
            sistemaCanRun = false;
            System.out.println("\nSaindo do Sistema Nican. Ate logo!\n\n");
            break;

          case 1:
            Login.fazerLogin(leitor);
            break;

          case 2:
            Login.Cadastro(leitor);
            break;

          case 3:
            Login.redefinirSenha(leitor);
            break;

          case 4:
            Login.Sair();
            break;

          case 5:
            if (sessao.estaLogado()) {
              Almoxarife.main(args, leitor, almoxarife);
            } else {
              System.out.println("\n  [AVISO] Faca login primeiro para acessar o Almoxarife.\n");
            }
            break;

          case 6:
            Login.abrirMenu(leitor);
            break;

          default:
            System.out.println("\nOpcao invalida! Tentando novamente...\n\n\n\n");
            break;
        }

      } catch (InputMismatchException e) {
        System.out.println("Entrada/Valor Invalido! Tentando novamente... \n\n\n\n");
        leitor.nextLine();
      }

    } while (sistemaCanRun);

    leitor.close();
  }
}
