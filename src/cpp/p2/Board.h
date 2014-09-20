#include <vector>

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
