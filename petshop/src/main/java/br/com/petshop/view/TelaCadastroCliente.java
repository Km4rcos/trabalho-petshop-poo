package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import br.com.petshop.controller.ClienteController;

public class TelaCadastroCliente extends JDialog {

    private JTextField txtNome, txtCpf, txtEmail, txtTelefone;
    private ClienteController controller;

    public TelaCadastroCliente(Frame parent) {
        super(parent, "Cadastro de Cliente", true);
        this.controller = new ClienteController();
        
        configurarJanela();
        criarFormulario();
        
        pack();
        setLocationRelativeTo(parent);
    }

    private void configurarJanela() {
        setLayout(new BorderLayout(15, 15));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void criarFormulario() {
        JPanel grid = new JPanel(new GridLayout(4, 2, 10, 10));

        grid.add(new JLabel("Nome:"));
        txtNome = new JTextField(25);
        grid.add(txtNome);

        grid.add(new JLabel("CPF:"));
        txtCpf = new JTextField(25);
        grid.add(txtCpf);

        grid.add(new JLabel("E-mail:"));
        txtEmail = new JTextField(25);
        grid.add(txtEmail);

        grid.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField(25);
        grid.add(txtTelefone);

        add(grid, BorderLayout.CENTER);

        JButton btnSalvar = new JButton("💾 Salvar Cliente");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        btnSalvar.addActionListener(e -> {
            try {
                controller.cadastrar(txtNome.getText(), txtEmail.getText(), txtTelefone.getText(), txtCpf.getText());
                JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(btnSalvar, BorderLayout.SOUTH);
    }
}