#include <iostream>
#include <queue>
#include <vector>
#include <limits>

#define NOT_VISITED -1

using namespace std;

struct Square {
  Square() : row(0), column(0) {};
  Square(int r, int c) : row(r), column(c) {};

  int row;
  int column;
};

struct Board {

  vector<vector<int> > sums;
  vector<vector<vector<int> > > squares;
  
  Board(int n, int horses) : 
    squares(vector<vector<vector<int> > > (n, vector<vector<int> > (n, vector<int> (horses, NOT_VISITED) ) ) ), 
        sums(vector<vector<int> > (n, vector<int>(n, 0)))
  {};

  bool is_marked(Square s, int horse)
  {
    if (squares[s.row][s.column][horse] == NOT_VISITED) {
      return false;
    }
    
    return true;
  };

  int get_distance(Square s, int horse) 
  {
    int dist = squares[s.row][s.column][horse];
    return dist;
  };

  void set_distance(Square s, int horse, int d)
  {
    squares[s.row][s.column][horse] = d;
    sums[s.row][s.column] += d;
    mostrar_sums();
  };

  void mostrar_sums()
  {
    cout << "SUMS" << endl;
    for (int i = 0; i < sums.size(); i++) {
     for (int j = 0; j < sums.size(); j++) {
       cout << sums[i][j] << ' ';
     }
     cout << endl << endl;
    }
  };

  vector<int> get_min_sum() 
  {
    int r, c;
    int min = std::numeric_limits<int>::max();
    for (int i = 0; i < sums.size(); i++) {
     for (int j = 0; j < sums.size(); j++) {
       if (min > sums[i][j]) {
         min = sums[i][j];
         r = i;
         c = j;
       }
     }
    }
    
    vector<int> result;
    result.push_back(r);
    result.push_back(c);
    result.push_back(min);

    return result;
  };
};

void bfs(int horse, Square origin, Board &board);

int main() {
 int n, k;

 cin >> n >> k;
 Board board(n, k);
 // la casilla original del caballo k
 int r, c;
 for (int i = 0; i < k; i++) {
   cin >> r >> c;
   bfs(i, Square(r, c), board);
 }

 vector<int> result = board.get_min_sum();

 cout << result[0] << ' ' << result[1] << ' ' << result[2] << endl;

 return 0;
}

int movements[][2] = {
  {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
  {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
};

queue<Square> get_neighbours(Square s, int n)
{
  queue<Square> neighbours;
  int r, c;
  for (int i = 0; i < 8; i++) {
    r = s.row + movements[i][0];
    c = s.column + movements[i][1];
    if (r >= 0 && c >= 0 && r < n && c < n) {
      neighbours.push(Square(r, c));
    }
  }

  return neighbours;
}

void bfs(int horse, Square origin, Board &board)
{
  cout << "BFS: horse: " << horse << " square: (" << origin.row << ", " << origin.column << ")" << endl; 
  queue<Square> nodes_left;
  nodes_left.push(origin);
  board.set_distance(origin, horse, 0);

  queue<Square> neighbours;
  Square node;
  int node_distance;
  while (!nodes_left.empty()) {
    node = nodes_left.front();
    node_distance = board.get_distance(node, horse);
    nodes_left.pop();

    neighbours = get_neighbours(node, board.squares.size());

    while (!neighbours.empty()) {
      Square neighbour = neighbours.front();
      neighbours.pop();
      if (!board.is_marked(neighbour, horse)) {
        cout << "SET DISTANCE: square (" << neighbour.row << ", " << neighbour.column << ") = " << node_distance+1 << endl;
        board.set_distance(neighbour, horse, node_distance+1); 
        nodes_left.push(neighbour);
      }
    }
  }
}


