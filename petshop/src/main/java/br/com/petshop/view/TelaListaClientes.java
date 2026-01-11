package br.com.petshop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import br.com.petshop.controller.ClienteController;
import br.com.petshop.model.Cliente;

public class TelaListaClientes extends JDialog {
    private JTable tabela;
    private DefaultTableModel model;
    private ClienteController controller = new ClienteController();

    public TelaListaClientes(Frame parent) {
        super(parent, "Gerenciar Clientes", true);
        setSize(850, 500);
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(new String[]{"ID", "CPF", "Nome", "E-mail", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(model);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnEditar = new JButton("✏️ Editar");
        JButton btnExcluir = new JButton("🗑️ Excluir");

        btnEditar.addActionListener(e -> acaoEditar(parent));
        btnExcluir.addActionListener(e -> acaoExcluir());

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDados();
        setLocationRelativeTo(parent);
    }

    private void carregarDados() {
        model.setRowCount(0);
        List<Cliente> lista = controller.listarTodos();
        for (Cliente c : lista) {
            model.addRow(new Object[]{c.getId(), c.getCpf(), c.getNome(), c.getEmail(), c.getTelefone()});
        }
    }

    private void acaoEditar(Frame parent) {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela!");
            return;
        }

        Cliente c = new Cliente();
        c.setId((int) model.getValueAt(linha, 0));
        c.setCpf(model.getValueAt(linha, 1).toString());
        c.setNome(model.getValueAt(linha, 2).toString());
        c.setEmail(model.getValueAt(linha, 3).toString());
        c.setTelefone(model.getValueAt(linha, 4).toString());

        new TelaCadastroCliente(parent, c).setVisible(true);
        carregarDados(); 
    }

    private void acaoExcluir() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
            return;
        }

        String cpf = model.getValueAt(linha, 1).toString();
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Excluir cliente CPF: " + cpf + "?", "Excluir", JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                controller.excluir(cpf);
                JOptionPane.showMessageDialog(this, "Cliente removido!");
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        }
    }
}