package com.faculdade.nican.service;
import com.faculdade.nican.model.Admin;
import com.faculdade.nican.model.Sessao;
import com.faculdade.nican.model.Usuario;
import com.faculdade.nican.repository.AdminRepository;
import com.faculdade.nican.repository.UsuarioRepository;
import com.faculdade.nican.model.Perfil;

public class LoginService {

    //retorna null = sucesso
    //retorna String = mensagem de erro

    public static String fazerLogin(String login, String senha){

        //1. valida se os campos estão preenchidos
        if (login == null || login.isBlank()) return "Preencha o campo de e-mail.";
        if (!login.contains("@"))             return "Digite um e-mail válido.";
        if (senha == null || senha.isBlank()) return "Preencha o campo de senha";

        //2. verifica se já tem alguém logado
        if (Sessao.get().estaLogado()) return "Já existe uma sessão ativa.";

        //3. tenta logar como admin
        Admin admin = AdminRepository.buscarPorLogin(login.trim());
        if (admin != null && admin.getSenha().equals(senha)) {
            Sessao.get().iniciarComoAdmin(admin);
            return null; // sucesso
        }

        // 4. tenta logar como Usuário
        Usuario usuario = UsuarioRepository.buscarPorLogin(login.trim());
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            return "Login ou senha incorretos.";
        }
        if (!usuario.isAtivo()) {
            return "Conta desativada. Contate o administrador.";
        }

        Sessao.get().iniciar(usuario);
        return null; // sucesso
    }

    public static String cadastrarAdmin(String nome, String login, String senha, String confirmaSenha) {
        if (nome == null || nome.isBlank())   return "Preencha o nome.";
        if (login == null || login.isBlank()) return "Preencha o e-mail.";
        if (!login.contains("@"))             return "Digite um e-mail válido.";
        if (senha == null || senha.isBlank()) return "Preencha a senha.";
        if (!senha.equals(confirmaSenha))     return "As senhas não coincidem.";
        if (senha.length() < 8)               return "Senha deve ter pelo menos 8 caracteres.";

        if (AdminRepository.buscarPorLogin(login.trim()) != null) {
            return "Este e-mail já está cadastrado como admin.";
        }

        Admin novoAdmin = new Admin(nome, login, senha);
        if (!AdminRepository.salvar(novoAdmin)) {
            return "Falha ao salvar. Tente novamente.";
        }
        return null; // sucesso
    }

    public static String cadastrarUsuario(String nome, String login, String senha, String confirmaSenha) {
        if (nome == null || nome.isBlank())   return "Preencha o nome.";
        if (login == null || login.isBlank()) return "Preencha o e-mail.";
        if (!login.contains("@"))             return "Digite um e-mail válido.";
        if (senha == null || senha.isBlank()) return "Preencha a senha.";
        if (!senha.equals(confirmaSenha))     return "As senhas não coincidem.";
        if (senha.length() < 8)               return "Senha deve ter pelo menos 8 caracteres.";

        if (UsuarioRepository.buscarPorLogin(login.trim()) != null) {
            return "Este e-mail já está cadastrado.";
        }

        Admin adminResponsavel = Sessao.get().usuarioEhAdmin()
                ? Sessao.get().getAdminLogado()
                : null;

        Usuario novo = new Usuario(nome, login, senha, Perfil.USUARIO, adminResponsavel);
        if (!UsuarioRepository.salvar(novo)) {
            return "Falha ao salvar. Tente novamente.";
        }
        return null; // sucesso
    }

    public static String redefinirSenha(String login, String senhaAtual, String novaSenha, String confirmaNovaSenha) {
        if (login == null || login.isBlank())           return "Preencha o e-mail.";
        if (senhaAtual == null || senhaAtual.isBlank()) return "Preencha a senha atual.";
        if (!login.contains("@"))                       return "Digite um e-mail válido.";
        if (novaSenha == null || novaSenha.isBlank())   return "Preencha a nova senha.";
        if (novaSenha.length() < 8)                     return "Nova senha deve ter pelo menos 8 caracteres.";
        if (!novaSenha.equals(confirmaNovaSenha))       return "As senhas não coincidem.";

        Usuario usuario = UsuarioRepository.buscarPorLogin(login.trim());
        if (usuario == null)                        return "Usuário não encontrado.";
        if (!usuario.getSenha().equals(senhaAtual)) return "Senha atual incorreta.";

        usuario.setSenha(novaSenha);
        if (!UsuarioRepository.atualizar(usuario)) {
            return "Falha ao atualizar. Tente novamente.";
        }
        return null; // sucesso
    }

    public static void fazerLogout() {
        Sessao.get().encerrar();
    }

    public static boolean estaLogado() { return Sessao.get().estaLogado(); }
    public static boolean ehAdmin()    { return Sessao.get().usuarioEhAdmin(); }
    public static String getNomeLogado() { return Sessao.get().getNomeLogado(); }
 }

