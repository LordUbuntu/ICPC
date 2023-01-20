#include <iostream>
#include <vector>
using namespace std;

int main() {
  int N;
  while (cin >> N) {
    vector<int> v(N);
    for (auto& x : v) cin >> x;
    int tot = 0, found = false;
    for (auto x : v) tot += x;
    for (auto x : v) if (x+x == tot) found = true;
    if (found) cout << tot/2 << endl; else cout << "BAD" << endl;
  }
}
