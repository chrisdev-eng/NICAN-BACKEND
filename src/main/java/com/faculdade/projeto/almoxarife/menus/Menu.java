package com.faculdade.projeto.almoxarife.menus;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.classes.*;
import com.faculdade.projeto.login.classes.*;




public class Menu {
  
  //  ~ Controler se e p roda o menu de almoxarife...
  private static boolean menuAlmoxarife = true;




  public static void main(String[] args, ListaItems lista, Scanner leitor) {    
    int escolhaMenu = 0;
    

    do {
      System.out.println("\n\n=== MENU ALMOXARIFE ===\n");
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Ver lista de Materiais da Sede.");
      System.out.println("[2] ~ Adicionar/Remover algum Material.");
      System.out.println("[3] ~ Requerimento de Algum Material.");
      System.out.println("[4] ~ Validar Requerimentos de Materiais");
      System.out.println("\n[3] ~ Voltar ao Menu Principal");
      System.out.println("\n\n");
      


      try {
        
        escolhaMenu = leitor.nextInt();
        

        switch (  escolhaMenu  ) {
          //  ~ Agora nao vamos mais passar a lista de passavamos pelos metodos, pois puxaremos da classe/banco automatico...
          case 1:
            listarMateriais(  leitor  );
            break;
          case 2:
            //  ~ Somentes admins podem realizar crud dos itens...
            if (  !Sessao.get().usuarioEhAdmin()  ) {
              System.out.println("\n SOMENTE ADMINS...\n");
              break;
            }
            adicionarRemover(  leitor  );
            break;
          case 3:
            fazerRequerimento(  leitor  );
            break;
          /*case 4:
            System.out.println("Menu 4");
            break;*/
          default:  
            menuAlmoxarife = false;
            System.out.println("\nVoltando... \n\n\n\n");
            break;
        }
      } 
      //  ~ Caso algo alem de numeros seja digitado, ele cai p aq
      catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Tentando novamente... ");
        leitor.nextLine();
      }
    } while (  menuAlmoxarife  ); 
  }








  //  ~ Opcao 1 do Menu;
  private static void listarMateriais(  Scanner leitor  ) {  
    
    boolean subMenu = true;
    int escolhaMenu = 0;
    

    do {
      System.out.println("\n\n====== Lista de Materiais ======\n"); 
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Listar Todos os Materiais.");
      System.out.println("[2] ~ Listar os Materiais por Categoria.");
      System.out.println("[3] ~ Listar os Materiais por Estado.");
      System.out.println("\n[4] ~ Voltar... ");
      System.out.println("\n\n");


      try {

        escolhaMenu = leitor.nextInt();
      

        switch (  escolhaMenu  ) {
          case 1:
            MenuListas.listarTudo(); 
            break;
          case 2:
            MenuListas.listarMenuCategoria();
            break;
          case 3:
            MenuListas.listarMenuEstado();
            break;
          default:
            subMenu = false;
            System.out.println("\nVoltando... \n\n\n\n");
            break;
        }
      }
      catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Tentando novamente... ");
        leitor.nextLine();
      } 
      catch (  Exception exception  ) {
        System.out.println("Deu erro aq:" + exception.getMessage() + " :/");
      }
    } while (  subMenu  );
  }







  //  ~ Opcao 2 do menu Principal...
  private static void adicionarRemover(  Scanner leitor  ) {
    
    boolean subMenu = true;
    int escolhaMenu = 0;
  


    do {
      System.out.println("\n\n====== Adicionar / Remover Itens ======\n"); 
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Adicionar Materiais.");
      System.out.println("[2] ~ Remover Materiais.");
      System.out.println("[3] ~ Mudar Quantidade de algum item.");
      System.out.println("\n[4] ~ Voltar... ");
      System.out.println("\n\n");


      try {
        escolhaMenu = leitor.nextInt();

        switch (  escolhaMenu  ) {


          //  ~ Adicionar Item
          case 1:
            MenuItens.adicionarItem(  leitor  );
            break;


          //  ~ Remover Item
          case 2:
            Item escolherItem = ListaItems.getItemLista(  leitor  );
            if (  escolherItem != null  ) {  
              ListaItems.removerItem(  escolherItem  );
              System.out.println("\nItem removido com sucesso!\n");
            } else {
              System.out.println("\nItem nao encontrado!\n");
            }
            break;


          //  ~ Mudar Quantidade
          case 3:
            Item escolherQuantItem = ListaItems.getItemLista(  leitor  );
            if (  escolherQuantItem != null  ) {
              System.out.println("\n\n=== Insira a Quantidade: [Positivo add, Negativo remov.] ===\n\n");
              try {
                int quantChange = leitor.nextInt();
                //  ~ Positivo: adiciona | Negativo: remove
                if (  quantChange >= 0  ) {
                  escolherQuantItem.aumentarQuant(  quantChange  );
                } else {
                  escolherQuantItem.diminuirQuant(  Math.abs(quantChange)  );
                }
                System.out.println("\nQuantidade atualizada com sucesso!\n");
              }
              catch (  InputMismatchException e  ) {
                System.out.println("Entrada invalida! Digite apenas numeros.");
                leitor.nextLine();
              }
            } else {
              System.out.println("\nItem nao encontrado!\n");
            }
            break;


          default:
            subMenu = false;
            System.out.println("\nVoltando... \n\n\n\n");
            break;
        }
      }
      catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Tentando novamente... ");
        leitor.nextLine();
      }
    } while (  subMenu  );

  }
}
