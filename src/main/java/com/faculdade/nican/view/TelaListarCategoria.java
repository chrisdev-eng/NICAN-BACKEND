package com.faculdade.nican.view;

<<<<<<< Updated upstream
import com.faculdade.nican.model.Item;
import com.faculdade.nican.model.AlmoxarifeService;
=======
import com.faculdade.nican.model.entity.Item;
import com.faculdade.nican.controller.ItemController;
>>>>>>> Stashed changes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarCategoria extends JFrame {
<<<<<<< Updated upstream
=======
    private final ItemController itemController = new ItemController();
>>>>>>> Stashed changes
    

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JComboBox<String> comboCategoria;

    public TelaListarCategoria() {
        setTitle("Materiais por Categoria - NICAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 540));
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Filtrar por Categoria"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        carregarTabela();
        setVisible(true);
    }

    private JPanel criarCorpo() {
<<<<<<< Updated upstream
        comboCategoria = NicanTheme.criarCombo(AlmoxarifeService.getCategorias());
=======
        comboCategoria = NicanTheme.criarCombo(itemController.getCategorias());
>>>>>>> Stashed changes
        JButton btnFiltrar = NicanTheme.criarBotaoPrimario("Filtrar");
        JButton btnVoltar  = NicanTheme.criarBotaoSecundario("Voltar");
        btnFiltrar.addActionListener(e -> carregarTabela());
        btnVoltar.addActionListener(e -> { new TelaHomeUsuario(); dispose(); });

        String[] colunas = {"Nome","Categoria","Qualidade","Total","Disponível"};
        modeloTabela = new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filtros.setBackground(NicanTheme.FUNDO);
        filtros.setBorder(new EmptyBorder(0, 0, 8, 0));
        filtros.add(NicanTheme.criarLabel("Categoria:"));
        filtros.add(comboCategoria);
        filtros.add(btnFiltrar);

        JPanel corpo = new JPanel(new BorderLayout(0, 8));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Materiais por Categoria"), BorderLayout.NORTH);
        topo.add(filtros, BorderLayout.CENTER);

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
        String cat = (String) comboCategoria.getSelectedItem();
<<<<<<< Updated upstream
        for (Item item : AlmoxarifeService.listarTodos())
=======
        for (Item item : itemController.listarTodos())
>>>>>>> Stashed changes
            if (item.getCategoria().toString().equalsIgnoreCase(cat))
                modeloTabela.addRow(new Object[]{item.getNome(), item.getCategoria().toString(), item.getQualidade().toString(), item.getQuantidadeTotal(), item.getQuantidadeDisponivel()});
    }
}
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
