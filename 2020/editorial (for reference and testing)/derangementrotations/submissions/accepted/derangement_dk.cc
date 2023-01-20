#include <cassert>
#include <iostream>
#include <vector>
using namespace std;

template<typename T> constexpr T Gcd(const T& a, const T& b) { return b != 0 ? Gcd(b, a%b) : a < 0 ? -a : a; }

int main() {
  int N, P;
  while (cin >> N >> P) {
    for (int x = 2; x*x <= P; x++) assert(P%x != 0);
    vector<int> p2(N, 1);
    for (int i = 1; i < N; i++) p2[i] = p2[i-1] * 2 % P;
    int64_t ret = 0;
    for (int x = 1; x < N; x++) {
      int g = Gcd(x, N);
      if (g == 1) continue;
      ret += p2[g-1] - 1;
    }
    ret = (ret%P) * (N-2) % P;
    cout << ret << endl;
  }
}
