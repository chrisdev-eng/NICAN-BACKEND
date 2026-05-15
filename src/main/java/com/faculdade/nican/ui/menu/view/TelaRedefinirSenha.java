package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.service.LoginService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaRedefinirSenha extends JFrame {

    private boolean veioDaHomeUsuario;
    private boolean veioDoPainelAdmin;

    public TelaRedefinirSenha() {
        this(false, false);
    }

    public TelaRedefinirSenha(boolean veioDaHomeUsuario) {
        this(veioDaHomeUsuario, false);
    }

    public TelaRedefinirSenha(boolean veioDaHomeUsuario, boolean veioDoPainelAdmin) {
        this.veioDaHomeUsuario = veioDaHomeUsuario;
        this.veioDoPainelAdmin = veioDoPainelAdmin;

        setTitle("Redefinir Senha - NICAN");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // campos
        JLabel lblEmail      = new JLabel("E-mail:");
        JTextField campoEmail = new JTextField();

        JLabel lblSenhaAtual       = new JLabel("Senha Atual:");
        JPasswordField campoSenhaAtual = new JPasswordField();

        JLabel lblNovaSenha        = new JLabel("Nova Senha:");
        JPasswordField campoNovaSenha  = new JPasswordField();

        JLabel lblConfirma         = new JLabel("Confirmar Nova Senha:");
        JPasswordField campoConfirma   = new JPasswordField();

        // botões
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnVoltar    = new JButton("Voltar");

        // navegação por teclado
        campoEmail.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    campoSenhaAtual.requestFocus();
                }
            }
        });

        campoSenhaAtual.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    campoEmail.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    campoNovaSenha.requestFocus();
                }
            }
        });

        campoNovaSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    campoSenhaAtual.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    campoConfirma.requestFocus();
                }
            }
        });

        campoConfirma.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    campoNovaSenha.requestFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnConfirmar.doClick();
                }
            }
        });

        // ação confirmar
        btnConfirmar.addActionListener(e -> {
            String email      = campoEmail.getText();
            String senhaAtual = new String(campoSenhaAtual.getPassword());
            String novaSenha  = new String(campoNovaSenha.getPassword());
            String confirma   = new String(campoConfirma.getPassword());

            String erro = LoginService.redefinirSenha(email, senhaAtual, novaSenha, confirma);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Senha redefinida com sucesso!");
                LoginService.fazerLogout();
                new TelaLogin();
                dispose();
            }
        });

        // ação voltar — retorna para a tela correta dependendo da origem
        btnVoltar.addActionListener(e -> {
            if (veioDaHomeUsuario) {
                new TelaHomeUsuario();
            } else if (veioDoPainelAdmin) {
                new TelaPainelAdmin();
            } else {
                new TelaHome();
            }
            dispose();
        });

        // layout dos campos
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
        painelCampos.add(lblSenhaAtual, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoSenhaAtual, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblNovaSenha, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoNovaSenha, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblConfirma, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoConfirma, gbc);

        // layout dos botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.add(btnConfirmar);
        painelBotoes.add(btnVoltar);

        painel.add(painelCampos);
        painel.add(Box.createVerticalStrut(20));
        painel.add(painelBotoes);

        add(painel);
        setVisible(true);
    }
}