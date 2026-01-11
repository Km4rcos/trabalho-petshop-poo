package br.com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.petshop.exception.BusinessException;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Pet;
import br.com.petshop.util.ConnectionFactory;

public class PetDAO {

    public void salvar(Pet pet) {
        String sql = "INSERT INTO pets (nome, especie, id_cliente) VALUES (?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, pet.getNome());
            ps.setString(2, pet.getEspecie());
            ps.setInt(3, pet.getDono().getId());

            ps.executeUpdate();
            System.out.println("Pet " + pet.getNome() + " salvo para o dono ID: " + pet.getDono().getId());
            
        } catch (SQLException e) {
            throw new BusinessException("Erro ao salvar pet: " + e.getMessage());
        }
    }

    public List<Pet> listarTodos() {
        String sql = "SELECT p.*, c.nome as nome_dono FROM pets p " +
                    "INNER JOIN clientes c ON p.id_cliente = c.id";
        List<Pet> pets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pet p = new Pet();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
            
                p.setEspecie(rs.getString("especie"));
                
                Cliente dono = new Cliente();
                dono.setId(rs.getInt("id_cliente"));
                dono.setNome(rs.getString("nome_dono"));
                p.setDono(dono);
                
                pets.add(p);
            }
            return pets;
        } catch (SQLException e) {
            throw new BusinessException("Erro ao listar pets: " + e.getMessage());
        }
    }
    public void atualizar(Pet pet) {
        String sql = "UPDATE pets SET nome = ?, especie = ?, id_cliente = ? WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, pet.getNome());
            ps.setString(2, pet.getEspecie());
            ps.setInt(3, pet.getDono().getId());
            ps.setInt(4, pet.getId());

            ps.executeUpdate();
            System.out.println("Pet ID " + pet.getId() + " atualizado com sucesso!");
            
        } catch (SQLException e) {
            throw new BusinessException("Erro ao atualizar pet: " + e.getMessage());
        }
    }
    public void excluir(int id) {
        String sql = "DELETE FROM pets WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Pet excluído com sucesso!");
            
        } catch (SQLException e) {
            throw new BusinessException("Erro ao excluir pet: " + e.getMessage());
        }
    }
    public Pet buscarPorId(int id) {
        String sql = "SELECT * FROM pets WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pet p = new Pet();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setEspecie(rs.getString("especie"));
                    return p;
                }
            }
        } catch (SQLException e) {
            throw new BusinessException("Erro ao buscar pet por ID.");
        }
        return null;
    }
}