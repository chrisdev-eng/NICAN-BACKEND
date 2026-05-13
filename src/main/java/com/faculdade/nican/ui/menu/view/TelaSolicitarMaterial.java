package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Item;
import com.faculdade.nican.model.Requerimento;
import com.faculdade.nican.model.Sessao;
import com.faculdade.nican.repository.RequerimentoRepository;
import com.faculdade.nican.service.AlmoxarifeService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaSolicitarMaterial extends JFrame {

    public TelaSolicitarMaterial() {
        setTitle("Solicitar Material - NICAN");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblItem = new JLabel("Material:");
        JLabel lblQuantidade = new JLabel("Quantidade:");

        List<Item> itens = AlmoxarifeService.listarTodos();
        JComboBox<Item> comboItens = new JComboBox<>(itens.toArray(new Item[0]));

        comboItens.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Item item) {
                    setText(item.getNome() + " - Disponível: " + item.getQuantidadeDisponivel());
                }

                return this;
            }
        });

        JTextField campoQuantidade = new JTextField();

        JButton btnSolicitar = new JButton("Solicitar");
        JButton btnVoltar = new JButton("Voltar");

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(lblItem, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        painel.add(comboItens, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(lblQuantidade, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        painel.add(campoQuantidade, gbc);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSolicitar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        painel.add(painelBotoes, gbc);

        btnSolicitar.addActionListener(e -> {
            Item itemSelecionado = (Item) comboItens.getSelectedItem();

            if (itemSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um material.");
                return;
            }

            int quantidade;

            try {
                quantidade = Integer.parseInt(campoQuantidade.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite uma quantidade válida.");
                return;
            }

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero.");
                return;
            }

            if (quantidade > itemSelecionado.getQuantidadeDisponivel()) {
                JOptionPane.showMessageDialog(this, "Quantidade maior que o estoque disponível.");
                return;
            }

            Requerimento req = new Requerimento(
                    Sessao.get().getUsuarioLogado(),
                    itemSelecionado,
                    quantidade
            );

            boolean salvou = RequerimentoRepository.salvar(req);

            if (salvou) {
                JOptionPane.showMessageDialog(this, "Requerimento enviado com sucesso!");
                new TelaRequerimentos();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao enviar requerimento.");
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaRequerimentos();
            dispose();
        });

        add(painel);
        setVisible(true);
    }
}