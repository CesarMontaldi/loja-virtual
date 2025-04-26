package br.com.cesarmontaldi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.model.PessoaJuridica;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<Pessoa, Long> {
	
	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public PessoaJuridica existsCnpjCadastrado(String cnpj);

	@Query(value = "select pj from PessoaJuridica pj where pj.inscricaoEstadual = ?1")
	public PessoaJuridica existsInscricaoEstadualCadastrada(String inscricaoEstadual);

}
