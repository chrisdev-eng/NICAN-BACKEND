package com.faculdade.nican.view;

import com.faculdade.nican.model.UsuarioService;
import com.faculdade.nican.model.Usuario;
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
        String[] colunas = {"ID","Nome","E-mail","Ativo"};
        modeloTabela = new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JButton btnDesativar = NicanTheme.criarBotaoPerigo("Desativar Selecionado");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        btnDesativar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) { NicanDialog.aviso(this, "Selecione um usuário."); return; }
            int id = (int) modeloTabela.getValueAt(linha, 0);
            String ativo = (String) modeloTabela.getValueAt(linha, 3);
            if (ativo.equals("Não")) { NicanDialog.aviso(this, "Este usuário já está desativado."); return; }
            if (NicanDialog.confirmar(this, "Confirmar", "Tem certeza que deseja desativar este usuário?")) {
                if (UsuarioService.desativar(id)) { NicanDialog.info(this, "Usuário desativado com sucesso!"); carregarTabela(); }
                else NicanDialog.erro(this, "Falha ao desativar.");
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
        for (Usuario u : UsuarioService.listarTodos())
            modeloTabela.addRow(new Object[]{u.getId(), u.getNome(), u.getLogin(), u.isAtivo() ? "Sim" : "Não"});
    }
}

