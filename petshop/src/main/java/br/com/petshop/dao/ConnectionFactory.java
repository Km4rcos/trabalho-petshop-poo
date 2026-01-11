package br.com.petshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection conexao;

    public static Connection getConnection() {
        try {
            
            if (conexao == null || conexao.isClosed()) {
                
                conexao = DriverManager.getConnection("jdbc:sqlite:petshop.db");
            }
            return conexao;

        } catch (SQLException e) {
            throw new RuntimeException("Erro fatal ao conectar no banco", e);
        }
    }
}