package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Categoria;
import com.faculdade.nican.model.Qualidade;
import com.faculdade.nican.service.AlmoxarifeService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaGerenciarItens extends JFrame {

    public TelaGerenciarItens() {
        setTitle("Adicionar Item - NICAN");
        setSize(420, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Gerenciar item"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── campos originais ──────────────────────────────────────────────────
        JTextField campoNome      = NicanTheme.criarCampo();
        JTextField campoQuantidade = NicanTheme.criarCampo();
        JComboBox<String> comboCategoria = NicanTheme.criarCombo(AlmoxarifeService.getCategorias());
        JComboBox<String> comboQualidade = NicanTheme.criarCombo(AlmoxarifeService.getQualidades());

        JButton btnSalvar = NicanTheme.criarBotaoPrimario("Salvar");
        JButton btnVoltar = NicanTheme.criarBotaoSecundario("Voltar");

        // ── ações originais ───────────────────────────────────────────────────
        btnSalvar.addActionListener(e -> {
            String nome     = campoNome.getText();
            String qtdTexto = campoQuantidade.getText();
            if (qtdTexto.isBlank()) { JOptionPane.showMessageDialog(this, "Preencha a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
            int quantidade;
            try { quantidade = Integer.parseInt(qtdTexto.trim()); }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
            Categoria categoria = AlmoxarifeService.getCategoriaByLabel((String) comboCategoria.getSelectedItem());
            Qualidade qualidade = AlmoxarifeService.getQualidadeByLabel((String) comboQualidade.getSelectedItem());
            String erro = AlmoxarifeService.adicionarItem(nome, quantidade, qualidade, categoria);
            if (erro != null) JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            else { JOptionPane.showMessageDialog(this, "Item adicionado com sucesso!"); new TelaAlmoxarife(); dispose(); }
        });
        btnVoltar.addActionListener(e -> { new TelaAlmoxarife(); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(32, 48, 24, 48));

        corpo.add(NicanTheme.criarCabecalhoSecao("Adicionar Item"));
        corpo.add(Box.createVerticalStrut(4));

        String[] labels = {"Nome", "Quantidade", "Categoria", "Qualidade / Estado"};
        JComponent[] campos = {campoNome, campoQuantidade, comboCategoria, comboQualidade};
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
        botoes.add(btnSalvar);
        botoes.add(btnVoltar);
        corpo.add(botoes);

        return corpo;
    }
}