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
#include <numeric>
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

int go[10][10];
int main() {
	string s;
	cin >> s;
	REP(i,1,s.size()) go[s[i - 1] - '1'][s[i] - '1']++;
	int order[9];
	iota(order, order + 9, 0);
	int ans = INF;
	do {
		int pos[9] = {};
		REP(p,0,9) pos[order[p]] = p;
		int sol = pos[s[0] - '1'];
		REP(i,0,9)
			REP(j,0,9)
				sol += go[i][j] * abs(pos[i] - pos[j]);
		ans = min(ans, sol);
	} while (next_permutation(order, order + 9));
	cout << ans + s.size() << endl;
  return 0;
}
