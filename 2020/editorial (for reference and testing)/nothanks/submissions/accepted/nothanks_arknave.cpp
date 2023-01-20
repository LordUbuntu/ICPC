#include <cstdint>
#include <iostream>

using namespace std;

constexpr int MAXN = 9e4 + 4;

bool used[MAXN];

int main() {
    int n;
    cin >> n;

    for (int i = 0; i < n; ++i) {
        int x;
        cin >> x;
        used[x] = true;
    }

    int64_t ans = 0;
    for (int i = 1; i < MAXN; ++i) {
        if (used[i] && !used[i - 1]) {
            ans += i;
        }
    }

    cout << ans << endl;
}
