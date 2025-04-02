package br.com.cesarmontaldi.exceptions;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cesarmontaldi.model.dto.ErrorResponse;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
	
	@Override
	@ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String message = "";
		ErrorResponse errorResponse = new ErrorResponse();
		
		
		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> errorList = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			
			for (ObjectError objectError : errorList) {
				message += objectError.getDefaultMessage() + "\n";
			}
		} else {
			message = ex.getMessage();
		}
		
		errorResponse.setError(message);
		errorResponse.setCode(status.value() + " ==> " + status.getReasonPhrase());
		
		ex.printStackTrace();
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {
		
		String message = "";
		ErrorResponse errorResponse = new ErrorResponse();
		
		if (ex instanceof SQLException) {
			message = "Erro de SQL no banco: " + ((SQLException)ex).getCause().getCause().getMessage();
		
	    }else if (ex instanceof DataIntegrityViolationException) {
			message = "Erro de integridade no banco: " + ((DataIntegrityViolationException)ex).getCause().getCause().getMessage();
		
		}else if (ex instanceof ConstraintViolationException) {
			message = "Erro de chave estrangeira: " + ((ConstraintViolationException)ex).getCause().getCause().getMessage();
		}
			message = ex.getMessage();
		
		errorResponse.setError(message);
		errorResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		
		ex.printStackTrace();
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}








