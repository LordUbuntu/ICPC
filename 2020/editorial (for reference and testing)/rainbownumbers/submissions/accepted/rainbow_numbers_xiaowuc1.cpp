#include <cassert>
#include <iostream>
#include <string>

typedef long long ll;

ll pownine[1000005];
ll powninepref[1000005];
const int MOD = 998244353;

using namespace std;

bool isgood(const string& s) {
  for(int i = 1; i < s.size(); i++) if(s[i-1] == s[i]) return false;
  return true;
}

int dfs(const string& s, int idx, bool nonzero, int last) {
  if(idx == s.size()) {
    if(nonzero) return 1;
    return 0;
  }
  int ret = 0;
  for(int i = 0; i < s[idx] - '0'; i++) {
    if(i == last) continue;
    if(i == 0 && !nonzero) ret += powninepref[s.size()-idx-1];
    else ret += pownine[s.size()-idx-1];
    if(ret >= MOD) ret -= MOD;
  }
  if(s[idx] - '0' != last) {
    ret += dfs(s, idx+1, nonzero || (s[idx] - '0' > 0), s[idx] - '0');
    if(ret >= MOD) ret -= MOD;
  }
  return ret;
}

int solve(const string& s) {
  return dfs(s, 0, false, -1);
}

int slowsolve(const string& s) {
  int rhs = 0;
  for(auto x: s) {
    rhs = 10 * rhs + x - '0';
  }
  int ret = 0;
  for(int i = 1; i <= rhs; i++) ret += isgood(to_string(i));
  return ret;
}

int main() {
  pownine[0] = 1;
  for(int i = 1; i <= 1000000; i++) {
    pownine[i] = (pownine[i-1] * 9) % MOD;
  }
  for(int i = 1; i <= 1000001; i++) {
    powninepref[i] = powninepref[i-1] + pownine[i];
    powninepref[i] %= MOD;
  }

  string lhs, rhs;
  cin >> lhs >> rhs;
  ll ret = solve(rhs) - solve(lhs);
  // check lhs
  {
    bool good = true;
    for(int i = 1; i < lhs.size(); i++) good &= lhs[i] != lhs[i-1];
    ret += good;
  }
  if(ret < 0) ret += MOD;
  cout << ret << "\n";
}
