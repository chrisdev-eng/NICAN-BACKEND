package com.faculdade.projeto.almoxarife;

import java.util.Scanner;

import com.faculdade.projeto.almoxarife.menus.*;
import com.faculdade.projeto.almoxarife.classes.*;



/**  ~ Classe principal que vai cuidar do almoxarifado ~
  *
  *
  */
public class Almoxarife {
  public static void main(  String[] args, Scanner leitor, ListaItems almoxarife  ) {




    //  ~ Declaracoes/Inicializacoes de Itens do Almoxarife (Terao um arquivo que inicializara)
    Item facaoGrupo = new Item("Facao", 4, Qualidade.N, Categoria.FC);
    Item facaoPantera = new Item("Facao", 1, "Patrulha Pantera", Qualidade.Q, Categoria.FC);



    //  ~ Arquivo Actions realizara as Acoes do Menu...
    Menu.main(args, almoxarife, leitor);
  }  
}
