package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.service.LoginService;
import javax.swing.*;
import java.awt.*;

public class TelaCadastroAdmin extends JFrame {

    public TelaCadastroAdmin() {
        setTitle("Cadastrar Administrador - NICAN");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // campos
        JLabel lblNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField();

        JLabel lblEmail = new JLabel("E-mail:");
        JTextField campoEmail = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();

        JLabel lblConfirma = new JLabel("Confirmar Senha:");
        JPasswordField campoConfirma = new JPasswordField();

        // botões
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> {
            String nome = campoNome.getText();
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());
            String confirma = new String(campoConfirma.getPassword());

            String erro = LoginService.cadastrarAdmin(nome, email, senha, confirma);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Administrador cadastrado com sucesso!");
                new TelaPainelAdmin();
                dispose();
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaPainelAdmin();
            dispose();
        });

        // painel dos campos
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblSenha, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoSenha, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblConfirma, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoConfirma, gbc);

        // painel dos botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnVoltar);

        painel.add(painelCampos);
        painel.add(Box.createVerticalStrut(20));
        painel.add(painelBotoes);

        add(painel);
        setVisible(true);
    }
}