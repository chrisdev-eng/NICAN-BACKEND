package com.faculdade.nican.ui.menu.view;
 
import com.faculdade.nican.service.LoginService;
import com.faculdade.nican.ui.menu.view.*;
import javax.swing.*;
import java.awt.*;
 
/**
 * Menu principal do Administrador.
 *
 */
public class TelaSistemaAdmin extends JFrame {
 
    public TelaSistemaAdmin() {
        setTitle("Painel Admin - NICAN");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
 
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
 
        // título com o nome do admin logado
        JLabel titulo = new JLabel("Bem-vindo, " + LoginService.getNomeLogado() + "!");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
 
        // botões
        JButton btnAlmoxarifado        = new JButton("Almoxarifado");
        JButton btnValidarRequerimentos = new JButton("Validar Requerimentos");
        JButton btnPainelAdmin         = new JButton("Painel Admin");
        JButton btnLogout              = new JButton("Logout");
 
        btnAlmoxarifado.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnValidarRequerimentos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPainelAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
 
        // ações
        btnAlmoxarifado.addActionListener(e -> {
            new TelaAlmoxarife();
            dispose();
        });
 
        btnValidarRequerimentos.addActionListener(e -> {
            new TelaValidarRequerimento();
            dispose();
        });
 
        btnPainelAdmin.addActionListener(e -> {
            new TelaPainelAdmin();
            dispose();
        });
 
        btnLogout.addActionListener(e -> {
            LoginService.fazerLogout();
            JOptionPane.showMessageDialog(this, "Logout realizado com sucesso!");
            new TelaHome();
            dispose();
        });
 
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(30));
        painel.add(btnAlmoxarifado);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnValidarRequerimentos);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnPainelAdmin);
        painel.add(Box.createVerticalStrut(10));
        painel.add(btnLogout);
 
        add(painel);
        setVisible(true);
    }
}
