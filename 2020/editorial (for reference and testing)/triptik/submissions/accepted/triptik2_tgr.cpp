#include <iostream>
#include <algorithm>
#include <vector>
using namespace std ;
using ll = long long ;
const int MAXK = 4 ;
int n ;
struct kset {
   kset(int v) { a[0] = v ; a[1] = n ; a[2] = n ; a[3] = n ; }
   kset() { a[0] = n ; a[1] = 1 ; a[2] = 1 ; a[3] = 1 ; }
   int a[MAXK] ;
} ;
void merge(const kset &a, const kset &b, kset &c) {
   int ao = 0 ;
   int bo = 0 ;
   for (int i=0; i<MAXK; i++) {
      int eq = -(a.a[ao] == b.a[bo]) ;
      int lt = -(a.a[ao] < b.a[bo]) ;
      c.a[i] = (lt & a.a[ao]) + ((~lt) & b.a[bo]) ;
      ao -= (lt | eq) ;
      bo -= ((~lt) | eq) ;
   }
}
vector<vector<kset>> st ;
vector<vector<kset>> bylev ;
int main(int argc, char *argv[]) {
   int k ;
   cin >> n >> k ;
   vector<ll> x(n) ;
   for (int i=0; i<n; i++) {
      cin >> x[i] ;
      x[i] *= 2 ;
   }
   reverse(x.begin(), x.end()) ;
   x.push_back(0) ;
   vector<pair<ll, int> > sortme ;
   for (int i=0; i<=n; i++)
      sortme.push_back({x[i], i}) ;
   sort(sortme.begin(), sortme.end()) ;
   st.resize(1) ;
   for (int i=0; i<=n; i++)
      st[0].push_back(kset(sortme[i].second)) ;
   st.resize(30) ;
   for (int b=1; (n+1)>>b; b++) {
      st[b].resize(n+2-(1<<b)) ;
      for (int j=0; j<(int)st[b].size(); j++)
         merge(st[b-1][j], st[b-1][j+(1<<(b-1))], st[b][j]) ;
   }
   vector<int> q ;
   vector<int> dist(32*(n+1), -1) ;
   q.push_back(32*n+1) ;
   dist[32*n+1] = 0 ;
   vector<int> r(n+1, -1) ;
   vector<int> w ;
   for (int i=0; i<(int)sortme.size(); i++)
      w.push_back(sortme[i].first) ;
   kset empty(-1) ;
   bylev.resize(n+1) ;
   for (int qg=0; qg<(int)q.size(); qg++) {
      int at = q[qg] >> 5 ;
      int b = q[qg] & 31 ;
      if (bylev[at].size() == 0)
         bylev[at].resize(32, empty) ;
      if (bylev[at][b].a[0] < 0) {
         ll xx = x[at] ;
         ll lo = xx - (1LL << b) ;
         ll hi = xx + (1LL << b) ;
         auto loit = lower_bound(w.begin(), w.end(), lo) - w.begin() ;
         auto hiit = upper_bound(w.begin(), w.end(), hi) - w.begin() ;
         int bb = 0 ;
         while (loit + (2 << bb) < hiit)
            bb++ ;
         merge(st[bb][loit], st[bb][hiit-(1<<bb)], bylev[at][b]) ;
      }
      int d = dist[q[qg]] ;
      int atgood = 0 ;
      for (int jj=0; jj<k && bylev[at][b].a[jj]<n; jj++) {
         int j = bylev[at][b].a[jj] ;
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
            if (db > 0 || bylev[at][b].a[k-1] < n) {
               dist[32*at+b2] = d + 1 ;
               q.push_back(32*at+b2) ;
            }
         }
      }
   }
   reverse(r.begin(), r.end()) ;
   for (int i=1; i<=n; i++)
      cout << r[i] << endl ;
}
