#include <algorithm>
#include <cassert>
#include <iostream>
#include <numeric>
#include <vector>

using namespace std;

int score(const vector<int>& perm) {
    int n = perm.size();

    assert(perm[0] == 1);
    int other = -1;
    for (int i = 0; i < n; ++i) {
        int d = (perm[i] + n - i) % n;
        if (d == 0) {
            // cout << "failing, not derangement" << endl;
            return -1;
        }

        if (d != 1 && other != -1 && d != other) {
            // cout << "failing, got d " << d << " expected other " << other << endl;
            return -1;
        }

        if (other == -1 && d != 1)
            other = d;
    }

    return other;
}

vector<int> brute(int n) {
    vector<int> perm(n);
    iota(perm.begin(), perm.end(), 0);
    swap(perm[0], perm[1]);

    vector<int> freq(n, 0);
    do {
        int offset = score(perm);
        if (offset >= 0) {
            ++freq[offset];
        }
    } while (next_permutation(perm.begin() + 1, perm.end()));

    return freq;
}

int main() {
    int n, mod;
    cin >> n >> mod;

    vector<int> pow2 = {1};
    for (int i = 1; i < n; ++i) {
        int x = pow2.back() << 1;
        if (x >= mod)
            x -= mod;

        pow2.push_back(x);
    }

    int ans = 0;
    int divs = 0;
    for (int i = 2; i < n; ++i) {
        int g = gcd(n, i);
        if (g > 1) {
            ++divs;
            ans += pow2[gcd(n, i) - 1];
            if (ans >= mod)
                ans -= mod;
        }
    }

    ans -= divs;
    if (ans < 0)
        ans += mod;

    cout << (1LL * ans * (n - 2) % mod) << '\n';

    return 0;
}
