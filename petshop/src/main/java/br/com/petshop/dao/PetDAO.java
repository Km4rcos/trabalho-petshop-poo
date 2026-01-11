package br.com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.petshop.model.Pet;

public class PetDAO {

    public void salvar(Pet pet) {
        String sql = "INSERT INTO pets (nome, tipo, cpf_dono) VALUES (?, ?, ?)";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setString(1, pet.getNome());
            ps.setString(2, pet.getTipo());
            ps.setString(3, pet.getCpfDono());

            ps.execute();
            System.out.println("Pet " + pet.getNome() + " salvo para o dono " + pet.getCpfDono());
            
            ps.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pet", e);
        }
    }
}
