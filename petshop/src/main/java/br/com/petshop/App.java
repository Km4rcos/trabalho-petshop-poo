package br.com.petshop;

import java.util.List;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.model.Cliente;

public class App {

    public static void main(String[] args) {
        System.out.println("Sistema PetShop - Relatório de Clientes");

        try {
            ClienteDAO dao = new ClienteDAO();

            List<Cliente> lista = dao.listarTodos();

            System.out.println("-----------------------------------------");
            if (lista.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado ainda.");
            } else {
                for (Cliente c : lista) {
                    System.out.println("Nome: " + c.getNome());
                    System.out.println("Email: " + c.getEmail());
                    System.out.println("-----------------------------------------");
                }
            }

        } catch (Exception e) {
            System.out.println("Erro:");
            e.printStackTrace();
        }
    }
}