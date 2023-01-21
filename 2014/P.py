# gold leaf
from operator import not_

T = int(input())
N, M = map(int, input().split())

def anti(M):
    return list(map(not_, M))

for _ in range(T):
    rows = [
        list(map(lambda c: True if c == '#' else False, input()))
        for _ in range(N)
    ]
    cols = list(map(list, zip(*rows)))

    
