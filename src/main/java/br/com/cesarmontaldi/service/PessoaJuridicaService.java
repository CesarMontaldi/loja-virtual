package br.com.cesarmontaldi.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.PessoaJuridica;
import br.com.cesarmontaldi.model.Usuario;
import br.com.cesarmontaldi.repository.PessoaRepository;
import br.com.cesarmontaldi.repository.UsuarioRepository;
import br.com.cesarmontaldi.util.GeneratePassword;

@Service
public class PessoaJuridicaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public PessoaJuridica salvar(PessoaJuridica pessoaJuridica) {

		if (pessoaJuridica == null) {
			throw new LojaVirtualException("Pessoa juridica nao pode ser NULL");
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existsCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new LojaVirtualException("Ja existe CNPJ cadastrado com o numero: " + pessoaJuridica.getCnpj());
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
			
			usuarioRepository.insereAcessoUserPJ(usuarioPJ.getId());
			
		}
		
		return pessoaJuridica;
	} 

}
