import java.io.*;
import java.util.*;

/**
 * Try all permutations. Count the number of times each pair of digits
 * appears in the sequence.
 *
 * @author Finn Lidbetter
 */

public class ant_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    char[] seq = br.readLine().toCharArray();
    int n = seq.length;
    // counts[i][j] is the number of times that digit j comes immediately after digit i.
    int[][] counts = new int[9][9];
    for (int i=1; i<seq.length; i++) {
      int d1 = seq[i-1]-'1';
      int d2 = seq[i]-'1';
      counts[d1][d2]++;
    }
    int[] perm = new int[9];
    for (int i=0; i<9; i++) {
      perm[i] = i;
    }
    int best = 987654321;
    while (perm!=null) {
      int[] index = new int[9];
      for (int i=0; i<perm.length; i++) {
        index[perm[i]] = i;
      }
      int sum = index[seq[0]-'1'];
      for (int i=0; i<9; i++) {
        for (int j=0; j<9; j++) {
          int dist = index[i]-index[j];
          if (dist<0)
            dist*=-1;
          sum += counts[i][j]*dist;
        }
      }
      sum += seq.length;
      if (sum<best)
        best = sum;
      perm = nextPermutation(perm);
    }
    System.out.println(best);


  }
  static int[] nextPermutation(int[] c) {
    int first = getFirst(c); 
    if (first == -1) 
      return null;
    int toSwap = c.length - 1;
    while (c[first] >= c[toSwap]) 
      --toSwap;
    swap(c, first++, toSwap); 
    toSwap = c.length - 1;
    while (first < toSwap) 
      swap(c, first++, toSwap--);
    return c; 
  }
  static int getFirst(int[] c) {
    for (int i = c.length - 2; i >= 0; --i) 
      if (c[i] < c[i + 1]) 
        return i;
    return -1; 
  }
  static void swap(int[] c, int i, int j) { 
    int tmp = c[i]; 
    c[i] = c[j]; 
    c[j] = tmp; 
  }
}
