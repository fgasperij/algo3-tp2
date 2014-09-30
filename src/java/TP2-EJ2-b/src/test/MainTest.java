package test;

public class MainTest {

	public static void main(String[] args) {
		// test constructor de aeropuertos

		TestDeConstructorDeAeropuertos testDeConstructorDeAeropuertos = new TestDeConstructorDeAeropuertos();
		testDeConstructorDeAeropuertos.testSinVuelos();
		testDeConstructorDeAeropuertos.testSinMasCiudades();
		// test de caso borde
		TestDeCasoBorde testDeCasoBorde = new TestDeCasoBorde();
		testDeCasoBorde.testUnVuelo();
		testDeCasoBorde.testSinVuelos();
		testDeCasoBorde.test2HorasDeDiferenecia();
		testDeCasoBorde.testCiclosALaMismaCiudad();
		testDeCasoBorde.testLlegoPorUnCaminoAntesQueDerecho();
		testDeCasoBorde.testCiclos();
		// test de casos generados
		TestGeneradorDeCasos testGeneradorDeCasos = new TestGeneradorDeCasos();
		testGeneradorDeCasos.testMuchosVuelos();
		testGeneradorDeCasos.testUnVuelo();
		// Test de peor caso
		// TestDePeorCaso testDePeorCaso = new TestDePeorCaso();
		// testDePeorCaso.todosLosVuelosFallanEnLaSegundaCiudad();
		// testDePeorCaso.test2Ciudades();
		System.out.println("test finalizaron correctamente!");
	}
}
