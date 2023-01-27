#include <iostream>
#include <cstdlib>
#include <string>
using namespace std ;
const int HASHSIZE=26*26*26*2 ;
int cnts[HASHSIZE] ;
int bhash(const char *s) {
   if (*s == 0)
      return 1 ;
   else
      return *s - 'A' + 26 * bhash(s+1) ;
}
int main(int argc, char *argv[]) {
   int T ;
   cin >> T ;
   string s ;
   while (T--) {
      int B ;
      cin >> B ;
      char alive[26] ;
      memset(cnts, 0, sizeof(cnts)) ;
      memset(alive, 0, sizeof(alive)) ;
      int tot = 0 ;
      for (int i=0; i<B; i++) {
         cin >> s ;
         const char *p = s.c_str() ;
         cnts[bhash(p)]++ ;
         while (*p)
            alive[*p++ - 'A'] = 1 ;
      }
      for (int trial=0; ; trial++) {
         if (trial)
            cout << " -> " ;
         int tot = 0 ;
         int votes[26] ;
         memset(votes, 0, sizeof(votes)) ;
         for (int i=26; i<HASHSIZE;)
            for (int j=0; j<26; j++, i++) {
               votes[j] += cnts[i] ;
               tot += cnts[i] ;
            }
         if (tot == 0) {
            cout << "no winner" ;
            goto nextcase ;
         }
         for (int i=0; i<26; i++)
            if (votes[i] * 2 > tot) {
               cout << (char)('A'+i) ;
               goto nextcase ;
            }
         int mini = -1 ;
         for (int i=0; i<26; i++)
            if (alive[i] && (mini < 0 || votes[i] < votes[mini]))
               mini = i ;
         for (int j=0; j<26; j++)
            if (alive[j] && votes[j] == votes[mini]) {
               cout << (char)('A'+j) ;
               alive[j] = 0 ;
               for (int hi=26; hi<HASHSIZE; hi++) {
                  if (cnts[hi] == 0)
                     continue ;
                  int lo = hi ;
                  if (lo >= 26 * 26 * 26 && lo / (26 * 26) % 26 == j)
                     lo = 26 * 26 + lo % (26 * 26) ;
                  if (lo >= 26 * 26 && lo / 26 % 26 == j)
                     lo = (lo / 26 / 26) * 26 + lo % 26 ;
                  if (lo >= 26 && lo % 26 == j)
                     lo /= 26 ;
                  if (lo != hi) {
                     cnts[lo] += cnts[hi] ;
                     cnts[hi] = 0 ;
                  }
               }
            }
      }
nextcase:
      cout << endl ;
   }
}
