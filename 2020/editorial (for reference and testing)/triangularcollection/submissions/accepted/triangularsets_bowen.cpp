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
	int n;
	cin >> n;
	VI es(n, 0);
	REP(i,0,n) cin >> es[i];
	sort(es.begin(), es.end());
  ll ans = 0;
	REP(i,0,n)
		REP(j,i+1,n)
			REP(k,j+1,n)
				if (es[i] + es[j] > es[k])
					ans += 1LL << (k - j - 1);
	cout << ans << endl;
  return 0;
}
