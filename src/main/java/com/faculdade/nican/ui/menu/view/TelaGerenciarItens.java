package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Categoria;
import com.faculdade.nican.model.Qualidade;
import com.faculdade.nican.service.AlmoxarifeService;
import javax.swing.*;
import java.awt.*;

public class TelaGerenciarItens extends JFrame {

    public TelaGerenciarItens() {
        setTitle("Adicionar Item - NICAN");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // campos
        JLabel lblNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField();

        JLabel lblQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField();

        JLabel lblCategoria = new JLabel("Categoria:");
        JComboBox<String> comboCategoria = new JComboBox<>(AlmoxarifeService.getCategorias());

        JLabel lblQualidade = new JLabel("Qualidade:");
        JComboBox<String> comboQualidade = new JComboBox<>(AlmoxarifeService.getQualidades());

        // botões
        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        btnSalvar.addActionListener(e -> {
            String nome = campoNome.getText();
            String qtdTexto = campoQuantidade.getText();

            if (qtdTexto.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantidade;
            try {
                quantidade = Integer.parseInt(qtdTexto.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Categoria categoria = AlmoxarifeService.getCategoriaByLabel((String) comboCategoria.getSelectedItem());
            Qualidade qualidade = AlmoxarifeService.getQualidadeByLabel((String) comboQualidade.getSelectedItem());

            String erro = AlmoxarifeService.adicionarItem(nome, quantidade, qualidade, categoria);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Item adicionado com sucesso!");
                new TelaAlmoxarife();
                dispose();
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaAlmoxarife();
            dispose();
        });

        // painel dos campos
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblQuantidade, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoQuantidade, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblCategoria, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(comboCategoria, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblQualidade, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(comboQualidade, gbc);

        // painel dos botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);

        painel.add(painelCampos);
        painel.add(Box.createVerticalStrut(20));
        painel.add(painelBotoes);

        add(painel);
        setVisible(true);
    }
}