package com.faculdade.nican.view;

import com.faculdade.nican.controller.LoginController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaSistemaAdmin extends JFrame {
    private final LoginController loginController = new LoginController();

    public TelaSistemaAdmin() {
        NicanTheme.configurarJanela(this, "Painel Admin - NICAN", 480, 500);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Administração"), BorderLayout.NORTH);
        raiz.add(NicanTheme.centralizarConteudo(criarCorpo(), 520), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── botões originais ──────────────────────────────────────────────────
        JButton btnAlmoxarifado        = NicanTheme.criarBotaoPrimario("Almoxarifado");
        JButton btnValidarRequerimentos = NicanTheme.criarBotaoPrimario("Validar Requerimentos");
        JButton btnPainelAdmin         = NicanTheme.criarBotaoSecundario("Painel Admin");
        JButton btnLogout              = NicanTheme.criarBotaoPerigo("Logout");

        // ── ações originais ───────────────────────────────────────────────────
        btnAlmoxarifado.addActionListener(e -> { new TelaAlmoxarife(); dispose(); });
        btnValidarRequerimentos.addActionListener(e -> { new TelaValidarRequerimento(); dispose(); });
        btnPainelAdmin.addActionListener(e -> { new TelaPainelAdmin(); dispose(); });
        btnLogout.addActionListener(e -> {
            loginController.fazerLogout();
            NicanDialog.info(this, "Logout realizado com sucesso!");
            new TelaHome();
            dispose();
        });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(28, 48, 24, 48));

        // Saudação com nome do admin
        JPanel saudacao = new JPanel(new BorderLayout());
        saudacao.setBackground(new Color(0xE8EDE8));
        saudacao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, NicanTheme.VERDE_CLARO),
                new EmptyBorder(10, 14, 10, 14)));
        saudacao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JLabel nomeLabel = new JLabel("Bem-vindo, " + loginController.getNomeLogado() + "!");
        nomeLabel.setFont(NicanTheme.fonteSerif(Font.PLAIN, 15));
        nomeLabel.setForeground(NicanTheme.TEXTO_ESCURO);
        JLabel perfilLabel = new JLabel("ADMINISTRADOR DO SISTEMA");
        perfilLabel.setFont(NicanTheme.fonte(Font.PLAIN, 10));
        perfilLabel.setForeground(NicanTheme.TEXTO_MUTED);
        saudacao.add(nomeLabel, BorderLayout.CENTER);
        saudacao.add(perfilLabel, BorderLayout.SOUTH);
        saudacao.setAlignmentX(Component.LEFT_ALIGNMENT);

        corpo.add(saudacao);
        corpo.add(Box.createVerticalStrut(24));
        corpo.add(NicanTheme.criarCabecalhoSecao("Menu Principal"));
        corpo.add(Box.createVerticalStrut(8));

        JPanel grade = new JPanel(new GridLayout(2, 2, 10, 10));
        grade.setBackground(NicanTheme.FUNDO);
        grade.setAlignmentX(Component.LEFT_ALIGNMENT);

        grade.add(criarCard("📦", "Almoxarifado", "Gerenciar itens", btnAlmoxarifado));
        grade.add(criarCard("✅", "Validar Requerimentos", "Aprovar ou recusar", btnValidarRequerimentos));
        grade.add(criarCard("⚙", "Painel Admin", "Usuários e configurações", btnPainelAdmin));
        grade.add(criarCard("🚪", "Logout", "Sair do sistema", btnLogout));

        corpo.add(grade);
        return corpo;
    }

    private JPanel criarCard(String icone, String titulo, String subtitulo, JButton btn) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(new Color(0xEEF2EE));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(NicanTheme.BORDA_CAMPO, 1),
                new EmptyBorder(14, 14, 14, 14)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel ico = new JLabel(icone);
        ico.setFont(NicanTheme.fonte(Font.PLAIN, 22));

        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setOpaque(false);
        JLabel tit = new JLabel(titulo);
        tit.setFont(NicanTheme.fonte(Font.BOLD, 12));
        tit.setForeground(NicanTheme.TEXTO_ESCURO);
        JLabel sub = new JLabel(subtitulo);
        sub.setFont(NicanTheme.fonte(Font.PLAIN, 10));
        sub.setForeground(NicanTheme.TEXTO_MUTED);
        txt.add(tit);
        txt.add(sub);

        card.add(ico, BorderLayout.WEST);
        card.add(txt, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { btn.doClick(); }
            public void mouseEntered(java.awt.event.MouseEvent e) { card.setBackground(new Color(0xDDE5DD)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { card.setBackground(new Color(0xEEF2EE)); }
        });
        return card;
    }
}
