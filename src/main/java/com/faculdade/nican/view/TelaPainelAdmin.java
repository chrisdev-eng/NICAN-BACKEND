package com.faculdade.nican.view;

import com.faculdade.nican.controller.LoginController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaPainelAdmin extends JFrame {
    private final LoginController loginController = new LoginController();

    public TelaPainelAdmin() {
        NicanTheme.configurarJanela(this, "Painel do Administrador - NICAN", 480, 520);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Painel Admin"), BorderLayout.NORTH);
        raiz.add(NicanTheme.centralizarConteudo(criarCorpo(), 560), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── botões originais ──────────────────────────────────────────────────
        JButton btnListarUsuarios   = NicanTheme.criarBotaoPrimario("Listar todos os Usuários");
        JButton btnRedefinirSenha   = NicanTheme.criarBotaoSecundario("Redefinir Senha");
        JButton btnCadastrarAdmin   = NicanTheme.criarBotaoPrimario("Cadastrar novo Administrador");
        JButton btnDesativarUsuario = NicanTheme.criarBotaoPerigo("Desativar conta de Usuário");
        JButton btnVoltar           = NicanTheme.criarBotaoSecundario("Voltar");
        JButton btnLogout           = NicanTheme.criarBotaoPerigo("Logout");

        // ── ações originais ───────────────────────────────────────────────────
        btnListarUsuarios.addActionListener(e -> { new TelaListarUsuarios(); dispose(); });
        btnRedefinirSenha.addActionListener(e -> { new TelaRedefinirSenha(false, true); dispose(); });
        btnCadastrarAdmin.addActionListener(e -> { new TelaCadastroAdmin(); dispose(); });
        btnDesativarUsuario.addActionListener(e -> { new TelaDesativarUsuario(); dispose(); });
        btnVoltar.addActionListener(e -> { new TelaSistemaAdmin(); dispose(); });
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

        corpo.add(NicanTheme.criarCabecalhoSecao("Painel do Administrador"));
        corpo.add(Box.createVerticalStrut(8));

        JPanel grade = new JPanel(new GridLayout(3, 2, 10, 10));
        grade.setBackground(NicanTheme.FUNDO);
        grade.setAlignmentX(Component.LEFT_ALIGNMENT);

        grade.add(criarCard("👥", "Listar Usuários", "Ver todos os usuários", btnListarUsuarios));
        grade.add(criarCard("🔑", "Redefinir Senha", "Alterar credenciais", btnRedefinirSenha));
        grade.add(criarCard("👤", "Novo Admin", "Cadastrar administrador", btnCadastrarAdmin));
        grade.add(criarCard("🚫", "Desativar Usuário", "Desativar uma conta", btnDesativarUsuario));
        grade.add(criarCard("◀", "Voltar", "Menu anterior", btnVoltar));
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

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { btn.doClick(); }
            public void mouseEntered(java.awt.event.MouseEvent e) { card.setBackground(new Color(0xDDE5DD)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { card.setBackground(new Color(0xEEF2EE)); }
        });
        return card;
    }
}
