package com.faculdade.projeto.login.classes;

/**
 *  ~ Gerencia a Sessao ativa do sistema ~
 *
 *  Singleton: existe apenas UMA sessao por vez (igual a um login real).
 *  Guarda o usuario logado e expoe metodos de verificacao de permissao.
 */
public class Sessao {

  //  ~ Se auto Intancia a classe
  private static Sessao instancia = new Sessao();
  private Usuario usuarioLogado = null;



  //  ~ Constructor privado, para que ele seja instanciado/inicializado somente dentro aqui da classe 
  private Sessao() {}

  public static Sessao get() {  return instancia;  }





  //  ~ Inicia a sessao com o usuario autenticado ~
  public void iniciar(  Usuario usuario  ) {
    this.usuarioLogado = usuario;
  }

  //  ~ Encerra a sessao (logout) ~
  public void encerrar() {
    this.usuarioLogado = null;
  }





  //  ~ Verificacoes de estado ~
  public boolean estaLogado() {  return usuarioLogado != null;  }
  public boolean usuarioEhAdmin() {  return estaLogado() && usuarioLogado.isAdmin(); }
  public Usuario getUsuarioLogado() {  return usuarioLogado;  }




  /**
   *  ~ Exibe no console quem esta logado atualmente ~
   *  ~ Util para mostrar no cabecalho dos menus.
   */
  public void imprimirStatusSessao() {
    if (  estaLogado()  ) {
      System.out.println("  Logado como: " + usuarioLogado.getNome()
          + " [" + usuarioLogado.getPerfil() + "]");
    } else {
      System.out.println("  Nenhum usuario logado.");
    }
  }
}
