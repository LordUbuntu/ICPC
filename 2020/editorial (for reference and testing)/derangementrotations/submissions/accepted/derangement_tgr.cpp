#include <iostream>
#include <vector>
#include <algorithm>
using namespace std ;
using ll = long long ;
ll n, mod ;
ll expo(ll a, ll n) {
   n %= (mod-1) ;
   ll r = 1 ;
   while (n) {
      if (n & 1)
         r = (r * a) % mod ;
      a = (a * a) % mod ;
      n >>= 1 ;
   }
   return r ;
}
ll phi(ll v) {
   ll exp = 1 ;
   if (v % 2 == 0) {
      v >>= 1 ;
      while (v % 2 == 0) {
         v >>= 1 ;
         exp = exp * 2 ;
      }
   }
   for (ll p=3; p*p<=v; p += 2) {
      if (v % p == 0) {
         v /= p ;
         exp = exp * (p-1) ;
         while (v % p == 0) {
            v /= p ;
            exp = exp * p ;
         }
      }
   }
   if (v > 1)
      exp = exp * (v - 1) ;
   return exp ;
}
int main() {
   cin >> n >> mod ;
   ll r = 0 ;
   for (ll f=2; f*f<=n; f++)
      if (n % f == 0) {
         ll f2 = n / f ;
         r = (r + (expo(2, f-1) - 1) * phi(f2)) % mod ;
         if (f2 != f)
            r = r + ((expo(2, f2-1) - 1) * phi(f)) % mod ;
      }
   r = r * (n - 2) % mod ;
   r = (r + mod) % mod ;
   cout << r << endl ;
}
