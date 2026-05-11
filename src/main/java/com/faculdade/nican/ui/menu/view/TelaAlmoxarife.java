package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Item;
import com.faculdade.nican.service.AlmoxarifeService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaAlmoxarife extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaAlmoxarife() {
        setTitle("Almoxarifado - NICAN");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // painel principal
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // título
        JLabel titulo = new JLabel("Itens do Almoxarifado", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        // tabela
        String[] colunas = {"ID", "Nome", "Categoria", "Qualidade", "Disponível", "Total"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        // botões
        JButton btnAdicionar = new JButton("Adicionar Item");
        JButton btnRemover = new JButton("Remover Item");
        JButton btnVoltar = new JButton("Voltar");

        btnAdicionar.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Em breve: adc tela de ITEM");
        });

        btnRemover.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um item para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            int confirma = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover este item?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                String erro = AlmoxarifeService.removerItem(id);
                if (erro != null) {
                    JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Item removido com sucesso!");
                    carregarTabela();
                }
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaSistemaAdmin();
            dispose();
        });

        // painel dos botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnVoltar);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);

        // carrega os itens do banco
        carregarTabela();

        setVisible(true);
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0); // limpa a tabela
        List<Item> itens = AlmoxarifeService.listarTodos();
        for (Item item : itens) {
            modeloTabela.addRow(new Object[]{
                    item.getIdItem(),
                    item.getNome(),
                    item.getCategoria().toString(),
                    item.getQualidade().toString(),
                    item.getQuantidadeDisponivel(),
                    item.getQuantidadeTotal()
            });
        }
    }
}