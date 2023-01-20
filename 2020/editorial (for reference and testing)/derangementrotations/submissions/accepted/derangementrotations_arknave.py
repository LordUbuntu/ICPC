#!/usr/bin/env python3
import math

def main():
    n, mod = map(int, input().split())

    ans = 0
    divs = 0
    for i in range(2, n):
        g = math.gcd(n, i)
        if g > 1:
            divs += 1
            ans += pow(2, g - 1, mod)
            if ans >= mod:
                ans -= mod

    ans += mod - divs
    if ans >= mod:
        ans -= mod

    ans = (n - 2) * ans % mod

    print(ans)


if __name__ == "__main__":
    main()
