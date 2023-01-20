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
#define MAXN 300005

int arr[MAXN];
#define MAXN 300005
int s[MAXN];
void add(int k, int v){
	k++;
	for(;k<MAXN;k+=-k&k) s[k]+=v;
}
int get(int k){
	if (k < 0) return 0;
	k++;
	int ans = 0;
	for(;k;k-=-k&k) ans += s[k];
	return ans;
}
int main() {
	int n;
	cin >> n;
	vector<PII> vals;
	REP(i,0,n) {
		cin >> arr[i];
		vals.push_back(PII(arr[i], i));
	}
	sort(vals.rbegin(), vals.rend());
	int cnt = 0;
	ll ans = 0;
	for (auto &vp: vals) {
		int v = vp.first, p = vp.second;
		int lcnt = get(p - 1), rcnt = cnt - lcnt;
		add(p, 1);
		ans += min(lcnt, rcnt);
		cnt++;
	}
	cout << ans << endl;
  return 0;
}
