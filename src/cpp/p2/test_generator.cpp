#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <time.h>
#include <string>

int main() {
  // test configuration
  const int instances = 200;
  const int start = 4;
  const bool vary_n = false;
  const int constant_horses = 5;
  const int constant_n = 100;
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

  for (int i = 2; i < instances; i++) {
    if (vary_n) {
      for (int l = 0; l < 3; l++) {
        for (int m = 0; m < 10; m++) {
          ofs << i << ' ' << constant_horses+5*l << std::endl;
          for (int j = 0; j < constant_horses+5*l; j++) {
            ofs << rand() % i << ' ' << rand() % i << std::endl;
          }
        }
      }
    } else {
      for (int l = 0; l < 3; l++) {
        for (int m = 0; m < 10; m++) {
          ofs << 40+l*30 << ' ' << i << std::endl;
          for (int j = 0; j < i; j++) {
            ofs << rand() % 40+l*30 << ' ' << rand() % 40+l*30 << std::endl;
          }
        }
      }
    }
  }

  ofs.close();

  return 0;
}
