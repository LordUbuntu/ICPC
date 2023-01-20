#include <iostream>
#include <iomanip>
#include <vector>
#include <cmath>
#include <limits>
#include <algorithm>
#include <string>

using namespace std;
typedef long double DOUBLE;
typedef vector<DOUBLE> VD;
typedef vector<VD> VVD;
typedef vector<int> VI;
typedef vector<string> VS;
const DOUBLE EPS = 1e-9;

struct LPSolver {
  int m, n;
  VI B, N;
  VVD D;

  LPSolver(const VVD &A, const VD &b, const VD &c) :
    m(b.size()), n(c.size()), N(n + 1), B(m), D(m + 2, VD(n + 2)) {
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) D[i][j] = A[i][j];
    for (int i = 0; i < m; i++) { B[i] = n + i; D[i][n] = -1; D[i][n + 1] = b[i]; }
    for (int j = 0; j < n; j++) { N[j] = j; D[m][j] = -c[j]; }
    N[n] = -1; D[m + 1][n] = 1;
  }

  void Pivot(int r, int s) {
    for (int i = 0; i < m + 2; i++) if (i != r)
      for (int j = 0; j < n + 2; j++) if (j != s)
        D[i][j] -= D[r][j] * D[i][s] / D[r][s];
    for (int j = 0; j < n + 2; j++) if (j != s) D[r][j] /= D[r][s];
    for (int i = 0; i < m + 2; i++) if (i != r) D[i][s] /= -D[r][s];
    D[r][s] = 1.0 / D[r][s];
    swap(B[r], N[s]);
  }

  bool Simplex(int phase) {
    int x = phase == 1 ? m + 1 : m;
    while (true) {
      int s = -1;
      for (int j = 0; j <= n; j++) {
        if (phase == 2 && N[j] == -1) continue;
        if (s == -1 || D[x][j] < D[x][s] || (D[x][j] == D[x][s] && N[j] < N[s])) s = j;
      }
      if (D[x][s] > -EPS) return true;
      int r = -1;
      for (int i = 0; i < m; i++) {
        if (D[i][s] < EPS) continue;
        if (r == -1 || D[i][n + 1] / D[i][s] < D[r][n + 1] / D[r][s] ||
          ((D[i][n + 1] / D[i][s]) == (D[r][n + 1] / D[r][s]) && B[i] < B[r])) r = i;
      }
      if (r == -1) return false;
      Pivot(r, s);
    }
  }

  DOUBLE Solve(VD &x) {
    int r = 0;
    for (int i = 1; i < m; i++) if (D[i][n + 1] < D[r][n + 1]) r = i;
    if (D[r][n + 1] < -EPS) {
      Pivot(r, n);
      if (!Simplex(1) || D[m + 1][n + 1] < -EPS) return -numeric_limits<DOUBLE>::infinity();
      for (int i = 0; i < m; i++) if (B[i] == -1) {
        int s = -1;
        for (int j = 0; j <= n; j++)
          if (s == -1 || D[i][j] < D[i][s] || (D[i][j] == D[i][s] && N[j] < N[s])) s = j;
        Pivot(i, s);
      }
    }
    if (!Simplex(2)) return numeric_limits<DOUBLE>::infinity();
    x = VD(n);
    for (int i = 0; i < m; i++) if (B[i] < n) x[B[i]] = D[i][n + 1];
    return D[m][n + 1];
  }
};

VI fact {1, 1, 2, 6, 24, 120, 720};

VS perms;

void perms_gen(int n) {
  string perm = "";
  for (int i = 0; i < n; i++) perm += 'A' + i;
  do {
    perms.push_back(perm);
  } while (next_permutation(perm.begin(), perm.end()));
}

VVD adj;

void adj_gen(int n) {
  for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
      adj.push_back(VD());
      for (int k = 0; k < perms.size(); k++) {
        if (perms[k].find('A' + i) < perms[k].find('A' + j)) {
          adj.back().push_back(1);
        } else {
          adj.back().push_back(-1);
        }
      }
    }
  }
}

VVD graph;
VD parents;
void rec_graph_gen(int n, int cur) {
  if (cur == n) {
    VD subgraph;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (parents[i] == j) {
          subgraph.push_back(1);
        } else if (parents[j] == i) {
          subgraph.push_back(-1);
        } else {
          subgraph.push_back(0);
        }
      }
    }
    graph.push_back(subgraph);
  } else {
    for (int i = 0; i < n; i++) {
      if (i == cur) continue;
      if (i < cur && parents[i] == cur) continue;
      parents.push_back(i);
      rec_graph_gen(n, cur + 1);
      parents.pop_back();
    }
  }
}

void graph_gen(int n) {
  rec_graph_gen(n, 0);
}

int main() {
  int N, K;

  cin >> N >> K;

  perms_gen(N);
  adj_gen(N);
  graph_gen(N);

  int dim = fact[N];

  VD in(dim, 0), x(dim), C(dim, -1);
  string ballot;
  int count;
  for (size_t i = 0; i < K; i++) {
    cin >> ballot >> count;
    auto it = find(perms.begin(), perms.end(), ballot);
    in[it - perms.begin()] += count;
  }
  DOUBLE best = 999999999;
  for (size_t probe = 0; probe <= dim; probe++) {
    if (probe < dim) in[probe]++;
    for (size_t i = 0; i < graph.size(); i++) {
      VVD A(N);
      VD B(N, -1);
      int cur = 0;
      for (size_t j = 0; j < graph[i].size(); j++) {
        if (graph[i][j] != 0) {
          A[cur] = adj[j];
          for (size_t k = 0; k < dim; k++) {
            A[cur][k] *= graph[i][j];
            B[cur] -= A[cur][k] * in[k];
          }
          cur++;
        }
      }

      LPSolver lp(A, B, C);
      DOUBLE ans = -lp.Solve(x);
      if (probe < dim) ans++;
      bool integral = true;
      for (size_t j = 0; j < dim; j++) {
        if (abs(round(x[j]) - x[j]) > 1e-2) integral = false;
      }
      if (integral) best = min(best, ans);
    }
    if (probe < dim) in[probe]--;
  }
  cout << lround(best) << endl;
}
