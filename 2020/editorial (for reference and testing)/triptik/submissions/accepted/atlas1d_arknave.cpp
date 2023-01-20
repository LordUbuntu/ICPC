#include <algorithm>
#include <cassert>
#include <iostream>
#include <queue>
#include <set>
#include <tuple>
#include <utility>
#include <vector>

#define DEBUG 0

using namespace std;

using ll = long long;

constexpr int INF = 1e9 + 7;

int main() {
    int n, k;
    cin >> n >> k;

    vector<ll> pts(n);
    ll max_log = 0;

    {
        ll min_pt = 0;
        ll max_pt = 0;
        for (int i = 0; i < n; ++i) {
            cin >> pts[i];

            min_pt = min(min_pt, pts[i]);
            max_pt = max(max_pt, pts[i]);
        }
        // add our starting node at 0 to the end of the list
        pts.emplace_back(0);

        while ((1LL << max_log) < max_pt - min_pt) {
            ++max_log;
        }
    }
    max_log++;

    vector<vector<vector<int>>> visible(max_log, vector<vector<int>>(n + 1));
    // at zoom level 0, only one lattice point
    for (int i = 0; i < n; ++i) {
        visible[0][i].push_back(i);
    }

    // for each zoom level, find the appropraite points
    for (int z = 1; z < max_log; ++z) {
        if (DEBUG) {
            printf("Building radius %d\n", z);
        }

        ll radius = (1LL << (z - 1));

        // There are 3 types of events:
        // Add a point
        // Remove a point
        // Query the top k points

        using event = tuple<ll, int, int>;
        vector<event> events;
        events.reserve(3 * n + 1);

        for (int i = 0; i <= n; ++i) {
            ll pt = pts[i];
            if (i < n) {
                events.emplace_back(pt - radius, -1, i);
                events.emplace_back(pt + radius, +1, i);
            }

            events.emplace_back(pt, +0, i);
        }

        sort(events.begin(), events.end());
    
        // We can keep track of all of these with a set
        set<int, greater<int>> s;
        for (auto [x, d, idx] : events) {
            if (d == 0) {
                for (auto it = s.begin(); it != s.end() && static_cast<int>(visible[z][idx].size()) < k; ++it) {
                    visible[z][idx].push_back(*it);
                }
            } else if (d == -1) {
                s.insert(idx);
            } else {
                assert(d == +1);
                s.erase(s.find(idx));
            }
        }
    }

    auto sees_self = [&](int z, int center) {
        const auto& adj = visible[z][center];
        auto it = find(adj.begin(), adj.end(), center);

        return it != adj.end();
    };

    queue<pair<int, int>> q;
    q.emplace(1, n);

    vector<vector<int>> dist(max_log, vector<int>(n + 1, INF));
    dist[1][n] = 0;

    vector<int> ans(n, INF);
    while (!q.empty()) {
        int z, center;
        tie(z, center) = q.front();
        q.pop();

        if (DEBUG) {
            printf("At point %d zoom level %d\n", center, z);
        }

        assert(0 <= z && z < max_log);
        assert(0 <= center && center <= n);

        int cur_dist = dist[z][center];

        if (center < n && ans[center] == INF && sees_self(z, center)) {
            ans[center] = cur_dist;
        }

        // zoom in
        if (z > 0 && cur_dist + 1 < dist[z - 1][center]) {
            dist[z - 1][center] = cur_dist + 1;
            q.emplace(z - 1, center);
        }

        // zoom out
        if (z + 1 < max_log && cur_dist + 1 < dist[z + 1][center]) {
            dist[z + 1][center] = cur_dist + 1;
            q.emplace(z + 1, center);
        }

        // recenter
        for (int new_center : visible[z][center]) {
            if (new_center < n && cur_dist + 1 < dist[z][new_center]) {
                dist[z][new_center] = cur_dist + 1;
                q.emplace(z, new_center);
            }
        }
    }

    for (int& x : ans) {
        if (x == INF) x = -1;
        cout << x << '\n';
    }

    return 0;
}
