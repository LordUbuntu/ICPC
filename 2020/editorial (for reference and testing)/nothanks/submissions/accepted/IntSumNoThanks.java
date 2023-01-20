import java.io.*;
import java.util.*;

/**
 * This solution would result in overflow if n can be up to 100,000.
 * But we decided that this was not the point of the problem.
 * So n is limited to 90,000, which cannot result in overflow.
 *
 * @author Finn Lidbetter
 */

public class IntSumNoThanks {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    String[] s = br.readLine().split(" ");
    boolean[] arr = new boolean[100005];
    for (int i=0; i<n; i++) {
      int val = Integer.parseInt(s[i]);
      arr[val] = true;
    }
    int sum = 0;
    for (int i=1; i<arr.length; i++) {
      if (arr[i] && !arr[i-1]) {
        sum += i;
      }
    }
    System.out.println(sum);
  }
}
