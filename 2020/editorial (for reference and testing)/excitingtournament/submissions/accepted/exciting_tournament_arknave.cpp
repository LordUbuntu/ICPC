#include <algorithm>
#include <array>
#include <cassert>
#include <cstdint>
#include <iostream>
#include <tuple>
#include <vector>

using namespace std;

namespace mcmf {
    const int MAXV = 500;
    const int MAXE = 500 * 500;
    const int64_t oo = 1e18;

    int V, E;
    int last[MAXV], curr[MAXV], bio[MAXV]; int64_t pi[MAXV];
    int next[MAXE], adj[MAXE]; int64_t cap[MAXE], cost[MAXE];

    void init(int n) {
        assert(n < MAXV);
        V = n;
        E = 0;
        for (int i = 0; i < V; ++i) {
            last[i] = -1;
        }
    }

    void edge(int x, int y, int64_t c, int64_t w) {
        adj[E] = y; cap[E] = c; cost[E] = +w; next[E] = last[x]; last[x] = E++;
        adj[E] = x; cap[E] = 0; cost[E] = -w; next[E] = last[y]; last[y] = E++;
        assert(E < MAXE);
    }

    int64_t push(int x, int sink, int64_t flow) {
        if (x == sink) return flow;
        if (bio[x]) return 0;
        bio[x] = true;

        for (int &e = curr[x]; e != -1; e = next[e]) {
            int y = adj[e];

            if (cap[e] && pi[x] == pi[y] + cost[e])
                if (int64_t f = push(y, sink, min(flow, cap[e])))
                    return cap[e] -= f, cap[e^1] += f, f;
        }
        return 0;
    }

    pair<int64_t , int64_t > run(int src, int sink) {
        int64_t total = 0;
        int64_t flow = 0;

        for (int i = 0; i < V; ++i) {
            pi[i] = oo;
        }
        pi[sink] = 0;

        for (;;) {
            bool done = true;
            for (int x = 0; x < V; ++x)
                for (int e = last[x]; e != -1; e = next[e])
                    if (cap[e] && pi[x] > pi[adj[e]] + cost[e])
                        pi[x] = pi[adj[e]] + cost[e], done = false;
            if (done) break;
        }

        for (;;) {
            for (int i = 0; i < V; ++i)
                bio[i] = false;
            for (int i = 0; i < V; ++i)
                curr[i] = last[i];

            while (int64_t f = push(src, sink, oo)) {
                total += pi[src] * f;
                flow += f;
                for (int i = 0; i < V; ++i) bio[i] = false;
            }

            int64_t inc = oo;
            for (int x = 0; x < V; ++x) if (bio[x]) {
                for (int e = last[x]; e != -1; e = next[e]) {
                    int y = adj[e];
                    if (cap[e] && !bio[y]) inc = min(inc, pi[y] + cost[e] - pi[x]);
                }
            }
            if (inc == oo) break;

            for (int i = 0; i < V; ++i) if (bio[i]) pi[i] += inc;
        }
        return make_pair(total, flow);
    }
}

int main() {
    // Solution idea: Every match eliminates a player.
    // Build a bipartite graph, each side has N nodes.
    // sink -> lhs node has capacity g_i - 1, cost 0
    // lhs -> rhs has capacity capacity 1, cost si ^ sj
    // rhs -> sink has capacity 1 cost 0
    // A path from src to sink represents 1 game where si eliminates sj
    // Order the games so the lowest skill player remaining is always eliminated first
    // Now run MCMF with both positive and negative weights
    //
    // TRICK: the strongest player cannot lose, so their capacity for wins is g, not g - 1

    int n;
    cin >> n;

    vector<pair<int, int>> a(n);
    for (int i = 0; i < n; ++i) {
        cin >> a[i].first >> a[i].second;
    }

    sort(a.begin(), a.end());

    array<int64_t, 2> ans;
    int ptr = 0;
    for (int sgn = 1; sgn >= -1; sgn -= 2) {
        int src = n + n;
        int sink = src + 1;
        mcmf::init(sink + 1);
        for (int i = 0; i < n; ++i) {
            int s, g;
            tie(s, g) = a[i];

            int cap = (i < n - 1) ? g - 1 : g;

            mcmf::edge(src, i, cap, 0);
            mcmf::edge(n + i, sink, 1, 0);

            for (int j = 0; j < i; ++j) {
                int cost = s ^ a[j].first;

                mcmf::edge(i, n + j, 1, sgn * cost);
            }
        }

        auto res = mcmf::run(src, sink);
        ans[ptr++] = sgn * res.first;
    }

    cout << ans[0] << " " << ans[1] << '\n';

    return 0;
}
