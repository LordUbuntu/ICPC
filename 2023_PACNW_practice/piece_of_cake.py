# piece of cake problem
n, h, v = map(int, input().split())
print(max(h, n - h) * max(v, n -v) * 4)
