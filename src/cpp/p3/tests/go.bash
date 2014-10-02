#!/bin/bash

make
#./randomGen > instancias.txt
cat instancias.txt | ./p3_test_complejidad > tiemposPorCantidadDeVerticesParaGrafosCompletos.txt
