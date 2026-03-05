package com.faculdade.projeto.login;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.login.classes.Sessao;
import com.faculdade.projeto.login.menus.MenuLogin;

/**
 *  ~ Classe principal do modulo de Login ~
 *
 *  Substitui o Login.java vazio original.
 *  E chamada pelo App.java exatamente como o Almoxarife.java e chamado.
 *
 *  Uso no App.java:
 *    case 1: Login.fazerLogin(leitor);    break;
 *    case 2: Login.cadastrar(leitor);     break;
 *    case 3: Login.redefinirSenha(leitor);break;
 *    case 4: Login.sair();                break;
 */
public class Login {

  /**
   *  ~ Abre o menu completo de autenticacao ~
   *  ~ Mostra as opcoes disponiveis dependendo se ha sessao ativa ou nao.
   *
   *
   *  O menu abaixo e so p quando for logado em ADMIN
   */
  public static void abrirMenu(  Scanner leitor  ) {
    //  ~ Pega/Verifica se existe algum usuario logado (se a secao atual existe)
    Sessao sessao = Sessao.get();
    boolean rodando = true;
    int escolha = 0;



    do {
      System.out.println("\n\n====== Sistema Nican — Conta ======\n");
      //  ~ Aqui e onde mostra aquela parte se ha alguem logado...
      sessao.imprimirStatusSessao();


      //  ~ Se tiver ninguem logado...
      if (!sessao.estaLogado()) {
        //  ~ Opcoes para usuario NAO logado ~
        System.out.println("[1] ~ Fazer Login.");
        System.out.println("[2] ~ Criar uma conta.");
        System.out.println("[3] ~ Redefinir senha.");
        System.out.println("\n[4] ~ Voltar ao Menu Principal.\n\n");
      } else {
        //  ~ Opcoes para usuario JA logado ~
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
            case 1: 
              MenuLogin.fazerLogin(leitor); 
              break;
            case 2: 
              MenuLogin.cadastrarUsuario(leitor); 
              break;
            case 3: 
              MenuLogin.redefinirSenha(leitor);  
              break;
            default:
              rodando = false;
              System.out.println("\nVoltando...\n\n\n\n");
              break;
          }
        } else {
          switch (  escolha  ) {
            case 1: 
              MenuLogin.redefinirSenha(leitor); 
              break;
            case 2:
              if (  sessao.usuarioEhAdmin()  ) MenuLogin.painelAdmin(leitor);
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




  //  ~ Atalhos estaticos para o App.java chamar diretamente ~
  //  ~ (compativel com o estilo que ja estava no App.java)   ~

  public static void Cadastro(Scanner leitor) { MenuLogin.cadastrarUsuario(leitor);  }
  public static void fazerLogin(Scanner leitor) { MenuLogin.fazerLogin(leitor);  }
  public static void redefinirSenha(Scanner leitor) {  MenuLogin.redefinirSenha(leitor);  }
  public static void Sair() {  MenuLogin.fazerLogout();  }
}
