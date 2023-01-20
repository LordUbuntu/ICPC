#!/usr/bin/env python3

import itertools


def main():
    n = int(input())
    xs = [int(input()) for _ in range(n)]

    ans = 0

    xs.sort()

    # Now for sets of size 2 or larger...
    # fix the two smallest elements in the set. Then we have a cap on the largest element
    for i in range(n):
        for j in range(i + 1, n):
            k = j + 1
            while k < n and xs[i] + xs[j] > xs[k]:
                k += 1

            # all values between j and k are optional
            ans = (ans + pow(2, k - j - 1) - 1) ;

    print(ans)


if __name__ == "__main__":
    main()
