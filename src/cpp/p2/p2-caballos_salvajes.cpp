#include <iostream>
#include <queue>
#include <vector>
#include <limits>
#include "Board.h"
#include "bfs_horse.h"

#define NOT_VISITED -1

using namespace std;

int main() {
 int n, k;

 cin >> n >> k;
 Board board(n, k);
 int r, c;
 for (int i = 0; i < k; i++) {
   cin >> r >> c;
   bfs(i, Square(r, c), board);
 }

 vector<int> result = board.get_min_sum();

 cout << result[0] << ' ' << result[1] << ' ' << result[2] << endl;

 return 0;
}



