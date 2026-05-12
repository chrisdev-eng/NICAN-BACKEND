package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Usuario;
import com.faculdade.nican.repository.UsuarioRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarUsuarios extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaListarUsuarios() {
        setTitle("Usuários - NICAN");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // título
        JLabel titulo = new JLabel("Lista de Usuários", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        // tabela
        String[] colunas = {"ID", "Nome", "E-mail", "Perfil", "Ativo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        // botão voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaPainelAdmin();
            dispose();
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
        List<Usuario> usuarios = UsuarioRepository.listarTodos();
        for (Usuario u : usuarios) {
            modeloTabela.addRow(new Object[]{
                    u.getId(),
                    u.getNome(),
                    u.getLogin(),
                    u.getPerfil(),
                    u.isAtivo() ? "Sim" : "Não"
            });
        }
    }
}