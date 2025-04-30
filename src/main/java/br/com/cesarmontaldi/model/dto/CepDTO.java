package br.com.cesarmontaldi.model.dto;

import java.io.Serializable;

public record CepDTO(String cep, String logradouro, String complemento, String bairro, String localidade, String uf) implements Serializable {
	
}
