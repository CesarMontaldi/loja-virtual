package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.CategoriaProduto;
import br.com.cesarmontaldi.model.dto.CategoriaProdutoDTO;
import br.com.cesarmontaldi.repository.CategoriaProdutoRepository;

@Service
public class CategoriaProdutoService {
	
	private Integer statusCode;
	
	@Autowired
	private CategoriaProdutoRepository repository;

	
	public CategoriaProduto salvar(CategoriaProduto categoria) {
		
		if (categoria.getEmpresa() == null || (categoria.getEmpresa().getId() == null)) {
			throw new LojaVirtualException("A empresa deve ser informada!", statusCode = 422);
		}
		
		if (repository.existsCategoria(categoria.getNomeDescricao())) {
			throw new LojaVirtualException("Não é possivel cadastrar categoria com o mesmo nome.", statusCode = 409);
		}
		
		return repository.save(categoria);
	}


	public List<CategoriaProdutoDTO> buscarPorDescricao(String descricao) {
		
		return repository.buscarCategoriaDescricao(descricao)
				.stream()
				.map(CategoriaProdutoDTO::new)
				.toList();
	}
	
	public CategoriaProdutoDTO buscarCategoria(Long idCategoria) {
		
		CategoriaProduto categoria = repository
				.findById(idCategoria)
				.orElseThrow(()-> new LojaVirtualException("Categoria não encontrada!", statusCode = 404));
		
		return new CategoriaProdutoDTO(categoria);
	}
	
	
	public void deletar(Long idCategoria) {
		
		if (!repository.existsById(idCategoria)) {
			throw new LojaVirtualException("Categoria não encontrada!", statusCode = 404);
		} else {
			repository.deleteById(idCategoria);
		}
	}

}
