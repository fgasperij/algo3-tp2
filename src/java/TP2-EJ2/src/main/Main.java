package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import servicio.Aterrizar;

import modelo.Aeropuerto;
import modelo.Vuelo;

public class Main {

	/**
	 * @param args
	 * @throws
	 */
	public static void main(String[] args) throws IOException {
		runAlgo("vuelos");
	}

	public static String runAlgo(String file) throws IOException {
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			line = br.readLine();
			String[] values = line.split(" ");
			String origen = values[0];
			String destino = values[1];
			int n = Integer.valueOf(values[2]);
			// line = br.readLine();
			String[] lines = new String[n];
			for (int j = 0; j < n; j++) {
				lines[j] = br.readLine();
			}
			Aeropuerto[] aeropuertos = crearInstancia(lines, origen, destino, n);
			Aterrizar.mejorVuelo(aeropuertos);
		} catch (FileNotFoundException e) {
			System.out.println("archivo no encontrado");
		} catch (IOException e) {
			System.out.println("error leyendo archivo");
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return builder.toString();
	}

	private static Aeropuerto[] crearInstancia(String[] lines, String origen,
			String destino, int n) {
		Set<String> ciudades = new TreeSet<String>();
		ciudades.add(origen);
		ciudades.add(destino);
		for (int i = 0; i < lines.length; i++) {
			String[] values = lines[i].split(" ");
			ciudades.add(values[0]);
			ciudades.add(values[1]);
		}
		Aeropuerto[] aeropuertos = new Aeropuerto[ciudades.size()];
		Map<String, Integer> ids = new HashMap<String, Integer>(ciudades.size());
		int i = 0;
		for (String ciudad : ciudades) {
			aeropuertos[i] = new Aeropuerto(ciudad, i);
			ids.put(ciudad, i);
			i++;
		}
		for (int j = 0; j < lines.length; j++) {
			String[] values = lines[j].split(" ");
			Vuelo vuelo = new Vuelo(aeropuertos[ids.get(values[0])],
					aeropuertos[ids.get(values[1])],
					Integer.valueOf(values[2]), Integer.valueOf(values[3]),
					j + 1);
			aeropuertos[ids.get(values[0])].agregarAvuelosQueSalen(vuelo);
			aeropuertos[ids.get(values[1])].agregarAvuelosQueLlegan(vuelo);
		}
		return aeropuertos;
	}
}
