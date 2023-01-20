#!/usr/bin python3

import sys

n, c = [int(x) for x in sys.stdin.readline().split(' ')]
items = []
for _ in range(n):
  items.append(int(sys.stdin.readline()))
items = sorted(items, reverse=True)

rsum = [0] * (n + 1)
for i in range(n-1,-1,-1):
  rsum[i] = rsum[i + 1] + items[i]
dp = [[False] * (c + 1) for _ in range(n + 1)]

dp[0][0] = True
ans = -1
for i in range(1, n+1):
  pt, t = i - 1, i
  w = items[i - 1]
  for v in range(0, c + 1):
    if dp[pt][v]:
      dp[t][v] = True
      if v + w <= c:
        dp[t][v + w] = True
  for v in range(0, c + 1):
    if dp[t][v]:
      r = c - rsum[i] - v
      if r >= 0 and r < w:
        ans = max(ans, r)

print (rsum[0] if ans == -1 else c - ans)
