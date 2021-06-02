package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager entityManager = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(entityManager);
//		Produto p = produtoDao.buscarPorId(1l);
		
		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("Celulares");
		todos.forEach(p2 -> System.out.println(p2.getNome()));
		
		BigDecimal preco = produtoDao.buscarPrecoDoProdutoPorNome("POCO X3");
		System.out.println("Preço do produto :"+preco);
	}

	private static void cadastrarProduto() {
		Categoria categoria = new Categoria("Celulares");
		Produto celular = new Produto("POCO X3", "Muito Legal", new BigDecimal("800"), categoria);
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		CategoriaDao categoriaDao = new CategoriaDao(entityManager);
		
		ProdutoDao produtoDao = new ProdutoDao(entityManager);
		
		entityManager.getTransaction().begin();
		categoriaDao.cadastrar(categoria);
		produtoDao.cadastrar(celular);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
