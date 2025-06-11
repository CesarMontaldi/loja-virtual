package br.com.cesarmontaldi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.cesarmontaldi.model.CategoriaProduto;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from categoria_produto where upper(nome_descricao) = upper(?1)")
	public boolean existsCategoria(String nomeCategoria);

	@Query("select c from CategoriaProduto c where upper(trim(c.nomeDescricao)) like %?1%")
	public List<CategoriaProduto> buscarCategoriaDescricao(String descricao);

}
