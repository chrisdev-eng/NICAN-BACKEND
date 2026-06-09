package com.faculdade.nican.view;

<<<<<<< Updated upstream
import com.faculdade.nican.model.Categoria;
import com.faculdade.nican.model.Qualidade;
import com.faculdade.nican.model.AlmoxarifeService;
=======
import com.faculdade.nican.model.entity.Categoria;
import com.faculdade.nican.model.entity.Qualidade;
import com.faculdade.nican.controller.ItemController;
>>>>>>> Stashed changes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaGerenciarItens extends JFrame {
<<<<<<< Updated upstream
=======
    private final ItemController itemController = new ItemController();
>>>>>>> Stashed changes
    

    public TelaGerenciarItens() {
        NicanTheme.configurarJanela(this, "Adicionar Item - NICAN", 420, 540);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Gerenciar item"), BorderLayout.NORTH);
        raiz.add(NicanTheme.centralizarConteudo(criarCorpo(), 420), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── campos originais ──────────────────────────────────────────────────
        JTextField campoNome      = NicanTheme.criarCampo();
        JTextField campoQuantidade = NicanTheme.criarCampo();
<<<<<<< Updated upstream
        JComboBox<String> comboCategoria = NicanTheme.criarCombo(AlmoxarifeService.getCategorias());
        JComboBox<String> comboQualidade = NicanTheme.criarCombo(AlmoxarifeService.getQualidades());
=======
        JComboBox<String> comboCategoria = NicanTheme.criarCombo(itemController.getCategorias());
        JComboBox<String> comboQualidade = NicanTheme.criarCombo(itemController.getQualidades());
>>>>>>> Stashed changes

        JButton btnSalvar = NicanTheme.criarBotaoPrimario("Salvar");
        JButton btnVoltar = NicanTheme.criarBotaoSecundario("Voltar");

        // ── ações originais ───────────────────────────────────────────────────
        btnSalvar.addActionListener(e -> {
            String nome     = campoNome.getText();
            String qtdTexto = campoQuantidade.getText();
            if (qtdTexto.isBlank()) { NicanDialog.erro(this, "Preencha a quantidade."); return; }
            int quantidade;
            try { quantidade = Integer.parseInt(qtdTexto.trim()); }
            catch (NumberFormatException ex) { NicanDialog.erro(this, "Quantidade deve ser um número inteiro."); return; }
<<<<<<< Updated upstream
            Categoria categoria = AlmoxarifeService.getCategoriaByLabel((String) comboCategoria.getSelectedItem());
            Qualidade qualidade = AlmoxarifeService.getQualidadeByLabel((String) comboQualidade.getSelectedItem());
            String erro = AlmoxarifeService.adicionarItem(nome, quantidade, qualidade, categoria);
=======
            Categoria categoria = itemController.getCategoriaByLabel((String) comboCategoria.getSelectedItem());
            Qualidade qualidade = itemController.getQualidadeByLabel((String) comboQualidade.getSelectedItem());
            String erro = itemController.adicionarItem(nome, quantidade, qualidade, categoria);
>>>>>>> Stashed changes
            if (erro != null) NicanDialog.erro(this, erro);
            else { NicanDialog.info(this, "Item adicionado com sucesso!"); new TelaAlmoxarife(); dispose(); }
        });
        btnVoltar.addActionListener(e -> { new TelaAlmoxarife(); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(32, 48, 24, 48));

        corpo.add(NicanTheme.criarCabecalhoSecao("Adicionar Item"));
        corpo.add(Box.createVerticalStrut(4));

        String[] labels = {"Nome", "Quantidade", "Categoria", "Qualidade / Estado"};
        JComponent[] campos = {campoNome, campoQuantidade, comboCategoria, comboQualidade};
        for (int i = 0; i < labels.length; i++) {
            corpo.add(NicanTheme.criarLabel(labels[i]));
            corpo.add(Box.createVerticalStrut(4));
            campos[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            corpo.add(campos[i]);
            corpo.add(Box.createVerticalStrut(i < labels.length-1 ? 12 : 24));
        }

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botoes.setBackground(NicanTheme.FUNDO);
        botoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoes.add(btnSalvar);
        botoes.add(btnVoltar);
        corpo.add(botoes);

        return corpo;
    }
}
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
