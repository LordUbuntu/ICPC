#include <iostream>
#include <vector>
using namespace std;

int main() {
  int N, x;
  while (cin >> N) {
    vector<int> stack;
    int64_t ret = 0;
    for (int i = 0; i < N; i++) {
      cin >> x;
      while (stack.size() && stack.back() < x) { ret++; stack.pop_back(); }
      if (stack.size()) ret++;
      stack.push_back(x);
    }
    cout << ret << endl;
  }
}
