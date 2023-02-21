# calculate total number of differences and return 2 to the power of that
from operator import ne
s1 = input()
s2 = input()
print(2**sum(map(ne, s1, s2)))
