package com.practicas.ems.covid;

import com.practica.exception.EmsDuplicateLocationException;
import com.practica.exception.EmsDuplicatePersonException;
import com.practica.exception.EmsInvalidNumberOfDataException;
import com.practica.exception.EmsInvalidTypeException;

public class Principal {
	
	
	public static void main(String[] args) throws EmsDuplicatePersonException, EmsDuplicateLocationException {
		String test_data_str = "PERSONA;87654321K;Jessica;Diaz;jessica.diaz@ems.com;La calle de jessica, 33;28033;25/01/1980\n" +
	            "PERSONA;98765432J;Angel;Panizo;angel.panizo@ems.com;La calle de Angel, 46;28871;12/01/1995\n" +
	            "LOCALIZACION;87654321K;25/10/2021;23:41;41.3870;2.1698\n" +
	            "LOCALIZACION;87654321K;25/10/2021;23:45;41.3870;2.1695\n" +
	            "LOCALIZACION;98765432J;25/10/2021;23:55;41.3871;2.1697\n" +
	            "LOCALIZACION;87654321K;25/10/2021;23:55;41.3871;2.1697\n";
		ContactosCovid contactosCovid = new ContactosCovid();
		try {
			contactosCovid.loadData(test_data_str, false);
			System.out.println(contactosCovid.getLocalizacion().toString());
			System.out.println(contactosCovid.getPoblacion().toString());
		} catch (EmsInvalidTypeException | EmsInvalidNumberOfDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
