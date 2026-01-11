package br.com.petshop.controller;

import java.util.List;
import br.com.petshop.dao.PetDAO;
import br.com.petshop.dao.FactoryDAO;
import br.com.petshop.model.Pet;
import br.com.petshop.exception.BusinessException;
import br.com.petshop.model.Cliente;

public class PetController {
    private PetDAO petDao;

    public PetController() {
        this.petDao = FactoryDAO.getPetDAO();
    }

    public void cadastrar(String nome, String especie, String raca, int idDono) {
        if (nome == null || nome.trim().isEmpty()) throw new BusinessException("Nome do pet é obrigatório!");
        if (especie == null || especie.trim().isEmpty()) throw new BusinessException("Espécie é obrigatória!");
        
        Cliente dono = FactoryDAO.getClienteDAO().buscarPorId(idDono);
        if (dono == null) throw new BusinessException("Dono não encontrado!");

        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        pet.setDono(dono);

        petDao.salvar(pet);
    }

    public void atualizar(int id, String nome, String especie, String raca) {
        Pet pet = petDao.buscarPorId(id);
        if (pet == null) throw new BusinessException("Pet não encontrado!");
        
        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        
        petDao.atualizar(pet);
    }

    // PONTO 7: Método para buscar um pet específico sem carregar a lista toda
    public Pet buscarPorId(int id) {
        Pet pet = petDao.buscarPorId(id);
        if (pet == null) throw new BusinessException("Pet não localizado no banco de dados.");
        return pet;
    }

    public void excluir(int id) {
        petDao.excluir(id);
    }

    public List<Pet> listarTodos() {
        return petDao.listarTodos();
    }
}