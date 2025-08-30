package br.com.cesarmontaldi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.ItemVendaLoja;
import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.model.StatusRastreio;
import br.com.cesarmontaldi.model.VendaCompraLojaVirtual;
import br.com.cesarmontaldi.model.dto.ItemVendaDTO;
import br.com.cesarmontaldi.model.dto.VendaCompraLojaVirtualDTO;
import br.com.cesarmontaldi.repository.EnderecoRepository;
import br.com.cesarmontaldi.repository.VendaCompraLojaVirtualRepository;

@Service
public class VendaCompraLojaVirtualService {
	
	private Integer statusCode;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private VendaCompraLojaVirtualRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private StatusRastreioService statusRastreioService;
	
	@Autowired
	private PessoaFisicaService pessoaFisicaService;
	
	@Autowired
	private NotaFiscalVendaService notaFiscalVendaService;
	

	public VendaCompraLojaVirtualDTO salvarVenda(VendaCompraLojaVirtual vendaLoja) {
		
		vendaLoja.getPessoa().setEmpresa(vendaLoja.getEmpresa());
		PessoaFisica pessoa = vendaLoja.getPessoa();
		pessoa.setEmpresa(vendaLoja.getEmpresa());
		pessoa = pessoaFisicaService.salvar(pessoa);
		vendaLoja.setPessoa(pessoa);
		
		vendaLoja.getEnderecoCobranca().setPessoa(pessoa);
		vendaLoja.getEnderecoCobranca().setEmpresa(vendaLoja.getEmpresa());
		Endereco enderecoCobranca = enderecoRepository.save(vendaLoja.getEnderecoCobranca());
		vendaLoja.setEnderecoCobranca(enderecoCobranca);
		
		vendaLoja.getEnderecoEntrega().setPessoa(pessoa);
		vendaLoja.getEnderecoEntrega().setEmpresa(vendaLoja.getEmpresa());
		Endereco enderecoEntrega = enderecoRepository.save(vendaLoja.getEnderecoEntrega());
		vendaLoja.setEnderecoEntrega(enderecoEntrega);
		
		vendaLoja.getNotaFiscalVenda().setEmpresa(vendaLoja.getEmpresa());
		
		for (int i = 0; i < vendaLoja.getItensVendaLoja().size(); i++) {
			vendaLoja.getItensVendaLoja().get(i).setEmpresa(vendaLoja.getEmpresa());
			vendaLoja.getItensVendaLoja().get(i).setVendaCompraLojaVirtual(vendaLoja);
		}
		
		VendaCompraLojaVirtual venda = repository.saveAndFlush(vendaLoja);
		
		StatusRastreio statusRastreio = new StatusRastreio();
		statusRastreio.setCentroDistribuicao("Local");
		statusRastreio.setCidade("Sumaré");
		statusRastreio.setEmpresa(venda.getEmpresa());
		statusRastreio.setEstado("SP");
		statusRastreio.setStatus("Em Separação");
		statusRastreio.setVendaCompraLojaVirtual(venda);
		
		statusRastreioService.salvarStatus(statusRastreio);
	 
		venda.getNotaFiscalVenda().setVendaCompraLojaVirtual(venda);
		
		notaFiscalVendaService.salvarNotaVenda(venda.getNotaFiscalVenda());
		
		VendaCompraLojaVirtualDTO vendaDTO = new VendaCompraLojaVirtualDTO(venda);
		
		for(ItemVendaLoja item: venda.getItensVendaLoja()) {
			ItemVendaDTO itemDTO = new ItemVendaDTO(item.getQuantidade(), item.getProduto());
			vendaDTO.getItensVenda().add(itemDTO);
		}
		
		return vendaDTO;
	}


	public VendaCompraLojaVirtual buscaVendaId(Long id) {
		
		return repository.findById(id)
				.orElseThrow(() -> new LojaVirtualException("Venda não encontrada!", statusCode = 404));
	}
	
	public void deletarVenda(Long idVenda) {
		buscaVendaId(idVenda);
		
		StringBuilder sql = new StringBuilder(); 
			
		sql.append(" begin; ");
		sql.append(" update nota_fiscal_venda set venda_compra_loja_virtual_id = null where venda_compra_loja_virtual_id ="); sql.append(idVenda); sql.append(";");
		sql.append(" delete from nota_fiscal_venda where venda_compra_loja_virtual_id ="); sql.append(idVenda); sql.append(";"); 
		sql.append(" delete from item_venda_loja where venda_compra_loja_virtual_id ="); sql.append(idVenda); sql.append(";");
		sql.append(" delete from status_rastreio where venda_compra_loja_virtual_id ="); sql.append(idVenda); sql.append(";");
		sql.append(" delete from venda_compra_loja_virtual where id ="); sql.append(idVenda); sql.append(";");
		sql.append(" commit; ");
		
		jdbcTemplate.execute(sql.toString());
	}
}










