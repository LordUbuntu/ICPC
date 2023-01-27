import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Top25 {
  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(in.readLine());
    while (T-- > 0) {
       HashMap<String, Integer> ids = new HashMap<String, Integer>();
       int n = Integer.parseInt(in.readLine());
       for (int i = 0; i < n; i++)
         ids.put(in.readLine(), i);
       for (int i = 0, l = -1, r = 0; i < n; i++) {
         r = Math.max(r, ids.get(in.readLine()));
         if (r == i) {
           if (l != -1)
              System.out.print(" ") ;
           System.out.print(r - l);
           l = r;
         }
      }
      System.out.println() ;
    }
  }
}
