#include <iostream>
using namespace std ;
int main() {
   int n, k, v, sum=0 ;
   cin >> n >> k ;
   for (int i=0; i<k; i++) {
      cin >> v ;
      sum += v ;
   }
   cout.precision(9);
   cout << (sum - 3 * (n - k)) / (double)n << " " <<
           (sum + 3 * (n - k)) / (double)n << endl ;
}
