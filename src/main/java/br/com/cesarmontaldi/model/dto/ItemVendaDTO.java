package br.com.cesarmontaldi.model.dto;

import java.util.List;

import br.com.cesarmontaldi.model.ItemVendaLoja;
import  br.com.cesarmontaldi.model.Produto;

public record ItemVendaDTO(Double quantidade, Produto produto) {
	
	public ItemVendaDTO(ItemVendaLoja itemVenda) {
		this(itemVenda.getQuantidade(), itemVenda.getProduto());
	}

}
