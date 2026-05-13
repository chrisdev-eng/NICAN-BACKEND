package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Item;
import com.faculdade.nican.service.AlmoxarifeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarCategoria extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> comboCategoria;

    public TelaListarCategoria() {

        setTitle("Materiais por Categoria - NICAN");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel("Listar Materiais por Categoria", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        comboCategoria = new JComboBox<>(AlmoxarifeService.getCategorias());

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Categoria: "));
        painelTopo.add(comboCategoria);
        painelTopo.add(btnFiltrar);

        String[] colunas = {
                "ID",
                "Nome",
                "Categoria",
                "Qualidade",
                "Disponível",
                "Total"
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        btnFiltrar.addActionListener(e -> carregarTabela());

        btnVoltar.addActionListener(e -> {
            new TelaHomeUsuario();
            dispose();
        });

        JPanel painelSul = new JPanel();
        painelSul.add(btnVoltar);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(painelTopo, BorderLayout.BEFORE_FIRST_LINE);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelSul, BorderLayout.SOUTH);

        add(painel);

        carregarTabela();

        setVisible(true);
    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        String categoriaSelecionada =
                (String) comboCategoria.getSelectedItem();

        List<Item> itens = AlmoxarifeService.listarTodos();

        for (Item item : itens) {

            if (item.getCategoria().toString()
                    .equalsIgnoreCase(categoriaSelecionada)) {

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
}
