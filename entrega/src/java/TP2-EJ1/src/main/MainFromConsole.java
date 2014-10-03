/*
 * Creation : 03/10/2014
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modelo.Aeropuerto;
import servicio.Aterrizar;

public class MainFromConsole extends Main {

	public static void main(String[] args) throws IOException {
		MainFromConsole main = new MainFromConsole();
		main.resolver();
	}

	public void resolver() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Ingresar vuelos:" + "\n");
		String line;
		line = br.readLine();
		String[] values = line.split(" ");
		String origen = values[0];
		String destino = values[1];
		int n = Integer.valueOf(values[2]);
		String[] lines = new String[n];
		for (int j = 0; j < n; j++) {
			lines[j] = br.readLine();
		}
		Aeropuerto[] aeropuertos = this.crearInstancia(lines, origen, destino);
		System.out.println(Aterrizar.mejorVuelo(aeropuertos));
	}

}
