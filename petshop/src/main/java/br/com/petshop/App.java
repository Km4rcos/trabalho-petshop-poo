package br.com.petshop;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.dao.PetDAO;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Pet;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Teste do Sistema PetShop...");

        try {
            ClienteDAO clienteDao = new ClienteDAO();
            PetDAO petDao = new PetDAO();

            // 1. Criar e Salvar um Cliente
            System.out.println("\n[1] Testando cadastro de Cliente...");
            Cliente novoCliente = new Cliente("123.456.789-00", "Carlos Alberto", "carlos@email.com", "11 98888-7777");
            clienteDao.salvar(novoCliente);
            // Se o ID for > 0, o banco gerou a chave corretamente
            System.out.println("✅ Cliente salvo! ID gerado no banco: " + novoCliente.getId());

            // 2. Criar e Salvar um Pet vinculado a esse Cliente
            System.out.println("\n[2] Testando cadastro de Pet...");
            // Usando o construtor: nome, especie, raca, dono
            Pet novoPet = new Pet("Thor", "Cachorro", "Bulldog", novoCliente);
            petDao.salvar(novoPet);
            System.out.println("✅ Pet salvo com sucesso!");

            // 3. Listar Clientes e seus Pets para validar o JOIN
            System.out.println("\n[3] Listando dados do Banco para validar integração:");
            List<Pet> listaDePets = petDao.listarTodos();

            if (listaDePets.isEmpty()) {
                System.out.println("❌ Nenhum pet encontrado no banco.");
            } else {
                for (Pet p : listaDePets) {
                    System.out.println("-------------------------------------------");
                    System.out.println("Pet: " + p.getNome());
                    System.out.println("Espécie: " + p.getEspecie());
                    System.out.println("Dono (Recuperado via JOIN): " + p.getDono().getNome());
                    System.out.println("ID do Dono: " + p.getDono().getId());
                }
                System.out.println("-------------------------------------------");
            }

            System.out.println("\n🏁 Teste finalizado com sucesso!");

        } catch (Exception e) {
            System.err.println("\n❌ OCORREU UM ERRO NO TESTE:");
            e.printStackTrace();
        }
    }
}