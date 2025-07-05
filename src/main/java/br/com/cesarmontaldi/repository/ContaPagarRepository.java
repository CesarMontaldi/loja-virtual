package br.com.cesarmontaldi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cesarmontaldi.model.ContaPagar;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
	
	@Query(value = "select c from ContaPagar c where upper(c.descricao) like %?1%") 
	List<ContaPagar> buscarContaDesc(String descricao);
	
	Boolean existsContaPagarByDescricao(String descricao);

	@Query(value = "select c from ContaPagar c where c.pessoa.id = ?1")
	List<ContaPagar> buscaContaPorPessoa(Long idPessoa);

	@Query(value = "select c from ContaPagar c where c.pessoaFornecedor.id = ?1")
	List<ContaPagar> buscaContaPorFornecedor(Long idFornecedor);
}
