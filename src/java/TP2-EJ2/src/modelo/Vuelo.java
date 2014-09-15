package modelo;

public class Vuelo {

	private Color color = Color.AMARILLO;

	private Aeropuerto inicio;

	private int id;

	private Aeropuerto destino;

	private int partida;

	private int llegada;

	public Vuelo(Aeropuerto inicio, Aeropuerto destino, int partida,
			int llegada, int id) {
		this.inicio = inicio;
		this.destino = destino;
		this.partida = partida;
		this.llegada = llegada;
		color = Color.AMARILLO;
		this.id = id;
	}

	public Aeropuerto origen() {
		return inicio;
	}

	public Aeropuerto destino() {
		return destino;
	}

	public int partida() {
		return partida;
	}

	public Color color() {
		return color;
	}

	public int llegada() {
		return llegada;
	}

	public void cambiarColor(Color color) {
		this.color = color;
	}

	public int id() {
		return id;
	}

}
