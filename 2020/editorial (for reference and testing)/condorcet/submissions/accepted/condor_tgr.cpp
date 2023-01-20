#include <iostream>
#include <vector>
#include <algorithm>
using namespace std ;
using ll = long long ;
const int MAXN = 1024 ;
ll h2h[MAXN][MAXN] ;
vector<int> a, best ;
vector<ll> ac ;
ll r ;
int n, m ;
int check(ll rounds) {
   ll inc = 0 ;
   for (int i=0; i<(int)ac.size(); i++) {
      ll v = ac[i] ;
      ll e = -1 ;
      if ((v ^ e ^ rounds) & 1)
         e-- ;
      if (v - e > rounds)
         return 0 ;
      inc += rounds - v + e ;
   }
   if (inc >= 2 * rounds) {
      return 1 ;
   }
   return 0 ;
}
ll bscyc() {
   if (check(0))
      return 0 ;
   ll b = 1 ;
   while (b < r && !check(b))
      b += b ;
   ll b2 = b >> 2 ;
   while (b2) {
      if (check(b-b2))
         b -= b2 ;
      b2 >>= 1 ;
   }
   if (b > 2 && check(b - 2))
      b -= 2 ;
   return b ;
}
int cyclehead(int st, int at) {
   ac.clear() ;
   int i = st ;
   int j = st ;
   do {
      ac.push_back(h2h[i][a[i]]) ;
      i = a[i] ;
      if (i > st)
         return 0 ;
      j = a[a[j]] ;
   } while (i != j) ;
   return i == st ;
}
void consider() {
   ll hi = 0 ;
   for (int i=0; i<n; i++)
      hi = max(hi, 1+h2h[i][a[i]]) ;
   if (hi >= r)
      return ;
   for (int i=0; i<n; i++)
      if (cyclehead(i, i)) {
         hi = max(hi, bscyc()) ;
         if (hi >= r)
            return ;
      }
   if (hi < r) {
      best = a ;
      r = hi ;
   }
}
void recur(int togo) {
   if (togo == 0) {
      consider() ;
      return ;
   }
   int i = n - togo ;
   for (int j=0; j<n; j++)
      if (i != j && (j > i || a[j] != i) && r > 1+h2h[i][j]) {
         a.push_back(j) ;
         recur(togo-1) ;
         a.pop_back() ;
      }
}
int main(int argc, char *argv[]) {
   cin >> n >> m ;
   for (int i=0; i<m; i++) {
      string s ;
      ll cnt ;
      cin >> s >> cnt ;
      for (int i=0; i<n; i++)
         for (int j=i+1; j<n; j++) {
            h2h[s[i]-'A'][s[j]-'A'] += cnt ;
            h2h[s[j]-'A'][s[i]-'A'] -= cnt ;
         }
   }
   r = 1000000000000000LL ;
   recur(n) ;
   cout << r << endl ;
   if (argc > 1) {
      for (int i=0; i<n; i++)
         cout << " " << best[i] ;
      cout << endl ;
   }
}
