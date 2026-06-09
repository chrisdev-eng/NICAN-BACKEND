package com.faculdade.nican.model.service;

import com.faculdade.nican.model.entity.*; import com.faculdade.nican.model.service.*; import com.faculdade.nican.model.repository.*;
import com.faculdade.nican.model.repository.*;
import java.util.List;

/**
 * Service responsavel pelas regras e operacoes de Usuario.
 */
public class UsuarioService {

    public static String cadastrarUsuario(String nome, String login, String senha, String confirmaSenha) {
        return LoginService.cadastrarUsuario(nome, login, senha, confirmaSenha);
    }

    public static String redefinirSenha(String login, String senhaAtual, String novaSenha, String confirmaNovaSenha) {
        return LoginService.redefinirSenha(login, senhaAtual, novaSenha, confirmaNovaSenha);
    }

    public static List<Usuario> listarTodos() {
        return UsuarioRepository.listarTodos();
    }

    public static Usuario buscarPorLogin(String login) {
        return UsuarioRepository.buscarPorLogin(login);
    }

    public static Usuario buscarPorId(Integer id) {
        return UsuarioRepository.buscarPorId(id);
    }

    public static boolean atualizar(Usuario usuario) {
        return UsuarioRepository.atualizar(usuario);
    }

    public static boolean desativar(Integer id) {
        return UsuarioRepository.desativar(id);
    }
}
