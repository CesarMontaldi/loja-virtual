package br.com.cesarmontaldi.controller;

import java.io.IOException;
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

import br.com.cesarmontaldi.model.Produto;
import br.com.cesarmontaldi.service.ProdutoService;

@RestController
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@ResponseBody
	@PostMapping("/salvarProduto")
	public ResponseEntity<Produto> salvarProduto(@RequestBody Produto produto) throws IOException {
		
		Produto newProduto = produtoService.salvar(produto);
		
		return new ResponseEntity<Produto>(newProduto, HttpStatus.OK);
	}
	
	@GetMapping("/buscarProduto/{idProduto}")
	public ResponseEntity<Produto> buscarProduto(@PathVariable Long idProduto) {
		
		Produto produto = produtoService.buscarProduto(idProduto);
		
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	
	@GetMapping("/buscarProdutoNome/{nome}")
	public ResponseEntity<List<Produto>> buscarProdutoNome(@PathVariable String nome) {
		
		List<Produto> produtos = produtoService.buscarPorNome(nome.toUpperCase());
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	} 

	@DeleteMapping("/deletarProduto/{idProduto}")
	public ResponseEntity<?> deletar(@PathVariable Long idProduto) {
		
		produtoService.deletar(idProduto);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
