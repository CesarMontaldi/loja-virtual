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

import br.com.cesarmontaldi.model.MarcaProduto;
import br.com.cesarmontaldi.service.MarcaProdutoService;

@RestController
public class MarcaProdutoController {

	@Autowired
	private MarcaProdutoService service;
	
	
	@PostMapping("salvarMarcaProduto")
	public ResponseEntity<MarcaProduto> salvaMarcaProduto(@Valid @RequestBody MarcaProduto marca) {

		MarcaProduto newMarca = service.salvar(marca);  
		
		return new ResponseEntity<MarcaProduto>(newMarca, HttpStatus.OK);
	}
	
	@GetMapping("obterMarca/{id}")
	public ResponseEntity<MarcaProduto> obterMarca(@PathVariable Long id) {
		
		MarcaProduto marca = service.obterMarca(id);
		
		return new ResponseEntity<MarcaProduto>(marca, HttpStatus.OK);
	}
	

	@GetMapping("buscarMarcaNome/{nome}")
	public ResponseEntity<List<MarcaProduto>> buscarMarca(@PathVariable String nome) {
		
		List<MarcaProduto> marcas = service.buscarMarcaPorNome(nome.toUpperCase());
		
		return new ResponseEntity<List<MarcaProduto>>(marcas, HttpStatus.OK);
	}
	
	
	@DeleteMapping("deleteMarca/{id}")
	public ResponseEntity<Void> deletarMarca(@PathVariable Long id) {
		
		service.deletarMarca(id);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
}
