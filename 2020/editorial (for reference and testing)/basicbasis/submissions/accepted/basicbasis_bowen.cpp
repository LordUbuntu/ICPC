#include <iostream>
#include <cstdio>
#include <cmath>
#include <cstring>
#include <algorithm>
#include <string>
#include <vector>
#include <stack>
#include <queue>
#include <set>
#include <map>
#include <unordered_set>
#include <unordered_map>
#include <sstream>
#include <complex>
#include <ctime>
#include <cassert>
#include <functional>

using namespace std;

typedef unsigned long long ull;
typedef vector<int> VI;
typedef pair<int,int> PII;
typedef vector<ull> VU;

#define REP(i,s,t) for(int i=(s);i<(t);i++)
#define FILL(x,v) memset(x,v,sizeof(x))
#define B 64
#define BW 13 // 800 / 64

const ull MAXULL = 0ULL - 1;

VU seq[1000], base[800];
int n, m, k, bk, bits[1 << 16], basei[800];
int value(char x) {
  if ('0' <= x && x <= '9') return x - '0';
  return 10 + (x - 'a');
}
int bitOffset(ull x) {
  if (x < (1ULL << 16)) return bits[x];
  return bitOffset(x >> 16) + 16;
}
int firstBit(VU &vs) {
  int res = 0;
  for (auto &v: vs) {
    if (v == 0) res += 64;
    else return bitOffset(-v & v) + res;
  }
  return -1;
}
VU vxor(VU &x, VU &y) {
  VU res(x.size(), 0);
  REP(i,0,x.size()) res[i] = x[i] ^ y[i];
  return res;
}
VU parseVU(string s) {
  VU vu;
  int idx = 0;
  REP(g,0,bk) {
    ull v = 0;
    REP(i,0,B/4) {
      if (idx >= s.size()) break;
      v = (v << 4) + value(s[idx++]);
    }
    vu.push_back(v);
  }
  return vu;
}
pair<VU, int> composeVU(VU v, int &maxi) {
  int b = -1;
  while ((b = firstBit(v)) != -1) {
    if (basei[b] == -1) break;
    maxi = max(maxi, basei[b]);
    v = vxor(v, base[b]);
  }
  return make_pair(v, b);
}

int main() {
  REP(i,0,16) bits[1 << i] = i;
  FILL(basei, -1);
  string s;
  cin >> n >> m >> k;
  bk = (k * 4 + B - 1) / B;
  int zero = -1, maxi;
  REP(i,0,n) {
    cin >> s;
    seq[i] = parseVU(s);
    pair<VU, int> vr = composeVU(seq[i], maxi);
    if (vr.second != -1) {
      base[vr.second] = vr.first;
      basei[vr.second] = i;
    } else if (zero == -1) zero = i + 1;
  }
  while (m--) {
    cin >> s;
    VU v = parseVU(s);
    int ans = -1;
    if (firstBit(v) == -1) ans = zero;
    else {
      maxi = -1;
      pair<VU, int> vr = composeVU(v, maxi);
      if (vr.second == -1) ans = maxi + 1;
    }
    cout << ans << endl;
  }
  return 0;
}
