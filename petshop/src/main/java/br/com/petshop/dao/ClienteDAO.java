package br.com.petshop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.petshop.exception.BusinessException;
import br.com.petshop.model.Cliente;
import br.com.petshop.util.ConnectionFactory;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) cliente.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new BusinessException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                clientes.add(c);
            }
            return clientes;
        } catch (SQLException e) {
            throw new BusinessException("Erro ao listar clientes: " + e.getMessage());
        }
    }

    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getEmail());
            ps.setInt(4, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("Erro ao excluir cliente.");
        }
    }

    public void excluirPorCpf(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            if (ps.executeUpdate() == 0) throw new BusinessException("CPF não encontrado.");
        } catch (SQLException e) {
            throw new BusinessException("Erro ao excluir cliente por CPF.");
        }
    }

    // BUSCA POR ID (Necessário para o PetController)
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    c.setEmail(rs.getString("email"));
                    c.setTelefone(rs.getString("telefone"));
                    return c;
                }
            }
        } catch (SQLException e) {
            throw new BusinessException("Erro ao buscar cliente por ID.");
        }
        return null;
    }

    // BUSCA POR CPF (Necessário para o ClienteController atualizar)
    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM clientes WHERE cpf = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCpf(rs.getString("cpf"));
                    c.setEmail(rs.getString("email"));
                    c.setTelefone(rs.getString("telefone"));
                    return c;
                }
            }
        } catch (SQLException e) {
            throw new BusinessException("Erro ao buscar cliente por CPF.");
        }
        return null;
    }
}