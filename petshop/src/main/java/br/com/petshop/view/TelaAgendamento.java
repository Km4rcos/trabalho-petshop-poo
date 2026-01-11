package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import br.com.petshop.dao.FactoryDAO;
import br.com.petshop.model.Pet;
import br.com.petshop.controller.ServicoController;

public class TelaAgendamento extends JDialog {
    private JComboBox<Pet> comboPets;
    private JTextField txtTipo, txtValor;
    private ServicoController controller;

    public TelaAgendamento(Frame parent) {
        super(parent, "Agendar Serviço", true);
        this.controller = new ServicoController();
        
        setLayout(new GridLayout(4, 2, 10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Selecione o Pet:"));
        comboPets = new JComboBox<>();
        carregarPets();
        add(comboPets);

        add(new JLabel("Tipo (Banho/Tosa):"));
        txtTipo = new JTextField();
        add(txtTipo);

        add(new JLabel("Valor R$:"));
        txtValor = new JTextField();
        add(txtValor);

        JButton btnConfirmar = new JButton("Confirmar Agendamento");
        btnConfirmar.addActionListener(e -> agendar());
        add(btnConfirmar);

        pack();
        setLocationRelativeTo(parent);
    }

    private void carregarPets() {
        List<Pet> pets = FactoryDAO.getPetDAO().listarTodos();
        for (Pet p : pets) comboPets.addItem(p);
        
        comboPets.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Pet) setText(((Pet) value).getNome());
                return this;
            }
        });
    }

    private void agendar() {
        Pet selecionado = (Pet) comboPets.getSelectedItem();
        double valor = Double.parseDouble(txtValor.getText());
        controller.agendar(txtTipo.getText(), valor, selecionado.getId());
        JOptionPane.showMessageDialog(this, "Agendado!");
        dispose();
    }
}