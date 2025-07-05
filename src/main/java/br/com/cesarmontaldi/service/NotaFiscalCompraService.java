package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.NotaFiscalCompra;
import br.com.cesarmontaldi.repository.NotaFiscalCompraRepository;

@Service
public class NotaFiscalCompraService {
	
	private Integer statusCode;
	
	@Autowired
	private NotaFiscalCompraRepository repository;
	
	public NotaFiscalCompra salvarNota(NotaFiscalCompra notaFiscal) {
			
		if (notaFiscal.getId() == null) {
			
			if (notaFiscal.getDescricaoObservacao() != null) {
				List<NotaFiscalCompra> notas = repository.buscarNotaDesc(notaFiscal.getDescricaoObservacao().toUpperCase().trim());
				if (! notas.isEmpty()) {
					throw new LojaVirtualException("Já existe Nota Fiscal de Compra com essa descrição! " + notaFiscal.getDescricaoObservacao(), statusCode = 409);
				}
			}
		}
		
		if (notaFiscal.getPessoa() == null || notaFiscal.getPessoa().getId() <= 0) {
			throw new LojaVirtualException("A Pessoa Juridica da nota fiscal deve ser informada!");
		}
		
		if (notaFiscal.getEmpresa() == null || notaFiscal.getEmpresa().getId() <= 0) {
			throw new LojaVirtualException("A Empresa responsável deve ser informada!");
		}
		
		if (notaFiscal.getContaPagar() == null || notaFiscal.getContaPagar().getId() <= 0) {
			throw new LojaVirtualException("A conta a pagar da nota deve ser informada!");
		}
		
		return repository.save(notaFiscal);
	}
	
	public NotaFiscalCompra obterNotaFiscal(Long idNotaFiscal) {
		return repository.findById(idNotaFiscal)
				.orElseThrow(()-> new LojaVirtualException("Não foi encontrado Nota Fiscal de Compra com o ID: " + idNotaFiscal, statusCode = 404));
	}
	
	public List<NotaFiscalCompra> buscaNotaFiscalCompraDesc(String descricao) {
		return repository.buscarNotaDesc(descricao);
	}
	
	public void deletarItemNotaFiscal(Long idItemNotaFiscal) {
		repository.findById(idItemNotaFiscal)
		.orElseThrow(()-> new LojaVirtualException("Não foi encontrado Nota Fiscal de Compra para o Item " , statusCode = 404));
		
		repository.deleteItemNotaFiscalCompra(idItemNotaFiscal);
	}
	
	public void deletarNotaFiscal(Long idNotaFiscal) {
		repository.findById(idNotaFiscal)
		.orElseThrow(()-> new LojaVirtualException("Não foi encontrado Nota Fiscal de Compra com o ID: " + idNotaFiscal, statusCode = 404));
		
		repository.deleteItemNotaFiscalCompra(idNotaFiscal);
		repository.deleteById(idNotaFiscal);
	}

}
