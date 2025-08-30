package br.com.cesarmontaldi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.ItemVendaLoja;
import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.model.VendaCompraLojaVirtual;
import br.com.cesarmontaldi.model.dto.ItemVendaDTO;
import br.com.cesarmontaldi.model.dto.VendaCompraLojaVirtualDTO;
import br.com.cesarmontaldi.repository.EnderecoRepository;
import br.com.cesarmontaldi.repository.PessoaFisicaRepository;
import br.com.cesarmontaldi.service.PessoaFisicaService;
import br.com.cesarmontaldi.service.VendaCompraLojaVirtualService;

@RestController
public class VendaCompraLojaVirtualController {
	
	@Autowired
	private VendaCompraLojaVirtualService service;
	
	@PostMapping(value = "**/salvarVenda")
	public ResponseEntity<VendaCompraLojaVirtualDTO> salvarVendaLoja(@RequestBody @Valid VendaCompraLojaVirtual vendaLoja) {
		
		VendaCompraLojaVirtualDTO vendaDTO = service.salvarVenda(vendaLoja);
		
		return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "consultaVendaId/{id}")
	public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaPorID(@PathVariable Long id) {
		
		VendaCompraLojaVirtual venda = service.buscaVendaId(id);
		
		VendaCompraLojaVirtualDTO vendaDTO = new VendaCompraLojaVirtualDTO(venda);
		
		for(ItemVendaLoja item: venda.getItensVendaLoja()) {
			ItemVendaDTO itemVendaDTO = new ItemVendaDTO(item.getQuantidade(), item.getProduto());
			
			vendaDTO.getItensVenda().add(itemVendaDTO);
		}
		
		return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaDTO, HttpStatus.OK);	
	}
	
	@DeleteMapping(value = "deletarVenda/{idVenda}")
	public ResponseEntity<Void> deletarVenda(@PathVariable Long idVenda) {
		
		service.deletarVenda(idVenda);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
