#!/usr/bin python3

import sys

n = int(sys.stdin.readline())
arr = [int(x) for x in sys.stdin.readline().split(' ')]
exist = [False] * 90001
for v in arr:
  exist[v] = True
ans = 0
for v in arr:
  if not exist[v - 1]:
    ans += v
print (ans)
