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
        validarDados(nome, email, telefone, cpf);
        
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        String telLimpo = telefone.replaceAll("[^0-9]", "");

        if (clienteDao.buscarPorCpf(cpfLimpo) != null) {
            throw new BusinessException("Já existe um cliente cadastrado com este CPF!");
        }

        Cliente novoCliente = new Cliente(cpfLimpo, nome, email, telLimpo);
        clienteDao.salvar(novoCliente);
    }

    public void atualizar(int id, String nome, String email, String telefone) {
        if (nome == null || nome.trim().isEmpty()) throw new BusinessException("Nome é obrigatório!");
        
        Cliente clienteExistente = clienteDao.buscarPorId(id);
        if (clienteExistente == null) {
            throw new BusinessException("Cliente não encontrado para atualização!");
        }

        clienteExistente.setNome(nome);
        clienteExistente.setEmail(email);
        clienteExistente.setTelefone(telefone.replaceAll("[^0-9]", ""));

        clienteDao.atualizar(clienteExistente);
    }

    private void validarDados(String nome, String email, String telefone, String cpf) {
        if (nome == null || nome.trim().isEmpty()) throw new BusinessException("O nome é obrigatório!");
        
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) throw new BusinessException("CPF inválido!");

        if (email == null || !email.contains("@")) throw new BusinessException("E-mail inválido!");

        String telLimpo = telefone.replaceAll("[^0-9]", "");
        if (telLimpo.length() < 10) throw new BusinessException("Telefone inválido!");
    }

    public List<Cliente> listarTodos() {return clienteDao.listarTodos();}
    public void excluir(int id) {clienteDao.excluir(id);}
}