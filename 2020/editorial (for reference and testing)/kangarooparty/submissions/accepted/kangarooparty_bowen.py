#!/usr/bin python3

import sys

n = int(sys.stdin.readline())


xs = [int(sys.stdin.readline()) for _ in range(n)]

ans = int(1e18)
for p in xs:
  for q in xs:
    sol = 0
    for x in xs:
      sol += min((x - p) * (x - p), (x - q) * (x - q))
    ans = min(ans, sol)
print (ans)
