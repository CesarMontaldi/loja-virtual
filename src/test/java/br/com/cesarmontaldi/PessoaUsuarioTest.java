package br.com.cesarmontaldi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.cesarmontaldi.controller.PessoaController;
import br.com.cesarmontaldi.model.PessoaJuridica;
import br.com.cesarmontaldi.repository.PessoaRepository;
import br.com.cesarmontaldi.service.PessoaUserService;
import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class PessoaUsuarioTest extends TestCase {
	
	
	@Autowired
	private PessoaController pessoaController;
	
	
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
		
		pessoaController.salvarPessoaJuridica(pessoaJuridica);
		

//		PessoaFisica pessoaFisica = new PessoaFisica();
//		
//		pessoaFisica.setCpf("355.124.028.03");
//		pessoaFisica.setNome("César Montaldi");
//		pessoaFisica.setEmail("guto@gmail.com");
//		pessoaFisica.setTelefone("(19)9 9646-9607");
	}
}
