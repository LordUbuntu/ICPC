import java.util.* ;
public class StampStamp {
   static class Point {
      Point(int x_, int y_) {
         x = x_ ;
         y = y_ ;
      }
      public int x, y ;
      boolean left(Point p1, Point p2) {
         return (p1.x-x)*(p2.y-y)-(p1.y-y)*(p2.x-x) >= 0 ;
      }
      Point flip() {
         return new Point(-x, y) ;
      }
      Point neg() {
         return new Point(-x, -y) ;
      }
      Point mul(int v) {
         return new Point(x*v, y*v) ;
      }
      public String toString() {
         return "(" + x + "," + y + ")" ;
      }
      public boolean equals(Object o) {
         Point p = (Point)o ;
         return p.x == x && p.y == y ;
      }
      public int hashCode() {
         return x * 65537 + y ;
      }
   }
   static void considerHull(Vector<Point> hull, Point pt) {
      int at = hull.size() - 2 ;
      while (at >= 0 && hull.get(at).left(hull.get(at+1), pt)) {
         hull.remove(at+1) ;
         at-- ;
      }
      hull.add(pt) ;
   }
   // not a normal gcd; handles zeros and negatives
   static int gcd(int a, int b) {
      if (a < 0)
         a = - a ;
      if (b < 0)
         b = - b ;
      if (a == 0 && b == 0)
         throw new IllegalArgumentException("No double zeros please") ;
      if (a == 0)
         return b ;
      if (b == 0)
         return a ;
      while (a != 0) {
         int t = b % a ;
         b = a ;
         a = t ;
      }
      return b ;
   }
   static void adddir(HashMap<Point, Integer> hm, Point pt1, Point pt2) {
      int dx = pt2.x - pt1.x ;
      int dy = pt2.y - pt1.y ;
      int g = gcd(dx, dy) ;
      hm.put(new Point(dx/g, dy/g), g) ;
   }
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in) ;
      int kases = sc.nextInt() ;
      for (int kase=1; kase<=kases; kase++) {
         int h = sc.nextInt() ;
         int w = sc.nextInt() ;
         int[] left = new int[h] ;
         int[] right = new int[h] ;
         char[][] b = new char[h][] ;
         Vector<Point> lefthull = new Vector<Point>() ;
         Vector<Point> righthull = new Vector<Point>() ;
         int pts = 0 ;
         int mini = h ;
         int maxi = 0 ;
         for (int i=0; i<h; i++) {
            b[i] = sc.next().toCharArray() ;
            left[i] = right[i] = -1 ;
            int j = 0 ;
            while (j < w && b[i][j] == '.')
               j++ ;
            if (j < w) {
               left[i] = j ;
               j = w-1 ;
               while (b[i][j] == '.')
                  j-- ;
               right[i] = j ;
               for (j=left[i]; j<=right[i]; j++)
                  if (b[i][j] == '#')
                     pts++ ;
               maxi = i ;
               if (mini == h)
                  mini = i ;
               considerHull(lefthull, new Point(left[i], i)) ;
               considerHull(righthull, new Point(right[i], i).flip()) ;
            }
         }
         if (pts < 2) {
            System.out.println(pts) ;
            continue ;
         }
         HashMap<Point, Integer> dirs = new HashMap<Point, Integer>() ;
         if (left[mini] != right[mini])
            adddir(dirs, new Point(left[mini], mini), new Point(right[mini], mini)) ;
         for (int i=0; i+1<lefthull.size(); i++)
            adddir(dirs, lefthull.get(i), lefthull.get(i+1)) ;
         if (left[maxi] != right[maxi])
            adddir(dirs, new Point(right[maxi], maxi), new Point(left[maxi], maxi)) ;
         for (int i=righthull.size()-1; i>0; i--)
            adddir(dirs, righthull.get(i).flip(), righthull.get(i-1).flip()) ;
         int best = pts ;
         for (Point dir : dirs.keySet()) {
            if (dir.x >= 0 && (dir.x != 0 || dir.y >= 0) &&
                dirs.get(dir.neg()) != null) {
               int cnt = Math.min(dirs.get(dir), dirs.get(dir.neg())) ;
               int rover = 0 ;
               for (int i=1; i<=cnt; i++) {
                  Point mydir = dir.mul(i) ;
                  int needed = 0 ;
searchloop:
                  for (int ii=0; ii<w; ii++) {
                     for (int jj=0; jj<h; jj++) {
                        // previous point must be off board
                        if (ii - mydir.x < 0 ||
                            jj - mydir.y < 0 || jj - mydir.y >= h) {
                           int y = jj ;
                           int x = ii ;
                           int run = 0 ;
                           while (true) {
                              char c = 'b' ;
                              if (x < w && y >= 0 && y < h)
                                 c = b[y][x] ;
                              if (c == '#') {
                                 run++ ;
                              } else {
                                 if (run == 1) {
                                    needed = pts ;
                                    break searchloop ;
                                 }
                                 needed += (run + 1) >> 1 ;
                                 run = 0 ;
                              }
                              if (c == 'b')
                                 break ;
                              x += mydir.x ;
                              y += mydir.y ;
                           }
                           if (needed >= best)
                              break searchloop ;
                        }
                     }
                  } 
                  if (needed < best)
                     best = needed ;
               }
            }
         }
         System.out.println(best) ;
      }
   }
}
