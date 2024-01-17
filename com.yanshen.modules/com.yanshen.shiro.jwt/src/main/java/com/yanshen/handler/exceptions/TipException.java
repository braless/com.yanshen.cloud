package com.yanshen.handler.exceptions;

public class TipException extends RuntimeException {

	private static final long serialVersionUID = 7827444065800695635L;
	private Integer code;


	public TipException(String message) {
		super(message);
		this.code = 500;
	}
	
	public TipException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}