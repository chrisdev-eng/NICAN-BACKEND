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
            if(!ValidarCad.LoginValido(login)){
                System.out.println("Formato de e-mail invalido! Tente novamente.");
            }
        } while(!ValidarCad.LoginValido(login));

        String senha;
        do {
            System.out.println("Digite sua senha: ");
            senha = scanner.nextLine();
            if(!ValidarCad.SenhaValida(senha)){
                System.out.println("Senha muito curta! Tente novamente.");
            }
        } while(!ValidarCad.SenhaValida(senha));

        Admin admin1 = new Admin(login, senha);
        admin1.setNome(nome);
        admins.add(admin1);

        System.out.println("Conta cadastrada como admin com sucesso!");
    }

}