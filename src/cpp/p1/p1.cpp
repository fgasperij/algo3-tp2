#include <iostream>
#include <map>
#include <list>
#include <climits>

using namespace std;

struct Vuelo {
    int ID;
    string origen;
    string destino;
    int horaDespegue;
    int horaAterrizaje;
    list<Vuelo> * vuelosDeCiudadDestino;
    
    Vuelo(int ID, string origen, string destino, int horaDespegue, int horaAterrizaje, list<Vuelo> * vuelosDeCiudadDestino) : ID(ID), origen(origen), destino(destino), horaDespegue(horaDespegue), horaAterrizaje(horaAterrizaje), vuelosDeCiudadDestino(vuelosDeCiudadDestino) {}
};

string ciudadA;
string ciudadB;
int cantidadVuelos;
int * tablaHoraOptima = NULL; // Dice para el ID de cada vuelo, la hora óptima de llegada a la ciudad B: -1 = no lo calculé, INT_MAX = +infinito (es decir, no hay itinerario que llegue a la ciudad B)
int * tablaProximoVueloOptimo = NULL; // Dice para el ID de cada vuelo, cuál sería el próximo vuelo óptimo a tomar para llegar lo antes posible a la ciudad B. Si para el vuelo v, tablaHoraOptima[v.ID] != -1, entonces -1 significa que no hay itinerario usando este vuelo que llegue a ciudad B, -2 significa que este vuelo tiene como destino a ciudad B, y cualquier valor entre 0 y n-1 es el vuelo óptimo para llegar lo antes posible a ciudad B. Si tablaHoraOptima[v.ID] = -1, el vuelo óptimo no está definido.
map< string, list<Vuelo> > vuelosSalientesPorCiudad; // Crear este diccionario lleva en el peor caso O(n*log(n)) + O(m). Como en el peor caso, hay el doble de ciudades que de vuelos, esto es O(m*log(m)) + O(m) = O(m*log(m)) que no es peor que O(m²)

// Esta función devuelve la hora más temprana a la que puedo llegar a la ciudad B tomándome el vuelo "v", o INT_MAX si no es posible llegar.
int mejorHoraAterrizajeACiudadBSiMeTomoElVuelo(const Vuelo & v) {
    // Si ya lo había calculado, listo
    if (tablaHoraOptima[v.ID] != -1) {
        return tablaHoraOptima[v.ID];
    }
    if (v.destino == ciudadB) {
        tablaProximoVueloOptimo[v.ID] = -2; // -2 significa que llegué a ciudadB
        tablaHoraOptima[v.ID] = v.horaAterrizaje;
    } else {
        int horaOptimaAterrizajeEnCiudadB = INT_MAX; // INT_MAX representa +infinito, es decir no existe itinerario que llegue a B
        int vueloOptimoParaLlegarACiudadB = -1; // -1 significa que no existe vuelo tal que eventualmente se llegue a B
        for (auto it = v.vuelosDeCiudadDestino->begin(); it != v.vuelosDeCiudadDestino->end(); it++) {
            // Primero tengo que chequear si el vuelo me lo puedo tomar, esto es, si estoy al menos dos horas antes
            if (v.horaAterrizaje <= it->horaDespegue - 2) {
                if (mejorHoraAterrizajeACiudadBSiMeTomoElVuelo(*it) < horaOptimaAterrizajeEnCiudadB) {
                    // Este vuelo llega antes que los que había calculado hasta ahora
                    horaOptimaAterrizajeEnCiudadB = tablaHoraOptima[it->ID];
                    vueloOptimoParaLlegarACiudadB = it->ID;
                }
            }
        }
        // En vueloOptimoParaLlegarACiudadB tengo el vuelo óptimo de todos los vuelos que me podía tomar para llegar a B, si existe
        tablaProximoVueloOptimo[v.ID] = vueloOptimoParaLlegarACiudadB;
        // En horaOptimaAterrizajeEnCiudadB tengo la hora óptima de todos los vuelos que me podía tomar para llegar a B
        tablaHoraOptima[v.ID] = horaOptimaAterrizajeEnCiudadB;
    }
    return tablaHoraOptima[v.ID];
}

/* 
 * En el peor caso, resolver el problema cuesta O(m²). Por ejemplo, si se tiene algo asi: 
 *     ciudad A ---(m/2 vuelos)---> ciudad C ---(m/2 vuelos)---> ciudad B
 * La mitad de los vuelos salen de la ciudad A hacia la ciudad intermedia C, y la otra mitad salen de C y llegan a B.
 * Calcular el mínimo de los últimos m/2 es O(1) para cada uno, entonces esa parte es O(m).
 * El problema es calcular los primeros m/2 vuelos. Como cada cálculo del mínimo recorre los vuelos que salen de la ciudad C, pueda
 * tomarlos o no, el costo de calcular los primeros m/2 vuelos será O(m/2 * m/2) = O(m²/4) = O(m²)
*/
int main(int argc, const char* argv[]) {
    cin >> ciudadA >> ciudadB >> cantidadVuelos;
    tablaHoraOptima = new int[cantidadVuelos];
    tablaProximoVueloOptimo = new int[cantidadVuelos];
    string oriTemp, destTemp;
    int despegueTemp, aterrizajeTemp;
    for (int i = 0; i < cantidadVuelos; i++) {
        cin >> oriTemp >> destTemp >> despegueTemp >> aterrizajeTemp;
        list<Vuelo> * vuelosCiudadDestino = & vuelosSalientesPorCiudad[destTemp]; // Esto inserta la ciudad en caso de que no exista aún
        Vuelo v(i, oriTemp, destTemp, despegueTemp, aterrizajeTemp, vuelosCiudadDestino);
        vuelosSalientesPorCiudad[oriTemp].push_back(v); // Pongo el vuelo en la lista de salientes de su ciudad de origen
        tablaHoraOptima[v.ID] = -1;
    }
    
    int horaOptimaAterrizajeEnCiudadB = INT_MAX;
    int vueloOptimoParaLlegarACiudadB = -1;
    list<Vuelo> * vuelosDeCiudadA = & vuelosSalientesPorCiudad[ciudadA];
    for (auto it = vuelosDeCiudadA->begin(); it != vuelosDeCiudadA->end(); it++) {
        if (mejorHoraAterrizajeACiudadBSiMeTomoElVuelo(*it) < horaOptimaAterrizajeEnCiudadB) {
            horaOptimaAterrizajeEnCiudadB = tablaHoraOptima[it->ID];
            vueloOptimoParaLlegarACiudadB = it->ID;
        }
    }
    
    if (horaOptimaAterrizajeEnCiudadB == INT_MAX) {
        cout << "no" << endl;
    } else {
        int tamItinerario = 0;
        list<int> vuelos;
        int horaLlegada = horaOptimaAterrizajeEnCiudadB;
        int ID_temp = vueloOptimoParaLlegarACiudadB;
        while (ID_temp != -2) {
            tamItinerario++;
            vuelos.push_back(ID_temp + 1);
            ID_temp = tablaProximoVueloOptimo[ID_temp];
        }
        cout << horaLlegada << " " << tamItinerario << " ";
        for (auto it = vuelos.begin(); it != vuelos.end(); it++) {
            cout << *it << " ";
        }
        cout << endl;
    }
    
    delete[] tablaHoraOptima;
    delete[] tablaProximoVueloOptimo;
    return 0;
}
