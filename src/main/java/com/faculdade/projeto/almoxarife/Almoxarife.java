package com.faculdade.projeto.almoxarife;

import java.util.Scanner;

import com.faculdade.projeto.almoxarife.menus.*;
import com.faculdade.projeto.almoxarife.classes.*;

/*
 *  ~ Classe principal do módulo Almoxarife ~
 *
 *  CORREÇÕES:
 *    - Removidas as instâncias de Item com construtores antigos (que usavam Strings soltas e
 *      parâmetros incompatíveis). Esses objetos não eram salvos no banco de qualquer forma.
 *    - O sistema agora carrega os itens diretamente do banco via ListaItems.
 */
public class Almoxarife {

  public static void main(String[] args, Scanner leitor, ListaItems almoxarife) {
    // Itens são gerenciados pelo banco — o Menu carrega diretamente via ListaItems.getListaItems()
    Menu.main(args, almoxarife, leitor);
  }
}
