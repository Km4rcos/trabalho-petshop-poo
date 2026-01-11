package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import br.com.petshop.controller.ServicoController;
import br.com.petshop.model.Pet;
import br.com.petshop.dao.FactoryDAO;

public class TelaAgendamento extends JDialog {
    private JComboBox<Pet> combo = new JComboBox<>();
    private JTextField txtTipo = new JTextField();
    private JTextField txtValor = new JTextField();
    private ServicoController controller = new ServicoController();

    public TelaAgendamento(Frame p) {
        super(p, "Agendar", true);
        setLayout(new GridLayout(4, 2, 10, 10));
        
        for (Pet pet : FactoryDAO.getPetDAO().listarTodos()) combo.addItem(pet);
        combo.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof Pet) setText(((Pet)v).getNome());
                return this;
            }
        });

        add(new JLabel(" Pet:")); add(combo);
        add(new JLabel(" Serviço:")); add(txtTipo);
        add(new JLabel(" Valor:")); add(txtValor);

        JButton btn = new JButton("Confirmar");
        btn.addActionListener(e -> {
            try {
                double v = Double.parseDouble(txtValor.getText().replace(",", "."));
                controller.agendar(txtTipo.getText(), v, ((Pet)combo.getSelectedItem()).getId());
                dispose();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Valor inválido!"); }
        });
        add(btn);
        pack(); setLocationRelativeTo(p);
    }
}