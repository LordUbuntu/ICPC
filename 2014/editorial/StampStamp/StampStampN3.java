
// N^3 solution to Stamp Stamp (intended)
// Use the convex hull to reduce the number of candidate vectors.

import java.util.*;

public class StampStampN3
{
   public static void main(String[] args)
   {
      new StampStampN3(new Scanner(System.in));
   }
   
   int W, H, oo=987654321;
   boolean[][] isMarked, hasInEdge, hasOutEdge;
   boolean isOk(int x, int y)
   {
      if (x < 0 || x >= W) return false;
      if (y < 0 || y >= H) return false;
      return true;
   }

   int score(int dx, int dy)
   {
      for (boolean[] e : hasInEdge)
         Arrays.fill(e, false);
      for (boolean[] e : hasOutEdge)
         Arrays.fill(e, false);
      for (int i=0; i<W; i++)
         for (int j=0; j<H; j++)
            if (isOk(i+dx, j+dy) && isMarked[i][j] && isMarked[i+dx][j+dy])
               hasOutEdge[i][j] = hasInEdge[i+dx][j+dy] = true;

      int res = 0;
      for (int i=0; i<W; i++)
      {
         for (int j=0; j<H; j++)
         {
            if (!isMarked[i][j]) continue;
           
            // This node is isolated
            if (!hasOutEdge[i][j] && !hasInEdge[i][j])
               return oo;
         
            // This node is a root! Walk the path
            if (!hasInEdge[i][j])
            {
               int numNodes = 1;
               int x = i;
               int y = j;
               while (isOk(x, y) && hasOutEdge[x][y])
               {
                  x += dx;
                  y += dy;
                  numNodes++;
               }
               
               res += (numNodes+1)/2;
            }
         }
      }
      
      return res;
   }

   int cross(vec2 p, vec2 a, vec2 b)
   {
      return a.sub(p).cross(b.sub(p));
   }

   // Assumes points are given sorted by x then y.
   vec2[] convexHull(vec2[] pts, int N)
   {
      vec2[] H = new vec2[2*N+1];
      int k=0;
      for (int i=0; i<N; i++)
      {
         while (k >= 2 && cross(H[k-2], H[k-1], pts[i]) <= 0) k--;
         H[k++] = pts[i];
      }
      int half = k;
      for (int i=N-1; i>=0; i--)
      {
         while (k-half >= 2 && cross(H[k-2], H[k-1], pts[i]) <= 0) k--;
         H[k++] = pts[i];
      }
      vec2[] res = new vec2[k];
      for (int i=0; i<k; i++)
         res[i] = H[i];
      return res;
   }

   int gcd(int a, int b)
   {
      return b==0 ? a : gcd(b, a%b);
   }

   int solve()
   {
      hasInEdge = new boolean[W][H];
      hasOutEdge = new boolean[W][H];

      // Obtain a list of points from the cells that are marked!
      vec2[] pts = new vec2[W*H];
      int N = 0;

      // No need to sort the convex hull because you pull them out of the grid
      for (int i=0; i<W; i++)
         for (int j=0; j<H; j++)
            if (isMarked[i][j])
               pts[N++] = new vec2(i,j);

      int res = oo;
      vec2[] hull = convexHull(pts, N);
      N = hull.length;
      for (int i=0; i<N; i++)
      {
         int j = (i+1)%N;
      
         // Remove the candidate vector
         vec2 dv = hull[j].sub(hull[i]);
        
         // Don't process duplicate points
         if (dv.x == 0 && dv.y == 0) continue;

         // We only have to consider half the vectors!
         if (dv.y < 0) continue;

         // Normalize the candidate vector
         int g = gcd(Math.abs(dv.x), dv.y);
         int dx = dv.x / g;
         int dy = dv.y / g;

         // Try all candinate vectors :)
         for (int s=1; s<=g; s++)
            res = Math.min(res, score(s*dx, s*dy));
      }

      return res;
   }
   
   public StampStampN3(Scanner in)
   {
      int T = in.nextInt();
      while (T-->0)
      {
         H = in.nextInt();
         W = in.nextInt();
         int res = 0;
         isMarked = new boolean[W][H];
         for (int j=0; j<H; j++)
         {
            String s = in.next();
            for (int i=0; i<W; i++)
               if (isMarked[i][j] = s.charAt(i) == '#')
                  res++;
         }
   
         res = Math.min(res, solve());

         System.out.printf("%d%n", res);
      }
   }
   
   class vec2
   {
      int x, y;
      vec2(int xx, int yy)
      {
         x=xx; y=yy;
      }

      vec2 sub(vec2 rhs)
      {
         return new vec2(x-rhs.x, y-rhs.y);
      }

      int cross(vec2 rhs)
      {
         return x*rhs.y-y*rhs.x;
      }

      public String toString()
      {
         return String.format("<%d, %d>", x, y);
      }
   }
}

