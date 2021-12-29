package com.practicas.ems.covid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	
	public Poblacion getPoblacion() {
		return poblacion;
	}


	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}


	public Localizacion getLocalizacion() {
		return localizacion;
	}


	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
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
	
	public List<PosicionPersona> localizacionPersona (String documento) throws EmsPersonNotFoundException  {
		int cont = 0;
		List<PosicionPersona> lista = new ArrayList<PosicionPersona>();
	    Iterator<PosicionPersona> it = this.localizacion.getLista().iterator();
	    while(it.hasNext()) {
	    	PosicionPersona pp = it.next();
	    	if(pp.getDocumento().equals(documento)) { 
	    		cont++;
	    		lista.add(pp);
	    	}
	    } 
	    if(cont==0)
	    	throw new EmsPersonNotFoundException();
	    else
	    	return lista;
	}
	
	public boolean delPersona (String documento) throws EmsPersonNotFoundException {
		int cont=0, pos=-1;
		Iterator<Persona> it = this.poblacion.getLista().iterator();
		while (it.hasNext()) {
			Persona persona = it.next();			
			if(persona.getDocumento().equals(documento)) {
				pos=cont;
			}
			cont++;
		}	
		if(pos==-1) {
			throw new EmsPersonNotFoundException();
		}
		this.poblacion.getLista().remove(pos);
		return false;
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
