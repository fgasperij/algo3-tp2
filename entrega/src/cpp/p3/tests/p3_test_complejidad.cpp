#include <chrono>
#include <climits>
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

enum Color {BLANCO, GRIS, NEGRO};

struct infoVerticeDFS {
    Vertice verticeAnterior;
    Arista aristaAnterior;
    Color estado;
    list<Arista> backEdges;
    infoVerticeDFS() : verticeAnterior(-1), aristaAnterior(), estado(BLANCO), backEdges() {}
};

void DFS_visit(vector< list<Arista> > & aristasDeCadaVerticeAGM, infoVerticeDFS * info, Vertice actual) {
    info[actual].estado = GRIS;
    for (auto it = aristasDeCadaVerticeAGM[actual].begin(); it != aristasDeCadaVerticeAGM[actual].end(); it++) {
        Vertice nuevoActual = it->dameElOtroVertice(actual);
        if (info[nuevoActual].estado == BLANCO) {
            info[nuevoActual].aristaAnterior = *it;
            info[nuevoActual].verticeAnterior = actual;
            DFS_visit(aristasDeCadaVerticeAGM, info, nuevoActual);
        } else if (info[nuevoActual].estado == GRIS) {
            if (nuevoActual == info[actual].verticeAnterior) {
                /* Si estoy acá es porque estaba volviendo por la misma arista que vine (formando un circuito NO simple) */
            } else {
                /* El nuevo vertice ya habia sido visitado, entonces hay back edge */
                info[nuevoActual].backEdges.push_back(*it);
            }
        }
    }
    info[actual].estado = NEGRO;
}

chrono::microseconds algoritmo(unsigned int n, unsigned int m, unsigned int costoTotal, vector< list<Arista> > aristasDeCadaVertice, vector< list<Arista> > aristasDeCadaVerticeAGM, set<Arista, comparacionArista> aristasGrafo, set<Vertice> verticesAGM, set<Arista, comparacionArista> aristasAGM, set<Arista, comparacionArista> aristasCandidatasAGM, list<Arista> aristasAnillo) 
{
    auto start_time = chrono::high_resolution_clock::now();
    // Arranco poniendo el vértice 0 en verticesAGM, y sus aristas en aristasCandidatasAGM
    verticesAGM.insert(0);
    for(auto it = aristasDeCadaVertice[0].begin(); it != aristasDeCadaVertice[0].end(); it++) {
        aristasCandidatasAGM.insert(*it);
    }
    
    while (aristasAGM.size() < n - 1 && aristasCandidatasAGM.size() > 0 ) {
        auto iterAristaMinima = aristasCandidatasAGM.begin();
        Arista a = *iterAristaMinima;
        aristasCandidatasAGM.erase(iterAristaMinima);           
        pair<bool,Vertice> infoIncidencia = a.incideEnDosVertices(verticesAGM);
        if (infoIncidencia.first) {                             
            continue;
        } else {
            Vertice nuevo = infoIncidencia.second;              
            verticesAGM.insert(nuevo);                          
            aristasGrafo.erase(a);                              
            aristasAGM.insert(a);                               
            Vertice otro = a.dameElOtroVertice(nuevo);
            aristasDeCadaVerticeAGM[nuevo].push_back(a);        
            aristasDeCadaVerticeAGM[otro].push_back(a);
            for(auto it = aristasDeCadaVertice[nuevo].begin(); it != aristasDeCadaVertice[nuevo].end(); it++) {
                if (it->incideEnDosVertices(verticesAGM).first) {
                    continue;                                   
                }
                aristasCandidatasAGM.insert(*it);
            }
        }
    }
    
    if ( (aristasAGM.size() < n - 1) || (aristasGrafo.size() == 0)) {
        // No hay solucion
    } else {
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
        infoVerticeDFS info[n];
        DFS_visit(aristasDeCadaVerticeAGM, info, primero);
        if (info[primero].backEdges.size() != 1) {
            cout << "Hay algo mal, el vertice deberia tener exactamente un back edge." << endl;
        }
        Arista backEdge = info[primero].backEdges.front();
        aristasAnillo.push_back(backEdge);
        aristasAGM.erase(backEdge);
        Vertice actual = backEdge.dameElOtroVertice(primero);
        while (actual != primero) {
            aristasAGM.erase(info[actual].aristaAnterior);
            aristasAnillo.push_back(info[actual].aristaAnterior);
            actual = info[actual].verticeAnterior;
        }
    }
    
    auto end_time = chrono::high_resolution_clock::now();
    chrono::microseconds tiempo = chrono::duration_cast<chrono::microseconds>(end_time - start_time);
    return tiempo;
}

const int CANT_INSTANCIAS = 1000;
const int MAX_VERTICES = 100;
const int CANT_REPETICIONES_CALCULO_INSTANCIA = 10;

int main(int argc, const char* argv[]) {
    for (int cantVertices = 1; cantVertices <= MAX_VERTICES; cantVertices++) {
        int promedio = 0;
        for (int instancia = 1; instancia <= CANT_INSTANCIAS; instancia++) {
            unsigned int n, m, costoTotal = 0;
            vector< list<Arista> > aristasDeCadaVertice;
            vector< list<Arista> > aristasDeCadaVerticeAGM;
            set<Arista, comparacionArista> aristasGrafo;
            set<Vertice> verticesAGM;
            set<Arista, comparacionArista> aristasAGM;
            set<Arista, comparacionArista> aristasCandidatasAGM;
            list<Arista> aristasAnillo;
            
            cin >> n >> m;
            
            for (unsigned int i = 0; i < n; i++) {
                aristasDeCadaVertice.push_back(list<Arista>());
                aristasDeCadaVerticeAGM.push_back(list<Arista>());
            }
            
            for (unsigned int i = 0; i < m; i++) {
                Vertice v1, v2;
                int l;
                cin >> v1 >> v2 >> l;
                v1--; v2--;
                Arista a(v1, v2, l);
                aristasDeCadaVertice[v1].push_back(a);
                aristasDeCadaVertice[v2].push_back(a);
                aristasGrafo.insert(a);
            }
            
            chrono::microseconds minTiempo(INT_MAX);
            for (int rep = 1; rep <= CANT_REPETICIONES_CALCULO_INSTANCIA; rep++) {
                // Notar que todo se pasa por copia para que no se modifique.
                chrono::microseconds tiempoRep = algoritmo(n, m, costoTotal, aristasDeCadaVertice, aristasDeCadaVerticeAGM, aristasGrafo, verticesAGM,aristasAGM, aristasCandidatasAGM, aristasAnillo);
                if (tiempoRep < minTiempo)
                    minTiempo = tiempoRep;
            }
            
            promedio += minTiempo.count();
        }
        promedio = promedio / CANT_INSTANCIAS;
        cout << cantVertices << " " << promedio << endl;
    }
    
    
    return 0;
}
