#include <algorithm>
#include <array>
#include <cstring>
#include <iostream>
#include <map>
#include <queue>

using namespace std;

int k;
array<int, 4> merge(const array<int, 4>& lhs, const array<int, 4>& rhs) {
  array<int, 4> ret = {-1, -1, -1, -1};
  int i = 0;
  int j = 0;
  while(i + j < k) {
    if(lhs[i] > rhs[j]) {
      ret[i+j] = lhs[i];
      i++;
    }
    else {
      ret[i+j] = rhs[j];
      j++;
    }
  }
  return ret;
}

const int SZ = 1 << 17;
array<int, 4> tree[2 * SZ];
void upd(int idx, int val) {
  idx += SZ;  
  tree[idx] = {val, -1, -1, -1};
  while(idx > 1) {
    idx /= 2;
    tree[idx] = merge(tree[2*idx], tree[2*idx+1]);
  }
}
array<int, 4> qry(int lhs, int rhs) {
  array<int, 4> ret = {-1, -1, -1, -1};
  lhs += SZ;
  rhs += SZ;
  while(lhs <= rhs) {
    if(lhs%2) ret = merge(ret, tree[lhs++]);
    if(rhs%2==0) ret = merge(ret, tree[rhs--]);
    lhs /= 2;
    rhs /= 2;
  }
  return ret;
}

int sortedx[100005];
int origx[100005];
int ret[100005];
int dp[100005][30];
int n;
int main() {
  cin >> n >> k;
  fill(ret, ret+n, 1e9);
  for(int i = 0; i < n; i++) {
    cin >> origx[i];
    origx[i] *= 2;
  }
  memcpy(sortedx, origx, sizeof(origx));
  sort(sortedx, sortedx+n+1);
  int dec = 0;
  for(int i = 0; i < n; i++) {
    int pos = lower_bound(sortedx, sortedx+n+1, origx[i]) - sortedx;
    upd(pos, i);
  }
  upd(lower_bound(sortedx, sortedx+n+1, 0) - sortedx, -1);
  queue<pair<int, int>> q; // <center, SZ>
  memset(dp, 1, sizeof(dp));
  q.emplace(lower_bound(sortedx, sortedx+n+1, 0) - sortedx, 1);
  dp[q.front().first][q.front().second] = 0;
  while(q.size()) {
    const auto curr = q.front(); q.pop();
    // recenter
    {
      int lhs = lower_bound(sortedx, sortedx+n+1, sortedx[curr.first] - (1 << curr.second)) - sortedx;
      int rhs = upper_bound(sortedx, sortedx+n+1, sortedx[curr.first] + (1 << curr.second)) - sortedx - 1;
      auto weights = qry(lhs, rhs);
      for(int w: weights) {
        if(w < 0) break;
        if(origx[w] == sortedx[curr.first]) ret[w] = min(ret[w], dp[curr.first][curr.second]);
        pair<int, int> cand = {lower_bound(sortedx, sortedx+n+1, origx[w]) - sortedx, curr.second};
        if(dp[cand.first][cand.second] > dp[curr.first][curr.second] + 1) {
          dp[cand.first][cand.second] = dp[curr.first][curr.second] + 1;
          q.push(cand);
        }
      }
    }
    // double
    if((1<<curr.second) < sortedx[n] - sortedx[0]) {
      pair<int, int> cand = {curr.first, curr.second + 1};
      if(dp[cand.first][cand.second] > dp[curr.first][curr.second] + 1) {
        dp[cand.first][cand.second] = dp[curr.first][curr.second] + 1;
        q.push(cand);
      }
    }
    // half
    if(curr.second > 0) {
      pair<int, int> cand = {curr.first, curr.second - 1};
      if(dp[cand.first][cand.second] > dp[curr.first][curr.second] + 1) {
        dp[cand.first][cand.second] = dp[curr.first][curr.second] + 1;
        q.push(cand);
      }
    }
  }
  for(int i = 0; i < n; i++) {
    if(ret[i] == 1e9) ret[i] = -1;
    cout << ret[i] << "\n";
  }
}