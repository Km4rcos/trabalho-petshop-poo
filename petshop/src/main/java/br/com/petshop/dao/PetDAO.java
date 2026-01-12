package br.com.petshop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.petshop.exception.BusinessException;
import br.com.petshop.model.Cliente;
import br.com.petshop.model.Pet;
import br.com.petshop.util.ConnectionFactory;

public class PetDAO {

    public void salvar(Pet pet) {
        String sql = "INSERT INTO pets (nome, especie, raca, id_cliente) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pet.getNome());
            ps.setString(2, pet.getEspecie());
            ps.setString(3, pet.getRaca());
            ps.setInt(4, pet.getDono().getId());
            ps.executeUpdate();
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
                pets.add(extrairObjeto(rs));
            }
            return pets;
        } catch (SQLException e) {
            throw new BusinessException("Erro ao listar pets.");
        }
    }

    public Pet buscarPorId(int id) {
        String sql = "SELECT p.*, c.nome as nome_dono FROM pets p " +
                    "INNER JOIN clientes c ON p.id_cliente = c.id WHERE p.id = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extrairObjeto(rs);
            }
        } catch (SQLException e) {
            throw new BusinessException("Erro ao buscar pet.");
        }
        return null;
    }

    private Pet extrairObjeto(ResultSet rs) throws SQLException {
        Pet p = new Pet();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setEspecie(rs.getString("especie"));
        p.setRaca(rs.getString("raca"));
        
        Cliente dono = new Cliente();
        dono.setId(rs.getInt("id_cliente"));
        dono.setNome(rs.getString("nome_dono"));
        p.setDono(dono);
        return p;
    }

    public void atualizar(Pet pet) {
        String sql = "UPDATE pets SET nome = ?, especie = ?, raca = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pet.getNome());
            ps.setString(2, pet.getEspecie());
            ps.setString(3, pet.getRaca());
            ps.setInt(4, pet.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("Erro ao atualizar pet.");
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("Erro ao excluir: pet possui serviços vinculados.");
        }
    }
}