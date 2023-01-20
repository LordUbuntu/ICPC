#include <iostream>
#include <algorithm>
#include <vector>
using namespace std ;
using ll = long long ;
vector<ll> v ;
ll cost(int a, int b) {
   if (b <= a)
      return 0 ;
   double sum = 0 ;
   for (int i=a; i<b; i++)
      sum += v[i] ;
   double av = sum / (b - a) ;
   int p = a ;
   for (int i=a+1; i<b; i++)
      if (abs(v[i]-av) < abs(v[p]-av))
         p = i ;
   sum = 0 ;
   for (int i=a; i<b; i++)
      sum += (v[i]-v[p])*(v[i]-v[p]) ;
   return sum ;
}
int main() {
   int n ;
   cin >> n ;
   v.resize(n) ;
   for (auto &vv : v)
      cin >> vv ;
   sort(v.begin(), v.end()) ;
   ll r = 1000000000000000000LL ;
   for (int m=0; m<=n; m++)
      r = min(r, cost(0, m)+cost(m, n)) ;
   cout << r << endl ;
}
