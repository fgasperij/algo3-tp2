package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import modelo.Aeropuerto;
import modelo.Vuelo;
import servicio.Aterrizar;

public class Main {

	/**
	 * @param args
	 * @throws
	 */
	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.runAlgo("vuelos");
		// runAlgo("vuelos");
	}

	public String runAlgo(String file) throws IOException {
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
			Aeropuerto[] aeropuertos = crearInstancia(lines, origen, destino);
			System.out.println(Aterrizar.mejorVuelo(aeropuertos));
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

	public Aeropuerto[] crearInstancia(String[] lines, String origen, String destino) {
		Set<String> ciudades = new TreeSet<String>();
		ciudades.add(origen);
		ciudades.add(destino);
		for (int i = 0; i < lines.length; i++) {
			String[] values = lines[i].split(" ");
			ciudades.add(values[0]);
			ciudades.add(values[1]);
		}
		Aeropuerto[] aeropuertos = new Aeropuerto[ciudades.size()];
		Map<String, Integer> ids = new TreeMap<String, Integer>();
		aeropuertos[0] = new Aeropuerto(origen, 0);
		aeropuertos[1] = new Aeropuerto(destino, 1);
		ids.put(origen, 0);
		ids.put(destino, 1);
		ciudades.remove(origen);
		ciudades.remove(destino);
		int i = 2;
		for (String ciudad : ciudades) {
			aeropuertos[i] = new Aeropuerto(ciudad, i);
			ids.put(ciudad, i);
			i++;
		}
		for (int j = 0; j < lines.length; j++) {
			String[] values = lines[j].split(" ");
			Vuelo vuelo = new Vuelo(aeropuertos[ids.get(values[0])], aeropuertos[ids.get(values[1])], Integer.valueOf(values[2]),
					Integer.valueOf(values[3]), j + 1);
			aeropuertos[ids.get(values[0])].agregarAvuelosQueSalen(vuelo);
			aeropuertos[ids.get(values[1])].agregarAvuelosQueLlegan(vuelo);
		}
		return aeropuertos;
	}
}
