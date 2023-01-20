import java.io.*;
import java.util.*;

/**
 * This solution uses dynamic programming to keep track of
 * the largest possible smallest unused value to get each
 * particular used capacity.
 *
 * @author Finn Lidbetter
 */

public class BadPackingFinn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int c = Integer.parseInt(s[1]);
    int[] vals = new int[n];
    for (int i=0; i<n; i++) {
      vals[i] = Integer.parseInt(br.readLine());
    }
    Arrays.sort(vals);

    // dp[i] is the largest possible smallest unused value to get a 
    // sum of i.
    // -1 indicates that the value is not reached by any sum.
    // 987654321 indicates that all values have been used.
    int USED_ALL = 987654321;
    int[] prev = new int[c+1];
    int[] dp = new int[c+1];
    Arrays.fill(prev, -1);
    Arrays.fill(dp, -1);
    prev[0] = USED_ALL;
    for (int i=0; i<n; i++) {
      int curr = vals[i];
      for (int w=0; w<=c; w++) {
        if (prev[w]!=-1) {
          // Try not using the current value.
          if (prev[w]==USED_ALL) {
            if (dp[w]==-1 || curr>dp[w]) {
              dp[w] = curr;
            }
          } else if (prev[w]>dp[w]) {
            // There is an earlier unused value. This is guaranteed to be
            // smaller than curr, so we have to use it. But it is an improvement
            // on what is currently in dp[w].
            dp[w] = prev[w];
          } else {
            // prev[w]<=dp[w]. We already have a better smallest
            // unused value to reach w. So do not overwrite it.
          }
          // Try using the current value.
          if (w+curr<=c) {
            if (prev[w]==USED_ALL) {
              dp[w+curr] = USED_ALL;
            } else {
              dp[w+curr] = prev[w]; 
            }
          }
        }
      }
      prev = dp;
      dp = new int[c+1];
      Arrays.fill(dp, -1);
    }
    // Choose the smallest filled capacity that such that the smallest
    // unused item will not fit with the remaining capacity.
    for (int i=0; i<=c; i++) {
      if (prev[i]>c-i) {
        System.out.println(i);
        return;
      }
    }
    // This should not be reachable.
    // Throw something that will give a run-time-error to give more
    // information about the bug.
    throw new NullPointerException();
  }
}
