#include <iostream>
#include <vector>
using namespace std ;
int main() {
   int n, k ;
   cin >> n >> k ;
   vector<int> tests ;
   for (int i=0; i<n; i++) {
      string s ;
      cin >> s ;
      int v = 0 ;
      for (int j=0; j<(int)s.size(); j++)
         if (s[j] == 'T')
            v |= 1LL << j ;
      tests.push_back(v) ;
   }
   vector<int> bc(1LL << k) ;
   for (int i=1; (i>>k)==0; i++)
      bc[i] = bc[i & (i-1)] + 1 ;
   int r = 0 ;
   for (int i=0; (i>>k)==0; i++) {
      int r2 = k ;
      for (auto t : tests)
         r2 = min(r2, bc[t ^ i]) ;
      r = max(r, r2) ;
   }
   cout << r << endl ;
}
