package br.com.petshop.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.petshop.model.Servico;
import br.com.petshop.model.StatusServico;
import br.com.petshop.util.ConnectionFactory;
import br.com.petshop.exception.BusinessException;

public class ServicoDAO {

    public void salvar(Servico servico) {
        String sql = "INSERT INTO servicos (tipo, valor, status, id_pet) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, servico.getTipo());
            ps.setDouble(2, servico.getValor());
            ps.setString(3, servico.getStatus().name());
            ps.setInt(4, servico.getPet().getId());

            ps.executeUpdate();
            System.out.println("Serviço de " + servico.getTipo() + " salvo com sucesso!");
            
        } catch (SQLException e) {
            throw new BusinessException("Erro ao salvar serviço: " + e.getMessage());
        }
    }

    public List<Servico> listarTodos() {
        String sql = "SELECT * FROM servicos";
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
                servicos.add(s);
            }
            return servicos;
        } catch (SQLException e) {
            throw new BusinessException("Erro ao listar serviços: " + e.getMessage());
        }
    }
}