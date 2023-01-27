import java.util.Arrays;
import java.util.Scanner;


public class Alchemy {
  static class Circle implements Comparable<Circle> {
    int x, y, r;
    int a, b;
    int label;
    Circle parent = null;
    
    public Circle (int x, int y, int r, int a, int b, int l) {
      this.x = x;
      this.y = y;
      this.r = r;
      this.a = a;
      this.b = b;
      this.label = l;
    }
    
    public int compareTo(Circle o) {
      return r - o.r;
    }
    
    public boolean isInside(Circle o) {
      return ((x - o.x) * (x - o.x) + (y - o.y) * (y - o.y) < o.r * o.r);
    }
  }
  
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    int T = s.nextInt();
    for (int tc = 0; tc < T; tc++) {
      int N = s.nextInt();
      Circle[] circles = new Circle[N];
      for (int i = 0; i < N; i++) {
        circles[i] = new Circle(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), i+1);
      }
      
      Arrays.sort(circles);
      int[] values = new int[N];
      int[] maxValues = new int[N];
      int[] ancestors = new int[N];
      int[] minAncestors = new int[N];
      int[] parent = new int[N];
      int total = 0;
      for (int i = 0; i < N; i++) {
        Circle c = circles[i];
        for (int j = i + 1; j < N; j++) {
          if (c.isInside(circles[j])) {
            if (parent[i] == 0) parent[i] = j;
            ancestors[i]++;
            values[i] += (ancestors[i] % 2 == 1 ? c.a : c.b);
            maxValues[i] = Math.max(maxValues[i], values[i]);
          }
        }
        total += maxValues[i];
        int curValue = values[i];
        for (int j = ancestors[i]; j >= 0; j--) {
          if (curValue == maxValues[i]) minAncestors[i] = j;
          curValue -= (j % 2 == 1 ? c.a : c.b);
        }
      }
      
      System.out.println(total);
      
      boolean[] active = new boolean[N];
      boolean[] locked = new boolean[N];
      for (int i = 0; i < N; i++) {
        if (i > 0) System.out.print(" ");
        Arrays.fill(locked, false);
        int best = -1;
        for (int j = 0; j < N; j++) {
          if (locked[j]) {
            locked[parent[j]] = true;
            continue;
          }
          if (active[j]) continue;
          if (values[j] == maxValues[j] && (best == -1 || circles[j].label < circles[best].label)) best = j;
          if (ancestors[j] == minAncestors[j]) locked[parent[j]] = true;
        }
        active[best] = true;
        for (int j = 0; j < best; j++) {
          if (circles[j].isInside(circles[best])) {
            values[j] -= (ancestors[j] % 2 == 1 ? circles[j].a : circles[j].b);
            ancestors[j]--;
          }
        }
        System.out.print(circles[best].label);
      }
      System.out.println();
    }
  }
}
