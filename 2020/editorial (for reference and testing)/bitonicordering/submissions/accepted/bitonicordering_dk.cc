#include <algorithm>
#include <functional>
#include <iostream>
#include <vector>
using namespace std;

template<typename T, typename OpType = T(*)(const T&, const T&)> struct SegmentTree {
  T ident;
  OpType op;
  vector<vector<T>> v;
  SegmentTree(vector<T>&& u, const T& ident = T(), OpType op = OpType())
      : ident(ident), op(op), v(1, move(u)) { resize(size()); fixFull(0, size()); }
  explicit SegmentTree(size_t sz, const T& ident = T(), OpType op = OpType())
      : SegmentTree(vector<T>(sz, ident), ident, op) {}

  inline size_t size() const { return v[0].size(); }
  typedef const T& const_reference;
  struct reference {
    SegmentTree* st;
    size_t idx;
    inline reference operator=(const T& val) { st->v[0][idx] = val; st->fix(idx); return *this; }
    inline reference operator=(T&& val) { st->v[0][idx] = move(val); st->fix(idx); return *this; }
    inline operator const_reference() const { return st->v[0][idx]; }
  };
  inline reference operator[](size_t idx) { return {this, idx}; };

  void resize(size_t sz) {
    v[0].resize(sz, ident);
    for (size_t i = 1; sz /= 2, true; i++) {
      if (i >= v.size() || !sz) v.resize(i+1);
      if (sz == v[i].size()) break;
      if (sz > v[i].size()) v[i].push_back(op(v[i-1][v[i].size()*2], v[i-1][v[i].size()*2+1]));  // Slight hack.
      v[i].resize(sz, ident);
    }
  }
  inline void fix(size_t x) {  // Aggregate all parents of x.
    for (size_t i = 1; x /= 2, x < v[i].size(); i++) v[i][x] = op(v[i-1][2*x], v[i-1][2*x+1]);
  }
  inline void fixFull(size_t s, size_t e) {  // Aggregate all nodes touching [s..e).
    for (size_t i = 1; s /= 2, e = min((e+1)/2, v[i].size()), s < e; i++) {
      for (size_t x = s; x < e; x++) v[i][x] = op(v[i-1][2*x], v[i-1][2*x+1]);
    }
  }

  T Calc(size_t s, size_t e) {
    T lv = ident, rv = ident;
    for (size_t i = 0; s < e; i++, s = (s+1)/2, e /= 2) {
      if (s&1) lv = op(lv, v[i][s]);
      if (e&1) rv = op(v[i][e-1], rv);
    }
    return op(lv, rv);
  }
};

int main() {
  int N, C, x;
  while (cin >> N) {
    vector<pair<int, int>> v;
    for (int i = 0; i < N; i++) {
      cin >> x;
      v.push_back({x, i});
    }
    sort(v.begin(), v.end());
    SegmentTree<int, plus<int>> used(N);
    int64_t ret = 0;
    for (auto [x, i] : v) {
      ret += min(i - used.Calc(0, i), N-1-i - used.Calc(i+1, N));
      used[i] = 1;
    }
    cout << ret << endl;
  }
}
