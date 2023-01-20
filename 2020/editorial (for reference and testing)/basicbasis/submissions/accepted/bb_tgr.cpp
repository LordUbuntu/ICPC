#include <iostream>
#include <vector>
#include <string>
#include <strings.h>
#include <string.h>
using namespace std ;
using ll = long long ;
using ull = unsigned long long ;
ull unhex[256] ;
void readv(vector<vector<ull> > &v, int k, int kk) {
   vector<ull> r(kk) ;
   string s ;
   cin >> s ;
   for (int i=0; i<k; i++)
      r[i>>4] |= (unhex[(int)s[i]] << (4 * (i & 15))) ;
   v.push_back(r) ;
}
int findbit(const vector<ull> &b) {
   int st = 0 ;
   while (st < (int)b.size() && b[st] == 0)
      st++ ;
   if (st >= (int)b.size())
      return -1 ;
   return st * 64 + ffsll(b[st]) - 1 ;
}
void xorit(vector<ull> &dst, const vector<ull> &src) {
   for (int i=0; i<(int)dst.size(); i++)
      dst[i] ^= src[i] ;
}
int main() {
   int n, m, k ;
   for (int i='0'; i<='9'; i++)
      unhex[i] = i - '0' ;
   for (int i='a'; i<='f'; i++)
      unhex[i] = i - 'a' + 10 ;
   cin >> n >> m >> k ;
   int kk = (k + 15) >> 4 ;
   vector<vector<ull> > a, b ;
   for (int i=0; i<n; i++)
      readv(b, k, kk) ;
   for (int i=0; i<m; i++)
      readv(a, k, kk) ;
   vector<int> hibitloc(4*k) ;
   for (auto &v: hibitloc)
      v = -1 ;
   int zeroanswer = -1 ;
   for (int i=0; i<(int)b.size(); i++) {
      int t = findbit(b[i]) ;
      while (t >= 0 && hibitloc[t] >= 0) {
         xorit(b[i], b[hibitloc[t]]) ;
         t = findbit(b[i]) ;
      }
      if (t >= 0)
         hibitloc[t] = i ;
      else if (zeroanswer < 0)
         zeroanswer = i+1 ;
   }
   for (int i=0; i<(int)a.size(); i++) {
      int r = 0 ;
      int t = findbit(a[i]) ;
      if (t < 0)
         r = zeroanswer ;
      else {
         while (t >= 0 && hibitloc[t] >= 0) {
            r = max(r, hibitloc[t]) ;
            xorit(a[i], b[hibitloc[t]]) ;
            t = findbit(a[i]) ;
         }
         if (t >= 0)
            r = -1 ;
         else
            r++ ;
      }
      cout << r << endl ;
   }
}
