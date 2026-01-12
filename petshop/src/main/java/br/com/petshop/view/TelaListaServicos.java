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
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnEditar = new JButton("✏️ Alterar");
        JButton btnCancelar = new JButton("🚫 Cancelar Serviço");

        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);

        btnEditar.addActionListener(e -> acaoEditar());
        btnCancelar.addActionListener(e -> acaoCancelar());

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        carregarDados();
        setLocationRelativeTo(p);
    }

    private void carregarDados() {
        model.setRowCount(0);
        for (Servico s : controller.listarTodos()) {
            model.addRow(new Object[]{
                s.getId(),
                s.getPet().getNome(),
                s.getTipo(),
                "R$ " + String.format("%.2f", s.getValor()), 
                s.getStatus()
            });
        }
    }

    private void acaoEditar() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        
        JComboBox<String> comboServico = new JComboBox<>(ServicoController.TIPOS_SERVICO.toArray(new String[0]));
        comboServico.setSelectedItem(model.getValueAt(row, 2).toString());
        
        String valorString = model.getValueAt(row, 3).toString()
                .replace("R$", "")
                .replace(",", ".")
                .trim();
        
        JTextField txtValor = new JTextField(valorString);

        Object[] mensagem = {
            "Selecione o novo serviço:", comboServico,
            "Novo valor (R$):", txtValor
        };

        int opcao = JOptionPane.showConfirmDialog(this, mensagem, "Alterar Serviço #" + id, JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            try {
                Servico s = new Servico();
                s.setId(id);
                s.setTipo(comboServico.getSelectedItem().toString());
                
                double novoValor = Double.parseDouble(txtValor.getText().replace(",", "."));
                s.setValor(novoValor);
                
                controller.alterar(s);
                
                carregarDados();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Digite um valor numérico válido (ex: 50.00)");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao alterar: " + ex.getMessage());
            }
        }
    }

    private void acaoCancelar() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            int resp = JOptionPane.showConfirmDialog(this, "Deseja cancelar o serviço #" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    controller.cancelar(id);
                    carregarDados();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao cancelar: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!");
        }
    }
}