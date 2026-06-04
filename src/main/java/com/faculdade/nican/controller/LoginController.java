package com.faculdade.nican.controller;

import com.faculdade.nican.model.LoginService;

/**
 * Controller responsavel pelo fluxo de autenticacao da interface Swing.
 * A View chama esta classe, e esta classe delega as regras ao LoginService.
 */
public class LoginController {

    public String fazerLogin(String login, String senha) {
        return LoginService.fazerLogin(login, senha);
    }

    public void fazerLogout() {
        LoginService.fazerLogout();
    }

    public String redefinirSenha(String login, String senhaAtual, String novaSenha, String confirmaNovaSenha) {
        return LoginService.redefinirSenha(login, senhaAtual, novaSenha, confirmaNovaSenha);
    }

    public boolean estaLogado() {
        return LoginService.estaLogado();
    }

    public boolean ehAdmin() {
        return LoginService.ehAdmin();
    }

    public String getNomeLogado() {
        return LoginService.getNomeLogado();
    }

    public String getLoginLogado() {
        return LoginService.getLoginLogado();
    }
}
