#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
using namespace std ;
int main() {
   string perm, numb ;
   int cnts[9][9] ;
   cin >> numb ;
   for (int i=0; i<9; i++)
      for (int j=0; j<9; j++)
         cnts[i][j] = 0 ;
   int prev = numb[0] - '1' ;
   for (int i=1; i<(int)numb.size(); i++) {
      int loc = numb[i] - '1' ;
      cnts[min(prev, loc)][max(prev, loc)]++ ;
      prev = loc ;
   }
   for (int i=0; i<9; i++)
      perm.push_back(i) ;
   int r = numb.size() * 100 + 1000 ;
   do {
      int t = perm[numb[0]-'1'] ;
      for (int i=0; i<9; i++)
         for (int j=i+1; j<9; j++)
            t += cnts[i][j] * abs(perm[i]-perm[j]) ;
      r = min(r, t) ;
   } while (next_permutation(perm.begin(), perm.end())) ;
   r += numb.size() ;
   cout << r << endl ;
}
