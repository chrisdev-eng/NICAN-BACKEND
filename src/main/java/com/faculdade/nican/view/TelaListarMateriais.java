package com.faculdade.nican.view;

import com.faculdade.nican.model.entity.Item;
import com.faculdade.nican.controller.ItemController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarMateriais extends JFrame {
    private final ItemController itemController = new ItemController();
    

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaListarMateriais() {
        setTitle("Listar Materiais - NICAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(780, 520));
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Almoxarifado"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        carregarTabela();
        setVisible(true);
    }

    private JPanel criarCorpo() {
        String[] colunas = {"Nome","Categoria","Qualidade","Total","Disponível"};
        modeloTabela = new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JButton btnVoltar = NicanTheme.criarBotaoSecundario("Voltar");
        btnVoltar.addActionListener(e -> { new TelaHomeUsuario(); dispose(); });

        JPanel corpo = new JPanel(new BorderLayout(0, 12));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Materiais Disponíveis"), BorderLayout.WEST);

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.add(btnVoltar);

        corpo.add(topo, BorderLayout.NORTH);
        corpo.add(scroll, BorderLayout.CENTER);
        corpo.add(sul, BorderLayout.SOUTH);
        return corpo;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Item item : itemController.listarTodos())
            modeloTabela.addRow(new Object[]{item.getNome(), item.getCategoria().toString(), item.getQualidade().toString(), item.getQuantidadeTotal(), item.getQuantidadeDisponivel()});
    }
}
