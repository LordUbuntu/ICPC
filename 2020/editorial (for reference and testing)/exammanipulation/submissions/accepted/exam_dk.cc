#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
using namespace std;

int main() {
  int N, K;
  while (cin >> N >> K) {
    vector<string> v(N);
    for (auto& s : v) cin >> s;
    int ret = 0;
    for (int b = 0; b < (1<<K); b++) {
      int mn = K;
      for (auto const& s : v) {
        int cur = 0;
        for (int i = 0; i < K; i++) cur += ((s[i] == 'T') ^ !(b&(1<<i)));
        mn = min(mn, cur);
      }
      ret = max(ret, mn);
    }
    cout << ret << endl;
  }
}
