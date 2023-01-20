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

int go[9][9];
int main() {
  srand(42);
	string s;
	cin >> s;
	REP(i,1,s.size()) {
		int a = s[i - 1] - '1', b = s[i] - '1';
		if (a > b) swap(a, b);
		go[a][b]++;
	}
	int perm[9];
	int ans = INF;
  time_t start = clock();
  while(clock() - start < 0.99 * CLOCKS_PER_SEC) {
		iota(perm, perm + 9, 0);
		int r = INF;
		int it = 100;
   	while (it--) {
			vector<pair<int, PII>> candidateSwaps;
			REP(ii,0,9) {
				REP(jj,ii+1,9) {
					swap(perm[ii], perm[jj]);
					int t = perm[s[0] - '1'];
					REP(i,0,9) REP(j,i+1,9) t += go[i][j] * abs(perm[i] - perm[j]);
					candidateSwaps.push_back(make_pair(t, PII(ii, jj)));
					swap(perm[ii], perm[jj]);
				}
			}
			sort(candidateSwaps.begin(), candidateSwaps.end());
			// Choose any swap among the 10 best cost decreases.
			int cho = rand() % min(10, (int)candidateSwaps.size());
			swap(perm[candidateSwaps[cho].second.first], perm[candidateSwaps[cho].second.second]);
			r = candidateSwaps[cho].first;
			ans = min(ans, r);
    }
	}
	cout << ans + s.size() << endl;
  return 0;
}
