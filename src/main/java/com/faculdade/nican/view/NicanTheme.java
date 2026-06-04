package com.faculdade.nican.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Utilitário de tema visual Nican Mopohua.
 * Centraliza cores, fontes e componentes estilizados.
 * NÃO contém lógica de negócio.
 */
public class NicanTheme {

    // ── Paleta ───────────────────────────────────────────────────────────────
    public static final Color VERDE_ESCURO   = new Color(0x3D4A3D);
    public static final Color VERDE_HOVER    = new Color(0x4F5F4F);
    public static final Color VERDE_BORDA    = new Color(0x5A6E5A);
    public static final Color VERDE_CLARO    = new Color(0x8BA888);
    public static final Color FUNDO          = new Color(0xF4F5F0);
    public static final Color FUNDO_CAMPO    = Color.WHITE;
    public static final Color TEXTO_ESCURO   = new Color(0x2B332B);
    public static final Color TEXTO_MUTED    = new Color(0x6B7A6B);
    public static final Color BORDA_CAMPO    = new Color(0xBFC9BF);
    public static final Color VERMELHO       = new Color(0x8B2020);
    public static final Color VERMELHO_BD    = new Color(0xC07070);
    public static final Color LINHA_TABELA   = new Color(0xE8EDE8);
    public static final Color SEL_TABELA     = new Color(0xD0DDD0);

    // ── Fontes ───────────────────────────────────────────────────────────────
    public static Font fonte(int estilo, int tamanho) {
        return new Font("Dialog", estilo, tamanho);
    }
    public static Font fonteSerif(int estilo, int tamanho) {
        return new Font("Georgia", estilo, tamanho);
    }

