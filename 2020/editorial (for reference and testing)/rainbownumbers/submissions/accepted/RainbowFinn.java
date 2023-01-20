import java.io.*;
import java.util.*;

/**
 * This submission uses dynamic programming.
 * Keep track of the number of ways for the first i digits where the last
 * digit is j. Additionally we use two more flags to identify whether we
 * have placed the first non-zero digit and whether or not the value is
 * less than the upper bound.
 *
 * @author Finn Lidbetter
 */

public class RainbowFinn {
  static long MOD = 998_244_353L;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String lo = br.readLine();
    String hi = br.readLine();
    long diff = solve(hi) - solve(lo);
    diff += 1;
    for (int i=1; i<lo.length(); i++) {
      if (lo.charAt(i)==lo.charAt(i-1)) {
        diff -= 1;
        break;
      }
    }
    if (diff<0) {
      diff += MOD;
    }
    System.out.println(diff);
  }
  static long solve(String hi) {
    if (hi.equals("0"))
      return 1;
    int n = hi.length();
    // dp[i][j][k][m] is the number of ways to represent the first 
    // i digits such that the last digit is j. k is 0 or 1. m is 0 or 1.
    // k is 1 if the representation is definitely less than hi already.
    // m is 1 if the first nonzero digit has been placed.
    long[][][][] dp = new long[n+1][10][2][2];
    dp[0][0][0][0] = 1L;
    for (int i=1; i<=n; i++) {
      int hiDigit = hi.charAt(i-1)-'0';
      for (int j=0; j<10; j++) {
        for (int l=0; l<10; l++) {
          if (j==0 && l==0) {
            // Place another leading 0.
            dp[i][j][1][0] += dp[i-1][l][0][0];
            dp[i][j][1][0] += dp[i-1][l][1][0];
            continue;
          } 
          if (l==j)
            continue;
          dp[i][j][1][1] += dp[i-1][l][1][1];
          dp[i][j][1][1] += dp[i-1][l][1][0];
          if (j==hiDigit) {
            dp[i][j][0][1] += dp[i-1][l][0][1];
            if (l==0) 
              dp[i][j][0][1] += dp[i-1][l][0][0];
          }
          if (j<hiDigit) {
            dp[i][j][1][1] += dp[i-1][l][0][1];
            if (l==0)
              dp[i][j][1][1] += dp[i-1][l][0][0];
          }
          for (int k=0; k<2; k++) {
            if (dp[i][j][k][1]>=MOD) {
              dp[i][j][k][1] %= MOD;
            }
          }
        }
      }
    }
    // 0 is not counted correctly.
    long sum = 1;
    for (int j=0; j<10; j++) {
      for (int k=0; k<2; k++) {
        sum += dp[n][j][k][1];
        if (sum>=MOD)
          sum %= MOD;
      }
    }
    return sum;
  }
}
