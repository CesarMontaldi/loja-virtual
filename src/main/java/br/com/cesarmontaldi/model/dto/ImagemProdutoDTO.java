package br.com.cesarmontaldi.model.dto;

import java.io.Serializable;

import br.com.cesarmontaldi.model.ImagemProduto;

public class ImagemProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String imagemOriginal;
	
	private String imagemMiniatura;
	
	private Long produto;
	
	private Long empresa;

	public ImagemProdutoDTO() {
		
	}
	
	public ImagemProdutoDTO(ImagemProduto image) {
		this.id = image.getId();
		this.imagemOriginal = image.getImagemOriginal();
		this.imagemMiniatura = image.getImagemMiniatura();
		this.produto = image.getProduto().getId();
		this.empresa = image.getEmpresa().getId();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImagemOriginal() {
		return imagemOriginal;
	}

	public void setImagemOriginal(String imagemOriginal) {
		this.imagemOriginal = imagemOriginal;
	}

	public String getImagemMiniatura() {
		return imagemMiniatura;
	}

	public void setImagemMiniatura(String imagemMiniatura) {
		this.imagemMiniatura = imagemMiniatura;
	}

	public Long getProduto() {
		return produto;
	}

	public void setProduto(Long produto) {
		this.produto = produto;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

}
