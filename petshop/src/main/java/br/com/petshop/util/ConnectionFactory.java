package br.com.petshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    private static final String URL = "jdbc:sqlite:petshop.db";

    public static Connection getConnection() {
        try {
            Connection conexao = DriverManager.getConnection(URL);
            criarTabelas(conexao);
            return conexao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro fatal ao conectar no banco de dados SQLite", e);
        }
    }

    private static void criarTabelas(Connection conn) {
        String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "cpf TEXT UNIQUE NOT NULL, "
                + "telefone TEXT, "
                + "email TEXT);";

        String sqlPets = "CREATE TABLE IF NOT EXISTS pets ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "especie TEXT, "
                + "raca TEXT, "
                + "id_cliente INTEGER, "
                + "FOREIGN KEY(id_cliente) REFERENCES clientes(id));";

        String sqlServicos = "CREATE TABLE IF NOT EXISTS servicos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "tipo TEXT NOT NULL, "
                + "valor REAL, "
                + "status TEXT, "
                + "id_pet INTEGER, "
                + "FOREIGN KEY(id_pet) REFERENCES pets(id));";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlClientes);
            stmt.execute(sqlPets);
            stmt.execute(sqlServicos);
        } catch (SQLException e) {
            System.err.println("Erro ao sincronizar tabelas: " + e.getMessage());
        }
    }
}