#!python3
import itertools
import math


def main():
    n = int(input())
    xs = [int(input()) for _ in range(n)]

    ans = math.inf
    for hosts in itertools.combinations(xs, 2):
        cost = sum(min((x - host) ** 2 for host in hosts) for x in xs)
        ans = min(ans, cost)

    print(ans)


if __name__ == "__main__":
    main()
