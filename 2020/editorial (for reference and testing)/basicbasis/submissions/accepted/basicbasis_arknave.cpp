#include <cassert>
#include <cstdint>
#include <iostream>
#include <map>
#include <vector>

constexpr int DEBUG = 0;

using namespace std;

struct Bitset {
    constexpr static int UNKNOWN = -2;

    Bitset(int n): n(n), cached_ffs(UNKNOWN) {
        data.assign((n + 63) / 64, 0);
    }

    bool get(int idx) const {
        int p = idx >> 6;
        int r = idx & 0x3F;

        if (DEBUG) {
            printf("Read idx %d gives p %d and r %d\n", idx, p, r);
        }

        return (data[p] & (1ULL << r)) > 0;
    }

    void set(int idx, int v) {
        int p = idx >> 6;
        int r = idx & 0x3F;

        if (v)
            data[p] |= (1ULL << r);
        else
            data[p] &= ~(1ULL << r);

        cached_ffs = UNKNOWN;
    }

    // find first set
    int ffs() {
        if (cached_ffs != UNKNOWN)
            return cached_ffs;

        for (size_t i = 0; i < data.size(); ++i) {
            if (data[i]) {
                for (uint64_t j = 63; j >= 0; --j) {
                    if (data[i] & (1ULL << j)) {
                        return cached_ffs = 64 * i + j;
                    }
                }
            }
        }

        return cached_ffs = -1;
    }

    void operator^=(const Bitset& o) {
        assert(n == o.n);
        for (size_t i = 0; i < data.size(); ++i) {
            data[i] ^= o.data[i];
        }

        cached_ffs = UNKNOWN;
    }

    int n, cached_ffs;
    vector<uint64_t> data;
};

Bitset read(int k) {
    Bitset bs(4 * k);
    string s;
    cin >> s;

    if (DEBUG) {
        printf("new read\n");
    }

    for (int i = 0; i < k; ++i) {
        // Intentionally kind of slow. Only sets 1 bit at a time instead of all 4
        int v = s[i] >= 'a' ? (s[i] - 'a' + 10) : (s[i] - '0');
        for (int j = 0; j < 4; ++j) {
            if (v & (1 << j)) {
                if (DEBUG)
                    printf("set bit %d\n", 4 * i + j);

                bs.set(4 * i + j, 1);
            }
        }
    }

    return bs;
}

int main() {
    int n, m, k;
    cin >> n >> m >> k;

    vector<Bitset> bs;
    bs.reserve(n);

    map<int, int> lookup;
    // This seems very slow
    // Potentially O(n^2 k log n)
    for (int i = 0; i < n; ++i) {
        bs.push_back(read(k));

        int bffs;
        while ((bffs = bs[i].ffs()) >= 0) {
            auto it = lookup.find(bffs);
            if (it == end(lookup)) {
                break;
            }

            if (DEBUG) {
                printf("xoring %d with %d\n", i, it->second);
            }
            bs[i] ^= bs[it->second];
        }

        if (DEBUG)
            printf("FFS of %d is %d\n", i, bffs);
        if (bffs >= 0) {
            assert(lookup.count(bffs) == 0);
            lookup[bffs] = i;
        }
    }

    // O(m * k * n / 16) also seems slow
    for (int i = 0; i < m; ++i) {
        Bitset a = read(k);
        int ans = -1;

        // handle a == 0 specially since we can't have an empty set
        if (a.ffs() < 0) {
            for (int j = 0; j < n; ++j) {
                if (bs[j].ffs() < 0) {
                    ans = j + 1;
                    break;
                }
            }
        } else {
            int affs;
            while ((affs = a.ffs()) >= 0) {
                auto it = lookup.find(affs);
                if (it == end(lookup)) {
                    break;
                }

                if (DEBUG) {
                    printf("xoring with %d\n", it->second);
                }

                a ^= bs[it->second];
                ans = max(ans, it->second + 1);
            }

            if (a.ffs() >= 0) {
                ans = -1;
            }
        }

        cout << ans << '\n';
    }
}
