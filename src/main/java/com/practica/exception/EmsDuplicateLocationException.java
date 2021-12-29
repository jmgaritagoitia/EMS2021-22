package com.practica.exception;

public class EmsDuplicateLocationException extends Exception{
	public EmsDuplicateLocationException() {
		super("LOCALIZACION DUPLICADA!");
		// TODO Auto-generated constructor stub
	}

	public EmsDuplicateLocationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
