#include <iostream>
#include <list>
#include <vector>
#include <set>

using namespace std;

typedef int Vertice;

class Arista {
    private:
        Vertice v1;
        Vertice v2;
        int l;
    public:
        Arista() : v1(-1), v2(-1), l(-1){}
        Arista(const Vertice & v1, const Vertice & v2, const int & l) : v1(v1), v2(v2), l(l){}
        int costo() const {
            return l;
        }
        bool incideEn(Vertice v) const {
            return (v == v1 || v == v2) ? true : false;
        }
        pair<bool,Vertice> incideEnDosVertices(const set<Vertice> & conjVertices) const {
            pair<bool,Vertice> res;
            int incideEnV1 = conjVertices.count(v1);            // set::count() es O(log |conj|)
            int incideEnV2 = conjVertices.count(v2);
            if (incideEnV1 + incideEnV2 == 2) {
                res.first = true;
            } else {
                res.first = false;
                res.second = (incideEnV1 == 0) ? v1 : v2;
            }
            return res;
        }
        pair<Vertice,Vertice> dameVertices() const {
            return pair<Vertice,Vertice>(v1,v2);
        }
        Vertice dameVerticeUno() const {
            return v1;
        }
        Vertice dameVerticeDos() const {
            return v2;
        }
        Vertice dameElOtroVertice(Vertice v) const {            // Requiere que (v == v1) ó (v == v2)
            return (v == v1) ? v2 : v1;
        }
};

struct comparacionArista {
    bool operator() (const Arista & lhs, const Arista & rhs) const {
        // Quiero que:
        // Si tienen = costo, ordene por costo
        // Si tienen != costo, ordene por vértices (excepto el caso particular en que sean e = (a,b,c), e' = (b,a,c); entonces e = e')
        if (lhs.costo() == rhs.costo()) { // (x1,y1,c), (x2,y2,c)
            if( lhs.dameVerticeUno() == rhs.dameVerticeUno() ) { // (a,y1,c), (a,y2,c)
                return lhs.dameVerticeDos() < rhs.dameVerticeDos();
            } else { // (a,y1,c), (b,y2,c) con a != b
                if (lhs.dameVerticeUno() == rhs.dameVerticeDos() && lhs.dameVerticeDos() == rhs.dameVerticeUno()) {
                    return false; // (a,b,c) y (b,a,c) son la misma arista
                } else {
                    return lhs.dameVerticeUno() < rhs.dameVerticeUno();
                }
            }
        } else {
            return lhs.costo() < rhs.costo();
        }
    }
};

void hallarCircuito(vector< list<Arista> > & aristasDeCadaVerticeAGM, bool * verticeVisitado, Vertice * verticeAnterior, Vertice actual, Vertice anterior, Arista * aristaAnterior);

