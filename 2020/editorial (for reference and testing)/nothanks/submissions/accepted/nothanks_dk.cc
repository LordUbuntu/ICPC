#include <algorithm>
#include <cassert>
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
    for (int i = 0; i < v.size(); i++) {
      assert(!i || v[i-1] != v[i]);
      if (!i || v[i-1]+1 != v[i]) ret += v[i];
    }
    cout << ret << endl;
  }
}
