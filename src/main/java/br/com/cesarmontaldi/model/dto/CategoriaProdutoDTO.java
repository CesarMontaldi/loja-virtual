package br.com.cesarmontaldi.model.dto;

import java.io.Serializable;

import br.com.cesarmontaldi.model.CategoriaProduto;

public record CategoriaProdutoDTO(Long id, String nomeDescricao, Long idEmpresa) implements Serializable {
	
	public CategoriaProdutoDTO(CategoriaProduto categoria) {
		this(categoria.getId(), categoria.getNomeDescricao(), categoria.getEmpresa().getId());
	}

}
