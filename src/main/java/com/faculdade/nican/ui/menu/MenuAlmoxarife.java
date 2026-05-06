package com.faculdade.nican.ui.menu;
import com.faculdade.nican.repository.*;

import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;




/*
 *  ~ Menu principal do Almoxarife ~
 *
 *  CORRECAO: fazerRequerimento agora verifica se o usuario logado eh um admin
 *  (admins nao tem entrada na tabela usuario, portanto nao podem ser o "solicitante"
 *  de um requerimento — a FK idUsuario quebraria). Admins so podem aprovar/recusar.
 *
 *  CORRECAO: validarRequerimentos agora usa sessao.getAdminLogado() diretamente,
 *  sem precisar de busca extra no banco.
 */
public class MenuAlmoxarife {

  private static boolean menuAlmoxarife = true;



  public static void abrir(Scanner leitor) {
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
        System.out.println("Entrada invalida! Tentando novamente...");
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
      System.out.println("[3] ~ Listar os Materiais por Estado de Conservacao.");
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
        System.out.println("Entrada invalida! Tentando novamente...");
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
        System.out.println("Entrada invalida! Tentando novamente...");
        leitor.nextLine();
      }
    } while (subMenu);
  }



  private static void fazerRequerimento(Scanner leitor) {
    try {
      Sessao sessao = Sessao.get();

      if (!sessao.estaLogado()) {
        System.out.println("\n  [ERRO] Faca login para solicitar materiais.\n");
        return;
      }

      // CORRECAO: admins nao existem na tabela usuario, portanto nao podem
      // ser solicitantes de requerimento (a FK quebraria no banco).
      if (sessao.usuarioEhAdmin()) {
        System.out.println("\n  [AVISO] Administradores nao fazem requerimentos, apenas aprovam/recusam.\n");
        return;
      }

      List<Item> itens = ItemRepository.getListaItems();
      if (itens.isEmpty()) {
        System.out.println("\n  [AVISO] Estoque vazio. Nenhum item disponivel para requisicao.\n");
        return;
      }

      System.out.println("\n====== Solicitar Material ======\n");
      for (Item i : itens) {
        System.out.println("ID " + i.getIdItem() + " | " + i.getNome() + " | Disponivel: " + i.getQuantidadeDisponivel());
      }

      System.out.print("\nDigite o ID do Item: ");
      int idEscolhido = leitor.nextInt();
      Item item = ItemRepository.buscarPorId(idEscolhido);
      if (item == null) {
        System.out.println("  Item nao encontrado.");
        return;
      }

      System.out.print("Quantidade desejada: ");
      int qtd = leitor.nextInt();

      if (qtd <= 0 || qtd > item.getQuantidadeDisponivel()) {
        System.out.println("\n  [ERRO] Quantidade invalida ou estoque insuficiente!\n");
        return;
      }

      Requerimento req = new Requerimento(sessao.getUsuarioLogado(), item, qtd);
      if (RequerimentoRepository.salvar(req)) {
        System.out.println("\n  Requerimento enviado com sucesso! Aguarde aprovacao do administrador.\n");
      } else {
        System.out.println("\n  Falha ao salvar no banco.\n");
      }
    } catch (Exception e) {
      System.out.println("Erro ao processar requisicao: " + e.getMessage());
    }
  }



  private static void validarRequerimentos(Scanner leitor) {
    try {
      Sessao sessao = Sessao.get();

      if (!sessao.estaLogado() || !sessao.usuarioEhAdmin()) {
        System.out.println("\n  [ERRO] Acesso restrito a administradores.\n");
        return;
      }

      // CORRECAO: usa getAdminLogado() diretamente — sem busca extra no banco
      Admin adminLogado = sessao.getAdminLogado();
      if (adminLogado == null) {
        System.out.println("\n  [ERRO] Nao foi possivel identificar o admin na sessao.\n");
        return;
      }

      List<Requerimento> pendentes = RequerimentoRepository.buscarPendentes();
      if (pendentes.isEmpty()) {
        System.out.println("\n  Nenhum requerimento pendente.\n");
        return;
      }

      System.out.println("\n====== Requerimentos Pendentes ======");
      for (Requerimento r : pendentes) {
        System.out.println("Req #" + r.getIdRequerimento()
            + " | Usuario: " + (r.getUsuario() != null ? r.getUsuario().getNome() : "N/A")
            + " | Item: "    + (r.getItem()    != null ? r.getItem().getNome()    : "N/A")
            + " | Qtd: "     + r.getQuantidadeSolicitada());
      }

      System.out.print("\nDigite o ID do requerimento para avaliar (0 para sair): ");
      int idReq = leitor.nextInt();
      if (idReq == 0) return;

      Requerimento alvo = pendentes.stream()
          .filter(r -> r.getIdRequerimento().equals(idReq))
          .findFirst().orElse(null);

      if (alvo == null) {
        System.out.println("\n  ID nao encontrado.\n");
        return;
      }

      System.out.println("\n[1] ~ Aprovar   [2] ~ Recusar\n");
      int decisao = leitor.nextInt();

      if (decisao == 1) {
        if (RequerimentoRepository.aprovar(alvo, adminLogado)) {
          System.out.println("\n  Requerimento aprovado e estoque atualizado!\n");
        } else {
          System.out.println("\n  Falha ao aprovar o requerimento.\n");
        }
      } else if (decisao == 2) {
        if (RequerimentoRepository.recusar(alvo, adminLogado)) {
          System.out.println("\n  Requerimento recusado.\n");
        }
      } else {
        System.out.println("\n  Opcao invalida.\n");
      }

    } catch (Exception e) {
      System.out.println("Erro na validacao: " + e.getMessage());
    }
  }
}
