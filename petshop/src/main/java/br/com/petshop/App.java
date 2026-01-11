package br.com.petshop;

import br.com.petshop.model.*;
import br.com.petshop.exception.BusinessException;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("=== Iniciando Teste do PetShop System ===\n");

            // 1. Criando um Cliente
            Cliente cliente = new Cliente();
            cliente.setId(1);
            cliente.setNome("João Silva");
            cliente.setCpf("123.456.789-00");
            cliente.setTelefone("(11) 98888-8888");

            // 2. Criando um Pet
            Pet pet1 = new Pet();
            pet1.setId(101);
            pet1.setNome("Rex");
            pet1.setEspecie("Cachorro");
            pet1.setRaca("Golden Retriever");
            pet1.setDono(cliente); // Relacionando o pet ao dono

            // 3. Adicionando o Pet à lista do Cliente
            cliente.getPets().add(pet1);

            // 4. Criando um Serviço para o Rex
            Servico banho = new Servico();
            banho.setId(501);
            banho.setTipo("Banho e Tosa");
            banho.setValor(85.00);
            banho.setPet(pet1);
            // O status já começa como ESPERANDO conforme definido no seu construtor

            // Adicionando ao histórico do Pet (usando o método que você criou!)
            pet1.adicionarServico(banho);

            // 5. Exibindo os dados para validar
            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Pets do Cliente:");
            
            for (Pet p : cliente.getPets()) {
                System.out.println(" - Pet: " + p.getNome() + " (" + p.getEspecie() + ")");
                
                System.out.println("   Histórico de Serviços:");
                for (Servico s : p.getHistorico()) {
                    System.out.println("     > Tipo: " + s.getTipo() + " | Valor: R$ " + s.getValor() + " | Status: " + s.getStatus());
                }
            }

            // 6. Testando a BusinessException (Exemplo manual)
            validarValorServico(banho);
            System.out.println("\nValidação de regra de negócio: OK!");

        } catch (BusinessException e) {
            System.err.println("Erro de Negócio: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    // Método auxiliar para testar a exceção que você criou
    public static void validarValorServico(Servico s) throws BusinessException {
        if (s.getValor() < 0) {
            throw new BusinessException("Valor do serviço não pode ser negativo!");
        }
    }
}