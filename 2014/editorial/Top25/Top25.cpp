#include <map>
#include <string>
#include <iostream>
using namespace std ;
int main(int argc, char *argv[]) {
   int T ;
   cin >> T ;
   while (T-- > 0) {
      map<string, int> firstset ;
      int N ;
      cin >> N ;
      string s ;
      for (int i=0; i<N; i++) {
         cin >> s ;
         firstset[s] = i ;
      }
      int first = 0 ;
      int last = 0 ;
      for (int i=0; i<N; i++) {
         cin >> s ;
         int loc = firstset[s] ;
         if (loc > last)
            last = loc ;
         if (last == i) {
            if (first)
               cout << " " ;
            cout << (last-first+1) ;
            first = i + 1 ;
            last = first ;
         }
      }
      cout << endl ;
   }
}
