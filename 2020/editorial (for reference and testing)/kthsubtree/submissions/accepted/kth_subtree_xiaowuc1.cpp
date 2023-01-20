#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

typedef long long ll;

const ll INF = 2e18;
ll safemult(ll a, ll b) {
  if(a == 0 || b == 0) return 0;
  if(a >= INF / b) return INF;
  return a * b;
}

void merge(vector<ll>& ret, const vector<ll>& a, const vector<ll>& b) {
  ret.resize(a.size() + b.size() - 1);
  for(int i = 0; i < a.size(); i++) {
    for(int j = 0; j < b.size(); j++) {
      ret[i+j] = min(INF, ret[i+j] + safemult(a[i], b[j]));
    }
  }
}

vector<int> edges[5005];
int treesz[5005];
ll dp[5005][5005]; // dp[i][j] is the number of subtrees rooted at i of size j, <= INF
void dfs(int curr, int par) {
  vector<pair<int, int>> sznode;
  treesz[curr] = 1;
  for(int i = 0; i < edges[curr].size(); i++) {
    if(edges[curr][i] == par) {
      if(i + 1 == edges[curr].size()) continue;
      swap(edges[curr][i], edges[curr][edges[curr].size()-1]);
      edges[curr].pop_back();
    }
    int child = edges[curr][i];
    dfs(child, curr);
    treesz[curr] += treesz[child];
    sznode.emplace_back(treesz[child], child);
  }
  sort(sznode.begin(), sznode.end());
  vector<ll> currsz(2);
  currsz[1] = 1;
  for(auto nodemd: sznode) {
    int node = nodemd.second;
    vector<ll> vs(nodemd.first + 1);
    for(int a = 0; a <= nodemd.first; a++) {
      vs[a] = dp[node][a];
    }
    vector<ll> ncurrsz;
    merge(ncurrsz, currsz, vs);
    currsz.swap(ncurrsz);
  }
  currsz[0] = 1;
  for(int i = 0; i <= treesz[curr]; i++) {
    dp[curr][i] = currsz[i];
  }
}
int main() {
  int n;
  ll thresh;
  cin >> n >> thresh;
  for(int i = 1; i < n; i++) {
    int a, b;
    cin >> a >> b;
    edges[a].push_back(b);
    edges[b].push_back(a);
  }
  dfs(1, -1);
  for(int j = 1; j <= n; j++) {
    for(int i = 1; i <= n; i++) {
      thresh -= dp[i][j];
      if(thresh <= 0) {
        cout << j << "\n";
        return 0;
      }
    }
  }
  cout << "-1\n";
}