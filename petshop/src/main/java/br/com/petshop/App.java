package br.com.petshop;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.dao.PetDAO;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Pet;

public class App {

    public static void main(String[] args) {
        System.out.println("🚀 Testando Integração Cliente + Pet");

        try {
            
            ClienteDAO clienteDao = new ClienteDAO();
            PetDAO petDao = new PetDAO();

            String cpfMarcos = "999.888.777-66";
            Cliente marcos = new Cliente(cpfMarcos, "Marcos Silva", "marcos@email.com", "11 90000-0000");
            
            try {
                clienteDao.salvar(marcos);
            } catch (Exception e) {
                System.out.println("⚠️ Aviso: Talvez o Marcos já exista no banco.");
            }

            Pet rex = new Pet("Rex", "Cachorro", cpfMarcos); 
            petDao.salvar(rex);

            System.out.println("Teste finalizado!");

        } catch (Exception e) {
            System.out.println("Erro fatal:");
            e.printStackTrace();
        }
    }
}