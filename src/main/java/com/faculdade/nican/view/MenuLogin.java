package com.faculdade.nican.view;


import com.faculdade.nican.model.*;
import com.faculdade.nican.model.*;
import com.faculdade.nican.model.*;
import com.faculdade.nican.model.*; 

import java.util.InputMismatchException;
import java.util.Scanner;



/*
 *  ~ Menus de interacao do sistema de Login ~
 *
 *  CORRECAO principal: fazerLogin agora chama sessao.iniciarComoAdmin(admin)
 *  em vez de criar um Usuario "fantasma" — o usuario fantasma nao existia no banco
 *  e causava erro de FK ao salvar requerimentos.
 */
public class MenuLogin {



  public static void cadastrarUsuario(Scanner leitor) {
    System.out.println("\n\n====== Cadastro de Usuario ======\n");
    cadastrarUsuarioInterno(leitor);
  }



  //  ~ Somente Admin autenticado pode cadastrar outro Admin
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

      if (AdminRepository.buscarPorLogin(login) != null) {
        System.out.println("  [ERRO] Este login ja esta cadastrado como admin.");
        return;
      }

      System.out.print("Senha (min. 8 chars): ");
      String senha = leitor.nextLine();
      if (Validador.temErro(Validador.validarSenha(senha))) return;

      System.out.print("Confirme a senha: ");
      if (!leitor.nextLine().equals(senha)) {
        System.out.println("  [ERRO] As senhas nao coincidem.");
        return;
      }

      Admin novoAdmin = new Admin(nome, login, senha);
      if (AdminRepository.salvar(novoAdmin)) {
        System.out.println("\n  [OK] Administrador '" + nome + "' cadastrado com sucesso!");
      } else {
        System.out.println("\n  [ERRO] Falha ao salvar no banco.");
      }
    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }



  private static void cadastrarUsuarioInterno(Scanner leitor) {
    try {
      leitor.nextLine();
      System.out.print("Nome completo: ");
      String nome = leitor.nextLine();
      if (Validador.temErro(Validador.validarNome(nome))) return;

      System.out.print("Login (email): ");
      String login = leitor.nextLine();
      if (Validador.temErro(Validador.validarEmail(login))) return;

      if (UsuarioRepository.buscarPorLogin(login) != null) {
        System.out.println("  [ERRO] Este login ja esta cadastrado.");
        return;
      }

      System.out.print("Senha (min. 8 chars): ");
      String senha = leitor.nextLine();
      if (Validador.temErro(Validador.validarSenha(senha))) return;

      System.out.print("Confirme a senha: ");
      if (!leitor.nextLine().equals(senha)) {
        System.out.println("  [ERRO] As senhas nao coincidem.");
        return;
      }

      //  ~ Admin responsavel: pega o admin logado se houver, senao null
      Admin adminResponsavel = null;
      if (Sessao.get().usuarioEhAdmin()) {
        //  ~ usa getAdminLogado() diretamente (sem busca no banco por ID)
        adminResponsavel = Sessao.get().getAdminLogado();
      }

      Usuario novo = new Usuario(nome, login, senha, Perfil.USUARIO, adminResponsavel);
      if (UsuarioRepository.salvar(novo)) {
        System.out.println("\n  [OK] Usuario '" + nome + "' cadastrado com sucesso!");
        if (!Sessao.get().estaLogado()) {
          Sessao.get().iniciar(novo);
          System.out.println("  [OK] Login automatico realizado.");
        }
      } else {
        System.out.println("\n  [ERRO] Falha ao salvar no banco.");
      }
    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }



  public static void fazerLogin(Scanner leitor) {
    try {
      Sessao sessao = Sessao.get();
      if (sessao.estaLogado()) {
        System.out.println("\n  [AVISO] Ja existe sessao ativa (" + sessao.getNomeLogado() + ").");
        return;
      }

      System.out.println("\n\n====== Login ======\n");
      leitor.nextLine();
      System.out.print("Login: ");
      String login = leitor.nextLine().trim();
      System.out.print("Senha: ");
      String senha = leitor.nextLine();



      //  ~ Tenta login como Admin primeiro
      Admin admin = AdminRepository.buscarPorLogin(login);
      if (admin != null && admin.getSenha().equals(senha)) {
        //  ~ usa iniciarComoAdmin — nao cria mais usuario "fantasma" que nao existe no banco
        sessao.iniciarComoAdmin(admin);
        System.out.println("\n  [OK] Bem-vindo, " + admin.getNome() + " [Administrador]\n");
        return;
      }


      //  ~ Tenta login como Usuario comum
      Usuario usuario = UsuarioRepository.buscarPorLogin(login);
      if (usuario == null || !usuario.getSenha().equals(senha)) {
        System.out.println("\n  [ERRO] Login ou senha incorretos.\n");
        return;
      }

      //  ~ conta desativada nao pode fazer login
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



  public static void redefinirSenha(Scanner leitor) {
    try {
      leitor.nextLine();
      System.out.println("\n\n====== Redefinir Senha ======\n");
      System.out.print("Login: ");
      String login = leitor.nextLine().trim();

      Usuario usuario = UsuarioRepository.buscarPorLogin(login);
      if (usuario == null) {
        System.out.println("  [ERRO] Usuario nao encontrado.");
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
      UsuarioRepository.atualizar(usuario);
      System.out.println("\n  [OK] Senha atualizada com sucesso!\n");

    } catch (Exception e) {
      System.out.println("  [ERRO] " + e.getMessage());
    }
  }



  public static void fazerLogout() {
    Sessao.get().encerrar();
    System.out.println("\n  [OK] Logout realizado.\n");
  }



  public static void painelAdmin(Scanner leitor) {
    if (!Sessao.get().usuarioEhAdmin()) {
      System.out.println("\n  [ERRO] Acesso restrito.\n");
      return;
    }

    boolean subMenu = true;
    while (subMenu) {
      System.out.println("\n====== Painel do Administrador ======\n");
      System.out.println("[1] ~ Listar todos os Usuarios.");
      System.out.println("[2] ~ Cadastrar novo Administrador.");
      System.out.println("[3] ~ Desativar conta de Usuario.");
      System.out.println("\n[0] ~ Voltar.\n");

      try {
        int op = leitor.nextInt();
        switch (op) {
          case 1:
            System.out.println("\n--- Usuarios cadastrados ---");
            UsuarioRepository.listarTodos().forEach(u -> u.infosUsuario());
            break;
          case 2:
            cadastrarAdmin(leitor);
            break;
          case 3:
            System.out.print("ID do usuario a desativar: ");
            int id = leitor.nextInt();
            if (UsuarioRepository.desativar(id)) {
              System.out.println("  [OK] Conta desativada.");
            } else {
              System.out.println("  [ERRO] Usuario nao encontrado.");
            }
            break;
          default:
            subMenu = false;
            break;
        }
      } catch (InputMismatchException e) {
        System.out.println("Entrada invalida.");
        leitor.nextLine();
      }
    }
  }
}
