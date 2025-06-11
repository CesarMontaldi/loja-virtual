package br.com.cesarmontaldi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(nome) = upper(?1)")
	boolean existsProduto(String nome);
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(nome) = upper(?1) and empresa_id = ?2")
	boolean existsProduto(String nome, Long idEmpresa);
	
	@Query("select p from Produto p where upper(p.nome) like %?1%")
	List<Produto> buscarProdutoNome(String nome);
	
	@Query("select p from Produto p where upper(p.nome) like %?1%and p.empresa.id = ?2")
	List<Produto> buscarProdutoNome(String nome, Long idEmpresa);

}
