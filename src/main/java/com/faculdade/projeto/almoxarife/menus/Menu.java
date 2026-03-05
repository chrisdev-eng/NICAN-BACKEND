package com.faculdade.projeto.almoxarife.menus;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.classes.*;




public class Menu {
  
  private static boolean menuAlmoxarife = true;




  public static void main(String[] args, ListaItems lista, Scanner leitor) {    
    int escolhaMenu = 0;
    do {
      System.out.println("\n\n=== MENU ALMOXARIFE ===\n");
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Ver lista de Materiais da Sede.");
      System.out.println("[2] ~ Adicionar/Remover algum Material.");
      //System.out.println("[3] ~ Requerimento de Algum Material.");
      //System.out.println("[4] ~ Validar Requerimentos de Materiais");
      System.out.println("\n[3] ~ Voltar ao Menu Principal");
      System.out.println("\n\n");
      


      try {
        
        escolhaMenu = leitor.nextInt();
        

        switch (  escolhaMenu  ) {
          case 1:
            listarMateriais(  lista, leitor  );
            break;
          case 2:
            adicionarRemover(  lista, leitor  );
            break;
          /*case 3:
            System.out.println("Menu 3");
            break;
          case 4:
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



  //  ~ Opcao 1;
  private static void listarMateriais(  ListaItems lista, Scanner leitor  ) {  
    
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
    } while (  subMenu  );
  }



  //  ~ Opcao 2
  private static void adicionarRemover(  ListaItems lista, Scanner leitor  ) {
    
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
            adicionarItem(  leitor  );
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



  //  ~ Logica de Adicionar Item separada para ficar mais limpo
  private static void adicionarItem(  Scanner leitor  ) {

    String nomeItem       = "";
    int quantidadeItem    = 0;
    String ramoSecaoItem  = "";
    Qualidade qualidade   = null;
    Categoria categoria   = null;

    try {
      System.out.println("\n\n=== Novo Item ===\n");

      //  ~ Nome
      System.out.print("Digite o nome do item: ");
      leitor.nextLine();  //  ~ Limpa o buffer antes de ler String
      nomeItem = leitor.nextLine();

      //  ~ Quantidade
      System.out.print("Digite a quantidade do item: ");
      try {
        quantidadeItem = leitor.nextInt();
      } catch (  InputMismatchException e  ) {
        System.out.println("Quantidade invalida! Usando 1 como padrao.");
        quantidadeItem = 1;
        leitor.nextLine();
      }

      //  ~ Ramo/Secao (opcional)
      System.out.print("\nEsse item pertence a algum Ramo/Secao especifico? [s/n]: ");
      leitor.nextLine();  //  ~ Limpa buffer apos nextInt
      String temRamo = leitor.nextLine().trim().toLowerCase();
      if (  temRamo.equals("s")  ) {
        System.out.print("Digite o nome do Ramo/Secao: ");
        ramoSecaoItem = leitor.nextLine();
      }

      //  ~ Categoria
      List<String> listaCategorias = Categoria.getListaCategorias();
      System.out.println("\n=== Categorias Disponiveis ===\n");
      for (  int i = 0; i < listaCategorias.size(); i++  ) {
        System.out.println("[ " + i + " ] " + listaCategorias.get(i));
      }
      System.out.print("\nDigite o numero da Categoria: ");

      int escolhaCategoria = -1;
      try {
        escolhaCategoria = leitor.nextInt();
        if (  escolhaCategoria >= 0 && escolhaCategoria < Categoria.values().length  ) {
          categoria = Categoria.values()[escolhaCategoria];
        } else {
          System.out.println("Opcao invalida! Usando categoria padrao: " + Categoria.values()[0].getCategoria());
          categoria = Categoria.values()[0];
        }
      } catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Usando categoria padrao: " + Categoria.values()[0].getCategoria());
        categoria = Categoria.values()[0];
        leitor.nextLine();
      }

      //  ~ Qualidade
      List<String> listaQualidades = Qualidade.getListaQualidade();
      System.out.println("\n=== Estados de Conservacao Disponiveis ===\n");
      for (  int i = 0; i < listaQualidades.size(); i++  ) {
        System.out.println("[ " + i + " ] " + listaQualidades.get(i));
      }
      System.out.print("\nDigite o numero do Estado de Conservacao: ");

      int escolhaQualidade = -1;
      try {
        escolhaQualidade = leitor.nextInt();
        if (  escolhaQualidade >= 0 && escolhaQualidade < Qualidade.values().length  ) {
          qualidade = Qualidade.values()[escolhaQualidade];
        } else {
          System.out.println("Opcao invalida! Usando qualidade padrao: " + Qualidade.values()[0].getEstado());
          qualidade = Qualidade.values()[0];
        }
      } catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Usando qualidade padrao: " + Qualidade.values()[0].getEstado());
        qualidade = Qualidade.values()[0];
        leitor.nextLine();
      }

      //  ~ Criando o Item (com ou sem Ramo/Secao)
      if (  ramoSecaoItem.isEmpty()  ) {
        new Item(  nomeItem, quantidadeItem, qualidade, categoria  );
      } else {
        new Item(  nomeItem, quantidadeItem, ramoSecaoItem, qualidade, categoria  );
      }

      System.out.println("\nItem '" + nomeItem + "' adicionado com sucesso!\n");

    } catch (  Exception e  ) {
      System.out.println("\nErro inesperado ao adicionar item: " + e.getMessage() + "\n");
      leitor.nextLine();
    }
  }
}
