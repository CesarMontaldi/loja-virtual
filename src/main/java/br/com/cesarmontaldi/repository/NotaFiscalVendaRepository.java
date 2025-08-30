package br.com.cesarmontaldi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.NotaFiscalVenda;

@Repository
public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {

}
