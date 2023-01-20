import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.PriorityQueue;
import java.util.AbstractQueue;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractCollection;
import java.util.stream.Stream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author lewin
 */
public class exciting_tournament_lewin {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        excitingtournament solver = new excitingtournament();
        solver.solve(1, in, out);
        out.close();
    }

    static class excitingtournament {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.nextInt();
            excitingtournament.Player[] ps = new excitingtournament.Player[n];
            for (int i = 0; i < n; i++) {
                ps[i] = new excitingtournament.Player(in.nextInt(), in.nextInt());
            }
            Arrays.sort(ps, Comparator.comparingInt(x -> x.skill));
            for (int i = 0; i < n - 1; i++) {
                ps[i].limit--;
            }
            long val1 = 0, val2 = 0 ;
            for (int sign : new int[]{-1, 1}) {
                int nnodes = 2 * n + 2;
                List<MinCostFlowLong.Edge>[] graph = LUtils.genArrayList(nnodes);
                for (int i = 0; i < n; i++) {
                    MinCostFlowLong.addEdge(graph, nnodes - 1, i, ps[i].limit, 0);
                    if (i < n - 1) MinCostFlowLong.addEdge(graph, i + n, nnodes - 2, 1, 0);
                    for (int j = 0; j < i; j++) {
                        MinCostFlowLong.addEdge(graph, i, j + n, 1, sign * (ps[i].skill ^ ps[j].skill));
                    }
                }
                val1 = val2 ;
                val2 = (sign * MinCostFlowLong.minCostFlow(graph, nnodes - 1, nnodes - 2, n - 1)[1]);
            }
            System.out.println("" + val2 + " " + val1);
        }

        static class Player {
            public int skill;
            public int limit;

            public Player(int skill, int limit) {
                this.skill = skill;
                this.limit = limit;
            }

        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void close() {
            writer.close();
        }

        public void println(long i) {
            writer.println(i);
        }

    }

    static class LUtils {
        public static <E> List<E>[] genArrayList(int size) {
            return Stream.generate(ArrayList::new).limit(size).toArray(List[]::new);
        }

    }

    static class MinCostFlowLong {
        public static void addEdge(List<MinCostFlowLong.Edge>[] graph, int s, int t, int cap, long cost) {
            graph[s].add(new MinCostFlowLong.Edge(t, cap, cost, graph[t].size()));
            graph[t].add(new MinCostFlowLong.Edge(s, 0, -cost, graph[s].size() - 1));
        }

        static void bellmanFord(List<MinCostFlowLong.Edge>[] graph, int s, long[] dist) {
            int n = graph.length;
            Arrays.fill(dist, Long.MAX_VALUE);
            dist[s] = 0;
            boolean[] inqueue = new boolean[n];
            int[] q = new int[n];
            int qt = 0;
            q[qt++] = s;
            for (int qh = 0; (qh - qt) % n != 0; qh++) {
                int u = q[qh % n];
                inqueue[u] = false;
                for (int i = 0; i < graph[u].size(); i++) {
                    MinCostFlowLong.Edge e = graph[u].get(i);
                    if (e.cap <= e.f)
                        continue;
                    int v = e.to;
                    long ndist = dist[u] + e.cost;
                    if (dist[v] > ndist) {
                        dist[v] = ndist;
                        if (!inqueue[v]) {
                            inqueue[v] = true;
                            q[qt++ % n] = v;
                        }
                    }
                }
            }
        }

        public static long[] minCostFlow(List<MinCostFlowLong.Edge>[] graph, int s, int t, int maxf) {
            int n = graph.length;
            long[] prio = new long[n];
            int[] curflow = new int[n];
            int[] prevedge = new int[n];
            int[] prevnode = new int[n];
            long[] pot = new long[n];

            bellmanFord(graph, s, pot); // bellmanFord invocation can be skipped if edges costs are non-negative
            int flow = 0;
            long flowCost = 0;
            while (flow < maxf) {
                PriorityQueue<Long> q = new PriorityQueue<>();
                q.add((long) s);
                Arrays.fill(prio, Long.MAX_VALUE);
                prio[s] = 0;
                boolean[] finished = new boolean[n];
                curflow[s] = Integer.MAX_VALUE;
                while (!finished[t] && !q.isEmpty()) {
                    long cur = q.remove();
                    int u = (int) (cur & 0xF_FFFFL);
                    long priou = cur >>> 20;
                    if (priou != prio[u])
                        continue;
                    finished[u] = true;
                    for (int i = 0; i < graph[u].size(); i++) {
                        MinCostFlowLong.Edge e = graph[u].get(i);
                        if (e.f >= e.cap)
                            continue;
                        int v = e.to;
                        long nprio = prio[u] + e.cost + pot[u] - pot[v];
                        if (prio[v] > nprio) {
                            prio[v] = nprio;
                            q.add((nprio << 20) + v);
                            prevnode[v] = u;
                            prevedge[v] = i;
                            curflow[v] = Math.min(curflow[u], e.cap - e.f);
                        }
                    }
                }
                if (prio[t] == Integer.MAX_VALUE)
                    break;
                for (int i = 0; i < n; i++)
                    if (finished[i])
                        pot[i] += prio[i] - prio[t];
                int df = Math.min(curflow[t], maxf - flow);
                flow += df;
                for (int v = t; v != s; v = prevnode[v]) {
                    MinCostFlowLong.Edge e = graph[prevnode[v]].get(prevedge[v]);
                    e.f += df;
                    graph[v].get(e.rev).f -= df;
                    flowCost += df * e.cost;
                }
            }
            return new long[]{flow, flowCost};
        }

        public static class Edge {
            public int to;
            public int f;
            public int cap;
            public int rev;
            public long cost;

            Edge(int to, int cap, long cost, int rev) {
                this.to = to;
                this.cap = cap;
                this.cost = cost;
                this.rev = rev;
            }

        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1 << 16];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (this.numChars == -1) {
                throw new UnknownError();
            } else {
                if (this.curChar >= this.numChars) {
                    this.curChar = 0;

                    try {
                        this.numChars = this.stream.read(this.buf);
                    } catch (IOException var2) {
                        throw new InputMismatchException();
                    }

                    if (this.numChars <= 0) {
                        return -1;
                    }
                }

                return this.buf[this.curChar++];
            }
        }

        public int nextInt() {
            int c;
            for (c = this.read(); isSpaceChar(c); c = this.read()) {
                ;
            }

            byte sgn = 1;
            if (c == 45) {
                sgn = -1;
                c = this.read();
            }

            int res = 0;

            while (c >= 48 && c <= 57) {
                res *= 10;
                res += c - 48;
                c = this.read();
                if (isSpaceChar(c)) {
                    return res * sgn;
                }
            }

            throw new InputMismatchException();
        }

        public static boolean isSpaceChar(int c) {
            return c == 32 || c == 10 || c == 13 || c == 9 || c == -1;
        }

    }
}

