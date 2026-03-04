package com.chrisdev.eng;

//classe para validar se o login está em formato de e-mail


public class ValidarCad {

    //validar se está em formato de e-mail
    public static boolean LoginValido(String login){
        String regexEmail = "^[A-Za-z0-9_.-]+@[A-Za-z.-]+$";
        return login.matches(regexEmail);
    }

    //validar se a senha atende requisitos mínimos de pelo menos 4 caracteres
     public static boolean SenhaValida(String senha){
        return senha.length() > 3;
     }
}
