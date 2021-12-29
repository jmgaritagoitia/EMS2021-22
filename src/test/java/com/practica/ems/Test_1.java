package com.practica.ems;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

public class Test_1 {

	private static ContactosCovid contactosCovid;

	@BeforeEach
	void setUp() {
		String test_data_str = "PERSONA;87654321K;John;Smith;john.smith@example.com;My street, 25;08001;30/12/1980\n"
				+ "PERSONA;98765432J;Jane;Doe;jane.doe@example.com;Her street, 5;08500;12/01/1995\n"
				+ "LOCALIZACION;87654321K;15/10/2021;13:41;41.3870;2.1698\n"
				+ "LOCALIZACION;87654321K;15/10/2021;13:45;41.3870;2.1695\n"
				+ "LOCALIZACION;98765432J;15/10/2021;13:50;41.3871;2.1697\n"
				+ "LOCALIZACION;87654321K;15/10/2021;13:50;41.3871;2.1697\n";
		contactosCovid = new ContactosCovid();
		try {
			contactosCovid.loadData(test_data_str, false);
		} catch (EmsInvalidTypeException | EmsInvalidNumberOfDataException | EmsDuplicatePersonException
				| EmsDuplicateLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@DisplayName("Add an entry with invalid type")
	@Test
	void test_1_1() {
		assertThrows(EmsInvalidTypeException.class, () -> {
			contactosCovid.loadData(
					"PERSONAS;87654321K;John;Smith;john.smith@example.com;My street, 25;08001;30/12/1980\n", false);
		});
		assertThrows(EmsInvalidTypeException.class, () -> {
			contactosCovid.loadData("LOCALIZACIONA;87654321K;15/10/2021;13:41;41.3870;2.1698\n", false);
		});
	}

	@DisplayName("Add an person with invalid number of data")
	@Test
	void test_1_2() {
		assertThrows(EmsInvalidNumberOfDataException.class, () -> {
			contactosCovid.loadData(
					"PERSONA;87654321K;John;Smith;john.smith@example.com;My street, 25;08001;30/12/1980;extra data\n",
					false);
		});
	}

	@DisplayName("Add an location with invalid number of data")
	@Test
	void test_1_3() {
		assertThrows(EmsInvalidNumberOfDataException.class, () -> {
			contactosCovid.loadData("LOCALIZACION;extra data;87654321K;15/10/2021;13:41;41.3870;2.1698\n", false);
		});
	}

	@DisplayName("Add a duplicated person")
	@Test
	void test_1_5() {

		assertThrows(EmsDuplicatePersonException.class, () -> {
			contactosCovid.loadData(
					"PERSONA;87654321K;John;Smith;john.smith@example.com;My street, 25;08001;30/12/1980\n", false);
		});
	}

	@DisplayName("Add a duplicated location")
	@Test
	void test_1_6() {
		assertThrows(EmsDuplicateLocationException.class, () -> {
			contactosCovid.loadData("LOCALIZACION;87654321K;15/10/2021;13:41;41.3870;2.1698\n", false);
		});
	}

	@DisplayName("Add a valid person entry")
	@Test
	void test_1_7() throws EmsPersonNotFoundException {
		try {
			contactosCovid.loadData(
					"PERSONA;12345678J;Juan Manuel;Garitagoitia;juan.garitagoitia@example.com;My street, 25;28001;30/12/1980\n",
					false);
			Assertions.assertEquals(contactosCovid.findPersona("87654321K"), 1);
		} catch (EmsInvalidTypeException | EmsInvalidNumberOfDataException | EmsDuplicatePersonException
				| EmsDuplicateLocationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargar una persona valida");
		}
	}

	@DisplayName("Add a valid location entry")
	@Test
	void test_1_8() throws EmsLocalizationNotFoundException {
		try {
			contactosCovid.loadData("LOCALIZACION;12345678J;15/10/2021;13:41;41.3870;2.1698\n", false);
			Assertions.assertEquals(contactosCovid.findLocalizacion("12345678J", "15/10/2021", "13:41"), 5);
		} catch (EmsInvalidTypeException | EmsInvalidNumberOfDataException | EmsDuplicatePersonException
				| EmsDuplicateLocationException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargar una localizacion valida");
		}
	}

	@DisplayName("Find an existing person")
	@Test
	void test_1_9() throws EmsPersonNotFoundException {
		assertEquals(contactosCovid.findPersona("87654321K"), 1);

	}

	@DisplayName("Find a non existing person")
	@Test
	void test_1_10() throws EmsPersonNotFoundException {
		assertThrows(EmsPersonNotFoundException.class, () -> {
			assertEquals(contactosCovid.findPersona("11111111A"), -1);
		});

	}
}
