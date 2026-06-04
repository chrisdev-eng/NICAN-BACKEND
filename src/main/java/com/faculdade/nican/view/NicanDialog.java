package com.faculdade.nican.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dialogs customizados estilizados com NicanTheme.
 * Substitui JOptionPane em todas as telas do sistema.
 */
public class NicanDialog {

    // ── Info / Sucesso ────────────────────────────────────────────────────────
    public static void info(Component pai, String mensagem) {
        mostrar(pai, "Informação", mensagem, "✓", NicanTheme.VERDE_ESCURO, NicanTheme.VERDE_CLARO, false);
    }

    // ── Aviso ─────────────────────────────────────────────────────────────────
    public static void aviso(Component pai, String mensagem) {
        mostrar(pai, "Aviso", mensagem, "!", new Color(0x7A5C00), new Color(0xD4A800), false);
    }

    // ── Erro ──────────────────────────────────────────────────────────────────
    public static void erro(Component pai, String mensagem) {
        mostrar(pai, "Erro", mensagem, "✕", NicanTheme.VERMELHO, NicanTheme.VERMELHO_BD, false);
    }

    // ── Confirmar (retorna true = sim) ────────────────────────────────────────
    public static boolean confirmar(Component pai, String titulo, String mensagem) {
        return mostrarConfirm(pai, titulo, mensagem);
    }

    // ── Input estilizado (retorna null se cancelado) ───────────────────────────
    public static String input(Component pai, String titulo, String hint) {
        return mostrarInput(pai, titulo, hint);
    }

    // ── Implementações internas ───────────────────────────────────────────────

