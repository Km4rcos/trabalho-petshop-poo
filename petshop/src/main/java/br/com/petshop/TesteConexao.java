package br.com.petshop;

import java.sql.Connection;

import br.com.petshop.util.ConnectionFactory;

public class TesteConexao {
    
    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTE ---");

        try {
            
            Connection con = ConnectionFactory.getConnection();
            
            if (con != null) {
                System.out.println("✅ SUCESSO! O Banco de Dados conectou!");
                con.close();
            }
            
        } catch (Exception e) {
            System.out.println("❌ ERRO: Ocorreu um problema.");
            e.printStackTrace();
        }
    }
}