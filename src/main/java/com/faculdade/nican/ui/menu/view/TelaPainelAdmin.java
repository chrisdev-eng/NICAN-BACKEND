package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.service.LoginService;
import javax.swing.*;
import java.awt.*;

/**
 * Painel de administração do sistema.
 *
 * CORREÇÕES:
 *   - Botão "Redefinir Senha" com veioDoPainelAdmin=true (item 11)
 *   - Botão "Logout" adicionado (item 14 do checklist)
 */
public class TelaPainelAdmin extends JFrame {

    public TelaPainelAdmin() {
        setTitle("Painel do Administrador - NICAN");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Painel do Administrador");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnListarUsuarios   = new JButton("Listar todos os Usuários");
        JButton btnRedefinirSenha   = new JButton("Redefinir Senha");
        JButton btnCadastrarAdmin   = new JButton("Cadastrar novo Administrador");
        JButton btnDesativarUsuario = new JButton("Desativar conta de Usuário");
        JButton btnVoltar           = new JButton("Voltar");
        JButton btnLogout           = new JButton("Logout");  // ADIÇÃO — item 14

        btnListarUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRedefinirSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrarAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDesativarUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnListarUsuarios.addActionListener(e -> {
            new TelaListarUsuarios();
            dispose();
        });

        btnRedefinirSenha.addActionListener(e -> {
            new TelaRedefinirSenha(false, true);
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

        // ADIÇÃO: logout direto do painel admin (item 14)
        btnLogout.addActionListener(e -> {
            LoginService.fazerLogout();
            JOptionPane.showMessageDialog(this, "Logout realizado com sucesso!");
            new TelaHome();
            dispose();
        });

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnListarUsuarios);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnRedefinirSenha);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnCadastrarAdmin);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnDesativarUsuario);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnVoltar);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnLogout);

        add(painel);
        setVisible(true);
    }
}