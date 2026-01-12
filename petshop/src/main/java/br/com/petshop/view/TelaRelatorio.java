package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import br.com.petshop.controller.ServicoController;

public class TelaRelatorio extends JDialog {
    
    public TelaRelatorio(Frame parent, ServicoController controller) {
        super(parent, "Relatório Financeiro", true);
        setSize(400, 250);
        setLayout(new BorderLayout());

        double total = controller.obterFaturamentoTotal();

        JPanel painel = new JPanel(new GridLayout(2, 1));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lblTitulo = new JLabel("Faturamento Total (Serviços Finalizados):", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblValor = new JLabel("R$ " + String.format("%.2f", total), SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 36));
        lblValor.setForeground(new Color(39, 174, 96));

        painel.add(lblTitulo);
        painel.add(lblValor);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        add(painel, BorderLayout.CENTER);
        add(btnFechar, BorderLayout.SOUTH);

        setLocationRelativeTo(parent);
    }
}