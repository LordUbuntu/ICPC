import java.io.*;
import java.util.*;

/**
 * Try making all remaining judges evaluate as 3.
 * Try making all remaining judges evaluate as -3.
 *
 * @author Finn Lidbetter
 */

public class rating_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int k = Integer.parseInt(s[1]);
    double initialSum = 0;
    for (int i=0; i<k; i++) {
      initialSum += Integer.parseInt(br.readLine());
    }
    double smallSum = initialSum - 3.0*(n-k);
    double bigSum = initialSum + 3.0*(n-k);
    System.out.println((smallSum/n) + " " + (bigSum/n));
  }
}
