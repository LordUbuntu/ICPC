# grid problem
# the nature of a grid makes a natural representation of a graph, we don't
# need to convert it to a graph representation.
# more info: https://www.youtube.com/watch?v=KiCBXu4P-2Y


# collect input and construct grid from it
from itertools import chain, product
m, n = map(int, input().split())
grid = [[int(char) for char in input()] for _ in range(m)]  # values
pos = list(product(range(n), range(m)))  # corresponding position of tile
start = pos[0]
end = pos[len(pos) - 1]

# perform a dfs on all reachable vertices
visited, stack = [], [start]
while stack:
    v = stack.pop()
    visited.append(v)
