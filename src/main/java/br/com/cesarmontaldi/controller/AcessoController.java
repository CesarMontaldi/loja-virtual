package br.com.cesarmontaldi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.Acesso;
import br.com.cesarmontaldi.service.AcessoService;

@RestController
public class AcessoController {

	@Autowired
	private AcessoService service;
	
	@PostMapping("salvarAcesso")
	public ResponseEntity<Acesso> salvaAcesso(@RequestBody Acesso acesso) {
		var newAcesso = service.salvarAcesso(acesso);  
		
		return new ResponseEntity<Acesso>(newAcesso, HttpStatus.OK);
	}
	
	@DeleteMapping("deleteAcesso/{id}")
	public ResponseEntity<Void> deletarAcesso(@PathVariable Long id) {
		
		service.deletarAcesso(id);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable Long id) {
		
		Acesso acesso = service.obterAcesso(id);
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}
	
}
