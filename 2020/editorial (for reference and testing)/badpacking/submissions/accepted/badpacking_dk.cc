#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
  int64_t N, C;
  while (cin >> N >> C) {
    vector<int64_t> v(N);
    for (auto& x : v) cin >> x;
    sort(v.begin(), v.end());
    vector<int64_t> sum(N+1);
    for (int i = 0; i < N; i++) sum[i+1] = sum[i] + v[i];

    int64_t ret = min(C, sum[N]);
    vector<int> seen(C+1);
    seen[0] = 1;
    for (int i = N-1; i >= 0; i--) {
      for (int64_t j = C; j >= v[i]; j--) seen[j] |= seen[j-v[i]];
      for (int64_t j = 0; j+sum[i] <= C; j++) {
        if (seen[j] && j+sum[i]+v[i] > C) ret = min(ret, j+sum[i]);
      }
    }
    cout << ret << endl;
  }
}
