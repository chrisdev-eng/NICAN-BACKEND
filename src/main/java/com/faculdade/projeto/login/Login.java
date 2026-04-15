package com.faculdade.projeto.login;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.login.classes.Sessao;
import com.faculdade.projeto.login.menus.MenuLogin;

/**
 *  ~ Classe principal do modulo de Login ~
 *  Chamada pelo App.java.
 */
public class Login {





  public static void abrirMenu(Scanner leitor) {
    Sessao sessao = Sessao.get();
    boolean rodando = true;
    int escolha = 0;

    do {
      System.out.println("\n\n====== Sistema Nican — Conta ======\n");
      sessao.imprimirStatusSessao();

      if (!sessao.estaLogado()) {
        System.out.println("[1] ~ Fazer Login.");
        System.out.println("[2] ~ Criar uma conta.");
        System.out.println("[3] ~ Redefinir senha.");
        System.out.println("\n[4] ~ Voltar ao Menu Principal.\n\n");
      } else {
        System.out.println("[1] ~ Redefinir minha senha.");
        if (sessao.usuarioEhAdmin()) {
          System.out.println("[2] ~ Painel do Administrador.");
        }
        System.out.println("\n[3] ~ Sair da conta (Logout).");
        System.out.println("[4] ~ Voltar ao Menu Principal.\n\n");
      }





      try {
        escolha = leitor.nextInt();

        if (!sessao.estaLogado()) {
          switch (escolha) {
            case 1: MenuLogin.fazerLogin(leitor);        break;
            case 2: MenuLogin.cadastrarUsuario(leitor);  break;
            case 3: MenuLogin.redefinirSenha(leitor);    break;
            default:
              rodando = false;
              System.out.println("\nVoltando...\n\n\n\n");
              break;
          }
        } else {
          switch (escolha) {
            case 1:
              MenuLogin.redefinirSenha(leitor);
              break;
            case 2:
              if (sessao.usuarioEhAdmin()) MenuLogin.painelAdmin(leitor);
              else { rodando = false; System.out.println("\nVoltando...\n\n\n\n"); }
              break;
            case 3:
              MenuLogin.fazerLogout();
              rodando = false;
              break;
            default:
              rodando = false;
              System.out.println("\nVoltando...\n\n\n\n");
              break;
          }
        }

      } catch (InputMismatchException e) {
        System.out.println("Entrada invalida! Tentando novamente...");
        leitor.nextLine();
      }

    } while (rodando);
  }



  public static void Cadastro(Scanner leitor)       { MenuLogin.cadastrarUsuario(leitor); }
  public static void fazerLogin(Scanner leitor)     { MenuLogin.fazerLogin(leitor); }
  public static void redefinirSenha(Scanner leitor) { MenuLogin.redefinirSenha(leitor); }
  public static void Sair()                         { MenuLogin.fazerLogout(); }
}
