package servicio;

import java.util.LinkedList;
import java.util.List;

import modelo.Aeropuerto;
import modelo.Color;
import modelo.Vuelo;

public class Aterrizar {

	public static String mejorVuelo(Aeropuerto[] aeropuertos) {
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

	public static boolean existeVuelo(Aeropuerto[] aeropuertos, Aeropuerto inicio, Aeropuerto destino, int t) {
		if (inicio.equals(destino)) {
			return true;
		}
		if (t + 2 <= inicio.obtenerUltimoVueloQueLlega()) {
			return true;
		}
		boolean llego = false;
		List<Vuelo> vuelos = inicio.vuelosQueSalen();
		List<Vuelo> vuelosNoAnalizados = new LinkedList<Vuelo>();
		inicio.vaciarVuelosQueSalen();
		for (Vuelo vuelo : vuelos) {
			if (vuelo.partida() >= t + 2) {
				if (vuelo.color().equals(Color.BLANCO)) {
					if (existeVuelo(aeropuertos, vuelo.destino(), destino, vuelo.llegada())) {
						vuelo.cambiarColor(Color.VERDE);
						if (vuelo.destino().primeroEnLlegar() == null || vuelo.destino().primeroEnLlegar().llegada() > vuelo.llegada()) {
							vuelo.destino().agregarPrimeroEnLlegar(vuelo);
						}
						if (inicio.obtenerUltimoVueloQueLlega() < vuelo.llegada()) {
							inicio.cambiarUltimoVueloQueLlega(vuelo);
						}
						llego = true;
					} else {
						vuelo.cambiarColor(Color.ROJO);
					}
				}
			} else {
				vuelosNoAnalizados.add(vuelo);
			}
		}
		inicio.asignarVuelosQueLlegan(vuelosNoAnalizados);
		return llego;
	}

}
