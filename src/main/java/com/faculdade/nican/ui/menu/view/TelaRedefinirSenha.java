package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.service.LoginService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaRedefinirSenha extends JFrame {

    private boolean veioDaHomeUsuario;
    private boolean veioDoPainelAdmin;

    public TelaRedefinirSenha() { this(false, false); }
    public TelaRedefinirSenha(boolean veioDaHomeUsuario) { this(veioDaHomeUsuario, false); }

    public TelaRedefinirSenha(boolean veioDaHomeUsuario, boolean veioDoPainelAdmin) {
        this.veioDaHomeUsuario  = veioDaHomeUsuario;
        this.veioDoPainelAdmin  = veioDoPainelAdmin;

        setTitle("Redefinir Senha - NICAN");
        setSize(420, 510);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Segurança da conta"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── campos originais ──────────────────────────────────────────────────
        JTextField     campoEmail      = NicanTheme.criarCampo();
        JPasswordField campoSenhaAtual = NicanTheme.criarCampoSenha();
        JPasswordField campoNovaSenha  = NicanTheme.criarCampoSenha();
        JPasswordField campoConfirma   = NicanTheme.criarCampoSenha();

        JButton btnConfirmar = NicanTheme.criarBotaoPrimario("Confirmar");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        // ── navegação por teclado (lógica original) ───────────────────────────
        campoEmail.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_ENTER) campoSenhaAtual.requestFocus();
            }
        });
        campoSenhaAtual.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_UP) campoEmail.requestFocus();
                if (e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_ENTER) campoNovaSenha.requestFocus();
            }
        });
        campoNovaSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_UP) campoSenhaAtual.requestFocus();
                if (e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_ENTER) campoConfirma.requestFocus();
            }
        });
        campoConfirma.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_UP) campoNovaSenha.requestFocus();
                if (e.getKeyCode()==KeyEvent.VK_ENTER) btnConfirmar.doClick();
            }
        });

        // ── ações originais ───────────────────────────────────────────────────
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
        btnVoltar.addActionListener(e -> {
            if (veioDaHomeUsuario) { new TelaHomeUsuario(); }
            else if (veioDoPainelAdmin) { new TelaPainelAdmin(); }
            else { new TelaHome(); }
            dispose();
        });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(32, 48, 24, 48));

        corpo.add(NicanTheme.criarCabecalhoSecao("Redefinir Senha"));
        corpo.add(Box.createVerticalStrut(4));

        String[] labels = {"E-mail", "Senha Atual", "Nova Senha", "Confirmar Nova Senha"};
        JComponent[] campos = {campoEmail, campoSenhaAtual, campoNovaSenha, campoConfirma};
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
        botoes.add(btnConfirmar);
        botoes.add(btnVoltar);
        corpo.add(botoes);

        return corpo;
    }
}
