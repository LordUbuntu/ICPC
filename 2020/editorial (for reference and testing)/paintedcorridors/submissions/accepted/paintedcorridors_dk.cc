#include <algorithm>
#include <cassert>
#include <cstring>
#include <iostream>
#include <string>
using namespace std;

string colors = "XRBPYOG";

int N;
int C[101][101];
int cseen[101][101];
int seen[101][101][101];

void doit(int r, int b, int y) {
  if (seen[r][b][y]) return;
  seen[r][b][y] = 1;
  for (int x = 1; x <= N; x++) if (C[r][x] == 0 || C[r][x] == 1) { cseen[r][x] = 1; doit(x, b, y); }
  for (int x = 1; x <= N; x++) if (C[b][x] == 0 || C[b][x] == 2) { cseen[b][x] = 1; doit(r, x, y); }
  for (int x = 1; x <= N; x++) if (C[y][x] == 0 || C[y][x] == 4) { cseen[y][x] = 1; doit(r, b, x); }
  if (r == b) for (int x = 1; x <= N; x++) if (C[r][x] == 3) { cseen[r][x] = 1; doit(x, x, y); }
  if (b == y) for (int x = 1; x <= N; x++) if (C[b][x] == 6) { cseen[b][x] = 1; doit(r, x, x); }
  if (y == r) for (int x = 1; x <= N; x++) if (C[y][x] == 5) { cseen[y][x] = 1; doit(x, b, x); }
}

int main() {
  int M, R, B, Y, I, J;
  char COL;
  while (cin >> N >> M >> R >> B >> Y) {
    memset(C, -1, sizeof(C));
    for (int i = 0; i < M; i++) {
      cin >> I >> J >> COL;
      assert(I < J);
      C[I][J] = C[J][I] = colors.find(COL);
    }
    memset(seen, 0, sizeof(seen));
    doit(R, B, Y);
    int ret = 1;
    for (int i = 1; i <= N; i++)
    for (int j = i+1; j <= N; j++) {
      if (C[i][j] > 0 && !cseen[i][j]) ret = 0;
    }
    cout << ret << endl;
  }
}
