import java.io.*;
import java.util.*;

/**
 * The optimal solution involves repeatedly moving the smallest
 * element to its nearest endpoint. The number of swaps needed
 * for each element is the minimum of the number of values greater
 * than it to its right, and the number of values greater than it
 * to its left.
 * The number of such values can be computed using a Fenwick tree.
 * We iterate over the values of the array in descending order. At 
 * each step if we are processing value v at index i we add 1 to 
 * the fenwick tree at index i. We then compare the sum over the 
 * range [i+1, n-1] to [0, i-1] in the fenwick tree. These sums
 * represent the number of values greater than v to the right of
 * index i and to the left of index i, respectively. Both adding 1
 * and querying the ranges are O(log n) operations.
 * This solution has complexity O(n log n)
 *
 * @author Finn Lidbetter
 */

public class FenwickBitonic {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    int n = Integer.parseInt(br.readLine());
    int[] arr = new int[n];
    TreeMap<Integer, Integer> valToIndex = new TreeMap<>();
    for (int i=0; i<n; i++) {
      arr[i] = Integer.parseInt(br.readLine());
      valToIndex.put(arr[i], i);
    }
    FenwickTree ft = new FenwickTree(n);
    // side[i] is False if value should move left, True otherwise.
    boolean[] side = new boolean[n];
    long swaps = 0;
    for (int val: valToIndex.descendingKeySet()) {
      int index = valToIndex.get(val);
      int rightDist = ft.sum(index+1, n);
      int leftDist = ft.sum(1, index);
      if (leftDist<rightDist) {
        side[index] = false;
        swaps += leftDist;
      } else {
        side[index] = true;
        swaps += rightDist;
      }
      ft.add(index+1, 1);
    }
    System.out.println(swaps);
  }
}
class FenwickTree {
  int[] arr;
  FenwickTree(int n) { 
    arr = new int[n + 1]; 
  }   
  public int sum(int i, int j) { // Get the sum of [i,j]
    return sum(j) - sum(i - 1); 
  }
  public int sum(int i) { // Get the sum of [1,i]
    int sum = 0; 
    while (i > 0) { 
      sum += arr[i]; 
      i -= i & -i; 
    } 
    return sum; 
  }
  public void add(int i, int delta) { // Add delta to value at i
    if (i <= 0) 
      return;
    while (i < arr.length) { 
      arr[i] += delta; 
      i += i & -i; 
    } 
  } 
}

