package com.faculdade.nican.ui.menu.view;

import com.faculdade.nican.service.LoginService;
import javax.swing.*;
import java.awt.*;


public class TelaSistemaAdmin extends  JFrame {

    public TelaSistemaAdmin(){
        setTitle("Painel Admin - NICAN");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));

        //titulo com o nome do admin que ta logado
        JLabel titulo = new JLabel("Bem-vindo, " + LoginService.getNomeLogado() + "!");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BOTÕES
        JButton btnAlmoxarifado = new JButton("Almoxarifado");
        JButton btnPainelAdmin = new JButton("Painel Admin");
        JButton btnLogout = new JButton("Logout");

        btnAlmoxarifado.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPainelAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

        //AÇÕES
        btnAlmoxarifado.addActionListener(e -> {
            new TelaAlmoxarife();
            dispose();
        });

        btnPainelAdmin.addActionListener(e -> {
            //futuramente tela de admin, painel admin
            JOptionPane.showMessageDialog(this, "Abrindo Painel Admin...");
        });

        btnLogout.addActionListener(e -> {
            LoginService.fazerLogout();
            JOptionPane.showMessageDialog(this,"logout realizado com sucesso!");
            new TelaHome();
            dispose();
        });

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnAlmoxarifado);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnPainelAdmin);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnLogout);

        add(painel);
        setVisible(true);

    }

}
