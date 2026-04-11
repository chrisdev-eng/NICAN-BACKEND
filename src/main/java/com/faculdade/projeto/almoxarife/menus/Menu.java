package com.faculdade.projeto.almoxarife.menus;

import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.almoxarife.classes.*;
import com.faculdade.projeto.login.classes.*;

/*
 *  ~ Menu principal do Almoxarife ~
 *
 *  CORREÇÕES:
 *    - validarRequerimentos: agora usa ListaAdmin.buscarPorId para obter o Admin correto
 *      e chama ListaRequerimentos.aprovar/recusar com o tipo certo
 *    - Opção de recusar requerimento adicionada (regra de negócio completa)
 */
public class Menu {

  private static boolean menuAlmoxarife = true;



  public static void main(String[] args, ListaItems lista, Scanner leitor) {
    int escolhaMenu = 0;
    menuAlmoxarife = true;

    do {
      System.out.println("\n\n=== MENU ALMOXARIFE ===\n");
      System.out.println("O que voce deseja saber/fazer?\n");
      System.out.println("[1] ~ Ver lista de Materiais da Sede.");
      System.out.println("[2] ~ Adicionar/Remover algum Material.");
      System.out.println("[3] ~ Requerimento de Algum Material.");
      System.out.println("[4] ~ Validar Requerimentos de Materiais.");
      System.out.println("\n[0] ~ Voltar ao Menu Principal.\n\n");

      try {
        escolhaMenu = leitor.nextInt();

        switch (escolhaMenu) {
          case 1:
            listarMateriais(leitor);
            break;
          case 2:
            // REGRA DE NEGÓCIO: somente admins podem gerenciar o estoque
            if (!Sessao.get().usuarioEhAdmin()) {
              System.out.println("\n  [ERRO] Somente Administradores podem gerenciar o estoque.\n");
              break;
            }
            adicionarRemover(leitor);
            break;
          case 3:
            fazerRequerimento(leitor);
            break;
          case 4:
            validarRequerimentos(leitor);
            break;
          default:
            menuAlmoxarife = false;
            System.out.println("\nVoltando...\n\n\n\n");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada inválida! Tentando novamente...");
        leitor.nextLine();
      }
    } while (menuAlmoxarife);
  }



  private static void listarMateriais(Scanner leitor) {
    boolean subMenu = true;
    int escolhaMenu = 0;

    do {
      System.out.println("\n\n====== Lista de Materiais ======\n");
      System.out.println("[1] ~ Listar Todos os Materiais.");
      System.out.println("[2] ~ Listar os Materiais por Categoria.");
      System.out.println("[3] ~ Listar os Materiais por Estado de Conservação.");
      System.out.println("\n[0] ~ Voltar...\n\n");

      try {
        escolhaMenu = leitor.nextInt();

        switch (escolhaMenu) {
          case 1: MenuListas.listarTudo();          break;
          case 2: MenuListas.listarMenuCategoria(); break;
          case 3: MenuListas.listarMenuEstado();    break;
          default:
            subMenu = false;
            System.out.println("\nVoltando...\n\n\n\n");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada inválida! Tentando novamente...");
        leitor.nextLine();
      } catch (Exception e) {
        System.out.println("Erro: " + e.getMessage());
      }
    } while (subMenu);
  }



  private static void adicionarRemover(Scanner leitor) {
    boolean subMenu = true;
    int escolhaMenu = 0;

    do {
      System.out.println("\n\n====== Adicionar / Remover Itens ======\n");
      System.out.println("[1] ~ Adicionar Materiais.");
      System.out.println("[2] ~ Remover Materiais.");
      System.out.println("[3] ~ Mudar Quantidade de algum item.");
      System.out.println("\n[0] ~ Voltar...\n\n");

      try {
        escolhaMenu = leitor.nextInt();

        switch (escolhaMenu) {
          case 1: MenuItens.adicionarItem(leitor);    break;
          case 2: MenuItens.removerItem(leitor);      break;
          case 3: MenuItens.mudarQuantidade(leitor);  break;
          default:
            subMenu = false;
            System.out.println("\nVoltando...\n\n\n\n");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada inválida! Tentando novamente...");
        leitor.nextLine();
      }
    } while (subMenu);
  }



  private static void fazerRequerimento(Scanner leitor) {
    try {
      Sessao sessao = Sessao.get();

      // REGRA DE NEGÓCIO: usuário precisa estar logado para solicitar material
      if (!sessao.estaLogado()) {
        System.out.println("\n  [ERRO] Faça login para solicitar materiais.\n");
        return;
      }

      List<Item> itens = ListaItems.getListaItems();
      if (itens.isEmpty()) {
        System.out.println("\n  [AVISO] Estoque vazio. Nenhum item disponível para requisição.\n");
        return;
      }

      System.out.println("\n====== Solicitar Material ======\n");
      for (Item i : itens) {
        System.out.println("ID " + i.getIdItem() + " | " + i.getNome() + " | Disponível: " + i.getQuantidadeDisponivel());
      }

      System.out.print("\nDigite o ID do Item: ");
      int idEscolhido = leitor.nextInt();
      Item item = ListaItems.buscarPorId(idEscolhido);
      if (item == null) {
        System.out.println("  Item não encontrado.");
        return;
      }

      System.out.print("Quantidade desejada: ");
      int qtd = leitor.nextInt();

      // REGRA DE NEGÓCIO: quantidade solicitada deve ser válida e respeitar o estoque disponível
      if (qtd <= 0 || qtd > item.getQuantidadeDisponivel()) {
        System.out.println("\n  [ERRO] Quantidade inválida ou estoque insuficiente!\n");
        return;
      }

      Requerimento req = new Requerimento(sessao.getUsuarioLogado(), item, qtd);
      if (ListaRequerimentos.salvar(req)) {
        System.out.println("\n  Requerimento enviado com sucesso! Aguarde aprovação do administrador.\n");
      } else {
        System.out.println("\n  Falha ao salvar no banco.\n");
      }
    } catch (Exception e) {
      System.out.println("Erro ao processar requisição: " + e.getMessage());
    }
  }



  private static void validarRequerimentos(Scanner leitor) {
    try {
      Sessao sessao = Sessao.get();

      // REGRA DE NEGÓCIO: somente administradores podem aprovar ou recusar requerimentos
      if (!sessao.estaLogado() || !sessao.usuarioEhAdmin()) {
        System.out.println("\n  [ERRO] Acesso restrito a administradores.\n");
        return;
      }

      // Busca o objeto Admin real do banco (necessário pois Sessao guarda Usuario, não Admin)
      Admin adminLogado = ListaAdmin.buscarPorId(sessao.getUsuarioLogado().getId());
      if (adminLogado == null) {
        System.out.println("\n  [ERRO] Não foi possível identificar o admin no banco.\n");
        return;
      }

      List<Requerimento> pendentes = ListaRequerimentos.buscarPendentes();
      if (pendentes.isEmpty()) {
        System.out.println("\n  Nenhum requerimento pendente.\n");
        return;
      }

      System.out.println("\n====== Requerimentos Pendentes ======");
      for (Requerimento r : pendentes) {
        System.out.println("Req #" + r.getIdRequerimento()
            + " | Usuário: " + (r.getUsuario() != null ? r.getUsuario().getNome() : "N/A")
            + " | Item: " + (r.getItem() != null ? r.getItem().getNome() : "N/A")
            + " | Qtd: " + r.getQuantidadeSolicitada());
      }

      System.out.print("\nDigite o ID do requerimento para avaliar (0 para sair): ");
      int idReq = leitor.nextInt();
      if (idReq == 0) return;

      Requerimento alvo = pendentes.stream()
          .filter(r -> r.getIdRequerimento().equals(idReq))
          .findFirst().orElse(null);

      if (alvo == null) {
        System.out.println("\n  ID não encontrado.\n");
        return;
      }

      System.out.println("\n[1] ~ Aprovar   [2] ~ Recusar\n");
      int decisao = leitor.nextInt();

      if (decisao == 1) {
        if (ListaRequerimentos.aprovar(alvo, adminLogado)) {
          System.out.println("\n  Requerimento aprovado e estoque atualizado!\n");
        } else {
          System.out.println("\n  Falha ao aprovar o requerimento.\n");
        }
      } else if (decisao == 2) {
        if (ListaRequerimentos.recusar(alvo, adminLogado)) {
          System.out.println("\n  Requerimento recusado.\n");
        }
      } else {
        System.out.println("\n  Opção inválida.\n");
      }

    } catch (Exception e) {
      System.out.println("Erro na validação: " + e.getMessage());
    }
  }
}
