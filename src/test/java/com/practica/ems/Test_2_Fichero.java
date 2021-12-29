package com.practica.ems;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.practica.exception.EmsDuplicateLocationException;
import com.practica.exception.EmsDuplicatePersonException;
import com.practica.exception.EmsInvalidNumberOfDataException;
import com.practica.exception.EmsInvalidTypeException;
import com.practica.exception.EmsLocalizationNotFoundException;
import com.practica.exception.EmsPersonNotFoundException;
import com.practicas.ems.covid.ContactosCovid;

public class Test_2_Fichero {

	private static ContactosCovid contactosCovid;

	@BeforeEach
	void setUp() {		
		contactosCovid = new ContactosCovid();
		contactosCovid.loadDataFile("datos.txt", false);
	}

	@DisplayName("Aniade una entrada con un tipo no valido")
	@Test
	void test_1_1() {
		assertThrows(EmsInvalidTypeException.class, () -> {
			contactosCovid.loadData(
					"PERSONAS;87654321K;Jessica;Diaz;jessica.diaz@ems.com;La calle de Jessica, 33;28033;25/01/1980", false);
		});
		assertThrows(EmsInvalidTypeException.class, () -> {
			contactosCovid.loadData("LOCALIZACIONA;87654321K;25/10/2021;23:41;41.3870;2.1698\n", false);
		});
	}

	@DisplayName("Aniade una persona con número de datos no válidos")
	@Test
	void test_1_2() {
		assertThrows(EmsInvalidNumberOfDataException.class, () -> {
			contactosCovid.loadData(
					"PERSONA;87654321K;Jessica;Diaz;jessica.diaz@ems.com;La calle de Jessica, 33;28033;25/01/1980;extra de Jessica\n",
					false);
		});
	}

	@DisplayName("Aniade localizacion con número de datos no validos")
	@Test
	void test_1_3() {
		assertThrows(EmsInvalidNumberOfDataException.class, () -> {
			contactosCovid.loadData("LOCALIZACION;extra de localizacion;87654321K;29/10/2020;13:41;41.3870;2.1698\n", false);
		});
	}

	@DisplayName("Aniade persona que ya existe")
	@Test
	void test_1_5() {

		assertThrows(EmsDuplicatePersonException.class, () -> {
			contactosCovid.loadData(
					"PERSONA;98765432J;Angel;Panizo;angel.panizo@ems.com;La calle de Angel, 46;28871;12/01/1995\n", false);
		});
	}

	@DisplayName("Aniade localización que ya existe")
	@Test
	void test_1_6() {
		assertThrows(EmsDuplicateLocationException.class, () -> {
			contactosCovid.loadData("LOCALIZACION;87654321K;25/10/2021;23:55;91.3971;12.1699\n", false);
		});
	}

	@DisplayName("Aniade persona valida")
	@Test
	void test_1_7() throws EmsPersonNotFoundException {
		try {
			contactosCovid.loadData(
					"PERSONA;12345678J;Juan Manuel;Garitagoitia;juanmanuel.garitagoitia@ems.com;La calle de Juanma, 225;28005;13/01/1972\n",
					false);
			Assertions.assertEquals(contactosCovid.findPersona("12345678J"), 3);
		} catch (EmsInvalidTypeException | EmsInvalidNumberOfDataException | EmsDuplicatePersonException
				| EmsDuplicateLocationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargar una persona valida");
		}
	}

	@DisplayName("Aniade localización valida")
	@Test
	void test_1_8() throws EmsLocalizationNotFoundException {
		try {
			contactosCovid.loadData("LOCALIZACION;12345678J;16/05/2021;20:45:41;54.3890;28.1698\n", false);
			Assertions.assertEquals(contactosCovid.findLocalizacion("12345678J", "16/05/2021", "20:45"), 5);
		} catch (EmsInvalidTypeException | EmsInvalidNumberOfDataException | EmsDuplicatePersonException
				| EmsDuplicateLocationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargar una localizacion valida");
		}
	}

	@DisplayName("Busca una persona que existe")
	@Test
	void test_1_9() throws EmsPersonNotFoundException {
		assertEquals(contactosCovid.findPersona("87654321K"), 1);

	}

	@DisplayName("Busca una persona que no existe")
	@Test
	void test_1_10() throws EmsPersonNotFoundException {
		assertThrows(EmsPersonNotFoundException.class, () -> {
			assertEquals(contactosCovid.findPersona("00000000A"), -1);
		});

	}
	
	@DisplayName("Obtiene localizaciones de una persona")
	@Test
	void test_1_11() throws EmsPersonNotFoundException {
		try {
			assertEquals(contactosCovid.localizacionPersona("87654321K").size(),3);
		} catch (EmsPersonNotFoundException e) {
			e.printStackTrace();
		}	
	}

	@DisplayName("Obtiene localizaciones de una persona que no existe")
	@Test
	void test_1_12() throws EmsPersonNotFoundException {
		assertThrows(EmsPersonNotFoundException.class, () ->{
			assertEquals(contactosCovid.localizacionPersona("00000000A").size(),3);
		});
	}
	
	@DisplayName("Elimina una persona que no existe")
	@Test
	void test_1_13() throws EmsPersonNotFoundException {
		assertThrows(EmsPersonNotFoundException.class, () ->{
			contactosCovid.delPersona("00000000A");
		});
	}
	
	@DisplayName("Elimina una persona que existe")
	@Test
	void test_1_14() throws EmsPersonNotFoundException {
		contactosCovid.delPersona("98765432J");
		assertEquals(contactosCovid.getPoblacion().getLista().size(),1);		
	}
}

