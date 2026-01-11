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
}