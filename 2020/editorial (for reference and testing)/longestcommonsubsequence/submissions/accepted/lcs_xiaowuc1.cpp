#include <algorithm>
#include <cassert>
#include <chrono>
#include <iostream>
#include <random>

using namespace std;

bool good[26][26];
int dp[26][26];

int main() {
  int n,slen;
  std::cin >> n >> slen;
  int k = 0;
  for(int i = 0; i < 26; i++) {
    for(int j = 0; j < 26; j++) {
      good[i][j] = true;
    }
    good[i][i] = false;
  }
  while(n--) {
    string s;
    cin >> s;
    k = max(k, (int)s.size());
    for(int i = 0; i < s.size(); i++) {
      for(int j = i+1; j < s.size(); j++) {
        good[s[j]-'A'][s[i]-'A'] = false;
      }
    }
  }
  for(int i = 0; i < k; i++) {
    for(int j = 0; j < k; j++) {
      if(good[i][j]) dp[i][j] = 1;
    }
  }
  for(int a = 0; a < k; a++) for(int b = 0; b < k; b++) for(int c = 0; c < k; c++) if(dp[a][b] && dp[b][c]) dp[a][c] = max(dp[a][c], dp[a][b] + dp[b][c]);
  for(int a = 0; a < k; a++) for(int b = 0; b < k; b++) for(int c = 0; c < k; c++) if(dp[a][b] && dp[b][c]) dp[a][c] = max(dp[a][c], dp[a][b] + dp[b][c]);
  for(int a = 0; a < k; a++) for(int b = 0; b < k; b++) for(int c = 0; c < k; c++) if(dp[a][b] && dp[b][c]) dp[a][c] = max(dp[a][c], dp[a][b] + dp[b][c]);
  int ret = 1;
  for(int a = 0; a < k; a++) for(int b = 0; b < k; b++) ret = max(ret, dp[a][b]+1);
  cout << ret << "\n";
}
