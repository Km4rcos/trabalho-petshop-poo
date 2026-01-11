package br.com.petshop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import br.com.petshop.controller.PetController;
import br.com.petshop.model.Pet;

public class TelaListaPets extends JDialog {
    private JTable tabela;
    private DefaultTableModel model;
    private PetController controller = new PetController();

    public TelaListaPets(Frame p) {
        super(p, "Gerenciar Pets", true);
        setSize(800, 400);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Nome", "Espécie", "Raça", "Dono"}, 0);
        tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnEdit = new JButton("✏️ Editar Pet");
        JButton btnDel = new JButton("🗑️ Excluir Pet");

        btnEdit.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                Pet pet = controller.listarTodos().stream().filter(x -> x.getId() == id).findFirst().get();
                new TelaCadastroPet(p, pet).setVisible(true);
                carregar();
            }
        });

        btnDel.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                if (JOptionPane.showConfirmDialog(this, "Excluir este pet?") == 0) {
                    controller.excluir(id);
                    carregar();
                }
            }
        });

        botoes.add(btnEdit); botoes.add(btnDel);
        add(botoes, BorderLayout.SOUTH);
        carregar();
        setLocationRelativeTo(p);
    }

    private void carregar() {
        model.setRowCount(0);
        for (Pet pet : controller.listarTodos()) {
            model.addRow(new Object[]{pet.getId(), pet.getNome(), pet.getEspecie(), pet.getRaca(), pet.getDono().getNome()});
        }
    }
}