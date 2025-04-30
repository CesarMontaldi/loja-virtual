package br.com.cesarmontaldi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cesarmontaldi.model.dto.CepDTO;

@Service
public class ConsultaCepService {
	
	public static CepDTO consulta(String cep) {
		
		return new RestTemplate()
				.getForEntity("https://viacep.com.br/ws/ " + cep + "/json/", CepDTO.class)
				.getBody();
	}

}
