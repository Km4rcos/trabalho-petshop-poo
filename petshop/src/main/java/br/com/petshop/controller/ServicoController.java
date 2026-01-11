package br.com.petshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import br.com.petshop.dao.FactoryDAO;
import br.com.petshop.dao.ServicoDAO;
import br.com.petshop.exception.BusinessException;
import br.com.petshop.model.Servico;
import br.com.petshop.model.StatusServico;
import br.com.petshop.observer.Observer;

public class ServicoController {
    private ServicoDAO servicoDao;
    private List<Observer> observadores = new ArrayList<>();

    // PONTO 5: Centralização de tipos de serviço para evitar erros de digitação nas telas
    public static final List<String> TIPOS_SERVICO = Arrays.asList(
        "Banho", 
        "Tosa", 
        "Banho + Tosa", 
        "Consulta", 
        "Vacina"
    );

    public ServicoController() {
        this.servicoDao = FactoryDAO.getServicoDAO();
    }

    public void addObserver(Observer o) {
        this.observadores.add(o);
    }

    private void notificar(String mensagem) {
        for (Observer obs : observadores) {
            obs.atualizar(mensagem);
        }
    }

    public void agendar(Servico s) {
        if (s.getPet() == null) throw new BusinessException("Selecione um Pet!");
        if (s.getValor() <= 0) throw new BusinessException("Valor deve ser maior que zero!");
        
        s.setStatus(StatusServico.AGENDADO); 
        servicoDao.salvar(s);
        notificar("Agendado: " + s.getTipo() + " para " + s.getPet().getNome());
    }

    public void alterar(Servico s) {
        if (s.getValor() <= 0) throw new BusinessException("Valor inválido!");
        servicoDao.atualizarDados(s);
        notificar("Serviço #" + s.getId() + " alterado para " + s.getTipo());
    }

    public void excluir(int id) {
        servicoDao.excluir(id);
        notificar("Serviço #" + id + " removido.");
    }

    public void atualizarStatus(int id, StatusServico novoStatus) {
        servicoDao.atualizarStatus(id, novoStatus);
        notificar("Serviço #" + id + " finalizado!");
    }

    public List<Servico> listarTodos() {
        return servicoDao.listarTodos();
    }
}