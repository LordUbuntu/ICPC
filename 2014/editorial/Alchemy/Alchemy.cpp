#include <iostream>
#include <vector>
#include <algorithm>
using namespace std ;
struct circ {
   circ(int i_, int x_, int y_, int r_, int a_, int b_) :
      id(i_), x(x_), y(y_), r(r_), a(a_), b(b_), anc(0),
      parent(0), leasthi(0), hibenefit(0), currbenefit(0), fireme(0),
      disabled(0), fired(0) {} ;
   bool encloses(circ &b) const {
      return r > b.r && (b.x-x)*(b.x-x)+(b.y-y)*(b.y-y) < r*r ;
   }
   int setParent(circ &par_) {
      parent = &par_ ;
      anc = parent->anc + 1 ;
      int benefit = 0 ;
      hibenefit = 0 ;
      for (int k=0; ; k++) {
         if (benefit > hibenefit) {
            hibenefit = benefit ;
            leasthi = k ; // last chance to fire
         }
         if (k == anc)
            break ;
         benefit += (k & 1) ? b : a ;
      }
      currbenefit = benefit ;
      return hibenefit ;
   }
   void fire() {
      fireme = 1 ;
      currbenefit -= (anc & 1) ? a : b ;
      anc-- ;
   }
   int id, x, y, r, a, b, anc, leasthi, hibenefit, currbenefit ;
   circ *parent ;
   char fireme, disabled, fired ;
} ;
bool operator<(circ a, circ b) {
   if (a.r != b.r)
      return a.r < b.r ;
   if (a.x != b.x)
      return a.x < b.x ;
   return a.y < b.y ;
}
int main(int argc, char *argv[]) {
   int T ;
   cin >> T ;
   while (T-- > 0) {
      int N ;
      cin >> N ;
      vector<circ> circs ;
      for (int i=0; i<N; i++) {
         int x, y, r, a, b ;
         cin >> x >> y >> r >> a >> b ;
         circs.push_back(circ(i+1, x, y, r, a, b)) ;
      }
      sort(circs.begin(), circs.end()) ;
      int r = 0 ;
      for (int i=circs.size()-1; i>=0; i--) {
         for (int j=i+1; j<circs.size(); j++)
            if (circs[j].encloses(circs[i])) {
               r += circs[i].setParent(circs[j]) ;
               break ;
            }
      }
      cout << r << endl ;
      for (int i=0; i<circs.size(); i++) {
         int best = -1 ;
         // first, bottom up, disable parents whose children are critical
         // and find best node to fire.
         // also reset fireme
         for (int j=0; j<circs.size(); j++) {
            circ &c = circs[j] ;
            c.fireme = 0 ;
            if (c.disabled) {
               if (c.parent)
                  c.parent->disabled = 1 ;
               continue ;
            }
            if (c.anc == c.leasthi && c.fired == 0)
               if (c.parent)
                  c.parent->disabled = 1 ;
            if (c.currbenefit == c.hibenefit &&  c.fired == 0 &&
                (best < 0 || circs[best].id > c.id))
               best = j ;
         }
         if (i != 0)
            cout << " " ;
         cout << circs[best].id ;
         circs[best].fireme = 1 ;
         circs[best].fired = 1 ;
         // top down; fire and make sure children know
         // also reset disabled
         for (int j=circs.size()-1; j>=0; j--) {
            circ &c = circs[j] ;
            c.disabled = 0 ;
            if (c.parent && c.parent->fireme)
               c.fire() ;
         }
      }
      cout << endl ;
   }
}
