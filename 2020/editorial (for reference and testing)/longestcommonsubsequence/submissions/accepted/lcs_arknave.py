#!/usr/bin/env python3

from collections import deque
import itertools


def main():
    n, k = map(int, input().split())

    # compute how many times characters appear in order over all strings
    freq = [[0 for _ in range(k)] for _ in range(k)]
    for _ in range(n):
        s = [ord(c) - ord("A") for c in input()]
        for i in range(k):
            for j in range(i + 1, k):
                freq[s[i]][s[j]] += 1

    # consider two characters in our LCS. They have to appear in the same order in all previous strings.
    # build a graph where there is an edge between two chars if they appear in the same order in all strings
    # answer is just the longest path in this DAG

    in_degree = [0 for _ in range(k)]
    graph = [list() for _ in range(k)]
    for x, y in itertools.product(range(k), repeat=2):
        if x == y:
            continue

        if freq[x][y] == n:
            graph[x].append(y)
            in_degree[y] += 1

    # dp[x] = longest path ending at x
    dp = [1 for _ in range(k)]

    # topological sort
    q = deque([x for x in range(k) if in_degree[x] == 0])
    while q:
        x = q.popleft()
        for y in graph[x]:
            dp[y] = max(dp[y], dp[x] + 1)
            in_degree[y] -= 1
            if in_degree[y] == 0:
                q.append(y)

    print(max(dp))


if __name__ == "__main__":
    main()
