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

import br.com.cesarmontaldi.model.ContaPagar;
import br.com.cesarmontaldi.model.Produto;
import br.com.cesarmontaldi.service.ContaPagarService;
import br.com.cesarmontaldi.service.ProdutoService;

@RestController
public class ContaPagarController {
	
	@Autowired
	private ContaPagarService contaService;

	@PostMapping("/salvarContaPagar")
	public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody ContaPagar conta) {
		
		ContaPagar newConta = contaService.salvar(conta);
		
		return new ResponseEntity<ContaPagar>(newConta, HttpStatus.OK);
	}
	
	@GetMapping("/contaPagar/{idConta}")
	public ResponseEntity<ContaPagar> buscarContaPorID(@PathVariable Long idConta) {
		
		ContaPagar conta = contaService.buscarContaPagar(idConta);
		
		return new ResponseEntity<ContaPagar>(conta, HttpStatus.OK);
	}
	
	@GetMapping("/buscarContaPagar/{descricao}")
	public ResponseEntity<List<ContaPagar>> buscarContaPorDescr(@PathVariable String descricao) {
		
		List<ContaPagar> contas = contaService.buscarPorDesc(descricao.toUpperCase());
		
		return new ResponseEntity<List<ContaPagar>>(contas, HttpStatus.OK);
	} 

	@DeleteMapping("/deletarConta/{idConta}")
	public ResponseEntity<?> delete(@PathVariable Long idConta) {
		
		contaService.deletar(idConta);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
