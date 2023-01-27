import java.io.* ;
import java.util.* ;
public class Containment {
   /*
    *   Note:  a sparse-matrix flow algorithm would probably run
    *   a fair bit faster.
    */
   static int augment(int[][] g, int[][] now, int at, int sink, boolean[] seen,
               int sofar) {
      if (at == sink)
         return sofar ;
      if (seen[at])
         return 0 ;
      seen[at] = true ;
      for (int i=0; i<g.length; i++)
         if (now[at][i] < g[at][i]) {
            int t = augment(g, now, i, sink, seen,
                            Math.min(sofar, g[at][i]-now[at][i])) ;
            if (t > 0) {
               now[at][i] += t ;
               now[i][at] -= t ;
               return t ;
            }
         }
      return 0 ;
   }
   static int[][] flow(int[][] g, int src, int sink) {
      int[][] now = new int[g.length][g[0].length] ;
      while (augment(g, now, src, sink, new boolean[g.length],
                                                        1000000000) > 0) ;
      return now ;
   }
   static int ind(int x, int y, int z) throws Exception {
      if (x < 0 || y < 0 || z < 0 || x > 9 || y > 9 || z > 9)
         throw new Exception("Data out of range " + x + " " + y + " " + z) ;
      return x + 10 * (y + 10 * z) ;
   }
   static void connect2(int[][] g, int g1, int g2) {
      g[g1][g2] = g[g2][g1] = 1 ;
   }
   // note: this *accumulates*
   static void connect1(int[][] g, int g1, int g2, int quant) {
      g[g1][g2] += quant ;
   }
   public static void main(String[] args) throws Exception {
      Scanner sc = new Scanner(System.in) ;
      int N = sc.nextInt() ;
      for (int kase=1; kase<=N; kase++) {
         int gn = 1000 + 2 ;
         int src = 1000 ;
         int sink = 1001 ;
         int[][] flow = new int[gn][gn] ;
         for (int x=0; x<10; x++)
            for (int y=0; y<10; y++) {
               for (int z=0; z<9; z++) {
                  connect2(flow, ind(x,y,z), ind(x,y,z+1)) ;
                  connect2(flow, ind(y,z,x), ind(y,z+1,x)) ;
                  connect2(flow, ind(z,x,y), ind(z+1,x,y)) ;
               }
               connect1(flow, ind(x,y,0), sink, 1) ;
               connect1(flow, ind(x,y,9), sink, 1) ;
               connect1(flow, ind(x,0,y), sink, 1) ;
               connect1(flow, ind(x,9,y), sink, 1) ;
               connect1(flow, ind(0,x,y), sink, 1) ;
               connect1(flow, ind(9,x,y), sink, 1) ;
            }
         int R = sc.nextInt() ;
         for (int i=0; i<R; i++) {
            int x = sc.nextInt() ;
            int y = sc.nextInt() ;
            int z = sc.nextInt() ;
            connect1(flow, src, ind(x,y,z), 6) ;
         }
         int[][] r = flow(flow, src, sink) ;
         int sum = 0 ;
         for (int i=0; i<gn; i++)
            sum += r[src][i] ;
         System.out.println(sum) ;
      }
   }
}
