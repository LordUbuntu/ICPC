import java.util.Arrays;
import java.util.Scanner;


public class Diamonds {
  static double[] mass = new double[256];
  static double[] clarity = new double[256];
  static int[] len = new int[256];
  static int n = 0;
  
  static int getLength(int idx) {
    if (len[idx] > 0) return len[idx];
    int answer = 0;
    for (int i = 0; i < idx; i++) {
      if (mass[i] < mass[idx] && clarity[i] > clarity[idx]) answer = Math.max(answer, getLength(i));
    }
    return len[idx] = answer + 1;
  }
  
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    for (int tc = 0; tc < T; tc++) {
      n = s.nextInt();
      for (int i = 0; i < n; i++) {
        mass[i] = s.nextDouble();
        clarity[i] = s.nextDouble();
      }
      
      Arrays.fill(len, 0);
      
      int answer = 0;
      for (int i = 0; i < n; i++) {
        answer = Math.max(answer, getLength(i));
      }
      System.out.println(answer);
    }
  }
}
