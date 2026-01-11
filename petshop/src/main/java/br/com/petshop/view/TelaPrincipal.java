package br.com.petshop.view;

import javax.swing.*;
import java.awt.*;
import br.com.petshop.observer.Observer;
import br.com.petshop.controller.ServicoController;

public class TelaPrincipal extends JFrame implements Observer {

    private JTextArea areaNotificacoes;
    private ServicoController servicoController;

    public TelaPrincipal() {
        this.servicoController = new ServicoController();
        this.servicoController.addObserver(this);

        configurarJanela();
        criarComponentes();
        
        setVisible(true);
    }

    private void configurarJanela() {
        setTitle("PetShop Pro - Dashboard");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void criarComponentes() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JButton btnCadCliente = criarBotaoMenu("👥 Novo Cliente");
        JButton btnCadPet = criarBotaoMenu("🐾 Novo Pet");
        JButton btnServicos = criarBotaoMenu("🚿 Agendar Serviço");

        btnCadCliente.addActionListener(e -> new TelaCadastroCliente(this).setVisible(true));

        sidebar.add(btnCadCliente);
        sidebar.add(btnCadPet);
        sidebar.add(btnServicos);

        add(sidebar, BorderLayout.WEST);

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Resumo de Atividades");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        painelCentral.add(lblTitulo, BorderLayout.NORTH);

        areaNotificacoes = new JTextArea();
        areaNotificacoes.setEditable(false);
        areaNotificacoes.setFont(new Font("Consolas", Font.PLAIN, 14));
        
        JScrollPane scroll = new JScrollPane(areaNotificacoes);
        scroll.setBorder(BorderFactory.createTitledBorder("🔔 Log de Eventos (Real-time)"));
        
        painelCentral.add(scroll, BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(180, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void atualizar(String mensagem) {
        areaNotificacoes.append(" > " + mensagem + "\n");
        areaNotificacoes.setCaretPosition(areaNotificacoes.getDocument().getLength());
    }
}