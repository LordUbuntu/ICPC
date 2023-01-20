import java.io.*;
import java.util.*;

/**
 * Iterate over all pairs of smallest two elements.
 * Find the largest value that can be used with these two elements
 * and take all non-empty subsets of terms in between.
 * Complexity: O(n^3)
 *
 * @author Finn Lidbetter
 */

public class triangular_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    int[] arr = new int[n];
    for (int i=0; i<n; i++) {
      arr[i] = Integer.parseInt(br.readLine());
    }
    Arrays.sort(arr);
    long sum = 0;
    for (int i=0; i<n; i++) {
      for (int j=i+1; j<n; j++) {
        // Count the number of subsets where arr[i] and arr[j] are 
        // the smallest elements.
        int end = j;
        for (int k=j+1; k<n; k++) {
          if (arr[i]+arr[j]>arr[k]) {
            end = k;
          } else {
            break;
          }
        }
        // Use arr[i], arr[j] with any nonempty subset of elements arr[j+1:end].
        sum += ((1L<<(end-j)) - 1);
      }
    }
    System.out.println(sum);
  }
}
