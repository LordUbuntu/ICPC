import java.util.*;
import java.io.*;

/**
 * This submission constructs a DAG on the letters.
 * There is an edge from letter 1 to letter 2 if in all
 * strings letter 2 comes after letter 1.
 * Find the longest path in this DAG using dynamic programming.
 *
 * @author Finn Lidbetter
 */

public class lcs_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int k = Integer.parseInt(s[1]);
    
    boolean[][] allowed = new boolean[k][k];
    for (int i=0; i<k; i++) {
      Arrays.fill(allowed[i], true);
    }
    for (int i=0; i<n; i++) {
      char[] seq = br.readLine().toCharArray();
      for (int j=0; j<k; j++) {
        char c1 = seq[j];
        for (int l=j+1; l<k; l++) {
          char c2 = seq[l];
          allowed[c2-'A'][c1-'A'] = false;
        }
      }
    }
    
    int[] dp = new int[k];
    int[] topOrder = topologicalSort(allowed);
    for (int i=topOrder.length-1; i>=0; i--) {
      int curr = topOrder[i];
      int longest = 0;
      for (int j=0; j<k; j++) {
        if (allowed[curr][j] && dp[j]>longest) {
          longest = dp[j];
        }
      }
      dp[curr] = longest + 1;
    }
    int best = 0;
    for (int i=0; i<k; i++) {
      if (dp[i]>best)
        best = dp[i];
    }
    System.out.println(best);

  }
  static int[] topologicalSort(boolean[][] adj) {
    int n = adj.length;
    boolean[] visited = new boolean[n];
    int[] order = new int[n];
    int index = n - 1;
    for (int u = 0; u < n; u++)
      if (!visited[u]) 
        index = visit(adj, visited, order, index, u);
    return order; 
  }
  static int visit(boolean[][] adj, boolean[] visited, int[] order, int i, int u) {
    visited[u] = true;
    for (int v = 0; v < adj.length; v++)
      if (adj[u][v] && !visited[v]) 
        i = visit(adj, visited, order, i, v);
    order[i] = u; 
    return i - 1; 
  }

}
