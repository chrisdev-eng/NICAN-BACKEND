package com.faculdade.nican.view;

import com.faculdade.nican.controller.UsuarioController;
import com.faculdade.nican.model.entity.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarUsuarios extends JFrame {
    private final UsuarioController usuarioController = new UsuarioController();
    
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaListarUsuarios() {
        setTitle("Usuários - NICAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(780, 520));
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Gestão de usuários"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        carregarTabela();
        setVisible(true);
    }

    private JPanel criarCorpo() {
        String[] colunas = {"ID","Nome","E-mail","Perfil","Ativo"};
        modeloTabela = new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JButton btnVoltar = NicanTheme.criarBotaoSecundario("Voltar");
        btnVoltar.addActionListener(e -> { new TelaPainelAdmin(); dispose(); });

        JPanel corpo = new JPanel(new BorderLayout(0, 12));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Lista de Usuários"), BorderLayout.WEST);

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
        for (Usuario u : usuarioController.listarTodos())
            modeloTabela.addRow(new Object[]{u.getId(), u.getNome(), u.getLogin(), u.getPerfil(), u.isAtivo() ? "Sim" : "Não"});
    }
}
