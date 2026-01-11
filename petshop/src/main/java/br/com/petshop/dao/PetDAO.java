package br.com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.petshop.exception.BusinessException;
import br.com.petshop.model.Pet;
import br.com.petshop.util.ConnectionFactory;

public class PetDAO {

    public void salvar(Pet pet) {
    String sql = "INSERT INTO pets (nome, tipo, id_cliente) VALUES (?, ?, ?)";

    try (Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, pet.getNome());
        ps.setString(2, pet.getTipo());
        
        ps.setInt(3, pet.getDono().getId()); 

        ps.executeUpdate();
        System.out.println("Pet " + pet.getNome() + " salvo para o dono ID: " + pet.getDono().getId());
        
    } catch (SQLException e) {
        throw new BusinessException("Erro ao salvar pet: " + e.getMessage());
    }
}
}
