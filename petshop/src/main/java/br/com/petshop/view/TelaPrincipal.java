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
        
        configurarJanela();
        
        add(criarSidebar(), BorderLayout.WEST);
        add(criarPainelCentral(), BorderLayout.CENTER);
        
        atualizarTabela();
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("PetShop Pro - Sistema de Gestão");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private JPanel criarSidebar() {
        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        sidebar.setBackground(new Color(44, 62, 80)); 
        sidebar.setPreferredSize(new Dimension(230, 0));

        sidebar.add(criarLabelTitulo("CADASTROS"));
        JButton btnNovoCliente = criarBtn("👥 Novo Cliente");
        JButton btnNovoPet = criarBtn("🐾 Novo Pet");
        btnNovoCliente.addActionListener(e -> new TelaCadastroCliente(this, null).setVisible(true));
        btnNovoPet.addActionListener(e -> new TelaCadastroPet(this, null).setVisible(true));
        sidebar.add(btnNovoCliente);
        sidebar.add(btnNovoPet);

        sidebar.add(criarSeparador());

        sidebar.add(criarLabelTitulo("SERVIÇOS"));
        JButton btnAgendar = criarBtn("🚿 Agendar Serviço");
        JButton btnFinalizar = criarBtn("✅ Finalizar Serviço");
        btnFinalizar.setBackground(new Color(39, 174, 96)); 
        btnFinalizar.setForeground(Color.WHITE);

        btnAgendar.addActionListener(e -> new TelaAgendamento(this, controller).setVisible(true));
        btnFinalizar.addActionListener(e -> finalizarServico());

        sidebar.add(btnAgendar);
        sidebar.add(btnFinalizar);

        sidebar.add(criarSeparador());

        sidebar.add(criarLabelTitulo("GERENCIAMENTO"));
        JButton btnGerenciarClientes = criarBtn("📋 Lista Clientes");
        JButton btnGerenciarPets = criarBtn("🐕 Lista Pets");
        JButton btnHistServicos = criarBtn("📊 Histórico Serviços");
        JButton btnRelatorio = criarBtn("💰 Relatório Financeiro");
        btnRelatorio.setBackground(new Color(241, 196, 15));
        btnRelatorio.setForeground(Color.BLACK);

        btnGerenciarClientes.addActionListener(e -> new TelaListaClientes(this).setVisible(true));
        btnGerenciarPets.addActionListener(e -> new TelaListaPets(this).setVisible(true));
        btnHistServicos.addActionListener(e -> new TelaListaServicos(this, controller).setVisible(true));
        btnRelatorio.addActionListener(e -> new TelaRelatorio(this, controller).setVisible(true));

        sidebar.add(btnGerenciarClientes);
        sidebar.add(btnGerenciarPets);
        sidebar.add(btnHistServicos);
        sidebar.add(btnRelatorio);

        return sidebar;
    }

    private JLabel criarLabelTitulo(String texto) {
        JLabel label = new JLabel("<html><font color='white'><b>" + texto + "</b></font></html>");
        label.setPreferredSize(new Dimension(200, 20));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private JSeparator criarSeparador() {
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setPreferredSize(new Dimension(200, 1));
        return sep;
    }

    private JButton criarBtn(String t) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(200, 40));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JPanel criarPainelCentral() {
        JPanel painelCentral = new JPanel(new BorderLayout(0, 10));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        model = new DefaultTableModel(new String[]{"ID", "Pet", "Serviço", "Valor", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(model);
        tabela.setRowHeight(25);
        
        log = new JTextArea(10, 0);
        log.setEditable(false);
        log.setBackground(new Color(245, 245, 245));

        painelCentral.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelCentral.add(new JScrollPane(log), BorderLayout.SOUTH);

        return painelCentral;
    }

    public void atualizarTabela() {
        model.setRowCount(0);
        for (Servico s : controller.listarTodos()) {
            if (s.getStatus() != StatusServico.FINALIZADO && s.getStatus() != StatusServico.CANCELADO) {
                model.addRow(new Object[]{
                    s.getId(), s.getPet().getNome(), s.getTipo(), 
                    "R$ " + String.format("%.2f", s.getValor()), s.getStatus()
                });
            }
        }
    }

    private void finalizarServico() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            int resp = JOptionPane.showConfirmDialog(this, "Deseja finalizar o serviço #" + id + "?");
            if (resp == JOptionPane.YES_OPTION) {
                controller.atualizarStatus(id, StatusServico.FINALIZADO);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela.");
        }
    }

    @Override
    public void atualizar(String msg) {
        log.append(" > " + msg + "\n");
        log.setCaretPosition(log.getDocument().getLength());
        atualizarTabela();
    }
}