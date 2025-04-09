package br.com.cesarmontaldi.model.dto;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String error;
	private Integer code;
	
	
	public ErrorResponse(String error) {
		super();
		this.error = error;
	}

	public ErrorResponse(String error, Integer code) {
		super();
		this.error = error;
		this.code = code;
	}

	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}

}
