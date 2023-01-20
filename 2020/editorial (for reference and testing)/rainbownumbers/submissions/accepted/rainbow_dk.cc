#include <cstring>
#include <iostream>
#include <string>
using namespace std;

#define MOD 998244353

string L, U;

int memo[100001][2][2][2][10];
int doit(int n, int nonzero, int lb, int ub, int lastdig) {
  if (n == L.size()) return 1;
  int& ret = memo[n][nonzero][lb][ub][lastdig];
  if (ret) return ret;
  for (int dig = 0; dig <= 9; dig++) {
    if (dig == lastdig && nonzero) continue;
    if (!lb && dig < L[n]-'0') continue;
    if (!ub && dig > U[n]-'0') continue;
    ret += doit(n+1, nonzero | (dig != 0), lb | (dig > L[n]-'0'), ub | (dig < U[n]-'0'), dig);
    ret %= MOD;
  }
  return ret;
}

int main() {
  while (cin >> L >> U) {
    L = string(U.size()-L.size(), '0') + L;
    memset(memo, 0, sizeof(memo));
    cout << doit(0, 0, 0, 0, 0) << endl;
  }
}
