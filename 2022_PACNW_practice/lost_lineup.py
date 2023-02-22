# lost lineup problem
# just put each friend at the input + 1 back from the front
n = int(input())
friends = map(int, input().split())
line = [1 for i in range(n)]
for i in range(2, n + 1):
    line[next(friends) + 1] = i
print(*line)
