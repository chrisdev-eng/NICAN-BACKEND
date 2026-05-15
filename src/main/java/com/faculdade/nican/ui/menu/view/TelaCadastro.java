package com.faculdade.nican.ui.menu.view;

import javax.swing.*;
import java.awt.*;
import com.faculdade.nican.service.LoginService;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaCadastro extends JFrame {

        public TelaCadastro(){
            setTitle("Criar Conta - NICAN");
            setSize(400,300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            //painel principal
            JPanel painel = new JPanel();
            painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
            painel.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

            //campos
            JLabel lblNome = new JLabel("Nome:");
            JTextField campoNome = new JTextField();

            JLabel lblEmail = new JLabel("Email:");
            JTextField campoEmail = new JTextField();

            JLabel lblSenha = new JLabel("Senha:");
            JPasswordField campoSenha = new JPasswordField();

            JLabel lblConfirma = new JLabel("Confirmar Senha:");
            JPasswordField campoConfirma = new JPasswordField();

            //botões
            JButton btnCadastrar = new JButton("Cadastrar");
            JButton btnVoltar = new JButton("Voltar");
            campoNome.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN ||
                            e.getKeyCode() == KeyEvent.VK_ENTER) {
                        campoEmail.requestFocus();
                    }
                }
            });

            campoEmail.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        campoNome.requestFocus();
                    }

                    if (e.getKeyCode() == KeyEvent.VK_DOWN ||
                            e.getKeyCode() == KeyEvent.VK_ENTER) {
                        campoSenha.requestFocus();
                    }
                }
            });

            campoSenha.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        campoEmail.requestFocus();
                    }

                    if (e.getKeyCode() == KeyEvent.VK_DOWN ||
                            e.getKeyCode() == KeyEvent.VK_ENTER) {
                        campoConfirma.requestFocus();
                    }
                }
            });

            campoConfirma.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        campoSenha.requestFocus();
                    }

                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        btnCadastrar.doClick();
                    }
                }
            });


            //ação do botão cadastrar
            btnCadastrar.addActionListener(e ->{
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String senha = new String(campoSenha.getPassword());
                String confirma = new String(campoConfirma.getPassword());
                String erro = LoginService.cadastrarUsuario(nome, email, senha, confirma);

                if(erro != null){
                    JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                    new TelaLogin();
                    dispose();
                }
            });

            //ação do btn voltar
            btnVoltar.addActionListener(e -> {
                new TelaHome();
                dispose();
            });

            //painel dos campos
            JPanel painelCampos = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();            gbc.insets = new Insets(5,5,5,5);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 0;

            gbc.gridx = 0; gbc.gridy = 0;
            painelCampos.add(lblNome, gbc);
            gbc.gridx = 1; gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            painelCampos.add(campoNome, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            painelCampos.add(lblEmail, gbc);
            gbc.gridx = 1; gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            painelCampos.add(campoEmail, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            painelCampos.add(lblSenha, gbc);
            gbc.gridx = 1; gbc.gridy = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            painelCampos.add(campoSenha, gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            painelCampos.add(lblConfirma, gbc);
            gbc.gridx = 1; gbc.gridy = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            painelCampos.add(campoConfirma, gbc);

            // painel dos botões
            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            painelBotoes.add(btnCadastrar);
            painelBotoes.add(btnVoltar);

            // adiciona tudo no painel principal
            painel.add(painelCampos);
            painel.add(Box.createVerticalStrut(20));
            painel.add(painelBotoes);

            add(painel);
            setVisible(true);
        }
}