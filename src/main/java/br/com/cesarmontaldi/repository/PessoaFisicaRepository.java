package br.com.cesarmontaldi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.PessoaFisica;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
	
	public boolean existsByCpf(String cpf);
	
	@Query(value = "select pf from PessoaFisica pf where upper(pf.nome) like %?1%")
	public Page<PessoaFisica> consultaPorNome(String nome, Pageable paginacao);
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public PessoaFisica consultaPorCpf(String cpf);
	
}
