/*
 * Creation : 22/09/2014
 */
package test;

import main.Main;
import modelo.Aeropuerto;
import servicio.Aterrizar;

public class TestDeCasoBorde {

	private Main main = new Main();

	public static void main(String[] args) {
		TestDeCasoBorde testDeCasoBorde = new TestDeCasoBorde();
		testDeCasoBorde.testUnVuelo();
		testDeCasoBorde.testSinVuelos();
		testDeCasoBorde.test2HorasDeDiferenecia();
		testDeCasoBorde.testCiclosALaMismaCiudad();
		System.out.println("test terminaron bien!");
	}

	public void testSinVuelos() {
		String[] vuelos = new String[1];
		vuelos[0] = "1 1 0 5";
		Aeropuerto[] aeropuertos = main.crearInstancia(vuelos, "1", "2");
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("no"));
	}

	public void testUnVuelo() {
		String[] vuelos = new String[3];
		vuelos[0] = "1 2 0 10";
		vuelos[1] = "1 2 0 9";
		vuelos[2] = "1 2 0 8";
		Aeropuerto[] aeropuertos = main.crearInstancia(vuelos, "1", "2");
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("8 1 3"));
	}

	public void testLlegoPorUnCaminoAntesQueDerecho() {
		String[] vuelos = new String[3];
		vuelos[0] = "1 2 0 10";
		vuelos[1] = "1 3 0 3";
		vuelos[2] = "3 2 5 8";
		Aeropuerto[] aeropuertos = main.crearInstancia(vuelos, "1", "2");
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("8 2 2 3"));
	}

	public void test2HorasDeDiferenecia() {
		String[] vuelos = new String[3];
		vuelos[0] = "1 3 0 6";
		vuelos[1] = "1 3 0 5";
		vuelos[2] = "3 2 7 8";
		Aeropuerto[] aeropuertos = main.crearInstancia(vuelos, "1", "2");
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("8 2 2 3"));
		vuelos[0] = "1 3 0 6";
		vuelos[1] = "1 3 0 7";
		vuelos[2] = "3 2 7 8";
		aeropuertos = main.crearInstancia(vuelos, "1", "2");
		assert (Aterrizar.mejorVuelo(aeropuertos).equals("no"));
	}

	public void testCiclosALaMismaCiudad() {
		String[] vuelos = new String[3];
		vuelos[0] = "1 1 0 2";
		vuelos[1] = "1 2 3 5";
		vuelos[2] = "2 3 7 8";
		Aeropuerto[] aeropuertos = main.crearInstancia(vuelos, "1", "3");
		String sol = Aterrizar.mejorVuelo(aeropuertos);
		assert (sol.equals("8 2 2 3"));
	}

	public void testCiclos() {
		String[] vuelos = new String[3];
		vuelos[0] = "1 2 0 2";
		vuelos[1] = "2 1 4 6";
		vuelos[2] = "1 3 8 10";
		Aeropuerto[] aeropuertos = main.crearInstancia(vuelos, "1", "3");
		String sol = Aterrizar.mejorVuelo(aeropuertos);
		assert (sol.equals("10 1 3") || sol.equals("10 3 1 2 3"));
	}

}
