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

            String sqlCliente = "CREATE TABLE IF NOT EXISTS clientes ("
                    + "cpf TEXT PRIMARY KEY, "
                    + "nome TEXT, "
                    + "email TEXT, "
                    + "telefone TEXT"
                    + ")";
            st.execute(sqlCliente);

            String sqlPet = "CREATE TABLE IF NOT EXISTS pets ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "nome TEXT, "
                    + "tipo TEXT, " 
                    + "cpf_dono TEXT, " 
                    + "FOREIGN KEY(cpf_dono) REFERENCES clientes(cpf)"
                    + ")";
            st.execute(sqlPet);
            
            System.out.println("Tabelas 'clientes' e 'pets' criadas/verificadas!");
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}