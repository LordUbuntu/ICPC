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

const ll INF = (ll)2E18;
#define MAXN 5005

VI adj[MAXN];
ll dp[MAXN][MAXN];
inline ll add(ll a, ll b) {
	if (a >= INF || b >= INF) return INF;
	return a + b > INF ? INF : a + b;
}
inline ll mult(ll a, ll b) {
	if (a == 0 || b == 0) return 0;
	if (a >= INF || b >= INF || a > INF / b) return INF;
	return a * b;
}
int dfs(int x, int p) {
	int xSize = 1;
	dp[x][1] = 1;
	REP(i,0,adj[x].size()) {
		int y = adj[x][i];
		if (y == p) continue;
		int ySize = dfs(y, x);
		for (int xs = xSize + ySize; xs >= 1; xs--) {
			REP(ys,max(1,xs-xSize),ySize+1) {
				ll pre = dp[y][ys];
				if (pre == 0) continue;
				if (xs - ys < 0) break;

				ll m = mult(dp[y][ys], dp[x][xs - ys]);
				if (m >= INF) dp[x][xs] = INF;
				else dp[x][xs] = add(dp[x][xs], m);
			}
		}
		xSize += ySize;
	}
	return xSize;
}
int main() {
	int n;
	ll k;
	cin >> n >> k;
	REP(i,0,n-1) {
		int a, b;
		cin >> a >> b; a--; b--;
		adj[a].push_back(b);
		adj[b].push_back(a);
	}
	dfs(0, -1);
	int sz = 1;
	while (k > 0 && sz <= n) {
		REP(x,0,n) {
			if (k <= dp[x][sz]) return cout << sz << endl, 0;
			k -= dp[x][sz];
		}
		sz++;
	}
	cout << -1 << endl;
  return 0;
}
