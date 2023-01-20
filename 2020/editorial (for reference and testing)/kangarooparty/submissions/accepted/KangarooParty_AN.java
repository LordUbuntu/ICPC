import java.util.*;

public class KangarooParty_AN {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int n = s.nextInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) arr[i] = s.nextInt();
    int best = 1000000000;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int cur = 0;
        for (int k = 0; k < n; k++) {
          cur += Math.min((arr[i] - arr[k]) * (arr[i] - arr[k]), (arr[j] - arr[k]) * (arr[j] - arr[k]));
        }
        best = Math.min(best, cur);
      }
    }
    System.out.println(best);
  }
}