    private static void mostrar(Component pai, String titulo, String mensagem,
                                String icone, Color corIcone, Color corBorda, boolean modal) {
        JDialog dlg = criarBase(pai, titulo, 360, 200);

        JPanel conteudo = new JPanel(new BorderLayout(14, 0));
        conteudo.setBackground(NicanTheme.FUNDO);
        conteudo.setBorder(new EmptyBorder(24, 24, 20, 24));

        // Ícone
        JLabel ico = new JLabel(icone, SwingConstants.CENTER);
        ico.setFont(NicanTheme.fonte(Font.BOLD, 22));
        ico.setForeground(Color.WHITE);
        ico.setOpaque(true);
        ico.setBackground(corIcone);
        ico.setPreferredSize(new Dimension(44, 44));
        ico.setBorder(BorderFactory.createLineBorder(corBorda, 1));

        // Texto
        JLabel txt = new JLabel("<html><body style='width:220px'>" +
                mensagem.replace("\n", "<br>") + "</body></html>");
        txt.setFont(NicanTheme.fonte(Font.PLAIN, 13));
        txt.setForeground(NicanTheme.TEXTO_ESCURO);

        conteudo.add(ico, BorderLayout.WEST);
        conteudo.add(txt, BorderLayout.CENTER);

        // Botão OK
        JButton btnOk = NicanTheme.criarBotaoPrimario("OK");
        btnOk.addActionListener(e -> dlg.dispose());

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.setBorder(new EmptyBorder(0, 24, 16, 24));
        sul.add(btnOk);

        dlg.add(conteudo, BorderLayout.CENTER);
        dlg.add(sul, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private static boolean mostrarConfirm(Component pai, String titulo, String mensagem) {
        final boolean[] resposta = {false};
        JDialog dlg = criarBase(pai, titulo, 420, 260);

        JPanel conteudo = new JPanel(new BorderLayout(14, 0));
        conteudo.setBackground(NicanTheme.FUNDO);
        conteudo.setBorder(new EmptyBorder(24, 24, 20, 24));

        // Ícone de aviso
        JLabel ico = new JLabel("!", SwingConstants.CENTER);
        ico.setFont(NicanTheme.fonte(Font.BOLD, 22));
        ico.setForeground(Color.WHITE);
        ico.setOpaque(true);
        ico.setBackground(new Color(0x7A5C00));
        ico.setPreferredSize(new Dimension(44, 44));
        ico.setBorder(BorderFactory.createLineBorder(new Color(0xD4A800), 1));

        JLabel txt = new JLabel("<html><body style='width:260px'>" +
                mensagem.replace("\n", "<br>") + "</body></html>");
        txt.setFont(NicanTheme.fonte(Font.PLAIN, 13));
        txt.setForeground(NicanTheme.TEXTO_ESCURO);

        conteudo.add(ico, BorderLayout.WEST);
        conteudo.add(txt, BorderLayout.CENTER);

        // Botões
        JButton btnSim = NicanTheme.criarBotaoPerigo("Sim, confirmar");
        JButton btnNao = NicanTheme.criarBotaoSecundario("Cancelar");

        btnSim.addActionListener(e -> { resposta[0] = true;  dlg.dispose(); });
        btnNao.addActionListener(e -> { resposta[0] = false; dlg.dispose(); });

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.setBorder(new EmptyBorder(0, 24, 16, 24));
        sul.add(btnNao);
        sul.add(btnSim);

        dlg.add(conteudo, BorderLayout.CENTER);
        dlg.add(sul, BorderLayout.SOUTH);
        dlg.setVisible(true);

        return resposta[0];
    }

    private static String mostrarInput(Component pai, String titulo, String hint) {
        final String[] resposta = {null};
        JDialog dlg = criarBase(pai, titulo, 420, 280);

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBackground(NicanTheme.FUNDO);
        conteudo.setBorder(new EmptyBorder(20, 28, 8, 28));

        // Hint
        if (hint != null && !hint.isBlank()) {
            JLabel lblHint = new JLabel("<html><body style='width:340px'>" +
                    hint.replace("\n", "<br>") + "</body></html>");
            lblHint.setFont(NicanTheme.fonte(Font.PLAIN, 12));
            lblHint.setForeground(NicanTheme.TEXTO_MUTED);
            lblHint.setAlignmentX(Component.LEFT_ALIGNMENT);
            conteudo.add(lblHint);
            conteudo.add(Box.createVerticalStrut(14));
        }

        // Label
        JLabel lbl = NicanTheme.criarLabel("Quantidade");
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        conteudo.add(lbl);
        conteudo.add(Box.createVerticalStrut(5));

        // Campo — tamanho fixo para garantir visibilidade
        JTextField campo = NicanTheme.criarCampo();
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        campo.setPreferredSize(new Dimension(360, 36));
        conteudo.add(campo);

        // Botões
        JButton btnOk     = NicanTheme.criarBotaoPrimario("Confirmar");
        JButton btnCancel = NicanTheme.criarBotaoSecundario("Cancelar");

        btnOk.addActionListener(e -> {
            String v = campo.getText().trim();
            if (!v.isBlank()) resposta[0] = v;
            dlg.dispose();
        });
        btnCancel.addActionListener(e -> dlg.dispose());

        // Enter no campo confirma
        campo.addActionListener(e -> btnOk.doClick());

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.setBorder(new EmptyBorder(12, 28, 16, 28));
        sul.add(btnCancel);
        sul.add(btnOk);

        dlg.add(conteudo, BorderLayout.CENTER);
        dlg.add(sul, BorderLayout.SOUTH);
        dlg.setVisible(true);

        return resposta[0];
    }

    // ── Fábrica de JDialog base ───────────────────────────────────────────────
    private static JDialog criarBase(Component pai, String titulo, int w, int h) {
        Window janela = pai instanceof Window ? (Window) pai
                : SwingUtilities.getWindowAncestor(pai);
        JDialog dlg = new JDialog((Frame) (janela instanceof Frame ? janela : null), true);
        dlg.setTitle(titulo);
        dlg.setSize(w, h);
        dlg.setResizable(false);
        dlg.setLocationRelativeTo(pai);
        dlg.setLayout(new BorderLayout());
        dlg.getContentPane().setBackground(NicanTheme.FUNDO);

        // Header estilizado
        dlg.add(NicanTheme.criarHeader(titulo), BorderLayout.NORTH);

        return dlg;
    }
}