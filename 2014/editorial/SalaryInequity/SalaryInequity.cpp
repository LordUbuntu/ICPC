#include <iostream>
#include <cstdlib>
using namespace std ;
const int MAXN = 1000001 ;
const int LOG2RADIX = 10 ;
const int RADIX = 1<<LOG2RADIX ;
const int RADIXMASK = RADIX-1 ;
struct emp {
   int sup, subcnt, id, ticket ;
} empdata[MAXN+1] ; // this is 1-indexed
int salaries[MAXN] ;
struct cache {
   int bonus, hi, lo ;
   char valid ;
} cache[RADIX] ;
int H(int v) {
   return v >> LOG2RADIX ;
}
void refresh(int ch) {
   struct cache &c = cache[ch] ;
   int start = ch * RADIX ;
   int hi = salaries[start] ;
   int lo = hi ;
   for (int i=1; i<RADIX; i++) {
      int s = salaries[i+start] ;
      hi = max(hi, s) ;
      lo = min(lo, s) ;
   }
   c.lo = lo ;
   c.hi = hi ;
   c.valid = 1 ;
}
int main(int argc, char *argv[]) {
   int T ;
   cin >> T ;
   while (T-- > 0) {
      int N ;
      cin >> N ;
      for (int i=1; i<=N; i++) {
         emp &e = empdata[i] ;
         e.sup = 0 ;
         e.subcnt = 1 ;
         e.ticket = 0 ;
         e.id = 0 ;
         if (i > 1)
            cin >> e.sup ;
      }
      for (int i=N; i>1; i--) {
         emp &e = empdata[i] ;
         emp &s = empdata[e.sup] ;
         s.subcnt += e.subcnt ;
      }
      empdata[1].ticket = 1 ;
      for (int i=2; i<=N; i++) {
         emp &e = empdata[i] ;
         emp &s = empdata[e.sup] ;
         e.id = s.ticket ;
         e.ticket = e.id + 1 ;
         s.ticket += e.subcnt ;
      }
      for (int i=1; i<=N; i++) {
         emp &e = empdata[i] ;
         cin >> salaries[e.id] ;
      }
      memset(cache, 0, sizeof(cache)) ;
      int Q ;
      cin >> Q ;
      for (int q=0; q<Q; q++) {
         char c ;
         int ebadge ;
         cin >> c ;
         cin >> ebadge ;
         emp &e = empdata[ebadge] ;
         int id = e.id ;
         int start = e.id ;
         int end = start + e.subcnt ;
         int firstscan = RADIXMASK & -start ;
         int firstchunk = H(start + RADIXMASK) ;
         int lastchunk = H(end) ;
         if (H(end) == H(start)) {
            firstscan = end - start ;
            end = lastchunk*RADIX ;
         }
         if (c == 'Q') {
            int hi = salaries[id] + cache[H(id)].bonus ;
            int lo = hi ;
            for (int i=start; i<start+firstscan; i++) {
               int s = salaries[i]+cache[H(i)].bonus ;
               lo = min(lo, s) ;
               hi = max(hi, s) ;
            }
            for (int ch=firstchunk; ch<lastchunk; ch++) {
               if (!cache[ch].valid)
                  refresh(ch) ;
               lo = min(lo, cache[ch].lo + cache[ch].bonus) ;
               hi = max(hi, cache[ch].hi + cache[ch].bonus) ;
            }
            for (int i=lastchunk*RADIX; i<end; i++) {
               int s = salaries[i]+cache[H(i)].bonus ;
               lo = min(lo, s) ;
               hi = max(hi, s) ;
            }
            cout << (hi - lo) << endl ;
         } else if (c == 'R') {
            int raise = 0 ;
            cin >> raise ;
            if (firstscan)
               cache[firstchunk-1].valid = 0 ;
            for (int i=start; i<start+firstscan; i++)
               salaries[i] += raise ;
            for (int ch=firstchunk; ch<lastchunk; ch++)
               cache[ch].bonus += raise ;
            if (end > lastchunk*RADIX)
               cache[lastchunk].valid = 0 ;
            for (int i=lastchunk*RADIX; i<end; i++)
               salaries[i] += raise ;
         } else {
            cerr << "Bad command " << c << endl ;
            exit(10) ;
         }
      }
   }
}
