package com.faculdade.projeto.login.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.login.classes.*;

/*
 *  ~ Menus de interação do sistema de Login ~
 *
 *  CORREÇÕES:
 *    - cadastrar(): agora trata corretamente o caso de admin nulo (primeiro cadastro)
 *    - cadastrarAdmin(): cria um objeto Admin no banco, não apenas Usuario com perfil ADMIN
 *    - painelAdmin(): exibe lista completa com JOIN (usuarios e admins)
 */
public class MenuLogin {



  // Cadastro de usuário comum
  public static void cadastrarUsuario(Scanner leitor) {
    System.out.println("\n\n====== Cadastro de Usuário ======\n");
    cadastrarUsuarioInterno(leitor);
  }



  // Cadastro de administrador — somente outro admin pode cadastrar
  // REGRA DE NEGÓCIO: somente um Admin autenticado pode cadastrar outro Admin
  public static void cadastrarAdmin(Scanner leitor) {
    Sessao sessao = Sessao.get();
    if (!sessao.estaLogado() || !sessao.usuarioEhAdmin()) {
      System.out.println("\n  [ERRO] Apenas administradores podem cadastrar outros admins.\n");
      return;
    }

    try {
      leitor.nextLine();
      System.out.println("\n\n====== Cadastro de Administrador ======\n");

      System.out.print("Nome completo: ");
      String nome = leitor.nextLine();
      if (Validador.temErro(Validador.validarNome(nome))) return;

      System.out.print("Login (email): ");
      String login = leitor.nextLine();
      if (Validador.temErro(Validador.validarEmail(login))) return;

      if (ListaAdmin.buscarPorLogin(login) != null) {
        System.out.println("  [ERRO] Este login já está cadastrado como admin.");
        return;
      }

      System.out.print("Senha (mín. 8 chars): ");
      String senha = leitor.nextLine();
      if (Validador.temErro(Validador.validarSenha(senha))) return;

      System.out.print("Confirme a senha: ");
      if (!leitor.nextLine().equals(senha)) {
        System.out.println("  [ERRO] As senhas não coincidem.");
        return;
      }

      Admin novoAdmin = new Admin(nome, login, senha);
      if (ListaAdmin.salvar(novoAdmin)) {
        System.out.println("\n  [OK] Administrador '" + nome + "' cadastrado com sucesso!");
      } else {
        System.out.println("\n  [ERRO] Falha ao salvar no banco.");
      }
    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }



  // Cadastro interno de usuário comum
  private static void cadastrarUsuarioInterno(Scanner leitor) {
    try {
      leitor.nextLine();
      System.out.print("Nome completo: ");
      String nome = leitor.nextLine();
      if (Validador.temErro(Validador.validarNome(nome))) return;

      System.out.print("Login (email): ");
      String login = leitor.nextLine();
      if (Validador.temErro(Validador.validarEmail(login))) return;

      if (ListaUsuarios.buscarPorLogin(login) != null) {
        System.out.println("  [ERRO] Este login já está cadastrado.");
        return;
      }

      System.out.print("Senha (mín. 8 chars): ");
      String senha = leitor.nextLine();
      if (Validador.temErro(Validador.validarSenha(senha))) return;

      System.out.print("Confirme a senha: ");
      if (!leitor.nextLine().equals(senha)) {
        System.out.println("  [ERRO] As senhas não coincidem.");
        return;
      }

      // Admin responsável: pega o admin logado, se houver. Senão, null (primeiro cadastro)
      Admin adminResponsavel = null;
      if (Sessao.get().estaLogado() && Sessao.get().usuarioEhAdmin()) {
        adminResponsavel = ListaAdmin.buscarPorId(Sessao.get().getUsuarioLogado().getId());
      }

      Usuario novo = new Usuario(nome, login, senha, Perfil.USUARIO, adminResponsavel);
      if (ListaUsuarios.salvar(novo)) {
        System.out.println("\n  [OK] Usuário '" + nome + "' cadastrado com sucesso!");
        if (!Sessao.get().estaLogado()) {
          Sessao.get().iniciar(novo);
          System.out.println("  [OK] Login automático realizado.");
        }
      } else {
        System.out.println("\n  [ERRO] Falha ao salvar no banco.");
      }
    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }



  // Login — verifica usuario comum E admin
  public static void fazerLogin(Scanner leitor) {
    try {
      Sessao sessao = Sessao.get();
      if (sessao.estaLogado()) {
        System.out.println("\n  [AVISO] Já existe sessão ativa (" + sessao.getUsuarioLogado().getNome() + ").");
        return;
      }

      System.out.println("\n\n====== Login ======\n");
      leitor.nextLine();
      System.out.print("Login: ");
      String login = leitor.nextLine().trim();
      System.out.print("Senha: ");
      String senha = leitor.nextLine();

      // Tenta login como Admin primeiro
      Admin admin = ListaAdmin.buscarPorLogin(login);
      if (admin != null && admin.getSenha().equals(senha)) {
        // Cria um Usuario "ponte" para a sessão que representa o admin
        // (A sessão guarda Usuario — o admin logado é representado como usuario com perfil ADMIN)
        Usuario usuarioAdmin = new Usuario(admin.getNome(), admin.getLogin(), admin.getSenha(), Perfil.ADMIN, null);
        usuarioAdmin.setId(admin.getId());
        sessao.iniciar(usuarioAdmin);
        System.out.println("\n  [OK] Bem-vindo, " + admin.getNome() + " [Administrador]\n");
        return;
      }

      // Tenta login como Usuario comum
      Usuario usuario = ListaUsuarios.buscarPorLogin(login);
      if (usuario == null || !usuario.getSenha().equals(senha)) {
        System.out.println("\n  [ERRO] Login ou senha incorretos.\n");
        return;
      }

      // REGRA DE NEGÓCIO: conta desativada não pode fazer login
      if (!usuario.isAtivo()) {
        System.out.println("\n  [ERRO] Conta desativada. Contate o administrador.\n");
        return;
      }

      sessao.iniciar(usuario);
      System.out.println("\n  [OK] Bem-vindo, " + usuario.getNome() + " [" + usuario.getPerfil() + "]\n");

    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }



  // Redefinir senha
  public static void redefinirSenha(Scanner leitor) {
    try {
      leitor.nextLine();
      System.out.println("\n\n====== Redefinir Senha ======\n");
      System.out.print("Login: ");
      String login = leitor.nextLine().trim();

      Usuario usuario = ListaUsuarios.buscarPorLogin(login);
      if (usuario == null) {
        System.out.println("  [ERRO] Usuário não encontrado.");
        return;
      }

      System.out.print("Senha atual: ");
      if (!leitor.nextLine().equals(usuario.getSenha())) {
        System.out.println("  [ERRO] Senha atual incorreta.");
        return;
      }

      System.out.print("Nova senha: ");
      String nova = leitor.nextLine();
      if (Validador.temErro(Validador.validarSenha(nova))) return;

      usuario.setSenha(nova);
      ListaUsuarios.atualizar(usuario);
      System.out.println("\n  [OK] Senha atualizada com sucesso!\n");

    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }



  // Logout
  public static void fazerLogout() {
    Sessao.get().encerrar();
    System.out.println("\n  [OK] Logout realizado.\n");
  }



  // Painel admin — lista usuários e admins cadastrados
  public static void painelAdmin(Scanner leitor) {
    if (!Sessao.get().usuarioEhAdmin()) {
      System.out.println("\n  [ERRO] Acesso restrito.\n");
      return;
    }

    boolean subMenu = true;
    while (subMenu) {
      System.out.println("\n====== Painel do Administrador ======\n");
      System.out.println("[1] ~ Listar todos os Usuários.");
      System.out.println("[2] ~ Cadastrar novo Administrador.");
      System.out.println("[3] ~ Desativar conta de Usuário.");
      System.out.println("\n[0] ~ Voltar.\n");

      try {
        int op = leitor.nextInt();
        switch (op) {
          case 1:
            System.out.println("\n--- Usuários cadastrados ---");
            ListaUsuarios.listarTodos().forEach(u -> u.infosUsuario());
            break;
          case 2:
            cadastrarAdmin(leitor);
            break;
          case 3:
            System.out.print("ID do usuário a desativar: ");
            int id = leitor.nextInt();
            // REGRA DE NEGÓCIO: admin pode desativar contas de usuários
            if (ListaUsuarios.desativar(id)) {
              System.out.println("  [OK] Conta desativada.");
            } else {
              System.out.println("  [ERRO] Usuário não encontrado.");
            }
            break;
          default:
            subMenu = false;
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada inválida.");
        leitor.nextLine();
      }
    }
  }
}
