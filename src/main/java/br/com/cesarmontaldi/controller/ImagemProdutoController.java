package br.com.cesarmontaldi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.ImagemProduto;
import br.com.cesarmontaldi.model.dto.ImagemProdutoDTO;
import br.com.cesarmontaldi.service.ImagemProdutoService;

@RestController
public class ImagemProdutoController {

	@Autowired
	private ImagemProdutoService service;
	
	@PostMapping(value = "**/salvarImagemProduto")
	public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto) {
		
		ImagemProduto newImagemProduto = service.salvar(imagemProduto);
		
		ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO(newImagemProduto);
		
		return new ResponseEntity<ImagemProdutoDTO>(imagemProdutoDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "**/obterImagePorProduto/{idProduto}")
	public ResponseEntity<List<ImagemProdutoDTO>> obterImagemProduto(@PathVariable Long idProduto) {
		
		List<ImagemProduto> imagens = service.buscarPorImagemProduto(idProduto);
		
		List<ImagemProdutoDTO> imagensDTO = new ArrayList<>();
		imagens.stream().forEach(imagemProduto -> {
			ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO(imagemProduto);
			imagensDTO.add(imagemProdutoDTO);
		});
		
		return new ResponseEntity<>(imagensDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "**/deletarImagensProdutoId/{idProduto}")
	public ResponseEntity<Void> deletarImagensProduto(@PathVariable Long idProduto) {
		
		service.deletarImagensProduto(idProduto);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
	
	@DeleteMapping(value = "**/deletarImagem/{idImagem}")
	public ResponseEntity<Void> deletarImagem(@PathVariable Long idImagem) {
		
		service.deletarImagem(idImagem);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
	}
}
