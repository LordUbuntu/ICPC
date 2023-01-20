#!/usr/bin/env python3

import sys
n, k = [int(x) for x in sys.stdin.readline().split(' ')]

cnt = [[0] * k for _ in range(k)]
for _ in range(n):
  s = sys.stdin.readline()
  for p in range(k):
    for q in range(p + 1, k):
      cnt[ord(s[p]) - ord('A')][ord(s[q]) - ord('A')] += 1

ans = 1
dp = [1] * k
for _ in range(k):
  for i in range(k):
    for j in range(k):
      if cnt[i][j] == n:
        dp[i] = max(dp[i], dp[j] + 1)
        ans = max(ans, dp[i])

print (ans)
