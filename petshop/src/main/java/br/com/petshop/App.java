package br.com.petshop;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.model.Cliente;

public class App {

    public static void main(String[] args) {
        System.out.println("🚀 Sistema PetShop iniciando...");

        try {
            
            Cliente c1 = new Cliente("123.456.789-00", "Brenda Developer", "brenda@java.com", "11 99999-8888");

            ClienteDAO dao = new ClienteDAO();
            dao.salvar(c1);

        } catch (Exception e) {
            System.out.println("Algo deu errado:");
            e.printStackTrace();
        }
    }
}