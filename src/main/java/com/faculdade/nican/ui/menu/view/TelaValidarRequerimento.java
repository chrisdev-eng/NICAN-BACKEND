package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Admin;
import com.faculdade.nican.model.Requerimento;
import com.faculdade.nican.model.Sessao;
import com.faculdade.nican.repository.RequerimentoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela para o Admin visualizar e validar (aprovar/recusar)
 * os requerimentos de materiais pendentes.
 *
 * Corresponde ao item 9 do checklist:
 * "Interface Almoxarifado - Validação de Requerimento de Materiais"
 *
 * CORREÇÃO: botão Voltar agora retorna para TelaSistemaAdmin
 * (antes ia para TelaAlmoxarife, que não é a tela de origem).
 */
public class TelaValidarRequerimento extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private List<Requerimento> requerimentosPendentes;

    public TelaValidarRequerimento() {
        setTitle("Validar Requerimentos - NICAN");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Requerimentos Pendentes", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        String[] colunas = {"ID", "Usuário", "Material", "Quantidade", "Data Solicitação", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnAprovar   = new JButton("Aprovar Selecionado");
        JButton btnRecusar   = new JButton("Recusar Selecionado");
        JButton btnAtualizar = new JButton("Atualizar Lista");
        JButton btnVoltar    = new JButton("Voltar");

        btnAprovar.addActionListener(e -> processarDecisao(true));
        btnRecusar.addActionListener(e -> processarDecisao(false));
        btnAtualizar.addActionListener(e -> carregarTabela());

        // CORREÇÃO: Voltar retorna para TelaSistemaAdmin (menu principal do admin)
        btnVoltar.addActionListener(e -> {
            new TelaSistemaAdmin();
            dispose();
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.add(btnAprovar);
        painelBotoes.add(btnRecusar);
        painelBotoes.add(btnAtualizar);
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
        requerimentosPendentes = RequerimentoRepository.buscarPendentes();

        for (Requerimento r : requerimentosPendentes) {
            modeloTabela.addRow(new Object[]{
                    r.getIdRequerimento(),
                    r.getUsuario() != null ? r.getUsuario().getNome() : "N/A",
                    r.getItem()    != null ? r.getItem().getNome()    : "N/A",
                    r.getQuantidadeSolicitada(),
                    r.getDataSolicitacao(),
                    r.getStatus()
            });
        }

        if (requerimentosPendentes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum requerimento pendente no momento.",
                    "Informação",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void processarDecisao(boolean aprovar) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um requerimento na tabela.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Requerimento alvo = requerimentosPendentes.get(linhaSelecionada);

        Admin adminLogado = Sessao.get().getAdminLogado();
        if (adminLogado == null) {
            JOptionPane.showMessageDialog(this,
                    "Sessão de administrador inválida. Faça login novamente.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String acao = aprovar ? "aprovar" : "recusar";
        int confirma = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja " + acao + " este requerimento?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirma != JOptionPane.YES_OPTION) return;

        boolean sucesso;
        if (aprovar) {
            sucesso = RequerimentoRepository.aprovar(alvo, adminLogado);
            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Requerimento aprovado! Estoque atualizado.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Falha ao aprovar. Verifique se há estoque suficiente.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            sucesso = RequerimentoRepository.recusar(alvo, adminLogado);
            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Requerimento recusado.",
                        "Concluído",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Falha ao recusar o requerimento.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (sucesso) {
            carregarTabela();
        }
    }
}