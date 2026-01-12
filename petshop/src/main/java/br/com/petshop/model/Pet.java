package br.com.petshop.model;

import java.util.ArrayList;
import java.util.List;

public class Pet extends Animal {
    
    private Cliente dono;
    private List<Servico> historicoServicos;

    public Pet() {
        super();
        this.historicoServicos = new ArrayList<>();
    }

    public Pet(String nome, String especie, String raca, Cliente dono) {
        this.setNome(nome);
        this.setEspecie(especie);
        this.setRaca(raca);
        this.dono = dono;
        this.historicoServicos = new ArrayList<>();
    }
    
    public Cliente getDono() { return dono; }
    public void setDono(Cliente dono) { this.dono = dono; }

    public List<Servico> getHistoricoServicos() { return historicoServicos; }
    public void adicionarServico(Servico servico) {this.historicoServicos.add(servico);}
}