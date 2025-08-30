package br.com.cesarmontaldi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.model.NotaFiscalVenda;
import br.com.cesarmontaldi.repository.NotaFiscalVendaRepository;

@Service
public class NotaFiscalVendaService {

	@Autowired
	private NotaFiscalVendaRepository repository;
	
	public NotaFiscalVenda salvarNotaVenda(NotaFiscalVenda nota) {
		
		return repository.save(nota);
	}
}
