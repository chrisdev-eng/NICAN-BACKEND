package com.faculdade.nican.view;

import com.faculdade.nican.model.*;
import com.faculdade.nican.model.EmprestimoRepository;
import com.faculdade.nican.model.RequerimentoRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *  ~ Menu de Devolução e Histórico ~
 *
 *  ADMIN  : controle de todas as devoluções em aberto + responsável por cada empréstimo.
 *  USUÁRIO: histórico de suas requisições + confirmação de devolução dos itens que pegou.
 */
public class MenuDevolucao {

  // ─────────────────────────────────────────────────────────────────
  //  PONTO DE ENTRADA — roteamento por perfil
  // ─────────────────────────────────────────────────────────────────

  public static void abrir(Scanner leitor) {
    Sessao sessao = Sessao.get();

    if (!sessao.estaLogado()) {
      System.out.println("\n  [ERRO] Faca login para acessar esta area.\n");
      return;
    }

    if (sessao.usuarioEhAdmin()) {
      menuAdmin(leitor);
    } else {
      menuUsuario(leitor);
    }
  }


  // ─────────────────────────────────────────────────────────────────
  //  MENU ADMIN — controle de devoluções e responsável
  // ─────────────────────────────────────────────────────────────────

  private static void menuAdmin(Scanner leitor) {
    boolean rodando = true;

    while (rodando) {
      System.out.println("\n\n====== Controle de Devolucao (Admin) ======\n");
      System.out.println("[1] ~ Ver todos os emprestimos em aberto.");
      System.out.println("[2] ~ Registrar devolucao de um item.");
      System.out.println("[3] ~ Ver emprestimos em atraso.");
      System.out.println("\n[0] ~ Voltar.\n");

      try {
        int op = leitor.nextInt();
        switch (op) {
          case 1: listarEmAberto();              break;
          case 2: registrarDevolucaoAdmin(leitor); break;
          case 3: listarAtrasados();             break;
          default:
            rodando = false;
            System.out.println("\nVoltando...\n");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada invalida!");
        leitor.nextLine();
      }
    }
  }

  private static void listarEmAberto() {
    List<Emprestimo> lista = EmprestimoRepository.buscarEmAberto();
    if (lista.isEmpty()) {
      System.out.println("\n  Nenhum emprestimo em aberto.\n");
      return;
    }

    System.out.println("\n--- Emprestimos em Aberto ---");
    for (Emprestimo e : lista) {
      String atraso = e.estaAtrasado() ? " *** ATRASADO ***" : "";
      System.out.println(
          "ID #" + e.getIdEmprestimo()
          + " | Responsavel: " + e.getUsuario().getNome()
          + " | Item: " + e.getItem().getNome()
          + " | Qtd: " + e.getQtdPega()
          + " | Pegou em: " + e.getDataPegou()
          + " | Dev. Prevista: " + e.getDevPrevista()
          + atraso
      );
    }
    System.out.println();
  }

  private static void listarAtrasados() {
    List<Emprestimo> lista = EmprestimoRepository.buscarEmAberto();
    List<Emprestimo> atrasados = lista.stream()
        .filter(Emprestimo::estaAtrasado)
        .toList();

    if (atrasados.isEmpty()) {
      System.out.println("\n  Nenhum emprestimo em atraso.\n");
      return;
    }

    System.out.println("\n--- Emprestimos em ATRASO ---");
    for (Emprestimo e : atrasados) {
      System.out.println(
          "ID #" + e.getIdEmprestimo()
          + " | Responsavel: " + e.getUsuario().getNome()
          + " | Item: " + e.getItem().getNome()
          + " | Dev. Prevista: " + e.getDevPrevista()
      );
    }
    System.out.println();
  }

  private static void registrarDevolucaoAdmin(Scanner leitor) {
    listarEmAberto();

    List<Emprestimo> lista = EmprestimoRepository.buscarEmAberto();
    if (lista.isEmpty()) return;

    try {
      System.out.print("Digite o ID do emprestimo para registrar devolucao (0 para cancelar): ");
      int id = leitor.nextInt();
      if (id == 0) return;

      Emprestimo alvo = lista.stream()
          .filter(e -> e.getIdEmprestimo().equals(id))
          .findFirst().orElse(null);

      if (alvo == null) {
        System.out.println("  [ERRO] ID nao encontrado.\n");
        return;
      }

      processarDevolucao(leitor, alvo);

    } catch (InputMismatchException e) {
      System.out.println("Entrada invalida!");
      leitor.nextLine();
    }
  }


  // ─────────────────────────────────────────────────────────────────
  //  MENU USUÁRIO — histórico + confirmação de devolução
  // ─────────────────────────────────────────────────────────────────

  private static void menuUsuario(Scanner leitor) {
    Sessao sessao = Sessao.get();
    Usuario usuario = sessao.getUsuarioLogado();
    boolean rodando = true;

    while (rodando) {
      System.out.println("\n\n====== Minhas Requisicoes e Devolucoes ======\n");
      System.out.println("[1] ~ Ver historico completo de requisicoes.");
      System.out.println("[2] ~ Ver itens que ainda nao devolvi.");
      System.out.println("[3] ~ Confirmar devolucao de um item.");
      System.out.println("\n[0] ~ Voltar.\n");

      try {
        int op = leitor.nextInt();
        switch (op) {
          case 1: verHistoricoRequisicoes(usuario.getId());       break;
          case 2: verItensEmAberto(usuario.getId());              break;
          case 3: confirmarDevolucaoUsuario(leitor, usuario.getId()); break;
          default:
            rodando = false;
            System.out.println("\nVoltando...\n");
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada invalida!");
        leitor.nextLine();
      }
    }
  }

  private static void verHistoricoRequisicoes(Integer idUsuario) {
    List<com.faculdade.nican.model.Requerimento> reqs =
        RequerimentoRepository.listarPorUsuario(idUsuario);

    if (reqs.isEmpty()) {
      System.out.println("\n  Voce ainda nao fez nenhuma requisicao.\n");
      return;
    }

    System.out.println("\n--- Historico de Requisicoes ---");
    for (com.faculdade.nican.model.Requerimento r : reqs) {
      System.out.println(
          "Req #" + r.getIdRequerimento()
          + " | Item: " + r.getItem().getNome()
          + " | Qtd: " + r.getQuantidadeSolicitada()
          + " | Status: " + r.getStatus().toUpperCase()
          + " | Solicitado em: " + r.getDataSolicitacao()
      );
    }

    // Mostrar também o histórico de empréstimos vinculados
    List<Emprestimo> emprestimos = EmprestimoRepository.buscarPorUsuario(idUsuario);
    if (!emprestimos.isEmpty()) {
      System.out.println("\n--- Historico de Emprestimos ---");
      for (Emprestimo e : emprestimos) {
        String situacao = e.foiDevolvido()
            ? "Devolvido em " + e.getDataDev()
            : (e.estaAtrasado() ? "EM ATRASO" : "Em aberto");
        System.out.println(
            "Emp #" + e.getIdEmprestimo()
            + " | " + e.getItem().getNome()
            + " | Qtd: " + e.getQtdPega()
            + " | Pegou: " + e.getDataPegou()
            + " | " + situacao
        );
      }
    }
    System.out.println();
  }

  private static void verItensEmAberto(Integer idUsuario) {
    List<Emprestimo> lista = EmprestimoRepository.buscarEmAbertoDoUsuario(idUsuario);
    if (lista.isEmpty()) {
      System.out.println("\n  Voce nao tem itens pendentes de devolucao.\n");
      return;
    }

    System.out.println("\n--- Itens Pendentes de Devolucao ---");
    for (Emprestimo e : lista) {
      String atraso = e.estaAtrasado() ? " *** ATRASADO ***" : "";
      System.out.println(
          "ID #" + e.getIdEmprestimo()
          + " | " + e.getItem().getNome()
          + " | Qtd: " + e.getQtdPega()
          + " | Dev. Prevista: " + e.getDevPrevista()
          + atraso
      );
    }
    System.out.println();
  }

  private static void confirmarDevolucaoUsuario(Scanner leitor, Integer idUsuario) {
    List<Emprestimo> lista = EmprestimoRepository.buscarEmAbertoDoUsuario(idUsuario);
    if (lista.isEmpty()) {
      System.out.println("\n  Voce nao tem itens pendentes de devolucao.\n");
      return;
    }

    verItensEmAberto(idUsuario);

    try {
      System.out.print("Digite o ID do emprestimo que deseja confirmar devolucao (0 para cancelar): ");
      int id = leitor.nextInt();
      if (id == 0) return;

      Emprestimo alvo = lista.stream()
          .filter(e -> e.getIdEmprestimo().equals(id))
          .findFirst().orElse(null);

      if (alvo == null) {
        System.out.println("  [ERRO] ID nao encontrado ou nao pertence a voce.\n");
        return;
      }

      processarDevolucao(leitor, alvo);

    } catch (InputMismatchException e) {
      System.out.println("Entrada invalida!");
      leitor.nextLine();
    }
  }


  // ─────────────────────────────────────────────────────────────────
  //  LÓGICA COMPARTILHADA — coleta estado e registra devolução
  // ─────────────────────────────────────────────────────────────────

  private static void processarDevolucao(Scanner leitor, Emprestimo emp) {
    try {
      leitor.nextLine(); // limpar buffer

      System.out.println("\n  Item: " + emp.getItem().getNome() + " | Qtd: " + emp.getQtdPega());
      System.out.println("\n  Em que estado o item esta sendo devolvido?");
      System.out.println("  [1] Bom para uso");
      System.out.println("  [2] Com danos leves");
      System.out.println("  [3] Danificado / Quebrado");
      System.out.print("\n  Escolha: ");

      int opcaoEstado;
      try {
        opcaoEstado = Integer.parseInt(leitor.nextLine().trim());
      } catch (NumberFormatException ex) {
        opcaoEstado = 1;
      }

      String estado = switch (opcaoEstado) {
        case 2 -> "danos leves";
        case 3 -> "danificado";
        default -> "bom";
      };

      System.out.print("  Observacao (deixe em branco se nao houver): ");
      String obs = leitor.nextLine().trim();

      if (EmprestimoRepository.registrarDevolucao(emp, estado, obs.isEmpty() ? null : obs)) {
        System.out.println("\n  [OK] Devolucao registrada com sucesso! O estoque foi atualizado.\n");
      } else {
        System.out.println("\n  [ERRO] Falha ao registrar devolucao. Tente novamente.\n");
      }
    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }
}
