package br.com.petshop.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import br.com.petshop.controller.ClienteController;

public class TelaCadastroCliente extends JDialog {
    private JTextField txtNome, txtEmail, txtTel;
    private JFormattedTextField txtCpf;
    private ClienteController controller = new ClienteController();

    public TelaCadastroCliente(Frame p) {
        super(p, "Novo Cliente", true);
        setLayout(new GridLayout(5, 2, 10, 10));
        
        add(new JLabel(" Nome:")); txtNome = new JTextField(); add(txtNome);
        
        try {
            MaskFormatter m = new MaskFormatter("###.###.###-##");
            m.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(m);
        } catch (Exception e) { txtCpf = new JFormattedTextField(); }
        
        add(new JLabel(" CPF:")); add(txtCpf);
        add(new JLabel(" E-mail:")); txtEmail = new JTextField(); add(txtEmail);
        add(new JLabel(" Tel:")); txtTel = new JTextField(); add(txtTel);

        JButton btn = new JButton("Salvar");
        btn.addActionListener(e -> {
            try {
                controller.cadastrar(txtNome.getText(), txtEmail.getText(), txtTel.getText(), txtCpf.getText());
                dispose();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        });
        add(btn);
        pack(); setLocationRelativeTo(p);
    }
}