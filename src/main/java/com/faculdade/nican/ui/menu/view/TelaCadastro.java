package com.faculdade.nican.ui.menu.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.faculdade.nican.service.LoginService;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaCadastro extends JFrame {

    public TelaCadastro() {
        setTitle("Criar Conta - NICAN");
        setSize(420, 510);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Nova conta"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── campos originais ──────────────────────────────────────────────────
        JTextField campoNome    = NicanTheme.criarCampo();
        JTextField campoEmail   = NicanTheme.criarCampo();
        JPasswordField campoSenha    = NicanTheme.criarCampoSenha();
        JPasswordField campoConfirma = NicanTheme.criarCampoSenha();

        JButton btnCadastrar = NicanTheme.criarBotaoPrimario("Cadastrar");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        // ── navegação por teclado (lógica original) ───────────────────────────
        campoNome.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_ENTER) campoEmail.requestFocus();
            }
        });
        campoEmail.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_UP) campoNome.requestFocus();
                if (e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_ENTER) campoSenha.requestFocus();
            }
        });
        campoSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_UP) campoEmail.requestFocus();
                if (e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_ENTER) campoConfirma.requestFocus();
            }
        });
        campoConfirma.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_UP) campoSenha.requestFocus();
                if (e.getKeyCode()==KeyEvent.VK_ENTER) btnCadastrar.doClick();
            }
        });

        // ── ações originais ───────────────────────────────────────────────────
        btnCadastrar.addActionListener(e -> {
            String nome     = campoNome.getText();
            String email    = campoEmail.getText();
            String senha    = new String(campoSenha.getPassword());
            String confirma = new String(campoConfirma.getPassword());
            String erro = LoginService.cadastrarUsuario(nome, email, senha, confirma);
            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                new TelaLogin();
                dispose();
            }
        });
        btnVoltar.addActionListener(e -> { new TelaHome(); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(32, 48, 24, 48));

        corpo.add(NicanTheme.criarCabecalhoSecao("Criar Conta"));
        corpo.add(Box.createVerticalStrut(4));

        String[] labels = {"Nome", "E-mail", "Senha", "Confirmar Senha"};
        JComponent[] campos = {campoNome, campoEmail, campoSenha, campoConfirma};
        for (int i = 0; i < labels.length; i++) {
            corpo.add(NicanTheme.criarLabel(labels[i]));
            corpo.add(Box.createVerticalStrut(4));
            campos[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            corpo.add(campos[i]);
            corpo.add(Box.createVerticalStrut(i < labels.length-1 ? 12 : 24));
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
