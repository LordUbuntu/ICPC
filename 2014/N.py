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
        print(min(result))


# new solution
# this works because c.most_common returns elements sorted first by
#   most occurences and then by increasing value, so always taking the
#   first element of the first index tuple will always return the
#   least valued most voted number.
from collections import Counter
def sol():
    T = int(input())
    for _ in range(T):
        V = int(input())
        # count occurrences of all votes
        c = Counter((int(input()) for _ in range(V)))
        # filter out only the most common numbers
        max_count = c.most_common()[0][1]
        c = filter(lambda t: t[1] == max_count, c.most_common())
        # get smallest number with most votes
        print(sorted(c)[0][0])
