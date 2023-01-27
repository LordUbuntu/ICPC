# Egg drop


N, K = map(int, input().split())
exp = []
for _ in range(N):
    i = input().split()
    exp.append((int(i[0]), i[1]))
exp.append((K, "BROKEN"))
exp.append((1, "SAFE"))
exp = sorted(exp)
for i in range(len(exp)):
    if exp[i][1] == "BROKEN":
        broken = exp[i - 1][0] + 1
        safe = exp[i][0] - 1
        print(broken, safe)
        break
