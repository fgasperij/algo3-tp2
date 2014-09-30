// Este programa va a mostrar por pantalla CANT_INSTANCIAS grafos completos de N vértices, para cada N = 1, ..., MAX_VERTICES.
// Hay que usar ./randomGenCompleto > textFile para guardar a disco.
// g++ -O3 randomGenCompleto.cpp -o randomGenCompleto

#include <iostream>
#include <unistd.h>
#include <cstdlib>

using namespace std;

const int CANT_INSTANCIAS = 100;
const int MAX_VERTICES = 100;
const int MAX_COSTO_ARISTA = 100;

int main(int argc, const char* argv[]) {
    srand(time(NULL) + getpid()); // Seedeo
    for (int n = 1; n <= MAX_VERTICES; n++) {
        for (int i = 1; i <= CANT_INSTANCIAS; i++) {
            int m = n * (n - 1) / 2;
            cout << n << " " << m << endl;
            for (int v = 1; v <= n; v++) {
                for (int a = 1; a <= (n-1) - (v-1); a++) {
                    cout << v << " " << (v + a) << " " << (rand() % 100) + 1 << endl;
                }
            }
        }
    }
}

