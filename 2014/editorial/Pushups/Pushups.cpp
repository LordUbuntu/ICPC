#include <iostream>
using namespace std ;
const int MAXP = 11000 ;
const int MAXC = 20 ;
char dp[MAXP+1][MAXP+1] ;
int a[MAXC] ;
int main(int argc, char *argv[]) {
   int t ;
   cin >> t ;
   while (t--) {
      int n, c ;
      cin >> n >> c ;
      for (int i=0; i<c; i++)
         cin >> a[i] ;
      int hic = a[0] ;
      for (int i=1; i<c; i++)
         if (a[i] > hic)
            hic = a[i] ;
      for (int i=0; i<=n; i++)
         for (int j=0; j<=n; j++)
            dp[i][j] = 0 ;
      dp[0][0] = 1 ;
      for (int i=0; i<n; i++)
         for (int j=0; j<n; j++)
            if (dp[i][j])
               for (int k=0; k<c; k++)
                  dp[i+j+a[k]][j+a[k]] = 1 ;
      int hi = -1 ;
      for (int i=0; i<=n; i++)
         if (dp[n][i])
            hi = i ;
      cout << hi << endl ;
   }
}
