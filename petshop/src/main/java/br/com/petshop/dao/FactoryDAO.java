package br.com.petshop.dao;

public class FactoryDAO {
    
    public static ClienteDAO getClienteDAO() {return new ClienteDAO();}
    public static PetDAO getPetDAO() {return new PetDAO();}
    public static ServicoDAO getServicoDAO() {return new ServicoDAO();}

}