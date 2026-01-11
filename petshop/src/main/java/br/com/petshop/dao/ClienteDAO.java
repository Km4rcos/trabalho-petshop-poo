package br.com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

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
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
            }
        }
        System.out.println("Cliente salvo com ID: " + cliente.getId());
        
    } catch (SQLException e) {
        throw new BusinessException("Erro ao salvar cliente: " + e.getMessage());
    }
}

    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                
                clientes.add(c);
            }

            ps.close();
            con.close();
            return clientes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes", e);
        }
    }
}
