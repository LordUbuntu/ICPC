import java.io.*;
import java.util.*;

public class NoThanks_Bowen {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    
    int n = sc.nextInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; i++)
      arr[i] = sc.nextInt();
      
    Arrays.sort(arr);
    int ans = arr[0];
    for (int i = 1; i < n; i++) {
      if (arr[i] != arr[i - 1] + 1) ans += arr[i];
    }
    System.out.println(ans);
  }
}
