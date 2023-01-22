# Solved by: Yaacov, Finian
# Programmed by: Finian
# Problem: Magic Trick
# Solution:
#   To guarantee that we will always be able to guess correctly, we simply
#   need to check whether there are any repeated characters or not.
#   To do this, we compare the length of the input with the length of
#   the input represented as a set (no repetitons). If they match, then
#   there are no repeated characters (and we can guarantee we will
#   always be able to guess correctly), if not, then we can't.
#   This solution is very elegant in Python.

inp = input()
print(1 if (len(set(inp)) == len(inp)) else 0)
