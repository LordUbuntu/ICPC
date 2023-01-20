#!/bin/python3
n = int(input())
cards = [int(x) for x in input().strip().split()]
cards = sorted(cards)
total = cards[0]
for i in range(1,len(cards)):
    if cards[i] != 1+cards[i-1]:
        total += cards[i]
print(total)

