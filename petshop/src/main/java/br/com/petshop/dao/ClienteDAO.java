package br.com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import br.com.petshop.model.Cliente;

public class ClienteDAO {

    public void salvar(Cliente cliente) {
        
        String sql = "INSERT INTO clientes (cpf, nome, email, telefone) VALUES (?, ?, ?, ?)";

        try {
            
            Connection con = ConnectionFactory.getConnection();
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefone());

            ps.execute();
            System.out.println("Cliente " + cliente.getNome() + " salvo com sucesso!");
            
            ps.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }
}
