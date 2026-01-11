package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import br.com.petshop.controller.ServicoController;
import br.com.petshop.model.Pet;
import br.com.petshop.model.Servico;
import br.com.petshop.dao.FactoryDAO;

public class TelaAgendamento extends JDialog {
    private JComboBox<Pet> comboPet;
    private JComboBox<String> comboServico;
    private JTextField txtValor;
    private ServicoController controller;

    public TelaAgendamento(Frame p, ServicoController controller) {
        super(p, "Agendar Serviço", true);
        this.controller = controller;
        
        setLayout(new BorderLayout());
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Pet:"));
        comboPet = new JComboBox<>();
        for (Pet pet : FactoryDAO.getPetDAO().listarTodos()) comboPet.addItem(pet);
        comboPet.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                if (v instanceof Pet) setText(((Pet)v).getNome());
                return this;
            }
        });
        painel.add(comboPet);

        painel.add(new JLabel("Serviço:"));
        comboServico = new JComboBox<>(new String[]{"Banho", "Tosa","Banho + Tosa", "Consulta", "Vacina"});
        painel.add(comboServico);

        painel.add(new JLabel("Valor R$:"));
        txtValor = new JTextField();
        painel.add(txtValor);

        JButton btn = new JButton("Confirmar Agendamento");
        btn.addActionListener(e -> {
            try {
                Servico s = new Servico();
                s.setPet((Pet) comboPet.getSelectedItem());
                s.setTipo(comboServico.getSelectedItem().toString());
                s.setValor(Double.parseDouble(txtValor.getText().replace(",", ".")));
                
                controller.agendar(s);
                JOptionPane.showMessageDialog(this, "Sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        add(painel, BorderLayout.CENTER);
        add(btn, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(p);
    }
}