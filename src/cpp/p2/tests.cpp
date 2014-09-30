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

 for (int i = 0; i < instances; i++) {
   std::chrono::high_resolution_clock::time_point t1 = std::chrono::high_resolution_clock::now();

   int n, k;
   ifs >> n >> k;
   Board board(n, k);
   int r, c;
   for (int i = 0; i < k; i++) {
     ifs >> r >> c;
     bfs(i, Square(r, c), board);
   }

   vector<int> result = board.get_min_sum();

   std::chrono::high_resolution_clock::time_point t2 = std::chrono::high_resolution_clock::now();
   //std::chrono::duration<std::chrono::microseconds> time_span = std::chrono::duration_cast<std::chrono::microseconds>(t2 - t1);

   if (test_n) {
     //ofs << n << ' ' << time_span.count() << endl;
     ofs << n << ' ' << std::chrono::duration_cast<std::chrono::microseconds>(t2 - t1).count() << endl;
   } else {
     ofs << k << ' ' << std::chrono::duration_cast<std::chrono::microseconds>(t2 - t1).count() << endl;
     //ofs << k << ' ' << time_span.count() << endl;
   }
 }

 return 0;
}



