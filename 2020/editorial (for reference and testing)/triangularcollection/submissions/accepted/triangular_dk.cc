#include <algorithm>
#include <iostream>
#include <vector>
using namespace std;

int main() {
  int N;
  while (cin >> N) {
    vector<int> v(N);
    for (auto& x : v) cin >> x;
    sort(v.begin(), v.end());
    int64_t ret = 0;
    for (int i = 0; i < N; i++)
    for (int j = i+1; j < N; j++)
    for (int k = j+1; k < N; k++) {
      if (v[k] < v[i]+v[j]) ret += (1LL<<(k-j-1));
    }
    cout << ret << endl;
  }
}
