#include <iostream>
#include <vector>
using namespace std ;
int main() {
   int N ;
   (void)scanf("%d", &N) ;
   vector<int> a ;
   int r = 0 ;
   for (int i=0; i<N; i++) {
      int v ;
      (void)scanf("%d", &v) ;
      int osize = a.size() ;
      while (a.size() && a[a.size()-1] < v)
         a.pop_back() ;
      r += min(osize, (int)(1+osize-a.size())) ;
      a.push_back(v) ;
   }
   cout << r << endl ;
}
