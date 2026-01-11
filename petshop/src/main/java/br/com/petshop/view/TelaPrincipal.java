package br.com.petshop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import br.com.petshop.observer.Observer;
import br.com.petshop.controller.ServicoController;
import br.com.petshop.model.Servico;
import br.com.petshop.model.StatusServico;

public class TelaPrincipal extends JFrame implements Observer {
    private JTextArea log;
    private JTable tabela;
    private DefaultTableModel model;
    private ServicoController controller;

    public TelaPrincipal() {
        controller = new ServicoController();
        controller.addObserver(this);
        
        setTitle("PetShop Pro - Sistema de Gestão");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Sidebar
        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(220, 0));

        JButton btnC = criarBtn("👥 Novo Cliente");
        JButton btnP = criarBtn("🐾 Novo Pet");
        JButton btnS = criarBtn("🚿 Agendar");
        JButton btnF = criarBtn("✅ Finalizar");
        JButton btnGerenciarClientes = criarBtn("👥 Gerenciar Clientes");
        btnGerenciarClientes.addActionListener(e -> new TelaListaClientes(this).setVisible(true));
        sidebar.add(btnGerenciarClientes);

        btnC.addActionListener(e -> new TelaCadastroCliente(this).setVisible(true));
        btnP.addActionListener(e -> new TelaCadastroPet(this).setVisible(true));
        btnS.addActionListener(e -> new TelaAgendamento(this).setVisible(true));
        btnF.addActionListener(e -> finalizar());

        sidebar.add(btnC); sidebar.add(btnP); sidebar.add(btnS); sidebar.add(btnF);
        add(sidebar, BorderLayout.WEST);

        // Centro
        JPanel center = new JPanel(new GridLayout(2, 1, 0, 10));
        model = new DefaultTableModel(new String[]{"ID", "Pet", "Serviço", "Valor", "Status"}, 0);
        tabela = new JTable(model);
        log = new JTextArea(); log.setEditable(false);
        
        center.add(new JScrollPane(tabela));
        center.add(new JScrollPane(log));
        add(center, BorderLayout.CENTER);

        atualizarTabela();
        setVisible(true);
    }

    private JButton criarBtn(String t) {
        JButton b = new JButton(t); b.setPreferredSize(new Dimension(190, 40));
        b.putClientProperty("JButton.buttonType", "roundRect");
        return b;
    }

    public void atualizarTabela() {
        model.setRowCount(0);
        for (Servico s : controller.listarTodos()) {
            model.addRow(new Object[]{s.getId(), s.getPet().getNome(), s.getTipo(), "R$ "+s.getValor(), s.getStatus()});
        }
    }

    private void finalizar() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            controller.atualizarStatus(id, StatusServico.FINALIZADO);
        }
    }

    @Override
    public void atualizar(String msg) {
        log.append(" > " + msg + "\n");
        atualizarTabela();
    }
}