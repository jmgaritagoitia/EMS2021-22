package com.practicas.ems.covid;

import java.util.Iterator;
import java.util.LinkedList;

import com.practica.ems.FechaHora;
import com.practica.ems.Persona;
import com.practica.ems.PosicionPersona;
import com.practica.exception.EmsDuplicateLocationException;
import com.practica.exception.EmsLocalizationNotFoundException;

public class Localizacion {
	LinkedList<PosicionPersona> lista;

	public Localizacion() {
		super();
		this.lista = new LinkedList<PosicionPersona>();
	};
	
	public void addLocalizacion (PosicionPersona p) throws EmsDuplicateLocationException {
		try {
			findLocalizacion(p.getDocumento(), p.getFechaPosicion());
			throw new EmsDuplicateLocationException();
		}catch(EmsLocalizationNotFoundException e) {
			lista.add(p);
		}
	}
	
	public int findLocalizacion (String documento, FechaHora fecha) throws EmsLocalizationNotFoundException {
	    int cont = 0;
	    Iterator<PosicionPersona> it = lista.iterator();
	    while(it.hasNext()) {
	    	cont++;
	    	PosicionPersona pp = it.next();
	    	if(pp.getDocumento().endsWith(documento) && 
	    	   pp.getFechaPosicion().equals(fecha)) {
	    		return cont;
	    	}
	    } 
	    throw new EmsLocalizationNotFoundException();
	}
	public void delLocalizacion(String documento, FechaHora fecha) throws EmsLocalizationNotFoundException {
	    int pos=-1;
	    int i;
	    
	    // Find if it exists
	    try {
			pos = findLocalizacion(documento, fecha);
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}
	    this.lista.remove(pos);
	    
	}
	
	void printLocalizacion() {    
	    for(int i = 0; i < this.lista.size(); i++) {
	        System.out.printf("%d;%s;", i, lista.get(i).getDocumento());
	        FechaHora fecha = lista.get(i).getFechaPosicion();        
	        System.out.printf("%02d/%02d/%04d;%02d:%02d;", 
	        		fecha.getFecha().getDia(), 
	        		fecha.getFecha().getMes(), 
	        		fecha.getFecha().getAnio(),
	        		fecha.getHora().getHora(),
	        		fecha.getHora().getMinuto());
	        System.out.printf("%.4f;%.4f\n", lista.get(i).getCoordenada().getLatitud(), 
	        		lista.get(i).getCoordenada().getLongitud());
	    }
	}

	@Override
	public String toString() {
		String cadena = "";
		for(int i = 0; i < this.lista.size(); i++) {
			PosicionPersona pp = lista.get(i);
	        cadena += String.format("%s;", pp.getDocumento());
	        FechaHora fecha = pp.getFechaPosicion();        
	        cadena+=String.format("%02d/%02d/%04d;%02d:%02d;", 
	        		fecha.getFecha().getDia(), 
	        		fecha.getFecha().getMes(), 
	        		fecha.getFecha().getAnio(),
	        		fecha.getHora().getHora(),
	        		fecha.getHora().getMinuto());
	        cadena+=String.format("%.4f;%.4f\n", pp.getCoordenada().getLatitud(), 
	        		pp.getCoordenada().getLongitud());
	    }
		
		return cadena;
		
	}
	
}
