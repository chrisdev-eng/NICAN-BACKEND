package com.faculdade.nican.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaHome extends JFrame {

    public TelaHome() {
        NicanTheme.configurarJanela(this, "Sistema NICAN", 420, 460);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Início"), BorderLayout.NORTH);
        raiz.add(NicanTheme.centralizarConteudo(criarCorpo(), 420), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── botões originais ──────────────────────────────────────────────────
        JButton btnLogin    = NicanTheme.criarBotaoPrimario("Login");
        JButton btnCadastro = NicanTheme.criarBotaoSecundario("Criar Conta");
        JButton btnRedefinir = NicanTheme.criarBotaoSecundario("Redefinir Senha");

        // ── ações originais ───────────────────────────────────────────────────
        btnLogin.addActionListener(e -> { new TelaLogin(); dispose(); });
        btnCadastro.addActionListener(e -> { new TelaCadastro(); dispose(); });
        btnRedefinir.addActionListener(e -> { new TelaRedefinirSenha(); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(40, 48, 32, 48));

        // Título
        corpo.add(NicanTheme.criarCabecalhoSecao("Bem-vindo ao NICAN"));

        // Subtítulo
        JLabel sub = new JLabel("Sistema de Gestão Escoteira");
        sub.setFont(NicanTheme.fonte(Font.PLAIN, 12));
        sub.setForeground(NicanTheme.TEXTO_MUTED);
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(sub);
        corpo.add(Box.createVerticalStrut(32));

        // Botões
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        corpo.add(btnLogin);
        corpo.add(Box.createVerticalStrut(10));

        btnCadastro.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCadastro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        corpo.add(btnCadastro);
        corpo.add(Box.createVerticalStrut(10));

        btnRedefinir.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRedefinir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        corpo.add(btnRedefinir);

        return corpo;
    }
}
