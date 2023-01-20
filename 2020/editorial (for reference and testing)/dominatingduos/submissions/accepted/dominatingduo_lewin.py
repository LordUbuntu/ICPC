#!python3
n = int(input())
arr = [int(input()) for __ in range(n)]
ans = 2 * n - 1

taken = [False for __ in range(n)]
pm = -1
for i in range(n):
    pm = max(pm, arr[i])
    if pm == arr[i]:
        taken[i] = True

pm = -1
for i in range(n-1,-1,-1):
    pm = max(pm, arr[i])
    if pm == arr[i]:
        taken[i] = True

for i in range(n):
    if taken[i]:
        ans -= 1

print(ans)
