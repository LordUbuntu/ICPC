#!/usr/bin python3

import sys

s = [ord(c) - ord('1') for c in sys.stdin.readline().rstrip()]
n = len(s)
go = [[0] * 9 for _ in range(9)]
for i in range(1, n):
  a, b = s[i - 1], s[i]
  if a > b: a, b = b, a
  go[a][b] += 1

ans = int(1e9)

def check(pos):
  global ans
  sol = pos[s[0]]
  for i in range(9):
    for j in range(i + 1, 9):
      sol += go[i][j] * abs(pos[i] - pos[j])
  ans = min(ans, sol)

def permutation(d, u, pos):
  if d == 9:
    check(pos)
    return
  for p in range(9):
    if not u[p]:
      u[p] = True
      pos[d] = p
      permutation(d + 1, u, pos)
      u[p] = False

permutation(0, [False] * 9, [-1] * 9)

print (ans + n)
