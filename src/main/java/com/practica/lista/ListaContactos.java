package com.practica.lista;

import com.practica.genericas.Coordenada;
import com.practica.genericas.PosicionPersona;

public class ListaContactos {
	private NodoTemporal lista;
	private int size;
	
	public void insertarNodoTemporal (PosicionPersona p) {
		NodoTemporal aux = lista, ant=null;
		boolean salir=false,  encontrado = false;
		/**
		 * Busco la posición adecuada donde meter el nodo de la lista, excepto
		 * que esté en la lista. Entonces solo añadimos una coordenada.
		 */
		while (aux!=null && !salir) {
			if(aux.getFecha().compareTo(p.getFechaPosicion())==0) {
				encontrado = true;
				salir = true;
				this.insertarCoordenada(aux, p.getCoordenada());
			}else if(aux.getFecha().compareTo(p.getFechaPosicion())<0) {
				ant = aux;
				aux=aux.getSiguiente();
			}else if(aux.getFecha().compareTo(p.getFechaPosicion())>0) {
				salir=true;
			}
		}
		/**
		 * No hemos encontrado ninguna posición temporal, así que
		 * metemos un nodo nuevo en la lista
		 */
		if(!encontrado) {
			NodoTemporal nuevo = new NodoTemporal();
			nuevo.setFecha(p.getFechaPosicion());
			this.insertarCoordenada(nuevo, p.getCoordenada());
			if(ant!=null) {
				nuevo.setSiguiente(aux);
				ant.setSiguiente(nuevo);
			}else {
				nuevo.setSiguiente(lista);
				lista = nuevo;
			}
			this.size++;
		}
	}
	/**
	 * 
	 * Insertamos la coordenada en lia lista de coordenadas el nodo temporal 
	 * en el que estamos trabajando
	 */
	private void insertarCoordenada (NodoTemporal nodo,  Coordenada coordenada) {
		NodoPosicion actual = nodo.getListaCoordenadas();
		NodoPosicion ant=null;
		boolean encontrado = false;
		while (actual!=null && !encontrado) {
			if(actual.getCoordenada().equals(coordenada)) {
				encontrado=true;
				actual.setNumPersonas(actual.getNumPersonas()+1);
			}else {
				ant = actual;
				actual = actual.getSiguiente();
			}
		}
		if(!encontrado) {
			NodoPosicion nuevo = new NodoPosicion(coordenada, 1, null);
			if(nodo.getListaCoordenadas()==null)
				nodo.setListaCoordenadas(nuevo);
			else
				ant.setSiguiente(nuevo);			
		}
	}
	
	
	public int personasEnCoordenadas () {
		NodoPosicion aux = this.lista.getListaCoordenadas();
		if(aux==null)
			return 0;
		else {
			int cont;
			for(cont=0;aux!=null;) {
				cont += aux.getNumPersonas();
				aux=aux.getSiguiente();
			}
			return cont;
		}
	}
	
	public int tamanioLista () {
		return this.size;
	}

	public String getPrimerNodo() {
		String cadena = this.lista.getFecha().getFecha().toString();
		cadena+= ";" +  this.lista.getFecha().getHora().toString();
		return cadena;
	}

	@Override
	public String toString() {
		String cadena="";
		int a,cont;
		cont=0;
		NodoTemporal aux = lista;
		for(cont=1; cont<size; cont++) {
			cadena += aux.getFecha().getFecha().toString();
			cadena += ";" +  aux.getFecha().getHora().toString() + " ";
			aux=aux.getSiguiente();
		}
		cadena += aux.getFecha().getFecha().toString();
		cadena += ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}
	
	
	
}
