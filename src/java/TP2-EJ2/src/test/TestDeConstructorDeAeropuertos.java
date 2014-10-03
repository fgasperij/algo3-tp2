/*
 * Creation : 24/09/2014
 */
package test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.Main;
import modelo.Aeropuerto;
import servicio.Aterrizar;

public class TestDeConstructorDeAeropuertos {

	Random random = new Random(new Date().getTime());
	private int maxHora = 1000;
	private Main main = new Main();

	public static void main(String[] args) {
		TestDeConstructorDeAeropuertos testDeConstructorDeAeropuertos = new TestDeConstructorDeAeropuertos();
		testDeConstructorDeAeropuertos.testRandomAeropuertos();
		System.out.println("test finalzaron correctamente!");
	}

	public void testRandomAeropuertos() {
		for (int j = 500; j < 100000; j += 500) {
			int vuelos = j;
			int ciudades = vuelos / 20;
			String[] strings = new String[vuelos];
			List<Integer> set = new LinkedList<Integer>();
			for (int i = 0; i < strings.length; i++) {
				int inicio = random.nextInt(ciudades);
				set.add(inicio);
				int destino = random.nextInt(ciudades);
				set.add(destino);
				int llegada = random.nextInt(maxHora - 1) + 1;
				int despegue = random.nextInt(llegada);
				strings[i] = inicio + " " + destino + " " + despegue + " "
						+ llegada;
			}
			String origen = String.valueOf(set.get(random.nextInt(set.size())));
			String destino = String
					.valueOf(set.get(random.nextInt(set.size())));
			while (destino.equals(origen)) {
				destino = String.valueOf(set.get(random.nextInt(set.size())));
			}
			Long min = null;
			for (int i = 0; i < 10; i++) {
				Long antes = new Date().getTime();
				Aeropuerto[] aeropuertos = main.crearInstancia(strings, origen,
						destino);
				Long dps = new Date().getTime();
				Aterrizar.mejorVuelo(aeropuertos);
				min = min == null || min > (dps - antes) ? dps - antes : min;
			}
			System.out.println(j + "," + min + "," + min * 100000 / j);
		}
	}
}