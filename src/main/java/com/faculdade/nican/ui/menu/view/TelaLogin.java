package com.faculdade.nican.ui.menu.view;
import javax.swing.*;
import java.awt.*;
import com.faculdade.nican.service.LoginService;

public class TelaLogin extends JFrame{

    public TelaLogin(){
        //configurações da janela
        setTitle("Login - NICAN");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

        //campos
        JLabel lblEmail = new JLabel("E-mail:");
        JTextField campoEmail = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();

        //botões
        JButton btnEntrar = new JButton("Entrar");
        JButton btnVoltar = new JButton("Voltar");

        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoltar.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ação do botão entrar
        btnEntrar.addActionListener(e -> {
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());

            String erro = LoginService.fazerLogin(email, senha);

            if(erro != null){
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                if (LoginService.ehAdmin()){
                    new TelaSistemaAdmin();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Acesso restrito a administradores.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //ação do botão voltar
        btnVoltar.addActionListener(e -> {
            new TelaHome();
            dispose();
        });

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // espaço entre os componentes
        gbc.anchor = GridBagConstraints.WEST;

        // label email - coluna 0, linha 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(lblEmail, gbc);

        // campo email - coluna 1, linha 0
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // ocupa o espaço restante
        painelCampos.add(campoEmail, gbc);

        // label senha - coluna 0, linha 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelCampos.add(lblSenha, gbc);

        // campo senha - coluna 1, linha 1
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCampos.add(campoSenha, gbc);

        //painel dos botões um ao lado do outro
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15,0));
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnVoltar);

        //adicionando tudo no painel principal
        painel.add(painelCampos);
        painel.add(Box.createVerticalStrut(20));
        painel.add(painelBotoes);

        add(painel);
        setVisible(true);
    }
}
