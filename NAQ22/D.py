# Problem D - Ghost Leg
n,m = input().split()
n = int(n)
m = int(m)
rungs = [int(input()) for _ in range(m)]
out = [0 for i in range(n)]
for i in range(1,n+1):
    pole = i
    for j in range(0,m):
        if rungs[j] == pole-1:
            pole -= 1
        elif rungs[j] == pole:
            pole += 1
    out[pole - 1] = i
for i in out:
    print(i)
