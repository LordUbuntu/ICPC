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
#define MAXZ 28
#define MAXSEG (4 * MAXN)

VI val[MAXSEG];
int K, p[MAXN], px[MAXN], xp[MAXN], xs[MAXN], X, ans[MAXN];
int dst[MAXN][MAXZ + 1];

VI merge(VI &a, VI &b) {
	VI r = a;
	r.insert(r.begin(), b.begin(), b.end());
	sort(r.rbegin(), r.rend());
	if (r.size() > K) r.erase(r.begin() + K, r.end());
	return r;
}
void build(int k, int nl, int nr) {
  if (nl == nr) {
		if (xp[nl] != -1) val[k].push_back(xp[nl]);
    return;
  }
  int nm = (nl + nr) / 2;
  build(2*k+1, nl, nm);
  build(2*k+2, nm+1, nr);
	val[k] = merge(val[2*k+1], val[2*k+2]);
}
VI get(int k, int nl, int nr, int l, int r) {
	if(r<nl || l>nr) return VI();
	if(l<=nl && nr<=r) return val[k];
	int nm = (nl+nr)>>1;
	VI vl = get(2*k+1, nl, nm, l, r);
	VI vr = get(2*k+2, nm+1, nr, l, r);
	return merge(vl, vr);
}
int Xi(int x) {
	return lower_bound(xs, xs + X, x) - xs;
}
int main() {
  int n;
	cin >> n >> K;
	REP(i,0,n) {
		cin >> p[i];
		xs[X++] = p[i];
	}
	xs[X++] = 0;
	sort(xs, xs + X);
	X = unique(xs, xs + X) - xs;
	FILL(xp, -1);
	REP(i,0,n) {
		px[i] = Xi(p[i]);
		xp[px[i]] = i;
	}
	build(0, 0, X - 1);

	FILL(ans, -1);
	REP(i,0,X) REP(z,0,MAXZ+1) dst[i][z] = INF;
	queue<PII> q;
	q.push(PII(Xi(0),1));
	dst[Xi(0)][1] = 0;
	while (q.size()) {
		int x = q.front().first, z = q.front().second, d = dst[x][z];
		q.pop();
		if (z + 1 <= MAXZ && dst[x][z + 1] == INF) {
			dst[x][z + 1] = d + 1;
			q.push(PII(x, z + 1));
		}
		if (z - 1 >= 0 && dst[x][z - 1] == INF) {
			dst[x][z - 1] = d + 1;
			q.push(PII(x, z - 1));
		}

		int span = z == 0 ? 0 : 1 << (z - 1);
		int xl = Xi(xs[x] - span), xr = Xi(xs[x] + span + 1) - 1;
		VI pts = get(0, 0, X - 1, xl, xr);
		if (xp[x] != -1 && find(pts.begin(), pts.end(), xp[x]) != pts.end() && ans[xp[x]] == -1) ans[xp[x]] = d;
		for (auto &np: pts) {
			int nx = px[np];
			if (dst[nx][z] == INF) {
				dst[nx][z] = d + 1;
				q.push(PII(nx, z));
			}
		}
	}
	REP(i,0,n) cout << ans[i] << endl;
  return 0;
}
