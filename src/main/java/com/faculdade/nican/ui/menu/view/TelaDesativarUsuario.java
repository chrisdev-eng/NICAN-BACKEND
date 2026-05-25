package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Usuario;
import com.faculdade.nican.repository.UsuarioRepository;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaDesativarUsuario extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaDesativarUsuario() {
        setTitle("Desativar Usuário - NICAN");
        setSize(780, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
        String[] colunas = {"ID","Nome","E-mail","Ativo"};
        modeloTabela = new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JButton btnDesativar = NicanTheme.criarBotaoPerigo("Desativar Selecionado");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        btnDesativar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione um usuário.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
            int id = (int) modeloTabela.getValueAt(linha, 0);
            String ativo = (String) modeloTabela.getValueAt(linha, 3);
            if (ativo.equals("Não")) { JOptionPane.showMessageDialog(this, "Este usuário já está desativado.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
            int confirma = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja desativar este usuário?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                if (UsuarioRepository.desativar(id)) { JOptionPane.showMessageDialog(this, "Usuário desativado com sucesso!"); carregarTabela(); }
                else JOptionPane.showMessageDialog(this, "Falha ao desativar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnVoltar.addActionListener(e -> { new TelaPainelAdmin(); dispose(); });

        JPanel corpo = new JPanel(new BorderLayout(0, 12));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Desativar Conta de Usuário"), BorderLayout.WEST);

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.add(btnDesativar);
        sul.add(btnVoltar);

        corpo.add(topo, BorderLayout.NORTH);
        corpo.add(scroll, BorderLayout.CENTER);
        corpo.add(sul, BorderLayout.SOUTH);
        return corpo;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        for (Usuario u : UsuarioRepository.listarTodos())
            modeloTabela.addRow(new Object[]{u.getId(), u.getNome(), u.getLogin(), u.isAtivo() ? "Sim" : "Não"});
    }
}
