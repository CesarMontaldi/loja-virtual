package br.com.cesarmontaldi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cesarmontaldi.model.AvaliacaoProduto;

@Repository
@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long>{

	@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1")
	List<AvaliacaoProduto> listAvaliacaoProduto(Long idProduto);
	
	@Query(value = "select a from AvaliacaoProduto a where a.pessoa.id = ?1")
	List<AvaliacaoProduto> listAvaliacaoPessoa(Long idPessoa);
	
	//@Query(value = "select a from AvaliacaoProduto a where a.produto.id = ?1and a.pessoa.id = ?2")
	@Query(value = "select * from avaliacao_produto where produto_id = ?1 and pessoa_id = ?2", nativeQuery = true)
	List<AvaliacaoProduto> listAvaliacaoProdutoPessoa(Long idProduto, Long idPessoa);
}
// select * from avaliacao_produto where produto_id = 1 and pessoa_id = 60