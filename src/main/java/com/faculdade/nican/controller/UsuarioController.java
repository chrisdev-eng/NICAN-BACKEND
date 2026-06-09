package com.faculdade.nican.controller;

import com.faculdade.nican.model.entity.Usuario;
import com.faculdade.nican.model.service.UsuarioService;
import java.util.List;

public class UsuarioController {

    public String cadastrarUsuario(String nome, String login, String senha, String confirmaSenha) {
        return UsuarioService.cadastrarUsuario(nome, login, senha, confirmaSenha);
    }

    public String redefinirSenha(String login, String senhaAtual, String novaSenha, String confirmaNovaSenha) {
        return UsuarioService.redefinirSenha(login, senhaAtual, novaSenha, confirmaNovaSenha);
    }

    public List<Usuario> listarTodos() {
        return UsuarioService.listarTodos();
    }

    public Usuario buscarPorLogin(String login) {
        return UsuarioService.buscarPorLogin(login);
    }

    public Usuario buscarPorId(Integer id) {
        return UsuarioService.buscarPorId(id);
    }

    public boolean atualizar(Usuario usuario) {
        return UsuarioService.atualizar(usuario);
    }

    public boolean desativar(Integer id) {
        return UsuarioService.desativar(id);
    }
}
