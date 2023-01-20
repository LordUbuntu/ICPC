#include <iostream>
#include <string>
using namespace std ;
int main() {
   string s ;
   cin >> s ;
   int dups = 0 ;
   for (int i=0; i<(int)s.size(); i++)
      for (int j=i+1; j<(int)s.size(); j++)
         if (s[i] == s[j])
            dups++ ;
   if (dups <= 0) // change 0 to 1 for "if she swaps, can you determine where"
      cout << 1 << endl ;
   else
      cout << 0 << endl ;
}
