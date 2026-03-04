package com.faculdade.projeto.almoxarife.menus;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.classes.*;





public class Menu {
  
  private static boolean menuAlmoxarife = true;




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
            MenuListas.listarMenuCategoria();;
            break;
          case 3:
            MenuListas.listarMenuEstado();
            break;
          default:
            subMenu = false;
            break;
        }
      }
      catch (  InputMismatchException e  ) {
        System.out.println("Entrada invalida! Tentando novamente... ");
        leitor.nextLine();
      }
    } while (  subMenu  );
  }



  public static void main(String[] args, ListaItems lista, Scanner leitor) {    
    int escolhaMenu = 0;
    do {
      System.out.println("\n\n=== MENU ALMOXARIFE ===\n");
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Ver lista de Materiais da Sede.");
      System.out.println("[2] ~ Adicionar/Remover algum Material.");
      System.out.println("[3] ~ Requerimento de Algum Material.");
      System.out.println("[4] ~ Validar Requerimentos de Materiais");
      System.out.println("\n[5] ~ Voltar ao Menu Principal");
      System.out.println("\n\n");
      



      try {
        
        escolhaMenu = leitor.nextInt();
        

        switch (  escolhaMenu  ) {
          case 1:
            listarMateriais(  lista, leitor  );
            break;
          case 2:
            System.out.println("Menu 2");
            break;
          case 3:
            System.out.println("Menu 3");
            break;
          case 4:
            System.out.println("Menu 4");
            break;
          default:  
            menuAlmoxarife = false;
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
}
