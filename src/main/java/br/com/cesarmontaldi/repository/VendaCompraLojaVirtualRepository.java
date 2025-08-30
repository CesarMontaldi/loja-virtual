package br.com.cesarmontaldi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cesarmontaldi.model.VendaCompraLojaVirtual;

@Repository
public interface VendaCompraLojaVirtualRepository extends JpaRepository<VendaCompraLojaVirtual, Long>{

}
