package br.com.cesarmontaldi.exceptions;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cesarmontaldi.model.dto.ErrorResponse;
import br.com.cesarmontaldi.service.EmailSendService;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private EmailSendService emailService;
	
	@ExceptionHandler(LojaVirtualException.class)
	public ResponseEntity<Object> handleExceptionCustom(LojaVirtualException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse( 
				ex.getMessage(),
				ex.getCode());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
	}
	
	@Override
	@ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String message = "";
		
		
		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> errorList = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors(); 
			
			for (ObjectError objectError : errorList) {
				message += objectError.getDefaultMessage() + " \n";
			}
		} 
		
		else if (ex instanceof HttpMessageNotReadableException) {
			message = "Não está sendo enivado dados para o BODY corpo da requisição";
		} 
		
		else {
			message = ex.getMessage();
		}
		
		ErrorResponse errorResponse = new ErrorResponse(message, status.value());
	
		ex.printStackTrace();

		emailService.sendEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "guto_montaldi@yahoo.com.br");
		
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse( 
				ex.getMessage(),
				HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()));
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {
		
		String message = "";
		
		if (ex instanceof SQLException) {
			message = "Erro de SQL no banco: " + ((SQLException)ex).getCause().getCause().getMessage();
		
	    } else if (ex instanceof DataIntegrityViolationException) {
			message = "Erro de integridade no banco: " + ((DataIntegrityViolationException)ex).getCause().getCause().getMessage();
		
		} else if (ex instanceof ConstraintViolationException) {
			message = "Erro de chave estrangeira: " + ((ConstraintViolationException)ex).getCause().getCause().getMessage();
		
		} else {
			message = ex.getMessage();
		}
		
		ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		ex.printStackTrace();
		
		emailService.sendEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "guto_montaldi@yahoo.com.br");
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}








