package br.com.cesarmontaldi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.PessoaJuridica;
import br.com.cesarmontaldi.model.dto.ConsultaCnpjDTO;
import br.com.cesarmontaldi.service.ConsultaCnpjService;
import br.com.cesarmontaldi.service.PessoaJuridicaService;

@RestController
public class PessoaJuridicaController {
	
	@Autowired
	private PessoaJuridicaService pessoaJuridicaService;
	
	
	@ResponseBody
	@PostMapping("/salvarPessoaJuridica")
	public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@Valid @RequestBody PessoaJuridica pessoaJuridica) {
		
		PessoaJuridica pessoaPJ = pessoaJuridicaService.salvar(pessoaJuridica);
		
		return new ResponseEntity<>(pessoaPJ, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/consultaPJPorNome/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaPorNome(@PathVariable String nome, @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		
		List<PessoaJuridica> pessoas = pessoaJuridicaService.consultaPorNome(nome, paginacao);
		
		return new ResponseEntity<List<PessoaJuridica>>(pessoas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/consultaPorCnpj/{cnpj}")
	public ResponseEntity<PessoaJuridica> consultaPorCpf(@PathVariable String cnpj) {
		
		PessoaJuridica pessoa = pessoaJuridicaService.consultaPorCnpj(cnpj);
		
		return new ResponseEntity<PessoaJuridica>(pessoa, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/consultaCnpj/{cnpj}")
	public ResponseEntity<ConsultaCnpjDTO> consultaCnpj(@PathVariable String cnpj) {
		
		return new ResponseEntity<ConsultaCnpjDTO>(ConsultaCnpjService.consultaCnpj(cnpj), HttpStatus.OK);
	}

}
