package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Item;
import com.faculdade.nican.service.AlmoxarifeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarEstado extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> comboEstado;

    public TelaListarEstado() {

        setTitle("Materiais por Estado - NICAN");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel("Listar Materiais por Estado", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        comboEstado = new JComboBox<>(AlmoxarifeService.getQualidades());

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel painelTopo = new JPanel();

        painelTopo.add(new JLabel("Estado: "));
        painelTopo.add(comboEstado);
        painelTopo.add(btnFiltrar);

        String[] colunas = {
                "Nome",
                "Categoria",
                "Qualidade",
                "Total",
                "Disponível"
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

        String estadoSelecionado =
                (String) comboEstado.getSelectedItem();

        List<Item> itens = AlmoxarifeService.listarTodos();

        for (Item item : itens) {

            if (item.getQualidade().toString()
                    .equalsIgnoreCase(estadoSelecionado)) {

                modeloTabela.addRow(new Object[]{
                        item.getNome(),
                        item.getCategoria().toString(),
                        item.getQualidade().toString(),
                        item.getQuantidadeTotal(),
                        item.getQuantidadeDisponivel(),

                });
            }
        }
    }
}