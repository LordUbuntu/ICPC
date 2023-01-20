#include <iostream>
#include <algorithm>
#include <vector>
using namespace std ;
using ll = long long ;
int main() {
   int n, c ;
   cin >> n >> c ;
   vector<ll> s(n) ;
   for (auto &v : s)
      cin >> v ;
   sort(s.begin(), s.end()) ;
   vector<char> p(c+1) ;
   ll r = 0 ;
   for (int i=0; i<(int)p.size(); i++)
      p[i] = 0 ;
   p[0] = 1 ;
   ll force = 0 ;
   for (auto v : s)
      force += v ;
   int rover = s.size() - 1 ;
   for (r=c; r>0; r--) {
      while (rover >= 0 && s[rover] == r + 1) {
         force -= r+1 ;
         for (ll j=c; j>=r+1; j--)
            p[j] |= p[j-r-1] ;
         rover-- ;
      }
      if (c - force - r >= 0 && p[c-force-r])
         break ;
   }
   cout << (c-r) << endl ;
}
