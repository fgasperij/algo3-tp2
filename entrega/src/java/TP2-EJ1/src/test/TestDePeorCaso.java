/*
 * Creation : 25/09/2014
 */
package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import main.Main;
import modelo.Aeropuerto;
import modelo.Vuelo;
import servicio.Aterrizar;

public class TestDePeorCaso extends AbstractTestCase {

	private static String origen = "origen";
	private static String destino = "destino";
	Random random = new Random(new Date().getTime());

	boolean check = false;

	public static void main(String[] args) {
		TestDePeorCaso testDePeorCaso = new TestDePeorCaso();
		testDePeorCaso.todosLosVuelosFallanEnLaSegundaCiudad();
		// testDePeorCaso.testTodosLosVuelosLlegan();
		// testDePeorCaso.testDeTodosLleganDesdeString();
		// testDePeorCaso.test2Ciudades();
		// System.out.println("test finalzaron correctamente!");
	}

	public void todosLosVuelosFallanEnLaSegundaCiudad() {
		int step = 100;
		int vuelosMax = 1000000;
		int corridas = 20;
		int inicio = 500;
		for (int j = inicio; j < vuelosMax; j += step) {
			int vuelos = j;
			String[] sVuelos = new String[vuelos];
			int mitad_vuelos = vuelos / 2;
			for (int i = 0; i < mitad_vuelos; i++) {
				sVuelos[i] = "1 3 1 3";
			}
			for (int i = mitad_vuelos; i < vuelos; i++) {
				sVuelos[i] = "3 2 4 5";
			}
			Main main = new Main();
			int prom = 0;
			for (int i = 0; i < corridas; i++) {
				Long antes = new Date().getTime();
				Aeropuerto[] aeropuertos = main.crearInstancia(sVuelos, "1", "2");
				String sol = Aterrizar.mejorVuelo(aeropuertos);
				Long dps = new Date().getTime();
				Long balance = dps - antes;
				if (prom > balance || prom == 0) {
					prom = balance.intValue();
				}
				assert (sol.equals("no"));
			}
			System.out.println(vuelos + "," + prom + "," + (prom*1000/vuelos) );
		}

	}

	public void testTodosLosVuelosLlegan() {
		int CIUDADES = 10000;
		int VUELOS_POR_CIUDAD = 1;
		int MAX_HS_VUELO = 1000;
		Aeropuerto[] aeropuertos = new Aeropuerto[CIUDADES];
		List<Vuelo> vuelos = crearVuelosComoLista(CIUDADES, VUELOS_POR_CIUDAD, MAX_HS_VUELO, aeropuertos);
		int id = vuelos.size() + 1;
		int maxhs = MAX_HS_VUELO + 10;
		for (int i = 0; i < aeropuertos.length; i++) {
			Aeropuerto aeropuerto = aeropuertos[i];
			Vuelo vuelo = new Vuelo(aeropuerto, aeropuertos[1], maxhs, maxhs + 1, id);
			id++;
			maxhs++;
			aeropuerto.agregarAvuelosQueSalen(vuelo);
			aeropuertos[1].agregarPrimeroEnLlegar(vuelo);
		}
		String sol = Aterrizar.mejorVuelo(aeropuertos);
		// System.out.println(sol);
		Vuelo[] vuelosArray = new Vuelo[vuelos.size() + 2];
		for (Vuelo vuelo : vuelos) {
			vuelosArray[vuelo.id()] = vuelo;
		}
		chequearSol(sol, aeropuertos, vuelosArray);
	}

	public void testDeTodosLleganDesdeString() {
		int ciudades = 100;
		for (int j = 1; j < 10000000; j += 1000) {
			int vuelosXciudad = j;
			String[] instancia = crearVuelos(ciudades, vuelosXciudad);
			Main main = new Main();
			Date inicio = new Date();
			Aeropuerto[] aeropuertos = main.crearInstancia(instancia, "0", "1");
			String sol = Aterrizar.mejorVuelo(aeropuertos);
			Date fin = new Date();
			// System.out.println(sol);
			if (check) {
				Vuelo[] vuelos = new Vuelo[instancia.length + 1];
				for (int i = 0; i < instancia.length; i++) {
					String[] s = instancia[i].split(" ");
					vuelos[i + 1] = new Vuelo(aeropuertos[Integer.valueOf(s[0])], aeropuertos[Integer.valueOf(s[1])], Integer.valueOf(s[2]),
							Integer.valueOf(s[3]), i + 1);
				}
				chequearSol(sol, aeropuertos, vuelos);
			}
			System.out.println("ciudades: " + ciudades + " vuelos: " + vuelosXciudad + " t: " + (fin.getTime() - inicio.getTime()));
		}

	}

	private String[] crearVuelos(int ciudades, int vuelosXciudad) {
		List<String> vuelos = new ArrayList<String>();
		for (int i = 0; i < ciudades - 1; i++) {
			for (int j = 0; j < vuelosXciudad; j++) {
				int horario_llegada = random.nextInt(MAX_HS_VUELO - 2) + 2;
				int horario_salida = random.nextInt(horario_llegada);
				String s = i + " " + random.nextInt(ciudades) + " " + horario_salida + " " + horario_llegada;
				// strings[id] = s;
				vuelos.add(s);
			}
		}
		for (int i = 0; i < ciudades - 1; i++) {
			String s = i + " " + 1 + " " + MAX_HS_VUELO + 2 + " " + MAX_HS_VUELO + 4;
			// strings[id] = s;
			vuelos.add(s);
		}
		String[] strings = new String[vuelos.size()];
		int id = 0;
		for (String string : vuelos) {
			strings[id] = string;
			id++;
		}
		return strings;
	}

	public void test2Ciudades() {
		for (int i = 0; i < 1000000000; i += 1000) {
			String[] vuelos = crearVuelosQueLlegan(i);
			Main main = new Main();
			int prom = 0;
			for (int j = 0; j < 30; j++) {
				Long inicio = new Date().getTime();
				Aeropuerto[] aeropuertos = main.crearInstancia(vuelos, "0", "1");
				String sol = Aterrizar.mejorVuelo(aeropuertos);
				Long fin = new Date().getTime();
				prom += fin - inicio;
			}
			System.out.println("ciudades: " + 2 + " vuelos: " + i + " t: " + (prom / 100));
		}

	}

	private String[] crearVuelosQueLlegan(int n) {
		String[] vuelos = new String[n];
		int id = 0;
		int h = 0;
		for (int i = 0; i < vuelos.length; i++) {
			vuelos[id] = "0 1 " + h + " " + h + 2;
			h += 3;
			id++;
		}
		return vuelos;
	}
}
