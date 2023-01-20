#include <algorithm>
#include <cstdint>
#include <iostream>
#include <vector>

using namespace std;

constexpr int DEBUG = 0;

template <typename T>
struct BIT {
    BIT(int n): f_tree(n + 1, T()) {}

    void update(size_t idx, T v) {
        for (++idx; idx < f_tree.size(); idx += (idx & -idx)) {
            f_tree[idx] += v;
        }
    }

    T query(size_t idx) const {
        T res = T();
        for (++idx; idx > 0; idx -= (idx & -idx)) {
            res += f_tree[idx];
        }

        return res;
    }

    vector<T> f_tree;
};

int main() {
    int n;
    cin >> n;
    vector<int> a(n);
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }

    vector<int> b(a);
    sort(b.begin(), b.end());

    vector<int> pos(n);
    BIT<int> bit(n);
    for (int i = 0; i < n; ++i) {
        a[i] = lower_bound(b.begin(), b.end(), a[i]) - b.begin();
        pos[a[i]] = i;

        bit.update(i, 1);
    }

    // a is now a permutation of 0..n-1 

    // the smallest value has to be at either the beginning or the end
    // process the values from low to high. For each value, greedily stick it
    // at the shorter end

    int64_t ans = 0;
    for (int v = 0; v < n; ++v) {
        int i = pos[v];

        int before = bit.query(i - 1);
        int after = n - v - 1 - before;
        ans += min(before, after);

        if (DEBUG) {
            cerr << "value " << b[v] << " before " << before << " " << after << endl;
        }

        bit.update(i, -1);
    }

    cout << ans << '\n';
}
