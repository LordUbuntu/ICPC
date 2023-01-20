#!python3

MOD = 998244353

def solve(s):
    n = len(s)

    ans = 0
    cur = 1
    for d in range(1, n):
        cur = (9 * cur) % MOD
        ans = (ans + cur) % MOD

    s = [int(c) for c in s]
    dp = [1, s[0] - 1]

    for i in range(1, n):
        ndp = [0, 0]
        ndp[0] = dp[0] if dp[0] > 0 and s[i] != s[i - 1] else 0
        ndp[1] = 9 * dp[1] % MOD

        if s[i - 1] < s[i]:
            ndp[1] += (s[i] - 1) * dp[0]
        else:
            ndp[1] += s[i] * dp[0]

        ndp[1] %= MOD

        dp = ndp

    return (ans + sum(dp)) % MOD

def is_rainbow(s):
    return all(x != y for x, y in zip(s, s[1:]))

def main():
    l = input()
    r = input()

    ans = solve(r) - solve(l) + is_rainbow(l)
    if ans < 0:
        ans += MOD

    ans %= MOD

    print(ans)

if __name__ == "__main__":
    main()
