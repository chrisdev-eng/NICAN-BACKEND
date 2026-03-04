package com.faculdade.projeto.almoxarife.classes;

import java.util.ArrayList;
import java.util.List;




public enum Categoria {
  FC("Ferramentas de Corte"),
  FS("Ferramentas de Sapa"),
  BLS("Barracas, Lonas, Sacos de Dormir"),
  ML("Materiais de Limpeza"),
  MR("Materiais de Ramo/Secao/Alcateia"),
  MP("Materiais de Patrulha"),
  MC("Materiais de Cerimonia");



  private String categoria; 
  Categoria(  String categoria  ) {  this.categoria = categoria;  }



  public String getCategoria() {  return this.categoria;  }
  
  public static List<String> getListaCategorias() {
    List<String> listaCategorias = new ArrayList<>();

    //  ~ Aqui e analizado a propia classe (os valores dela) para que possamos retornar 
    //  ~ um array de categorias; 
    for (  Categoria category : Categoria.values()  ) {
      listaCategorias.add(  category.getCategoria()  );
    }
  
    return listaCategorias;
  }
}
