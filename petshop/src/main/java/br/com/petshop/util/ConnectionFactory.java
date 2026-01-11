package br.com.petshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    private static Connection conexao;

    public static Connection getConnection() {
    try {
        if (conexao == null || conexao.isClosed()) {
            conexao = DriverManager.getConnection("jdbc:sqlite:petshop.db");
            criarTabelas();
        }
        return conexao;
    } catch (SQLException e) {
        throw new RuntimeException("Erro fatal ao conectar no banco", e);
    }
}

private static void criarTabelas() {
    String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, cpf TEXT, telefone TEXT);";
    String sqlPets = "CREATE TABLE IF NOT EXISTS pets (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, especie TEXT, raca TEXT, id_cliente INTEGER, FOREIGN KEY(id_cliente) REFERENCES clientes(id));";
    String sqlServicos = "CREATE TABLE IF NOT EXISTS servicos (id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, valor REAL, status TEXT, id_pet INTEGER, FOREIGN KEY(id_pet) REFERENCES pets(id));";

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:petshop.db");
        Statement stmt = conn.createStatement()) {
        stmt.execute(sqlClientes);
        stmt.execute(sqlPets);
        stmt.execute(sqlServicos);
    } catch (SQLException e) {
        System.err.println("Erro ao criar tabelas: " + e.getMessage());
    }
}
}