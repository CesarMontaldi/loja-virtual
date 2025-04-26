package br.com.cesarmontaldi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.model.PessoaJuridica;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<Pessoa, Long> {
	
	@Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
	public PessoaFisica existsCpfCadastrado(String cpf);

}
