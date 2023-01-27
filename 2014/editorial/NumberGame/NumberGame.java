import java.util.Scanner;


public class NumberGame {
  static int[] arr = new int[128];
  
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    for (int tc = 0; tc < T; tc++) {
      int N = s.nextInt();
      int goalIdx = -1;
      for (int i = 0; i < N; i++) {
        arr[i] = s.nextInt();
        if (arr[i] == 1) goalIdx = i;
      }
      
      boolean leftEdge = goalIdx == 0;
      boolean rightEdge = goalIdx == N - 1;
      boolean leftLocked = (goalIdx > 1 && arr[goalIdx - 1] < arr[goalIdx - 2]);
      boolean rightLocked = (goalIdx < N - 2 && arr[goalIdx + 1] < arr[goalIdx + 2]);
      int leftShield = 0;
      if (!(leftEdge || leftLocked)) {
        for (int i = goalIdx - 2; i >= 0 && arr[i] < arr[i + 1]; i--) leftShield++;
      }
      int rightShield = 0;
      if (!(rightEdge || rightLocked)) {
        for (int i = goalIdx + 2; i < N && arr[i] < arr[i - 1]; i++) rightShield++;
      }
      
      boolean win = true;
      if (N % 2 == 0) {
        if (leftShield % 2 == 0 && rightShield % 2 == 0) win = false;
        if (leftShield % 2 == 1 && rightLocked) win = false;
        if (rightShield % 2 == 1 && leftLocked) win = false;
      } else {
        if (leftShield % 2 == 1 && rightShield % 2 == 1) win = false;
        if (leftShield % 2 == 1 && rightEdge) win = false;
        if (rightShield % 2 == 1 && leftEdge) win = false;
      }
      
      if (win) {
        System.out.println("Alice");
      } else {
        System.out.println("Bob");
      }
    }
  }
}
