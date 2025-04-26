package br.com.cesarmontaldi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.PessoaJuridica;
import br.com.cesarmontaldi.service.PessoaJuridicaService;

@RestController
public class PessoaJuridicaController {
	
	@Autowired
	private PessoaJuridicaService pessoaJuridicaService;
	
	
	@ResponseBody
	@PostMapping("/salvarPessoaJuridica")
	public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) {
		
		PessoaJuridica pessoaPJ = pessoaJuridicaService.salvar(pessoaJuridica);
		
		return new ResponseEntity<>(pessoaPJ, HttpStatus.OK);
	}

}
