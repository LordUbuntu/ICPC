import java.util.*;

public class Atlas1D_AN2 {
  static int n, k;

  static class Point {
    int x, w;
    public Point(int x, int w) {
      this.x = x;
      this.w = w;
    }
    public int getX() {return x;}
  }

  private static int[] merge(int[] a, int[] b) {
    int[] ans = new int[Math.min(k, a.length + b.length)];
    int ai = 0, bi = 0, i = 0;
    while (ai < a.length && bi < b.length && i < ans.length) {
      if (a[ai] > b[bi]) {
        ans[i++] = a[ai++];
      } else {
        ans[i++] = b[bi++];
      }
    }

    while (ai < a.length && i < ans.length) ans[i++] = a[ai++];
    while (bi < b.length && i < ans.length) ans[i++] = b[bi++];

    return ans;
  }

  private static int ii = 0;
  private static int[] merge2(int[] a, int[] b) {
    int[] ans = work[ii++][Math.min(k, a.length + b.length)];
    ii &= 3;
    int ai = 0, bi = 0, i = 0;
    while (ai < a.length && bi < b.length && i < ans.length) {
      if (a[ai] > b[bi]) {
        ans[i++] = a[ai++];
      } else {
        ans[i++] = b[bi++];
      }
    }

    while (ai < a.length && i < ans.length) ans[i++] = a[ai++];
    while (bi < b.length && i < ans.length) ans[i++] = b[bi++];

    return ans;
  }

  private static int jj = 0;
  private static int[] merge3(int[] a, int[] b) {
    int[] ans = work2[jj++][Math.min(k, a.length + b.length)];
    jj &= 3;
    int ai = 0, bi = 0, i = 0;
    while (ai < a.length && bi < b.length && i < ans.length) {
      if (a[ai] > b[bi]) {
        ans[i++] = a[ai++];
      } else {
        ans[i++] = b[bi++];
      }
    }

    while (ai < a.length && i < ans.length) ans[i++] = a[ai++];
    while (bi < b.length && i < ans.length) ans[i++] = b[bi++];

    return ans;
  }

  static class Node {
    int value;
    Node left = null;
    Node right = null;
    int[] topK;
  }

  static final int[] EMPTY = new int[0];
  static final int[][][] work = new int[4][11][];
  static {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 11; j++) work[i][j] = new int[j];
    }
  }
  static final int[][][] work2 = new int[4][11][];
  static {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 11; j++) work2[i][j] = new int[j];
    }
  }

  static class RangeTree {
    Node root;

    public RangeTree(Point[] points) {
      Arrays.sort(points, Comparator.comparingInt(Point::getX));
      root = construct(points, 0, points.length);
    }

    private Node construct(Point[] sortedPoints, int low, int high) {
      int mid = (low + high) / 2;
      Node cur = new Node();
      cur.value = sortedPoints[mid].x;
      if (mid == low) {
        cur.topK = new int[1];
        cur.topK[0] = sortedPoints[mid].w;
      } else {
        cur.left = construct(sortedPoints, low, mid);
        cur.right = construct(sortedPoints, mid, high);
        cur.topK = merge(cur.left.topK, cur.right.topK);
      }
      return cur;
    }

    private int[] rightPoints(int low, Node cur) {
      if (cur == null) return EMPTY;
      if (cur.value < low) {
        return rightPoints(low, cur.right);
      }
      if (cur.left == null && cur.right == null) return cur.topK;
      int[] left = rightPoints(low, cur.left);
      if (cur.right == null) return left;
      return merge3(cur.right.topK, left);
    }

    private int[] leftPoints(int high, Node cur) {
      if (cur == null) return EMPTY;
      if (cur.value > high) {
        return leftPoints(high, cur.left);
      }
      if (cur.left == null && cur.right == null) return cur.topK;
      int[] right = leftPoints(high, cur.right);
      if (cur.left == null) return right;
      return merge2(cur.left.topK, right);
    }

    public int[] rangeQuery(int low, int high) {
      Node cur = root;
      while (cur.value < low || cur.value > high) {
        if (cur.value < low) {
          cur = cur.right;
        } else {
          cur = cur.left;
        }
        if (cur == null) return EMPTY;
      }
      if (cur.left == null && cur.right == null) return cur.topK;
      return merge2(rightPoints(low, cur.left), leftPoints(high, cur.right));
    }
  }

  static class Viewport {
    int zoom;
    int ptIdx;
    int dist;
    public Viewport(int zoom, int ptIdx, int dist) {
      this.zoom = zoom;
      this.ptIdx = ptIdx;
      this.dist = dist;
    }
  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    n = s.nextInt();
    k = s.nextInt();
    Point[] pts = new Point[n + 1];
    pts[0] = new Point(0, 0);
    for (int i = 1; i <= n; i++) {
      int x = s.nextInt();
      pts[i] = new Point(2 * x, i);
    }
    RangeTree tree = new RangeTree(Arrays.copyOf(pts, n + 1));

    int[][] dist = new int[31][n + 1];
    boolean[][] visible = new boolean[31][n + 1];
    boolean[] anyvis = new boolean[n + 1];
    int viscount = 0;
    for (int i = 0; i < 31; i++) Arrays.fill(dist[i], -1);
    Queue<Viewport> queue = new LinkedList<Viewport>();
    queue.add(new Viewport(1, 0, 0));
    while (!queue.isEmpty()) {
      Viewport cur = queue.remove();
      if (dist[cur.zoom][cur.ptIdx] >= 0) continue;
      dist[cur.zoom][cur.ptIdx] = cur.dist;
      if (cur.zoom > 0) queue.add(new Viewport(cur.zoom - 1, cur.ptIdx, cur.dist + 1));
      if (cur.zoom < 30) queue.add(new Viewport(cur.zoom + 1, cur.ptIdx, cur.dist + 1));
      Point center = pts[cur.ptIdx];
      int d = 1 << cur.zoom;
      for (int p : tree.rangeQuery(center.x - d, center.x + d)) {
        if (p == cur.ptIdx) {
          visible[cur.zoom][cur.ptIdx] = true;
          if (!anyvis[cur.ptIdx]) {
            anyvis[cur.ptIdx] = true;
            viscount++;
          }
        } else {
          queue.add(new Viewport(cur.zoom, p, cur.dist + 1));
        }
      }
      if (viscount == n + 1) break;
    }

    for (int i = 1; i <= n; i++) {
      int best = 1000000000;
      for (int j = 0; j < 31; j++) if (visible[j][i]) best = Math.min(best, dist[j][i]);
      if (best == 1000000000) best = -1;
      System.out.println(best);
    }
  }
}
