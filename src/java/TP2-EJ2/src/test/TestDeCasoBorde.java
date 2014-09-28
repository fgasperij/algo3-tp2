/*
 * Creation : 22/09/2014
 */
package test;

import modelo.Aeropuerto;
import modelo.Vuelo;
import servicio.Aterrizar;

public class TestDeCasoBorde {

	private static final int CIUDADES = 10;

	public static void main(String[] args) {
		TestDeCasoBorde testDeCasoBorde = new TestDeCasoBorde();
		testDeCasoBorde.testUnVuelo();
		testDeCasoBorde.testSinVuelos();
		testDeCasoBorde.test2HorasDeDiferenecia();
		System.out.println("test terminaron bien!");
	}

	public void testSinVuelos() {
		Aeropuerto[] aeropuertos = crearAeropuertos();
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("no"));
	}

	public void testUnVuelo() {
		Aeropuerto[] aeropuertos = crearAeropuertos();
		aeropuertos[0].agregarAvuelosQueSalen(new Vuelo(aeropuertos[0], aeropuertos[1], 0, 10, 1));
		aeropuertos[0].agregarAvuelosQueSalen(new Vuelo(aeropuertos[0], aeropuertos[1], 0, 9, 2));
		aeropuertos[0].agregarAvuelosQueSalen(new Vuelo(aeropuertos[0], aeropuertos[1], 0, 8, 3));
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("8 1 3"));
	}

	public void test2HorasDeDiferenecia() {
		Aeropuerto[] aeropuertos = crearAeropuertos();
		aeropuertos[0].agregarAvuelosQueSalen(new Vuelo(aeropuertos[0], aeropuertos[2], 0, 6, 1));
		aeropuertos[0].agregarAvuelosQueSalen(new Vuelo(aeropuertos[0], aeropuertos[2], 0, 5, 2));
		aeropuertos[2].agregarAvuelosQueSalen(new Vuelo(aeropuertos[2], aeropuertos[1], 7, 8, 3));
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("8 2 2 3"));
		aeropuertos = crearAeropuertos();
		aeropuertos[0].agregarAvuelosQueSalen(new Vuelo(aeropuertos[0], aeropuertos[2], 0, 6, 1));
		aeropuertos[0].agregarAvuelosQueSalen(new Vuelo(aeropuertos[0], aeropuertos[2], 0, 7, 2));
		aeropuertos[2].agregarAvuelosQueSalen(new Vuelo(aeropuertos[2], aeropuertos[1], 7, 8, 3));
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("no"));
	}

	private Aeropuerto[] crearAeropuertos() {
		Aeropuerto[] aeropuertos = new Aeropuerto[CIUDADES];
		for (int i = 0; i < aeropuertos.length; i++) {
			aeropuertos[i] = new Aeropuerto(String.valueOf(i), i);
		}
		return aeropuertos;
	}
}
