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

    //  ~ Os itens agora sao carregados do banco de dados automaticamente pelo ListaItems
    //  ~ Arquivo Actions realizara as Acoes do Menu...
    Menu.main(args, almoxarife, leitor);
  }  
}
