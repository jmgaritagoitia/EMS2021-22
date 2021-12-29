package com.practica.exception;

public class EmsDuplicatePersonException extends Exception{
	public EmsDuplicatePersonException() {
		super("PERSONA DUPLICADA!");
		// TODO Auto-generated constructor stub
	}

	public EmsDuplicatePersonException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
