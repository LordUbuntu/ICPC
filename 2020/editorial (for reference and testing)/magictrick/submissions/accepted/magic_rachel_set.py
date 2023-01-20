#!python3

# read card string
cards = input()

# insert each card letter into a set, checking for duplicates along the way
duplicate = False
card_set = set()
for card in cards:
    if card in card_set:
        duplicate = True
        break
    card_set.add(card)

# if there are no duplicates, you can always guess audience member's operation
# if there are duplicates, there's no way to tell if they swapped the identical cards or not
if duplicate:
    print(0)
else:
    print(1)