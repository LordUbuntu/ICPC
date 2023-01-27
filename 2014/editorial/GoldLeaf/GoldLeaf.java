import java.util.Scanner;

public class GoldLeaf {

  static final Line inf = new Line(Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2);

  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(System.in);
    int kases = in.nextInt() ;
   for (int kase=1; kase<=kases; kase++) {
    int n = in.nextInt(), m = in.nextInt();
    char[][] leaf = new char[n][];
    for (int i = 0; i < n; i++)
      leaf[i] = in.next().toCharArray();

    Line result = inf;
    result = Line.min(result, horizontal(leaf));
    result = Line.min(result, diagonal(leaf));
    leaf = transpose(leaf);
    result = Line.min(result, horizontal(leaf).transpose(m, 1));
    result = Line.min(result, diagonal(leaf).transpose(m, 0));

    System.out.printf("%d %d %d %d\n", result.x1 + 1, result.y1 + 1, result.x2 + 1, result.y2 + 1);
   }
  }

  static Line horizontal(char[][] leaf) {
    Line result = inf;
    int n = leaf.length, m = leaf[0].length, c;
    for (int fold = 0; fold < n; fold++) {
      boolean ok = true;
      boolean[][] v = new boolean[n][m];
      for (int i = 0, k = 2 * fold + 1; i <= fold; i++, k--) {
        for (int j = 0; j < m; j++) {
          ok &= ((c = get(leaf, 2 * fold - i + 1, j)) != 0 && c != leaf[i][j]) || (c == 0 && leaf[i][j] == '#');
          v[i][j] = true;
          if (c != 0) v[k][j] = true;
        }
      }
      ok &= checkAllV(leaf, v);
      if (ok) result = Line.min(result, new Line(fold, 0, fold, m - 1));
    }
    return result;
  }

  static Line diagonal(char[][] leaf) {
    Line result = inf;
    int n = leaf.length, m = leaf[0].length, c;
    for (int fold = 0; fold < n + m; fold++) {
      boolean ok = true;
      boolean[][] v = new boolean[n][m];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < m; j++)
          if (i + j < fold) {
            int df = fold - i - j;
            ok &= ((c = get(leaf, i + df, j + df)) != 0 && c != leaf[i][j]) || (c == 0 && leaf[i][j] == '#');
            v[i][j] = true;
            if (c != 0) v[i + df][j + df] = true;
          } else if (i + j == fold) {
            ok &= leaf[i][j] == '#';
            v[i][j] = true;
          }
      ok &= checkAllV(leaf, v);
      if (ok) {
        int x = Math.min(n - 1, fold), y = Math.min(m - 1, fold);
        result = Line.min(result, new Line(x, fold - x, fold - y, y));
      }
    }
    return result;
  }

  static boolean checkAllV(char[][] leaf, boolean[][] v) {
    boolean ok = true;
    for (int i = 0; i < leaf.length; i++)
      for (int j = 0; j < leaf[0].length; j++)
        ok &= leaf[i][j] == '#' || v[i][j];
    return ok;
  }

  static int get(char[][] leaf, int i, int j) {
    return 0 <= i && i < leaf.length && 0 <= j && j < leaf[0].length ? leaf[i][j] : 0;
  }

  static char[][] transpose(char[][] a) {
    int n = a.length, m = a[0].length;
    char[][] b = new char[m][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < m; j++)
        b[m - 1 - j][i] = a[i][j];
    return b;
  }

  static class Line {
    int x1, y1, x2, y2;

    Line(int a, int b, int c, int d) {
      if (b > d || (b == d && a > c)) {
        x2 = a;
        y2 = b;
        x1 = c;
        y1 = d;
      } else {
        x1 = a;
        y1 = b;
        x2 = c;
        y2 = d;
      }
    }

    Line transpose(int n, int d) {
      return this == inf ? inf : new Line(y1, n - 1 - x1 - d, y2, n - 1 - x2 - d);
    }

    static Line min(Line a, Line b) {
      int comp = 0;
      if (comp == 0) comp = Integer.compare(a.x1, b.x1);
      if (comp == 0) comp = Integer.compare(a.y1, b.y1);
      if (comp == 0) comp = Integer.compare(a.x2, b.x2);
      if (comp == 0) comp = Integer.compare(a.y2, b.y2);
      return comp <= 0 ? a : b;
    }
  }
}
