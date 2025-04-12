package br.com.cesarmontaldi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.model.PessoaJuridica;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	@Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
	public PessoaJuridica existsCnpjCadastrado(String cnpj); 

}
