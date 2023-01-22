# Solved by Yaacov, Finian, and Jacobus
# Programmed by Jacobus
# rating problems

n, k = map(int, input().split())
a = n - k
sum = 0
for _ in range(k):
    sum += int(input())
min = ((a* -3) + sum) / n
max = ((a * 3) + sum) / n

print(min, max)
