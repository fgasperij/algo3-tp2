/*
 * Creation : 23/09/2014
 */
package test;

import java.util.Date;
import java.util.Random;

import modelo.Aeropuerto;
import modelo.Vuelo;
import servicio.Aterrizar;

public class TestGeneradorDeCasos {

	private Random random = new Random(new Date().getTime());

	int MAX_HS_VUELO = 1000;

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
		Vuelo[] vuelos = crearVuelos(CIUDADES, VUELOS_POR_CIUDAD, MAX_HS_VUELO, aeropuertos);
		String sol = Aterrizar.mejorVuelo(aeropuertos);
		System.out.println(sol);
		chequearSol(sol, aeropuertos, vuelos);
	}

	public void testUnVuelo() {
		int CIUDADES = 10;
		int VUELOS_POR_CIUDAD = 12;
		Aeropuerto[] aeropuertos = new Aeropuerto[CIUDADES];
		Vuelo[] vuelos = crearVuelos(CIUDADES, VUELOS_POR_CIUDAD, MAX_HS_VUELO, aeropuertos);
		String sol = Aterrizar.mejorVuelo(aeropuertos);
		System.out.println(sol);
		chequearSol(sol, aeropuertos, vuelos);
	}

	private Vuelo[] crearVuelos(int CIUDADES, int VUELOS_POR_CIUDAD, int MAX_HS_VUELO, Aeropuerto[] aeropuertos) {
		for (int i = 0; i < aeropuertos.length; i++) {
			aeropuertos[i] = new Aeropuerto(String.valueOf(i), i);
		}
		int id_vuelo = 1;
		Vuelo[] vuelos = new Vuelo[VUELOS_POR_CIUDAD * CIUDADES + 2];
		for (int i = 0; i < aeropuertos.length; i++) {
			Aeropuerto aeropuerto = aeropuertos[i];
			for (int j = 0; j < VUELOS_POR_CIUDAD; j++) {
				int horario_llegada = random.nextInt(MAX_HS_VUELO - 2) + 2;
				int horario_salida = random.nextInt(horario_llegada);
				Aeropuerto destino = aeropuertos[random.nextInt(aeropuertos.length)];
				Vuelo vuelo = new Vuelo(aeropuerto, destino, horario_salida, horario_llegada, id_vuelo);
				aeropuerto.agregarAvuelosQueSalen(vuelo);
				destino.agregarAvuelosQueLlegan(vuelo);
				vuelos[id_vuelo] = vuelo;
				id_vuelo++;
			}
		}
		return vuelos;
	}

	private void chequearSol(String sol, Aeropuerto[] aeropuertos, Vuelo[] vuelos) {
		if (!sol.equals("no")) {
			String[] values = sol.split(" ");
			assert (values.length == Integer.valueOf(values[1]) + 2);
			for (int i = 2; i < values.length - 1; i++) {
				int id_inicio = Integer.valueOf(values[i]);
				int id_destino = Integer.valueOf(values[i + 1]);
				Vuelo inicio = vuelos[id_inicio];
				Vuelo destino = vuelos[id_destino];
				assert (inicio.destino().ciudad().equals(destino.origen().ciudad()));
				assert (inicio.llegada() <= destino.partida() - 2);
			}
			Vuelo vuelo_inicial = vuelos[Integer.valueOf(values[2])];
			Vuelo vuelo_final = vuelos[Integer.valueOf(values[values.length - 1])];
			assert (vuelo_inicial.origen().ciudad().equals(aeropuertos[0].ciudad()));
			assert (vuelo_final.destino().ciudad().equals(aeropuertos[1].ciudad()));
		} else {
			assert (!existeSol(aeropuertos, aeropuertos[0], aeropuertos[1], MAX_HS_VUELO + 10));
		}
	}

	private boolean existeSol(Aeropuerto[] aeropuertos, Aeropuerto origen, Aeropuerto destino, int t) {
		if (origen.ciudad().equals(destino.ciudad())) {
			return true;
		}
		boolean existe = false;
		for (Vuelo vuelo : destino.vuelosQueLlegan()) {
			if (vuelo.llegada() <= t - 2) {
				existe = existe || existeSol(aeropuertos, origen, vuelo.origen(), vuelo.partida());
			}
		}
		return existe;
	}
}
