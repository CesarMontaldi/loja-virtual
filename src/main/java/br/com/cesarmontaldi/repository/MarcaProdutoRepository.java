package br.com.cesarmontaldi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cesarmontaldi.model.MarcaProduto;

@Repository
@Transactional
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {
	
	Boolean existsMarcaProdutoByNomeDescricao(String nomeDescricao);
	
	@Query(value = "select m from MarcaProduto m where m.id = ?1")
	Optional<MarcaProduto> buscarMarcaID(Long id);
	
	@Query(value = "select m from MarcaProduto m where upper(trim(m.nomeDescricao)) like %?1%")
	List<MarcaProduto> buscarMarcaNome(String nome);
}
