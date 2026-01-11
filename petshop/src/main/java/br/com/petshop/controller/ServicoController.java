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

    public ServicoController() { this.dao = FactoryDAO.getServicoDAO(); }

    public void addObserver(Observer o) { this.observadores.add(o); }

    private void notificar(String msg) {
        for (Observer o : observadores) o.atualizar(msg);
    }

    public void agendar(String tipo, double valor, int idPet) {
        if (valor <= 0) throw new BusinessException("O valor deve ser maior que zero!");
        
        Pet p = FactoryDAO.getPetDAO().buscarPorId(idPet);
        if (p == null) throw new BusinessException("Pet não encontrado!");

        Servico s = new Servico();
        s.setTipo(tipo);
        s.setValor(valor);
        s.setPet(p);

        dao.salvar(s);
        notificar(p.getNome() + " agendou " + tipo + " (R$ " + valor + ")");
    }

    public void atualizarStatus(int id, StatusServico status) {
        dao.atualizarStatus(id, status);
        notificar("Serviço #" + id + " atualizado para " + status);
    }

    public List<Servico> listarTodos() { return dao.listarTodos(); }
}