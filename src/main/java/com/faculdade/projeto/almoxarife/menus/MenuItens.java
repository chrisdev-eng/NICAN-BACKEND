package com.faculdade.projeto.almoxarife.menus;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.classes.*;
import com.faculdade.projeto.login.classes.Sessao;
import com.faculdade.projeto.login.classes.Usuario;





public class MenuItens {


  //  ~ Opcao de Adcionar itens do Menu de Itens, de Menu.java
  public static void adicionarItem(Scanner leitor) {
    
    //  ~ Variaveis de inicialization
    String nomeItem       = " ";
    int quantidadeItem    = 0;
    Qualidade qualidade   = null;
    Categoria categoria   = null;





    try {
      //  ~ Nome do Item
      System.out.println( "\n\n=== Novo Item ===\n ");
      System.out.print( "Digite o nome do item:  ");
      leitor.nextLine();
      nomeItem = leitor.nextLine();



      System.out.print( "Digite a quantidade do item:  ");
      try {
        quantidadeItem = leitor.nextInt();
      } catch (  InputMismatchException e  ) {
        System.out.println( "Quantidade invalida! Usando 1 como padrao. ");
        quantidadeItem = 1;
        leitor.nextLine();
      }



      //  ~ Categoria do Item
      List<String> listaCategorias = Categoria.getListaCategorias();
      System.out.println( "\n=== Categorias Disponiveis ===\n ");
      for (  int i = 0; i < listaCategorias.size(); i++  ) {
        System.out.println( "[  " + i + " ]  " + listaCategorias.get(i));
      }
      System.out.print( "\nDigite o numero da Categoria:  ");
      int escolhaCategoria = leitor.nextInt();
      categoria = (escolhaCategoria >= 0 && escolhaCategoria < Categoria.values().length) ? Categoria.values()[escolhaCategoria] : Categoria.values()[0];



      //  ~ Qualidade do Item
      List<String> listaQualidades = Qualidade.getListaQualidade();
      System.out.println( "\n=== Estados de Conservacao Disponiveis ===\n ");
      for (  int i = 0; i < listaQualidades.size(); i++  ) {
        System.out.println( "[  " + i + " ]  " + listaQualidades.get(i));
      }
      System.out.print( "\nDigite o numero do Estado:  ");
      int escolhaQualidade = leitor.nextInt();
      qualidade = (escolhaQualidade >= 0 && escolhaQualidade < Qualidade.values().length) 
                  ? Qualidade.values()[escolhaQualidade] : Qualidade.values()[0];



  
      //  ~ O usuario Responsavel pelo item (Nao nescessariamente um admin)
      Usuario userLogado = Sessao.get().getUsuarioLogado();
      if (  userLogado == null || !userLogado.isAdmin()  ) {
        System.out.println("\n Secao invalida... \n");
        return;
      }





      //  ~ Cria e salva no banco
      Item novoItem = new Item(nomeItem, quantidadeItem, quantidadeItem, qualidade, categoria, userLogado);


      if (  ListaItems.adicionar(  novoItem  )  ) {
      }


    } catch (  Exception e  ) {
      System.out.println( "\nErro ao adicionar item:  " + e.getMessage() +  "\n ");
      leitor.nextLine();
    }
  }
}
