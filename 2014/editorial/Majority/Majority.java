import java.util.Arrays;
import java.util.Scanner;


public class Majority {
  static int[] arr = new int[1024];
  
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    for (int tc = 0; tc < T; tc++) {
      int V = s.nextInt();
      Arrays.fill(arr, 0);
      for (int i = 0; i < V; i++) arr[s.nextInt()]++;
      int best = 0;
      for (int i = 1; i <= 1000; i++) if (arr[i] > arr[best]) best = i;
      System.out.println(best);
    }
  }
}
