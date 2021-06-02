package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager entityManager;

	public ProdutoDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void cadastrar(Produto produto) {
		this.entityManager.persist(produto);
	}
	
	public void atualizar(Produto produto) {
		this.entityManager.merge(produto);
	}
	
	public void remover(Produto produto) {
		produto = this.entityManager.merge(produto);
		this.entityManager.remove(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return this.entityManager.find(Produto.class, id);
		
	}
	
	public List<Produto> buscarTodos() {
		String jpql = "SELECT p FROM Produto p";
		return this.entityManager.createQuery(jpql, Produto.class).getResultList();
		
	}
	
	public List<Produto> buscarPorNome(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return this.entityManager.createQuery(jpql, Produto.class).setParameter("nome", nome).getResultList();
		
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String nome) {
		String jpql = "SELECT p FROM Produto p WHERE p.categoria.nome = :nome";
		return this.entityManager.createQuery(jpql, Produto.class).setParameter("nome", nome).getResultList();
		
	}
	
	public BigDecimal buscarPrecoDoProdutoPorNome(String nome) {
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return this.entityManager.createQuery(jpql, BigDecimal.class).setParameter("nome", nome).getSingleResult();
		
	}
	
	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
		String jpql = "SELECT p FROM Produto p WHERE 1=1 ";
		if(nome != null || nome.trim().isEmpty()) {
			jpql += "AND p.nome = :nome ";
		}
		if(preco != null) {
			jpql += "AND p.preco = :preco ";
		}
		if(dataCadastro != null) {
			jpql += "AND p.dataCadastro = :dataCadastro ";
		}
		TypedQuery<Produto> typedQuery = this.entityManager.createQuery(jpql, Produto.class);
		if(nome != null || nome.trim().isEmpty()) {
			typedQuery.setParameter("nome", nome);
		}
		if(preco != null) {
			typedQuery.setParameter("preco", preco);
		}
		if(dataCadastro != null) {
			typedQuery.setParameter("dataCadastro", dataCadastro);
		}
		return typedQuery.getResultList();
	}
	
	public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> from = criteriaQuery.from(Produto.class);
		Predicate filtros = criteriaBuilder.and();
		if(nome != null) {
			if(!nome.trim().isEmpty()) {
				filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("nome"), nome));
			}
		}
		if(preco != null) {
			filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("preco"), preco));
		}
		if(dataCadastro != null) {
			filtros = criteriaBuilder.and(filtros, criteriaBuilder.equal(from.get("dataCadastro"), dataCadastro));
		}
		criteriaQuery.where(filtros);
		
		return this.entityManager.createQuery(criteriaQuery).getResultList();
	}
}
