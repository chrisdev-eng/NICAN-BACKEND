package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Item;
import com.faculdade.nican.model.Requerimento;
import com.faculdade.nican.model.Sessao;
import com.faculdade.nican.repository.RequerimentoRepository;
import com.faculdade.nican.service.AlmoxarifeService;
import com.faculdade.nican.service.LoginService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TelaSolicitarMaterial extends JFrame {

    public TelaSolicitarMaterial() {
        setTitle("Solicitar Material - NICAN");
        setSize(480, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Solicitar material"), BorderLayout.NORTH);
        raiz.add(criarCorpo(), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        // ── lógica original ───────────────────────────────────────────────────
        List<Item> itens = AlmoxarifeService.listarTodos();
        JComboBox<Item> comboItens = new JComboBox<>(itens.toArray(new Item[0]));
        comboItens.setFont(NicanTheme.fonte(Font.PLAIN, 13));
        comboItens.setBackground(NicanTheme.FUNDO_CAMPO);
        comboItens.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item item) setText(item.getNome() + " — Disponível: " + item.getQuantidadeDisponivel());
                return this;
            }
        });

        JTextField campoQuantidade = NicanTheme.criarCampo();

        JButton btnSolicitar = NicanTheme.criarBotaoPrimario("Solicitar");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        btnSolicitar.addActionListener(e -> {
            Item itemSelecionado = (Item) comboItens.getSelectedItem();
            if (itemSelecionado == null) { JOptionPane.showMessageDialog(this, "Selecione um material."); return; }
            int quantidade;
            try { quantidade = Integer.parseInt(campoQuantidade.getText().trim()); }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Digite uma quantidade válida."); return; }
            if (quantidade <= 0) { JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero."); return; }
            if (quantidade > itemSelecionado.getQuantidadeDisponivel()) { JOptionPane.showMessageDialog(this, "Quantidade maior que o estoque disponível."); return; }
            Requerimento req = new Requerimento(Sessao.get().getUsuarioLogado(), itemSelecionado, quantidade);
            boolean salvou = RequerimentoRepository.salvar(req);
            if (salvou) { JOptionPane.showMessageDialog(this, "Requerimento enviado com sucesso!"); new TelaRequerimentos(LoginService.getLoginLogado()); dispose(); }
            else JOptionPane.showMessageDialog(this, "Erro ao enviar requerimento.");
        });
        btnVoltar.addActionListener(e -> { new TelaRequerimentos(LoginService.getLoginLogado()); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(32, 48, 24, 48));

        corpo.add(NicanTheme.criarCabecalhoSecao("Solicitar Material"));
        corpo.add(Box.createVerticalStrut(4));

        corpo.add(NicanTheme.criarLabel("Material"));
        corpo.add(Box.createVerticalStrut(4));
        comboItens.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(comboItens);
        corpo.add(Box.createVerticalStrut(12));

        corpo.add(NicanTheme.criarLabel("Quantidade"));
        corpo.add(Box.createVerticalStrut(4));
        campoQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        corpo.add(campoQuantidade);
        corpo.add(Box.createVerticalStrut(24));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botoes.setBackground(NicanTheme.FUNDO);
        botoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        botoes.add(btnSolicitar);
        botoes.add(btnVoltar);
        corpo.add(botoes);

        return corpo;
    }
}
