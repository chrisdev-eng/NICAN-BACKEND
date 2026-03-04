package com.chrisdev.eng;

public class Admin extends PessoaSistema {
    //precisa conter usuario (login) e uma senha para admin
    //atributos da conta admin
    private String nome;
    private String login;
    private String senha;

    public Admin(String login, String senha) {
        this.login = login;
        this.senha = senha;
        //nome será colocado depois via setter para fins de relatorio
    }

    //Nome e login não é alteravel, uma vez cadastrado irão permanecer
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // verificando se o login está correto
    public boolean autenticar(String login, String senha) {
            boolean loginAceito = this.login.equals(login);
            boolean senhaAceita = this.senha.equals(senha);

        return loginAceito && senhaAceita;
    }

    @Override
    public String acessarSistema(){
        return "Admin: acesso completo ao almoxarifado.";
    }

    @Override
    public String visualizarRelatorio(){
        return "Admin: acesso a relatorios completos.";
    }

}
