package br.com.petshop.controller;

import java.util.List;
import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.dao.FactoryDAO;
import br.com.petshop.model.Cliente;
import br.com.petshop.exception.BusinessException;

public class ClienteController {
    private ClienteDAO clienteDao;

    public ClienteController() {
        this.clienteDao = FactoryDAO.getClienteDAO(); 
    }

    public void cadastrar(String nome, String email, String telefone, String cpf) {
        // Validação de Nome
        if (nome == null || nome.trim().isEmpty()) {
            throw new BusinessException("O nome é obrigatório!");
        }

        // Validação de CPF (Exatamente 11 números)
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            throw new BusinessException("O CPF deve conter exatamente 11 números digitados!");
        }

        // Validação de Email básica
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new BusinessException("O e-mail informado é inválido!");
        }

        Cliente novoCliente = new Cliente(cpfLimpo, nome, email, telefone);
        clienteDao.salvar(novoCliente);
    }

    public List<Cliente> listarTodos() {
        return clienteDao.listarTodos();
    }
    
    public void excluir(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        clienteDao.excluirPorCpf(cpfLimpo);
    }
    public void atualizar(String nome, String email, String telefone, String cpf) {
    // 1. Validações básicas
    if (nome == null || nome.trim().isEmpty()) throw new BusinessException("Nome é obrigatório!");
    
    // 2. Limpar CPF para busca
    String cpfLimpo = cpf.replaceAll("[^0-9]", "");
    
    // 3. Buscar o cliente no banco para garantir que ele existe e pegar o ID correto
    // Se o seu DAO não tiver buscarPorCpf, use o listarTodos e filtre ou adicione o buscarPorCpf no DAO.
    List<Cliente> lista = clienteDao.listarTodos();
    Cliente clienteExistente = null;
    for(Cliente c : lista) {
        if(c.getCpf().equals(cpfLimpo)) {
            clienteExistente = c;
            break;
        }
    }

    if (clienteExistente == null) throw new BusinessException("Cliente não encontrado para atualização!");

    // 4. Atualiza os dados do objeto
    clienteExistente.setNome(nome);
    clienteExistente.setEmail(email);
    clienteExistente.setTelefone(telefone);

    // 5. Salva a alteração
    clienteDao.atualizar(clienteExistente);
}
}