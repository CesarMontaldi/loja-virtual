package br.com.cesarmontaldi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cesarmontaldi.model.NotaItemProduto;

@Repository
@Transactional
public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {
	
	@Query("select nf from NotaItemProduto nf where nf.produto.id = ?1 and nf.notaFiscalCompra.id = ?2")
	List<NotaItemProduto> buscaNotaItemPorProdutoNota(Long idProduto, Long idNotaFiscal);

	@Query("select nf from NotaItemProduto nf where nf.produto.id = ?1")
	List<NotaItemProduto> buscaNotaItemPorProduto(Long idProduto);
	
	@Query("select nf from NotaItemProduto nf where nf.notaFiscalCompra.id = ?1")
	List<NotaItemProduto> buscaItemProdutoNotaFiscal(Long idNotaFiscal);
	
	@Query("select nf from NotaItemProduto nf where nf.empresa.id = ?1")
	List<NotaItemProduto> buscaNotaItemPorEmpresa(Long idEmpresa);
}
