package br.com.cesarmontaldi.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.Pessoa;
import br.com.cesarmontaldi.model.VendaCompraLojaVirtual;

public class VendaCompraLojaVirtualDTO {
		
	private Long id;
	private Pessoa pessoa; 
	private Long idEmpresa;
	private BigDecimal valorTotal; 
	private Long idNotaFiscalVenda;
	private Endereco entrega;
	private Endereco cobranca;
	private BigDecimal valorFrete;
	private List<ItemVendaDTO> itensVenda = new ArrayList<ItemVendaDTO>();
	
	
	
	
	
	public VendaCompraLojaVirtualDTO(VendaCompraLojaVirtual venda) {
		this.id = venda.getId();
		this.pessoa = venda.getPessoa();
		this.idEmpresa = venda.getEmpresa().getId();
		this.valorTotal = venda.getValorTotal();
		this.idNotaFiscalVenda = venda.getNotaFiscalVenda().getId();
		this.entrega = venda.getEnderecoEntrega();
		this.cobranca = venda.getEnderecoCobranca();
		this.valorFrete = venda.getValorFrete();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Long getIdNotaFiscalVenda() {
		return idNotaFiscalVenda;
	}
	public void setIdNotaFiscalVenda(Long idNotaFiscalVenda) {
		this.idNotaFiscalVenda = idNotaFiscalVenda;
	}
	public Endereco getEntrega() {
		return entrega;
	}
	public void setEntrega(Endereco entrega) {
		this.entrega = entrega;
	}
	public Endereco getCobranca() {
		return cobranca;
	}
	public void setCobranca(Endereco cobranca) {
		this.cobranca = cobranca;
	}
	public BigDecimal getValorFrete() {
		return valorFrete;
	}
	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}
	public List<ItemVendaDTO> getItensVenda() {
		
		return itensVenda;
	}
	public void setItensVenda(List<ItemVendaDTO> itensVenda) {
		this.itensVenda = itensVenda;
	}

}
