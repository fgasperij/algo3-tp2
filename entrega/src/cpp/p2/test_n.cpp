#include <iostream>
#include <queue>
#include <vector>
#include <limits>
#include <string>
#include <fstream>
#include <chrono>
#include "Board.h"
#include "bfs_horse.h"

#define NOT_VISITED -1

int main() {
 // configuration
 const bool test_n = true;

 std::string in_file;
 std::string out_file;
 if (test_n) {
   in_file = "tests_n.in";
   out_file = "tests_n.out";
 } else {
   in_file = "tests_k.in";
   out_file = "tests_k.out";
 }

 std::ifstream ifs;
 ifs.open(in_file.c_str(), std::ifstream::in);
 std::ofstream ofs;
 ofs.open(out_file.c_str(), std::ofstream::out);

 int instances;

 ifs >> instances;

 for (int i = 4; i < 100; i++) {
   std::cout << "INSTANCE " << i << endl;
   vector<int> acum_tiempos (3, 0);
   for (int p = 0; p < 3; p++) {
     for (int m = 0; m < 10; m++){
       int n, k;
       ifs >> n >> k;

       vector<vector<int> > horses (k, vector<int>(2, -1));
       for (int h = 0; h < k; h++) {
         ifs >> horses[h][0] >> horses[h][1];
       }

       long long int minimum = std::chrono::microseconds::max().count();
       for (int j = 0; j < 10; j++) {
         std::chrono::high_resolution_clock::time_point t1 = std::chrono::high_resolution_clock::now();

         Board board(n, k);
         
         for (int l = 0; l < k; l++) {
           bfs(l, Square(horses[l][0], horses[l][1]), board);
         }
         

         vector<int> result = board.get_min_sum();

         std::chrono::high_resolution_clock::time_point t2 = std::chrono::high_resolution_clock::now();

         long long int current = std::chrono::duration_cast<std::chrono::microseconds>(t2 - t1).count();
         if (current < minimum) {
           minimum = current;
         }
       }

       acum_tiempos[p] += minimum;
     }
   }
   ofs << i << ' ' << acum_tiempos[0]/(10*i) << ' ' << acum_tiempos[1]/(10*i) << ' ' << acum_tiempos[2]/(10*i) << endl;
 }

 return 0;
}



