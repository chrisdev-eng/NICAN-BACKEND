package com.chrisdev.eng;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Admin> admins = new ArrayList<>();

        System.out.println(" ~~~~~~~~ Cadastro de Admin ~~~~~~~~ ");

        System.out.println("Digite seu nome: ");
        String nome = scanner.nextLine();

        String login;
        do {
            System.out.println("Digite seu e-mail para login: ");
            login = scanner.nextLine();
            if (!ValidarCad.LoginValido(login)) {
                System.out.println("Formato de e-mail invalido! Tente novamente.");
            }
        } while (!ValidarCad.LoginValido(login));

        String senha;
        do {
            System.out.println("Digite sua senha: ");
            senha = scanner.nextLine();
            if (!ValidarCad.SenhaValida(senha)) {
                System.out.println("Senha muito curta! Tente novamente.");
            }
        } while (!ValidarCad.SenhaValida(senha));

        Admin admin1 = new Admin(login, senha);
        admin1.setNome(nome);
        admins.add(admin1);

        System.out.println("Conta cadastrada como admin com sucesso!");

        for (PessoaSistema p: admins){
            System.out.println(p.acessarSistema());
            System.out.println(p.visualizarRelatorio());
        }

        //Para login
        boolean autenticado = false; // começa false e enquanto não tiver autenticado(true) retorna o else

        while (!autenticado) { //quando ficar true
            System.out.println("~~~~~~~~ Login - Admin ~~~~~~~~");
            System.out.println("Login: ");
            String loginInput = scanner.nextLine();
            System.out.println("Senha: ");
            String senhaInput = scanner.nextLine();

            if (admin1.autenticar(loginInput, senhaInput)) {

                autenticado = true;

                System.out.println("Login realizado! Acessando menu do almoxarifado...");

                //CHAMAR MENU DO ALMOXARIFADO

                System.out.println(" ~ La menu"); // tradicione ne galera
            } else {
                System.out.println("Login ou senha inválidos! Tente novamente. ");
            }
        }

    }
}