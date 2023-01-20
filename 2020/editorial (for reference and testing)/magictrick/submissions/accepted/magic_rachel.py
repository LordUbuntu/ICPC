#!python3

# read card string
cards = input()

# sort the cards
cards = sorted(cards)

# step through the sorted cards, checking for duplicates
duplicate = False
for i in range(1, len(cards)):
    if cards[i] == cards[i-1]:
        duplicate = True
        break

# if there are no duplicates, you can always guess audience member's operation
# if there are duplicates, there's no way to tell if they swapped the identical cards or not
if duplicate:
    print(0)
else:
    print(1)