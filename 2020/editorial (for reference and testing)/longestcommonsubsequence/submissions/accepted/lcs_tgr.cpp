#include <iostream>
#include <string>
using namespace std ;
int cnt[26][26] ;
int main() {
   int n, k ;
   cin >> n >> k ;
   string s ;
   for (int i=0; i<n; i++) {
      cin >> s ;
      for (int j=0; j<k; j++)
         for (int m=j+1; m<k; m++)
            cnt[s[j]-'A'][s[m]-'A']++ ;
   }
   int r = 0 ;
   for (int i=0; i<k; i++)
      for (int j=0; j<k; j++)
         r = max(r, cnt[i][j] = (cnt[i][j] == n)) ;
   for (int m=0; m<k; m++)
      for (int i=0; i<k; i++)
         for (int j=0; j<k; j++)
            if (cnt[i][m] && cnt[m][j] && cnt[i][m] + cnt[m][j] > cnt[i][j])
               r = max(r, (cnt[i][j] = cnt[i][m] + cnt[m][j])) ;
   cout << (1+r) << endl ;
}
