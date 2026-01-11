package br.com.petshop.controller;

import java.util.List;
import br.com.petshop.dao.PetDAO;
import br.com.petshop.dao.FactoryDAO;
import br.com.petshop.model.Pet;
import br.com.petshop.exception.BusinessException;

public class PetController {
    private PetDAO petDao;

    public PetController() {
        this.petDao = FactoryDAO.getPetDAO();
    }

    public void cadastrar(String nome, String especie, String raca, int idDono) {
        if (nome == null || nome.trim().isEmpty()) throw new BusinessException("Nome do pet é obrigatório!");
        if (especie == null || especie.trim().isEmpty()) throw new BusinessException("Espécie é obrigatória!");
        
        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        
        // Agora o buscarPorId existe no ClienteDAO
        pet.setDono(FactoryDAO.getClienteDAO().buscarPorId(idDono));

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

    public void excluir(int id) {
        petDao.excluir(id);
    }

    public List<Pet> listarTodos() {
        return petDao.listarTodos();
    }
}