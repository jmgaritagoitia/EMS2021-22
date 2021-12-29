package com.practica.exception;

public class EmsPersonNotFoundException extends Exception{

	public EmsPersonNotFoundException() {
		super("PERSONA NO ENCONTRADA!");
	}

	public EmsPersonNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
