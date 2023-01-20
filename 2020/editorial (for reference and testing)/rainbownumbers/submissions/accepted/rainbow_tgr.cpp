#include <iostream>
#include <string>
#include <algorithm>
using namespace std ;
using ll = long long ;
const ll MOD = 998244353LL ;
ll sum(const string &R) {
   ll r = 0 ;
   ll cnt = 9 ;
   int digs = 1 ;
   while (digs < (int)R.size()) {
      r = (r + cnt) % MOD ;
      cnt = (cnt * 9) % MOD ;
      digs++ ;
   }
   ll less = 0 ;
   ll eq = 1 ;
   int pdig = 10 ;
   int low = 1 ;
   for (int i=0; i<(int)R.size(); i++) {
      int cdig = R[i] - '0' ;
      less = (9 * less) % MOD ;
      if (cdig > low) {
         if (cdig <= pdig)
            less += (cdig - low) * eq ;
         else
            less += (cdig - low - 1) * eq ;
      }
      if (cdig == pdig)
         eq = 0 ;
      pdig = cdig ;
      low = 0 ;
   }
   r = (r + less) % MOD ;
   return r ;
}
int main() {
   string L, R ;
   cin >> L >> R ;
   string R2 ;    // "increment" R
   int carry = 1 ;
   for (int i=R.size()-1; i>=0; i--) {
      carry += R[i]-'0' ;
      R2.push_back(carry%10+'0') ;
      carry /= 10 ;
   }
   if (carry)
      R2.push_back(carry+'0') ;
   reverse(R2.begin(), R2.end()) ;
   swap(R, R2) ;
   ll r = sum(R) - sum(L) ;
   r = (r + MOD) % MOD ;
   cout << r << endl ;
}
