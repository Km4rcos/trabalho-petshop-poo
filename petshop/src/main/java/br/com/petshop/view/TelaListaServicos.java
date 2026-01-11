package br.com.petshop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import br.com.petshop.controller.ServicoController;
import br.com.petshop.model.Servico;

public class TelaListaServicos extends JDialog {
    private JTable tabela;
    private DefaultTableModel model;
    private ServicoController controller;

    public TelaListaServicos(Frame p, ServicoController controller) {
        super(p, "Gerenciar Serviços", true);
        this.controller = controller;
        setSize(900, 500);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "Pet", "Serviço", "Valor", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnEditar = new JButton("✏️ Alterar");
        JButton btnExcluir = new JButton("🗑️ Excluir");

        btnEditar.addActionListener(e -> acaoEditar());
        btnExcluir.addActionListener(e -> acaoExcluir());

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDados();
        setLocationRelativeTo(p);
    }

    private void carregarDados() {
        model.setRowCount(0);
        for (Servico s : controller.listarTodos()) {
            model.addRow(new Object[]{s.getId(), s.getPet().getNome(), s.getTipo(), s.getValor(), s.getStatus()});
        }
    }

    private void acaoEditar() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        
        // --- PAINEL DE EDIÇÃO CONTROLADA ---
        String[] opcoesServico = {"Banho", "Tosa", "Banho e Tosa", "Consulta", "Vacina"};
        JComboBox<String> comboEspecies = new JComboBox<>(opcoesServico);
        comboEspecies.setSelectedItem(model.getValueAt(row, 2).toString());
        
        JTextField txtValor = new JTextField(model.getValueAt(row, 3).toString());

        Object[] mensagem = {
            "Selecione o novo serviço:", comboEspecies,
            "Novo valor (R$):", txtValor
        };

        int opcao = JOptionPane.showConfirmDialog(this, mensagem, "Alterar Serviço #" + id, JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {
                Servico s = new Servico();
                s.setId(id);
                s.setTipo(comboEspecies.getSelectedItem().toString());
                s.setValor(Double.parseDouble(txtValor.getText().replace(",", ".")));
                
                controller.alterar(s);
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: Valor numérico inválido!");
            }
        }
    }

    private void acaoExcluir() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            int conf = JOptionPane.showConfirmDialog(this, "Excluir serviço #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                controller.excluir(id);
                carregarDados();
            }
        }
    }
}