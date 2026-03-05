package com.faculdade.projeto.login.classes;

/**
 *  ~ Valida os dados de entrada do usuario ~
 *
 *  Regras de senha: minimo 8 caracteres.
 *  Facil de ajustar as regras aqui sem mexer no resto do codigo.
 */
public class Validador {

  private Validador() {}



  public static String validarNome(String nome) {
    if (nome == null || nome.trim().length() < 2) {
      return "Nome deve ter pelo menos 2 caracteres.";
    }
    return null;  //  ~ null = sem erro
  }




  public static String validarEmail(String email) {
    //  ~ Tentamos colocar uma regex aqui p validar o email... mais n entendemos mto disso daq n
    String regexEmail = "^[A-Za-z0-9_.-]+@[A-Za-z.-]+$";
    if (email == null || !email.matches(regexEmail)) {
      return "E-mail invalido. Formato esperado: nome@dominio.com";
    }
    return null;
  }




  //  ~ Minimo 8 caracteres, sem outras restricoes por enquanto ~
  public static String validarSenha(String senha) {
    if (senha == null || senha.length() < 8) {
      return "Senha deve ter pelo menos 8 caracteres.";
    }
    return null;
  }




  //  ~ Imprime o erro e retorna true se houver falha ~
  public static boolean temErro(String resultado) {
    if (resultado != null) {
      System.out.println("  [ERRO] " + resultado);
      return true;
    }
    return false;
  }
}
