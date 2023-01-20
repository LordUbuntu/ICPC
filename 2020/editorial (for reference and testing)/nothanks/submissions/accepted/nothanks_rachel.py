#!python3

# read input
n = int(input())
cards = map(int, input().split())

# sort the cards
cards = sorted(cards)

# step through cards to compute score
# only add card if it is not part of a consecutive sequence
score = cards[0]
for i in range(1, n):
    if cards[i] != cards[i-1]+1:
        score += cards[i]

print(score)