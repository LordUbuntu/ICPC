#!python3

import itertools


def score(a, b):
    return sum(x == y for x, y in zip(a, b))


def main():
    n, k = map(int, input().split())
    subs = [input() for _ in range(n)]

    ans = 0
    for key in itertools.product("TF", repeat=k):
        cur = min(score(sub, key) for sub in subs)
        ans = max(ans, cur)

    print(ans)


if __name__ == "__main__":
    main()
