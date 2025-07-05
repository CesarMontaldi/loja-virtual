package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.ContaPagar;
import br.com.cesarmontaldi.repository.ContaPagarRepository;

@Service
public class ContaPagarService {
	
	private Integer statusCode;
	
	@Autowired
	private ContaPagarRepository repository;
	


	public ContaPagar salvar(ContaPagar conta) {
		
		
		if (conta.getEmpresa() == null || (conta.getEmpresa().getId() <= 0)) {
			throw new LojaVirtualException("Empresa deve ser informada!", statusCode = 422);
		}
		
		if (conta.getPessoa() == null || (conta.getPessoa().getId() <= 0)) {
			throw new LojaVirtualException("Pessoa deve ser informada!", statusCode = 422);
		}
		
		if (conta.getPessoaFornecedor() == null || (conta.getPessoaFornecedor().getId() <= 0)) {
			throw new LojaVirtualException("Fornecedor deve ser informado!", statusCode = 422);
		}
		
		if (conta.getId() == null) {
			if (repository.existsContaPagarByDescricao(conta.getDescricao())) {
				throw new LojaVirtualException("Já existe uma conta com a mesma descrição: " + conta.getDescricao(), statusCode = 409);
			}
		}
		
		return repository.save(conta);
	}


	public List<ContaPagar> buscarPorDesc(String descricao) {
		
		return repository.buscarContaDesc(descricao)
				.stream()
				.toList();
	}
	
	
	public ContaPagar buscarContaPagar(Long idConta) {
		
		ContaPagar conta  = repository
				.findById(idConta)
				.orElseThrow(()-> new LojaVirtualException("Conta não encontrado!", statusCode = 404));
		
		return conta;
	}
	
	
	public void deletar(Long idConta) {
		
		if (!repository.existsById(idConta)) {
			throw new LojaVirtualException("Conta não encontrada!", statusCode = 404);
		} else {
			repository.deleteById(idConta);
		}
	}

}
