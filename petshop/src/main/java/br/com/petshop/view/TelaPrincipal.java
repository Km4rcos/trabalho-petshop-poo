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
        
        // --- SIDEBAR ---
        JPanel sidebar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(220, 0));

        // Botões de Cadastro Simples
        JButton btnC = criarBtn("👥 Novo Cliente");
        JButton btnP = criarBtn("🐾 Novo Pet");
        JButton btnS = criarBtn("🚿 Agendar");
        
        // Botões de Gerenciamento (Tabelas)
        JButton btnGerenciarClientes = criarBtn("📋 Gerenciar Clientes");
        JButton btnGerenciarPets = criarBtn("🐕 Gerenciar Pets"); // NOVO
        
        // Botão de Operação
        JButton btnF = criarBtn("✅ Finalizar Serviço");

        // --- CONFIGURAÇÃO DAS AÇÕES ---
        btnC.addActionListener(e -> new TelaCadastroCliente(this, null).setVisible(true));
        btnP.addActionListener(e -> new TelaCadastroPet(this, null).setVisible(true));
        btnS.addActionListener(e -> new TelaAgendamento(this).setVisible(true));
        btnF.addActionListener(e -> finalizar());
        
        btnGerenciarClientes.addActionListener(e -> new TelaListaClientes(this).setVisible(true));
        btnGerenciarPets.addActionListener(e -> new TelaListaPets(this).setVisible(true)); // NOVO

        // Adicionando na Sidebar na ordem correta
        sidebar.add(btnC);
        sidebar.add(btnGerenciarClientes);
        sidebar.add(new JSeparator(JSeparator.HORIZONTAL));
        sidebar.add(btnP);
        sidebar.add(btnGerenciarPets);
        sidebar.add(new JSeparator(JSeparator.HORIZONTAL));
        sidebar.add(btnS);
        sidebar.add(btnF);

        add(sidebar, BorderLayout.WEST);

        // --- CENTRO (DASHBOARD) ---
        JPanel center = new JPanel(new GridLayout(2, 1, 0, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        model = new DefaultTableModel(new String[]{"ID", "Pet", "Serviço", "Valor", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabela = new JTable(model);
        log = new JTextArea(); 
        log.setEditable(false);
        log.setBackground(new Color(245, 245, 245));
        
        center.add(new JScrollPane(tabela));
        center.add(new JScrollPane(log));
        add(center, BorderLayout.CENTER);

        atualizarTabela();
        setVisible(true);
    }

    private JButton criarBtn(String t) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(190, 40));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.putClientProperty("JButton.buttonType", "roundRect");
        return b;
    }

    public void atualizarTabela() {
        model.setRowCount(0);
        for (Servico s : controller.listarTodos()) {
            // Verificação de segurança para não dar erro se o pet for nulo no banco
            String nomePet = (s.getPet() != null) ? s.getPet().getNome() : "Desconhecido";
            model.addRow(new Object[]{
                s.getId(), 
                nomePet, 
                s.getTipo(), 
                "R$ " + String.format("%.2f", s.getValor()), 
                s.getStatus()
            });
        }
    }

    private void finalizar() {
        int row = tabela.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            int resposta = JOptionPane.showConfirmDialog(this, "Finalizar o serviço #" + id + "?");
            if (resposta == JOptionPane.YES_OPTION) {
                controller.atualizarStatus(id, StatusServico.FINALIZADO);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela para finalizar.");
        }
    }

    @Override
    public void atualizar(String msg) {
        log.append(" > " + msg + "\n");
        log.setCaretPosition(log.getDocument().getLength());
        atualizarTabela();
    }
}