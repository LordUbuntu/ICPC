import java.io.*;
import java.util.*;

/**
 * This solution is linear in the max allowed value.
 *
 * @author Finn Lidbetter
 */

public class FinnNoThanks {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    String[] s = br.readLine().split(" ");
    boolean[] arr = new boolean[100005];
    for (int i=0; i<n; i++) {
      int val = Integer.parseInt(s[i]);
      arr[val] = true;
    }
    long sum = 0;
    for (int i=1; i<arr.length; i++) {
      if (arr[i] && !arr[i-1]) {
        sum += i;
      }
    }
    System.out.println(sum);
  }
}
