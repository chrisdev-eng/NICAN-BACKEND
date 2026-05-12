package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.service.LoginService;
import javax.swing.*;
import java.awt.*;

public class TelaPainelAdmin extends JFrame {

    public TelaPainelAdmin() {
        setTitle("Painel do Administrador - NICAN");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // título
        JLabel titulo = new JLabel("Painel do Administrador");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        // botões
        JButton btnListarUsuarios = new JButton("Listar todos os Usuários");
        JButton btnCadastrarAdmin = new JButton("Cadastrar novo Administrador");
        JButton btnDesativarUsuario = new JButton("Desativar conta de Usuário");
        JButton btnVoltar = new JButton("Voltar");

        btnListarUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrarAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDesativarUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ações

        btnListarUsuarios.addActionListener(e -> {
            new TelaListarUsuarios();
            dispose();
        });

        btnCadastrarAdmin.addActionListener(e -> {
            new TelaCadastroAdmin();
            dispose();
        });

        btnDesativarUsuario.addActionListener(e -> {
            new TelaDesativarUsuario();
            dispose();
        });

        btnVoltar.addActionListener(e -> {
            new TelaSistemaAdmin();
            dispose();
        });

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnListarUsuarios);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnCadastrarAdmin);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnDesativarUsuario);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnVoltar);

        add(painel);
        setVisible(true);
    }
}