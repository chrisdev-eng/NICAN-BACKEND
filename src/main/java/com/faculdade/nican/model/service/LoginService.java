package com.faculdade.nican.model.service;

import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.entity.Perfil;
import com.faculdade.nican.model.entity.Sessao;
import com.faculdade.nican.model.entity.Usuario;
import com.faculdade.nican.model.repository.AdminRepository;
import com.faculdade.nican.model.repository.UsuarioRepository;

public class LoginService {

    public static String fazerLogin(String login, String senha) {
        if (login == null || login.isBlank()) return "Preencha o campo de e-mail.";
        if (!login.contains("@"))             return "Digite um e-mail válido.";
        if (senha == null || senha.isBlank()) return "Preencha o campo de senha.";
        if (Sessao.get().estaLogado())        return "Já existe uma sessão ativa.";

        Admin admin = AdminRepository.buscarPorLogin(login.trim());
        if (admin != null && admin.getSenha().equals(senha)) {
            Sessao.get().iniciarComoAdmin(admin);
            return null;
        }

        Usuario usuario = UsuarioRepository.buscarPorLogin(login.trim());
        if (usuario == null || !usuario.getSenha().equals(senha)) return "Login ou senha incorretos.";
        if (!usuario.isAtivo()) return "Conta desativada. Contate o administrador.";

        Sessao.get().iniciar(usuario);
        return null;
    }

    public static String cadastrarAdmin(String nome, String login, String senha, String confirmaSenha) {
        if (nome == null || nome.isBlank())   return "Preencha o nome.";
        if (login == null || login.isBlank()) return "Preencha o e-mail.";
        if (!login.contains("@"))             return "Digite um e-mail válido.";
        if (senha == null || senha.isBlank()) return "Preencha a senha.";
        if (!senha.equals(confirmaSenha))     return "As senhas não coincidem.";
        if (senha.length() < 8)               return "Senha deve ter pelo menos 8 caracteres.";
        if (AdminRepository.buscarPorLogin(login.trim()) != null) return "Este e-mail já está cadastrado como admin.";

        Admin novoAdmin = new Admin(nome, login, senha);
        return AdminRepository.salvar(novoAdmin) ? null : "Falha ao salvar. Tente novamente.";
    }

    public static String cadastrarUsuario(String nome, String login, String senha, String confirmaSenha) {
        if (nome == null || nome.isBlank())   return "Preencha o nome.";
        if (login == null || login.isBlank()) return "Preencha o e-mail.";
        if (!login.contains("@"))             return "Digite um e-mail válido.";
        if (senha == null || senha.isBlank()) return "Preencha a senha.";
        if (!senha.equals(confirmaSenha))     return "As senhas não coincidem.";
        if (senha.length() < 8)               return "Senha deve ter pelo menos 8 caracteres.";
        if (UsuarioRepository.buscarPorLogin(login.trim()) != null) return "Este e-mail já está cadastrado.";

        Admin adminResponsavel = Sessao.get().usuarioEhAdmin() ? Sessao.get().getAdminLogado() : null;
        Usuario novo = new Usuario(nome, login, senha, Perfil.USUARIO, adminResponsavel);
        return UsuarioRepository.salvar(novo) ? null : "Falha ao salvar. Tente novamente.";
    }

    public static String redefinirSenha(String login, String senhaAtual, String novaSenha, String confirma) {
        if (login == null || login.isBlank())           return "Preencha o e-mail.";
        if (!login.contains("@"))                       return "Digite um e-mail válido.";
        if (senhaAtual == null || senhaAtual.isBlank()) return "Preencha a senha atual.";
        if (novaSenha == null || novaSenha.isBlank())   return "Preencha a nova senha.";
        if (novaSenha.length() < 8)                     return "Nova senha deve ter pelo menos 8 caracteres.";
        if (!novaSenha.equals(confirma))                return "As senhas não coincidem.";

        Usuario usuario = UsuarioRepository.buscarPorLogin(login.trim());
        if (usuario == null)                        return "Usuário não encontrado.";
        if (!usuario.getSenha().equals(senhaAtual)) return "Senha atual incorreta.";

        usuario.setSenha(novaSenha);
        return UsuarioRepository.atualizar(usuario) ? null : "Falha ao atualizar. Tente novamente.";
    }

    public static void    fazerLogout()    { Sessao.get().encerrar(); }
    public static boolean estaLogado()     { return Sessao.get().estaLogado(); }
    public static boolean ehAdmin()        { return Sessao.get().usuarioEhAdmin(); }
    public static String  getNomeLogado()  { return Sessao.get().getNomeLogado(); }

    public static String getLoginLogado() {
        if (Sessao.get().getUsuarioLogado() != null) return Sessao.get().getUsuarioLogado().getLogin();
        if (Sessao.get().getAdminLogado()   != null) return Sessao.get().getAdminLogado().getLogin();
        return null;
    }

    /** Exposto para LoginController — View nunca acessa Sessao diretamente. */
    public static Usuario getUsuarioLogado() { return Sessao.get().getUsuarioLogado(); }

    /** Exposto para LoginController — View nunca acessa Sessao diretamente. */
    public static Admin getAdminLogado()     { return Sessao.get().getAdminLogado(); }
}
