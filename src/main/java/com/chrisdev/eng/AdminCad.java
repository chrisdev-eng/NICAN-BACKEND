package com.chrisdev.eng;

public class AdminCad {
    private String nome;
    private String login;
    private String senha;

    //construtor
    public AdminCad(String nome, String login, String senha){
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    //getters para ler os valores e setters para alterar caso seja necessario
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
