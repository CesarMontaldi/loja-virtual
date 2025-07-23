package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.ImagemProduto;
import br.com.cesarmontaldi.repository.ImagemProdutoRepository;

@Service
public class ImagemProdutoService {
	
	@Autowired
	private ImagemProdutoRepository repository;
	private Integer httpStatus;
	
	
	
	public ImagemProduto salvar(ImagemProduto imagemProduto) {
	
		ImagemProduto imagem = repository.save(imagemProduto);
		
		return imagem;
	}


	public List<ImagemProduto> buscarPorImagemProduto(Long idProduto) {
		
		List<ImagemProduto> imagens = repository.buscaImagemProduto(idProduto);
		
		if (imagens .isEmpty()) {
			throw new LojaVirtualException("Produto de ID: " + idProduto + " não possui imagens cadastradas.", httpStatus = 404);
		}
		
		return imagens;
	}
 

	public void deletarImagensProduto(Long idProduto) {
		
		repository.findById(idProduto)
		.orElseThrow(()-> new LojaVirtualException("Imagem do produto não encontrada.", httpStatus = 404));
		
		repository.deletarImagemPorId(idProduto);
	}
	
	public void deletarImagem(Long idImagem) {
	
		repository.findById(idImagem)
		.orElseThrow(()-> new LojaVirtualException("Imagem do produto não encontrada.", httpStatus = 404));
		
		repository.deleteById(idImagem);
	}
}
