
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
        board.set_distance(neighbour, horse, node_distance+1); 
        nodes_left.push(neighbour);
      }
    }
  }
}
