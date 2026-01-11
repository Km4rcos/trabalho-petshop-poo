package br.com.petshop;

import java.util.List;
import java.util.Scanner;

import br.com.petshop.controller.ClienteController;
import br.com.petshop.model.Cliente;

public class App {

    public static void main(String[] args) {
        System.out.println("🚀 Sistema PetShop - MVC");
        
        ClienteController controller = new ClienteController();
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) { 
                System.out.println("\n--- MENU ---");
                System.out.println("1 - Cadastrar Cliente");
                System.out.println("2 - Listar Clientes");
                System.out.println("3 - Excluir Cliente"); 
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");
                
                String opcao = scanner.nextLine();

                if (opcao.equals("0")) {
                    System.out.println("Saindo...");
                    break;
                }
                
                else if (opcao.equals("1")) {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String tel = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();

                    controller.cadastrar(nome, email, tel, cpf);
                    System.out.println("Cadastro realizado!");
                } 
                
                else if (opcao.equals("2")) {
                    List<Cliente> lista = controller.listarTodos();
                    System.out.println("\n--- Lista de Clientes ---");
                    for (Cliente c : lista) {
                        System.out.println(" " + c.getNome() + " | CPF: " + c.getCpf() + " | " + c.getEmail());
                    }
                } 
                
                else if (opcao.equals("3")) { 
                    System.out.print("Digite o CPF do cliente para excluir: ");
                    String cpfParaExcluir = scanner.nextLine();
                    
                    controller.excluir(cpfParaExcluir);
                    
                    System.out.println("🗑️ Exclusão solicitada!");
                }
            }

        } catch (Exception e) {
            System.out.println("1Erro: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}