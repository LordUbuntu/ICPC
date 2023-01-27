# excellence

N = int(input())
s = sorted(map(int, (input() for _ in range(N))))
sums = []
for i in range(N//2):
    sums.append(s[i] + s[N - 1 - i])
print(min(sums))
