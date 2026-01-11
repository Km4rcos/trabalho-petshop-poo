package br.com.petshop.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import br.com.petshop.controller.ClienteController;
import br.com.petshop.model.Cliente;

public class TelaCadastroCliente extends JDialog {
    private JTextField txtNome, txtEmail;
    private JFormattedTextField txtCpf, txtTel; // Ambos agora formatados
    private ClienteController controller = new ClienteController();
    private Cliente clienteExistente = null;

    public TelaCadastroCliente(Frame p) {
        this(p, null);
    }

    public TelaCadastroCliente(Frame p, Cliente cliente) {
        super(p, cliente == null ? "Novo Cliente" : "Editar Cliente", true);
        this.clienteExistente = cliente;
        
        configurarLayout();
        
        if (cliente != null) {
            preencherCampos(cliente);
        }

        pack();
        setLocationRelativeTo(p);
    }

    private void configurarLayout() {
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField(20);
        painel.add(txtNome);

        // Máscara CPF
        painel.add(new JLabel("CPF:"));
        txtCpf = criarCampoFormatado("###.###.###-##");
        if (clienteExistente != null) txtCpf.setEditable(false);
        painel.add(txtCpf);

        painel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        // Máscara Telefone (DDD + 9 + 8 dígitos)
        painel.add(new JLabel("Telefone:"));
        txtTel = criarCampoFormatado("(##) #####-####");
        painel.add(txtTel);

        JButton btnSalvar = new JButton(clienteExistente == null ? "Salvar" : "Atualizar");
        btnSalvar.addActionListener(e -> acaoSalvar());
        
        add(painel, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);
    }

    // Método auxiliar para não repetir código de máscara
    private JFormattedTextField criarCampoFormatado(String mascara) {
        try {
            MaskFormatter m = new MaskFormatter(mascara);
            m.setPlaceholderCharacter('_');
            return new JFormattedTextField(m);
        } catch (Exception e) {
            return new JFormattedTextField();
        }
    }

    private void preencherCampos(Cliente c) {
        txtNome.setText(c.getNome());
        txtCpf.setText(c.getCpf());
        txtEmail.setText(c.getEmail());
        txtTel.setText(c.getTelefone());
    }

    private void acaoSalvar() {
        try {
            if (clienteExistente == null) {
                controller.cadastrar(txtNome.getText(), txtEmail.getText(), txtTel.getText(), txtCpf.getText());
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            } else {
                controller.atualizar(txtNome.getText(), txtEmail.getText(), txtTel.getText(), txtCpf.getText());
                JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}