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
typedef pair<VI,char> PIIC;
#define REP(i,s,t) for(int i=(s);i<(t);i++)
#define FILL(x,v) memset(x,v,sizeof(x))

const int INF = (int)1E9;
#define MAXN 100

bool v[MAXN][MAXN][MAXN];
bool ve[MAXN * MAXN];
vector<PIIC> adj[MAXN];
void dfs(int r, int b, int y, int e) {
	if (e != -1) ve[e] = true;
	if (v[r][b][y]) return;
	v[r][b][y] = true;
	for (auto &x: VI({r, b, y}))
		for (auto &ei: adj[x]) {
			int p = ei.first[0], q = ei.first[1], ne = ei.first[2];
			char c = ei.second;
			if ((c == 'R' || c == 'X') && p == r) dfs(q, b, y, ne);
			else if ((c == 'B' || c == 'X') && p == b) dfs(r, q, y, ne);
			else if ((c == 'Y' || c == 'X') && p == y) dfs(r, b, q, ne);
			else if (c == 'P' && r == b && p == r) dfs(q, q, y, ne);
			else if (c == 'O' && r == y && p == r) dfs(q, b, q, ne);
			else if (c == 'G' && b == y && p == b) dfs(r, q, q, ne);
		}
}

int main() {
	int n, m, R, B, Y;
	cin >> n >> m >> R >> B >> Y; R--; B--; Y--;
	VI ce;
	REP(i,0,m) {
		int a, b;
		string c;
		cin >> a >> b >> c; a--; b--;
		adj[a].push_back(make_pair(VI({a, b, c[0] == 'X' ? -1 : i}), c[0]));
		adj[b].push_back(make_pair(VI({b, a, c[0] == 'X' ? -1 : i}), c[0]));
		if (c[0] != 'X') ce.push_back(i);
	}
	dfs(R, B, Y, -1);
	bool ans = true;
	for (auto &e: ce) if (!ve[e]) ans = false;
	cout << ans << endl;
  return 0;
}
