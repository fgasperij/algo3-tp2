CXXFLAGS = -std=c++0x -Wall
CC = g++

all: p3
	
p3: p3.o
	$(CC) $(CXXFLAGS) p3.o -o p3

p3.o: p3.cpp
	$(CC) -c $(CXXFLAGS) p3.cpp

clean:
		rm -rf *o p3
