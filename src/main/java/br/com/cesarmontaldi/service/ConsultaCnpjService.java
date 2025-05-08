package br.com.cesarmontaldi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cesarmontaldi.model.dto.ConsultaCnpjDTO;

@Service
public class ConsultaCnpjService {

	public static ConsultaCnpjDTO consultaCnpj(String CNPJ) {
		CNPJ = CNPJ.replaceAll("\\.", "").replaceAll("\\/", "").replaceAll("\\-", "");
		
		return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + CNPJ, ConsultaCnpjDTO.class).getBody();
	}
}
