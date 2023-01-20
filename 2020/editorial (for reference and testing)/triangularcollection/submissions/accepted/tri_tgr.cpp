#include <iostream>
#include <algorithm>
#include <vector>
using namespace std ;
using ll = long long ;
int main() {
   int n ;
   cin >> n ;
   vector<int> v(n) ;
   for (auto &vv : v)
      cin >> vv ;
   sort(v.begin(), v.end()) ;
   ll r = 0 ;
   for (int a=0; a<(int)v.size(); a++)
      for (int b=a+1, c=b+1; b<(int)v.size(); b++) {
         while (c < (int)v.size() && v[c] < v[a] + v[b])
            c++ ;
         r += (1LL << (c-b-1)) - 1 ;
      }
   cout << r << endl ;
}
