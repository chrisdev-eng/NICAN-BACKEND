package com.faculdade.nican.ui.menu.view;

import javax.swing.*;
import java.awt.*;
import com.faculdade.nican.service.LoginService;

public class TelaHomeUsuario extends JFrame {

    public TelaHomeUsuario() {
        setTitle("Home Usuário - NICAN");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("NICAN - Painel do Usuário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel menu = new JPanel(new GridLayout(7, 1, 10, 10));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        JButton btnListarMateriais = new JButton("Materiais");
        JButton btnRequerimentos = new JButton("Meus Requerimentos");
        JButton btnListarCategoria = new JButton("Filtrar por Categoria");
        JButton btnListarEstado = new JButton("Filtrar por Estado");
        JButton btnRedefinirSenha = new JButton("Alterar Senha");
        JButton btnLogout = new JButton("Logout");

        menu.add(btnListarMateriais);
        menu.add(btnRequerimentos);
        menu.add(btnListarCategoria);
        menu.add(btnListarEstado);
        menu.add(btnRedefinirSenha);
        menu.add(btnLogout);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(menu, BorderLayout.CENTER);

        btnLogout.addActionListener(e -> {
            LoginService.fazerLogout();
            new TelaLogin();
            dispose();
        });

        btnRedefinirSenha.addActionListener(e -> {
            new TelaRedefinirSenha(true);
            dispose();
        });

        btnListarMateriais.addActionListener(e -> {
            new TelaListarMateriais();
            dispose();
        });

        btnListarCategoria.addActionListener(e -> {
            new TelaListarCategoria();
            dispose();
        });

        btnListarEstado.addActionListener(e -> {
            new TelaListarEstado();
            dispose();
        });

        btnRequerimentos.addActionListener(e -> {
            new TelaRequerimentos(LoginService.getLoginLogado());
            dispose();
        });

        add(painel);
        setVisible(true);
    }
}