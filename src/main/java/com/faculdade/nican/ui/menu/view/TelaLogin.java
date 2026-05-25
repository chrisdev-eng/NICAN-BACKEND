package com.faculdade.nican.ui.menu.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.faculdade.nican.service.LoginService;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        setTitle("Login - NICAN");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Acesso ao sistema"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── campos originais ──────────────────────────────────────────────────
        JTextField campoEmail = NicanTheme.criarCampo();
        Preferences prefsInicio = Preferences.userRoot();
        campoEmail.setText(prefsInicio.get("ultimoLogin", ""));

        JPasswordField campoSenha = NicanTheme.criarCampoSenha();

        // ── botões originais ──────────────────────────────────────────────────
        JButton btnEntrar = NicanTheme.criarBotaoPrimario("Entrar");
        JButton btnVoltar = NicanTheme.criarBotaoSecundario("Voltar");

        // ── navegação por teclado (lógica original) ───────────────────────────
        campoEmail.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) campoSenha.requestFocus();
            }
        });
        campoSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) campoEmail.requestFocus();
            }
        });
        campoEmail.addActionListener(e -> campoSenha.requestFocus());
        campoSenha.addActionListener(e -> btnEntrar.doClick());

        // ── ações originais ───────────────────────────────────────────────────
        btnEntrar.addActionListener(e -> {
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());
            LoginService.fazerLogout();
            String erro = LoginService.fazerLogin(email, senha);
            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                Preferences prefs = Preferences.userRoot();
                prefs.put("ultimoLogin", email);
                if (LoginService.ehAdmin()) { new TelaSistemaAdmin(); } else { new TelaHomeUsuario(); }
                dispose();
            }
        });
        btnVoltar.addActionListener(e -> { new TelaHome(); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(32, 48, 24, 48));

        corpo.add(NicanTheme.criarCabecalhoSecao("Entrar"));
        corpo.add(Box.createVerticalStrut(4));

        corpo.add(NicanTheme.criarLabel("E-mail"));
        corpo.add(Box.createVerticalStrut(4));
        campoEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(campoEmail);
        corpo.add(Box.createVerticalStrut(12));

        corpo.add(NicanTheme.criarLabel("Senha"));
        corpo.add(Box.createVerticalStrut(4));
        campoSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(campoSenha);
        corpo.add(Box.createVerticalStrut(24));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botoes.setBackground(NicanTheme.FUNDO);
        botoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoes.add(btnEntrar);
        botoes.add(btnVoltar);
        corpo.add(botoes);

        return corpo;
    }
}
