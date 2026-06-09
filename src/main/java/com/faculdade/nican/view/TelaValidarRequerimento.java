package com.faculdade.nican.view;

import com.faculdade.nican.controller.LoginController;
import com.faculdade.nican.controller.RequerimentoController;
import com.faculdade.nican.model.entity.Admin;
import com.faculdade.nican.model.entity.Requerimento;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaValidarRequerimento extends JFrame {
    private final LoginController        loginController        = new LoginController();
    private final RequerimentoController requerimentoController = new RequerimentoController();

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private List<Requerimento> requerimentosPendentes;

    public TelaValidarRequerimento() {
        setTitle("Validar Requerimentos - NICAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(960, 540));
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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
        String[] colunas = {"ID", "Usuário", "Material", "Quantidade", "Data Solicitação", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

        JButton btnAprovar   = NicanTheme.criarBotaoPrimario("Aprovar Selecionado");
        JButton btnRecusar   = NicanTheme.criarBotaoPerigo("Recusar Selecionado");
        JButton btnAtualizar = NicanTheme.criarBotaoSecundario("Atualizar Lista");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        btnAprovar.addActionListener(e   -> processarDecisao(true));
        btnRecusar.addActionListener(e   -> processarDecisao(false));
        btnAtualizar.addActionListener(e -> carregarTabela());
        btnVoltar.addActionListener(e    -> { new TelaSistemaAdmin(); dispose(); });

        JPanel corpo = new JPanel(new BorderLayout(0, 12));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Requerimentos Pendentes"), BorderLayout.WEST);

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.add(btnAprovar); sul.add(btnRecusar); sul.add(btnAtualizar); sul.add(btnVoltar);

        corpo.add(topo,   BorderLayout.NORTH);
        corpo.add(scroll, BorderLayout.CENTER);
        corpo.add(sul,    BorderLayout.SOUTH);
        return corpo;
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        requerimentosPendentes = requerimentoController.buscarPendentes();
        for (Requerimento r : requerimentosPendentes)
            modeloTabela.addRow(new Object[]{
                    r.getIdRequerimento(),
                    r.getUsuario() != null ? r.getUsuario().getNome() : "N/A",
                    r.getItem()    != null ? r.getItem().getNome()    : "N/A",
                    r.getQuantidadeSolicitada(),
                    r.getDataSolicitacao(),
                    r.getStatus()
            });
        if (requerimentosPendentes.isEmpty())
            NicanDialog.info(this, "Nenhum requerimento pendente no momento.");
    }

    private void processarDecisao(boolean aprovar) {
        int linha = tabela.getSelectedRow();
        if (linha == -1) { NicanDialog.aviso(this, "Selecione um requerimento na tabela."); return; }
        Requerimento alvo = requerimentosPendentes.get(linha);
        // View obtém o admin via controller — sem acessar Sessao diretamente
        Admin adminLogado = loginController.getAdminLogado();
        if (adminLogado == null) {
            NicanDialog.erro(this, "Sessão de administrador inválida. Faça login novamente."); return;
        }
        if (!NicanDialog.confirmar(this, "Confirmar",
                "Tem certeza que deseja " + (aprovar ? "aprovar" : "recusar") + " este requerimento?")) return;

        boolean sucesso;
        if (aprovar) {
            sucesso = requerimentoController.aprovar(alvo, adminLogado);
            if (sucesso) NicanDialog.info(this, "Requerimento aprovado! Estoque atualizado.");
            else         NicanDialog.erro(this, "Falha ao aprovar. Verifique se há estoque suficiente.");
        } else {
            sucesso = requerimentoController.recusar(alvo, adminLogado);
            if (sucesso) NicanDialog.info(this, "Requerimento recusado.");
            else         NicanDialog.erro(this, "Falha ao recusar o requerimento.");
        }
        if (sucesso) carregarTabela();
    }
}
