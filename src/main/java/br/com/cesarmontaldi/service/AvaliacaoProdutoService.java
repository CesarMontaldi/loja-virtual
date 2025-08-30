package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.AvaliacaoProduto;
import br.com.cesarmontaldi.repository.AvaliacaoProdutoRepository;

@Service
public class AvaliacaoProdutoService {
	
	private Integer statusCode;
	
	@Autowired
	private AvaliacaoProdutoRepository repository;
	

	public AvaliacaoProduto salvarAvaliacao(AvaliacaoProduto avaliacao) {
		
		if (avaliacao.getEmpresa() == null || (avaliacao.getEmpresa() != null && avaliacao.getEmpresa().getId() <= 0)) {
			throw new LojaVirtualException("Informe a empresa dona do registro.");
		}
		
		if (avaliacao.getProduto() == null || (avaliacao.getProduto() != null && avaliacao.getProduto().getId() <= 0)) {
			throw new LojaVirtualException("A avaliacao deve conter um produto associado.");
		}
		
		if (avaliacao.getPessoa() == null || (avaliacao.getPessoa() != null && avaliacao.getPessoa().getId() <= 0)) {
			throw new LojaVirtualException("A avaliacao deve conter uma pessoa ou cliente associado.");
		}
		
		return repository.save(avaliacao);
	}
	
	public List<AvaliacaoProduto> listarAvaliacaoProduto(Long idProduto) {
		
		List<AvaliacaoProduto> avaliacoes = repository.listAvaliacaoProduto(idProduto);
		
		return avaliacoes.stream().toList();
	}
	
	public List<AvaliacaoProduto> listarAvaliacaoPessoa(Long idPessoa) {
		
		List<AvaliacaoProduto> avaliacoes = repository.listAvaliacaoPessoa(idPessoa);
		
		return avaliacoes.stream().toList();
	}
	
	public List<AvaliacaoProduto> listarAvaliacaoProdutoPessoa(Long idProduto, Long idPessoa) {
		
		List<AvaliacaoProduto> avaliacoes = repository.listAvaliacaoProdutoPessoa(idProduto, idPessoa);
		
		return avaliacoes.stream().toList();
	}
	
	public void deletar(Long idAvaliacao) {
		if (!repository.existsById(idAvaliacao)) {
			throw new LojaVirtualException("Avaliacao não encontrada!", statusCode = 404);
		}
		repository.deleteById(idAvaliacao);
	}

}
