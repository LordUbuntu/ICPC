#include <iostream>

using namespace std;

int main() {
  int T;
  cin >> T;
  for (int tc = 0; tc < T; tc++) {
    int V, E;
    cin >> V >> E;
    cout << 2 + E - V << endl;
  }
}
