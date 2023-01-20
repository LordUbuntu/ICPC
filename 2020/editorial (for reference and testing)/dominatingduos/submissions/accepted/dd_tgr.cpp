#include <iostream>
#include <vector>
using namespace std ;
int main() {
   int N ;
   cin >> N ;
   vector<int> a ;
   int r = 0 ;
   for (int i=0; i<N; i++) {
      int v ;
      cin >> v ;
//    cout << "At " << i << " adding " << min((int)a.size(), (int)(1 + lower_bound(a.rbegin(), a.rend(), v) - a.rbegin())) << " size " << a.size() << endl ;
      r += min((int)a.size(), (int)(1 + lower_bound(a.rbegin(), a.rend(), v) - a.rbegin())) ;
      while (a.size() && a[a.size()-1] < v)
         a.pop_back() ;
      a.push_back(v) ;
   }
   cout << r << endl ;
}
