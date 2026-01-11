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

    // Método principal para cadastro
    public void cadastrar(String nome, String email, String telefone, String cpf) {
        // 1. Valida tudo primeiro
        validarDados(nome, email, telefone, cpf);
        
        // 2. Limpa os dados para salvar padronizado
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        String telLimpo = telefone.replaceAll("[^0-9]", "");

        // 3. Salva
        Cliente novoCliente = new Cliente(cpfLimpo, nome, email, telLimpo);
        clienteDao.salvar(novoCliente);
    }

    // Método para atualizar (Edição)
    public void atualizar(String nome, String email, String telefone, String cpf) {
        // 1. Valida os novos dados
        validarDados(nome, email, telefone, cpf);
        
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        String telLimpo = telefone.replaceAll("[^0-9]", "");

        // 2. Busca o cliente original para manter o ID
        // É melhor usar o buscarPorCpf direto do DAO se você o criou
        Cliente clienteExistente = clienteDao.buscarPorCpf(cpfLimpo);

        if (clienteExistente == null) {
            throw new BusinessException("Cliente com CPF " + cpf + " não encontrado para atualização!");
        }

        // 3. Atualiza os dados do objeto
        clienteExistente.setNome(nome);
        clienteExistente.setEmail(email);
        clienteExistente.setTelefone(telLimpo);

        // 4. Salva a alteração
        clienteDao.atualizar(clienteExistente);
    }

    // MÉTODO NOVO: Validação centralizada (Blindagem)
    private void validarDados(String nome, String email, String telefone, String cpf) {
        // Nome
        if (nome == null || nome.trim().isEmpty()) {
            throw new BusinessException("O nome é obrigatório!");
        }

        // CPF
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            throw new BusinessException("O CPF deve conter exatamente 11 números!");
        }

        // E-mail
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new BusinessException("O e-mail informado é inválido!");
        }

        // Telefone (DDD + 9 + 8 dígitos)
        String telLimpo = telefone.replaceAll("[^0-9]", "");
        if (telLimpo.length() != 11) {
            throw new BusinessException("O telefone deve ter 11 dígitos: (DD) 9XXXX-XXXX");
        }
        if (telLimpo.charAt(2) != '9') {
            throw new BusinessException("O número de celular deve começar com 9 após o DDD.");
        }
    }

    public List<Cliente> listarTodos() {
        return clienteDao.listarTodos();
    }
    
    // Excluir por CPF
    public void excluir(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        clienteDao.excluirPorCpf(cpfLimpo);
    }

    // Opcional: Excluir por ID (Mais comum em interfaces com JTable)
    public void excluirPorId(int id) {
        clienteDao.excluir(id);
    }
}