#include <algorithm>
#include <iostream>
#include <string>
#include <vector>
using namespace std;

string Hex = "0123456789abcdef";

vector<bool> operator^(const vector<bool>& a, const vector<bool>& b) {
  vector<bool> ret(a.size());
  for (int i = 0; i < a.size(); i++) ret[i] = a[i] ^ b[i];
  return ret;
}

int main() {
  int N, M, K;
  char H;
  while (cin >> N >> M >> K) {
    vector<vector<bool>> BA(N+M);
    for (int i = 0; i < N+M; i++)
    for (int j = 0; j < K; j++) {
      cin >> H;
      int h = Hex.find(H);
      for (int k = 3; k >= 0; k--) BA[i].push_back(h&(1<<k));
    }
    for (int i = 0; i < N; i++) for (int j = 0; j < i; j++) BA[i] = min(BA[i], BA[i] ^ BA[j]);
    vector<bool> zero(4*K);
    for (int i, j = N; j < N+M; j++) {
      if (BA[j] == zero) {
        for (i = 0; i < N && BA[i] != zero; i++)
          ;
        cout << (i < N ? i+1 : -1) << endl;
      } else {
        for (i = 0; i < N && BA[j] != zero; i++) BA[j] = min(BA[j], BA[j] ^ BA[i]);
        cout << (BA[j] == zero ? i : -1) << endl;
      }
    }
  }
}
