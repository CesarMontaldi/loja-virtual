package br.com.cesarmontaldi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.cesarmontaldi.model.Acesso;
import br.com.cesarmontaldi.repository.AcessoRepository;
import br.com.cesarmontaldi.service.AcessoService;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class AcessoTest {
	
	@Autowired
	private AcessoRepository repository;

	@Autowired
	private AcessoService service;
	
	@Test
	public void testCadastroAcesso() {
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ADMIN");
		
		repository.save(acesso);
	}
}
