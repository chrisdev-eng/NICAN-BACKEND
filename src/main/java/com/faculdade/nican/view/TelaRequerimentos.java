package com.faculdade.nican.view;

<<<<<<< Updated upstream
import com.faculdade.nican.model.RequerimentoService;
import com.faculdade.nican.model.Requerimento;
import com.faculdade.nican.model.Usuario;
import com.faculdade.nican.model.UsuarioService;
=======
import com.faculdade.nican.controller.RequerimentoController;
import com.faculdade.nican.model.entity.Requerimento;
import com.faculdade.nican.model.entity.Usuario;
import com.faculdade.nican.controller.UsuarioController;
>>>>>>> Stashed changes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
<<<<<<< Updated upstream
=======
    private final RequerimentoController requerimentoController = new RequerimentoController();
    private final UsuarioController usuarioController = new UsuarioController();
>>>>>>> Stashed changes
    

    public TelaRequerimentos(String loginUsuario) {
        setTitle("Meus Requerimentos - NICAN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(860, 520));
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(NicanTheme.FUNDO);
        raiz.add(NicanTheme.criarHeader("Meus requerimentos"), BorderLayout.NORTH);
        raiz.add(criarCorpo(loginUsuario), BorderLayout.CENTER);
        raiz.add(NicanTheme.criarRodape(), BorderLayout.SOUTH);

        add(raiz);
        setVisible(true);
    }

    private JPanel criarCorpo(String loginUsuario) {
        // ── lógica original ───────────────────────────────────────────────────
        String[] colunas = {"ID","Material","Quantidade","Data Solicitação","Status"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        JTable tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = NicanTheme.criarScrollTabela(tabela);

<<<<<<< Updated upstream
        Usuario usuario = UsuarioService.buscarPorLogin(loginUsuario);
        List<Requerimento> requerimentos = usuario == null
                ? List.of()
                : RequerimentoService.listarPorUsuario(usuario.getId());
=======
        Usuario usuario = usuarioController.buscarPorLogin(loginUsuario);
        List<Requerimento> requerimentos = usuario == null
                ? List.of()
                : requerimentoController.listarPorUsuario(usuario.getId());
>>>>>>> Stashed changes

        for (Requerimento r : requerimentos)
            modeloTabela.addRow(new Object[]{r.getIdRequerimento(), r.getItem()!=null?r.getItem().getNome():"N/A", r.getQuantidadeSolicitada(), r.getDataSolicitacao(), r.getStatus()});

        if (requerimentos.isEmpty())
            NicanDialog.info(this, "Você ainda não possui requerimentos.");

        JButton btnSolicitar = NicanTheme.criarBotaoPrimario("Solicitar Material");
        JButton btnAtualizar = NicanTheme.criarBotaoSecundario("Atualizar");
        JButton btnVoltar    = NicanTheme.criarBotaoSecundario("Voltar");

        btnSolicitar.addActionListener(e -> { new TelaSolicitarMaterial(); dispose(); });
        btnAtualizar.addActionListener(e -> { new TelaRequerimentos(loginUsuario); dispose(); });
        btnVoltar.addActionListener(e -> { new TelaHomeUsuario(); dispose(); });

        // ── layout visual ─────────────────────────────────────────────────────
        JPanel corpo = new JPanel(new BorderLayout(0, 12));
        corpo.setBackground(NicanTheme.FUNDO);
        corpo.setBorder(new EmptyBorder(20, 24, 16, 24));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(NicanTheme.FUNDO);
        topo.add(NicanTheme.criarCabecalhoSecao("Meus Requerimentos"), BorderLayout.WEST);

        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        sul.setBackground(NicanTheme.FUNDO);
        sul.add(btnSolicitar);
        sul.add(btnAtualizar);
        sul.add(btnVoltar);

        corpo.add(topo, BorderLayout.NORTH);
        corpo.add(scroll, BorderLayout.CENTER);
        corpo.add(sul, BorderLayout.SOUTH);
        return corpo;
    }
}
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
