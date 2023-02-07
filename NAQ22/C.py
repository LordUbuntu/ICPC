# Problem C - Class Field Trip
# The wonders of sorted input, we can just cat, sort, print it!
print(''.join(sorted(list(input() + input()))))


# one minor improvement of mine is that the list() conversion is
#   redundant since a string is treated like a list of char anyways.
print(''.join(sorted(input() + input())))
