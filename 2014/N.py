# Jacobus Burger (2022)
# majority problem, 2014 ICPC D2

def solution():
    T = int(input())
    for _ in range(T):
        V = int(input())
        votes = {}
        for _ in range(V):
            vote = int(input())
            if vote in votes:
                votes[vote] += 1
            else:
                votes[vote] = 1
        result = []
        for k, v in votes.items():
            if v == max(votes.values()):
                result.append(k)
        print(f"{min(result): 8}")
