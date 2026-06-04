package com.faculdade.nican.view;

import com.faculdade.nican.model.Item;
import com.faculdade.nican.model.AlmoxarifeService;
import com.faculdade.nican.controller.LoginController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaAlmoxarife extends JFrame {
    private final LoginController loginController = new LoginController();
    

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaAlmoxarife() {
        setTitle("Almoxarifado - NICAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 540));
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

    // ── Dialogs estilizados ───────────────────────────────────────────────────

    /** Exibe mensagem informativa/sucesso estilizada. */
    private void mostrarInfo(String mensagem) {
        NicanDialog.info(this, mensagem);
    }

    /** Exibe mensagem de aviso (amarelo) estilizada. */
    private void mostrarAviso(String mensagem) {
        NicanDialog.aviso(this, mensagem);
    }

    /** Exibe mensagem de erro (vermelho) estilizada. */
    private void mostrarErro(String mensagem) {
        NicanDialog.erro(this, mensagem);
    }

    /** Abre input dialog estilizado para digitar quantidade. Retorna null se cancelado. */
    private String pedirQuantidade(String titulo, String hint) {
        return NicanDialog.input(this, titulo, hint);
    }

    /** Abre confirm dialog estilizado. Retorna true se confirmado. */
    private boolean confirmar(String titulo, String mensagem) {
        return NicanDialog.confirmar(this, titulo, mensagem);
    }

    // ── Corpo da tela ─────────────────────────────────────────────────────────

    private JPanel criarCorpo() {
        String[] colunas = {"ID", "Nome", "Categoria", "Qualidade", "Disponível", "Total"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JButton btnAdicionar    = NicanTheme.criarBotaoPrimario("Adicionar Item");
        JButton btnAdicionarQtd = NicanTheme.criarBotaoSecundario("Adicionar Quantidade");
        JButton btnRemoverQtd   = NicanTheme.criarBotaoSecundario("Remover Quantidade");
        JButton btnExcluirItem  = NicanTheme.criarBotaoPerigo("Excluir Item");
        JButton btnVoltar       = NicanTheme.criarBotaoSecundario("Voltar");
        JButton btnLogout       = NicanTheme.criarBotaoPerigo("Logout");

        // ── ações ─────────────────────────────────────────────────────────────
        btnAdicionar.addActionListener(e -> { new TelaGerenciarItens(); dispose(); });

        btnAdicionarQtd.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) { mostrarAviso("Selecione um item na tabela."); return; }
            int id         = (int) modeloTabela.getValueAt(linha, 0);
            int totalAtual = (int) modeloTabela.getValueAt(linha, 5);
            String entrada = pedirQuantidade(
                    "Adicionar Quantidade",
                    "Quantidade total atual: " + totalAtual + "\nQuantas unidades deseja adicionar?"
            );
            if (entrada == null) return;
            try {
                int qtd = Integer.parseInt(entrada.trim());
                String erro = AlmoxarifeService.adicionarQuantidade(id, qtd);
                if (erro != null) mostrarErro(erro);
                else { mostrarInfo("Quantidade adicionada com sucesso!"); carregarTabela(); }
            } catch (NumberFormatException ex) { mostrarErro("Digite um número válido."); }
        });

        btnRemoverQtd.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) { mostrarAviso("Selecione um item na tabela."); return; }
            int id         = (int) modeloTabela.getValueAt(linha, 0);
            int totalAtual = (int) modeloTabela.getValueAt(linha, 5);
            String entrada = pedirQuantidade(
                    "Remover Quantidade",
                    "Quantidade total em estoque: " + totalAtual + "\nQuantas unidades deseja baixar?"
            );
            if (entrada == null) return;
            try {
                int qtd = Integer.parseInt(entrada.trim());
                String erro = AlmoxarifeService.removerQuantidade(id, qtd);
                if (erro != null) mostrarErro(erro);
                else { mostrarInfo("Quantidade removida com sucesso!"); carregarTabela(); }
            } catch (NumberFormatException ex) { mostrarErro("Digite um número válido."); }
        });

        btnExcluirItem.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) { mostrarAviso("Selecione um item para excluir."); return; }
            int id     = (int) modeloTabela.getValueAt(linha, 0);
            String nome = (String) modeloTabela.getValueAt(linha, 1);
            boolean ok = confirmar(
                    "Confirmar Exclusão",
                    "Tem certeza que deseja EXCLUIR o item\n\"" + nome + "\" do sistema?\n\nEsta ação não pode ser desfeita."
            );
            if (ok) {
                String erro = AlmoxarifeService.removerItem(id);
                if (erro != null) mostrarErro(erro);
                else { mostrarInfo("Item excluído com sucesso!"); carregarTabela(); }
            }
        });

        btnVoltar.addActionListener(e -> { new TelaSistemaAdmin(); dispose(); });

        btnLogout.addActionListener(e -> {
            loginController.fazerLogout();
            mostrarInfo("Logout realizado com sucesso!");
            new TelaHome();
            dispose();
        });

        // ── layout ────────────────────────────────────────────────────────────
        JPanel corpo = new JPanel(new BorderLayout(0, 12));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Itens do Almoxarifado"), BorderLayout.WEST);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.setBackground(NicanTheme.FUNDO);
        botoes.add(btnAdicionar);
        botoes.add(btnAdicionarQtd);
        botoes.add(btnRemoverQtd);
        botoes.add(btnExcluirItem);
        botoes.add(btnVoltar);
        botoes.add(btnLogout);

        corpo.add(topo, BorderLayout.NORTH);
        corpo.add(scroll, BorderLayout.CENTER);
        corpo.add(botoes, BorderLayout.SOUTH);
        return corpo;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Item> itens = AlmoxarifeService.listarTodos();
        for (Item item : itens) {
            modeloTabela.addRow(new Object[]{
                    item.getIdItem(), item.getNome(),
                    item.getCategoria().toString(), item.getQualidade().toString(),
                    item.getQuantidadeDisponivel(), item.getQuantidadeTotal()
            });
        }
    }
}

