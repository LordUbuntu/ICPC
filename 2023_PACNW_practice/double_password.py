# calculate total number of differences and return 2 to the power of that
from operator import ne
print(2**sum(map(ne, input(), input())))
