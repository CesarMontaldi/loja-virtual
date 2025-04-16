package br.com.cesarmontaldi.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
	
	@Value("${application.mail.username}")
	private String userName;
	@Value("${application.mail.password}")
	private String password;
	
	@Async
	public void sendEmailHtml(String assunto, String menssagem, String emailDestino) {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls", "false");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		
		session.setDebug(true);
		
		try {
			Address[] toUser = InternetAddress.parse(emailDestino);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName, "César Montaldi", "UTF-8"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			message.setContent(menssagem, "text/html; charset=utf-8");
			
			Transport.send(message);
		}
		catch (UnsupportedEncodingException | MessagingException ex) {
			ex.printStackTrace();
		}
	}
}








