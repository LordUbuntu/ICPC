# no thanks problem
# we sort and then count cards, only counting first in consecutive sequences
n = int(input())
cards = sorted(map(int, input().split()))
total = cards[0]  # count first card to start
for i in range(1, n):
    if cards[i - 1] != cards[i] - 1:
        total += cards[i]
print(total)
