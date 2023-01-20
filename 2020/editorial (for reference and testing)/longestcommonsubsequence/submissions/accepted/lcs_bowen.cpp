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

typedef long long ll;
typedef vector<int> VI;
typedef pair<int,int> PII;

#define REP(i,s,t) for(int i=(s);i<(t);i++)
#define FILL(x,v) memset(x,v,sizeof(x))

const int INF = (int)1E9;
#define MAXN 100005

int cnt[26][26], dp[26];
int main() {
  int n, k;
  string s;
  cin >> n >> k;
  REP(i,0,n) {
    cin >> s;
    REP(p,0,k) REP(q,p+1,k) cnt[s[p] - 'A'][s[q] - 'A']++;
  }
  REP(c,0,k) dp[c] = 1;
  REP(it,0,k)
    REP(i,0,k)
      REP(j,0,k)
        if (cnt[i][j] == n)
          dp[i] = max(dp[i], dp[j] + 1);
  int ans = 0;
  REP(c,0,k) ans = max(ans, dp[c]);
  cout << ans << endl;
  return 0;
}
