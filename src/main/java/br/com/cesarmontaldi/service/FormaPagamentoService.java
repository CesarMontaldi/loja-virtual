package br.com.cesarmontaldi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.FormaPagamento;
import br.com.cesarmontaldi.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {

	@Autowired
	private FormaPagamentoRepository repository;

	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		
		if (formaPagamento.getId() == null) {
			if (repository.existsFormaPagamentoByDescricao(formaPagamento.getDescricao())) {
				throw new LojaVirtualException("Forma de pagamento já cadastrada cadastre outra forma de pagamento.");
			}
		}
		
		return repository.save(formaPagamento);
	}
}
