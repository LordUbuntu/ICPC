#include <algorithm>
#include <cstring>
#include <iostream>

using namespace std;

int l[1001];
int psum[1002];
bool dp[100001];
int main() {
  int n, k;
  cin >> n >> k;
  for(int i = 0; i < n; i++) {
    cin >> l[i];
  }
  sort(l, l+n);
  for(int i = 0; i < n; i++) {
    psum[i+1] = psum[i] + l[i];
  }
  if(psum[n] <= k) {
    cout << psum[n] << "\n";
    return 0;
  }
  int ret = k;
  dp[0] = true;
  for(int i = n-1; i >= 0; i--) {
    int v = max(0, k - psum[i+1] + 1);
    int x = v;
    if(v) {
      v--;
      while(true) {
        v++;
        if(v > k) {
          x = k;
          break;
        }
        if(dp[v]) {
          x = v;
          break;
        }
      }      
    }
    ret = min(ret, x + psum[i]);
    for(int a = k; a >= l[i]; a--) {
      dp[a] |= dp[a-l[i]];
    }
  }
  cout << ret << "\n";
}