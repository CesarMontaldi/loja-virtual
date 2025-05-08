package br.com.cesarmontaldi.enums;

public enum TipoPessoa {
	
	JURIDICA("Jutídica"),
	JURIDICA_FORNECEDOR("Jurídica e fornecedor"),
	FISICA("Física");
	
	private String descricao;
	
	private TipoPessoa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
