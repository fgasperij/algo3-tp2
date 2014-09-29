package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Aeropuerto {

	private int id;

	public Aeropuerto(String ciudad, int i) {
		this.ciudad = ciudad;
		this.id = i;
	}

	private int ultimoVueloQueLlega = -1;

	public int obtenerUltimoVueloQueLlega() {
		return ultimoVueloQueLlega;
	}

	public void cambiarUltimoVueloQueLlega(Vuelo ultimoVueloQueLlega) {
		this.ultimoVueloQueLlega = ultimoVueloQueLlega.llegada();
	}

	private Vuelo primero_en_llegar;

	private String ciudad;

	private List<Vuelo> vuelosQueLlegaronAdestino = new LinkedList<Vuelo>();

	public void agregarAvuelosQueLleganAdestino(Vuelo vuelo) {
		vuelosQueLlegaronAdestino.add(vuelo);
	}

	private List<Vuelo> vuelosQueLlegan = new LinkedList<Vuelo>();

	private List<Vuelo> vuelosQueSalen = new LinkedList<Vuelo>();

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

	public Vuelo primeroEnLlegar() {
		return primero_en_llegar;
	}

	public void agregarPrimeroEnLlegar(Vuelo vuelo) {
		primero_en_llegar = vuelo;
	}

	public void vaciarVuelosQueSalen() {
		vuelosQueSalen = new ArrayList<Vuelo>();
	}

	public void vaciarVuelosQueLlegan() {
		vuelosQueLlegan = new ArrayList<Vuelo>();
	}

	boolean yaLoCalcule = false;

	public boolean yaLoCalcule() {
		return yaLoCalcule;
	}

	public void loCalcule() {
		yaLoCalcule = true;
	}

	public void asignarVuelosQueLlegan(List<Vuelo> vuelosNoAnalizados) {
		vuelosQueSalen = vuelosNoAnalizados;
	}

}