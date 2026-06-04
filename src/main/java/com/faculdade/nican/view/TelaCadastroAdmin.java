package com.faculdade.nican.view;

import com.faculdade.nican.controller.AdminController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastroAdmin extends JFrame {
    private final AdminController adminController = new AdminController();

    public TelaCadastroAdmin() {
        NicanTheme.configurarJanela(this, "Cadastrar Administrador - NICAN", 420, 540);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Cadastrar administrador"), BorderLayout.NORTH);
        raiz.add(NicanTheme.centralizarConteudo(criarCorpo(), 420), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── campos originais ──────────────────────────────────────────────────
        JTextField    campoNome     = NicanTheme.criarCampo();
        JTextField    campoEmail    = NicanTheme.criarCampo();
        JPasswordField campoSenha   = NicanTheme.criarCampoSenha();
        JPasswordField campoConfirma = NicanTheme.criarCampoSenha();

        JButton btnCadastrar = NicanTheme.criarBotaoPrimario("Cadastrar");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        // ── ações originais ───────────────────────────────────────────────────
        btnCadastrar.addActionListener(e -> {
            String nome     = campoNome.getText();
            String email    = campoEmail.getText();
            String senha    = new String(campoSenha.getPassword());
            String confirma = new String(campoConfirma.getPassword());

            String erro = adminController.cadastrarAdmin(nome, email, senha, confirma);

            if (erro != null) {
                NicanDialog.erro(this, erro);
            } else {
                NicanDialog.info(this, "Administrador cadastrado com sucesso!");
                new TelaPainelAdmin();
                dispose();
            }
        });

        btnVoltar.addActionListener(e -> { new TelaPainelAdmin(); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(32, 48, 24, 48));

        corpo.add(NicanTheme.criarCabecalhoSecao("Cadastrar Administrador"));
        corpo.add(Box.createVerticalStrut(4));

        String[]     labels = {"Nome", "E-mail", "Senha", "Confirmar Senha"};
        JComponent[] campos = {campoNome, campoEmail, campoSenha, campoConfirma};

        for (int i = 0; i < labels.length; i++) {
            corpo.add(NicanTheme.criarLabel(labels[i]));
            corpo.add(Box.createVerticalStrut(4));
            campos[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            corpo.add(campos[i]);
            corpo.add(Box.createVerticalStrut(i < labels.length - 1 ? 12 : 24));
        }

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botoes.setBackground(NicanTheme.FUNDO);
        botoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoes.add(btnCadastrar);
        botoes.add(btnVoltar);
        corpo.add(botoes);

        return corpo;
    }
}
