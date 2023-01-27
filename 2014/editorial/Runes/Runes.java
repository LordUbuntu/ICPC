import java.util.Scanner;
import java.util.StringTokenizer;

public class Runes {

  static String expr, s;
  static char[] a;
  static int idx;

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in) ;
    int cnt = sc.nextInt() ;
    for (int i=0; i<cnt; i++)
       doOne(sc) ;
  }
  public static void doOne(Scanner sc) throws Exception {
    expr = sc.next() ;
    System.out.println(solve());
  }

  static int solve() {
    for (char d = '0'; d <= '9'; d++)
      if (expr.indexOf(d) == -1 && ok(d)) return d - '0';
    return -1;
  }

  static boolean ok(char d) {
    boolean ok = true;
    StringTokenizer str = new StringTokenizer(expr.replace('?', d), "+-*=", true);
    long A = parseLong("-".equals(s = str.nextToken()) ? s + str.nextToken() : s);
    char op = str.nextToken().charAt(0);
    long B = parseLong("-".equals(s = str.nextToken()) ? s + str.nextToken() : s);
    str.nextToken();
    long C = parseLong("-".equals(s = str.nextToken()) ? s + str.nextToken() : s);
    ok &= A != Long.MAX_VALUE && B != Long.MAX_VALUE && C != Long.MAX_VALUE;
    ok &= !str.hasMoreTokens();
    if (op == '+')
      ok &= A + B == C;
    else if (op == '-')
      ok &= A - B == C;
    else
      ok &= A * B == C;
    return ok;
  }

  static long parseLong(String s) {
    long n = Integer.parseInt(s);
    return (n == 0 && !"0".equals(s)) || (Math.abs(n) > 999999) ? Long.MAX_VALUE : n;
  }

}
