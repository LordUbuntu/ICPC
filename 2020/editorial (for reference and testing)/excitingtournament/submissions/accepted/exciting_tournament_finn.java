import java.util.*;
import java.io.*;

/**
 * Use MinCostMaxFlow. 
 * Every player except the player with the highest skill
 * will win up to gameLimits[i]-1 games and lose exactly 1 game.
 * The player with the highest skill will win up to gameLimits[i] games.
 * We represent this as a bipartite graph with 2 nodes for each player
 * plus a source and a sink. 
 * We add edges from the source to each initial player nodes with 0 cost 
 * and capacity equal to the maximum number of games that the player can 
 * win. We add edges from the final player nodes with cost 0 and capacity
 * 1. Finally, we add edges from initial player node i to final player
 * node j if i has higher skill than j. The cost is equal to the xor 
 * of the skills and the capacity is 1. Choosing such an edge in the Max
 * flow corresponds to having player i compete against player j.
 * The least exciting tournament is given by computing the Min Cost Max Flow
 * on the resulting graph.
 * To get the Max Cost Max Flow we can negate the costs and shift the nonzero
 * costs by a value larger than the largest cost. Since the max flow is
 * constant and all paths from source to sink have the same length we can 
 * just subtract off the max flow multiplied by the offset and negate the 
 * result.
 *
 * @author Finn Lidbetter
 */

public class exciting_tournament_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int n = Integer.parseInt(br.readLine());
    int[] skills = new int[n];
    int[] gameLimits = new int[n];
    int maxSkillIndex = -1;
    int maxSkill = -1;
    for (int i=0; i<n; i++) {
      String[] s = br.readLine().split(" ");
      int skill = Integer.parseInt(s[0]);
      int maxGames = Integer.parseInt(s[1]);
      skills[i] = skill;
      gameLimits[i] = maxGames;
      if (skill>maxSkill) {
        maxSkill = skill;
        maxSkillIndex = i;
      }
    }
    long[][] cap = new long[2*n + 2][2*n + 2];
    long[][] cost = new long[2*n + 2][2*n + 2];
    for (int i=0; i<n; i++) {
      cap[2*n][i] = gameLimits[i]-1;
      if (i==maxSkillIndex)
        cap[2*n][i]++;
      cap[n+i][2*n+1] = 1;
    }
    for (int i=0; i<n; i++) {
      for (int j=0; j<n; j++) {
        if (skills[i]>skills[j]) {
          cap[i][n+j] = 1;
          cost[i][n+j] = skills[i] ^ skills[j];
        }
      }
    }
    long minCost = MinCostFlow.getFlowCost(cap, cost, 2*n, 2*n+1)[1];
    
    long offset = (1L<<31);
    cap = new long[2*n + 2][2*n + 2];
    cost = new long[2*n + 2][2*n + 2];
    for (int i=0; i<n; i++) {
      cap[2*n][i] = gameLimits[i]-1;
      if (i==maxSkillIndex)
        cap[2*n][i]++;
      cap[n+i][2*n+1] = 1;
    }
    for (int i=0; i<n; i++) {
      for (int j=0; j<n; j++) {
        if (skills[i]>skills[j]) {
          cap[i][n+j] = 1;
          cost[i][n+j] = offset - (skills[i] ^ skills[j]);
        }
      }
    }
    long maxCost = offset*(n-1) - MinCostFlow.getFlowCost(cap, cost, 2*n, 2*n+1)[1];
    System.out.println(minCost+" " + maxCost);

  }
}

class MinCostFlow {
  static final long INF = Long.MAX_VALUE / 2 - 1;
  static boolean found[];
  static int N, dad[];
  static long flow[][], dist[], pi[];
  static long[] getFlowCost(long cap[][], long cost[][], int s, int t) {
    N = cap.length; found = new boolean[N]; dad = new int[N];
    flow = new long[N][N]; dist = new long[N + 1]; pi = new long[N];
    long totflow = 0, totcost = 0;
    while (search(cap, cost, s, t)) {
      long amt = INF;
      for (int x = t; x != s; x = dad[x])
        amt = Math.min(amt, flow[x][dad[x]] != 0 ? flow[x][dad[x]] : cap[dad[x]][x] - flow[dad[x]][x]);
      for (int x = t; x != s; x = dad[x]) {
        if (flow[x][dad[x]] != 0) {
          flow[x][dad[x]] -= amt;
          totcost -= amt * cost[x][dad[x]];
        } else {
          flow[dad[x]][x] += amt;
          totcost += amt * cost[dad[x]][x]; 
        } 
      }
      totflow += amt; 
    }
    return new long[] { totflow, totcost }; 
  }
  static boolean search(long cap[][], long cost[][], int s, int t) {
    Arrays.fill(found, false); Arrays.fill(dist, INF);
    dist[s] = 0;
    while (s != N) {
      int best = N; found[s] = true;
      for (int k = 0; k < N; k++) {
        if (found[k]) continue;
        if (flow[k][s] != 0) {
          long val = dist[s] + pi[s] - pi[k] - cost[k][s];
          if (dist[k] > val) { dist[k] = val; dad[k] = s; } }
        if (flow[s][k] < cap[s][k]) {
          long val = dist[s] + pi[s] - pi[k] + cost[s][k];
          if (dist[k] > val) { dist[k] = val; dad[k] = s; } }
        if (dist[k] < dist[best]) best = k; }
      s = best; }
    for (int k = 0; k < N; k++) pi[k] = Math.min(pi[k] + dist[k], INF);
    return found[t]; 
  }
}

