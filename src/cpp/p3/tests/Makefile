CXXFLAGS = -std=c++0x -Wall
CC = g++

all: p3_test_complejidad randomGen randomGenCompleto

randomGen: randomGen.o
	$(CC) $(CXXFLAGS) -O3 randomGen.o -o randomGen

randomGen.o: randomGen.cpp
	$(CC) -c $(CXXFLAGS) -O3 randomGen.cpp
	
randomGenCompleto: randomGenCompleto.o
	$(CC) $(CXXFLAGS) -O3 randomGenCompleto.o -o randomGenCompleto

randomGenCompleto.o: randomGenCompleto.cpp
	$(CC) -c $(CXXFLAGS) -O3 randomGenCompleto.cpp

p3_test_complejidad: p3_test_complejidad.o
	$(CC) $(CXXFLAGS) p3_test_complejidad.o -o p3_test_complejidad

p3_test_complejidad.o: p3_test_complejidad.cpp
	$(CC) -c $(CXXFLAGS) p3_test_complejidad.cpp

clean:
		rm -rf *o p3_test_complejidad randomGen randomGenCompleto
