package com.faculdade.nican.model.entity;

/**
 *  ~ Gerencia a Sessao ativa do sistema ~
 *
 *  Singleton: existe apenas UMA sessao por vez.
 *
 *  CORRECAO: a sessao agora guarda separadamente o Usuario logado E o Admin logado,
 *  evitando o problema anterior onde o login de admin criava um Usuario "fantasma"
 *  que nao existia no banco — isso causava erro de FK ao tentar salvar requerimentos
 *  ou buscar o admin pelo ID da sessao.
 */
public class Sessao {

  private static Sessao instancia = new Sessao();

  private Usuario usuarioLogado = null;
  //  ~ campo separado para guardar o Admin logado (evita usuario "fantasma" no banco)
  private Admin adminLogado = null;

  private Sessao() {}

  public static Sessao get() { return instancia; }



  //  ~ Inicia sessao com usuario comum
  public void iniciar(Usuario usuario) {
    this.usuarioLogado = usuario;
    this.adminLogado = null;
  }

  //  ~ metodo separado para iniciar sessao como Admin
  public void iniciarComoAdmin(Admin admin) {
    this.adminLogado = admin;
    this.usuarioLogado = null;
  }

  //  ~ Encerra a sessao (logout)
  public void encerrar() {
    this.usuarioLogado = null;
    this.adminLogado = null;
  }



  public boolean estaLogado()      { return usuarioLogado != null || adminLogado != null; }
  public boolean usuarioEhAdmin()  { return adminLogado != null; }

  public Usuario getUsuarioLogado() { return usuarioLogado; }

  //  ~ retorna o Admin diretamente (sem precisar buscar no banco pelo ID)
  public Admin getAdminLogado()     { return adminLogado; }

  //  ~ Retorna o nome de quem estiver logado (usuario ou admin)
  public String getNomeLogado() {
    if (adminLogado != null)   return adminLogado.getNome();
    if (usuarioLogado != null) return usuarioLogado.getNome();
    return "Nenhum";
  }

  //  ~ Retorna o ID de quem estiver logado
  public Integer getIdLogado() {
    if (adminLogado != null)   return adminLogado.getId();
    if (usuarioLogado != null) return usuarioLogado.getId();
    return null;
  }



  public void imprimirStatusSessao() {
    if (adminLogado != null) {
      System.out.println("  Logado como: " + adminLogado.getNome() + " [Administrador]");
    } else if (usuarioLogado != null) {
      System.out.println("  Logado como: " + usuarioLogado.getNome()
          + " [" + usuarioLogado.getPerfil() + "]");
    } else {
      System.out.println("  Nenhum usuario logado.");
    }
  }
}
