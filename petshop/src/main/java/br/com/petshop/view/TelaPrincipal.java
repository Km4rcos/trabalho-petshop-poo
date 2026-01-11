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
        // Inicializa o Controller e registra a tela como Observer
        controller = new ServicoController();
        controller.addObserver(this);
        
        configurarJanela();
        
        // Criamos os componentes e adicionamos ao layout
        add(criarSidebar(), BorderLayout.WEST);
        add(criarPainelCentral(), BorderLayout.CENTER);
        
        // Carga inicial de dados
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
        sidebar.setBackground(new Color(44, 62, 80)); // Azul Escuro
        sidebar.setPreferredSize(new Dimension(230, 0));

        // --- BOTÕES DE CADASTRO ---
        JButton btnNovoCliente = criarBtn("👥 Novo Cliente");
        JButton btnNovoPet = criarBtn("🐾 Novo Pet");
        JButton btnAgendar = criarBtn("🚿 Agendar Serviço");
        
        // --- BOTÕES DE GERENCIAMENTO ---
        JButton btnGerenciarClientes = criarBtn("📋 Lista de Clientes");
        JButton btnGerenciarPets = criarBtn("🐕 Lista de Pets");
        
        // --- BOTÃO DE OPERAÇÃO ---
        JButton btnFinalizar = criarBtn("✅ Finalizar Serviço");
        btnFinalizar.setBackground(new Color(39, 174, 96)); // Verde
        btnFinalizar.setForeground(Color.WHITE);
        JButton btnGerenciarServicos = criarBtn("📋 Histórico/Gerenciar");
        btnGerenciarServicos.addActionListener(e -> new TelaListaServicos(this, controller).setVisible(true));
        sidebar.add(btnGerenciarServicos);

        // --- AÇÕES ---
        btnNovoCliente.addActionListener(e -> new TelaCadastroCliente(this, null).setVisible(true));
        btnNovoPet.addActionListener(e -> new TelaCadastroPet(this, null).setVisible(true));
        btnAgendar.addActionListener(e -> new TelaAgendamento(this, controller).setVisible(true));
        
        btnGerenciarClientes.addActionListener(e -> new TelaListaClientes(this).setVisible(true));
        btnGerenciarPets.addActionListener(e -> new TelaListaPets(this).setVisible(true));
        
        btnFinalizar.addActionListener(e -> finalizarServico());

        // Adicionando à sidebar com separadores visuais
        sidebar.add(new JLabel("<html><font color='white'><b>CADASTROS</b></font></html>"));
        sidebar.add(btnNovoCliente);
        sidebar.add(btnNovoPet);
        
        sidebar.add(new JSeparator(SwingConstants.HORIZONTAL));
        sidebar.add(new JLabel("<html><font color='white'><b>AGENDAMENTOS</b></font></html>"));
        sidebar.add(btnAgendar);
        sidebar.add(btnFinalizar);

        sidebar.add(new JSeparator(SwingConstants.HORIZONTAL));
        sidebar.add(new JLabel("<html><font color='white'><b>RELATÓRIOS</b></font></html>"));
        sidebar.add(btnGerenciarClientes);
        sidebar.add(btnGerenciarPets);

        return sidebar;
    }

    private JPanel criarPainelCentral() {
        JPanel painelCentral = new JPanel(new BorderLayout(0, 10));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tabela de Serviços (Parte de cima)
        model = new DefaultTableModel(new String[]{"ID", "Pet", "Serviço", "Valor", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(model);
        tabela.setRowHeight(25);
        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createTitledBorder("Agenda de Serviços em Aberto"));

        // Log de Notificações (Parte de baixo)
        log = new JTextArea(10, 0);
        log.setEditable(false);
        log.setFont(new Font("Consolas", Font.PLAIN, 12));
        log.setBackground(new Color(236, 240, 241));
        JScrollPane scrollLog = new JScrollPane(log);
        scrollLog.setBorder(BorderFactory.createTitledBorder("🔔 Log de Atividades"));

        painelCentral.add(scrollTabela, BorderLayout.CENTER);
        painelCentral.add(scrollLog, BorderLayout.SOUTH);

        return painelCentral;
    }

    private JButton criarBtn(String t) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(200, 40));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        b.putClientProperty("JButton.buttonType", "roundRect");
        return b;
    }

    public void atualizarTabela() {
        model.setRowCount(0);
        for (Servico s : controller.listarTodos()) {
            // Só mostra na tela principal o que NÃO estiver finalizado (opcional)
            if (s.getStatus() != StatusServico.FINALIZADO) {
                model.addRow(new Object[]{
                    s.getId(), 
                    s.getPet().getNome(), 
                    s.getTipo(), 
                    "R$ " + String.format("%.2f", s.getValor()), 
                    s.getStatus()
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
            JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela para finalizar.");
        }
    }

    @Override
    public void atualizar(String msg) {
        log.append(" > " + msg + "\n");
        log.setCaretPosition(log.getDocument().getLength()); // Auto-scroll
        atualizarTabela();
    }
}