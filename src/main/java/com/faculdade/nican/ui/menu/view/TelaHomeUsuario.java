package com.faculdade.nican.ui.menu.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.faculdade.nican.service.LoginService;

public class TelaHomeUsuario extends JFrame {

    public TelaHomeUsuario() {
        setTitle("Home Usuário - NICAN");
        setSize(520, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Painel do usuário"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── botões originais ──────────────────────────────────────────────────
        JButton btnListarMateriais = NicanTheme.criarBotaoPrimario("Materiais");
        JButton btnRequerimentos   = NicanTheme.criarBotaoPrimario("Meus Requerimentos");
        JButton btnListarCategoria = NicanTheme.criarBotaoSecundario("Filtrar por Categoria");
        JButton btnListarEstado    = NicanTheme.criarBotaoSecundario("Filtrar por Estado");
        JButton btnRedefinirSenha  = NicanTheme.criarBotaoSecundario("Alterar Senha");
        JButton btnLogout          = NicanTheme.criarBotaoPerigo("Logout");

        // ── ações originais ───────────────────────────────────────────────────
        btnLogout.addActionListener(e -> { LoginService.fazerLogout(); new TelaHome(); dispose(); });
        btnRedefinirSenha.addActionListener(e -> { new TelaRedefinirSenha(true); dispose(); });
        btnListarMateriais.addActionListener(e -> { new TelaListarMateriais(); dispose(); });
        btnListarCategoria.addActionListener(e -> { new TelaListarCategoria(); dispose(); });
        btnListarEstado.addActionListener(e -> { new TelaListarEstado(); dispose(); });
        btnRequerimentos.addActionListener(e -> { new TelaRequerimentos(LoginService.getLoginLogado()); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(28, 48, 24, 48));

        // Saudação
        JPanel saudacao = new JPanel(new BorderLayout());
        saudacao.setBackground(new Color(0xE8EDE8));
        saudacao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, NicanTheme.VERDE_CLARO),
                new EmptyBorder(10, 14, 10, 14)));
        saudacao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JLabel nomeLabel = new JLabel(LoginService.getNomeLogado());
        nomeLabel.setFont(NicanTheme.fonteSerif(Font.PLAIN, 15));
        nomeLabel.setForeground(NicanTheme.TEXTO_ESCURO);
        JLabel perfilLabel = new JLabel("MEMBRO · ESCOTEIRO");
        perfilLabel.setFont(NicanTheme.fonte(Font.PLAIN, 10));
        perfilLabel.setForeground(NicanTheme.TEXTO_MUTED);
        saudacao.add(nomeLabel, BorderLayout.CENTER);
        saudacao.add(perfilLabel, BorderLayout.SOUTH);
        saudacao.setAlignmentX(Component.LEFT_ALIGNMENT);

        corpo.add(saudacao);
        corpo.add(Box.createVerticalStrut(24));
        corpo.add(NicanTheme.criarCabecalhoSecao("O que deseja fazer?"));
        corpo.add(Box.createVerticalStrut(8));

        // Grade 2x3 de cards
        JPanel grade = new JPanel(new GridLayout(3, 2, 10, 10));
        grade.setBackground(NicanTheme.FUNDO);
        grade.setAlignmentX(Component.LEFT_ALIGNMENT);

        grade.add(criarCard("📦", "Materiais", "Ver itens disponíveis", btnListarMateriais));
        grade.add(criarCard("📋", "Meus Requerimentos", "Acompanhar pedidos", btnRequerimentos));
        grade.add(criarCard("🗂", "Por Categoria", "Filtrar materiais", btnListarCategoria));
        grade.add(criarCard("⭐", "Por Estado", "Filtrar por qualidade", btnListarEstado));
        grade.add(criarCard("🔒", "Alterar Senha", "Segurança da conta", btnRedefinirSenha));
        grade.add(criarCard("🚪", "Logout", "Sair do sistema", btnLogout));

        corpo.add(grade);
        return corpo;
    }

    private JPanel criarCard(String icone, String titulo, String subtitulo, JButton btn) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBackground(new Color(0xEEF2EE));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(NicanTheme.BORDA_CAMPO, 1),
                new EmptyBorder(12, 14, 12, 14)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel ico = new JLabel(icone);
        ico.setFont(NicanTheme.fonte(Font.PLAIN, 20));

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

        // Clique no card dispara o botão original
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { btn.doClick(); }
            public void mouseEntered(java.awt.event.MouseEvent e) { card.setBackground(new Color(0xDDE5DD)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { card.setBackground(new Color(0xEEF2EE)); }
        });

        return card;
    }
}
