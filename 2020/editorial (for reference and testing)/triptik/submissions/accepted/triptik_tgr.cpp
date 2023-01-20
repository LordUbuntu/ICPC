#include <iostream>
#include <math.h>
#include <algorithm>
#include <vector>
using namespace std ;
using ll = long long ;
vector<vector<vector<int>>> bylev ;
int main(int argc, char *argv[]) {
   int divi = 50 ;
   if (argc > 1)
      divi = atof(argv[1]) ;
   int n, k ;
   cin >> n >> k ;
   vector<ll> x(n) ;
   bylev.resize(n+1) ;
   for (int i=0; i<n; i++) {
      cin >> x[i] ;
      x[i] *= 2 ;
   }
   x.push_back(0) ;
   vector<pair<ll, int> > sortme ;
   for (int i=0; i<n; i++)
      sortme.push_back({x[i], i}) ;
   sort(sortme.begin(), sortme.end()) ;
   vector<vector<pair<ll, int> > > sortme2 ;
   vector<int> lims ;
   for (int sz=k; ; sz = (sz + 1 + sz / divi)) {
      lims.push_back(sz) ;
      sortme2.push_back({}) ;
      if (sz >= n)
         break ;
   }
   for (int i=0; i<n; i++) {
      int k = n - sortme[i].second - 1 ;
      for (int j=lims.size()-1; j>=0 && k<lims[j]; j--)
         sortme2[j].push_back(sortme[i]) ;
   }
   vector<int> q ;
   vector<int> dist(32*(n+1), -1) ;
   q.push_back(32*n+1) ;
   dist[32*n+1] = 0 ;
   vector<int> r(n+1, -1) ;
   vector<int> w ;
   int hib = 1 ;
   while (hib < (int)lims.size())
      hib *= 2 ;
   for (int qg=0; qg<(int)q.size(); qg++) {
      int at = q[qg] >> 5 ;
      int b = q[qg] & 31 ;
      if (bylev[at].size() == 0)
         bylev[at].resize(32) ;
      if (bylev[at][b].size() == 0) {
         ll xx = x[at] ;
         ll lo = xx - (1LL << b) ;
         ll hi = xx + (1LL << b) ;
         int good = lims.size()-1 ;
         for (ll bit=hib; bit; bit>>=1)
            if (good >= bit) {
               auto &sortme = sortme2[good-bit] ;
               pair<ll, int> lok({lo, -1}) ;
               pair<ll, int> hik({hi, n+1}) ;
               auto loit = lower_bound(sortme.begin(), sortme.end(), lok) ;
               auto hiit = lower_bound(sortme.begin(), sortme.end(), hik) ;
               int wcnt = hiit - loit ;
               if (wcnt >= k)
                  good -= bit ;
               if (wcnt == k) {
                  for (auto it=loit; it<hiit; it++)
                     bylev[at][b].push_back(it->second) ;
                  break ;
               }
            }
         if (bylev[at][b].size() == 0) {
            auto &sortme = sortme2[good] ;
            pair<ll, int> lok({lo, -1}) ;
            pair<ll, int> hik({hi, n+1}) ;
            auto loit = lower_bound(sortme.begin(), sortme.end(), lok) ;
            auto hiit = lower_bound(sortme.begin(), sortme.end(), hik) ;
            w.clear() ;
            while (loit < hiit)
               w.push_back(-loit++->second) ;
            nth_element(w.begin(), w.begin()+k, w.end()) ;
            for (int i=0; i<k && i<(int)w.size(); i++)
               bylev[at][b].push_back(-w[i]) ;
         }
      }
      int d = dist[q[qg]] ;
      int atgood = 0 ;
      for (auto j : bylev[at][b]) {
         if (dist[32*j+b] < 0) {
            dist[32*j+b] = d + 1 ;
            q.push_back(32*j+b) ;
         }
         atgood |= j==at ;
      }
      if (atgood && r[at] < 0)
         r[at] = d ;
      for (int db=-1; db<=1; db += 2) {
         int b2 = b + db ;
         if (b2 >= 0 && b2 < 32 && dist[32*at+b2] < 0) {
            if (db > 0 || (int)bylev[at][b].size() >= k) {
               dist[32*at+b2] = d + 1 ;
               q.push_back(32*at+b2) ;
            }
         }
      }
   }
   for (int i=0; i<n; i++)
      cout << r[i] << endl ;
}
