package com.faculdade.projeto;

import java.util.Scanner;

import com.faculdade.projeto.almoxarife.Almoxarife;
import com.faculdade.projeto.almoxarife.classes.ListaItems;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

      Scanner leitor = new Scanner(  System.in );
      ListaItems almoxarife = new ListaItems();


      Almoxarife.main(  args, leitor, almoxarife  ); 
    
    }
}
