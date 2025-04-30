package br.com.cesarmontaldi.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.Endereco;
import br.com.cesarmontaldi.model.PessoaJuridica;
import br.com.cesarmontaldi.model.Usuario;
import br.com.cesarmontaldi.model.dto.CepDTO;
import br.com.cesarmontaldi.repository.EnderecoRepository;
import br.com.cesarmontaldi.repository.PessoaJuridicaRepository;
import br.com.cesarmontaldi.repository.UsuarioRepository;
import br.com.cesarmontaldi.util.GeneratePassword;
import br.com.cesarmontaldi.util.ValidaCNPJ;

@Service
public class PessoaJuridicaService {
	
	@Autowired
	private PessoaJuridicaRepository pessoaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmailSendService serviceSendEmail;
	
	
	public PessoaJuridica salvar(PessoaJuridica pessoaJuridica) {

		if (pessoaJuridica == null) {
			throw new LojaVirtualException("Pessoa juridica nao pode ser NULL");
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existsCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new LojaVirtualException("Ja existe CNPJ cadastrado com o numero: " + pessoaJuridica.getCnpj());
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existsInscricaoEstadualCadastrada(pessoaJuridica.getInscricaoEstadual()) != null) {
			throw new LojaVirtualException("Ja existe Inscricao Estadual cadastrada com o numero: " + pessoaJuridica.getInscricaoEstadual());
		}
		
		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new LojaVirtualException("CNPJ: " + pessoaJuridica.getCnpj() + " esta invalido.");
		}
		
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				
				CepDTO cep = ConsultaCepService.consulta(pessoaJuridica.getEnderecos().get(p).getCep());
				
				pessoaJuridica.getEnderecos().get(p).setLogradouro(cep.logradouro());
				pessoaJuridica.getEnderecos().get(p).setBairro(cep.bairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cep.localidade());
				pessoaJuridica.getEnderecos().get(p).setUf(cep.uf());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cep.complemento());
			}
		} else {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				Endereco endereco = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				
				if (!endereco.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
					
					CepDTO cep = ConsultaCepService.consulta(pessoaJuridica.getEnderecos().get(p).getCep());
					
					pessoaJuridica.getEnderecos().get(p).setLogradouro(cep.logradouro());
					pessoaJuridica.getEnderecos().get(p).setBairro(cep.bairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cep.localidade());
					pessoaJuridica.getEnderecos().get(p).setUf(cep.uf());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cep.complemento());
				}
			}
		}
		
		for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}
		
		pessoaJuridica = pessoaRepository.save(pessoaJuridica);
		
		Usuario usuarioPJ = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());
		
		 
		if (usuarioPJ == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if(constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop CONSTRAINT " + constraint + "; commit;");
			}
			
			usuarioPJ = new Usuario();
			usuarioPJ.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPJ.setEmpresa(pessoaJuridica);
			usuarioPJ.setPessoa(pessoaJuridica);
			usuarioPJ.setLogin(pessoaJuridica.getEmail());
			
			String senha = GeneratePassword.generateRandomPassword(15);
			String senhaCrypt = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPJ.setSenha(senhaCrypt);
			usuarioPJ = usuarioRepository.save(usuarioPJ);
			
			usuarioRepository.insereAcessoUserPJ(usuarioPJ.getId(), "ROLE_ADMIN");
			
			StringBuilder message = new StringBuilder();
			
			message.append("<strong>Segue abaixo seus dados de acesso para loja virtual</strong> <br/><br/>");
			message.append("<strong>Login:  </strong>" + pessoaJuridica.getEmail() + "<br/>");
			message.append("<strong>Senha:  </strong>" + senha + "<br/><br/><br/>");
			message.append("Obrigado!");
			
			try {
				serviceSendEmail.sendEmailHtml("Acesso gerado para Loja Virtual", message.toString(), pessoaJuridica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return pessoaJuridica;
	} 

}
