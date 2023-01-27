import java.util.* ;
public class Wrench {
   public static HashMap<String, String> lookup =
                                             new HashMap<String, String>() ;
   static void consider(String a, String b) {
      if (lookup.get(a) == null)
         lookup.put(a, b) ;
   }
   static void init() {
      for (int b=1; b<=128; b += b) {
         for (int v=0; v<=10*b; v++) {
            if (b == 1) {
               consider(""+v, ""+v) ;
               consider(""+v+".", ""+v) ;
            }
            int b10 = 1 ;
            for (int dig=0; dig<=6; dig++, b10 *= 10) {
               int frac = v % b ;
               int wh = v / b ;
               for (int xs=0; xs<2; xs++) {
                  String suff = ""+(b10 + (frac * b10 + xs * (b-1)) / b) ;
                  suff = suff.substring(1) ;
                  String fracs = "" ;
                  if (b != 1)
                     fracs = frac + "/" + b ;
                  if (wh == 0) {
                     consider("."+suff, "" + fracs) ;
                     consider("0."+suff, "" + fracs) ;
                  } else {
                     if (b == 1)
                        consider(""+wh+"."+suff, "" + wh) ;
                     else
                        consider(""+wh+"."+suff, "" + wh + " " + fracs) ;
                  }
               }
            }
         }
      }
   }
   public static void main(String[] args) throws Exception {
      init() ;
      Scanner sc = new Scanner(System.in) ;
      int kases = sc.nextInt() ;
      for (int kase=0; kase<kases; kase++) {
         String inp = sc.next() ;
         String res = lookup.get(inp) ;
         if (res == null)
            throw new Exception("Missing value for " + inp) ;
         System.out.println(res + '"') ;
      }
   }
}
