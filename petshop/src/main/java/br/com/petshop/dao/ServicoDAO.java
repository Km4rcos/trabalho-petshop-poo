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
        } catch (SQLException e) {
            throw new BusinessException("Erro ao salvar serviço.");
        }
    }

    public List<Servico> listarTodos() {
        String sql = "SELECT s.*, p.nome as nome_pet FROM servicos s " +
                    "INNER JOIN pets p ON s.id_pet = p.id";
        List<Servico> lista = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(extrairObjeto(rs));
            }
        } catch (SQLException e) {
            throw new BusinessException("Erro ao listar serviços.");
        }
        return lista;
    }

    private Servico extrairObjeto(ResultSet rs) throws SQLException {
        Servico s = new Servico();
        s.setId(rs.getInt("id"));
        s.setTipo(rs.getString("tipo"));
        s.setValor(rs.getDouble("valor"));
        s.setStatus(StatusServico.valueOf(rs.getString("status")));
        
        Pet p = new Pet();
        p.setId(rs.getInt("id_pet"));
        p.setNome(rs.getString("nome_pet"));
        s.setPet(p);
        return s;
    }

    public void atualizarStatus(int id, StatusServico novoStatus) {
        String sql = "UPDATE servicos SET status = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, novoStatus.name());
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("Erro ao atualizar status.");
        }
    }

    public void atualizarDados(Servico s) {
        String sql = "UPDATE servicos SET tipo = ?, valor = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getTipo());
            ps.setDouble(2, s.getValor());
            ps.setInt(3, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("Erro ao alterar serviço.");
        }
    }
    public void excluir(int id) {
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException("Erro ao excluir serviço.");
        }
    }
    public double calcularTotalFinalizados() {
        String sql = "SELECT SUM(valor) as total FROM servicos WHERE status = 'FINALIZADO'";
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            throw new BusinessException("Erro ao calcular faturamento.");
        }
        return 0.0;
    }
    public Servico buscarPorId(int id) {
    String sql = "SELECT s.*, p.nome as nome_pet FROM servicos s " +
                "INNER JOIN pets p ON s.id_pet = p.id WHERE s.id = ?";
    try (Connection con = ConnectionFactory.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return extrairObjeto(rs);
            }
        }
    } catch (SQLException e) {
        throw new BusinessException("Erro ao buscar serviço por ID.");
    }
    return null;
}
}