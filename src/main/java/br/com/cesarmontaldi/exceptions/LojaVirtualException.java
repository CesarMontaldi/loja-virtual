package br.com.cesarmontaldi.exceptions;

import java.io.Serializable;

public class LojaVirtualException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int code;

	public LojaVirtualException(String message) {
		super(message);
	}
	
	public LojaVirtualException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
