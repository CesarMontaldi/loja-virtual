package br.com.cesarmontaldi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.model.Acesso;
import br.com.cesarmontaldi.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public void salvarAcesso(Acesso acesso) {
		
		acessoRepository.save(acesso);
	}

}
