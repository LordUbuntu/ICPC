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

int n, si = 0;
string s;
int main() {
	cin >> n >> s;
	REP(i,1,n+1) {
		VI ds;
		int x = i;
		while (x) {
			ds.push_back(x % 10);
			x /= 10;
		}
		reverse(ds.begin(), ds.end());
		for (auto &d: ds) {
			if (d != s[si++] - '0') return cout << i << endl, 0;
		}
	}
  return 0;
}
