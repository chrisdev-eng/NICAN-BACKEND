package com.faculdade.projeto.almoxarife.classes;

import java.util.ArrayList;
import java.util.List;



public class ListaItems {
  
  private static List<Item> lista;
  private static int listaTamanho;



  public ListaItems() {
    lista = new ArrayList<>();
    listaTamanho = lista.size();
  }



  //  ~ GETTER ~ Retorna um print da lista 
  public static List<Item> getListaItems() {  return lista;  } 
  public static int getListaTamanho()  {  return listaTamanho;  }



  public static void returnListaItems() {
    System.out.println("\n\n\n ~ Lista de Items do Grupo ~\n\n");
    for (  Item item : lista  ) {
      item.infosGeralItem();
    }
  }
  public static void adicionarItem(  Item novoItem  ) {  lista.add(novoItem);  }
  public static void removerItem(  Item removItem  ) {  lista.remove(  removItem.getIdItem()  );  }
}
