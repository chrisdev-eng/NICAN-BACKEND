package com.faculdade.projeto;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.Almoxarife;
import com.faculdade.projeto.almoxarife.classes.ListaItems;
import com.faculdade.projeto.login.Login;
import com.faculdade.projeto.login.classes.Sessao;



/**
 *  ~ Sistema Nican — Ponto de entrada principal ~
 *
 *  Mudancas em relacao ao App.java original:
 *    - O cabecalho mostra quem esta logado
 *    - Integration com o Banco de dados em TODAS as operacoes (Requerimento, Almoxarife, Logins)
 *    
 */
public class App {
  public static void main(String[] args) {



    //  ~ Variaveis de sistema
    Scanner leitor = new Scanner(System.in);
    ListaItems almoxarife = new ListaItems();
    //  ~ Aqui a gente cria essa variavel para definir/logar alguem e/ou verificar se ha alguem logado e talz
    Sessao sessao = Sessao.get();

    int escolhaSistema;
    boolean sistemaCanRun = true;





    do {
      System.out.println("\n\n\n\n====== Bem vindo ao Sistema Nican ======\n");
      sessao.imprimirStatusSessao();   //  ~ Mostra quem esta logado (ou nao)
      System.out.println("\nO que voce deseja fazer?\n");

      System.out.println("[1] ~ Fazer Login no Sistema.");
      System.out.println("[2] ~ Criar uma conta pro Sistema.");
      System.out.println("[3] ~ Redefinir senha.");
      System.out.println("[4] ~ Sair da Conta (Logout).\n");

      //  ~ Almoxarife so aparece se houver alguem logado
      if (  sessao.estaLogado()  ) {
        System.out.println("[5] ~ Ver Almoxarife (Lista de Materiais).");

        
        //  ~ Painel Admin aparece so para admins
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
            if (  sessao.estaLogado()  ) {
              Almoxarife.main(args, leitor, almoxarife);
            } 
            else {
              System.out.println("\n  [AVISO] Faca login primeiro para acessar o Almoxarife.\n");
            }
            break;

          case 6:
            //  ~ Redireciona para o menu completo de conta/admin ~
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
