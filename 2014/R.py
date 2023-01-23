# ramp number problem



# My eventual realization that every ramp number contains a ramp number
#   within itself, and an exhaustive check led to me realizing my recursive
#   solution to this problem...
# The idea is that we recursively decrease the current number and also
#   chop off its leftmost digit, and count a 1 if we encounter a 1 or
#   a number that has previously been reached.
# For example:
#   13 => 12 and 3, 3 becomes 0 and 2, becomes 0 and 1, returns 1
#   12 => 11 and 2, 2 becomes 0 and 1, returns 1 (notice 2)
#   11 => 10 and 1, 1 returns 1
#   10 => not a ramp, returns 0
#   9  => becomes 0 and 8, 0 7, 0 6, 0 5, 0 4, 0 3, 3 returns 1
#   8  => becomes 0 and 7, returns 1
#   ...
#   1  => becomes 0 and 
# Note:
#   Still a work in progress... doesn't work for several cases yet
def ramp(n):
    # n is not a ramp number, go to next number down
    if not all(d1 <= d2 for d1, d2 in zip(n[:len(n) - 1], n[1:])):
        return ramp(n - 1) + 0
    # n is 0, return 0
    if n == 0:
        return 0
    # n is 1, return 1
    if n == 1:
        return 1
    # n is a single digit, go to next number down
    if len(str(n)) == 1:
        return ramp(n - 1) + 0
