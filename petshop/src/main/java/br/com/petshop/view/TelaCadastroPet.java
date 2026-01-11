package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import br.com.petshop.controller.PetController;
import br.com.petshop.model.Pet;
import br.com.petshop.model.Cliente;
import br.com.petshop.dao.FactoryDAO;

public class TelaCadastroPet extends JDialog {
    private JTextField txtNome, txtRaca, txtOutraEspecie;
    private JComboBox<String> comboEspecie;
    private JComboBox<Cliente> comboDono;
    private PetController controller = new PetController();
    private Pet petEdicao = null;

    public TelaCadastroPet(Frame p, Pet pet) {
        super(p, pet == null ? "Novo Pet" : "Editar Pet", true);
        this.petEdicao = pet;
        configurarLayout();
        if (pet != null) preencherCampos();
        pack();
        setLocationRelativeTo(p);
    }

    private void configurarLayout() {
        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Nome do Pet:"));
        txtNome = new JTextField(20);
        painel.add(txtNome);

        painel.add(new JLabel("Espécie:"));
        String[] opcoes = {"Cão", "Gato", "Outro"};
        comboEspecie = new JComboBox<>(opcoes);
        painel.add(comboEspecie);

        painel.add(new JLabel("Qual espécie? (Se outro):"));
        txtOutraEspecie = new JTextField();
        txtOutraEspecie.setEnabled(false); // Começa desativado
        painel.add(txtOutraEspecie);

        // Lógica para habilitar/desabilitar o campo "Outro"
        comboEspecie.addActionListener(e -> {
            txtOutraEspecie.setEnabled(comboEspecie.getSelectedItem().equals("Outro"));
        });

        painel.add(new JLabel("Raça:"));
        txtRaca = new JTextField();
        painel.add(txtRaca);

        painel.add(new JLabel("Dono:"));
        comboDono = new JComboBox<>();
        for (Cliente c : FactoryDAO.getClienteDAO().listarTodos()) comboDono.addItem(c);
        // Renderizador para mostrar nome do cliente no combo
        comboDono.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof Cliente) setText(((Cliente)v).getNome());
                return this;
            }
        });
        if (petEdicao != null) comboDono.setEnabled(false); // Não muda o dono na edição aqui
        painel.add(comboDono);

        JButton btnSalvar = new JButton(petEdicao == null ? "Salvar" : "Atualizar");
        btnSalvar.addActionListener(e -> acaoSalvar());

        add(painel, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);
    }

    private void preencherCampos() {
        txtNome.setText(petEdicao.getNome());
        txtRaca.setText(petEdicao.getRaca());
        if (petEdicao.getEspecie().equals("Cão") || petEdicao.getEspecie().equals("Gato")) {
            comboEspecie.setSelectedItem(petEdicao.getEspecie());
        } else {
            comboEspecie.setSelectedItem("Outro");
            txtOutraEspecie.setEnabled(true);
            txtOutraEspecie.setText(petEdicao.getEspecie());
        }
    }

    private void acaoSalvar() {
        try {
            String especieFinal = comboEspecie.getSelectedItem().toString();
            if (especieFinal.equals("Outro")) especieFinal = txtOutraEspecie.getText();

            if (petEdicao == null) {
                Cliente dono = (Cliente) comboDono.getSelectedItem();
                controller.cadastrar(txtNome.getText(), especieFinal, txtRaca.getText(), dono.getId());
            } else {
                controller.atualizar(petEdicao.getId(), txtNome.getText(), especieFinal, txtRaca.getText());
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}