import java.util.*;
public class NumberGame2 {
	static Set<State> seen, win;
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		int t = scan.nextInt();
		seen = new HashSet<State>();
		win = new HashSet<State>();
		for(int casenum = 1; casenum <= t; casenum++) {
			int n = scan.nextInt();
			int[] list = new int[n];
			int index = -1;
			for(int i = 0; i < n; i++) {
				list[i] = scan.nextInt();
				if(list[i] == 1) {
					index = i;
				}
			}
			int numInInterval = 1;
			int left = index;
			while(left > 0) {
				if(list[left-1] < list[left]) break;
				numInInterval++;
				left--;
			}
			int leftleft = left;
			int leftAvail = 0;
			while(leftleft > 0) {
				if(list[leftleft-1] > list[leftleft]) break;
				leftleft--;
				leftAvail++;
			}
			int right = index;
			while(right < n-1) {
				if(list[right+1] < list[right]) break;
				numInInterval++;
				right++;
			}
			int rightright = right;
			int rightAvail = 0;
			while(rightright < n-1) {
				if(list[rightright+1] > list[rightright]) break;
				rightright++;
				rightAvail++;
			}
			System.out.println(solve(left, right, leftAvail, rightAvail, n - numInInterval - leftAvail - rightAvail) ? "Alice" : "Bob");
		}
	}
	
	public static boolean solve(int left, int right, int leftAvail, int rightAvail, int other) {
		if(left == right) return true;
		State key = new State(left, right, leftAvail, rightAvail, other);
		if(seen.contains(key)) {
			return win.contains(key);
		}
		seen.add(key);
		boolean winwin = false;
		if(!solve(left+1, right, 0, rightAvail, other + leftAvail)) winwin = true;
		if(!solve(left, right-1, leftAvail, 0, other + rightAvail)) winwin = true;
		if(other > 0 && !solve(left, right, leftAvail, rightAvail, other-1)) winwin = true;
		if(winwin) {
			win.add(key);
		}
		return winwin;
	}
	
	static class State {
		public int left, right, leftAvail, rightAvail, other;

		public State(int left, int right, int leftAvail, int rightAvail,
				int other) {
			super();
			this.left = left;
			this.right = right;
			this.leftAvail = leftAvail;
			this.rightAvail = rightAvail;
			this.other = other;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + left;
			result = prime * result + leftAvail;
			result = prime * result + other;
			result = prime * result + right;
			result = prime * result + rightAvail;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (left != other.left)
				return false;
			if (leftAvail != other.leftAvail)
				return false;
			if (this.other != other.other)
				return false;
			if (right != other.right)
				return false;
			if (rightAvail != other.rightAvail)
				return false;
			return true;
		}
		
	}
	
}

