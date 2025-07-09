package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.NotaItemProduto;
import br.com.cesarmontaldi.repository.NotaItemProdutoRepository;

@Service
public class NotaItemProdutoService {
	
	private Integer statusCode;
	
	@Autowired
	private NotaItemProdutoRepository repository;
	
	public NotaItemProduto salvarNotaItem(NotaItemProduto notaItem) {
		
		if (notaItem.getId() == null) {
			
			if (notaItem.getProduto() == null || notaItem.getProduto().getId() < 0) {
				throw new LojaVirtualException("O produto deve ser informado.");
			}
			
			if (notaItem.getNotaFiscalCompra() == null || notaItem.getNotaFiscalCompra().getId() < 0) {
				throw new LojaVirtualException("A nota fiscal deve ser informada.");
			}
			
			if (notaItem.getEmpresa() == null || notaItem.getEmpresa().getId() < 0) {
				throw new LojaVirtualException("A empresa deve ser informada.");
			}
			
			List<NotaItemProduto> notaExistente = repository
					.buscaNotaItemPorProdutoNota(notaItem.getProduto().getId(), notaItem.getNotaFiscalCompra().getId());
			if (! notaExistente.isEmpty()) {
				throw new LojaVirtualException(notaItem.getProduto().getDescricao() + " já cadastrado nesta Nota Fiscal.");
			}
		}
		
		if (notaItem.getQuantidade() <= 0) {
			throw new LojaVirtualException("A quantidade do produto deve ser informada.");
		}
		
		return repository.save(notaItem);
	}
	
	public NotaItemProduto buscarNota(Long idNota) {
		return repository.findById(idNota)
		.orElseThrow(()-> new LojaVirtualException("Não foi encontrado NotaItemProduto para o ID: " + idNota, statusCode = 404));
	}
	
	public void deletarNota(Long idNota) {
		repository.findById(idNota)
		.orElseThrow(()-> new LojaVirtualException("Não foi encontrado NotaItemProduto para o ID: " + idNota, statusCode = 404));
		
		repository.deleteById(idNota);
	}

}
