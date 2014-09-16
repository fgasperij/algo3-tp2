CXXFLAGS = -std=c++0x -Wall
CC = g++

all: p1
	
p1: p1.o
	$(CC) $(CXXFLAGS) p1.o -o p1

p1.o: p1.cpp
	$(CC) -c $(CXXFLAGS) p1.cpp

clean:
		rm -rf *o p1
