import java.io.*;
import java.util.*;

/**
 * Build a segment tree to answer queries of the form:
 *    What are the Point of Interest with the k highest weights
 *    between position A and position B?
 * Use coordinate compression to make this efficient.
 * Then build a graph where there is a node for each possible center,
 * viewport size pair. (This includes center at 0.)
 * Determine neighbours by querying the segment tree.
 * Do a breadth first search on the resulting graph and be careful to
 * only mark a Point of Interest as reached if we reach a node that 
 * makes it centered and visible.
 *
 * @author Finn Lidbetter
 */

public class triptik_finn {
  static int k;
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0])+1;
    k = Integer.parseInt(s[1]);
    POI[] pts = new POI[n];
    POI[] ptsSorted = new POI[n];
    HashMap<Integer, Integer> positionToIndex = new HashMap<>();
    for (int i=0; i<n-1; i++) {
      int p = Integer.parseInt(br.readLine());
      int w = i;
      pts[i] = new POI(p, w);
      ptsSorted[i] = pts[i];
      positionToIndex.put(pts[i].p, i);
    }
    pts[n-1] = new POI(0, -1);
    ptsSorted[n-1] = pts[n-1];
    positionToIndex.put(0, n-1);
    Arrays.sort(ptsSorted, new PositionComparator());
    int minP = ptsSorted[0].p;
    int maxP = ptsSorted[n-1].p;
    TreeNode rt = new TreeNode(ptsSorted, 0, n-1);
    ArrayList<Integer> windowSizeList = new ArrayList<>();
    windowSizeList.add(1);
    int sz = 1;
    while(sz<maxP-minP) {
      sz*=2;
      windowSizeList.add(sz);
    }
    int[] windowSizeArr = new int[windowSizeList.size()];
    for (int i=0; i<windowSizeList.size(); i++) {
      windowSizeArr[i] = windowSizeList.get(i);
    }
    int logLen = windowSizeArr.length;
    GraphNode[] nodes = new GraphNode[n*logLen];
    for (int i=0; i<n; i++) {
      for (int j=0; j<logLen; j++) {
        int id = i*logLen + j;
        nodes[id] = new GraphNode(id, i);
      }
    }
    for (int i=0; i<n; i++) {
      for (int j=0; j<logLen; j++) {
        int p = pts[i].p;
        int currSz = windowSizeArr[j];
        int id = i*logLen + j;
        int lo = p-currSz/2;
        int hi = p+currSz/2;
        if (lo<minP) {
          lo = minP;
        }
        if (hi>maxP) {
          hi = maxP;
        }
        POI[] vis = rt.query(lo, hi);
        for (POI nbr: vis) {
          int nbrID = positionToIndex.get(nbr.p)*logLen + j;
          if (nbrID!=id) {
            nodes[id].adj.add(nbrID);
          } else {
            nodes[id].visible = true;
          }
        }
        if (j<logLen-1) {
          nodes[id].adj.add(id+1);
        }
        if (j>0) {
          nodes[id].adj.add(id-1);
        }
      }
    }
    int[] dist = new int[n];
    Arrays.fill(dist, -1);
    boolean[] vis = new boolean[n*logLen];
    LinkedList<Integer> q = new LinkedList<>();
    q.offer((n-1)*logLen+1);
    vis[(n-1)*logLen+1] = true;
    q.offer(null);
    int depth = 0;
    while (q.size()>1) {
      Integer curr = q.poll();
      if (curr==null) {
        q.offer(null);
        depth++;
        continue;
      }
      int ptIndex = curr/logLen;
      int currSz = curr%logLen;
      if (dist[ptIndex]==-1 && nodes[curr].visible) {
        dist[ptIndex] = depth;
      }
      for (int nbr: nodes[curr].adj) {
        if (!vis[nbr]) {
          q.offer(nbr);
          vis[nbr] = true;
        }
      }
    }
    StringBuilder sb = new StringBuilder();
    for (int i=0; i<n-1; i++) {
      sb.append(dist[i]+"\n");
    }
    System.out.print(sb);
  }
  static void printQuery(int lo, int hi, TreeNode rt) {
    System.out.printf("[%d,%d]: %s\n",lo,hi,Arrays.toString(rt.query(lo, hi)));
  }
}
class GraphNode {
  ArrayList<Integer> adj;
  int id;
  int ptIndex;
  boolean visible = false;
  public GraphNode(int idd, int index) {
    id = idd;
    this.ptIndex = index;
    adj = new ArrayList<>();
  }
}

class TreeNode {
  static int N_VIS = triptik_finn.k;
  static final long INF = Long.MAX_VALUE;
  int l,r;
  POI minPos, maxPos;
  POI[] vis;

  TreeNode left, right;

  public TreeNode(POI[] pts, int ll, int rr) {
    l = ll;
    r = rr;
    minPos = pts[l]; 
    maxPos = pts[r];
    if (l == r) {
      left = right = null;
      vis = new POI[]{pts[l]};
    } else {
      int mid = (l + r) / 2;
      left = new TreeNode(pts, l, mid);
      right = new TreeNode(pts, mid + 1, r);
      vis = combine(left.vis, right.vis);
    }
  }
  public POI[] query(int pLo, int pHi) {
    // Get the (at most) N_VIS highest weight points between pLo and pHi inclusive.
    if (pLo<=minPos.p && maxPos.p<=pHi) {
      return vis;
    }
    if (left==null || right==null)
      return vis;
    if (pHi<right.minPos.p) {
      return left.query(pLo, pHi);
    }
    if (pLo>left.maxPos.p) {
      return right.query(pLo, pHi);
    }
    return combine(left.query(pLo, pHi), right.query(pLo, pHi));
  }

  static POI[] combine(POI[] lp, POI[] rp) {
    int len = N_VIS;
    if (lp.length+rp.length<N_VIS) {
      len = lp.length + rp.length;
    }
    POI[] result = new POI[len];
    int li = 0;
    int ri = 0;
    int index = 0;
    while (index<len && (li<lp.length || ri<rp.length)) {
      if (li<lp.length && ri<rp.length) {
        if (lp[li].w>rp[ri].w) {
          result[index] = lp[li];
          li++;
        } else {
          result[index] = rp[ri];
          ri++;
        }
      } else if (li<lp.length) {
        result[index] = lp[li];
        li++;
      } else {
        result[index] = rp[ri];
        ri++;
      }
      index++;
    }
    return result;
  }
  
  public void print(boolean recurse) {
    System.out.printf("[%d, %d] minPos: %d, maxPos: %d, vis: %s\n", l,r,minPos.p,maxPos.p, Arrays.toString(vis));
    if (left!=null && recurse) {
      left.print(recurse);
      right.print(recurse);
    }
  }
}
class PositionComparator implements Comparator<POI> {
  // Sort positions lo to hi
  public int compare(POI p1, POI p2) {
    return p1.p - p2.p;
  }
}
class POI {
  int p, w;
  public POI(int pp, int ww) {
    w = ww;
    p = pp;
  }
  public String toString() {
    return String.format("%d (w:%d)", p, w);
  }
}

