package br.com.petshop.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import br.com.petshop.controller.ClienteController;
import br.com.petshop.model.Cliente;

public class TelaCadastroCliente extends JDialog {
    private JTextField txtNome, txtEmail, txtTel;
    private JFormattedTextField txtCpf;
    private ClienteController controller = new ClienteController();
    private Cliente clienteExistente = null; // Armazena o cliente caso seja edição

    // Construtor para NOVO cliente
    public TelaCadastroCliente(Frame p) {
        this(p, null);
    }

    // Construtor que aceita um cliente para EDIÇÃO
    public TelaCadastroCliente(Frame p, Cliente cliente) {
        super(p, cliente == null ? "Novo Cliente" : "Editar Cliente", true);
        this.clienteExistente = cliente;
        
        configurarLayout();
        
        // Se for edição, preenche os campos
        if (cliente != null) {
            preencherCampos(cliente);
        }

        pack();
        setLocationRelativeTo(p);
    }

    private void configurarLayout() {
        // Usando um painel com borda para não ficar grudado na janela
        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField(20);
        painel.add(txtNome);

        painel.add(new JLabel("CPF:"));
        try {
            MaskFormatter m = new MaskFormatter("###.###.###-##");
            m.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(m);
        } catch (Exception e) {
            txtCpf = new JFormattedTextField();
        }
        // Se for edição, desabilita o CPF (geralmente não se muda a chave primária)
        if (clienteExistente != null) txtCpf.setEditable(false);
        painel.add(txtCpf);

        painel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        painel.add(new JLabel("Telefone:"));
        txtTel = new JTextField();
        painel.add(txtTel);

        JButton btnSalvar = new JButton(clienteExistente == null ? "Salvar" : "Atualizar");
        btnSalvar.addActionListener(e -> acaoSalvar());
        
        add(painel, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);
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
                // Lógica de Cadastrar
                controller.cadastrar(txtNome.getText(), txtEmail.getText(), txtTel.getText(), txtCpf.getText());
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            } else {
                // Lógica de Atualizar (Você precisará criar o método atualizar no seu ClienteController)
                controller.atualizar(txtNome.getText(), txtEmail.getText(), txtTel.getText(), txtCpf.getText());
                JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}