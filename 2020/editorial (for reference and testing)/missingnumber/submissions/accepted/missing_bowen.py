#!/usr/bin python3

import sys

n = int(sys.stdin.readline())
s = sys.stdin.readline().rstrip()

for missing in range(1, n + 1):
  ms = ''
  for i in range(1, n + 1):
    if i == missing: continue
    ms += str(i)
  if ms == s:
    print (missing)
    sys.exit(0)
