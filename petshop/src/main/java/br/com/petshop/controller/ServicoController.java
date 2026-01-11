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

    public static final List<String> TIPOS_SERVICO = Arrays.asList(
        "Banho", "Tosa", "Banho + Tosa", "Consulta", "Vacina"
    );

    public ServicoController() {
        this.servicoDao = FactoryDAO.getServicoDAO();
    }

    public void addObserver(Observer o) { this.observadores.add(o); }

    private void notificar(String mensagem) {
        for (Observer obs : observadores) obs.atualizar(mensagem);
    }

    public void agendar(Servico s) {
        if (s.getPet() == null) throw new BusinessException("Selecione um Pet!");
        if (s.getValor() <= 0) throw new BusinessException("Valor inválido!");
        s.setStatus(StatusServico.AGENDADO); 
        servicoDao.salvar(s);
        notificar("Agendado: " + s.getTipo() + " para " + s.getPet().getNome());
    }

    // MUDANÇA: Agora cancelamos em vez de excluir fisicamente
    public void cancelar(int id) {
        servicoDao.atualizarStatus(id, StatusServico.CANCELADO);
        notificar("Serviço #" + id + " foi CANCELADO.");
    }

    public void atualizarStatus(int id, StatusServico novoStatus) {
        servicoDao.atualizarStatus(id, novoStatus);
        notificar("Serviço #" + id + " atualizado para " + novoStatus);
    }

    public double obterFaturamentoTotal() {
        return servicoDao.calcularTotalFinalizados();
    }

    public List<Servico> listarTodos() {
        return servicoDao.listarTodos();
    }
    public void alterar(Servico s) {
    if (s.getValor() <= 0) throw new BusinessException("Valor inválido!");
    servicoDao.atualizarDados(s); // Verifica se o DAO tem esse método
    notificar("Serviço #" + s.getId() + " alterado para " + s.getTipo());
}
}