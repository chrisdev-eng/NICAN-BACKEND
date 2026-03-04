package com.faculdade.projeto.almoxarife.classes;

import java.util.ArrayList;
import java.util.List;

public enum Qualidade {
  N("Novo"),
  B("Bom para uso"),
  C("Precisa de Conserto"),
  Q("Quebrado/Ruim para uso"),
  U("Uso Unico");



  private String estado;
  Qualidade(  String estado  ) {  this.estado = estado;  }



  public String getEstado() {  return this.estado;  }




  //  ~ Vamos retornar uma lista dos estados de Conservacao;
  public static List<String> getListaQualidade() {
    List<String> listaQualidades = new ArrayList<>();


    for (  Qualidade qualty : Qualidade.values()  ) {
      listaQualidades.add(  qualty.getEstado()  );
    }


    return listaQualidades;
  } 
}
