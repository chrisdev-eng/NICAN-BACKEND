package com.faculdade.nican.controller;

<<<<<<< Updated upstream
import com.faculdade.nican.model.Admin;
import com.faculdade.nican.model.AdminRepository;
import com.faculdade.nican.model.LoginService;
import com.faculdade.nican.model.Usuario;
import com.faculdade.nican.model.UsuarioService;
=======
import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.entity.Usuario;
import com.faculdade.nican.model.service.AdminService;
>>>>>>> Stashed changes
import java.util.List;

/**
 * Controller responsavel pelas acoes administrativas.
 */
public class AdminController {
<<<<<<< Updated upstream

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
=======
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
>>>>>>> Stashed changes
    }
}