    public static void configurarJanela(JFrame janela, String titulo, int larguraMinima, int alturaMinima) {
        janela.setTitle(titulo);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setMinimumSize(new Dimension(larguraMinima, alturaMinima));
        janela.setResizable(true);
        janela.setLocationRelativeTo(null);
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static JPanel centralizarConteudo(JPanel conteudo, int larguraMaxima) {
        Dimension pref = conteudo.getPreferredSize();
        conteudo.setPreferredSize(new Dimension(larguraMaxima, Math.max(pref.height, 1)));
        conteudo.setMaximumSize(new Dimension(larguraMaxima, Integer.MAX_VALUE));

        JPanel area = new JPanel(new GridBagLayout());
        area.setBackground(FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 1;
        area.add(conteudo, gbc);
        return area;
    }

    // ── Header ───────────────────────────────────────────────────────────────
    public static JPanel criarHeader(String subtitulo) {
        JPanel h = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 12));
        h.setBackground(VERDE_ESCURO);
        h.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, VERDE_BORDA));
        h.add(new LogoPanel(44, 44));
        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setBackground(VERDE_ESCURO);
        JLabel nome = new JLabel("Nican Mopohua");
        nome.setFont(fonteSerif(Font.BOLD, 16));
        nome.setForeground(FUNDO);
        JLabel sub = new JLabel(subtitulo.toUpperCase());
        sub.setFont(fonte(Font.PLAIN, 10));
        sub.setForeground(VERDE_CLARO);
        txt.add(nome);
        txt.add(Box.createVerticalStrut(2));
        txt.add(sub);
        h.add(txt);
        return h;
    }

    // ── Rodapé ────────────────────────────────────────────────────────────────
    public static JPanel criarRodape() {
        JPanel r = new JPanel(new FlowLayout(FlowLayout.CENTER));
        r.setBackground(FUNDO);
        r.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0xDDE0DD)));
        JLabel txt = new JLabel("© Nican Mopohua — Sistema de Gestão Escoteira");
        txt.setFont(fonte(Font.PLAIN, 10));
        txt.setForeground(new Color(0xAAB2AA));
        r.add(txt);
        return r;
    }

    // ── Título de seção ───────────────────────────────────────────────────────
    public static JPanel criarCabecalhoSecao(String titulo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(FUNDO);
        p.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(fonteSerif(Font.PLAIN, 20));
        lbl.setForeground(TEXTO_ESCURO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel div = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(VERDE_CLARO);
                g.fillRect(0, 0, 48, 2);
            }
        };
        div.setOpaque(false);
        div.setPreferredSize(new Dimension(200, 8));
        div.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        div.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(lbl);
        p.add(div);
        return p;
    }

    // ── Label de campo ────────────────────────────────────────────────────────
    public static JLabel criarLabel(String texto) {
        JLabel l = new JLabel(texto.toUpperCase());
        l.setFont(fonte(Font.BOLD, 10));
        l.setForeground(TEXTO_MUTED);
        return l;
    }

    // ── TextField estilizado ──────────────────────────────────────────────────
    public static JTextField criarCampo() {
        JTextField f = new JTextField();
        estilizarCampo(f);
        return f;
    }

    public static JPasswordField criarCampoSenha() {
        JPasswordField f = new JPasswordField();
        estilizarCampo(f);
        return f;
    }

    public static JComboBox<String> criarCombo(String[] opcoes) {
        JComboBox<String> c = new JComboBox<>(opcoes);
        c.setFont(fonte(Font.PLAIN, 13));
        c.setBackground(FUNDO_CAMPO);
        c.setForeground(TEXTO_ESCURO);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CAMPO, 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        return c;
    }

    private static void estilizarCampo(JTextField f) {
        f.setFont(fonte(Font.PLAIN, 13));
        f.setForeground(TEXTO_ESCURO);
        f.setBackground(FUNDO_CAMPO);
        f.setCaretColor(VERDE_ESCURO);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA_CAMPO, 1),
                BorderFactory.createEmptyBorder(6, 9, 6, 9)));
    }

    // ── Botão primário ────────────────────────────────────────────────────────
    public static JButton criarBotaoPrimario(String texto) {
        JButton b = new JButton(texto) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? VERDE_HOVER : VERDE_ESCURO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(fonte(Font.BOLD, 12));
        b.setForeground(FUNDO);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(b.getPreferredSize().width + 24, 36));
        return b;
    }

    // ── Botão secundário ──────────────────────────────────────────────────────
    public static JButton criarBotaoSecundario(String texto) {
        JButton b = new JButton(texto) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0xEEF0EE) : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(BORDA_CAMPO);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(fonte(Font.PLAIN, 12));
        b.setForeground(VERDE_ESCURO);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(b.getPreferredSize().width + 24, 36));
        return b;
    }

    // ── Botão perigo ──────────────────────────────────────────────────────────
    public static JButton criarBotaoPerigo(String texto) {
        JButton b = new JButton(texto) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(0xFDF0F0) : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(VERMELHO_BD);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(fonte(Font.PLAIN, 12));
        b.setForeground(VERMELHO);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(b.getPreferredSize().width + 24, 36));
        return b;
    }

    // ── Estilizar JTable ──────────────────────────────────────────────────────
    public static void estilizarTabela(JTable tabela) {
        tabela.setFont(fonte(Font.PLAIN, 13));
        tabela.setForeground(TEXTO_ESCURO);
        tabela.setBackground(Color.WHITE);
        tabela.setRowHeight(28);
        tabela.setGridColor(LINHA_TABELA);
        tabela.setSelectionBackground(SEL_TABELA);
        tabela.setSelectionForeground(TEXTO_ESCURO);
        tabela.setShowHorizontalLines(true);
        tabela.setShowVerticalLines(false);
        tabela.setFillsViewportHeight(true);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(fonte(Font.BOLD, 11));
        header.setBackground(VERDE_ESCURO);
        header.setForeground(FUNDO);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static JScrollPane criarScrollTabela(JTable tabela) {
        estilizarTabela(tabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(BORDA_CAMPO, 1));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    // ── Logo Panel ────────────────────────────────────────────────────────────
    public static class LogoPanel extends JPanel {
        private final int w, h;
        public LogoPanel(int w, int h) {
            this.w = w; this.h = h;
            setPreferredSize(new Dimension(w, h));
            setOpaque(false);
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.scale((double) w / 500.0, (double) h / 500.0);
            g2.setColor(FUNDO);
            Path2D p1 = new Path2D.Double();
            p1.moveTo(189.582,440.666); p1.curveTo(189.01,440.185,188.439,439.704,187.865,439.226);
            p1.curveTo(168.143,421.869,151.234,402.89,138.443,380.515); p1.curveTo(130.881,367.287,126.599,352.838,123.775,337.931);
            p1.curveTo(122.562,331.531,121.935,325.096,122.005,318.56); p1.curveTo(122.066,312.89,122.049,307.218,122.032,301.546);
            p1.curveTo(122.007,293.184,121.981,284.822,122.206,276.467); p1.curveTo(122.76,255.846,123.376,235.212,124.734,214.635);
            p1.curveTo(124.897,212.158,125.054,209.679,125.21,207.199); p1.curveTo(125.681,199.753,126.151,192.298,126.809,184.861);
            p1.curveTo(127.451,177.607,131.592,174.625,137.406,172.742); p1.curveTo(152.274,167.926,167.427,163.976,182.764,161.202);
            p1.curveTo(200.126,158.062,217.579,155.237,235.353,154.817); p1.curveTo(237.76,154.76,240.173,154.651,242.588,154.542);
            p1.curveTo(246.679,154.358,250.777,154.173,254.866,154.24); p1.curveTo(274.798,154.563,294.57,156.547,314.14,160.434);
            p1.curveTo(330.155,163.615,346.005,167.436,361.548,172.467); p1.curveTo(370.382,175.327,372.855,178.584,373.286,187.813);
            p1.curveTo(373.464,191.636,373.625,195.461,373.785,199.286); p1.curveTo(374.135,207.629,374.485,215.973,375.024,224.304);
            p1.curveTo(375.543,232.333,375.914,240.37,376.285,248.406); p1.curveTo(376.693,257.234,377.101,266.06,377.704,274.869);
            p1.curveTo(378.215,282.321,378.405,289.782,378.594,297.23); p1.curveTo(378.675,300.432,378.757,303.632,378.863,306.828);
            p1.curveTo(379.668,330.934,375.145,354.285,364.112,375.932); p1.curveTo(357.753,388.41,349.908,400.029,340.441,410.459);
            p1.curveTo(338.835,412.229,337.261,414.032,335.688,415.835); p1.curveTo(332.716,419.239,329.744,422.643,326.552,425.826);
            p1.curveTo(314.073,438.269,300.252,449.109,285.898,459.311); p1.curveTo(276.31,466.127,266.215,472.084,256.073,478.002);
            p1.curveTo(252.148,480.293,248.708,480.819,244.468,478.494); p1.curveTo(226.297,468.53,209.481,456.615,193.302,443.742);
            p1.curveTo(192.043,442.74,190.812,441.703,189.582,440.666);
            p1.moveTo(137.048,92.6702); p1.curveTo(135.979,89.2376,134.909,85.805,133.832,82.3751);
            p1.curveTo(133.654,81.5968,133.368,80.8768,133.097,80.1924); p1.curveTo(132.396,78.4236,131.789,76.8921,133.374,75.2038);
            p1.curveTo(135.538,72.8977,138.056,72.7507,140.962,73.3951); p1.curveTo(146.918,74.7161,152.839,76.1228,158.618,78.1103);
            p1.curveTo(163.89,79.9235,168.113,83.1353,170.056,88.3282); p1.curveTo(176.72,106.138,205.339,109.092,216.44,93.7666);
            p1.curveTo(221.184,87.2183,221.856,79.6836,219.824,72.3347); p1.curveTo(218.169,66.3452,218.143,61.1662,221.167,55.7032);
            p1.curveTo(227.631,44.0235,234.413,32.6366,244.292,23.3918); p1.curveTo(248.954,19.0282,251.485,18.7418,255.909,23.3096);
            p1.curveTo(266.528,34.2737,274.847,46.7509,280.626,60.9458); p1.curveTo(282.181,64.7661,281.273,68.2833,280.373,71.7742);
            p1.curveTo(280.014,73.1626,279.657,74.5468,279.456,75.9442); p1.curveTo(277.271,91.158,288.748,104.208,304.001,103.473);
            p1.curveTo(309.88,103.19,315.574,102.575,320.776,99.4841); p1.curveTo(325.038,96.9518,327.802,93.2307,329.638,88.7788);
            p1.curveTo(331.921,83.2422,335.952,79.7602,341.621,78.1048); p1.curveTo(343.302,77.6138,344.968,77.0558,346.634,76.4976);
            p1.curveTo(351.27,74.9441,355.908,73.3902,360.895,73.2802); p1.curveTo(365.371,73.1814,367.438,75.6803,366.134,79.8899);
            p1.curveTo(360.07,99.4738,353.968,119.047,347.73,138.576); p1.curveTo(345.973,144.075,342.763,145.7,337.279,144.203);
            p1.curveTo(322.188,140.083,306.815,137.28,291.331,135.452); p1.curveTo(271.447,133.105,251.502,131.908,231.394,133.343);
            p1.curveTo(212.589,134.686,193.978,136.919,175.601,141.08); p1.curveTo(171.425,142.025,167.261,143.062,163.16,144.289);
            p1.curveTo(157.78,145.899,154.875,144.887,152.441,139.672); p1.curveTo(150.032,134.51,148.407,129.065,146.783,123.622);
            p1.curveTo(146.107,121.358,145.432,119.094,144.7,116.851); p1.curveTo(142.078,108.814,139.563,100.742,137.048,92.6702);
            p1.moveTo(284.71,422.981); p1.curveTo(300.633,410.548,315.256,396.921,327.341,380.285);
            p1.curveTo(331.342,375.597,334.183,370.524,336.945,365.387); p1.curveTo(345.623,349.25,350.752,332.19,350.432,313.717);
            p1.curveTo(350.391,311.358,350.372,308.999,350.354,306.641); p1.curveTo(350.304,300.413,350.255,294.186,349.799,287.942);
            p1.curveTo(349.186,279.541,349.074,271.104,348.963,262.667); p1.curveTo(348.915,259.041,348.867,255.415,348.779,251.792);
            p1.curveTo(348.759,250.959,348.734,250.126,348.692,249.294); p1.curveTo(348.472,245.007,348.241,240.72,348.01,236.433);
            p1.curveTo(347.409,225.265,346.807,214.097,346.406,202.921); p1.curveTo(346.255,198.714,344.746,196.47,340.816,195.103);
            p1.curveTo(331.188,191.755,321.239,189.729,311.305,187.706); p1.curveTo(309.03,187.242,306.756,186.779,304.486,186.3);
            p1.curveTo(289.874,183.218,274.925,182.434,260.03,181.934); p1.curveTo(235.31,181.104,210.834,183.491,186.636,188.621);
            p1.curveTo(177.702,190.515,168.831,192.685,160.135,195.485); p1.curveTo(160.075,195.504,160.014,195.523,159.953,195.543);
            p1.curveTo(157.871,196.211,155.697,196.908,155.547,200.008); p1.curveTo(155.283,205.443,154.94,210.876,154.596,216.309);
            p1.curveTo(154.006,225.648,153.416,234.986,153.23,244.333); p1.curveTo(153.145,248.611,152.912,252.884,152.679,257.158);
            p1.curveTo(152.279,264.51,151.878,271.862,152.23,279.236); p1.curveTo(152.304,280.785,152.17,282.343,152.036,283.901);
            p1.curveTo(151.967,284.697,151.899,285.493,151.858,286.289); p1.curveTo(151.103,300.978,150.57,315.711,152.621,330.297);
            p1.curveTo(154.726,345.271,160.301,359.049,168.37,371.937); p1.curveTo(179.399,389.553,193.89,403.974,209.49,417.325);
            p1.curveTo(220.644,426.872,232.792,435.147,245.384,442.768); p1.curveTo(249.054,444.988,251.963,445.371,255.653,443.031);
            p1.curveTo(265.603,436.723,275.395,430.254,284.71,422.981);
            g2.fill(p1);
            Path2D p2 = new Path2D.Double();
            p2.moveTo(216.004,382.726); p2.curveTo(219.427,383.601,222.956,382.744,226.554,382.754);
            p2.curveTo(224.271,391.428,224.298,391.615,231.823,396.073); p2.curveTo(235.406,398.195,237.548,401.517,240.055,404.671);
            p2.curveTo(247.017,413.427,251.385,414.97,258.905,405.649); p2.curveTo(259.252,405.219,259.613,404.793,259.901,404.324);
            p2.curveTo(262.587,399.955,266.603,397.087,270.814,394.362); p2.curveTo(274.653,391.88,275.329,389.046,273.469,384.832);
            p2.curveTo(273.193,384.207,272.85,383.573,273.731,382.574); p2.curveTo(277.957,383.528,282.463,383.449,286.955,382.558);
            p2.curveTo(287.935,382.364,288.954,382.243,289.266,381.126); p2.curveTo(289.597,379.941,289.145,378.921,288.188,378.189);
            p2.curveTo(287.308,377.516,286.367,376.921,285.441,376.309); p2.curveTo(278.599,371.79,273.795,365.866,272.289,357.646);
            p2.curveTo(283.593,351.082,283.932,346.761,273.952,337.788); p2.curveTo(272.749,336.706,273.067,335.635,273.366,334.412);
            p2.curveTo(274.827,328.443,278.768,325.313,286.042,325.291); p2.curveTo(295.16,325.264,300.859,330.548,298.879,340.548);
            p2.curveTo(298.508,342.418,298.226,344.303,298.664,346.396); p2.curveTo(305.682,347.279,311.159,344.256,316.435,340.524);
            p2.curveTo(325.058,334.425,329.72,321.938,327.641,311.921); p2.curveTo(324.022,294.481,303.625,282.354,285.096,290.349);
            p2.curveTo(281.653,291.835,278.436,293.945,274.745,295.152); p2.curveTo(274.175,293.027,275.413,291.896,276.036,290.571);
            p2.curveTo(281.864,278.185,283.973,265.189,281.089,251.799); p2.curveTo(277.52,235.234,267.819,222.143,255.91,210.576);
            p2.curveTo(250.84,205.65,248.434,205.893,243.276,210.812); p2.curveTo(237.431,216.387,232.636,222.774,228.408,229.615);
            p2.curveTo(217.192,247.761,213.631,266.708,222.409,286.954); p2.curveTo(223.53,289.541,224.527,292.182,226.025,295.899);
            p2.curveTo(222.008,294.009,219.035,292.66,216.105,291.223); p2.curveTo(206.543,286.533,197.08,286.729,187.68,291.689);
            p2.curveTo(184.39,293.425,182.405,296.553,179.766,298.982); p2.curveTo(174.056,304.238,171.788,311.18,172.016,318.39);
            p2.curveTo(172.423,331.226,182.712,343.461,195.424,346.194); p2.curveTo(201.357,347.47,202.046,346.768,200.812,340.897);
            p2.curveTo(199.787,336.021,200.275,331.179,204.461,328.024); p2.curveTo(209.003,324.601,214.457,324.88,219.578,326.674);
            p2.curveTo(224.471,328.389,225.941,332.668,226.287,337.423); p2.curveTo(215.54,343.427,215.805,348.815,227.284,357.629);
            p2.curveTo(226.475,362.225,224.374,366.221,221.229,369.664); p2.curveTo(218.427,372.73,215.378,375.542,211.948,377.906);
            p2.curveTo(210.851,378.662,209.963,379.509,210.237,380.953); p2.curveTo(210.551,382.609,211.949,382.522,213.189,382.611);
            p2.curveTo(213.881,382.661,214.577,382.662,216.004,382.726);
            g2.fill(p2);
            g2.dispose();
        }
    }
}
