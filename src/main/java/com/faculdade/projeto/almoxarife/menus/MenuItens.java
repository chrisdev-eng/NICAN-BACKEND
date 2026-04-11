package com.faculdade.projeto.almoxarife.menus;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.classes.*;
import com.faculdade.projeto.login.classes.Admin;
import com.faculdade.projeto.login.classes.Sessao;
import com.faculdade.projeto.login.classes.Usuario;

/*
 *  ~ Menu de operações com Itens do Almoxarife ~
 *
 *  CORREÇÕES:
 *    - adicionarItem: o construtor de Item agora exige Admin, não Usuario
 *      → busca o Admin pelo id do usuario logado para passar corretamente
 *    - REGRA DE NEGÓCIO: somente Admin pode adicionar/remover itens (já estava, mantido)
 */
public class MenuItens {



  public static void adicionarItem(Scanner leitor) {

    String nomeItem    = " ";
    int quantidadeItem = 0;
    Qualidade qualidade = null;
    Categoria categoria = null;

    try {
      // REGRA DE NEGÓCIO: somente admins podem adicionar itens
      Usuario userLogado = Sessao.get().getUsuarioLogado();
      if (userLogado == null || !userLogado.isAdmin()) {
        System.out.println("\n  [ERRO] Somente administradores podem adicionar itens.\n");
        return;
      }

      System.out.println("\n\n=== Novo Item ===\n");
      System.out.print("Digite o nome do item: ");
      leitor.nextLine();
      nomeItem = leitor.nextLine();

      System.out.print("Digite a quantidade do item: ");
      try {
        quantidadeItem = leitor.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Quantidade inválida! Usando 1 como padrão.");
        quantidadeItem = 1;
        leitor.nextLine();
      }

      // Categoria
      List<String> listaCategorias = Categoria.getListaCategorias();
      System.out.println("\n=== Categorias Disponíveis ===\n");
      for (int i = 0; i < listaCategorias.size(); i++) {
        System.out.println("[" + i + "] " + listaCategorias.get(i));
      }
      System.out.print("\nDigite o número da Categoria: ");
      int escolhaCategoria = leitor.nextInt();
      categoria = (escolhaCategoria >= 0 && escolhaCategoria < Categoria.values().length)
          ? Categoria.values()[escolhaCategoria] : Categoria.values()[0];

      // Qualidade
      List<String> listaQualidades = Qualidade.getListaQualidade();
      System.out.println("\n=== Estados de Conservação Disponíveis ===\n");
      for (int i = 0; i < listaQualidades.size(); i++) {
        System.out.println("[" + i + "] " + listaQualidades.get(i));
      }
      System.out.print("\nDigite o número do Estado: ");
      int escolhaQualidade = leitor.nextInt();
      qualidade = (escolhaQualidade >= 0 && escolhaQualidade < Qualidade.values().length)
          ? Qualidade.values()[escolhaQualidade] : Qualidade.values()[0];

      // CORREÇÃO: Item agora exige Admin no construtor — buscamos o Admin pelo ID do usuario logado
      // O Admin é buscado diretamente pelo EntityManager para garantir o tipo correto
      Admin adminLogado = com.faculdade.projeto.login.classes.ListaAdmin.buscarPorId(userLogado.getId());
      if (adminLogado == null) {
        System.out.println("\n  [ERRO] Admin não encontrado no banco. Faça login novamente.\n");
        return;
      }

      Item novoItem = new Item(nomeItem, quantidadeItem, quantidadeItem, qualidade, categoria, adminLogado);
      ListaItems.adicionarItem(novoItem);
      System.out.println("\n\n  Item cadastrado com sucesso!\n\n");

    } catch (Exception e) {
      System.out.println("\nErro ao adicionar item: " + e.getMessage() + "\n");
      leitor.nextLine();
    }
  }



  public static void removerItem(Scanner leitor) {
    // REGRA DE NEGÓCIO: somente admins podem remover itens
    if (!Sessao.get().usuarioEhAdmin()) {
      System.out.println("\n  [ERRO] Somente administradores podem remover itens.\n");
      return;
    }
    Item escolherItem = ListaItems.getItemLista(leitor);
    if (escolherItem != null) {
      ListaItems.removerItem(escolherItem);
      System.out.println("\n  Item removido com sucesso!\n");
    } else {
      System.out.println("\n  Item não encontrado!\n");
    }
  }



  public static void mudarQuantidade(Scanner leitor) {
    Item escolherQuantItem = ListaItems.getItemLista(leitor);

    if (escolherQuantItem != null) {
      System.out.println("\n\n=== Insira a Quantidade: [Positivo = adicionar | Negativo = remover] ===\n");

      try {
        int quantChange = leitor.nextInt();

        if (quantChange >= 0) {
          escolherQuantItem.aumentarQuant(quantChange);
        } else {
          // REGRA DE NEGÓCIO: não permite quantidade disponível negativa (tratada em diminuirQuant)
          escolherQuantItem.diminuirQuant(Math.abs(quantChange));
        }

        ListaItems.atualizar(escolherQuantItem);
        System.out.println("\n  Quantidade atualizada com sucesso!\n");

      } catch (InputMismatchException e) {
        System.out.println("Entrada inválida! Digite apenas números.");
        leitor.nextLine();
      }
    } else {
      System.out.println("\n  Item não encontrado...\n");
    }
  }
}
