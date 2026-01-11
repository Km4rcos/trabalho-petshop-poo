package br.com.petshop;

import java.util.List;
import java.util.Scanner;

import br.com.petshop.controller.ClienteController;
import br.com.petshop.controller.ServicoController;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Servico;
import br.com.petshop.model.StatusServico;
import br.com.petshop.observer.Observer;

public class App implements Observer {

    public static void main(String[] args) {
        new App().iniciar();
    }

    @Override
    public void atualizar(String mensagem) {
        System.out.println("\n🔔 NOTIFICAÇÃO DO SISTEMA: " + mensagem);
    }

    public void iniciar() {
        System.out.println("🚀 Sistema PetShop - Com Observer");

        ClienteController clienteController = new ClienteController();
        ServicoController servicoController = new ServicoController();
        
        servicoController.addObserver(this);

        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n--- MENU PRINCIPAL ---");
                System.out.println("1 - [CLIENTE] Cadastrar");
                System.out.println("2 - [CLIENTE] Listar");
                System.out.println("3 - [CLIENTE] Excluir");
                System.out.println("4 - [SERVIÇO] Agendar Banho/Tosa"); 
                System.out.println("5 - [SERVIÇO] Mudar Status");       
                System.out.println("6 - [SERVIÇO] Listar Agenda");      
                System.out.println("0 - Sair");
                System.out.print("Opção: ");

                String opcao = scanner.nextLine();

                if (opcao.equals("0")) break;

                if (opcao.equals("1")) {
                    System.out.print("Nome: "); String nome = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Telefone: "); String tel = scanner.nextLine();
                    System.out.print("CPF: "); String cpf = scanner.nextLine();
                    clienteController.cadastrar(nome, email, tel, cpf);
                } 
                else if (opcao.equals("2")) {
                    for (Cliente c : clienteController.listarTodos()) {
                        System.out.println("👤 " + c.getNome() + " (CPF: " + c.getCpf() + ")");
                    }
                }
                else if (opcao.equals("3")) {
                    System.out.print("CPF para excluir: ");
                    clienteController.excluir(scanner.nextLine());
                }

                else if (opcao.equals("4")) {
                    System.out.print("Tipo (ex: Banho): "); 
                    String tipo = scanner.nextLine();
                    System.out.print("Valor: "); 
                    double valor = Double.parseDouble(scanner.nextLine());
                    System.out.print("ID do Pet (veja no banco): "); 
                    int idPet = Integer.parseInt(scanner.nextLine());

                    servicoController.agendar(tipo, valor, idPet);
                }
                else if (opcao.equals("5")) {
                    System.out.print("ID do Serviço: "); 
                    int idServico = Integer.parseInt(scanner.nextLine());
                    
                    System.out.println("Escolha: 1-ESPERANDO, 2-ATENDENDO, 3-FINALIZADO");
                    String st = scanner.nextLine();
                    StatusServico novoStatus = StatusServico.ESPERANDO;
                    if(st.equals("2")) novoStatus = StatusServico.ATENDENDO;
                    if(st.equals("3")) novoStatus = StatusServico.FINALIZADO;

                    servicoController.atualizarStatus(idServico, novoStatus);
                }
                else if (opcao.equals("6")) {
                    List<Servico> lista = servicoController.listarTodos();
                    for (Servico s : lista) {
                        System.out.println("🚿 ID: " + s.getId() + " | " + s.getTipo() + " | " + s.getStatus());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}