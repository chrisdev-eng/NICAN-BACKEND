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
    private String usuarioLogado;

    public TelaRequerimentos() {
        this.usuarioLogado = null;
        iniciarTela();
    }

    public TelaRequerimentos(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        iniciarTela();
    }

    private void iniciarTela() {

        setTitle("Requerimentos - NICAN");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel(
                "Meus Requerimentos",
                SwingConstants.CENTER
        );


        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        String[] colunas = {
                "Usuário",
                "Item",
                "Quantidade",
                "Data Solicitação",
                "Status"
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

            if (usuarioLogado == null ||
                    r.getUsuario().getLogin().equalsIgnoreCase(usuarioLogado)) {

                modeloTabela.addRow(new Object[]{

                        r.getUsuario().getNome(),
                        r.getItem().getNome(),
                        r.getQuantidadeSolicitada(),
                        r.getDataSolicitacao(),
                        r.getStatus()
                });
            }
        }
    }
}

