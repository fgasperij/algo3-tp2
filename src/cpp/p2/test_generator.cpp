#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <time.h>
#include <string>

int main() {
  // test configuration
  const int instances = 100;
  const int start = 4;
  const bool vary_n = false;
  const int constant_horses = 4;
  const int constant_n = 1000;
  srand(time(NULL));

  std::ofstream ofs;
  std::string test_file;
  if (vary_n) {
    test_file = "tests_n.in";
  } else {
    test_file = "tests_k.in";
  }
  ofs.open(test_file.c_str(), std::ofstream::out);

  ofs << instances << std::endl;

  for (int i = start; i < instances; i++) {
    if (vary_n) {
      ofs << i << ' ' << constant_horses << std::endl;
      for (int j = 0; j < constant_horses; j++) {
        ofs << rand() % i << ' ' << rand() % i << std::endl;
      }
    } else {
      ofs << constant_n << ' ' << i << std::endl;
      for (int j = 0; j < i; j++) {
        ofs << rand() % constant_n << ' ' << rand() % constant_n << std::endl;
      }
    }
  }

  ofs.close();

  return 0;
}
