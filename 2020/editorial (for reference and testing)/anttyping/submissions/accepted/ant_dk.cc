#include <algorithm>
#include <cmath>
#include <iostream>
#include <string>
#include <vector>
using namespace std;

int main() {
  string S;
  while (cin >> S) {
    vector<vector<int>> t(9, vector<int>(9));
    for (int i = 1; i < S.size(); i++) t[S[i-1]-'1'][S[i]-'1']++;
    vector<int> pos;
    for (int i = 0; i < 9; i++) pos.push_back(i);
    int ret = 1e9;
    do {
      int cur = pos[S[0]-'1']+1;
      for (int i = 0; i < 9; i++) for (int j = 0; j < 9; j++) cur += t[i][j] * (abs(pos[i]-pos[j])+1);
      ret = min(ret, cur);
    } while (next_permutation(pos.begin(), pos.end()));
    cout << ret << endl;
  }
}
