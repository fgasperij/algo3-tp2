package servicio;

import java.util.List;

import modelo.Aeropuerto;
import modelo.Color;
import modelo.Vuelo;

public class Aterrizar {

	public static void mejorVuelo(Aeropuerto[] aeropuertos) {
		if (existeVuelo(aeropuertos, aeropuertos[0], aeropuertos[1], 0)) {
			System.out.println("llego");
			Vuelo min = null;
			for (Vuelo vuelo : aeropuertos[1].vuelosQueLlegan()) {
				min = (min == null || vuelo.llegada() < min.llegada())
						&& vuelo.color().equals(Color.VERDE) ? vuelo : min;
			}
			StringBuilder builder = new StringBuilder();
			builder.insert(0, " " + min.id());
			String llegada = min.llegada() + " ";
			int k = 1;
			while (!min.origen().equals(aeropuertos[0])) {
				min = buscarProx(min, aeropuertos);
				builder.insert(0, " " + min.id());
				k++;
			}
			System.out.println(llegada + k + builder.toString());
		} else {
			System.out.println("no");
		}

	}

	private static Vuelo buscarProx(Vuelo min, Aeropuerto[] aeropuertos) {
		Aeropuerto aeropuerto = min.origen();
		List<Vuelo> vuelos = aeropuerto.vuelosQueLlegan();
		for (Vuelo vuelo : vuelos) {
			if (vuelo.color().equals(Color.VERDE)) {
				return vuelo;
			}
		}
		throw new RuntimeException("fallo encontrando vuelos");
	}

	private static boolean existeVuelo(Aeropuerto[] aeropuertos,
			Aeropuerto inicio, Aeropuerto destino, int t) {
		if (inicio.equals(destino)) {
			return true;
		}
		// Aeropuerto.maximoVerde = hora de salida del vuelvo verde que sale mas tarde
		boolean llego = false;
		if (inicio.maximoVerde >= t) {
			llego = true;
		}
		for (Vuelo vuelo : inicio.vuelosQueSalen()) {
				if (vuelo.color().equals(Color.BLANCO) && vuelo.salida() >= t+2) { 
					inicio.vuelosQueSalen().remove(vuelo);
					if (existeVuelo(aeropuertos, vuelo.destino(), destino,
							vuelo.llegada())) {
						vuelo.cambiarColor(Color.VERDE);
						if (vuelo.salida() > inicio.maximoVerde) {
							inicio.maximoVerde = vuelo.salida();
						}
						llego = true;
					} else {
						vuelo.cambiarColor(Color.ROJO);
					}
				}
			}
		return llego;
	}
}
