import java.io.*;
import java.util.*;
public class SalaryInequity {
	static BufferedReader br;
	static PrintWriter pw;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numCases = Integer.parseInt(br.readLine());
		for(int casenum = 0; casenum < numCases; casenum++) {
			solve();
		}
		pw.close();
	}

	static int[] parent;
	static LinkedList<Integer>[] children;
	static int n;
	static int[] startInterval;
	static int[] numNodesInSubtree;
	static RangeTree tree;
	
	public static void solve() throws IOException {
		n = Integer.parseInt(br.readLine());
		generateTree();
		int numOperations = Integer.parseInt(br.readLine());
		for(int i = 0; i < numOperations; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String type = st.nextToken();
			int root = Integer.parseInt(st.nextToken()) - 1;
			if(type.equals("Q")) {
				pw.println(tree.query(startInterval[root], startInterval[root] + numNodesInSubtree[root] - 1));
			}
			else {
				int val = Integer.parseInt(st.nextToken());
				tree.update(startInterval[root], startInterval[root] + numNodesInSubtree[root] - 1, val);
			}
		}
	}
		
	public static void generateTree() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		children = new LinkedList[n];
		int[] numDirectChildren = new int[n];
		numNodesInSubtree = new int[n];
		Arrays.fill(numNodesInSubtree, 1);
		for(int i = 0; i < n; i++) {
			children[i] = new LinkedList<Integer>();
		}
		parent = new int[n];
		parent[0] = -1;
		for(int i = 1; i < n; i++) {
			parent[i] = Integer.parseInt(st.nextToken())-1;
			children[parent[i]].add(i);
			numDirectChildren[parent[i]]++;
		}
		LinkedList<Integer> q = new LinkedList<Integer>();
		for(int i = 0; i < n; i++) {
			if(numDirectChildren[i] == 0) {
				q.add(i);
			}
		}
		while(!q.isEmpty()) {
			int curr = q.removeFirst();
			if(parent[curr] < 0) {
				break;
			}
			numNodesInSubtree[parent[curr]] += numNodesInSubtree[curr];
			if(--numDirectChildren[parent[curr]] == 0) {
				q.add(parent[curr]);
			}
		}
		startInterval = new int[n];
		q.add(0);
		while(!q.isEmpty()) {
			int curr = q.removeFirst();
			int currStart = startInterval[curr]+1;
			for(int child: children[curr]) {
				startInterval[child] = currStart;
				currStart += numNodesInSubtree[child];
				q.add(child);
			}
		}
		tree = new RangeTree(n);
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++) {
			tree.update(startInterval[i], startInterval[i], Integer.parseInt(st.nextToken()));
		}
	}
	
	static class RangeTree {
		int[] lazy, min, max;
		int size;
		public RangeTree(int n) {
			size = n;
			lazy = new int[4*n];
			min = new int[4*n];
			max = new int[4*n];
		}
		public void propagate(int treeIndex) {
			lazy[2*treeIndex] += lazy[treeIndex];
			lazy[2*treeIndex+1] += lazy[treeIndex];
			lazy[treeIndex] = 0;
		}
		public void fix(int treeIndex) {
			min[treeIndex] = Math.min(min[2*treeIndex] + lazy[2*treeIndex], min[2*treeIndex+1] + lazy[2*treeIndex+1]);
			max[treeIndex] = Math.max(max[2*treeIndex] + lazy[2*treeIndex], max[2*treeIndex+1] + lazy[2*treeIndex+1]);
		}
		public void update(int left, int right, int inc) {
			update(1, 0, size-1, left, right, inc);
		}
		public void update(int treeIndex, int treeLeft, int treeRight, int iLeft, int iRight, int val) {
			if(iLeft > treeRight) return;
			if(iRight < treeLeft) return;
			if(treeLeft >= iLeft && treeRight <= iRight) {
				lazy[treeIndex] += val;
				return;
			}
			propagate(treeIndex);
			int treeMid = (treeLeft + treeRight) / 2;
			update(2*treeIndex, treeLeft, treeMid, iLeft, iRight, val);
			update(2*treeIndex+1, treeMid+1, treeRight, iLeft, iRight, val);
			fix(treeIndex);
		}
		public int query(int left, int right) {
			return maxQuery(1, 0, size-1, left, right) - minQuery(1, 0, size-1, left, right);
		}
		public int maxQuery(int treeIndex, int treeLeft, int treeRight, int iLeft, int iRight) {
			if(iLeft > treeRight) return 0;
			if(iRight < treeLeft) return 0;
			if(treeLeft >= iLeft && treeRight <= iRight) {
				return lazy[treeIndex] + max[treeIndex];
			}
			propagate(treeIndex);
			int treeMid = (treeLeft + treeRight) / 2;
			int ret = maxQuery(2*treeIndex, treeLeft, treeMid, iLeft, iRight);
			ret = Math.max(ret, maxQuery(2*treeIndex+1, treeMid+1, treeRight, iLeft, iRight));
			fix(treeIndex);
			return ret;
		}
		public int minQuery(int treeIndex, int treeLeft, int treeRight, int iLeft, int iRight) {
			if(iLeft > treeRight) return Integer.MAX_VALUE;
			if(iRight < treeLeft) return Integer.MAX_VALUE;
			if(treeLeft >= iLeft && treeRight <= iRight) {
				return lazy[treeIndex] + min[treeIndex];
			}
			propagate(treeIndex);
			int treeMid = (treeLeft + treeRight) / 2;
			int ret = minQuery(2*treeIndex, treeLeft, treeMid, iLeft, iRight);
			ret = Math.min(ret, minQuery(2*treeIndex+1, treeMid+1, treeRight, iLeft, iRight));
			fix(treeIndex);
			return ret;
		}
		
	}
}

