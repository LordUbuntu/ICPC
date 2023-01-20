#include <algorithm>
#include <cstring>
#include <iostream>
#include <set>
#include <vector>
using namespace std;

int N, K;
int vis[100001][29][4];
int best[100001][29];
int ret[100001];

int main() {
  while (cin >> N >> K) {
    vector<pair<int, int>> v{{0, 0}};
    for (int i = 1, x; i <= N; i++) {
      cin >> x;
      v.push_back({x, i});
    }
    sort(v.begin(), v.end());

    memset(vis, 0, sizeof(vis));
    for (int d = 28, sz = (1<<27); d >= 0; d--, sz >>= 1) {
      set<pair<int, int>> pos;
      for (int i = 0, j = 0, k = 0; i < v.size(); ) {
        if (k < v.size() && v[k].first <= v[j].first+sz) {
          pos.insert({-v[k].second, v[k].first});
          k++;
        } else if (j < v.size() && v[j].first <= v[i].first+sz) {
          auto it = pos.begin();
          for (int m = 0; m < K && it != pos.end(); m++, ++it) vis[v[j].second][d][m] = -it->first;
          j++;
        } else {
          pos.erase({-v[i].second, v[i].first});
          i++;
        }
      }
    }

    memset(best, 63, sizeof(best));
    memset(ret, 63, sizeof(ret));
    vector<pair<int, int>> q{{0, 1}};
    best[0][1] = 0;
    for (int dist = 0; !q.empty(); dist++) {
      vector<pair<int, int>> q2;
      for (auto [i, d] : q) {
        if (d && best[i][d-1] > dist+1) { best[i][d-1] = dist+1; q2.push_back({i, d-1}); }
        if (d < 28 && best[i][d+1] > dist+1) { best[i][d+1] = dist+1; q2.push_back({i, d+1}); }
        for (int m = 0; m < K; m++) {
          int n = vis[i][d][m];
          if (n == i) ret[i] = min(ret[i], dist);
          if (n != -1 && best[n][d] > dist+1) { best[n][d] = dist+1; q2.push_back({n, d}); }
        }
      }
      q.swap(q2);
    }

    for (int i = 1; i <= N; i++) cout << (ret[i] > 1e8 ? -1 : ret[i]) << endl;
  }
}
