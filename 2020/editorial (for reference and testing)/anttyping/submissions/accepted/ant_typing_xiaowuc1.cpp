#include <algorithm>
#include <iostream>
#include <numeric>
#include <vector>

int main() {
  std::string s;
  std::cin >> s;
  std::vector<std::vector<int>> differences(9, std::vector<int>(9));
  for(int i = 1; i < s.size(); i++) {
    differences[s[i-1]-'1'][s[i]-'1']++;
  }
  std::vector<int> row(9);
  iota(row.begin(), row.end(), 0);
  int ans = 1e9;
  do {
    int cand = s.size() + row[s[0]-'1'];
    for(int i = 0; i < 9; i++) {
      for(int j = 0; j < 9; j++) {
        cand += abs(row[i] - row[j]) * differences[i][j];
      }
    }
    ans = std::min(ans, cand);
  }
  while(next_permutation(row.begin(), row.end()));
  std::cout << ans << "\n";
}
