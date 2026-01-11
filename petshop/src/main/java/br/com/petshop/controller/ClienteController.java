package br.com.petshop.controller;

import java.util.List;

import br.com.petshop.dao.ClienteDAO;
import br.com.petshop.model.Cliente;

public class ClienteController {

    private ClienteDAO clienteDao;

    public ClienteController() {

        this.clienteDao = new ClienteDAO();
    }

    public void cadastrar(String nome, String email, String telefone, String cpf) {
        
        if (nome == null || nome.isEmpty()) {
            throw new RuntimeException("O nome é obrigatório!");
        }

        Cliente novoCliente = new Cliente(cpf, nome, email, telefone);
        clienteDao.salvar(novoCliente);
    }

    public List<Cliente> listarTodos() {
        return clienteDao.listarTodos();
    }
    
    public void excluir(String cpf) {
        clienteDao.excluirPorCpf(cpf);
    }

}
