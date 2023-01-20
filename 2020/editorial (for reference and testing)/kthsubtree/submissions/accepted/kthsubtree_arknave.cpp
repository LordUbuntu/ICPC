#include <cassert>
#include <iostream>
#include <vector>

using namespace std;

constexpr int DEBUG = 0;

using ll = long long;

constexpr ll INF = 2e18;

ll safe_add(ll a, ll b) {
    if (a > INF - b) {
        return INF;
    } else {
        return a + b;
    }
}

ll safe_mul(ll a, ll b) {
    if (b != 0 and a > (INF + b - 1) / b) {
        return INF;
    } else {
        return a * b;
    }
}

vector<ll> merge(const vector<ll>& lhs, const vector<ll>& rhs) {
    int n = lhs.size();
    int m = rhs.size();
    vector<ll> res(n + m - 1, 0);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            res[i + j] = safe_add(res[i + j], safe_mul(lhs[i], rhs[j]));
        }

        res[i] = safe_add(res[i], lhs[i]);
    }

    assert(res.back() > 0);
    return res;
}

int main() {
    int n;
    ll k;
    cin >> n >> k;

    vector<vector<int>> tree(n);
    for (int i = 1; i < n; ++i) {
        int u, v;
        cin >> u >> v;
        --u; --v;

        tree[u].push_back(v);
        tree[v].push_back(u);
    }

    vector<ll> ans(n + 1, 0);
    vector<vector<ll>> dp(n);
    auto dfs = [&](auto self, int u, int p) -> void {
        dp[u] = {0, 1};
        for (int v : tree[u]) {
            if (v == p) continue;

            self(self, v, u);
            dp[u] = merge(dp[u], dp[v]);
        }

        assert(dp[u].size() <= ans.size());
        for (int i = 0; i < dp[u].size(); ++i) {
            ans[i] = safe_add(ans[i], dp[u][i]);
        }
    };

    dfs(dfs, 0, -1);

    if (DEBUG) {
        for (int i = 0; i <= n; ++i) {
            cerr << ans[i] << " ";
        }
        cerr << endl;
    }

    --k;
    for (int i = 0; i <= n; ++i) {
        if (k < ans[i]) {
            cout << i << '\n';
            return 0;
        }

        k -= ans[i];
    }

    cout << -1 << '\n';
    return 0;
}
