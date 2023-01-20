#include <iostream>
#include <algorithm>
#include <vector>
using namespace std ;
int main() {
   int n ;
   cin >> n ;
   vector<int> v(n) ;
   for (auto &vv : v)
      cin >> vv ;
   sort(v.begin(), v.end()) ;
   int r = 0 ;
   for (int i=0; i<(int)v.size(); i++)
      if (i==0 || v[i] != 1+v[i-1])
         r += v[i] ;
   cout << r << endl ;
}
