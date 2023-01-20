#include <iostream>
#include <algorithm>
#include <vector>
using namespace std ;
using ll = long long ;
const int SH = 9 ;
int main() {
   ll n, r = 0 ;
   cin >> n ;
   vector<int> v(n), cnt2(1+(n>>SH)) ;
   for (int i=0; i<n; i++)
      cin >> v[i] ;
   for (int i=0; i<(n>>SH); i++)
      cnt2[i] = 1LL << SH ;
   cnt2[(n-1)>>SH] = n & (1LL << SH) ;
   vector<pair<int, int> > sortme(n) ;
   for (int i=0; i<n; i++)
      sortme[i] = {v[i], i} ;
   sort(sortme.begin(), sortme.end()) ;
   int left = n - 1 ;
   for (auto [val, j]: sortme) {
      ll oneside = 0, i = 0 ;
      while (i < (j >> SH))
         oneside += cnt2[i++] ;
      for (i<<=SH; i<j; i++)
         oneside += (v[i] > val) ;
      r += min(oneside, left-oneside) ;
      left-- ;
      cnt2[j>>SH]-- ;
   }
   cout << r << endl ;
}
