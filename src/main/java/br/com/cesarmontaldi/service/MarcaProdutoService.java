package br.com.cesarmontaldi.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.MarcaProduto;
import br.com.cesarmontaldi.repository.MarcaProdutoRepository;

@Service
public class MarcaProdutoService {
	
	private Integer statusCode;
	
	@Autowired
	private MarcaProdutoRepository repository;
	
	public MarcaProduto salvar(MarcaProduto marca) {
		
		if (repository.existsMarcaProdutoByNomeDescricao(marca.getNomeDescricao())) {
			throw new LojaVirtualException("Já existe Marca com o nome: " + marca.getNomeDescricao(), statusCode = 409);
		}
		return repository.save(marca);
	}

	public MarcaProduto obterMarca(Long id) {
		return repository.buscarMarcaID(id)
				.orElseThrow(()-> new LojaVirtualException("Não foi encontrado Marca com o ID: " + id, statusCode = 404));
	}
	
	public List<MarcaProduto> buscarMarcaPorNome(String nome) {
		return repository.buscarMarcaNome(nome);
	}
	
	public void deletarMarca(Long id) {
		repository.buscarMarcaID(id)
		.orElseThrow(()-> new EntityNotFoundException("Não foi encontrado Marca com o ID: " + id));
		
		repository.deleteById(id);
	}

}
