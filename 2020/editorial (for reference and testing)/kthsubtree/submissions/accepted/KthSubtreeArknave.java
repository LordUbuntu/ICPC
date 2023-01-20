import java.io.*;
import java.util.*;

public class KthSubtreeArknave {
    static final long INF = (long)3e18;
    public static void main(String[] args) throws IOException {
        Solver solver = new Solver();
        solver.solve();
    }

    static class Solver {
        long[] ans;
        List<Integer>[] tree;

        public void solve() throws IOException {
            Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();
            long k = scan.nextLong();

            tree = new List[n];
            for (int i = 0; i < n; i++) {
                tree[i] = new ArrayList<>();
            }

            for (int i = 1; i < n; i++) {
                int u = scan.nextInt() - 1;
                int v = scan.nextInt() - 1;

                tree[u].add(v);
                tree[v].add(u);
            }

            ans = new long[n + 1];

            dfs(0, -1);

            // System.err.println(Arrays.toString(ans));

            --k;
            for (int i = 0; i < n; i++) {
                if (k < ans[i]) {
                    System.out.println(i + 1);
                    System.exit(0);
                }

                k -= ans[i];
            }

            System.out.println(-1);
        }

        public long[] dfs(int u, int p) {
            long[] dp = new long[1];
            dp[0] = 1;

            for (int v : tree[u]) {
                if (v == p) {
                    continue;
                }

                dp = merge(dp, dfs(v, u));
            }

            for (int i = 0; i < dp.length; i++) {
                ans[i] = safeAdd(ans[i], dp[i]);
            }

            return dp;
        }

        public long[] merge(long[] a, long[] b) {
            long[] c = new long[a.length + b.length];

            for (int i = 0; i < a.length; i++) {
                c[i] = safeAdd(c[i], a[i]);
                for (int j = 0; j < b.length; j++) {
                    c[i + j + 1] = safeAdd(c[i + j + 1], safeMul(a[i], b[j]));
                }
            }

            return c;
        }

        long safeAdd(long a, long b) {
            return Math.min(INF, a + b);
        }

        long safeMul(long a, long b) {
            if (b != 0 && a > (INF + b - 1) / b) {
                return INF;
            } else {
                return a * b;
            }
        }
    }
}
