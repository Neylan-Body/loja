package br.com.alura.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Cliente;

public class ClienteDao {

	private EntityManager entityManager;

	public ClienteDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void cadastrar(Cliente cliente) {
		this.entityManager.persist(cliente);
	}
	
	public void atualizar(Cliente cliente) {
		this.entityManager.merge(cliente);
	}
	
	public void remover(Cliente cliente) {
		cliente = this.entityManager.merge(cliente);
		this.entityManager.remove(cliente);
	}
	
	public Cliente buscarPorId(Long id) {
		return this.entityManager.find(Cliente.class, id);
		
	}
	
	public List<Cliente> buscarTodos() {
		String jpql = "SELECT c FROM Cliente c";
		return this.entityManager.createQuery(jpql, Cliente.class).getResultList();
		
	}
	
	public List<Cliente> buscarPorNome(String nome) {
		String jpql = "SELECT c FROM Cliente c WHERE c.nome = :nome";
		return this.entityManager.createQuery(jpql, Cliente.class).setParameter("nome", nome).getResultList();
		
	}
	
	public Cliente buscarPorCpf(String cpf) {
		String jpql = "SELECT c FROM Cliente c WHERE c.cpf = :cpf";
		return this.entityManager.createQuery(jpql, Cliente.class).setParameter("cpf", cpf).getSingleResult();
		
	}
}
