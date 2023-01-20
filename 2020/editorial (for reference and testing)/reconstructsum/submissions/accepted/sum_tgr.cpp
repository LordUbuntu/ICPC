#include <iostream>
#include <vector>
using namespace std ;
using ll = long long ;
int main() {
   int n ;
   cin >> n ;
   vector<ll> v(n) ;
   for (auto &val: v)
      cin >> val ;
   ll s = 0 ;
   for (auto val : v)
      s += val ;
   for (auto val : v)
      if (val + val == s) {
         cout << val << endl ;
         exit(0) ;
      }
   cout << "BAD" << endl ;
}
