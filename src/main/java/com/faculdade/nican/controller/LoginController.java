package com.faculdade.nican.controller;

import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.entity.Usuario;
import com.faculdade.nican.model.service.LoginService;

/**
 * Controller responsável pelo fluxo de autenticação.
 * É o único ponto de contato entre a View e a camada de autenticação.
 */
public class LoginController {

    public String fazerLogin(String login, String senha) {
        return LoginService.fazerLogin(login, senha);
    }

    public void fazerLogout() {
        LoginService.fazerLogout();
    }

    public String redefinirSenha(String login, String senhaAtual, String novaSenha, String confirma) {
        return LoginService.redefinirSenha(login, senhaAtual, novaSenha, confirma);
    }

    public boolean estaLogado()      { return LoginService.estaLogado(); }
    public boolean ehAdmin()         { return LoginService.ehAdmin(); }
    public String  getNomeLogado()   { return LoginService.getNomeLogado(); }
    public String  getLoginLogado()  { return LoginService.getLoginLogado(); }

    /** Para TelaSolicitarMaterial — sem que a View acesse Sessao diretamente. */
    public Usuario getUsuarioLogado() { return LoginService.getUsuarioLogado(); }

    /** Para TelaValidarRequerimento — sem que a View acesse Sessao diretamente. */
    public Admin getAdminLogado()     { return LoginService.getAdminLogado(); }
}
