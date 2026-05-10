package com.faculdade.nican.ui.menu;


import com.faculdade.nican.repository.*;
import com.faculdade.nican.util.*;
import com.faculdade.nican.config.*;
import com.faculdade.nican.model.*; 


import java.util.List;
import java.util.ArrayList;






public final class MenuListas {




  //  ~ Acessando a classe pois o metodo getItemRepository e estatico (nao e nescessario uma variavel...)
  public static void listarTudo() {
    System.out.println("\n\n====== Lista Completa de Materiais ======\n\n");
    for (  Item itemLista : ItemRepository.getListaItems()   ) {
      itemLista.infosGeralItem();
    }
  }





  public static void listarMenuCategoria() {
    //  ~ Para cada Categoria listada no Enum, a gente vai verificar cada uma... 
    for (  String categoria : Categoria.getListaCategorias()  ) {

      List<Item> listaDaCategoria = new ArrayList<>();
      for (  Item itemDaLista : ItemRepository.getListaItems()  ) {
        if (  itemDaLista.getCategoria().equals(categoria)  ) {
          listaDaCategoria.add(  itemDaLista  );
        }
      }


      //  ~ Se tiver algo dentro da lista da Categoria avaliada, vai ter um print;
      if (  !listaDaCategoria.isEmpty()  ) {
        System.out.println("\n\n====== " + categoria + " ======\n\n");
        for (  Item item : listaDaCategoria  ) {
          item.infosGeralItem();
        }
      }
      else {
        System.out.println("\n\n====== " + categoria + " ======\n\n");
        System.out.println("~ Categoria Vazia ~\n\n");
      }
    } 
  }





  //  ~ Mesmo conceito de antes, mas agora vamos estar vendo o estado de conservacao dos itens...
  public static void listarMenuEstado() {
    for (  String qualty : Qualidade.getListaQualidade()  ) {
      List<Item> listaDaQualidade = new ArrayList<>();
      

      for (  Item itemDaLista : ItemRepository.getListaItems()  ) {
        if (  itemDaLista.getQualidade().equals(  qualty  )  ) {
          listaDaQualidade.add(  itemDaLista  );
        }
      }


      if (  !listaDaQualidade.isEmpty()  ) {
        System.out.println("\n\n====== " + qualty + " ======\n\n");
        for (  Item item : listaDaQualidade  ) {
          item.infosGeralItem();
        }
      }
      else { 
        System.out.println("\n\n====== " + qualty + " ======\n\n");
        System.out.println("~ Nenhum item dessa Qualidade ~\n\n");
      }
    }
  }


}
