package br.com.cesarmontaldi.controller;

import java.util.List;

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

import br.com.cesarmontaldi.model.AvaliacaoProduto;
import br.com.cesarmontaldi.service.AvaliacaoProdutoService;

@RestController
public class AvaliacaoProdutoController {

	@Autowired
	private AvaliacaoProdutoService service;
	
	@PostMapping("**/salvarAvaliacaoProduto")
	public ResponseEntity<AvaliacaoProduto> salvarAvaliacaoProduto(@RequestBody @Valid AvaliacaoProduto avaliacao) {
		
		AvaliacaoProduto novaAvaliacao = service.salvarAvaliacao(avaliacao);
		
		return new ResponseEntity<AvaliacaoProduto>(novaAvaliacao, HttpStatus.OK);
	}
	
	@GetMapping("avaliacaoProduto/{idProduto}")
	public ResponseEntity<List<AvaliacaoProduto>> avaliacaoProduto(@PathVariable Long idProduto) {
		
		List<AvaliacaoProduto> avaliacaoProdutos = service.listarAvaliacaoProduto(idProduto);
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutos, HttpStatus.OK);
	}
	
	@GetMapping("avaliacaoPessoa/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> avaliacaoPessoao(@PathVariable Long idPessoa) {
		
		List<AvaliacaoProduto> avaliacaoPessoa = service.listarAvaliacaoPessoa(idPessoa);
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoPessoa, HttpStatus.OK);
	}
	
	@GetMapping("avaliacaoProdutoPessoa/{idProduto}/{idPessoa}")
	public ResponseEntity<List<AvaliacaoProduto>> avaliacaoProdutoPessoa(@PathVariable Long idProduto, @PathVariable Long idPessoa) {
		
		List<AvaliacaoProduto> avaliacaoProdutos = service.listarAvaliacaoProdutoPessoa(idProduto, idPessoa);
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutos, HttpStatus.OK);
	}
	
	@DeleteMapping("deletarAvaliacao/{idAvaliacao}")
	public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long idAvaliacao) {
		
		service.deletar(idAvaliacao);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	} 
}
