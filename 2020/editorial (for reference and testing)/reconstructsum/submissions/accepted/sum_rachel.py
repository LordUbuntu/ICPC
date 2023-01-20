#!python3


# read n
n = int(input())

# read and sum the numbers
numbers = [0] * n
number_sum = 0
for i in range(n):
    numbers[i] = int(input())
    number_sum += numbers[i]

# want to find a number such that the sum of all other numbers is equal to that number
# x1 + x2 + x3 + ... + xn = Xsum
# so any valid solution requires that x1 + x2 + ... + xn = Xsum - xi
# ie, total sum - number = number

# check all numbers in this way
solved = False
for i in range(n):
    if number_sum - numbers[i] == numbers[i]:
        print(numbers[i])
        solved = True
        break

if not solved:
    print("BAD")