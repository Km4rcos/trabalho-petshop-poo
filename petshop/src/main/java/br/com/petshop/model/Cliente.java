package br.com.petshop.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private List<Pet> pets;

    public Cliente(){this.pets = new ArrayList<>();}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    
    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public String getNome() {return nome;}public void setNome(String nome) {this.nome = nome;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public List<Pet> getPets() { return pets; }
    public void setPets(List<Pet> pets) { this.pets = pets; }
}