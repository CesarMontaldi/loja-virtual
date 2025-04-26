package br.com.cesarmontaldi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.service.PessoaFisicaService;

@RestController
public class PessoaFisicaController {
	
	@Autowired
	private PessoaFisicaService pessoaFisicaService;
	
	
	@ResponseBody
	@PostMapping("/salvarPessoaFisica")
	public ResponseEntity<PessoaFisica> salvarPessoaFisica(@RequestBody PessoaFisica pessoaFisica) {
		
		PessoaFisica pessoaPF = pessoaFisicaService.salvar(pessoaFisica);
		
		return new ResponseEntity<>(pessoaPF, HttpStatus.OK);
	}

}