int main(int argc, const char* argv[]) {
    unsigned int n, m, costoTotal = 0;                          // n = #vertices, m = #aristas
    vector< list<Arista> > aristasDeCadaVertice;                // aristasDeCadaVertice[i] es la lista de las aristas del vértice i
    vector< list<Arista> > aristasDeCadaVerticeAGM;
    set<Arista, comparacionArista> aristasGrafo;
    set<Vertice> verticesAGM;                                   // conjunto con los vertices ya puestos en el AGM parcial
    set<Arista, comparacionArista> aristasAGM;                  // conjunto con las aristas ya puestas en el AGM parcial
    set<Arista, comparacionArista> aristasCandidatasAGM;        // conjunto de las aristas candidatas para el AGM en un momento dado
    list<Arista> aristasAnillo;
    
    cin >> n >> m;
    
    if (m < n) {                                                // Si hay menos de n aristas, no existe solución (ver Correctitud)
        cout << "no" << endl;
        return 0;
    }
    
    for (unsigned int i = 0; i < n; i++) {
        aristasDeCadaVertice.push_back(list<Arista>());
        aristasDeCadaVerticeAGM.push_back(list<Arista>());
    }
    
    for (unsigned int i = 0; i < m; i++) {
        Vertice v1, v2;
        int l;
        cin >> v1 >> v2 >> l;
        v1--; v2--; // Como los equipos van de 1 a n, resto uno para que v1 y v2 vayan de 0 a n-1. Al devolver la solución sumo 1 y listo
        Arista a(v1, v2, l);
        aristasDeCadaVertice[v1].push_back(a);
        aristasDeCadaVertice[v2].push_back(a);
        aristasGrafo.insert(a); // costo total O(m log m) que en el peor caso es O(n²log n²) = O(n² log n) porque log(n²) = 2 log(n)
    }
    
    // Arranco poniendo el vértice 0 en verticesAGM, y sus aristas en aristasCandidatasAGM
    verticesAGM.insert(0);
    for(auto it = aristasDeCadaVertice[0].begin(); it != aristasDeCadaVertice[0].end(); it++) {
        aristasCandidatasAGM.insert(*it);
    }
    
    // El costo total de hacer este ciclo es O(n² log n) porque se consideran todas las aristas, entonces insertarlas todas en el conjunto aristasCandidatasAGM cuesta c*(1+2+...+m) = O(log m!) = O(m log m) = O(n² log n²) = O(n² log n) en el peor caso (m = n(n-1) = O(n²)). Sacando estas inserciones, para cada arista se hacen O(log n) operaciones, entonces el costo total de esto es O(m log n) = O(n² log n) en el peor caso.
    while (aristasAGM.size() < n - 1 && aristasCandidatasAGM.size() > 0 ) { // set::size() es O(1)
        auto iterAristaMinima = aristasCandidatasAGM.begin();
        Arista a = *iterAristaMinima;
        aristasCandidatasAGM.erase(iterAristaMinima);           // Saco la arista del pool de candidatas, costo amortizado constante
        pair<bool,Vertice> infoIncidencia = a.incideEnDosVertices(verticesAGM); // costo O(log |V(T)|)
        if (infoIncidencia.first) {                             // Si incide en 2 vertices, no la puedo usar
            continue;
        } else {
            Vertice nuevo = infoIncidencia.second;              // Este es el vértice en el que no incide, no está en AGM
            verticesAGM.insert(nuevo);                          // costo O(log |V(T)|)
            aristasGrafo.erase(a);                              // Saco del grafo la arista que voy a poner en el AGM, costo amort. cte.
            aristasAGM.insert(a);                               // costo O(log |V(T)|) porque un árbol tiene m = n - 1
            Vertice otro = a.dameElOtroVertice(nuevo);
            aristasDeCadaVerticeAGM[nuevo].push_back(a);        // costo O(1)
            aristasDeCadaVerticeAGM[otro].push_back(a);
            for(auto it = aristasDeCadaVertice[nuevo].begin(); it != aristasDeCadaVertice[nuevo].end(); it++) {
                if (it->incideEnDosVertices(verticesAGM).first) {
                    continue;                                   // Si estoy acá, iba a agregar una arista repetida o ya considerada
                }
                aristasCandidatasAGM.insert(*it);
            }
        }
    }
    
    if (aristasAGM.size() < n - 1) {                            // Si el árbol no tiene n - 1 aristas, no es generador (el grafo original no era conexo)
        cout << "no" << endl;
        return 0;
    }
    
    // Ahora agrego la arista con menor peso:
    if (aristasGrafo.size() == 0) {                             // En aristasGrafo quedaron las aristas que no puse en el AGM
        cout << "no" << endl;
        return 0;
    }
    Arista menor = *aristasGrafo.begin();
    aristasAGM.insert(menor);
    for (auto it = aristasAGM.begin(); it != aristasAGM.end(); it++) {
        costoTotal += it->costo();
    }
    Vertice primero = menor.dameVerticeUno();
    Vertice segundo = menor.dameVerticeDos();
    aristasDeCadaVerticeAGM[primero].push_back(menor);
    aristasDeCadaVerticeAGM[segundo].push_back(menor);
    // Tengo que encontrar el circuito simple, esto es, el anillo
    Arista aristaAnterior[n];
    bool verticeVisitado[n]; for (unsigned int i = 0; i < n; i++) verticeVisitado[i] = false;
    Vertice verticeAnterior[n]; for (unsigned int i = 0; i < n; i++) verticeAnterior[i] = -1;
    verticeVisitado[primero] = true;
    aristaAnterior[segundo] = menor;
    
    hallarCircuito(aristasDeCadaVerticeAGM, verticeVisitado, verticeAnterior, segundo, primero, aristaAnterior); // costo O(m) = O(n) porque el grafo solución tiene n aristas (el AGM tiene m = n - 1, +1 arista agregada).
    
    Vertice actual = primero;
    do {
        aristasAGM.erase(aristaAnterior[actual]);               // En aristasGM van a quedar las aristas fuera del circuito
        aristasAnillo.push_back(aristaAnterior[actual]);        // Lo contrario para aristasAnillo
        actual = verticeAnterior[actual];
    } while(actual != primero);
    
    cout << costoTotal << " " << aristasAnillo.size() << " " << aristasAGM.size() << endl;
    for (auto it = aristasAnillo.begin(); it != aristasAnillo.end(); it++) {
        cout << it->dameVerticeUno() + 1 << " " << it->dameVerticeDos() + 1 << endl;
    }
    for (auto it = aristasAGM.begin(); it != aristasAGM.end(); it++) {
        cout << it->dameVerticeUno() + 1 << " " << it->dameVerticeDos() + 1 << endl;
    }
    
    return 0;
}


// A --> B
// 1     1      anterior de B = A
//   <--      ¿A visitado? SI. ¿Vengo de B (esto es, el circuito NO es simple)? SI. Venir de B = verticeAnterior[B] = A

void hallarCircuito(vector< list<Arista> > & aristasDeCadaVerticeAGM, bool * verticeVisitado, Vertice * verticeAnterior, Vertice actual, Vertice anterior, Arista * aristaAnterior) {
    if (verticeVisitado[actual]) {                      // El vertice actual ya fue visitado antes
        if (verticeAnterior[anterior] != actual) {      // Si es FALSE significa que estoy haciendo un circuito NO simple
            verticeAnterior[actual] = anterior;
        }
    } else {                                            // El vertice no fue visitado, tengo que seguir
        verticeVisitado[actual] = true;
        verticeAnterior[actual] = anterior;
        for (auto it = aristasDeCadaVerticeAGM[actual].begin(); it != aristasDeCadaVerticeAGM[actual].end(); it++) {
            Vertice nuevoActual = it->dameElOtroVertice(actual);
            Vertice nuevoAnterior = actual;
            if (nuevoActual == anterior) continue;
            aristaAnterior[nuevoActual] = *it;
            hallarCircuito(aristasDeCadaVerticeAGM, verticeVisitado, verticeAnterior, nuevoActual, nuevoAnterior, aristaAnterior);
        }
    }
}
