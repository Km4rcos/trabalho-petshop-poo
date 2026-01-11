package br.com.petshop.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.petshop.dao.FactoryDAO; 
import br.com.petshop.dao.ServicoDAO;
import br.com.petshop.exception.BusinessException;
import br.com.petshop.model.Pet;
import br.com.petshop.model.Servico;
import br.com.petshop.model.StatusServico;
import br.com.petshop.observer.Observer;

public class ServicoController {

    private ServicoDAO dao;
    
    private List<Observer> observadores = new ArrayList<>();

    public ServicoController() {
        this.dao = FactoryDAO.getServicoDAO();
    }

    public void addObserver(Observer observer) {
        this.observadores.add(observer);
    }

    private void notificar(String mensagem) {
        for (Observer obs : observadores) {
            obs.atualizar(mensagem);
        }
    }

    public void agendar(String tipo, double valor, int idPet) {
    
    Pet p = FactoryDAO.getPetDAO().buscarPorId(idPet); 
    
    if (p == null) {
        throw new BusinessException("Pet não encontrado!");
    }

    Servico s = new Servico();
    s.setTipo(tipo);
    s.setValor(valor);
    s.setStatus(StatusServico.ESPERANDO);
    s.setPet(p);

    dao.salvar(s);
    
    notificar(" " + p.getNome() + " entrou na fila para: " + tipo);
}

    public void atualizarStatus(int idServico, StatusServico novoStatus) {
        dao.atualizarStatus(idServico, novoStatus);
        
        notificar("Status atualizado! Serviço " + idServico + " agora está: " + novoStatus);
    }
    
    public List<Servico> listarTodos() {
        return dao.listarTodos();
    }
}
