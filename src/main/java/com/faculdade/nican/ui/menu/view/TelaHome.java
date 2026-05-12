package com.faculdade.nican.ui.menu.view;
import javax.swing.*;
import java.awt.*;

public class TelaHome extends JFrame {

    public TelaHome(){
        // configuração da janela
        setTitle("Sistema NICAN");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centralizar na tela

        //painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        // titulo
        JLabel titulo = new JLabel("Bem-vindo ao NICAN");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        //botões
        JButton btnLogin = new JButton("Login");
        JButton btnCadastro = new JButton("Criar Conta");
        JButton btnRedefinir = new JButton("Redefinir Senha");

        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastro.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRedefinir.setAlignmentX(Component.CENTER_ALIGNMENT);

        //fazendo os botãozin funcionar
        btnLogin.addActionListener(e -> {
            new TelaLogin();
            dispose(); // fecha a TelaHome
        });

        btnCadastro.addActionListener(e -> {
            new TelaCadastro();
            dispose();
        });

        btnRedefinir.addActionListener(e -> {
            new TelaRedefinirSenha();
            dispose();
        });

        //adicionando tudo no painel
        painel.add(Box.createVerticalStrut(40)); // espaço encima
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30)); // espaço entre titulo e os botões
        painel.add(btnLogin);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnCadastro);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnRedefinir);

        add(painel);
        setVisible(true);
    }

}
