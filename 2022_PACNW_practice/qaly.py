# quality adjusted life year problem
# we get the total of the product of all inputs
n = int(input())
total = 0
for i in range(n):
    q, y = map(flaot, input().split())
    total += q * y
print("%.3f" % total)
