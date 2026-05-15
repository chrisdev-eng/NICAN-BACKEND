package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.model.Requerimento;
import com.faculdade.nican.model.Usuario;
import com.faculdade.nican.repository.RequerimentoRepository;
import com.faculdade.nican.repository.UsuarioRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tela que exibe os requerimentos do usuário logado.
 * Acessada pela TelaHomeUsuario via LoginService.getLoginLogado().
 *
 * CORREÇÃO: RequerimentoRepository não tem listarPorLogin(String).
 * O método existente é listarPorUsuario(Integer id).
 * Buscamos o usuário pelo login primeiro, depois listamos pelo ID.
 */
public class TelaRequerimentos extends JFrame {

    public TelaRequerimentos(String loginUsuario) {
        setTitle("Meus Requerimentos - NICAN");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("Meus Requerimentos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        String[] colunas = {"ID", "Material", "Quantidade", "Data Solicitação", "Status"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabela);

        // CORREÇÃO: busca o usuario pelo login para obter o ID,
        // depois chama listarPorUsuario(id) que é o método que existe no repositório
        List<Requerimento> requerimentos = List.of();
        Usuario usuario = UsuarioRepository.buscarPorLogin(loginUsuario);
        if (usuario != null) {
            requerimentos = RequerimentoRepository.listarPorUsuario(usuario.getId());
        }

        for (Requerimento r : requerimentos) {
            modeloTabela.addRow(new Object[]{
                    r.getIdRequerimento(),
                    r.getItem() != null ? r.getItem().getNome() : "N/A",
                    r.getQuantidadeSolicitada(),
                    r.getDataSolicitacao(),
                    r.getStatus()
            });
        }

        if (requerimentos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Você ainda não possui requerimentos.",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
        }

        JButton btnSolicitar = new JButton("Solicitar Material");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnVoltar    = new JButton("Voltar");

        btnSolicitar.addActionListener(e -> {
            new TelaSolicitarMaterial();
            dispose();
        });

        btnAtualizar.addActionListener(e -> {
            new TelaRequerimentos(loginUsuario);
            dispose();
        });

        btnVoltar.addActionListener(e -> {
            new TelaHomeUsuario();
            dispose();
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelBotoes.add(btnSolicitar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        add(painel);
        setVisible(true);
    }
}