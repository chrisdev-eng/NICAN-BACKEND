package com.faculdade.projeto.login.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.faculdade.projeto.login.classes.*;

/**
 *  ~ Menus de interacao do sistema de Login ~
 *
 *  Cada metodo cuida de uma acao: cadastro, login, redefinir senha e logout.
 *  Segue o mesmo padrao visual do Menu.java do almoxarife.
 */
public class MenuLogin {


  //  ~ Cadastro's ~
  public static void cadastrarUsuario(Scanner leitor) {
        System.out.println("\n\n====== Cadastro de Usuario ======\n");
        cadastrar(leitor, Perfil.USUARIO);
    }


  public static void cadastrarAdmin(Scanner leitor) {
    Sessao sessao = Sessao.get();
    if (!sessao.estaLogado() || !sessao.usuarioEhAdmin()) {
        System.out.println("\n  [ERRO] Apenas administradores podem cadastrar outros admins.\n");
        return;
      }
    System.out.println("\n\n====== Cadastro de Administrador ======\n");
    cadastrar(leitor, Perfil.ADMIN);
  }



  //  ~ Cadastro "base" ~
  private static void cadastrar(Scanner leitor, Perfil perfil) {
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

            // Cria com admin null inicialmente (pode ser ajustado depois se necessário)
            Usuario novo = new Usuario(nome, login, senha, perfil, null);
            if (ListaUsuarios.salvar(novo)) {
                System.out.println("\n  [OK] " + perfil.getLabel() + " '" + nome + "' cadastrado com sucesso!");
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























  //  ~ Login ~
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

            Usuario usuario = ListaUsuarios.buscarPorLogin(login);
            if (usuario == null || !usuario.getSenha().equals(senha)) {
                System.out.println("\n  [ERRO] Login ou senha incorretos.\n");
                return;
            }
            if (!usuario.isAtivo()) {
                System.out.println("\n  [ERRO] Conta desativada.\n");
                return;
            }

            sessao.iniciar(usuario);
            System.out.println("\n  [OK] Bem-vindo, " + usuario.getNome() + " [" + usuario.getPerfil() + "]\n");
        } catch (Exception e) {
            System.out.println("  [ERRO] " + e.getMessage());
        }
    }

















  //  ~ Mudar d Senha ~
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

















  //  ~ Outros method's ~
  public static void fazerLogout() {
        Sessao.get().encerrar();
        System.out.println("\n  [OK] Logout realizado.\n");
    }

  public static void painelAdmin(Scanner leitor) {
        if (!Sessao.get().usuarioEhAdmin()) {
            System.out.println("\n  [ERRO] Acesso restrito.\n");
            return;
        }
        ListaUsuarios.listarTodos().forEach(u -> u.infosUsuario());
    }

}
