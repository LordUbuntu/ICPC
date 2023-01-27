#include <iostream>
#include <algorithm>
#include <vector>
#include <set>
#include <string>

using namespace std;

typedef vector<int> VI;
typedef vector<VI> VVI;

typedef vector<bool> VB;
typedef vector<VB> VVB;

typedef set<int> SI;
typedef vector<SI> VSI;
typedef vector<VSI> VVSI;

int flatten(const VI& row) {
  int answer = 0;
  for (int i = 0; i < row.size(); i++) {
    answer <<= 3;
    answer += row[i];
  }
  return answer;
}

struct latin_square {
  VI r, c;
  VVI m;
  
  latin_square() : r(), c() {}
  
  latin_square(const VVI& mat, const VI& rp, const VI& cp) : r(mat.size(), 0),
                                                             c(mat.size(), 0),
                                                             m(mat.size(), VI(mat.size())) {
    int n = mat.size();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        r[i] <<= 3;
        r[i] += mat[rp[i]][cp[j]];
        c[i] <<= 3;
        c[i] += mat[rp[j]][cp[i]];
        m[i][j] = mat[rp[i]][cp[j]];
      }
    }
  }
  
  void print() {
    int n = r.size();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        cout << m[i][j];
      }
      cout << endl;
    }
  }
};

struct lookup_table {
  int n;
  VVSI constraints;
  vector<latin_square> squares;
  
  lookup_table(int n) : n(n), constraints(n+1, VSI(n+1)), squares() {
    VI perm(n);
    for (int i = 0; i < n; i++) perm[i] = i+1;
    do {
      int left = 0, right = 0;
      int lmax = 0, rmax = 0;
      for (int i = 0; i < n; i++) {
        if (perm[i] > lmax) {lmax = perm[i]; left++;}
        if (perm[n-i-1] > rmax) {rmax = perm[n-i-1]; right++;}
      }
      int flat = flatten(perm);
      constraints[0][0].insert(flat);
      constraints[left][0].insert(flat);
      constraints[0][right].insert(flat);
    } while (next_permutation(perm.begin(), perm.end()));
    
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        VI intersect(120);
        VI::iterator end = set_intersection(constraints[i][0].begin(), constraints[i][0].end(),
                                           constraints[0][j].begin(), constraints[0][j].end(),
                                           intersect.begin());
        for (VI::iterator it = intersect.begin(); it != end; ++it) constraints[i][j].insert(*it);
      }
    }
    
    VVI mat(n, VI(n));
    for (int i = 0; i < n; i++) mat[i][0] = mat[0][i] = i+1;
    VVB seen(n, VB(n+1));
    for (int i = 1; i < n; i++) seen[i][i+1] = true;
    recFill(mat, seen, 1);
  }
  
  void recFill(VVI& mat, VVB& seen, int r) {
    if (r == n) addAll(mat);
    else {
      for (int i = 1; i < n; i++) mat[r][i] = (i <= r ? i : i+1);
      do {
        bool ok = true;
        for (int i = 1; i < n; i++) if (seen[i][mat[r][i]]) ok = false;
        if (ok) {
          for (int i = 1; i < n; i++) seen[i][mat[r][i]] = true;
          recFill(mat, seen, r+1);
          for (int i = 1; i < n; i++) seen[i][mat[r][i]] = false;
        }
      } while (next_permutation(++mat[r].begin(), mat[r].end()));
    }
  }
  
  void addAll(const VVI& mat) {
    VI rp(n), cp(n);
    for (int i = 0; i < n; i++) rp[i] = cp[i] = i;
    do {
      do {
        squares.push_back(latin_square(mat, rp, cp));
      } while (next_permutation(++cp.begin(), cp.end()));
    } while (next_permutation(rp.begin(), rp.end()));
  }
  
  bool find(const VVI& mat, const VI& l, const VI& r, const VI& u, const VI& d, latin_square& ans) {
    for (int i = 0; i < squares.size(); i++) {
      bool ok = true;
      for (int j = 0; j < n; j++) {
        if (constraints[l[j]][r[j]].find(squares[i].r[j]) == constraints[l[j]][r[j]].end()) {
          ok = false;
          break;
        }
        if (constraints[u[j]][d[j]].find(squares[i].c[j]) == constraints[u[j]][d[j]].end()) {
          ok = false;
          break;
        }
      }
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          if (mat[j][k] > 0 && mat[j][k] != squares[i].m[j][k]) {
            ok = false;
            break;
          }
        }
        if (!ok) break;
      }
      if (ok) {
        ans = squares[i];
        return true;
      }
    }
    
    return false;
  }
};

int read(char c) {return c == '-' ? 0 : c - '0';}

int main() {
  vector<lookup_table> tables;
  for (int i = 1; i <= 5; i++) tables.push_back(lookup_table(i));
  int kases ;
  cin >> kases ;
  string line;
  for (int kase=1; kase<=kases; kase++) {
    int n ;
    cin >> n ;
    cin >> line;
    VI l(n), r(n), u(n), d(n);
    VVI mat(n, VI(n));
    for (int i = 0; i < n; i++) u[i] = read(line[i+1]);
    for (int i = 0; i < n; i++) {
      cin >> line;
      l[i] = read(line[0]);
      r[i] = read(line[n+1]);
      for (int j = 0; j < n; j++) mat[i][j] = read(line[j+1]);
    }
    cin >> line;
    for (int i = 0; i < n; i++) d[i] = read(line[i+1]);
    latin_square s;
    if (tables[n-1].find(mat, l, r, u, d, s)) {
      s.print();
    } else {
      cout << "no" << endl;
    }
    cout << endl;
  }
}
