#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

vector<vector<int>> c;

pair<vector<int64_t>, vector<int64_t>> doit(int x, int par) {
  vector<int64_t> ret1{0, 1}, ret2{1, 0};
  for (int i = 0; i < c[x].size(); i++) if (c[x][i] != par) {
    auto [v1, v2] = doit(c[x][i], x);
    ret2.resize(ret2.size() + v1.size()-1);
    for (int i = 1; i < v1.size(); i++) ret2[i] += v1[i];
    for (int i = 1; i < v2.size(); i++) ret2[i] += v2[i];
    for (int i = 0; i < ret2.size(); i++) ret2[i] = min(int64_t(2e18), ret2[i]);
    vector<int64_t> v(ret1.size() + v1.size()-1);
    for (int i = 1; i < ret1.size(); i++) v[i] = ret1[i];
    for (int i = 1; i < ret1.size(); i++) for (int j = 1; j < v1.size(); j++) {
      if (double(ret1[i])*v1[j] > 2e18) v[i+j] = 2e18; else v[i+j] = min(int64_t(2e18), v[i+j] + ret1[i]*v1[j]);
    }
    ret1.swap(v);
  }
  return {ret1, ret2};
}

int main() {
  int N, U, V;
  int64_t K;
  while (cin >> N >> K) {
    c = vector<vector<int>>(N+1);
    for (int i = 0; i < N-1; i++) {
      cin >> U >> V;
      c[U].push_back(V);
      c[V].push_back(U);
    }

    auto [v1, v2] = doit(1, -1);
    int ret = 1;
    for (; ret <= N && K > v1[ret]+v2[ret]; ret++) K -= v1[ret]+v2[ret];
    cout << (ret > N ? -1 : ret) << endl;
  }
}
