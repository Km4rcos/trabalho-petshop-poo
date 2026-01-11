package br.com.petshop.model;

public class Pet extends Animal {
    private Cliente dono;

    public Pet() {super();}

    public Cliente getDono() { return dono; }
    public void setDono(Cliente dono) { this.dono = dono; }
}