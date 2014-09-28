package servicio;

import java.util.LinkedList;
import java.util.List;

import modelo.Aeropuerto;
import modelo.Color;
import modelo.Vuelo;

public class Aterrizar {

	public static String mejorVueloA(Aeropuerto[] aeropuertos) {
		if (existeVuelo(aeropuertos, aeropuertos[0], aeropuertos[1], -2)) {
			Vuelo min = aeropuertos[1].primeroEnLlegar();
			StringBuilder builder = new StringBuilder();
			builder.insert(0, " " + min.id());
			String llegada = min.llegada() + " ";
			int k = 1;
			while (!min.origen().equals(aeropuertos[0])) {
				min = min.origen().primeroEnLlegar();
				builder.insert(0, " " + min.id());
				k++;
			}
			return llegada + k + builder.toString();
		}
		return "no";
	}

	public static String mejorVuelo(Aeropuerto[] aeropuertos) {
		return mejorVueloB(aeropuertos);
	}

	public static String mejorVueloB(Aeropuerto[] aeropuertos) {
		aterrizar(aeropuertos, aeropuertos[0], aeropuertos[1]);
		if (aeropuertos[0].primeroEnLlegar() != null) {
			Vuelo min = aeropuertos[1].primeroEnLlegar();
			StringBuilder builder = new StringBuilder();
			builder.insert(0, " " + min.id());
			String llegada = min.llegada() + " ";
			int k = 1;
			while (!min.origen().equals(aeropuertos[0])) {
				min = min.origen().primeroEnLlegar();
				builder.insert(0, " " + min.id());
				k++;
			}
			return llegada + k + builder.toString();
		}
		return "no";
	}

	public static boolean existeVuelo(Aeropuerto[] aeropuertos,
			Aeropuerto inicio, Aeropuerto destino, int t) {
		if (inicio.equals(destino)) {
			return true;
		}
		if (t + 2 <= inicio.obtenerUltimoVueloQueLlega()) {
			return true;
		}
		boolean llego = false;
		List<Vuelo> vuelos = inicio.vuelosQueSalen();
		inicio.vaciarVuelosQueSalen();
		for (Vuelo vuelo : vuelos) {
			if (vuelo.partida() >= t + 2) {
				if (vuelo.color().equals(Color.BLANCO)) {
					if (existeVuelo(aeropuertos, vuelo.destino(), destino,
							vuelo.llegada())) {
						vuelo.cambiarColor(Color.VERDE);
						if (vuelo.destino().primeroEnLlegar() == null
								|| vuelo.destino().primeroEnLlegar().llegada() > vuelo
										.llegada()) {
							vuelo.destino().agregarPrimeroEnLlegar(vuelo);
						}
						if (inicio.obtenerUltimoVueloQueLlega() < vuelo
								.llegada()) {
							inicio.cambiarUltimoVueloQueLlega(vuelo);
						}
						llego = true;
					} else {
						vuelo.cambiarColor(Color.ROJO);
					}
				}
			} else {
				inicio.agregarAvuelosQueSalen(vuelo);
			}
		}
		return llego;
	}

	public int mejorHora(Vuelo vuelo) {

		return 0;
	}

	public static void aterrizar(Aeropuerto[] aeropuertos, Aeropuerto inicio,
			Aeropuerto destino) {
		List<Aeropuerto> proximos = new LinkedList<Aeropuerto>();
		proximos.add(destino);
		while (!proximos.isEmpty()) {
			Aeropuerto proximo = proximos.get(0);
			proximos.remove(0);
			List<Vuelo> vuelos = proximo.vuelosQueLlegan();
			proximo.vaciarVuelosQueLlegan();
			for (Vuelo vuelo : vuelos) {
				if (vuelo.llegada() + 2 <= proximo.obtenerUltimoVueloQueLlega()
						|| proximo.equals(destino)) {
					if (vuelo.origen().obtenerUltimoVueloQueLlega() == -1
							|| vuelo.origen().obtenerUltimoVueloQueLlega() < vuelo
									.partida()) {
						vuelo.origen().cambiarUltimoVueloQueLlega(vuelo);
					}
					vuelo.cambiarColor(Color.VERDE);
					// if (proximo.primeroEnLlegar() == null
					// || proximo.primeroEnLlegar().llegada() > vuelo
					// .llegada()) {
					// proximo.agregarPrimeroEnLlegar(vuelo);
					// }
					if (!vuelo.origen().yaLoCalcule()) {
						proximos.add(vuelo.origen());
						vuelo.origen().loCalcule();
					}
				}
			}
		}
	}
}
// private static boolean existeVuelo(Aeropuerto[] aeropuertos,
// Aeropuerto inicio, Aeropuerto destino, int t) {
// if (inicio.equals(destino)) {
// return true;
// }
// boolean llego = false;
// for (Vuelo vuelo : inicio.vuelosQueSalen()) {
// if (vuelo.partida() >= t + 2) {
// if (vuelo.color().equals(Color.VERDE)) {
// llego = true;
// }
// if (vuelo.color().equals(Color.BLANCO)) {
// if (existeVuelo(aeropuertos, vuelo.destino(), destino,
// vuelo.llegada())) {
// vuelo.cambiarColor(Color.VERDE);
// if (vuelo.destino().primeroEnLlegar() == null
// || vuelo.destino().primeroEnLlegar().llegada() > vuelo
// .llegada()) {
// vuelo.destino().agregarPrimeroEnLlegar(vuelo);
// }
// llego = true;
// } else {
// vuelo.cambiarColor(Color.ROJO);
// }
// }
// }
// }
// return llego;
// }