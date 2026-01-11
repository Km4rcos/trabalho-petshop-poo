package br.com.petshop;

import java.sql.Connection;
import br.com.petshop.dao.ConnectionFactory;

public class TesteConexao {

    public static void main(String[] args) {
        try {
            Connection con = ConnectionFactory.getConnection();
            System.out.println("Conexão realizada com sucesso!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}