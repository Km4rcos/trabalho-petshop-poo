package br.com.petshop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import br.com.petshop.observer.Observer;
import br.com.petshop.controller.ServicoController;
import br.com.petshop.model.Servico;

public class TelaPrincipal extends JFrame implements Observer {

    private JTextArea areaNotificacoes;
    private JTable tabelaServicos;
    private DefaultTableModel tableModel;
    private ServicoController servicoController;

    public TelaPrincipal() {
        this.servicoController = new ServicoController();
        this.servicoController.addObserver(this);

        configurarJanela();
        criarComponentes();
        atualizarTabela();
        
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("PetShop Pro - Dashboard Administrativo");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void criarComponentes() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JButton btnCadCliente = criarBotaoMenu("👥 Novo Cliente");
        JButton btnCadPet = criarBotaoMenu("🐾 Novo Pet");
        JButton btnServicos = criarBotaoMenu("🚿 Agendar Serviço");
        JButton btnAtualizar = criarBotaoMenu("🔄 Atualizar Tabela");

        btnCadCliente.addActionListener(e -> new TelaCadastroCliente(this).setVisible(true));
        
        btnCadPet.addActionListener(e -> {
            new TelaCadastroPet(this).setVisible(true);
        });

        btnServicos.addActionListener(e -> {
            new TelaAgendamento(this).setVisible(true);
        });

        btnAtualizar.addActionListener(e -> atualizarTabela());

        sidebar.add(btnCadCliente);
        sidebar.add(btnCadPet);
        sidebar.add(btnServicos);
        sidebar.add(new JSeparator(JSeparator.HORIZONTAL));
        sidebar.add(btnAtualizar);

        add(sidebar, BorderLayout.WEST);

        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Controle de Atendimentos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painelCentral.add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Serviço", "Valor", "Status"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaServicos = new JTable(tableModel);
        JScrollPane scrollTabela = new JScrollPane(tabelaServicos);
        scrollTabela.setBorder(BorderFactory.createTitledBorder("Agenda do Dia"));
        
        areaNotificacoes = new JTextArea(8, 0);
        areaNotificacoes.setEditable(false);
        areaNotificacoes.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaNotificacoes.setForeground(new Color(39, 174, 96));
        
        JScrollPane scrollLog = new JScrollPane(areaNotificacoes);
        scrollLog.setBorder(BorderFactory.createTitledBorder("🔔 Log de Eventos (Real-time)"));

        JPanel painelDados = new JPanel(new GridLayout(2, 1, 0, 10));
        painelDados.add(scrollTabela);
        painelDados.add(scrollLog);

        painelCentral.add(painelDados, BorderLayout.CENTER);
        add(painelCentral, BorderLayout.CENTER);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.putClientProperty("JButton.buttonType", "roundRect");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        return btn;
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Servico> lista = servicoController.listarTodos();
        for (Servico s : lista) {
            tableModel.addRow(new Object[]{
                s.getId(), 
                s.getTipo(), 
                "R$ " + s.getValor(), 
                s.getStatus()
            });
        }
    }

    @Override
    public void atualizar(String mensagem) {
        areaNotificacoes.append(" > " + mensagem + "\n");
        areaNotificacoes.setCaretPosition(areaNotificacoes.getDocument().getLength());
        atualizarTabela();
    }
}