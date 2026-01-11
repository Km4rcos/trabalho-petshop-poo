package br.com.petshop.model;

public class Pet {
    private int id;
    private String nome;
    private String tipo;
    private String cpfDono; 

    public Pet() {}

    public Pet(String nome, String tipo, String cpfDono) {
        this.nome = nome;
        this.tipo = tipo;
        this.cpfDono = cpfDono;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getCpfDono() { return cpfDono; }
    public void setCpfDono(String cpfDono) { this.cpfDono = cpfDono; }
}