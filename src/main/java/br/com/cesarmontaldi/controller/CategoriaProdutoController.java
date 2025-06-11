package br.com.cesarmontaldi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.CategoriaProduto;
import br.com.cesarmontaldi.model.dto.CategoriaProdutoDTO;
import br.com.cesarmontaldi.service.CategoriaProdutoService;

@RestController
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoService categoriaProdutoService;
	
	@ResponseBody
	@PostMapping("/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto categoriaProduto) {
		
		CategoriaProduto categoria = categoriaProdutoService.salvar(categoriaProduto);
		
		CategoriaProdutoDTO novaCategoria = new CategoriaProdutoDTO(categoria);
		
		return new ResponseEntity<CategoriaProdutoDTO>(novaCategoria, HttpStatus.OK);
	}
	
	@GetMapping("/buscarCategoria/{idCategoria}")
	public ResponseEntity<CategoriaProdutoDTO> buscarCategoria(@PathVariable Long idCategoria) {
		
		CategoriaProdutoDTO categoria = categoriaProdutoService.buscarCategoria(idCategoria);
		
		return new ResponseEntity<CategoriaProdutoDTO>(categoria, HttpStatus.OK);
	}
	
	@GetMapping("/buscarCategoriaDescricao/{descricao}")
	public ResponseEntity<List<CategoriaProdutoDTO>> buscarCategoriaDescricao(@PathVariable String descricao) {
		
		List<CategoriaProdutoDTO> categorias = categoriaProdutoService.buscarPorDescricao(descricao.toUpperCase());
		
		return new ResponseEntity<List<CategoriaProdutoDTO>>(categorias, HttpStatus.OK);
	} 

	@DeleteMapping("/deletarCategoria/{idCategoria}")
	public ResponseEntity<?> deleteCategoria(@PathVariable Long idCategoria) {
		
		categoriaProdutoService.deletar(idCategoria);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
