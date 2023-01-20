import java.io.*;
import java.util.*;

public class exam_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int k = Integer.parseInt(s[1]);
    char[][] ans = new char[n][k];
    for (int i=0; i<n; i++) {
      ans[i] = br.readLine().toCharArray();
    }
    int best = 0;
    for (int i=0; i<(1<<k); i++) {
      int minCorrect = k;
      char[] key = new char[k];
      for (int l=0; l<k; l++) {
        key[l] = ((i&(1<<l)) > 0) ? 'T' : 'F';
      }
      for (int j=0; j<n; j++) {
        int correct = 0;
        for (int l=0; l<k; l++) {
          if (ans[j][l]==key[l])
            correct++;
        }
        if (correct<minCorrect)
          minCorrect = correct;
      }
      if (minCorrect>best)
        best = minCorrect;
    }
    System.out.println(best);
  }
}
