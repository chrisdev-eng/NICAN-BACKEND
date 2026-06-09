package com.faculdade.nican.view;

import com.faculdade.nican.controller.LoginController;
import com.faculdade.nican.controller.ItemController;
import com.faculdade.nican.controller.RequerimentoController;
import com.faculdade.nican.model.entity.Item;
import com.faculdade.nican.model.entity.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TelaSolicitarMaterial extends JFrame {
    private final LoginController        loginController        = new LoginController();
    private final ItemController         itemController         = new ItemController();
    private final RequerimentoController requerimentoController = new RequerimentoController();

    public TelaSolicitarMaterial() {
        setTitle("Solicitar Material - NICAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(480, 380));
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Solicitar material"), BorderLayout.NORTH);
        raiz.add(NicanTheme.centralizarConteudo(criarCorpo(), 480), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo() {
        List<Item> itens = itemController.listarTodos();
        JComboBox<Item> comboItens = new JComboBox<>(itens.toArray(new Item[0]));
        comboItens.setFont(NicanTheme.fonte(Font.PLAIN, 13));
        comboItens.setBackground(NicanTheme.FUNDO_CAMPO);
        comboItens.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item item)
                    setText(item.getNome() + " — Disponível: " + item.getQuantidadeDisponivel());
                return this;
            }
        });

        JTextField campoQuantidade = NicanTheme.criarCampo();
        JButton btnSolicitar = NicanTheme.criarBotaoPrimario("Solicitar");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        btnSolicitar.addActionListener(e -> {
            Item itemSelecionado = (Item) comboItens.getSelectedItem();
            if (itemSelecionado == null) { NicanDialog.aviso(this, "Selecione um material."); return; }
            int quantidade;
            try { quantidade = Integer.parseInt(campoQuantidade.getText().trim()); }
            catch (NumberFormatException ex) { NicanDialog.aviso(this, "Digite uma quantidade válida."); return; }
            if (quantidade <= 0) { NicanDialog.aviso(this, "A quantidade deve ser maior que zero."); return; }
            if (quantidade > itemSelecionado.getQuantidadeDisponivel()) {
                NicanDialog.aviso(this, "Quantidade maior que o estoque disponível."); return;
            }
            // View acessa o usuário logado via controller — sem tocar Sessao diretamente
            Usuario usuarioLogado = loginController.getUsuarioLogado();
            boolean salvou = usuarioLogado != null
                    && requerimentoController.criarRequerimento(usuarioLogado, itemSelecionado, quantidade);
            if (salvou) {
                NicanDialog.info(this, "Requerimento enviado com sucesso!");
                new TelaRequerimentos(loginController.getLoginLogado());
                dispose();
            } else {
                NicanDialog.erro(this, "Erro ao enviar requerimento.");
            }
        });
        btnVoltar.addActionListener(e -> { new TelaRequerimentos(loginController.getLoginLogado()); dispose(); });

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
