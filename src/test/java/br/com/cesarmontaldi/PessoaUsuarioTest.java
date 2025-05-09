package br.com.cesarmontaldi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.cesarmontaldi.controller.PessoaFisicaController;
import br.com.cesarmontaldi.controller.PessoaJuridicaController;
import br.com.cesarmontaldi.enums.TipoEndereco;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.model.PessoaJuridica;
import br.com.cesarmontaldi.repository.PessoaFisicaRepository;
import br.com.cesarmontaldi.repository.PessoaJuridicaRepository;
import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class PessoaUsuarioTest extends TestCase {
	
	
	@Autowired
	private PessoaJuridicaController pessoaJuridicaController;
	
	@Autowired
	private PessoaFisicaController pessoaFisicaController;
	
	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	
	@Test
	public void cadastrarPessoa() {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		
		pessoaJuridica.setCnpj("83.564.365/0001-76");
		pessoaJuridica.setNome("Carlos Andrade");
		pessoaJuridica.setEmail("carlos@gmail.com");
		pessoaJuridica.setTelefone("(11)9 9585-5420");
		pessoaJuridica.setInscricaoEstadual("471.089.684.749");
		pessoaJuridica.setInscricaoMunicipal("726.269.435/1309");
		pessoaJuridica.setNomeFantasia("Lanchonete do Carlão");
		pessoaJuridica.setRazaoSocial("Carlos Andrade ME");
		
		pessoaJuridicaController.salvarPessoaJuridica(pessoaJuridica);
		
	}
	
	@Test
	public void cadastrarPessoaFisica() {
		
		PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.consultaPorCnpj("19.370.390/0001-10");
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setCpf("522.790.750-16");
		pessoaFisica.setNome("Renato Braga");
		pessoaFisica.setEmail("renato.braga@hotmail.com.br");
		pessoaFisica.setTelefone("(19)9 9954-6578");
		pessoaFisica.setDataNascimento(Date.from(LocalDate.of(1977, 8, 22).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		pessoaFisica.setTipoPessoa("FISICA");
		pessoaFisica.setEmpresa(pessoaJuridica);
		
		Endereco endereco = new Endereco();
		endereco.setCep("13171-810");
		endereco.setLogradouro("Rua Graciliano Ramos");
		endereco.setNumero("541");
		endereco.setBairro("Parque Residencial Casarão");
		endereco.setCidade("Sumaré");
		endereco.setUf("SP");
		endereco.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco.setPessoa(pessoaFisica);
		endereco.setEmpresa(pessoaJuridica);
		
		pessoaFisicaController.salvarPessoaFisica(pessoaFisica);
		
		pessoaFisicaController.salvarPessoaFisica(pessoaFisica);
		
	}
	
	@Test
	public void consultaCpf() {
		if (pessoaFisicaRepository.existsByCpf("522.790.750-16")) {
			System.out.println("Olá");
		}
	}
}
