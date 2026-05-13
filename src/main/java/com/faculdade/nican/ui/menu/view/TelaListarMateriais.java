package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Item;
import com.faculdade.nican.service.AlmoxarifeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarMateriais extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaListarMateriais() {
        setTitle("Listar Materiais - NICAN");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Materiais Disponíveis", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        String[] colunas = {"ID", "Nome", "Categoria", "Qualidade", "Disponível", "Total"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnVoltar = new JButton("Voltar");

        btnVoltar.addActionListener(e -> {
            new TelaHomeUsuario();
            dispose();
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.add(btnVoltar);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);

        carregarTabela();

        setVisible(true);
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);

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
