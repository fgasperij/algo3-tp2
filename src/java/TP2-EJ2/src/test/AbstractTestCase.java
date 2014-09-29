/*
 * Creation : 25/09/2014
 */
package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import modelo.Aeropuerto;
import modelo.Vuelo;

public class AbstractTestCase {

	protected int MAX_HS_VUELO = 10000;

	protected Random random = new Random(new Date().getTime());

	public void chequearSol(String sol, Aeropuerto[] aeropuertos, Vuelo[] vuelos) {
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

	public Vuelo[] crearVuelosComoArray(int CIUDADES, int VUELOS_POR_CIUDAD, int MAX_HS_VUELO, Aeropuerto[] aeropuertos) {
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

	public List<Vuelo> crearVuelosComoLista(int CIUDADES, int VUELOS_POR_CIUDAD, int MAX_HS_VUELO, Aeropuerto[] aeropuertos) {
		for (int i = 0; i < aeropuertos.length; i++) {
			aeropuertos[i] = new Aeropuerto(String.valueOf(i), i);
		}
		int id_vuelo = 1;
		List<Vuelo> vuelos = new ArrayList<Vuelo>();
		for (int i = 0; i < aeropuertos.length; i++) {
			Aeropuerto aeropuerto = aeropuertos[i];
			for (int j = 0; j < VUELOS_POR_CIUDAD; j++) {
				int horario_llegada = random.nextInt(MAX_HS_VUELO - 2) + 2;
				int horario_salida = random.nextInt(horario_llegada);
				Aeropuerto destino = aeropuertos[random.nextInt(aeropuertos.length)];
				Vuelo vuelo = new Vuelo(aeropuerto, destino, horario_salida, horario_llegada, id_vuelo);
				aeropuerto.agregarAvuelosQueSalen(vuelo);
				destino.agregarAvuelosQueLlegan(vuelo);
				vuelos.add(vuelo);
				id_vuelo++;
			}
		}
		return vuelos;
	}
}
