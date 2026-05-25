package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Admin;
import com.faculdade.nican.model.Requerimento;
import com.faculdade.nican.model.Sessao;
import com.faculdade.nican.repository.RequerimentoRepository;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Validar requerimentos"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        carregarTabela();
        setVisible(true);
    }

    private JPanel criarCorpo() {
        String[] colunas = {"ID","Usuário","Material","Quantidade","Data Solicitação","Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JButton btnAprovar   = NicanTheme.criarBotaoPrimario("Aprovar Selecionado");
        JButton btnRecusar   = NicanTheme.criarBotaoPerigo("Recusar Selecionado");
        JButton btnAtualizar = NicanTheme.criarBotaoSecundario("Atualizar Lista");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        btnAprovar.addActionListener(e -> processarDecisao(true));
        btnRecusar.addActionListener(e -> processarDecisao(false));
        btnAtualizar.addActionListener(e -> carregarTabela());
        btnVoltar.addActionListener(e -> { new TelaSistemaAdmin(); dispose(); });

        JPanel corpo = new JPanel(new BorderLayout(0, 12));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Requerimentos Pendentes"), BorderLayout.WEST);

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.add(btnAprovar);
        sul.add(btnRecusar);
        sul.add(btnAtualizar);
        sul.add(btnVoltar);

        corpo.add(topo, BorderLayout.NORTH);
        corpo.add(scroll, BorderLayout.CENTER);
        corpo.add(sul, BorderLayout.SOUTH);
        return corpo;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        requerimentosPendentes = RequerimentoRepository.buscarPendentes();
        for (Requerimento r : requerimentosPendentes)
            modeloTabela.addRow(new Object[]{r.getIdRequerimento(), r.getUsuario()!=null?r.getUsuario().getNome():"N/A", r.getItem()!=null?r.getItem().getNome():"N/A", r.getQuantidadeSolicitada(), r.getDataSolicitacao(), r.getStatus()});
        if (requerimentosPendentes.isEmpty())
            JOptionPane.showMessageDialog(this, "Nenhum requerimento pendente no momento.", "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    private void processarDecisao(boolean aprovar) {
        int linha = tabela.getSelectedRow();
        if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione um requerimento na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Requerimento alvo = requerimentosPendentes.get(linha);
        Admin adminLogado = Sessao.get().getAdminLogado();
        if (adminLogado == null) { JOptionPane.showMessageDialog(this, "Sessão de administrador inválida. Faça login novamente.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        String acao = aprovar ? "aprovar" : "recusar";
        int confirma = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja " + acao + " este requerimento?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirma != JOptionPane.YES_OPTION) return;
        boolean sucesso;
        if (aprovar) {
            sucesso = RequerimentoRepository.aprovar(alvo, adminLogado);
            JOptionPane.showMessageDialog(this, sucesso ? "Requerimento aprovado! Estoque atualizado." : "Falha ao aprovar. Verifique se há estoque suficiente.", sucesso?"Sucesso":"Erro", sucesso?JOptionPane.INFORMATION_MESSAGE:JOptionPane.ERROR_MESSAGE);
        } else {
            sucesso = RequerimentoRepository.recusar(alvo, adminLogado);
            JOptionPane.showMessageDialog(this, sucesso ? "Requerimento recusado." : "Falha ao recusar o requerimento.", sucesso?"Concluído":"Erro", sucesso?JOptionPane.INFORMATION_MESSAGE:JOptionPane.ERROR_MESSAGE);
        }
        if (sucesso) carregarTabela();
    }
}
