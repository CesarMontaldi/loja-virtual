package br.com.cesarmontaldi.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.Acesso;
import br.com.cesarmontaldi.repository.AcessoRepository;

@Service
public class AcessoService {
	
	private Integer statusCode;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso salvarAcesso(Acesso acesso) {
		
		if (acessoRepository.existsAcessoByDescricao(acesso.getDescricao().toUpperCase())) {
			throw new LojaVirtualException("Já existe Acesso com a descricao: " + acesso.getDescricao(), statusCode = 409);
		}
		return acessoRepository.save(acesso);
	}

	public Acesso obterAcesso(Long id) {
		return acessoRepository.buscarAcessoID(id)
				.orElseThrow(()-> new LojaVirtualException("Não foi encontrado Acesso com o ID: " + id, statusCode = 404));
	}
	
	public List<Acesso> buscarAcessoDescricao(String descricao) {
		return acessoRepository.buscarAcessoDesc(descricao);
	}
	
	public void deletarAcesso(Long id) {
		acessoRepository.buscarAcessoID(id)
		.orElseThrow(()-> new EntityNotFoundException("Não foi encontrado Acesso com o ID: " + id));
		
		acessoRepository.deleteById(id);
	}

}
