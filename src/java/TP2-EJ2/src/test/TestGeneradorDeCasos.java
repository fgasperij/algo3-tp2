/*
 * Creation : 23/09/2014
 */
package test;

import java.util.Date;
import java.util.Random;

import modelo.Aeropuerto;
import modelo.Vuelo;
import servicio.Aterrizar;

public class TestGeneradorDeCasos extends AbstractTestCase {

	private Random random = new Random(new Date().getTime());

	public static void main(String[] args) {
		TestGeneradorDeCasos testGeneradorDeCasos = new TestGeneradorDeCasos();
		testGeneradorDeCasos.testMuchosVuelos();
		testGeneradorDeCasos.testUnVuelo();
		System.out.println("test terminaron bien!");
	}

	public void testMuchosVuelos() {
		int CIUDADES = 100;
		int VUELOS_POR_CIUDAD = CIUDADES * 10;
		int MAX_HS_VUELO = 1000;
		Aeropuerto[] aeropuertos = new Aeropuerto[CIUDADES];
		Vuelo[] vuelos = crearVuelosComoArray(CIUDADES, VUELOS_POR_CIUDAD, MAX_HS_VUELO, aeropuertos);
		String sol = Aterrizar.mejorVuelo(aeropuertos);
		System.out.println(sol);
		chequearSol(sol, aeropuertos, vuelos);
	}

	public void testUnVuelo() {
		int CIUDADES = 10;
		int VUELOS_POR_CIUDAD = 12;
		Aeropuerto[] aeropuertos = new Aeropuerto[CIUDADES];
		Vuelo[] vuelos = crearVuelosComoArray(CIUDADES, VUELOS_POR_CIUDAD, MAX_HS_VUELO, aeropuertos);
		String sol = Aterrizar.mejorVuelo(aeropuertos);
		System.out.println(sol);
		chequearSol(sol, aeropuertos, vuelos);
	}

}
