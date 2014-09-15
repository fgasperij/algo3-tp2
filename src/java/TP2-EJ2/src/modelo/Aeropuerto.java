package modelo;

import java.util.ArrayList;
import java.util.List;

public class Aeropuerto {

	private int id;

	public Aeropuerto(String ciudad, int i) {
		this.ciudad = ciudad;
		this.id = i;
	}

	private String ciudad;

	private List<Vuelo> vuelosQueLlegan = new ArrayList<Vuelo>();

	private List<Vuelo> vuelosQueSalen = new ArrayList<Vuelo>();

	public String ciudad() {
		return ciudad;
	}

	public List<Vuelo> vuelosQueSalen() {
		return vuelosQueSalen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aeropuerto other = (Aeropuerto) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public List<Vuelo> vuelosQueLlegan() {
		return vuelosQueLlegan;
	}

	public void agregarAvuelosQueSalen(Vuelo vuelo) {
		vuelosQueSalen.add(vuelo);
	}

	public void agregarAvuelosQueLlegan(Vuelo vuelo) {
		vuelosQueLlegan.add(vuelo);
	}

}
