import java.util.*;
public class Knights {
	static final int MOD = 1000000009;
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		int t = scan.nextInt();
		for(int casenum = 1; casenum <= t; casenum++) {
			int r = scan.nextInt();
			int c = scan.nextInt();
			if(c == 1) {
				System.out.println(1 << r);
				continue;
			}
			final int BOTTOM = (1 << r) - 1;
			long[][] translationMatrix = new long[1 << (2 * r)][1 << (2 * r)];
			for(int i = 0; i < translationMatrix.length; i++) {
				int leftCol = i & BOTTOM;
				int rightCol = (i >> r);
				if((leftCol&(rightCol<<2)) != 0) continue;
				if((rightCol&(leftCol<<2)) != 0) continue;
				for(int j = 0; j < translationMatrix.length; j++) {
					if(rightCol != (j & BOTTOM)) continue;
					int thirdCol = (j >> r);
					if((thirdCol & (rightCol << 2)) != 0) continue;
					if((rightCol & (thirdCol << 2)) != 0) continue;
					if((thirdCol & (leftCol << 1)) != 0) continue;
					if((leftCol & (thirdCol << 1)) != 0) continue;
					translationMatrix[i][j] = 1;
				}
			}
			if(c > 2)
				translationMatrix = pow(translationMatrix, c - 2);
			long[][] initial = new long[1 << (2*r)][1];
			for(int i = 0; i < 1 << r; i++) {
				for(int j = 0; j < 1 << r; j++) {
					if((i&(j<<2))!=0) continue;
					if((j&(i<<2))!=0) continue;
					initial[i|(j<<r)][0] = 1;
				}
			}
			if(c > 2) {
				initial = mult(translationMatrix, initial);
			}
			long ret = 0;
			for(int i = 0; i < initial.length; i++) {
				ret += initial[i][0];
				ret %= MOD;
			}
			System.out.println(ret);
		}
	}

	public static long[][] pow(long[][] b, long e) {
		if(e == 1) return b;
		if(e % 2 == 0) {
			long[][] x = pow(b, e/2);
			return mult(x, x);
		}
		return mult(b, pow(b, e-1));
	}

	public static long[][] mult(long[][] a, long[][] b) {
		long[][] c = new long[a.length][b[0].length];
		for(int i = 0; i < c.length; i++) {
			for(int j = 0; j < c[i].length; j++) {
				for(int k = 0; k < a[i].length; k++) {
					c[i][j] += a[i][k] * b[k][j];
					c[i][j] %= MOD;
				}
			}
		}
		return c;
	}

}

