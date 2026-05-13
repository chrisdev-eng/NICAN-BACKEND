package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Requerimento;
import com.faculdade.nican.repository.RequerimentoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaRequerimentos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaRequerimentos() {

        setTitle("Requerimentos - NICAN");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel(
                "Lista de Requerimentos",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        String[] colunas = {
                "ID",
                "Usuário",
                "Item",
                "Quantidade",
                "Status",
                "Data Solicitação"
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnVoltar = new JButton("Voltar");
        JButton btnSolicitar = new JButton("Solicitar Material");

        btnVoltar.addActionListener(e -> {
            new TelaHomeUsuario();
            dispose();
        });

        btnSolicitar.addActionListener(e -> {
            new TelaSolicitarMaterial();
            dispose();
        });

        JPanel painelSul = new JPanel();
        painelSul.add(btnSolicitar);
        painelSul.add(btnVoltar);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelSul, BorderLayout.SOUTH);

        add(painel);

        carregarTabela();

        setVisible(true);
    }

    private void carregarTabela() {

        modeloTabela.setRowCount(0);

        List<Requerimento> lista =
                RequerimentoRepository.buscarPendentes();

        for (Requerimento r : lista) {

            modeloTabela.addRow(new Object[]{

                    r.getIdRequerimento(),
                    r.getUsuario().getNome(),
                    r.getItem().getNome(),
                    r.getQuantidadeSolicitada(),
                    r.getStatus(),
                    r.getDataSolicitacao()
            });
        }
    }
}