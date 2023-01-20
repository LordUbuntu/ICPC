#!python3

s = input()
f = []
for _ in range(9):
  f.append([0] * 9)
for i in range(len(s)-1):
  f[int(s[i])-1][int(s[i+1])-1] += 1

ret = float('inf')

def dfs(currcost, idx, used, perm):
  global ret
  if currcost >= ret:
    return
  if idx == 9:
    ret = min(ret, currcost)
    return
  for candval in range(9):
    if used[candval]:
      continue
    used[candval] = True
    candcost = currcost
    for oidx, val in enumerate(perm):
      candcost += (idx - oidx) * (f[candval][val] + f[val][candval])
    if candval == int(s[0])-1:
      candcost += idx
    perm.append(candval)
    dfs(candcost, idx+1, used, perm)
    perm.pop()
    used[candval] = False

dfs(0, 0, [False] * 9, [])
print(ret + len(s))