package com.faculdade.projeto.login.classes;

/**
 *  ~ Define o nivel de acesso do usuario no sistema ~
 *
 *    USUARIO  → acesso basico (ver almoxarife, fazer requerimentos)
 *    ADMIN    → acesso total  (gerenciar usuarios, validar requerimentos, etc.)
 */
public enum Perfil {
  //  ~ No futuro iremos mudar para 3 tipos de perfil, Jovem, Chefe e Diretoria, mas por enquanto estaremos 
  //  ~ Fazedno uma verificacao basica.
  USUARIO("Usuario"),
  ADMIN("Administrador");


  private String tipoPerfil;
  Perfil(  String tipoPerfilDefinido  ) { this.tipoPerfil = tipoPerfilDefinido; }


  public String getLabel() {  return this.tipoPerfil;  }



  //  ~ Adicionar um negocio de lista dos tipos de usuarios
}
