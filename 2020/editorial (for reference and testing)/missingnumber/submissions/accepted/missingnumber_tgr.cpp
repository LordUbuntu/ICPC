#include <iostream>
using namespace std ;
int main() {
   int n ;
   string s ;
   cin >> n >> s ;
   for (int i=s.size()-1; ;)
      for (int v=n--; v; v /= 10)
         if (i < 0 || s[i--] != '0' + v % 10)
            cout << n+1 << endl, exit(0) ;
}
