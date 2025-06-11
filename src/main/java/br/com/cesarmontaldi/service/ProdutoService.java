package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.Produto;
import br.com.cesarmontaldi.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	private Integer statusCode;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	


	public Produto salvar(Produto produto) {
		
		
		if (produto.getEmpresa() == null || (produto.getEmpresa().getId() == null)) {
			throw new LojaVirtualException("A empresa deve ser informada!", statusCode = 422);
		}
		
		if (produto.getId() == null && produtoRepository.existsProduto(produto.getNome().toUpperCase(), produto.getEmpresa().getId())) {
			throw new LojaVirtualException("Já existe um produto com o nome: " + produto.getNome(), statusCode = 409);
		}
		
		if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0) {
			throw new LojaVirtualException("A empresa responsavel deve ser informada!", statusCode = 422);
		}
		
		if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0) {
			throw new LojaVirtualException("Categoria deve ser informada!", statusCode = 422);
		}
		
		if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0) {
			throw new LojaVirtualException("Marca do Produto deve ser informada!", statusCode = 422);
		}
		
		return produtoRepository.save(produto);
	}


	public List<Produto> buscarPorNome(String nome) {
		
		return produtoRepository.buscarProdutoNome(nome)
				.stream()
				.toList();
	}
	
	
	public Produto buscarProduto(Long idProduto) {
		
		Produto produto  = produtoRepository
				.findById(idProduto)
				.orElseThrow(()-> new LojaVirtualException("Produto não encontrado!", statusCode = 404));
		
		return produto;
	}
	
	
	public void deletar(Long idProduto) {
		
		if (!produtoRepository.existsById(idProduto)) {
			throw new LojaVirtualException("Produto não encontrado!", statusCode = 404);
		} else {
			produtoRepository.deleteById(idProduto);
		}
	}

}
