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
        setSize(800, 500);
        setLayout(new BorderLayout(10, 10));

        // Tabela
        model = new DefaultTableModel(new String[]{"ID", "CPF", "Nome", "E-mail", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        JButton btnEditar = new JButton("✏️ Editar Selecionado");
        JButton btnExcluir = new JButton("🗑️ Excluir Selecionado");

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

        // Criamos um objeto cliente com os dados da linha selecionada
        Cliente c = new Cliente();
        c.setId((int) model.getValueAt(linha, 0));
        c.setCpf(model.getValueAt(linha, 1).toString());
        c.setNome(model.getValueAt(linha, 2).toString());
        c.setEmail(model.getValueAt(linha, 3).toString());
        c.setTelefone(model.getValueAt(linha, 4).toString());

        // Abre a tela de cadastro em modo EDIÇÃO
        new TelaCadastroCliente(parent, c).setVisible(true);
        carregarDados(); // Atualiza a lista após voltar
    }

    private void acaoExcluir() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!");
            return;
        }

        String cpf = model.getValueAt(linha, 1).toString();
        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o cliente com CPF " + cpf + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                controller.excluir(cpf);
                JOptionPane.showMessageDialog(this, "Cliente removido!");
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }
}