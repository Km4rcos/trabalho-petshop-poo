package br.com.petshop.model;

public class Servico {
    
    private int id;
    private String tipo;
    private double valor;
    private StatusServico status;
    private Pet pet;

    public Servico() {this.status = StatusServico.ESPERANDO;}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public StatusServico getStatus() { return status; }
    public void setStatus(StatusServico status) { this.status = status; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }
}