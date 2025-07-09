package br.com.cesarmontaldi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.NotaItemProduto;
import br.com.cesarmontaldi.service.NotaItemProdutoService;

@RestController
public class NotaItemProdutoController {

	@Autowired
	private NotaItemProdutoService service;
	
	@PostMapping("/salvarNotaItem")
	public ResponseEntity<NotaItemProduto> salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItem) {
		
		NotaItemProduto notaItemProduto = service.salvarNotaItem(notaItem);
		
		return new ResponseEntity<NotaItemProduto>(notaItemProduto, HttpStatus.OK);
	}
	
	@GetMapping("obterNotaItemProduto/{idNota}")
	public ResponseEntity<NotaItemProduto> obterNota(@PathVariable Long idNota) {
		
		NotaItemProduto notaItem = service.buscarNota(idNota);
		
		return new ResponseEntity<NotaItemProduto>(notaItem, HttpStatus.OK);
	}
	
	@DeleteMapping("deletarNotaItemProduto/{idNotaItem}")
	public ResponseEntity<Void> deletarNotaItem(@PathVariable Long idNotaItem) {
		
		service.deletarNota(idNotaItem);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
