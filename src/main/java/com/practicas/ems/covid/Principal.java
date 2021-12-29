package com.practicas.ems.covid;

import com.practica.exception.EmsDuplicateLocationException;
import com.practica.exception.EmsDuplicatePersonException;
import com.practica.exception.EmsInvalidNumberOfDataException;
import com.practica.exception.EmsInvalidTypeException;

public class Principal {
	
	
	public static void main(String[] args) throws EmsDuplicatePersonException, EmsDuplicateLocationException {
		String test_data_str = "PERSONA;87654321K;John;Smith;john.smith@example.com;My street, 25;08001;30/12/1980\n" +
	            "PERSONA;98765432J;Jane;Doe;jane.doe@example.com;Her street, 5;08500;12/01/1995\n" +
	            "LOCALIZACION;87654321K;15/10/2021;13:41;41.3870;2.1698\n" +
	            "LOCALIZACION;87654321K;15/10/2021;13:45;41.3870;2.1695\n" +
	            "LOCALIZACION;98765432J;15/10/2021;13:50;41.3871;2.1697\n" +
	            "LOCALIZACION;87654321K;15/10/2021;13:50;41.3871;2.1697\n";
		ContactosCovid contactosCovid = new ContactosCovid();
		try {
			contactosCovid.loadData(test_data_str, false);
			
		} catch (EmsInvalidTypeException | EmsInvalidNumberOfDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
