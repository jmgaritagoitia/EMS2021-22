package com.practica.ems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.practica.exception.EmsDuplicateLocationException;
import com.practica.exception.EmsDuplicatePersonException;
import com.practica.exception.EmsInvalidNumberOfDataException;
import com.practica.exception.EmsInvalidTypeException;
import com.practicas.ems.covid.ContactosCovid;

public class Test_3 {
	private static ContactosCovid contactosCovid;

	@BeforeEach
	void setUp() {		
		contactosCovid = new ContactosCovid();
		contactosCovid.loadDataFile("datos2.txt", false);
	}
	@DisplayName("Se genera la lista de contactos")
	@Test
	void test_1 () {
		assertNotNull(contactosCovid.getListaContactos());		
	}
	
	@DisplayName("Comprobamos la primera localización del fichero")
	@Test
	void test_2 () {
		assertEquals(contactosCovid.getListaContactos().getPrimerNodo(), "25/05/2021;16:30");		
	}
	
	@DisplayName("Comprobamos el número total de nodos temporales")
	@Test
	void test_3 () {
		assertEquals(contactosCovid.getListaContactos().tamanioLista(), 4);		
	}
	
	@DisplayName("Aniadimos un nuevo nodo temporal al principio de la lista")
	@Test
	void test_4 () throws EmsInvalidTypeException, EmsInvalidNumberOfDataException, EmsDuplicatePersonException, EmsDuplicateLocationException {
		contactosCovid.loadData("LOCALIZACION;66666666S;25/05/2021;11:01;44.3870;2.4698", false);
		assertEquals(contactosCovid.getListaContactos().getPrimerNodo(), "25/05/2021;11:01");		
	}
	
	@DisplayName("Al aniadir un nuevo nodo al principio la lista temporal aumenta en uno")
	@Test
	void test_5 () throws EmsInvalidTypeException, EmsInvalidNumberOfDataException, EmsDuplicatePersonException, EmsDuplicateLocationException {
		int tam_ant = contactosCovid.getListaContactos().tamanioLista();
		contactosCovid.loadData("LOCALIZACION;66666666S;25/05/2021;11:01;44.3870;2.4698", false);
		assertEquals(contactosCovid.getListaContactos().tamanioLista(), (tam_ant+1));		
	}
	
	@DisplayName("Aniadimos un nuevo nodo temporal al final de la lista")
	@Test
	void test_6 () throws EmsInvalidTypeException, EmsInvalidNumberOfDataException, EmsDuplicatePersonException, EmsDuplicateLocationException {
		contactosCovid.loadData("LOCALIZACION;66666666S;25/05/2021;19:01;44.3870;2.4698", false);
		assertEquals(contactosCovid.getListaContactos().toString(), "25/05/2021;16:30 25/05/2021;16:36 25/05/2021;17:18 25/05/2021;18:01 25/05/2021;19:01");		
	}
	
	@DisplayName("Aniadimos un nuevo nodo temporal en medio de la lista")
	@Test
	void test_7 () throws EmsInvalidTypeException, EmsInvalidNumberOfDataException, EmsDuplicatePersonException, EmsDuplicateLocationException {
		contactosCovid.loadData("LOCALIZACION;12121212R;25/05/2021;17:05;43.3870;2.3698", false);
		assertEquals(contactosCovid.getListaContactos().toString(), "25/05/2021;16:30 25/05/2021;16:36 25/05/2021;17:05 25/05/2021;17:18 25/05/2021;18:01");		
	}
}
