package br.com.cesarmontaldi.service;


import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.enums.TipoPessoa;
import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.PessoaFisica;
import br.com.cesarmontaldi.model.Usuario;
import br.com.cesarmontaldi.repository.PessoaFisicaRepository;
import br.com.cesarmontaldi.repository.UsuarioRepository;
import br.com.cesarmontaldi.util.GeneratePassword;
import br.com.cesarmontaldi.util.ValidaCPF;

@Service
public class PessoaFisicaService {
	
	@Autowired
	private PessoaFisicaRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EmailSendService serviceSendEmail;
	
	
	public PessoaFisica salvar(PessoaFisica pessoaFisica) {

		if (pessoaFisica == null) {
			throw new LojaVirtualException("Pessoa fisica nao pode ser NULL");
		}
		
		if (pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
		}
		
		if (pessoaFisica.getId() == null && repository.existsByCpf(pessoaFisica.getCpf())) {
			throw new LojaVirtualException("Ja existe CPF cadastrado com o numero: " + pessoaFisica.getCpf());
		}
		
		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new LojaVirtualException("CPF: " + pessoaFisica.getCpf() + " esta invalido.");
		}
		
		for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
			pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
		}
		
		pessoaFisica = repository.save(pessoaFisica);
		
		Usuario usuarioPF = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());
		
		 
		if (usuarioPF == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if(constraint != null) {
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop CONSTRAINT " + constraint + "; commit;");
			}
			
			usuarioPF = new Usuario();
			usuarioPF.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPF.setEmpresa(pessoaFisica.getEmpresa());
			usuarioPF.setPessoa(pessoaFisica);
			usuarioPF.setLogin(pessoaFisica.getEmail());
			
			String senha = GeneratePassword.generateRandomPassword(15);
			String senhaCrypt = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPF.setSenha(senhaCrypt);
			usuarioPF = usuarioRepository.save(usuarioPF);
			
			usuarioRepository.insereAcessoUser(usuarioPF.getId());
			
			StringBuilder message = new StringBuilder();
			
			message.append("<strong>Segue abaixo seus dados de acesso para loja virtual</strong> <br/><br/>");
			message.append("<strong>Login:  </strong>" + pessoaFisica.getEmail() + "<br/>");
			message.append("<strong>Senha:  </strong>" + senha + "<br/><br/><br/>");
			message.append("Obrigado!");
			
			try {
				serviceSendEmail.sendEmailHtml("Acesso gerado para Loja Virtual", message.toString(), pessoaFisica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return pessoaFisica;
	}
	
	public List<PessoaFisica> consultaPorNome(String nome, Pageable paginacao) {
		Page<PessoaFisica> pessoas = repository.consultaPorNome(nome.toUpperCase(), paginacao);
		
		return pessoas.stream()
				.collect(Collectors.toList());
	}
	
	public PessoaFisica consultaPorCpf(String cpf) {
		return repository.consultaPorCpf(cpf);
		
	}

}










