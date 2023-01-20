import java.util.*;
import java.io.*;

/**
 * The complexity of this solution is something like O(n^n),
 * although it could easily be improved to O((n-1)^n).
 *
 * Try all possible condorcet scenarios and calculate the minimum
 * number of additional votes needed in each case.
 * In cases where all candidates are within 2 votes of reaching
 * condorcet (and no-one is ahead by more than 2), do a brute force
 * search of the states to get the minimum needed. Otherwise,
 * we will need just enough votes to get the person furthest from 
 * beating their "target" in the scenario to pull ahead.
 *
 * @author Finn Lidbetter
 */

public class condorcet_finn {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    String[] s = br.readLine().split(" ");
    int n = Integer.parseInt(s[0]);
    int m = Integer.parseInt(s[1]);
    // vs[i][j] is the number of votes for which i is preferred to j.
    // vs[i][j] = -vs[j][i]
    long[][] vs = new long[n][n];
    for (int i=0; i<m; i++) {
      s = br.readLine().split(" ");
      char[] seq = s[0].toCharArray();
      int k = Integer.parseInt(s[1]);
      for (int j=0; j<n; j++) {
        int winner = seq[j]-'A';
        for (int l=j+1; l<n; l++) {
          int loser = seq[l]-'A';
          vs[winner][loser] += k;
          vs[loser][winner] -= k;
        }
      }
    }
    // beater[i] = j means: j beats i head-to-head.
    int[] beater = new int[n];
    long best = Long.MAX_VALUE;
    boolean done = false;
    while (!done) {
      if (isValid(beater)) {
        long[] matchups = new long[n];
        for (int i=0; i<n; i++) {
          matchups[i] = vs[beater[i]][i];
        }
        Arrays.sort(matchups);
        if (matchups[0]>0) {
          best = 0;
          System.out.println(0);
          return;
        }
        long req = 0;
        boolean skip = false;
        if (matchups[n-1]<=2 && matchups[0]>=-2) {
          // Brute force cases where the number of votes needed is small.
          req = search(matchups);
        } else {
          int diffSum = 0;
          for (int i=1; i<n-1; i++) {
            diffSum += matchups[i]-matchups[0];
          }
          if (matchups[n-1]+diffSum<=-matchups[0]) {
            skip = true;
          }
          // We need at least enough votes to get the furthest behind candidate
          // ahead of their target. While doing this, we should be able to distribute
          // the other votes such that everyone else stays ahead of their target (unless
          // we hit the skip condition). If the skip condition is met, there is guaranteed
          // to be a better set of matchups, so we can skip calculating the actual value
          // needed.
          req = -matchups[0] + 1;
        }
        if (!skip && req<best) {
          best = req;
        }
      }
      for (int i=0; i<n; i++) {
        if (beater[i]==n-1) {
          beater[i] = 0;
          if (i==n-1) {
            done = true;
          }
        } else {
          beater[i]++;
          break;
        }
      }
    }
    System.out.println(best);
  }
  
  // Make sure that is no pair that beat one another.
  // Make sure that no-one beats themselves.
  static boolean isValid(int[] beater) {
    int n = beater.length;
    for (int i=0; i<n; i++) {
      if (beater[i]==i)
        return false;
      if (beater[beater[i]]==i)
        return false;
    }
    return true;
  }

  // Brute force search small cases.
  static long search(long[] initial) {
    LinkedList<State> q = new LinkedList<>();
    HashSet<State> vis = new HashSet<>();
    q.offer(new State(initial));
    vis.add(new State(initial));
    q.offer(null);
    int depth = 0;
    int n = initial.length;
    while (q.size()>1) {
      State curr = q.poll();
      if (curr==null) {
        q.offer(null);
        depth++;
        continue;
      }
      boolean good = true;
      for (int i=0; i<n; i++) {
        if (curr.arr[i]<=0) {
          good = false;
        }
      }
      if (good)
        return depth;
      long[] next = new long[n];
      for (int i=0; i<n; i++) {
        next[i] = curr.arr[i];
      }
      for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
          if (i==j) {
            next[j]--;
          } else {
            next[j]++;
          }
        }
        State nextState = new State(next);
        if (!vis.contains(nextState)) {
          vis.add(nextState);
          q.offer(nextState);
        }
        for (int j=0; j<n; j++) {
          if (i==j) {
            next[j]++;
          } else {
            next[j]--;
          }
        }
      }
    }
    return Long.MAX_VALUE;
  }
}
class State {
  long[] arr;
  public State(long[] vals) {
    arr = new long[vals.length];
    for (int i=0; i<vals.length; i++) {
      arr[i] = vals[i];
    }
  }
  public int hashCode() {
    return Arrays.hashCode(arr);
  }
  public boolean equals(Object o) {
    State s2 = (State)o;
    for (int i=0; i<arr.length; i++) {
      if (arr[i]!=s2.arr[i])
        return false;
    }
    return true;
  }
}
