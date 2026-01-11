package br.com.petshop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.petshop.model.*;
import br.com.petshop.util.ConnectionFactory;
import br.com.petshop.exception.BusinessException;

public class ServicoDAO {
    public void salvar(Servico s) {
        String sql = "INSERT INTO servicos (tipo, valor, status, id_pet) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getTipo());
            ps.setDouble(2, s.getValor());
            ps.setString(3, s.getStatus().name());
            ps.setInt(4, s.getPet().getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new BusinessException("Erro ao salvar serviço."); }
    }

    public List<Servico> listarTodos() {
        String sql = "SELECT s.*, p.nome as nome_pet FROM servicos s JOIN pets p ON s.id_pet = p.id";
        List<Servico> servicos = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Servico s = new Servico();
                s.setId(rs.getInt("id"));
                s.setTipo(rs.getString("tipo"));
                s.setValor(rs.getDouble("valor"));
                s.setStatus(StatusServico.valueOf(rs.getString("status")));
                Pet p = new Pet(); p.setNome(rs.getString("nome_pet"));
                s.setPet(p);
                servicos.add(s);
            }
            return servicos;
        } catch (SQLException e) { throw new BusinessException("Erro ao listar."); }
    }

    public void atualizarStatus(int id, StatusServico status) {
        String sql = "UPDATE servicos SET status = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new BusinessException("Erro ao atualizar status."); }
    }
}