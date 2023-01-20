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
#define MAXC 100001
bool dp[1001][MAXC];
int main() {
	int n, c;
	cin >> n >> c;
	VI items, rsum(n + 1, 0);
	REP(i,0,n) {
		int sz;
		cin >> sz;
		items.push_back(sz);
	}
	sort(items.rbegin(), items.rend());
	rsum[n - 1] = items[n - 1];
	for (int i = n - 2; i >= 0; i--) rsum[i] = items[i] + rsum[i + 1];

  int t = 0, pt, ans = -1;
	dp[0][0] = true;
	REP(i,1,n+1) {
	  pt = i - 1; t = i;
		int w = items[i - 1];
		REP(v,0,c+1) {
			if (dp[pt][v]) {
				dp[t][v] = true;
				if (v + w <= c) dp[t][v + w] = true;
			}
		}
		REP(v,0,c+1) {
			if (dp[t][v]) {
				int r = c - rsum[i] - v;
				if (r >= 0 && r < items[i - 1]) ans = max(ans, r);
			}
		}
	}
	cout << (ans == -1 ? rsum[0] : c - ans) << endl;
  return 0;
}
