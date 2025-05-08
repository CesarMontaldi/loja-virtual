package br.com.cesarmontaldi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.model.dto.CepDTO;
import br.com.cesarmontaldi.service.ConsultaCepService;
import br.com.cesarmontaldi.service.PessoaFisicaService;

@RestController
public class PessoaFisicaController {
	
	@Autowired
	private PessoaFisicaService pessoaFisicaService;
	
	
	@ResponseBody
	@PostMapping("/salvarPessoaFisica")
	public ResponseEntity<PessoaFisica> salvarPessoaFisica(@Valid @RequestBody PessoaFisica pessoaFisica) {
		
		PessoaFisica pessoaPF = pessoaFisicaService.salvar(pessoaFisica);
		
		return new ResponseEntity<>(pessoaPF, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/consultaPFPorNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPorNome(@PathVariable String nome, @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		
		List<PessoaFisica> pessoas = pessoaFisicaService.consultaPorNome(nome, paginacao);
		
		return new ResponseEntity<List<PessoaFisica>>(pessoas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/consultaPorCpf/{cpf}")
	public ResponseEntity<PessoaFisica> consultaPorCpf(@PathVariable String cpf) {
		
		PessoaFisica pessoa = pessoaFisicaService.consultaPorCpf(cpf);
		
		return new ResponseEntity<PessoaFisica>(pessoa, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable String cep) {
		CepDTO cepEncontrado = ConsultaCepService.consulta(cep);
		
		return new ResponseEntity<CepDTO>(cepEncontrado, HttpStatus.OK);
	}

}
