package br.com.cesarmontaldi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cesarmontaldi.model.NotaFiscalCompra;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {

	@Query("select nf from NotaFiscalCompra nf where upper(trim(nf.descricaoObservacao)) like %?1%")
	List<NotaFiscalCompra> buscarNotaDesc(String descricao);
 
	@Query("select nf from NotaFiscalCompra nf where nf.pessoa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorPessoa(Long idPessoa);
	
	@Query("select nf from NotaFiscalCompra nf where nf.contaPagar.id = ?1")
	List<NotaFiscalCompra> buscarNotaContaPagar(Long idContaPagar);
	
	@Query("select nf from NotaFiscalCompra nf where nf.empresa.id = ?1")
	List<NotaFiscalCompra> buscarNotaPorEmpresa(Long idEmpresa);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value = "delete from nota_item_produto where nota_fiscal_compra_id = ?1")
	void deleteItemNotaFiscalCompra(Long idNotaFiscalCompra);
}
