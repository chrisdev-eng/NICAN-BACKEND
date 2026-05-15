package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Usuario;
import com.faculdade.nican.repository.UsuarioRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaDesativarUsuario extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaDesativarUsuario() {
        setTitle("Desativar Usuário - NICAN");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // título
        JLabel titulo = new JLabel("Selecione o usuário para desativar", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        // tabela
        String[] colunas = {"ID", "Nome", "E-mail", "Ativo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        // botões
        JButton btnDesativar = new JButton("Desativar Selecionado");
        JButton btnVoltar = new JButton("Voltar");

        btnDesativar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            String ativo = (String) modeloTabela.getValueAt(linhaSelecionada, 3);

            if (ativo.equals("Não")) {
                JOptionPane.showMessageDialog(this, "Este usuário já está desativado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirma = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja desativar este usuário?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                if (UsuarioRepository.desativar(id)) {
                    JOptionPane.showMessageDialog(this, "Usuário desativado com sucesso!");
                    carregarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao desativar.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaPainelAdmin();
            dispose();
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.add(btnDesativar);
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
                    u.isAtivo() ? "Sim" : "Não"
            });
        }
    }
}