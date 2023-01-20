#include <iostream>

using namespace std;

int main() {
  int n, k;
  cin >> n >> k;
  double ans = 0;
  for(int i = 0; i < k; i++) {
    int x;
    cin >> x;
    ans += x;
  }
  printf("%f %f\n", (ans - 3 * (n-k))/n, (ans + 3 * (n-k))/n);
}
