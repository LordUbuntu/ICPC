import java.io.*;
import java.util.*;
public class Pushups {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int t = scan.nextInt();
		for(int casenum = 1; casenum <= t; casenum++) {
			int n = scan.nextInt();
			int m = scan.nextInt();
			int[] list = new int[m];
			for(int i = 0; i < m; i++) {
				list[i] = scan.nextInt();
			}
			System.out.println(solve(n, m, list));
		}
	}

	public static int solve(int n, int m, int[] list) {
		boolean[][] seen = new boolean[n+1][n+1];
		seen[0][0] = true;
		LinkedList<State> q = new LinkedList<State>();
		q.add(new State(0, 0));
		while(!q.isEmpty()) {
			State curr = q.removeFirst();
			for(int out: list) {
				int nS = curr.score + out;
				int nP = curr.pushup + nS;
				if(nP <= n && !seen[nS][nP]) {
					seen[nS][nP] = true;
					q.add(new State(nS, nP));
				}
			}
		}
		for(int i = n; i >= 0; i--) {
			if(seen[i][n]) return i;
		}
		return -1;
	}

	static class State {
		public int score, pushup;

		public State(int score, int pushup) {
			super();
			this.score = score;
			this.pushup = pushup;
		}

	}
}

