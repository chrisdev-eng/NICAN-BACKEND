package com.faculdade.nican.model.service;

import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.repository.AdminRepository;
import com.faculdade.nican.model.service.LoginService;
import com.faculdade.nican.model.entity.Usuario;
import com.faculdade.nican.model.service.UsuarioService;
import java.util.List;

public class AdminService {

    public String cadastrarAdmin(String nome, String login, String senha, String confirmaSenha) {
        return LoginService.cadastrarAdmin(nome, login, senha, confirmaSenha);
    }

    public List<Admin> listarTodos() {
        return AdminRepository.listarTodos();
    }

    public Admin buscarPorId(Integer id) {
        return AdminRepository.buscarPorId(id);
    }

    public Admin buscarPorLogin(String login) {
        return AdminRepository.buscarPorLogin(login);
    }

    public boolean atualizar(Admin admin) {
        return AdminRepository.atualizar(admin);
    }

    public List<Usuario> listarUsuarios() {
        return UsuarioService.listarTodos();
    }

    public boolean desativarUsuario(Integer id) {
        return UsuarioService.desativar(id);
    }
}
