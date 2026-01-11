package br.com.petshop;

import com.formdev.flatlaf.FlatLightLaf;
import br.com.petshop.view.TelaPrincipal;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            new TelaPrincipal();
        } catch (Exception ex) {
            new TelaPrincipal();
        }
    }
}