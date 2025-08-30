package br.com.cesarmontaldi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.model.StatusRastreio;
import br.com.cesarmontaldi.repository.StatusRastreioRepository;

@Service
public class StatusRastreioService {

	@Autowired
	private StatusRastreioRepository repository;
	
	
	public StatusRastreio salvarStatus(StatusRastreio rastreio) {
		
		return repository.save(rastreio);
	}
}
