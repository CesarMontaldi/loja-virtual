package br.com.cesarmontaldi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.Acesso;
import br.com.cesarmontaldi.service.AcessoService;

@RestController
@RequestMapping("/acessos")
public class AcessoController {

	@Autowired
	private AcessoService service;
	
	@PostMapping
	public void salvaAcesso(Acesso acesso) {
		service.salvarAcesso(acesso);
	}
}
