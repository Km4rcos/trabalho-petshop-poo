package br.com.petshop.model;

import java.util.ArrayList;
import java.util.List;

public class Pet extends Animal {
    private Cliente dono;

    private List<Servico> historicoServicos = new ArrayList<>();

    public Pet() {
        super();
    }

    public Cliente getDono() { return dono; }
    public void setDono(Cliente dono) { this.dono = dono; }

    public List<Servico> getHistorico() {
        return this.historicoServicos;
    }

    public void adicionarServico(Servico servico) {
        this.historicoServicos.add(servico);
    }
}