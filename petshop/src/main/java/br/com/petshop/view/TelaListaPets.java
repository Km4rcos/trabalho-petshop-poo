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
        setSize(850, 450);
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Nome", "Espécie", "Raça", "Dono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabela = new JTable(model);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(25);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEdit = new JButton("✏️ Editar Pet");
        JButton btnDel = new JButton("🗑️ Excluir Pet");

        btnEdit.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row != -1) {
                try {
                    int id = (int) model.getValueAt(row, 0);
                    Pet pet = controller.buscarPorId(id);
                    
                    new TelaCadastroPet(p, pet).setVisible(true);
                    carregar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao abrir edição: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um pet na tabela para editar.");
            }
        });

        btnDel.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                String nome = model.getValueAt(row, 1).toString();
                
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o pet: " + nome + "?", "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.excluir(id);
                        JOptionPane.showMessageDialog(this, "Pet excluído com sucesso!");
                        carregar();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
                    }
                }
            }
        });

        botoes.add(btnEdit); 
        botoes.add(btnDel);
        add(botoes, BorderLayout.SOUTH);

        carregar();
        setLocationRelativeTo(p);
    }

    private void carregar() {
        model.setRowCount(0);
        for (Pet pet : controller.listarTodos()) {
            model.addRow(new Object[]{
                pet.getId(),
                pet.getNome(),
                pet.getEspecie(),
                pet.getRaca(),
                pet.getDono().getNome()
            });
        }
    }
}