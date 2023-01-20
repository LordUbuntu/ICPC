#!python3


# read input
n = int(input())

# read integer array
numbers = [0] * n
for i in range(n):
    numbers[i] = int(input())

# sort it
numbers = sorted(numbers)

# count the sets!
triangular_sets = 0

# start by choosing first 2 numbers in the triangular set
# look at all pairs, starting from the smallest
for a in range(n):
    for b in range(a+1, n):

        # conditions for a triangle:
        #   a + b > c
        #   b + c > a
        #   a + c > b

        # if we assume a and b are 2 sides of a triangle, then c must fall in the range:
        #   b < c < a + b

        # we've already 'selected' a and b, so find the largest possible c
        c = b+1
        while c < n and numbers[c] < numbers[a] + numbers[b]:
            c += 1

        # now c is the first number too big to be in the triangular set containing a and b
        # this means there are m = c - b - 1 possible c values to go with a and b
        # any of those c values would make a triangle

        # additionally, any trio from {a, b, c1, c2, ... cm} will form a valid triangle
        # because a + b + cm is the 'worst-case'
        # for all possible triples from this set a, b, c with a < b < c:
        #   b > a   =>   b + c > a
        #   c > b   =>   a + c > b
        #   a + b > c   because c < a + b (set creation)

        # so any non-empty subset of {c1, c2, ... cm} combined with {a, b}
        # is a valid triangular set
        # and there are 2^m - 1 such subsets
        triangular_sets += (2 ** (c - b - 1)) - 1

print(triangular_sets)