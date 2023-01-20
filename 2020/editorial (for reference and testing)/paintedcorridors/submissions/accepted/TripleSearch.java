import java.io.*;
import java.util.*;

/**
 * This solution works by searching over all triples of vertices,
 * representing the locations of the 3 robots. Secondary colour
 * edges can only be traversed if the two appropriate robots are
 * at the same vertex with the incident secondary colour edge.
 *
 * @author Finn Lidbetter
 */

public class TripleSearch {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int m = Integer.parseInt(s[1]);
    int r0 = Integer.parseInt(s[2]) - 1;
    int b0 = Integer.parseInt(s[3]) - 1;
    int y0 = Integer.parseInt(s[4]) - 1;
    Node[] nodes = new Node[n];
    Edge[] edges = new Edge[m];
    for (int i=0; i<n; i++) {
      nodes[i] = new Node(i);
    }
    for (int i=0; i<m; i++) {
      s = br.readLine().split(" ");
      int u = Integer.parseInt(s[0]) - 1;
      int v = Integer.parseInt(s[1]) - 1;
      char c = s[2].charAt(0);
      Edge e1 = new Edge(i, u, v, c);
      Edge e2 = new Edge(i, v, u, c);
      switch (c) {
        case 'X':
          nodes[u].adjX.add(e1);
          nodes[v].adjX.add(e2);
          break;
        case 'R':
          nodes[u].adjR.add(e1);
          nodes[v].adjR.add(e2);
          break;
        case 'B':
          nodes[u].adjB.add(e1);
          nodes[v].adjB.add(e2);
          break;
        case 'Y':
          nodes[u].adjY.add(e1);
          nodes[v].adjY.add(e2);
          break;
        case 'O':
          nodes[u].adjO.add(e1);
          nodes[v].adjO.add(e2);
          break;
        case 'P':
          nodes[u].adjP.add(e1);
          nodes[v].adjP.add(e2);
          break;
        case 'G':
          nodes[u].adjG.add(e1);
          nodes[v].adjG.add(e2);
          break;
      }
      edges[i] = e1;
    }
    boolean[] edgeVis = new boolean[m];
    boolean[][][] nodeTripleVis = new boolean[n][n][n];
    LinkedList<Triple> q = new LinkedList<>();
    q.offer(new Triple(r0,b0,y0));
    while (q.size()>0) {
      Triple curr = q.poll();
      // Move robots along unspecified edges.
      for (Edge e: nodes[curr.r].adjX) {
        Triple next = new Triple(e.v, curr.b, curr.y);
        if (!nodeTripleVis[next.r][next.b][next.y]) {
          q.offer(next);
          nodeTripleVis[next.r][next.b][next.y] = true;
        }
      }
      for (Edge e: nodes[curr.b].adjX) {
        Triple next = new Triple(curr.r, e.v, curr.y);
        if (!nodeTripleVis[next.r][next.b][next.y]) {
          q.offer(next);
          nodeTripleVis[next.r][next.b][next.y] = true;
        }
      }
      for (Edge e: nodes[curr.y].adjX) {
        Triple next = new Triple(curr.r, curr.b, e.v);
        if (!nodeTripleVis[next.r][next.b][next.y]) {
          q.offer(next);
          nodeTripleVis[next.r][next.b][next.y] = true;
        }
      }

      // Move red robot along red edges.
      for (Edge e: nodes[curr.r].adjR) {
        Triple next = new Triple(e.v, curr.b, curr.y);
        if (!nodeTripleVis[next.r][next.b][next.y]) {
          q.offer(next);
          nodeTripleVis[next.r][next.b][next.y] = true;
        } 
        edgeVis[e.id] = true;
      }
      // Move blue robot along blue edges.
      for (Edge e: nodes[curr.b].adjB) {
        Triple next = new Triple(curr.r, e.v, curr.y);
        if (!nodeTripleVis[next.r][next.b][next.y]) {
          q.offer(next);
          nodeTripleVis[next.r][next.b][next.y] = true;
        }
        edgeVis[e.id] = true;
      }
      // Move yellow robot along yellow edges.
      for (Edge e: nodes[curr.y].adjY) {
        Triple next = new Triple(curr.r, curr.b, e.v);
        if (!nodeTripleVis[next.r][next.b][next.y]) {
          q.offer(next);
          nodeTripleVis[next.r][next.b][next.y] = true;
        }
        edgeVis[e.id] = true;
      }
      if (curr.r==curr.b) {
        // Move red and blue along purple edges.
        for (Edge e: nodes[curr.r].adjP) {
          Triple next = new Triple(e.v, e.v, curr.y);
          if (!nodeTripleVis[next.r][next.b][next.y]) {
            q.offer(next);
            nodeTripleVis[next.r][next.b][next.y] = true;
          }
          edgeVis[e.id] = true;
        }
      }
      if (curr.r==curr.y) {
        // Move red and yellow along orange edges.
        for (Edge e: nodes[curr.r].adjO) {
          Triple next = new Triple(e.v, curr.b, e.v);
          if (!nodeTripleVis[next.r][next.b][next.y]) {
            q.offer(next);
            nodeTripleVis[next.r][next.b][next.y] = true;
          }
          edgeVis[e.id] = true;
        }
      }
      if (curr.b==curr.y) {
        // Move blue and yellow along green edges.
        for (Edge e: nodes[curr.b].adjG) {
          Triple next = new Triple(curr.r, e.v, e.v);
          if (!nodeTripleVis[next.r][next.b][next.y]) {
            q.offer(next);
            nodeTripleVis[next.r][next.b][next.y] = true;
          }
          edgeVis[e.id] = true;
        }
      }
    }
    for (int i=0; i<m; i++) {
      if (!edgeVis[i] && edges[i].c!='X') {
        System.out.println(0);
        return;
      }
    }
    System.out.println(1);
  }
}
class Node {
  int id;
  ArrayList<Edge> adjX;
  ArrayList<Edge> adjR;
  ArrayList<Edge> adjB;
  ArrayList<Edge> adjY;
  ArrayList<Edge> adjO;
  ArrayList<Edge> adjP;
  ArrayList<Edge> adjG;

  public Node(int idd) {
    id = idd;
    adjX = new ArrayList<Edge>();
    adjR = new ArrayList<Edge>();
    adjB = new ArrayList<Edge>();
    adjY = new ArrayList<Edge>();
    adjO = new ArrayList<Edge>();
    adjP = new ArrayList<Edge>();
    adjG = new ArrayList<Edge>();
  }
}
class Edge {
  int id;
  int u,v;
  char c;
  public Edge(int idd, int uu, int vv, char cc) {
    id = idd;
    u = uu;
    v = vv;
    c = cc;
  }
}
class Triple {
  int r,b,y;
  public Triple(int rr, int bb, int yy) {
    r = rr;
    b = bb;
    y = yy;
  }
  public int hashCode() {
    return Objects.hash(r,b,y);
  }
  public boolean equals(Object o) {
    Triple t2 = (Triple)o;
    return r==t2.r && b==t2.b && y==t2.y;
  }
}
