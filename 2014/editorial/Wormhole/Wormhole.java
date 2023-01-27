import java.util.* ;
public class Wormhole {
   static double d2(int dx, int dy, int dz) {
      return Math.sqrt(dx*(double)dx + dy*(double)dy + dz*(double)dz) ;
   }
   public static void main(String[] args) throws Exception {
      Scanner sc = new Scanner(System.in) ;
      int T = sc.nextInt() ;
      for (int kase=1; kase<=T; kase++) {
         System.out.println("Case " + kase + ":") ;
         int P = sc.nextInt() ;
         HashMap<String, Integer> id = new HashMap<String, Integer>() ;
         int x[] = new int[P] ;
         int y[] = new int[P] ;
         int z[] = new int[P] ;
         for (int i=0; i<P; i++) {
            String nam = sc.next() ;
            id.put(nam, i) ;
            x[i] = sc.nextInt() ;
            y[i] = sc.nextInt() ;
            z[i] = sc.nextInt() ;
         }
         double d[][] = new double[P][P] ;
         for (int i=0; i<P; i++)
            for (int j=i+1; j<P; j++)
               d[j][i] = d[i][j] = d2(x[i]-x[j], y[i]-y[j], z[i]-z[j]) ;
         int W = sc.nextInt() ;
         for (int i=0; i<W; i++) {
            int p1 = id.get(sc.next()) ;
            int p2 = id.get(sc.next()) ;
            d[p1][p2] = 0 ;
         }
         for (int k=0; k<P; k++)
            for (int i=0; i<P; i++)
               for (int j=0; j<P; j++)
                  if (d[i][k]+d[k][j] < d[i][j])
                     d[i][j] = d[i][k]+d[k][j] ;
         int Q = sc.nextInt() ;
         for (int i=0; i<Q; i++) {
            String p1s = sc.next() ;
            String p2s = sc.next() ;
            System.out.println("The distance from " + p1s + " to " + p2s +
               " is " +
              (int)Math.floor(d[id.get(p1s)][id.get(p2s)]+0.5) + " parsecs.") ;
         }
      }
   }
}
