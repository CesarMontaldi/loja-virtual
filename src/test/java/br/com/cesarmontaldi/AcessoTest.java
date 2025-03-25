package br.com.cesarmontaldi;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cesarmontaldi.controller.AcessoController;
import br.com.cesarmontaldi.model.Acesso;
import br.com.cesarmontaldi.repository.AcessoRepository;
import br.com.cesarmontaldi.service.AcessoService;
import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class AcessoTest extends TestCase {
	
	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext applicationContext;
	
	
	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.applicationContext);
		MockMvc mockMvc = builder.build();
		
		Acesso newAcesso = new Acesso();
		
		newAcesso.setDescricao("ROLE_CLIENTE");
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions responseApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/salvarAcesso")
						.content(mapper.writeValueAsString(newAcesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println( "Response API: " + responseApi.andReturn().getResponse().getContentAsString());
		
		Acesso acesso = mapper
				.readValue(responseApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(newAcesso.getDescricao(), acesso.getDescricao());
	}
	
	@Test
	public void testRestApiObterAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.applicationContext);
		MockMvc mockMvc = builder.build();
		
		Acesso newAcesso = new Acesso();
		
		newAcesso.setDescricao("ROLE_GERENTE");
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions responseApi = mockMvc
				.perform(MockMvcRequestBuilders.get("/obterAcesso/" + newAcesso.getId())
						.content(mapper.writeValueAsString(newAcesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println( "Response API: " + responseApi.andReturn().getResponse().getContentAsString());
		
		Acesso acesso = mapper
				.readValue(responseApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(newAcesso.getDescricao(), acesso.getDescricao());
	}
	
	@Test
	public void testRestApiDeletarAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.applicationContext);
		MockMvc mockMvc = builder.build();
		
		Acesso newAcesso = new Acesso();
		
		newAcesso.setDescricao("ROLE_CLIENTE");
		
		newAcesso = acessoRepository.save(newAcesso);
		
		ObjectMapper mapper = new ObjectMapper();
		
		ResultActions responseApi = mockMvc
				.perform(MockMvcRequestBuilders.delete("/deleteAcesso/" + newAcesso.getId())
						.content(mapper.writeValueAsString(newAcesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));
		
		System.out.println( "Response API: " + responseApi.andReturn().getResponse().getContentAsString());
		System.out.println( "ResponseStatus API: " + responseApi.andReturn().getResponse().getStatus());
		
		assertEquals(200, responseApi.andReturn().getResponse().getStatus());
	}

}
