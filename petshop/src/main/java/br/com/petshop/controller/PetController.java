package br.com.petshop.controller;

import java.util.List;
import br.com.petshop.dao.PetDAO;
import br.com.petshop.dao.FactoryDAO;
import br.com.petshop.model.Pet;
import br.com.petshop.model.Cliente;
import br.com.petshop.exception.BusinessException;

public class PetController {
    private PetDAO petDao;

    public PetController() {
        this.petDao = FactoryDAO.getPetDAO();
    }

    public void cadastrar(String nome, String especie, String raca, int idDono) {
        // 1. Validação de campos básicos
        if (nome == null || nome.trim().isEmpty()) throw new BusinessException("Nome do pet é obrigatório!");
        if (especie == null || especie.trim().isEmpty()) throw new BusinessException("Espécie é obrigatória!");
        
        // 2. PONTO 4: Validação de Existência do Dono (Blindagem)
        // Buscamos o cliente no banco para garantir que ele existe antes de vincular ao pet
        Cliente dono = FactoryDAO.getClienteDAO().buscarPorId(idDono);
        if (dono == null) {
            throw new BusinessException("Não foi possível cadastrar: O cliente selecionado não existe no sistema!");
        }

        Pet pet = new Pet();
        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        pet.setDono(dono);

        petDao.salvar(pet);
    }

    public void atualizar(int id, String nome, String especie, String raca) {
        // 1. Validação de existência do Pet
        Pet pet = petDao.buscarPorId(id);
        if (pet == null) throw new BusinessException("Pet não encontrado para atualização!");
        
        // 2. Validação de dados
        if (nome == null || nome.trim().isEmpty()) throw new BusinessException("O nome não pode ser vazio!");
        
        pet.setNome(nome);
        pet.setEspecie(especie);
        pet.setRaca(raca);
        
        petDao.atualizar(pet);
    }

    public void excluir(int id) {
        // Antes de excluir, poderíamos checar se o Pet existe, 
        // mas o PetDAO já lança exceção se nada for deletado.
        petDao.excluir(id);
    }

    public List<Pet> listarTodos() {
        return petDao.listarTodos();
    }

    // PONTO 7 (Antecipado): Necessário para a TelaListaPets editar corretamente
    public Pet buscarPorId(int id) {
        return petDao.buscarPorId(id);
    }
}