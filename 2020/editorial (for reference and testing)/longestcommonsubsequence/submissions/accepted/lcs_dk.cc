#include <algorithm>
#include <cstring>
#include <iostream>
#include <string>
using namespace std;

int N, K;
int t[26][26];

int memo[26];
int doit(int x) {
  int& ret = memo[x];
  if (ret) return ret;
  ret = 1;
  for (int y = 0; y < 26; y++) if (t[x][y] == N) ret = max(ret, 1+doit(y));
  return ret;
}

int main() {
  while (cin >> N >> K) {
    memset(t, 0, sizeof(t));
    for (int i = 0; i < N; i++) {
      string s;
      cin >> s;
      for (int j = 0; j < K; j++)
      for (int k = j+1; k < K; k++) {
        t[s[j]-'A'][s[k]-'A']++;
      }
    }

    memset(memo, 0, sizeof(memo));
    int ret = 0;
    for (int i = 0; i < K; i++) ret = max(ret, doit(i));
    cout << ret << endl;
  }
}
