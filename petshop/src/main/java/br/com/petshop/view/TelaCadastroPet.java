package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import br.com.petshop.dao.FactoryDAO;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Pet;

public class TelaCadastroPet extends JDialog {

    private JTextField txtNome, txtEspecie, txtRaca;
    private JComboBox<Cliente> comboDono;

    public TelaCadastroPet(Frame parent) {
        super(parent, "Cadastrar Novo Pet", true);
        configurarJanela();
        criarFormulario();
        carregarClientes();
        
        pack();
        setLocationRelativeTo(parent);
    }

    private void configurarJanela() {
        setLayout(new BorderLayout(15, 15));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void criarFormulario() {
        JPanel grid = new JPanel(new GridLayout(4, 2, 10, 10));

        grid.add(new JLabel("Nome do Pet:"));
        txtNome = new JTextField(20);
        grid.add(txtNome);

        grid.add(new JLabel("Espécie:"));
        txtEspecie = new JTextField(20);
        grid.add(txtEspecie);

        grid.add(new JLabel("Raça:"));
        txtRaca = new JTextField(20);
        grid.add(txtRaca);

        grid.add(new JLabel("Dono (Cliente):"));
        comboDono = new JComboBox<>();

        comboDono.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    setText(((Cliente) value).getNome());
                }
                return this;
            }
        });
        grid.add(comboDono);

        add(grid, BorderLayout.CENTER);

        JButton btnSalvar = new JButton("🐾 Salvar Pet");
        btnSalvar.addActionListener(e -> acaoSalvar());
        add(btnSalvar, BorderLayout.SOUTH);
    }

    private void carregarClientes() {
        List<Cliente> clientes = FactoryDAO.getClienteDAO().listarTodos();
        for (Cliente c : clientes) {
            comboDono.addItem(c);
        }
    }

    private void acaoSalvar() {
        try {
            Cliente donoSelecionado = (Cliente) comboDono.getSelectedItem();
            if (donoSelecionado == null) throw new Exception("Selecione um dono!");

            Pet novoPet = new Pet(txtNome.getText(), txtEspecie.getText(), txtRaca.getText(), donoSelecionado);
            FactoryDAO.getPetDAO().salvar(novoPet);

            JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
}