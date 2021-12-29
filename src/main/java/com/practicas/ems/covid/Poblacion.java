package com.practicas.ems.covid;

import java.util.Iterator;
import java.util.LinkedList;

import com.practica.ems.Persona;
import com.practica.exception.EmsDuplicatePersonException;
import com.practica.exception.EmsPersonNotFoundException;

public class Poblacion {
	LinkedList<Persona> lista ;

	public Poblacion() {
		super();
		this.lista = new LinkedList<Persona>();
	}
	
	public void addPersona (Persona persona) throws EmsDuplicatePersonException {
		try {
			findPersona(persona.getDocumento());
			throw new EmsDuplicatePersonException();
		} catch (EmsPersonNotFoundException e) {
			lista.add(persona);
		} 
	}
	
	public void delPersona(String documento) throws EmsPersonNotFoundException {
		int pos=-1;
		try {
			pos = findPersona(documento);
		} catch (EmsPersonNotFoundException e) {
			throw new EmsPersonNotFoundException();
		}
		lista.remove(pos);		
	}
	
	public int findPersona (String documento) throws EmsPersonNotFoundException {
		int cont=0;
		Iterator<Persona> it = lista.iterator();
		while (it.hasNext() ) {
			Persona persona = it.next();
			cont++;
			if(persona.getDocumento().equals(documento)) {
				return cont;
			}
		}		
		throw new EmsPersonNotFoundException();
	}
	
	public void printPoblacion() {   
	    for(int i = 0; i < lista.size(); i++) {
	        // Print position and document	    	
	        System.out.printf("%d;%s;", i, lista.get(i).getDocumento());
	        // Print name and surname	              
	        System.out.printf("%s,%s;",lista.get(i).getApellidos(), lista.get(i).getNombre());	        
	        // Print email	
	        System.out.printf("%s;", lista.get(i).getEmail());
	        // Print address and CP
	        System.out.printf("%s,%s;", lista.get(i).getDireccion(), lista.get(i).getCp());	        
	        // Print birthday date
	        System.out.printf("%02d/%02d/%04d\n", lista.get(i).getFechaNacimiento().getFecha().getDia(), 
	         lista.get(i).getFechaNacimiento().getFecha().getMes(), 
	         lista.get(i).getFechaNacimiento().getFecha().getAnio());	        
	    }
	}
	
	
	
}
