package br.com.cesarmontaldi.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.model.Acesso;
import br.com.cesarmontaldi.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso salvarAcesso(Acesso acesso) {
		
		return acessoRepository.save(acesso);
	}
	
	public void deletarAcesso(Long id) {
		
		acessoRepository.deleteById(id);
	}
	
	public Acesso obterAcesso(Long id) {
		return acessoRepository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Acesso não encontrado"));
	}

}
