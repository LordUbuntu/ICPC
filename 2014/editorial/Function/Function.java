import java.util.*;

public class Function {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    for (int tc = 0; tc < T; tc++) {
      int n = s.nextInt();
      int m = s.nextInt();
      int a = s.nextInt();
      int b = s.nextInt();
      int c = s.nextInt();
      int d = s.nextInt();
      int r = s.nextInt();
      double[][] matrix = new double[2 * m + 1][2 * m + 2];
      for (int i = 0; i <= m; i++) {
        matrix[i][i] = r * m * m;
        matrix[i][2 * (m - i)] -= b * m * m + r * (i - m) * (i - m);
        matrix[i][2 * m + 1] = a * m * m;
      }
      for (int i = m + 1; i <= 2 * m; i++) {
        matrix[i][i] = r * m * m;
        matrix[i][2 * (2 * m - i)] -= d * m * m + r * (i - m) * (i - m);
        matrix[i][2 * m + 1] = c * m * m;
      }

      rref(matrix);
      
      int cr = m + n;
      while (Math.abs(matrix[cr][m + n]) < 1e-9) cr--;
      System.out.println(matrix[cr][2 * m + 1]);
      
    }
  }
  
  static int rref (double[][] a){
    int i,j,r=0,c;
    int n = a.length;
    int m = a[0].length;
    // don't reduce last column, in case matrix is singular
    for (c=0;c<m-1;c++){
      j=r;
      for (i=r+1;i<n;i++) if (Math.abs(a[i][c])>Math.abs(a[j][c])) j = i;
      if (Math.abs(a[j][c])<1e-9) continue;
      double[] tmp = a[j]; a[j] = a[r]; a[r] = tmp;
      
      double s = a[r][c];
      for (j=0;j<m;j++) a[r][j] /= s;
      for (i=0;i<n;i++) if (i != r){
        double t = a[i][c];
        if (Math.abs(t) < 1e-9) continue;
        for (j=0;j<m;j++) a[i][j] -= t * a[r][j];
      }
      r++;
      if (r >= n) break;
    }
    return r;
  }

}
