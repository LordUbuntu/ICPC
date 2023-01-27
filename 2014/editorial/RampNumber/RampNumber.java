import java.util.Arrays;
import java.util.Scanner;


public class RampNumber {
  static long[][] dp = new long[128][10];
  static long[][] adp = new long[128][10];
  
  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) adp[0][i] = 1;
    for (int i = 1; i < 80; i++) {
      for (int j = 0; j < 10; j++) {
        for (int k = j; k < 10; k++) adp[i][j] += adp[i-1][k];
      }
    }
    
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    outer: for (int tc = 0; tc < T; tc++) {
      char[] num = new StringBuffer(s.next()).reverse().toString().toCharArray();
      for (int i = 1; i < num.length; i++) if (num[i] > num[i-1]) {
        System.out.println(-1);
        continue outer;
      }
      for (int i = 0; i < 128; i++) {
        Arrays.fill(dp[i], 0L);
      }
      for (int i = 0; i < num[0] - '0'; i++) {
        dp[0][i] = 1;
      }
      for (int i = 1; i < num.length; i++) {
        for (int j = 0; j < num[i] - '0'; j++) {
          dp[i][j] = adp[i][j];
        }
        for (int j = num[i] - '0'; j < 10; j++) {
          dp[i][num[i] - '0'] += dp[i-1][j];
        }
      }
      
      long answer = 0;
      for (int i = 0; i < 10; i++) {
        answer += dp[num.length - 1][i];
      }
      System.out.println(answer);
    }
  }
}
