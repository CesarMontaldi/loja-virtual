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

import br.com.cesarmontaldi.model.NotaFiscalCompra;
import br.com.cesarmontaldi.service.NotaFiscalCompraService;

@RestController
public class NotaFiscalCompraController {
	
	@Autowired
	private NotaFiscalCompraService service;
	
	
	@PostMapping("salvarNotaFiscalCompra")
	public ResponseEntity<NotaFiscalCompra> salvarNotaFiscal(@RequestBody @Valid NotaFiscalCompra notaFiscal) {
		
		NotaFiscalCompra nota = service.salvarNota(notaFiscal);
		
		return new ResponseEntity<NotaFiscalCompra>(nota, HttpStatus.OK);
	}
	
	
	@GetMapping("obterNotaFiscal/{idNota}")
	public ResponseEntity<NotaFiscalCompra> obterMarca(@PathVariable Long idNota) {
		
		NotaFiscalCompra notaFiscal = service.obterNotaFiscal(idNota);
		
		return new ResponseEntity<NotaFiscalCompra>(notaFiscal, HttpStatus.OK);
	}
	
	@GetMapping("buscarNotas/{descricao}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarNotasPorDesc(@PathVariable String descricao) {
		
		List<NotaFiscalCompra> notas = service.buscaNotaFiscalCompraDesc(descricao.toUpperCase().trim());
		
		return new ResponseEntity<List<NotaFiscalCompra>>(notas, HttpStatus.OK);
	}
	
	@DeleteMapping("deletarNotaFiscal/{idNota}")
	public ResponseEntity<Void> deletarNotaFiscal(@PathVariable Long idNota) {
		
		service.deletarNotaFiscal(idNota);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
