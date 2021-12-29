package com.practicas.ems.covid;

import com.practica.ems.Constantes;
import com.practica.ems.Coordenada;
import com.practica.ems.FechaHora;
import com.practica.ems.Persona;
import com.practica.ems.PosicionPersona;
import com.practica.ems.Utilidades;
import com.practica.exception.EmsDuplicateLocationException;
import com.practica.exception.EmsDuplicatePersonException;
import com.practica.exception.EmsInvalidNumberOfDataException;
import com.practica.exception.EmsInvalidTypeException;
import com.practica.exception.EmsLocalizationNotFoundException;
import com.practica.exception.EmsPersonNotFoundException;

public class ContactosCovid {
	private Poblacion poblacion;
	private Localizacion localizacion;
	
	public ContactosCovid() {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();		
	}
	
	
	public void loadData(String data, boolean reset) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException, EmsDuplicatePersonException, EmsDuplicateLocationException {
		// borro información anterior
		if(reset) {
			this.poblacion = new Poblacion();
			this.localizacion = new Localizacion();
		}
		String datas[] = dividirEntrada(data);
		for(String linea:datas) {
			String datos[] = this.dividirLineaData(linea);
			if(!datos[0].equals("PERSONA") && !datos[0].equals("LOCALIZACION")) {
				throw new EmsInvalidTypeException();
			}
			if(datos[0].equals("PERSONA")) {
				if(datos.length != Constantes.MAX_DATOS_PERSONA) {
					throw new EmsInvalidNumberOfDataException("El número de datos para PERSONA es menor de 8");
				}
				this.poblacion.addPersona(this.crearPersona(datos));
			}
			if(datos[0].equals("LOCALIZACION")) {
				if(datos.length != Constantes.MAX_DATOS_LOCALIZACION) {
					throw new EmsInvalidNumberOfDataException("El número de datos para LOCALIZACION es menor de 6");
				}
				this.localizacion.addLocalizacion(this.crearPosicionPersona(datos));
			}
			
		}
		
	}
	
	public int findPersona (String documento) throws EmsPersonNotFoundException {
		int pos;
		try {
			pos = this.poblacion.findPersona(documento);
			return pos;
		} catch (EmsPersonNotFoundException e) {
			throw new EmsPersonNotFoundException();
		}				
	}
	
	public int findLocalizacion (String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
		FechaHora fechaHora = Utilidades.parsearFecha(fecha, hora);
		int pos;
		try {
			pos = localizacion.findLocalizacion(documento, fechaHora);
			return pos;
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}		
	}
	
	private String[] dividirEntrada(String input) {
		String cadenas[] = input.split("\\n");
		return cadenas;
	}
	
	private String[] dividirLineaData(String data) {
		String cadenas[] = data.split("\\;");
		return cadenas;
	}
	private  Persona crearPersona (String[] data) {
		Persona persona = new Persona();
		for(int i=1; i<Constantes.MAX_DATOS_PERSONA; i++) {
			String s = data[i];
			switch(i) {
			case 1: persona.setDocumento(s); break;
			case 2: persona.setNombre(s); break;
			case 3: persona.setApellidos(s);break;
			case 4: persona.setEmail(s);break;
			case 5: persona.setDireccion(s); break; 
			case 6: persona.setCp(s);break;
			case 7: persona.setFechaNacimiento(Utilidades.parsearFecha(s)); break;
			}
		}		
		return persona;		
	}
	
	private  PosicionPersona crearPosicionPersona (String[] data) {
		PosicionPersona posicionPersona = new PosicionPersona();
		String fecha = null, hora;
		float latitud = 0, longitud;
		for(int i=1; i<Constantes.MAX_DATOS_LOCALIZACION; i++) {
			String s = data[i];
			switch(i) {
			case 1: posicionPersona.setDocumento(s); break;
			case 2: fecha = data[i]; break;
			case 3: hora = data [i]; 
				posicionPersona.setFechaPosicion(Utilidades.parsearFecha(fecha, hora));break;
			case 4: latitud = Float.parseFloat(s);break;
			case 5: longitud = Float.parseFloat(s);
					posicionPersona.setCoordenada(new Coordenada(latitud, longitud));
					break; 
			}
		}		
		return posicionPersona;		
	}
}
