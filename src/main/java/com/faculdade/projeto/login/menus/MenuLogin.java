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




  // ═══════════════════════════════════════════════════════════════════════════
  //  CADASTRO
  // ═══════════════════════════════════════════════════════════════════════════

  /**
   *  Cadastra um novo USUARIO comum (qualquer pessoa pode usar).
   */
  public static void cadastrarUsuario(Scanner leitor) {
    System.out.println("\n\n====== Cadastro de Usuario ======\n");
    cadastrar(leitor, Perfil.USUARIO);
  }




  /**
   *  Cadastra um ADMIN — so pode ser chamado por um admin logado.
   */
  public static void cadastrarAdmin(Scanner leitor) {
    Sessao sessao = Sessao.get();

    if (!sessao.estaLogado() || !sessao.usuarioEhAdmin()) {
      System.out.println("\n  [ERRO] Apenas administradores podem cadastrar outros admins.\n");
      return;
    }

    System.out.println("\n\n====== Cadastro de Administrador ======\n");
    cadastrar(leitor, Perfil.ADMIN);
  }




  //  ~ Logica comum de cadastro (reutilizada pelos dois metodos acima) ~
  private static void cadastrar(Scanner leitor, Perfil perfil) {
    leitor.nextLine();  //  ~ Limpa buffer do scanner

    //  ~ Nome ~
    System.out.print("Nome completo: ");
    String nome = leitor.nextLine();
    if (Validador.temErro(Validador.validarNome(nome))) return;

    //  ~ Email ~
    System.out.print("E-mail: ");
    String email = leitor.nextLine();
    if (Validador.temErro(Validador.validarEmail(email))) return;

    if (ListaUsuarios.buscarPorEmail(email) != null) {
      System.out.println("  [ERRO] Este e-mail ja esta cadastrado.");
      return;
    }

    //  ~ Senha ~
    System.out.print("Senha (min. 6 chars, 1 maiuscula, 1 numero): ");
    String senha = leitor.nextLine();
    if (Validador.temErro(Validador.validarSenha(senha))) return;

    System.out.print("Confirme a senha: ");
    String confirmacao = leitor.nextLine();
    if (!senha.equals(confirmacao)) {
      System.out.println("  [ERRO] As senhas nao coincidem.");
      return;
    }

    //  ~ Cria e salva ~
    Usuario novo = new Usuario(nome, email, senha, perfil);
    ListaUsuarios.adicionarUsuario(novo);

    System.out.println("\n  [OK] " + perfil.getLabel() + " '" + nome + "' cadastrado com sucesso!");

    //  ~ Login automatico se ninguem estiver logado ~
    if (!Sessao.get().estaLogado()) {
      Sessao.get().iniciar(novo);
      System.out.println("  [OK] Login realizado automaticamente como '" + nome + "'.");
    }

    System.out.println();
  }




  // ═══════════════════════════════════════════════════════════════════════════
  //  LOGIN
  // ═══════════════════════════════════════════════════════════════════════════

  public static void fazerLogin(Scanner leitor) {
    Sessao sessao = Sessao.get();

    if (sessao.estaLogado()) {
      System.out.println("\n  [AVISO] Ja existe uma sessao ativa ("
          + sessao.getUsuarioLogado().getNome() + "). Faca logout primeiro.\n");
      return;
    }

    System.out.println("\n\n====== Login ======\n");
    leitor.nextLine();

    System.out.print("E-mail: ");
    String email = leitor.nextLine().trim();

    System.out.print("Senha: ");
    String senha = leitor.nextLine();

    Usuario usuario = ListaUsuarios.buscarPorEmail(email);

    if (usuario == null || !usuario.getSenha().equals(senha)) {
      System.out.println("\n  [ERRO] E-mail ou senha incorretos.\n");
      return;
    }

    if (!usuario.isAtivo()) {
      System.out.println("\n  [ERRO] Conta desativada. Fale com o administrador.\n");
      return;
    }

    sessao.iniciar(usuario);
    System.out.println("\n  [OK] Bem-vindo(a), " + usuario.getNome()
        + "! [" + usuario.getPerfil() + "]\n");
  }




  // ═══════════════════════════════════════════════════════════════════════════
  //  REDEFINIR SENHA
  // ═══════════════════════════════════════════════════════════════════════════

  public static void redefinirSenha(Scanner leitor) {
    Sessao sessao = Sessao.get();

    System.out.println("\n\n====== Redefinir Senha ======\n");
    leitor.nextLine();

    //  ~ Se logado: pede a senha atual e redefine direto ~
    if (sessao.estaLogado()) {
      redefinirSenhaLogado(leitor, sessao.getUsuarioLogado());
      return;
    }

    //  ~ Se nao logado: pede email para identificar a conta ~
    System.out.println("  (Voce nao esta logado. Informe o e-mail da conta.)\n");
    System.out.print("E-mail: ");
    String email = leitor.nextLine().trim();

    Usuario usuario = ListaUsuarios.buscarPorEmail(email);
    if (usuario == null) {
      System.out.println("\n  [ERRO] E-mail nao encontrado.\n");
      return;
    }

    redefinirSenhaLogado(leitor, usuario);
  }




  //  ~ Logica de troca de senha (com confirmacao da senha atual) ~
  private static void redefinirSenhaLogado(Scanner leitor, Usuario usuario) {
    System.out.print("Senha atual: ");
    String senhaAtual = leitor.nextLine();

    if (!usuario.getSenha().equals(senhaAtual)) {
      System.out.println("\n  [ERRO] Senha atual incorreta.\n");
      return;
    }

    System.out.print("Nova senha: ");
    String novaSenha = leitor.nextLine();
    if (Validador.temErro(Validador.validarSenha(novaSenha))) return;

    if (novaSenha.equals(senhaAtual)) {
      System.out.println("\n  [ERRO] A nova senha deve ser diferente da atual.\n");
      return;
    }

    System.out.print("Confirme a nova senha: ");
    String confirmacao = leitor.nextLine();
    if (!novaSenha.equals(confirmacao)) {
      System.out.println("\n  [ERRO] As senhas nao coincidem.\n");
      return;
    }

    usuario.setSenha(novaSenha);
    System.out.println("\n  [OK] Senha redefinida com sucesso!\n");
  }




  // ═══════════════════════════════════════════════════════════════════════════
  //  LOGOUT
  // ═══════════════════════════════════════════════════════════════════════════

  public static void fazerLogout() {
    Sessao sessao = Sessao.get();

    if (!sessao.estaLogado()) {
      System.out.println("\n  [AVISO] Nenhum usuario logado no momento.\n");
      return;
    }

    String nome = sessao.getUsuarioLogado().getNome();
    sessao.encerrar();
    System.out.println("\n  [OK] Ate logo, " + nome + "! Sessao encerrada.\n");
  }




  // ═══════════════════════════════════════════════════════════════════════════
  //  PAINEL ADMIN — gerenciar usuarios
  // ═══════════════════════════════════════════════════════════════════════════

  public static void painelAdmin(Scanner leitor) {
    Sessao sessao = Sessao.get();

    if (!sessao.estaLogado() || !sessao.usuarioEhAdmin()) {
      System.out.println("\n  [ERRO] Acesso restrito a administradores.\n");
      return;
    }

    boolean subMenu = true;
    int escolha     = 0;

    do {
      System.out.println("\n\n====== Painel do Administrador ======\n");
      System.out.println("[1] ~ Listar todos os usuarios.");
      System.out.println("[2] ~ Cadastrar novo Administrador.");
      System.out.println("[3] ~ Ativar / Desativar usuario.");
      System.out.println("\n[4] ~ Voltar...");
      System.out.println("\n\n");

      try {
        escolha = leitor.nextInt();

        switch (escolha) {
          case 1:
            ListaUsuarios.listarTodos();
            break;
          case 2:
            cadastrarAdmin(leitor);
            break;
          case 3:
            alternarStatusUsuario(leitor);
            break;
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




  //  ~ Ativa ou desativa uma conta pelo ID ~
  private static void alternarStatusUsuario(Scanner leitor) {
    int id = -1;
    boolean entradaValida = false;

    //  ~ Loop ate o usuario digitar um numero de verdade ~
    while (!entradaValida) {
      System.out.print("\n  Informe o ID do usuario (veja na listagem): ");
      try {
        id = leitor.nextInt();
        entradaValida = true;
      } catch (InputMismatchException e) {
        System.out.println("  [ERRO] ID invalido! Digite apenas numeros.");
        leitor.nextLine();  //  ~ Limpa o buffer para nao travar o scanner ~
      }
    }

    Usuario alvo = ListaUsuarios.buscarPorId(id);

    if (alvo == null) {
      System.out.println("  [ERRO] Usuario nao encontrado.\n");
      return;
    }

    //  ~ Nao deixa desativar o proprio admin logado ~
    if (alvo.getId() == Sessao.get().getUsuarioLogado().getId()) {
      System.out.println("  [ERRO] Voce nao pode desativar sua propria conta.\n");
      return;
    }

    alvo.setAtivo(!alvo.isAtivo());
    System.out.println("  [OK] Usuario '" + alvo.getNome() + "' agora esta: "
        + (alvo.isAtivo() ? "ATIVO" : "INATIVO") + "\n");
  }
}
