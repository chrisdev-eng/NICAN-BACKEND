package com.faculdade.nican.controller;

import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.entity.Usuario;
import com.faculdade.nican.model.service.AdminService;
import java.util.List;

/**
 * Controller responsavel pelas acoes administrativas.
 */
public class AdminController {
    private final AdminService adminService = new AdminService();

    public String cadastrarAdmin(String nome, String login, String senha, String confirmaSenha) {
        return adminService.cadastrarAdmin(nome, login, senha, confirmaSenha);
    }

    public List<Admin> listarTodos() {
        return adminService.listarTodos();
    }

    public Admin buscarPorId(Integer id) {
        return adminService.buscarPorId(id);
    }

    public Admin buscarPorLogin(String login) {
        return adminService.buscarPorLogin(login);
    }

    public boolean atualizar(Admin admin) {
        return adminService.atualizar(admin);
    }

    public List<Usuario> listarUsuarios() {
        return adminService.listarUsuarios();
    }

    public boolean desativarUsuario(Integer id) {
        return adminService.desativarUsuario(id);
    }
}
