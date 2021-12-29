package com.practica.genericas;

public class Coordenada {
	private float latitud, longitud;

	
	public Coordenada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coordenada(float latitud, float longitud) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
	
}
