#include <iostream>
#include <vector>
int main() {
  int n;
  std::cin >> n;
  std::vector<int> v(n);
  long long ans = 0;
  for(auto& x: v) {
    std::cin >> x;
    ans += x;
  }
  for(auto& x: v) {
    if(2*x == ans) {
      std::cout << x << "\n";
      return 0;
    }
  }
  std::cout << "BAD\n";
}
