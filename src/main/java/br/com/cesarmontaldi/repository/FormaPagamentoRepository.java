package br.com.cesarmontaldi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

	//@Query(value = "select f from FormaPagamento f where upper(f.descricao) = ?1")
	boolean existsFormaPagamentoByDescricao(String descricao);
}
