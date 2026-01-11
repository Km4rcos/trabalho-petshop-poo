package br.com.petshop;

import java.sql.Connection;
import java.sql.Statement;

import br.com.petshop.dao.ConnectionFactory;

public class SetupBanco {

    public static void main(String[] args) {
        try {
            System.out.println("🔨 Criando tabelas no banco...");
            
            Connection con = ConnectionFactory.getConnection();
            Statement st = con.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS clientes ("
                    + "cpf TEXT PRIMARY KEY, "
                    + "nome TEXT, "
                    + "email TEXT, "
                    + "telefone TEXT"
                    + ")";
            
            st.execute(sql);
            
            System.out.println("Tabela 'clientes' criada com sucesso!");
            con.close();
            
        } catch (Exception e) {
            System.out.println("Erro ao criar tabelas:");
            e.printStackTrace();
        }
    }
}