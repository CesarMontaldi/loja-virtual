package br.com.cesarmontaldi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.FormaPagamento;
import br.com.cesarmontaldi.service.FormaPagamentoService;

@RestController
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoService service;
	
	@PostMapping(value = "**/salvarFormaPagamento")
	public ResponseEntity<FormaPagamento> salvarFormaPagamneto(@RequestBody @Valid FormaPagamento formaPagamento) {
		
		FormaPagamento newFormaPagamento = service.salvar(formaPagamento);
		
		return new ResponseEntity<FormaPagamento>(newFormaPagamento, HttpStatus.OK);
	}
}
