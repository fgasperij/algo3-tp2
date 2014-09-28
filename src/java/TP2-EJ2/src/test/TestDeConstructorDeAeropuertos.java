/*
 * Creation : 24/09/2014
 */
package test;

import java.util.Date;
import java.util.Random;

import main.Main;
import modelo.Aeropuerto;
import modelo.Vuelo;

public class TestDeConstructorDeAeropuertos {

	private static String origen = "origen";
	private static String destino = "destino";
	Random random = new Random(new Date().getTime());
	private int maxHora = 1000;
	private Main main = new Main();

	

	public void testSinVuelos() {
		String[] s = new String[0];
		Aeropuerto[] aeropuertos = main.crearInstancia(s, origen, destino);
		assert (aeropuertos.length == 2);
		assert (aeropuertos[0].ciudad().equals(origen));
		assert (aeropuertos[1].ciudad().equals(destino));
	}

	public void testSinMasCiudades() {
		int vuelos = 100;
		String[] s = new String[vuelos];
		int llegada = random.nextInt(maxHora) + 1;
		for (int i = 0; i < s.length; i++) {
			s[i] = origen + " " + destino + " " + random.nextInt(llegada) + " "
					+ llegada;
		}
		Aeropuerto[] aeropuertos = main.crearInstancia(s, origen, destino);
		assert (aeropuertos.length == 2);
		assert (aeropuertos[0].ciudad().equals(origen));
		assert (aeropuertos[1].ciudad().equals(destino));
		assert (aeropuertos[0].vuelosQueSalen().size() == vuelos);
		assert (aeropuertos[1].vuelosQueLlegan().size() == vuelos);
		for (Vuelo vuelo : aeropuertos[0].vuelosQueSalen()) {
			String sVuelo = s[vuelo.id() - 1];
			String[] values = sVuelo.split(" ");
			assert (values[0].equals(vuelo.origen().ciudad()));
			assert (values[1].equals(vuelo.destino().ciudad()));
			assert (values[2].equals(String.valueOf(vuelo.partida())));
			assert (values[3].equals(String.valueOf(vuelo.llegada())));
		}
	}

}
