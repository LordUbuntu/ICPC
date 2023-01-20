import java.util.*;

public class BadPacking_AN {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int n = s.nextInt(), c = s.nextInt();
    int[] dp = new int[2 * c + 1];
    int[] arr = new int[n + 1];
    for (int i = 1; i <= n; i++) arr[i] = s.nextInt();
    
    Arrays.fill(dp, -1);
    Arrays.sort(arr);
    TreeSet<Integer> unique = new TreeSet<>();
    for (int i : arr) unique.add(i);

    dp[0] = 0;
    for (int i = 1; i <= n; i++) {
      for (int j = c; j >= 0; j--) {
        if (dp[j] >= 0) {
          if (dp[j] == arr[i]) {
            dp[j]--;
            dp[j + arr[i]] = arr[i];
          } else if (dp[j] == arr[i - 1]) {
            dp[j + arr[i]] = arr[i];
          } else {
            dp[j + arr[i]] = Math.max(dp[j + arr[i]], dp[j]);
          }
        }
      }
    }


    for (int i = 0; i <= c; i++) {
      if (dp[i] == -1) continue;
      if (dp[i] == arr[n] || unique.higher(dp[i]) + i > c) {
        System.out.println(i);
        return;
      }
    }
  }
}
