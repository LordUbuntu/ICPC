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

int main() {
	ll ans = 0;
	int n;
	cin >> n;
	VI hs(n, 0);
	REP(i,0,n) cin >> hs[i];
	REP(t,0,2) {
		VI stk;
		REP(i,0,n) {
			int h = hs[i];
			while (stk.size() && stk.back() < h) {
				ans++;
				stk.pop_back();
			}
			stk.push_back(h);
		}
		reverse(hs.begin(), hs.end());
	}
	cout << ans << endl;
  return 0;
}
