package com.faculdade.nican.ui.menu.view;
import javax.swing.*;
import java.awt.*;
import com.faculdade.nican.service.LoginService;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        setTitle("Login - NICAN");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblEmail = new JLabel("E-mail:");
        JTextField campoEmail = new JTextField();
        Preferences prefsInicio = Preferences.userRoot();
        campoEmail.setText(prefsInicio.get("ultimoLogin", ""));

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();

        JButton btnEntrar = new JButton("Entrar");
        JButton btnVoltar = new JButton("Voltar");

        campoEmail.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    campoSenha.requestFocus();
                }
            }
        });

        campoSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    campoEmail.requestFocus();
                }
            }
        });

        campoEmail.addActionListener(e -> campoSenha.requestFocus());
        campoSenha.addActionListener(e -> btnEntrar.doClick());

        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnEntrar.addActionListener(e -> {
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());

            // CORREÇÃO: garante que não há sessão ativa antes de tentar login
            LoginService.fazerLogout();

            String erro = LoginService.fazerLogin(email, senha);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                Preferences prefs = Preferences.userRoot();
                prefs.put("ultimoLogin", email);

                if (LoginService.ehAdmin()) {
                    new TelaSistemaAdmin();
                } else {
                    new TelaHomeUsuario();
                }
                dispose();
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaHome();
            dispose();
        });

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblSenha, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoSenha, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnVoltar);

        painel.add(painelCampos);
        painel.add(Box.createVerticalStrut(20));
        painel.add(painelBotoes);

        add(painel);
        setVisible(true);
    }
}