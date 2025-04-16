package br.com.cesarmontaldi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.model.Usuario;
import br.com.cesarmontaldi.repository.UsuarioRepository;

@Service
public class TarefaAutomatizadaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailSendService emailSendService;
	
	
	
	//@Scheduled(initialDelay = 2000, fixedDelay = 86400000)
	//@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
	public void notificaUserTrocaSenha() throws InterruptedException {
		
		List<Usuario> usuarios = usuarioRepository.userPasswordExpired();
		
		for (Usuario usuario : usuarios) {
			
			StringBuilder message = new StringBuilder();
			message.append("Olá, ").append(usuario.getEmpresa().getNome()).append("<br/>");
			message.append("Está na hora de trocar sua senha, já passo 90 dias de validade").append("<br/>");
			message.append("Troque sua senha na Loja Virtual");
			
			emailSendService.sendEmailHtml("Troca de senha", message.toString(), usuario.getLogin());
			
			Thread.sleep(3000);
		}
		
	}

}
