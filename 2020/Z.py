# Solved by: Finian and Yaacov
# Programmed by: Finian
# Problem: Missing Number
# Solution:
#   The idea is to iterate over the count string and check if the current
#   value of next is in it, if it isn't, we've found our missing number,
#   if it is, we chop off the approapriate number of chars and move on.

input()  # we can ignore n
numbers = input()
next = 1
while True:
    s = str(next)
    if not numbers.startswith(s):
        print(next)
        break
    numbers = numbers[len(s):]
    next += 1
